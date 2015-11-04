package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long>{

	List<Rating> findAll();
	
	Rating findByratingId(long id);
}
