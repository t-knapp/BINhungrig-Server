package de.fhbingen.binhungrig.server.parser;

/**
 * URL Builder for Studierendenwerk Mainz
 * 
 * @author tknapp
 *
 */
public class UrlBuilder {

	private final String BUILDING = "building_id=";
	private String baseURL = "http://www.studierendenwerk-mainz.de/speiseplan/db/include.php?";
	private long building;
	private TimeInterval timeInterval;
		
	public UrlBuilder setBuilding(final long buildingId){
		this.building = buildingId;
		return this;
	}
	
	public UrlBuilder setTimeInterval(TimeInterval timeinterval){
		this.timeInterval = timeinterval;
		return this;
	}
	
	public String getUrl() {
		StringBuilder sb = new StringBuilder(baseURL);
		sb.append(BUILDING).append(building);
		
		//Append time modifier if set
		if(timeInterval != null){
			sb.append(this.timeInterval.toString());
		}
		
		return sb.toString();
	}
	
	public long getBuildingId(){
		return building;
	}
	
	public enum Building {
		ZENTRALMENSA ("building_id=1"),
		BINGENCAMPUS ("building_id=4"),
		BINGENSTADT  ("building_id=8");
		
		private final String modifier;
		
		Building(final String modifier){
			this.modifier = modifier;
		}
		
		@Override
		public String toString() {
			return this.modifier;
		}
		
		public long getBuildingId(){
			return Long.parseLong(modifier.split("=")[1]);
		}
		
	}
	
	public enum TimeInterval {
		CURRENTDAY  ("&display_type=1"),
		CURRENTWEEK ("&display_type=2"), //XHR Default if not specified
		NEXTWEEK    ("&display_type=3");
		
		private final String modifier;
		
		private TimeInterval(final String modifier) {
			this.modifier = modifier;
		}
		
		@Override
		public String toString() {
			return this.modifier;
		}
	}
}
