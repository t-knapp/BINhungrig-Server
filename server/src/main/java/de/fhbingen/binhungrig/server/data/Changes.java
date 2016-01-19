package de.fhbingen.binhungrig.server.data;

import java.util.LinkedList;
import java.util.List;

/**
 * Container object holding all changes
 * 
 * @author tknapp
 *
 */
public class Changes {

	public List<Building> buildings;
	public List<Ingredient> ingredients;
	public List<Date> dates;
	public List<Delete> deletes      = new LinkedList<Delete>();
	public List<Dish> dishes         = new LinkedList<Dish>();
	public List<Rating> ratings      = new LinkedList<Rating>();
	public List<OfferedAt> offeredAt = new LinkedList<OfferedAt>();
	public List<Photo> photos        = new LinkedList<Photo>();
	public Sequence sequence;
	public boolean needToUpdate;
	
}
