import java.util.List;
import java.lang.Math;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CachedPresenter{
	CachedFoodTruckConsumer cachedFoodTruck;

	public CachedPresenter() throws Exception {
		this.cachedFoodTruck = CachedFoodTruckConsumer.createCachedFoodTruckConsumer();
	}


	public void interactivePresentation() throws Exception {

		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance		
		
		while (cachedFoodTruck.hasNext()) {

			System.out.println("=========================================================================================================");
			System.out.println(" Name	          \t Address (and other information)            "+cachedFoodTruck.pageInfo());
			System.out.println("---------------- \t-----------------------------------------------------------------------");

			List<FoodTruck> foodTrucks = cachedFoodTruck.next();				
			for (FoodTruck foodTruck:foodTrucks) {

				calendar.setTime(new Date());   // assigns calendar to given date 

				String name = foodTruck.getName();
				StringBuilder addressBldr = new StringBuilder();
					addressBldr.append(foodTruck.getAddress());
					addressBldr.append(" (");
					addressBldr.append(foodTruck.getStartTime());
					addressBldr.append("~");
					addressBldr.append(foodTruck.getEndTime());
					int openTime = Integer.parseInt(foodTruck.getStart24().substring(0,2));
					int closeTime = Integer.parseInt(foodTruck.getEnd24().substring(0,2));
					int curTime = calendar.get(Calendar.HOUR_OF_DAY);
					if (openTime > curTime) {
						addressBldr.append(" - Not OPEN Yet!) ");
					}
					else if ( closeTime <= curTime) {
						addressBldr.append(" - CLOSED Now!) ");
					}
					else {
						addressBldr.append(") ");
					}				
					addressBldr.append(foodTruck.getShortDescription());
				String address = addressBldr.toString();
					
				int lineFeeds = Math.max(name.length()/20, address.length()/90) + 2;
				for (int i=0; i < lineFeeds; i++) {
					int n1 = Math.min(i*20,name.length());
					int n2 = Math.min((i+1)*20,name.length());
					int a1 = Math.min(i*90,address.length());
					int a2 = Math.min((i+1)*90,address.length());
						
					System.out.println(String.format("%1$-20s", name.substring(n1,n2))
								+"\t"+
								String.format("%1$-90s", address.substring(a1,a2)));
				}

			}
 			System.in.read();
		}

	}	

}

