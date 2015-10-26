package de.fhbingen.binhungrig.server;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.fhbingen.binhungrig.server.parser.Dish;
import de.fhbingen.binhungrig.server.parser.Parser;
import de.fhbingen.binhungrig.server.parser.UrlBuilder;
import de.fhbingen.binhungrig.server.parser.UrlBuilder.Building;
import de.fhbingen.binhungrig.server.parser.UrlBuilder.TimeInterval;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args ) throws IOException
    {        

    	UrlBuilder urlB = new UrlBuilder();
    	//urlB.setBuilding(Building.ZENTRALMENSA);
    	urlB.setBuilding(Building.BINGENCAMPUS);
    	urlB.setTimeInterval(TimeInterval.CURRENTDAY);
    	
        Parser p = new Parser();
        List<Dish> list = p.parse(urlB);
        /*
        for (Dish dish : list) {
			System.out.println(dish.toString());
		}
		*/

    }
}
