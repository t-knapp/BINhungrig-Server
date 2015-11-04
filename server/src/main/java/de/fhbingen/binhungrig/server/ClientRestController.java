package de.fhbingen.binhungrig.server;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.fhbingen.binhungrig.server.data.Building;
import de.fhbingen.binhungrig.server.data.BuildingRepository;
import de.fhbingen.binhungrig.server.data.Changes;
import de.fhbingen.binhungrig.server.data.DateRepository;
import de.fhbingen.binhungrig.server.data.DeleteRepository;
import de.fhbingen.binhungrig.server.data.Dish;
import de.fhbingen.binhungrig.server.data.DishRepository;
import de.fhbingen.binhungrig.server.data.SequenceRepository;

@RestController
public class ClientRestController {
	
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
		b.setTimeOpenFrom(new Date());
		b.setTimeOpenTill(new Date());
		
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
	
	@RequestMapping("/dishes/{id}/building")
	public Building buildingOfDish(@PathVariable long id) {
		return dishRepo.findBydishId(id).getBuilding();
	}
	
	/*
	 * Clients 
	 */
	
	@RequestMapping("/changes")
	public Changes changes(
			@RequestParam(value = "buildings") Long[] arBuildings,
			@RequestParam(value = "seq") long seq){
		Changes changes = new Changes();
		
		//All subscribed buildings
		changes.buildings = buildingRepo.findBySeqGreaterThan(seq);
		
		//All deletes
		changes.deletes = deleteRepo.findBySeqGreaterThan(seq);
		
		//All dishes
		Set<Long> setBuildings = new HashSet<Long>();
		Collections.addAll(setBuildings, arBuildings);
		changes.dishes = dishRepo.findBybuildingIdInAndSeqGreaterThan(setBuildings, seq);
		
		//All offeredAt
		//changes.dates = dateRepo.findByBuildingsAndSeq(arBuildings, seq);
		
		// Dates
		
		// Ratings
		
		// Photos ?
		
		//Last sequence
		changes.sequence = seqRepo.getLast();
		
		return changes;
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

}
