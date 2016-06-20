package de.fhbingen.binhungrig.server.task;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.fhbingen.binhungrig.server.Utils;
import de.fhbingen.binhungrig.server.data.BuildingRepository;
import de.fhbingen.binhungrig.server.data.DateRepository;
import de.fhbingen.binhungrig.server.data.DishRepository;
import de.fhbingen.binhungrig.server.data.OfferedAt;
import de.fhbingen.binhungrig.server.data.OfferedAtRepository;
import de.fhbingen.binhungrig.server.parser.Dish;
import de.fhbingen.binhungrig.server.parser.Parser;
import de.fhbingen.binhungrig.server.parser.UrlBuilder;
import de.fhbingen.binhungrig.server.parser.UrlBuilder.TimeInterval;

/**
 * Menu plan fetcher task
 * 
 * @author tknapp
 *
 */
@Component
public class FetchMenuPlanTask {

	public final static String TAG = FetchMenuPlanTask.class.getSimpleName();
	
	private void log(final String msg){
		Utils.log("%s : %s", TAG, msg);
	}
	
	@Scheduled(fixedDelay = 3600000)
	//@Scheduled(cron = "0 0,6,12,18 * * * *")
	public void fetch(){	
		List<de.fhbingen.binhungrig.server.data.Building> dbBuildings = buildingRepo.findAll();
		for(final de.fhbingen.binhungrig.server.data.Building tmpBuilding : dbBuildings){
			fetchWeekForBuilding(
					TimeInterval.NEXTWEEK, 
					tmpBuilding.getBuildingId()
			);
		}
	}
	
	/**
	 * Fetches menu plan for given building and given time interval (day|week|nextweek)
	 * 
	 * @param timeInterval
	 * @param buildingId
	 */
	private void fetchWeekForBuilding(final TimeInterval timeInterval, final long buildingId){
		final Parser parser = new Parser();
		final UrlBuilder urlBuilder = new UrlBuilder();
		
		urlBuilder.setBuilding(buildingId);
		urlBuilder.setTimeInterval(timeInterval);
		
		final List<Dish> dishes = parser.parse(urlBuilder);
		
		de.fhbingen.binhungrig.server.data.Dish dbDish;
		for(final Dish tmpDish : dishes){
			System.out.println(tmpDish.toString());
			
			dbDish = dishRepo.findByTitleAndBuildingId(
					tmpDish.getTitle(), 
					tmpDish.getBuildingId()
			);
			if(dbDish == null){
				// Create new dish and save to DB
				//log("Create new db.dish");
				createDish(tmpDish);
			} else {
				// Check if Update is needed, update to DB
				//log("Update dish");
				updateDish(dbDish, tmpDish);
			}
		}
	}
	
	/**
	 * Updates a dbDish with data provided by Dish object
	 * 
	 * @param dbDish
	 * @param newDish
	 */
	private void updateDish(de.fhbingen.binhungrig.server.data.Dish dbDish, final Dish newDish){
		// Check Dish contents (price, ingredients, etc.) and update if needed
		if(dbDish.needToUpdate(newDish)){
			dbDish.update(newDish);
			dbDish = dishRepo.save(dbDish);
		}
		final long dbDishId = dbDish.getDishId();
		log("updateDish: dbDish.dbDishId: " + dbDishId);
		
		// Check if Date exits, else create
		final long dbDateId = checkCreateDateId(newDish.getDate());
		log("updateDish: dbDate.dateId: " + dbDateId);
		
		// Check if offeredAt exists, else create
		OfferedAt oA = offeredAtRepo.findByDateIdAndDishId(dbDateId, dbDishId);
		if(oA == null){
			oA = new OfferedAt(dbDish.getDishId(), dbDateId);
			offeredAtRepo.save(oA);
		}
	}
	
	/**
	 * Creates a new Dish Entity and OfferedAt Entity
	 * 
	 * @param newDish
	 */
	private void createDish(final Dish newDish){
		// Check if Date exists in Dates, extract DateId
		final long dbDateId = checkCreateDateId(newDish.getDate());
		log("createDish: dbDate.dateId: " + dbDateId);
		
		// Insert new Dish
		de.fhbingen.binhungrig.server.data.Dish dbDish = new de.fhbingen.binhungrig.server.data.Dish(
				newDish.getTitle(), 
				newDish.getIngredients(),
				newDish.getPriceStd(),
				newDish.getPriceStd(),
				newDish.getType(),
				newDish.getBuildingId());
		dbDish = dishRepo.save(dbDish);
		log("createDish: dbDish.dishId: " + dbDish.getDishId());
		
		// Insert row in offeredAt
		final OfferedAt oA = new OfferedAt(dbDish.getDishId(), dbDateId);
		offeredAtRepo.save(oA);
	}
	
	/**
	 * Checks if Date Entity exists or create it.
	 * 
	 * @param strDate
	 * @return
	 */
	private long checkCreateDateId(final String strDate){
		// Check if Date exists in Dates, extract DateId
		de.fhbingen.binhungrig.server.data.Date dbDate = dateRepo.findByDate(Date.valueOf(strDate));
		if(dbDate == null){
			dbDate = new de.fhbingen.binhungrig.server.data.Date(strDate);
			dbDate = dateRepo.save(dbDate);
		}
		return dbDate.getDateId();
	}	
	
	@Autowired
	private DishRepository dishRepo;
	
	@Autowired 
	private DateRepository dateRepo;
	
	@Autowired
	private OfferedAtRepository offeredAtRepo;
	
	@Autowired
	private BuildingRepository buildingRepo;

}
