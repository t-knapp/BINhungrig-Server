package de.fhbingen.binhungrig.server.parser;

import com.fasterxml.jackson.annotation.JsonValue;

import de.fhbingen.binhungrig.server.parser.UrlBuilder.Building;

public class Dish {

	public enum DishType {
		NORMAL,
		VEGETARIAN,
		VEGAN;
		
		@JsonValue
		public int toValue(){
			return this.ordinal();
		}
	}

	public Dish(
			final String title,
			final String date,
			final String ingredients,
			final float priceStd,
			final float priceNonStd,
			final long buildingId,
			final DishType type) {
		this.title       = title;
		this.date        = date;
		this.ingredients = ingredients;
		this.priceStd    = priceStd;
		this.priceNonStd = priceNonStd;
		this.buildingId    = buildingId;
		this.type        = type;
	}
	
	public float getPriceStd() {
		return priceStd;
	}

	public float getPriceNonStd() {
		return priceNonStd;
	}

	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public String getIngredients() {
		return ingredients;
	}
	public long getBuildingId() {
		return buildingId;
	}
	public DishType getType() {
		return type;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[Dish] : { ");
		sb.append(date).append(" | ");
		sb.append(title).append(" | ");
		sb.append(ingredients).append(" | ");
		sb.append(priceStd + " / " + priceNonStd).append(" | ");
		sb.append(type).append(" | ");
		sb.append(buildingId).append(" }");
		return sb.toString();
	}
	
	private String title;
	private String date;
	private String ingredients;
	//private Float[] prices; //Index 0 student, Index 0 non-student
	private float priceStd;
	private float priceNonStd;
	private DishType type;
	private long buildingId;
}
