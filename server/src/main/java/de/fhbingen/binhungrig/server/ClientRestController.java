package de.fhbingen.binhungrig.server;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.fhbingen.binhungrig.server.data.Building;
import de.fhbingen.binhungrig.server.data.BuildingRepository;
import de.fhbingen.binhungrig.server.data.Changes;
import de.fhbingen.binhungrig.server.data.ContainerBuildings;
import de.fhbingen.binhungrig.server.data.DateRepository;
import de.fhbingen.binhungrig.server.data.DeleteRepository;
import de.fhbingen.binhungrig.server.data.DishRepository;
import de.fhbingen.binhungrig.server.data.OfferedAtRepository;
import de.fhbingen.binhungrig.server.data.PhotoRepository;
import de.fhbingen.binhungrig.server.data.RatingRepository;
import de.fhbingen.binhungrig.server.data.SequenceRepository;

@RestController
public class ClientRestController {
		
	/*
	 * Clients 
	 */
	
	@RequestMapping("/buildings")
	public ContainerBuildings buildings() {
		return new ContainerBuildings(buildingRepo.findAll());
	}
	
	@RequestMapping("/changes")
	public Changes changes(
			@RequestParam(value = "buildings", defaultValue = "0") Long[] arBuildings,
			@RequestParam(value = "seq") long seq){
		Changes changes = new Changes();
		
		//Last sequence
		changes.sequence = seqRepo.getLast();
		
		changes.needToUpdate = changes.sequence.getLastSequence() > seq;
		
		if(changes.needToUpdate){
		
			// All new (or updated) buildings
			changes.buildings = buildingRepo.findBySeqGreaterThan(seq);
			
			// All deletes
			changes.deletes = deleteRepo.findBySeqGreaterThan(seq);
			
			// All dishes for given buildings and newer seq
			Set<Long> setBuildings = new HashSet<Long>();
			Collections.addAll(setBuildings, arBuildings);
			changes.dishes = dishRepo.findBybuildingIdInAndSeqGreaterThan(setBuildings, seq);
			
			// All new ratings for dishes, served in selected buildings
			changes.ratings = ratingRepo.findByBuildingIdsAndSeq(Arrays.asList(arBuildings), seq);
			
			// All offeredAt
			changes.offeredAt = offeredAtRepo.findByBuildingIdsAndSeq(Arrays.asList(arBuildings), seq);
			
			// All new Dates for dishes, served in selected buildings
			// TODO: Fix Bug:
			// If existing dish is added to existing date, a client has not downloaded bc. this
			// date is not already used by dishes of his building, this date is not downloaded 
			// if dish and date getting connected with new seq in offeredAt
			// -> Date does not exist on device -> Dish is not shown!
			//changes.dates = dateRepo.findByBuildingIdsAndSeq(Arrays.asList(arBuildings), seq);
			// Fix
			changes.dates = dateRepo.findBySeqGreaterThanAndDateGreaterThanEqual(
					seq,
					new Date(System.currentTimeMillis()-86400000) // Yesterday
			);
			
			// All new Photos for dishes, served in selected buildings
	//		changes.photos = photoRepo.findByBuildingIdsAndSeq(Arrays.asList(arBuildings), seq);
			
		}
		
		return changes;
	}
	
	//putRating
	
	//putPhoto
	
	//putComplain
	
	
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

}
