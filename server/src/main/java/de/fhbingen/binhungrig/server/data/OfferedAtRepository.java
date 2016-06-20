package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * JPA Repository for OfferedAt entities
 * 
 * @author tknapp
 *
 */
public interface OfferedAtRepository extends CrudRepository<OfferedAt, Long> {

	/**
	 * Returns a list of OfferedAt entities for given list of buildingIds
	 * and a sequence greaterThan a provided sequence
	 * 
	 * @see http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
	 * @param buildings
	 * @param seq
	 * @return
	 */
	@Query("SELECT oa FROM OfferedAt oa " + 
		   "JOIN oa.dish di " + 
           "JOIN di.building b " +
		   "WHERE b.buildingId IN (:buildings) AND oa.seq > :seq"
		  )
	List<OfferedAt> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
	/**
	 * Returns a list of OfferedAt entities with given buildingId and 
	 * a sequence greater than a provided seq
	 * 
	 * @see http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
	 * @param buildingId
	 * @param seq
	 * @return
	 */
	@Query("SELECT oa FROM OfferedAt oa " + 
		   "JOIN oa.dish di " + 
           "JOIN di.building b " +
		   "WHERE b.buildingId = :buildingId AND oa.seq > :seq"
		  )
	List<OfferedAt> findByBuildingIdAndSeqGreaterThan(
			@Param("buildingId") long buildingId,
			@Param("seq") long seq);
	
	/**
	 * Returns OfferedAt entity identified by dateId and dishId
	 * 
	 * Note: Used by Plan Fetcher Task.
	 * 
	 * @param dateId
	 * @param dishId
	 * @return
	 */
	OfferedAt findByDateIdAndDishId(final long dateId, final long dishId);
}
