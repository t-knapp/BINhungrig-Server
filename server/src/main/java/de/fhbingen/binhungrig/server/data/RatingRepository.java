package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * JPA Repository for Ratings
 * 
 * @author tknapp
 *
 */
public interface RatingRepository extends CrudRepository<Rating, Long>{

	List<Rating> findAll();
	
	Rating findByratingId(long ratingId);
	
	/**
	 * Returns a list of Rating entities for a list of buildingIds and a sequence
	 * greater than a provided sequence
	 * 
	 * @see http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
	 * @param buildings
	 * @param seq
	 * @return
	 */
	@Query("SELECT ra FROM Rating ra " + 
	       "JOIN ra.dish di " + 
		   "JOIN di.building b " +
	       "WHERE b.buildingId IN (:buildings) AND ra.seq > :seq"
		  )
	List<Rating> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
	/**
	 * Returns a list of Rating entities for a building and a sequence greater
	 * than the provided sequence.
	 * 
	 * @see http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
	 * @param buildingId
	 * @param seq
	 * @return
	 */
	@Query("SELECT ra FROM Rating ra " + 
		   "JOIN ra.dish di " + 
		   "JOIN di.building b " +
		   "WHERE b.buildingId = :buildingId AND ra.seq > :seq"
	      )
	List<Rating> findByBuildingIdAndSeqGreaterThan(
			@Param("buildingId") long buildingId,
			@Param("seq") long seq);
}
