package de.fhbingen.binhungrig.server.parser;

import java.util.Arrays;

import de.fhbingen.binhungrig.server.parser.UrlBuilder.Building;

public class Dish {

	public enum DishType {
		NORMAL,
		VEGETARIAN,
		VEGAN
	}

	public Dish(
			final String title,
			final String date,
			final String[] ingredients,
			final Double[] prices,
			final Building building,
			final DishType type) {
		this.title       = title;
		this.date        = date;
		this.ingredients = ingredients;
		this.prices      = prices;
		this.building    = building;
		this.type        = type;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public String[] getIngredients() {
		return ingredients;
	}
	public Double[] getPrices() {
		return prices;
	}
	public Building getBuilding() {
		return building;
	}
	public DishType getType() {
		return type;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[Dish] : { ");
		sb.append(date).append(" | ");
		sb.append(title).append(" | ");
		sb.append(Arrays.toString(ingredients)).append(" | ");
		sb.append(Arrays.toString(prices)).append(" | ");
		sb.append(type).append(" | ");
		sb.append(building.name()).append(" }");
		return sb.toString();
	}
	
	private String title;
	private String date;
	private String[] ingredients;
	private Double[] prices; //Index 0 student, Index 0 non-student
	private DishType type;
	private Building building;
}
