package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * JPA Repository for Building
 * 
 * @author tknapp
 *
 */
public interface BuildingRepository extends CrudRepository<Building, Long>{

	List<Building> findAll();
	
	List<Building> findByName(final String name);
	
	Building findByBuildingId(final long id);
	
	/**
	 * Returns a list of new Buildings (sequence greater than provided sequence)
	 * 
	 * @param seq
	 * @return
	 */
	List<Building> findBySeqGreaterThan(final long seq);
}
