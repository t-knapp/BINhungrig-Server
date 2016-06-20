package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * JPA Repository for Date 
 * 
 * @author tknapp
 *
 */
public interface DateRepository extends CrudRepository<Date, Long> {

	List<Date> findAll();
	
	Date findByDateId(final long id);
	
	/**
	 * Returns a distinct list of buildings whose buildingIds are in param buildings
	 * and whose sequence is higher than the provided param seq.
	 * 
	 * @param buildings
	 * @param seq
	 * @return
	 */
	@Query("SELECT DISTINCT da FROM Date da " + 
		       "JOIN da.offeredDishes di " + 
			   "JOIN di.building b " +
		       "WHERE b.buildingId IN (:buildings) AND da.seq > :seq"
			  )
	List<Date> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
	/**
	 * Returns list of date entities with higher (are newer) than the privided sequence
	 * 
	 * @param seq
	 * @return
	 */
	List<Date> findBySeqGreaterThan(final long seq);
	
	/**
	 * Returns list of date entities whose sequence is greater than the sequence provided
	 * and have a given date
	 * 
	 * @param seq
	 * @param date
	 * @return
	 */
	List<Date> findBySeqGreaterThanAndDateGreaterThanEqual(final long seq, final java.sql.Date date);
	
	/**
	 * Find Date entity by date.
	 * 
	 * Note: Used by Plan Fetcher Task.
	 * 
	 * @param date
	 * @return
	 */
	Date findByDate(final java.sql.Date date);
}
