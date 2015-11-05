package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends CrudRepository<Rating, Long>{

	List<Rating> findAll();
	
	Rating findByratingId(long id);
	
	/*
	SELECT ra.* FROM Ratings ra
	JOIN Dishes di ON ra.fk_dishId = di.dishId
	JOIN Buildings b on di.fk_buildingId = b.buildingId
	WHERE b.buildingId IN (8)
	AND ra.seq > 50;
	*/
	@Query("SELECT ra FROM Rating ra " + 
	       "JOIN ra.dish di " + 
		   "JOIN di.building b " +
	       "WHERE b.buildingId IN (:buildings) AND ra.seq > :seq"
		  )
	List<Rating> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
}
