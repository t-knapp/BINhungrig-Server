package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * JPA Repository for Delete
 * 
 * @author tknapp
 *
 */
public interface DeleteRepository extends CrudRepository<Delete, Long> {

	/**
	 * Returns a list of Delete entities with a sequence greater than 
	 * the provided param seq
	 * 
	 * @param seq
	 * @return
	 */
	List<Delete> findBySeqGreaterThan(long seq);
	
}
