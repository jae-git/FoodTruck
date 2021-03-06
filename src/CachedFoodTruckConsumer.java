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
public class CachedFoodTruckConsumer {
	// static variable single_instance of the cache // a simulated cache of backend service  
	private static List<FoodTruck> cached_foodTrucks = null;
	
	static int totalCounter;
	
	Pageable pageable;	

	protected CachedFoodTruckConsumer() throws Exception {
		this.pageable = new DefaultPagination(this.totalCounter);
	}

	// factory pattern 
	public static CachedFoodTruckConsumer createCachedFoodTruckConsumer() throws Exception {

		if (cached_foodTrucks == null) {
			System.out.println("This is a simulated back-end service action to refresh the daily cached list of food trucks");
			populateDailyCache();
		}	
		
		return new CachedFoodTruckConsumer();
	}


	private static void populateDailyCache() throws Exception {  // simulated daily cache refresh
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

		totalCounter = totalTrucks;

		// refresh the daily cache
		String dailyQuery = foodTruckURL + "?dayorder=" + dayOfWeek + "&$order=applicant";
		
		cached_foodTrucks = getAllFoodTrucks(dailyQuery);
	}


	public boolean hasNext() throws Exception {
		return this.pageable.hasNext();
	}

	public String pageInfo() {
		return "Page "+this.pageable.getCurrentPage()+" of "+this.pageable.getTotalPages();
	}

	public List<FoodTruck> next(){
		int offset = this.pageable.getOffset();
		int pageSize = this.pageable.getPageSize();
		this.pageable.next();
		int page_end_index = java.lang.Math.min(offset+pageSize,totalCounter);
		return cached_foodTrucks.subList(offset, page_end_index);
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


	protected static List<FoodTruck> getAllFoodTrucks(String queryURL) throws Exception {
		ArrayList<FoodTruck> list = new ArrayList<FoodTruck>(); 
		
		String jsonText = getJsonTextFromUpstream(queryURL);
		
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

		return list;
	}

}
