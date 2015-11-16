package de.fhbingen.binhungrig.server.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.fhbingen.binhungrig.server.parser.Dish.DishType;

@Entity
@Table(name = "Dishes")
public class Dish {

	public Dish(){
		
	}
	
	public Dish(
			final String title,
			final String ingredients,
			final float priceStd,
			final float priceNonStd,
			final DishType type,
			final long buildingId
			){
		this.title = title;
		this.ingredients = ingredients;
		this.priceStd = priceStd;
		this.priceNonStd = priceNonStd;
		this.buildingId = buildingId;
		this.type = type;
	}
	
	@Id
	@GeneratedValue
	private long dishId;
	
	private long seq;
	
	private String title;
	
	private String ingredients;
	
	private float priceStd;
	
	private float priceNonStd;
	
	@Enumerated(EnumType.ORDINAL)
	private DishType type;
	
	@Column(name = "fk_buildingId")
	private long buildingId;
	
	//Ref. to dates on this dish is offered.
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

	public String getIngredients() {
		return ingredients;
	}
		
	public DishType getType() {
		return type;
	}

	/**
	 * Compare this Dish to a Dish from Parser
	 * @return
	 */
	public boolean needToUpdate(final de.fhbingen.binhungrig.server.parser.Dish other){
		// Possible Changes: Prices, Ingredients
		if(priceNonStd != other.getPriceNonStd() || priceStd != other.getPriceStd()){
			return true;
		}
		
		String[] myIngredients = ingredients.split(",");
		Arrays.sort(myIngredients);
		
		String[] otherIngredients = other.getIngredients().split(",");
		Arrays.sort(otherIngredients);
		
		if(!Arrays.deepEquals(myIngredients, otherIngredients)){
			return true;
		}
		
		return false;
	}
	
	public Dish update(final de.fhbingen.binhungrig.server.parser.Dish newDish){
		// Possible Changes: Prices, Ingredients
		priceNonStd = newDish.getPriceNonStd();
		priceStd = newDish.getPriceStd();
		ingredients = newDish.getIngredients();
		return this;
	}
}
