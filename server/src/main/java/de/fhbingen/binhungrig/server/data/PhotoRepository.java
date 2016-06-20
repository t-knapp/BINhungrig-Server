package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * JPA Repository for Photo
 * 
 * @author tknapp
 *
 */
public interface PhotoRepository extends CrudRepository<Photo, Long> {

	Photo findByPhotoId(final long photoId);	
	
	/**
	 * Returns list of Photo Entities for all buildings provided by List<Long> buildings 
	 * and sequence greater than param seq
	 * 
	 * @see http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
	 * @param buildings
	 * @param seq
	 * @return
	 */
	@Query("SELECT p FROM Photo p " + 
	       "JOIN p.dish di " + 
		   "JOIN di.building b " +
	       "WHERE b.buildingId IN (:buildings) AND p.seq > :seq"
		  )
	List<Photo> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
	/**
	 * Returns a list of photos for a given buildingId and a sequence 
	 * greater than provided sequence.
	 * 
	 * @see http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
	 * @param buildingId
	 * @param seq
	 * @return
	 */
	@Query("SELECT p FROM Photo p " + 
	       "JOIN p.dish di " + 
		   "JOIN di.building b " +
	       "WHERE b.buildingId = :buildingId AND p.seq > :seq"
		  )
	List<Photo> findByBuildingIdAndSeqGreaterThan(
			@Param("buildingId") long buildingId,
			@Param("seq") long seq);
}
