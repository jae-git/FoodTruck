public class FoodTruck{
	String name;
	String address;
	String shortDescription;
	String locationDescription;
	String startTime;
	String endTime;
	String start24;
	String end24;

	public FoodTruck(String name, String address, String startTime, String endTime, String start24, String end24) {
		this.name = name;
		this.address = address;
		this.startTime = startTime;
		this.endTime = endTime;
		this.start24 = start24;
		this.end24 = end24;
		this.shortDescription = "";
		this.locationDescription = "";
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getLocationDescription() {
		return this.locationDescription;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public String getStart24() {
		return this.start24;
	}

	public String getEnd24() {
		return this.end24;
	}
}
