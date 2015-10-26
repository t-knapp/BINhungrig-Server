package de.fhbingen.binhungrig.server.parser;

import java.util.List;

public abstract class AParser {
	
	public abstract List<Dish> parse(final UrlBuilder builder);
	
}
