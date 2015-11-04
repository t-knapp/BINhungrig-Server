package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeleteRepository extends CrudRepository<Delete, Long> {

	List<Delete> findBySeqGreaterThan(long seq);
	
}
