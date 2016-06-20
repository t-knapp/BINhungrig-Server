package de.fhbingen.binhungrig.server.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA Repository for Sequences
 * 
 * @author tknapp
 *
 */
public interface SequenceRepository extends CrudRepository<Sequence, String> {
	
	/**
	 * Returns the latest (current) Sequence Entity.
	 * 
	 * @return
	 */
	@Query("SELECT s FROM Sequence s WHERE s.seq_name = 'sync'")
	Sequence getLast();
}
