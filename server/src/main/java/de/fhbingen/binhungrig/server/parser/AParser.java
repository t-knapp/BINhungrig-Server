package de.fhbingen.binhungrig.server.parser;

import java.util.List;

/**
 * Abstract superclass of all parsers
 * 
 * @author tknapp
 *
 */
public abstract class AParser {
	
	public abstract List<Dish> parse(final UrlBuilder builder);
	
}
