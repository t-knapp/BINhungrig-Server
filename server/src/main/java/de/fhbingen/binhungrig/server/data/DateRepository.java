package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DateRepository extends CrudRepository<Date, Long> {

	List<Date> findAll();
	
	Date findByDateId(final long id);
	
	/*
	@Query(value = "SELECT DISTINCT da.* FROM Dates da " +
                   "JOIN offeredAt o ON da.dateId = o.fk_dateId " +
                   "JOIN Dishes di on o.fk_dishId = di.dishId " +
                   "JOIN Buildings b on di.fk_buildingId = b.buildingId " +
                   "WHERE b.buildingId IN ( :buildings ) " +
                   "AND di.seq > :seq",
            nativeQuery = true
    )
    
	@Query("SELECT INSTINCT d from Dates d " +
           "JOIN offeredAt o ON d.dateId = o.fk_dateId " +
		   "JOIN Dishes di on o.fk_dishId = di.dishId " +
           "JOIN Buildings b on di.fk_buildingId = b.buildingId " +
		   "WHERE b.buildingId IN ( :buildings ) " +
           "AND di.seq > :seq ")
	List<Date> findByBuildingsAndSeq(
			@Param("buildings") Long[] buildings,
			@Param("seq") long seq );
	*/
}
