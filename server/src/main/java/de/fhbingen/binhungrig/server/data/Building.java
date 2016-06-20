package de.fhbingen.binhungrig.server.data;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * JPA Entity for Buildings
 * 
 * @author tknapp
 *
 */
@Entity
@Table(name="Buildings")
public class Building {

	@Id
	private long buildingId;
	
	//TODO: Value generated by MySQL Trigger but not passed to JPA after insert/update
	//@GeneratedValue(strategy=GenerationType.TABLE)
	//@Column(columnDefinition = "INT(10) auto_increment")
	//@Basic(optional = false)
	//@Column(insertable = false, updatable = false)
	private long seq;
	
	private String name;
	private String address;
	private Time timeOpenFrom;
	private Time timeOpenTill;
	
	//All dishes that getting served in this building
	@OneToMany(mappedBy = "building")
	@JsonIgnore
	private List<Dish> dishes;
	
	@JsonIgnore
	public List<Dish> getDishes(){
		return dishes;
	}
	
	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}

	public long getSeq() {
		return seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Time getTimeOpenFrom() {
		return timeOpenFrom;
	}

	public void setTimeOpenFrom(Time timeOpenFrom) {
		this.timeOpenFrom = timeOpenFrom;
	}

	public Time getTimeOpenTill() {
		return timeOpenTill;
	}

	public void setTimeOpenTill(Time timeOpenTill) {
		this.timeOpenTill = timeOpenTill;
	}

	@Override
    public String toString() {
        return String.format(
                "Building[buildingId=%d, seq='%d', name='%s', address='%s']",
                buildingId, seq, name, address);
    }
	
	@JsonIgnore
	public boolean isOpenNow(){
		// create a java calendar instance
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(1970, 0, 1);
    	 
    	// get a java date (java.util.Date) from the Calendar instance.
    	// this java date will represent the current date, or "now".
    	java.util.Date currentDate = calendar.getTime();
    	 
    	// now, create a java.sql.Date from the java.util.Date
    	java.sql.Date nowDate = new java.sql.Date(currentDate.getTime());
        return this.timeOpenFrom.before(nowDate)
                && this.timeOpenTill.after(nowDate);
	}
}
