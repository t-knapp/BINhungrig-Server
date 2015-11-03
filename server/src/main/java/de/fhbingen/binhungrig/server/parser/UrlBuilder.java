package de.fhbingen.binhungrig.server.parser;

public class UrlBuilder {

	private String baseURL = "http://www.studierendenwerk-mainz.de/speiseplan/db/include.php?";
	private Building building;
	private TimeInterval timeInterval;
		
	public UrlBuilder setBuilding(Building building){
		this.building = building;
		return this;
	}
	
	public UrlBuilder setTimeInterval(TimeInterval timeinterval){
		this.timeInterval = timeinterval;
		return this;
	}
	
	public String getUrl() {
		StringBuilder sb = new StringBuilder(baseURL);
		sb.append(this.building.toString());
		
		//Append time modifier if set
		if(timeInterval != null){
			sb.append(this.timeInterval.toString());
		}
		
		return sb.toString();
	}
	
	public Building getBuilding(){
		return building;
	}
	
	public enum Building {
		ZENTRALMENSA ("building_id=1"),
		BINGENCAMPUS ("building_id=4"),
		BINGENSTADT  ("building_id=8");
		
		private final String modifier;
		
		private Building(final String modifier){
			this.modifier = modifier;
		}
		
		@Override
		public String toString() {
			return this.modifier;
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
