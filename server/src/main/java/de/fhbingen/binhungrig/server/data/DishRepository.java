package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Long>{

	List<Dish> findAll();
	
	Dish findBydishId(long id);
		
	/**
	 * Returns a list of all dishes of a building and a sequence greater than its buildings seq
	 * 
	 * @param buildingId
	 * @param buildingSeq
	 * @return
	 */
	List<Dish> findByBuildingIdAndSeqGreaterThan(final long buildingId, final long buildingSeq);
				
	/**
	 * Returns a dish found by its title and buildingId (where dish is offered at)
	 * 
	 * Note: Used by Plan Fetcher Task.
	 * 
	 * @param title
	 * @param buildingId
	 * @return
	 */
	Dish findByTitleAndBuildingId(final String title, final long buildingId);
	
}
