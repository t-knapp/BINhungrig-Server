package de.fhbingen.binhungrig.server;

import java.sql.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			@RequestParam Map<String, String> allParams
			){
		// Extract client sequence (global)
		final long seq = Long.parseLong(allParams.get("seq"));
		allParams.remove("seq");
		
		System.out.println(String.format("Global Sequence is %d", seq));
		
		Changes result = new Changes();
		
		// Global stuff: Sequence, Dates, Buildings, Deletes
		// Clients need to pull changes on all buildings to build list
		// Clients need to pull dates independent from building id
		// Sequence is updated at the beginning to ensure no data is skipped
		// TODO: Alternative: User Database Transactions
		result.sequence = seqRepo.getLast();
		
		result.dates = dateRepo.findBySeqGreaterThanAndDateGreaterThanEqual(
				seq, new Date(System.currentTimeMillis()-86400000));
		
		result.buildings = buildingRepo.findBySeqGreaterThan(seq);
		
		// Skip deletes if new device 
		if(seq != 0){
			result.deletes = deleteRepo.findBySeqGreaterThan(seq);
		}
				
		long buildingId;
		long buildingSeq;
		for(Map.Entry<String, String> entry : allParams.entrySet()){
			System.out.println(String.format("buildingId: %s with buildingSeq: %s", entry.getKey(), entry.getValue()));
			buildingId  = Long.parseLong(entry.getKey());
			buildingSeq = Long.parseLong(entry.getValue());
			
			// Dishes
			result.dishes.addAll(dishRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
			
			// Ratings
			result.ratings.addAll(ratingRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
			
			// Photos
			//result.photos.addAll(photoRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
			
			// OfferedAt
			result.offeredAt.addAll(offeredAtRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
		}
		
		result.needToUpdate = (
				   !result.dishes.isEmpty() 
				|| !result.ratings.isEmpty() 
				|| !result.offeredAt.isEmpty()
				|| !result.buildings.isEmpty()
				|| !result.dates.isEmpty()
				|| !result.deletes.isEmpty()
				|| result.sequence.getLastSequence() > seq );
				
		return result;
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
