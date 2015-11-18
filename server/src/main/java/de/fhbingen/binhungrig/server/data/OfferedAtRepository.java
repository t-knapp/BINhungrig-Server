package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OfferedAtRepository extends CrudRepository<OfferedAt, Long> {

	@Query("SELECT oa FROM OfferedAt oa " + 
		   "JOIN oa.dish di " + 
           "JOIN di.building b " +
		   "WHERE b.buildingId IN (:buildings) AND oa.seq > :seq"
		  )
	List<OfferedAt> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
	@Query("SELECT oa FROM OfferedAt oa " + 
		   "JOIN oa.dish di " + 
           "JOIN di.building b " +
		   "WHERE b.buildingId = :buildingId AND oa.seq > :seq"
		  )
	List<OfferedAt> findByBuildingIdAndSeqGreaterThan(
			@Param("buildingId") long buildingId,
			@Param("seq") long seq);
	
	
	// Fetcher commands
	OfferedAt findByDateIdAndDishId(final long dateId, final long dishId);
}
