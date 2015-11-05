package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

	@Query("SELECT p FROM Photo p " + 
	       "JOIN p.dish di " + 
		   "JOIN di.building b " +
	       "WHERE b.buildingId IN (:buildings) AND p.seq > :seq"
		  )
	List<Photo> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
}
