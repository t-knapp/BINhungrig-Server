package de.fhbingen.binhungrig.server.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * JPA Repository for Ingredients
 * 
 * @author tknapp
 *
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

	List<Ingredient> findByKey(final String key);
	
	/**
	 * Returns a list of new ingredients (ingredient.seq >= seq)
	 * @param seq
	 * @return
	 */
	List<Ingredient> findBySeqGreaterThan(final long seq);
}
