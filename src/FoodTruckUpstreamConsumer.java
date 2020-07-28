import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.json.JSONArray;
import org.json.JSONObject;

//  
public class FoodTruckUpstreamConsumer {
	int dayOfWeek;
	Pageable pageable;	
	String baseURL;

	protected FoodTruckUpstreamConsumer(String baseURL, int totalCount, int dayOfWeek) throws Exception {
		this.baseURL = baseURL; 
		this.pageable = new DefaultPagination(totalCount);
		this.dayOfWeek = dayOfWeek;
	}

	// factory pattern 
	public static FoodTruckUpstreamConsumer createDefaultUpstreamConsumer() throws Exception {
		String foodTruckURL = "http://data.sfgov.org/resource/jjew-r69b.json";
		
		// get the total count of the food trucks
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance		
		calendar.setTime(new Date());
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 

		String totalQueryURL = foodTruckURL + "?dayorder=" + dayOfWeek
						+"&$select=count(locationid)"; // location id is selected as a representative Id 
		String jsonText = getJsonTextFromUpstream(totalQueryURL);
		JSONArray totalArray = new JSONArray(jsonText); // the API always returns an array
		JSONObject counter = totalArray.getJSONObject(0); // the first element of the array
		int totalTrucks = counter.getInt("count_locationid");

		return new FoodTruckUpstreamConsumer(foodTruckURL, totalTrucks,dayOfWeek);
	}

	protected static String getJsonTextFromUpstream(String upstreamURL) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(upstreamURL);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();

		return result.toString();
	}

	public boolean hasNext() throws Exception {
		return this.pageable.hasNext();
	}

	protected String getNextQueryURL() {
		StringBuilder nextURL = new StringBuilder();

		nextURL.append(this.baseURL);
 		nextURL.append("?dayorder=");
		nextURL.append(this.dayOfWeek);
		nextURL.append("&$order=");
		nextURL.append(this.pageable.getSortBy());
		nextURL.append("&$limit=");
		nextURL.append(this.pageable.getPageSize());
		nextURL.append("&$offset=");
		nextURL.append(this.pageable.getOffset());

		return nextURL.toString();
	}

	public List<FoodTruck> next() throws Exception {
		ArrayList<FoodTruck> list = new ArrayList<FoodTruck>(); 
		
		String jsonText = getJsonTextFromUpstream(getNextQueryURL());
		
		// execute the query and convert into list of foodtrucks	
		JSONArray foodTruckArray = new JSONArray(jsonText);
		for (int i = 0; i < foodTruckArray.length(); i++) {
			JSONObject truck = foodTruckArray.getJSONObject(i);
			Set<String> keys = truck.keySet();

			FoodTruck foodTruck = new FoodTruck( 
							truck.getString("applicant"), 
							truck.getString("location"),
							truck.getString("starttime"),
							truck.getString("endtime"),
							truck.getString("start24"),
							truck.getString("end24")
						);
			// set the optional fields when exists
			if (keys.contains("locationdesc")) {
				foodTruck.setLocationDescription(truck.getString("locationdesc"));
			}
			if (keys.contains("optionaltext")) {
				foodTruck.setShortDescription(truck.getString("optionaltext"));
			}

			// add to the list
			list.add(foodTruck);
		}

		this.pageable.next();
		return list;
	}

	public String pageInfo() {
		return "Page "+this.pageable.getCurrentPage()+" of "+this.pageable.getTotalPages();
	}
}
