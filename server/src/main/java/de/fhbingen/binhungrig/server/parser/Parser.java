package de.fhbingen.binhungrig.server.parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import de.fhbingen.binhungrig.server.parser.Dish.DishType;
import de.fhbingen.binhungrig.server.parser.UrlBuilder.Building;

public class Parser extends AParser {

	private static final String DIVCLASSDATE    = "speiseplan_date";
	private static final String DIVCLASSSPECIAL = "specialbox";
	private static final String DIVCLASSBOX1    = "counter count1 ";
	private static final String DIVCLASSBOX2    = "counter count2 ";
	private static final String DIVCLASSBOX4    = "counter count4 ";
	
	private static final String DIVCLASSDISH    = "menuspeise";
	private static final String DIVCLASSNAME    = "speiseplanname";
	private static final String DIVCLASSPRICE   = "price";
	private static final String DIVCLASSVEGAN   = "vegan_icon";
	private static final String DIVCLASSSPDISH  = "special_menu";
	private static final String DIVCLASSSPNAME  = "spmenuname";
	
	@Override
	public List<Dish> parse(UrlBuilder builder) {
		final String urlString = builder.getUrl();
		final List<Dish> resultDishes = new LinkedList<Dish>();
		final long buildingId = builder.getBuildingId();
		
		Logger.getGlobal().log(Level.INFO, "parse: " + urlString);
		
		Document doc = null;
		try {
			doc = Jsoup.connect(urlString).timeout(10000).get();
			
		} catch (IOException e) {			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new LinkedList<Dish>();
		}
		
		//Fix HTML encoded special chars
		final String unescapedString = org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(doc.toString());
		doc = Jsoup.parse(unescapedString);
		
		//RootDiv <div class="speiseplan"> ... </div>
		final Element rootDiv = doc.getElementsByClass("speiseplan").first();
			
		String date = null;
		String title = null;
		String ingredients = null;
		Float[] prices = null;
		for(int i = 0; i < rootDiv.children().size(); i++){
			Element currentElement = rootDiv.child(i);
			switch (currentElement.className()) {
			case DIVCLASSDATE:
				//Current div holds the date for the next divs
				date = getDate(currentElement);
				break;
			case DIVCLASSBOX1:
			case DIVCLASSBOX2:
			//case DIVCLASSBOX4:
				//Div with dishes in "menuspeise" divs
				final Element divMenuSpeise = currentElement.getElementsByClass(DIVCLASSDISH).first();
				
				//Title and ingredients
				final Element divTitle = divMenuSpeise.getElementsByClass(DIVCLASSNAME).first();
				title = divTitle.text();
				//String oldTitle = title;
				List<String> groupedIngredents = getIngredients(title);
				title = removeIngredents(title);
				ingredients = mergeIngredents(groupedIngredents);
				
				//Price
				final Element divPrice = divMenuSpeise.getElementsByClass(DIVCLASSPRICE).first();
				prices = getPrices(divPrice.text());
				
				//Type
				final Element divVegan = divMenuSpeise.getElementsByClass(DIVCLASSVEGAN).first();
				DishType type = getType(divVegan);
				
				resultDishes.add(
						new Dish(
								title, 
								date, 
								ingredients, 
								prices[0],
								prices[1],
								buildingId, 
								type));
				break;
			case DIVCLASSSPECIAL:
				//Special dishes: Soups, Salads and Sausage
				final Elements specialMenues = currentElement.getElementsByClass(DIVCLASSSPDISH);
				//Foreach Special Dish
				for (Element element : specialMenues) {
					
					//Title and Ingredients
					Element divName = element.getElementsByClass(DIVCLASSSPNAME).first();
					String spTitle = divName.text().trim();
					
					//Fix: Remove "oder" if first word
					if(spTitle.startsWith("oder")) {
						spTitle = spTitle.replace("oder", "").trim();
					}
					
					//Fix: Replace "oder mit" with "mit"
					spTitle = spTitle.replace("oder mit", "mit");
					
					//Fix: "mit Kohl" -> Kohl
					if(spTitle.startsWith("mit ")){
						spTitle = spTitle.replace("mit ", "");
					}
					
					List<String> spGroupedIngredients = getIngredients(spTitle);
					String spIngedients = mergeIngredents(spGroupedIngredients);
					spTitle = removeIngredents(spTitle);
					
					//Price
					Element divSPPrice = element.getElementsByClass(DIVCLASSPRICE).first();
					Float[] spPrice = getPrices(divSPPrice.text());
					
					//Type
					DishType spType = getType(divName);
									
					resultDishes.add(new Dish(
							spTitle, date, spIngedients, spPrice[0], spPrice[1], buildingId, spType
							)
					);
				}
				break;
			default:
				break;
			}	
		}
		return resultDishes;
	}

	/**
	 * Returns a list of strings containing _all_ ingredients of a dish.
	 * 
	 * [(1,M,Gl), (0,S)]
	 * 
	 * @param title
	 * @return
	 */
	private List<String> getIngredients(final String title){
		final Pattern pattern = Pattern.compile("(\\([^\\(]*\\))");
		
		final List<String> founds = new LinkedList<String>();
		
		final Matcher matcher = pattern.matcher(title);
		while(matcher.find()){
			founds.add(matcher.group());
		}
			
		return founds;
	}
	
	/**
	 * Merges a list of all ingredients to an array of strings
	 * @param groupedIngredents
	 * @return
	 */
	private String mergeIngredents(final List<String> groupedIngredents){
		final Set<String> ingredents = new HashSet<String>();
		
		String tmp;
		String[] parts;
		for (String string : groupedIngredents) {
			// (a,bc,43)
			tmp = string.substring(1, string.length()-1);
			parts = tmp.split(",");
			for (String part : parts) {
				ingredents.add(part);
			}
		}
		return StringUtils.collectionToCommaDelimitedString(ingredents);
	}
	
	/**
	 * Removes ingredients from dish title
	 * @param title
	 * @return
	 */
	private String removeIngredents(final String title){
		final Pattern patternIngredients = Pattern.compile("(\\([^\\(]*\\))");
		final Matcher matcherIngredients = patternIngredients.matcher(title);
		final String removedIngredients = matcherIngredients.replaceAll("").trim();
		
		final Pattern patternWhitespaces = Pattern.compile("\\s+");
		final Matcher matcherWhitespaces = patternWhitespaces.matcher(removedIngredients);
		return matcherWhitespaces.replaceAll(" ");
	}
	
	private String getDate(final Element divDate){
		//input text: Montag 16-11-2015
		final String[] dateParts = divDate.text().split(" ")[1].split("-");
		return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
	}
	
	private Float[] getPrices(final String strPrice){
		final Pattern pattern = Pattern.compile("(\\d+.\\d{2})");
		final Matcher matcher = pattern.matcher(strPrice);
		final Float[] prices = new Float[2];
		for(int i = 0; i < 2 && matcher.find(); i++){
			prices[i] = Float.valueOf(matcher.group(i));
		}
		return prices;
	}
	
	private DishType getType(final Element divElement){
		if(divElement.toString().contains("Veggi")){
			return DishType.VEGETARIAN;
		}
		if(divElement.toString().contains("Vegan")){
			return DishType.VEGAN;
		}
		return DishType.NORMAL;
	}
}
