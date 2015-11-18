package de.fhbingen.binhungrig.server.data;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Long>{

	List<Dish> findAll();
	
	List<Dish> findBybuildingIdInAndSeqGreaterThan(Collection<Long> buildings, long seq);
	
	List<Dish> findByBuildingIdAndSeqGreaterThan(final long buildingId, final long buildingSeq);
	
	Dish findBydishId(long id);
	
	// Fetcher commands
	
	Dish findByTitleAndBuildingId(final String title, final long buildingId);
	
}
