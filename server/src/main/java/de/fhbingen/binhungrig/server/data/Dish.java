package de.fhbingen.binhungrig.server.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Dishes")
public class Dish {

	@Id
	private long dishId;
	
	private long seq;
	
	private String title;
	
	//TODO: String-Array of Ingredients
	//TODO: Before insert, check if ingredients did change or not to
	//prevent seq++ if content did not change.
	
	private float priceStd;
	
	private float priceNonStd;
	
	@Column(name = "fk_buildingId")
	private long buildingId;
	
	//Ref. to dates on this dish is offered.
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "offeredAt", 
	    joinColumns = @JoinColumn(name = "fk_dishId", referencedColumnName = "dishId"),
	    inverseJoinColumns = @JoinColumn(name = "fk_dateId", referencedColumnName = "dateId")
	)
	@JsonIgnore
	private List<Date> offeredAtDates;
	
	@JsonIgnore
	public List<Date> getOfferedAtDates(){
		return offeredAtDates;
	}

	//Ref. to building in which this dish is offered.
	//Need to be fetched EAGER to avoid Jackson Serializer Exception in Lazy mode (?!)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_buildingId", updatable = false, insertable = false)
	@JsonIgnore
	private Building building;
	
	@JsonIgnore
	public Building getBuilding() {
		return building;
	}
	
	@OneToMany(mappedBy = "dish")
	@JsonIgnore
	private List<Rating> ratings;
	
	@JsonIgnore
	public List<Rating> getRatings(){
		return ratings;
	}
	
	public long getBuildingId(){
		return buildingId;
	}
	
	public long getDishId() {
		return dishId;
	}

	public long getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}

	public float getPriceStd() {
		return priceStd;
	}

	public float getPriceNonStd() {
		return priceNonStd;
	}
		
}
