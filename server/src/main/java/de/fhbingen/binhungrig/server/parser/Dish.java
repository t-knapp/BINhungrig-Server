package de.fhbingen.binhungrig.server.parser;

public class Dish {

	public enum DishType {
		NORMAL,
		VEGETARIAN,
		VEGAN
	}

	public Dish(
			final String title,
			final String date,
			final String[] incredients,
			final Double[] prices,
			final DishType type) {
		this.title       = title;
		this.date        = date;
		this.incredients = incredients;
		this.prices      = prices;
		this.type        = type;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public String[] getIncredients() {
		return incredients;
	}
	public Double[] getPrices() {
		return prices;
	}
	public DishType getType() {
		return type;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[Dish] : { ");
		sb.append(date).append(" | ");
		sb.append(title).append(" | ");
		sb.append(type).append(" }");
		return sb.toString();
	}
	
	private String title;
	private String date;
	private String[] incredients;
	private Double[] prices; //Index 0 student, Index 0 non-student
	private DishType type;
}
