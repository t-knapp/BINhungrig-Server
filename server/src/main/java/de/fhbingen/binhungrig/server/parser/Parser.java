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

import de.fhbingen.binhungrig.server.parser.Dish.DishType;

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
		
		Logger.getGlobal().log(Level.INFO, "parse: " + urlString);
		
		Document doc = null;
		try {
			doc = Jsoup.connect(urlString).get();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Fix HTML encoded special chars
		doc = Jsoup.parse(org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(doc.toString()));
		
		//RootDiv <div class="speiseplan"> ... </div>
		final Element rootDiv = doc.getElementsByClass("speiseplan").first();
			
		String date = null;
		String title = null;
		String[] ingredients = null;
		Double[] prices = null;
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
				title = removeIngredents(title, groupedIngredents);
				ingredients = mergeIngredents(groupedIngredents);
				
				//Price
				final Element divPrice = divMenuSpeise.getElementsByClass(DIVCLASSPRICE).first();
				prices = getPrices(divPrice.text());
				
				//Type
				final Element divVegan = divMenuSpeise.getElementsByClass(DIVCLASSVEGAN).first();
				DishType type = getType(divVegan);
				
				//TODO: Create DB Object and fire
				System.out.println(date);
				//System.out.println(oldTitle);
				System.out.println(title);
				System.out.println(Arrays.toString(ingredients));
				System.out.println(Arrays.toString(prices));
				System.out.println(type);
				System.out.println();
				break;
			case DIVCLASSSPECIAL:
				//Special dishes: Soups, Salads and Sausage
				final Elements specialMenues = currentElement.getElementsByClass(DIVCLASSSPDISH);
				//Foreach Special Dish
				for (Element element : specialMenues) {
					
					System.out.println(date);
					
					//Title and Ingredients
					Element divName = element.getElementsByClass(DIVCLASSSPNAME).first();
					String spTitle = divName.text().replace("oder ", "");
					List<String> spGroupedIngredients = getIngredients(spTitle);
					String[] spIngedients = mergeIngredents(spGroupedIngredients);
					spTitle = removeIngredents(spTitle, spGroupedIngredients);
					System.out.println(spTitle);
					System.out.println(Arrays.toString(spIngedients));
					
					//Price
					Element divSPPrice = element.getElementsByClass(DIVCLASSPRICE).first();
					Double[] spPrice = getPrices(divSPPrice.text());
					System.out.println(Arrays.toString(spPrice));
					
					//Type
					DishType spType = getType(divName);
					System.out.println(spType);
					System.out.println();
				}
				
				break;

			default:
				break;
			}
			
			
		}
			
		return null;
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
	private String[] mergeIngredents(final List<String> groupedIngredents){
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
		return ingredents.toArray(new String[0]);
	}
	
	/**
	 * Removes ingredients from dish title
	 * @param title
	 * @param groupedIngredents
	 * @return
	 */
	private String removeIngredents(final String title, final List<String> groupedIngredents){
		String result = title;
		for (String string : groupedIngredents) {
			result = result.replaceAll(string, "");
		}
		return result.replace("(", "").replace(")", "").replaceAll(" +", " ").trim();
	}
	
	private String getDate(final Element divDate){
		return divDate.text().split(" ")[1];
	}
	
	private Double[] getPrices(final String strPrice){
		final Pattern pattern = Pattern.compile("(\\d+.\\d{2})");
		final Matcher matcher = pattern.matcher(strPrice);
		final Double[] prices = new Double[2];
		for(int i = 0; i < 2 && matcher.find(); i++){
			prices[i] = Double.valueOf(matcher.group(i));
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
