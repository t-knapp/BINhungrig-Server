package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

	List<Ingredient> findByKey(final String key);
	
	List<Ingredient> findBySeqGreaterThan(final long seq);
}
