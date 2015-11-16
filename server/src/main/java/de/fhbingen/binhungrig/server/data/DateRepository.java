package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DateRepository extends CrudRepository<Date, Long> {

	List<Date> findAll();
	
	Date findByDateId(final long id);
		
	@Query("SELECT DISTINCT da FROM Date da " + 
		       "JOIN da.offeredDishes di " + 
			   "JOIN di.building b " +
		       "WHERE b.buildingId IN (:buildings) AND da.seq > :seq"
			  )
	List<Date> findByBuildingIdsAndSeq(
			@Param("buildings") List<Long> buildings,
			@Param("seq") long seq);
	
	List<Date> findBySeqGreaterThan(final long seq);
	
	List<Date> findBySeqGreaterThanAndDateGreaterThanEqual(final long seq, final java.sql.Date date);
}
