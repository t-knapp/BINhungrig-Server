package de.fhbingen.binhungrig.server.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SequenceRepository extends CrudRepository<Sequence, String> {
	
	@Query("SELECT s FROM Sequence s WHERE s.seq_name = 'sync'")
	Sequence getLast();
}
