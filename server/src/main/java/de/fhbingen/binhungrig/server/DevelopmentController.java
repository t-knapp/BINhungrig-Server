package de.fhbingen.binhungrig.server;

import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fhbingen.binhungrig.server.data.Building;
import de.fhbingen.binhungrig.server.data.BuildingRepository;
import de.fhbingen.binhungrig.server.data.DateRepository;
import de.fhbingen.binhungrig.server.data.DeleteRepository;
import de.fhbingen.binhungrig.server.data.Dish;
import de.fhbingen.binhungrig.server.data.DishRepository;
import de.fhbingen.binhungrig.server.data.Ingredient;
import de.fhbingen.binhungrig.server.data.IngredientRepository;
import de.fhbingen.binhungrig.server.data.OfferedAtRepository;
import de.fhbingen.binhungrig.server.data.PhotoRepository;
import de.fhbingen.binhungrig.server.data.Rating;
import de.fhbingen.binhungrig.server.data.RatingRepository;
import de.fhbingen.binhungrig.server.data.SequenceRepository;

@RestController
@RequestMapping("/dev")
public class DevelopmentController {
	
	/*
	 * DEVELOPMENT
	 */
	@RequestMapping(value = "/ingredients", method = RequestMethod.GET)
	@ResponseBody
	public List<Ingredient> getAll(@RequestParam final long seq) {
		return ingredientRepo.findBySeqGreaterThan(seq);
	}	
	
	@RequestMapping("/buildings")
	public List<Building> buildings(){
		return buildingRepo.findAll();
	}
	
	@RequestMapping("/buildings/id")
	public String buildings(@RequestParam(value="id") long id){
		return buildingRepo.findByBuildingId(id).toString();
	}
	
	@RequestMapping("/buildings/{id}/dishes")
	public List<Dish> dishesOfBuilding(@PathVariable(value="id") long id){
		return buildingRepo.findByBuildingId(id).getDishes();
	}
	
	
	@RequestMapping("/buildings/new/{id}/{name}/{address}")
	public Building newBuilding(
			@PathVariable long id,
			@PathVariable String name,
			@PathVariable String address){
		
		Building b = new Building();
		b.setBuildingId(id);
		b.setName(name);
		b.setAddress(address);

		b.setTimeOpenFrom(new Time(System.currentTimeMillis()));
		b.setTimeOpenTill(new Time(System.currentTimeMillis()));
		
		return buildingRepo.save(b);
	}
	
	@RequestMapping("/dates")
	public List<de.fhbingen.binhungrig.server.data.Date> dates() {
		return dateRepo.findAll();
	}
	
	@RequestMapping("/dates/{id}/dishes")
	public List<Dish> dishesOfDate(@PathVariable long id) {
		return dateRepo.findByDateId(id).getOfferedDishes();
	}
	
	@RequestMapping("/dishes")
	public List<Dish> dishes() {
		return dishRepo.findAll();
	}
	
	@RequestMapping("/dishes/{id}/dates")
	public List<de.fhbingen.binhungrig.server.data.Date> datesOfDish(@PathVariable long id) {
		return dishRepo.findBydishId(id).getOfferedAtDates();
	}
	
	@RequestMapping("/dishes/{id}/ratings")
	public List<Rating> ratingsOfDish(@PathVariable long id) {
		return dishRepo.findBydishId(id).getRatings();
	}
	
	@RequestMapping("/dishes/{id}/building")
	public Building buildingOfDish(@PathVariable long id) {
		return dishRepo.findBydishId(id).getBuilding();
	}
	
	@RequestMapping("/ratings")
	public List<Rating> ratings(){
		return ratingRepo.findAll();
	}
	
	@RequestMapping("/ratings/{id}/dish")
	public Dish dishOfRating(@PathVariable long id){
		return ratingRepo.findByratingId(id).getDish();
	}
	
	@Autowired
	private BuildingRepository buildingRepo;
	
	@Autowired
	private DeleteRepository deleteRepo;
	
	@Autowired
	private DishRepository dishRepo;
	
	@Autowired
	private SequenceRepository seqRepo;
	
	@Autowired
	private DateRepository dateRepo;
	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private OfferedAtRepository offeredAtRepo;
	
	@Autowired
	private PhotoRepository photoRepo;
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
}
