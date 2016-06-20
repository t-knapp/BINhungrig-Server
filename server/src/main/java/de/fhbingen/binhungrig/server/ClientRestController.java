package de.fhbingen.binhungrig.server;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.fhbingen.binhungrig.server.data.Building;
import de.fhbingen.binhungrig.server.data.BuildingRepository;
import de.fhbingen.binhungrig.server.data.Changes;
import de.fhbingen.binhungrig.server.data.DateRepository;
import de.fhbingen.binhungrig.server.data.DeleteRepository;
import de.fhbingen.binhungrig.server.data.Dish;
import de.fhbingen.binhungrig.server.data.DishRepository;
import de.fhbingen.binhungrig.server.data.IngredientRepository;
import de.fhbingen.binhungrig.server.data.OfferedAtRepository;
import de.fhbingen.binhungrig.server.data.Photo;
import de.fhbingen.binhungrig.server.data.PhotoRepository;
import de.fhbingen.binhungrig.server.data.Rating;
import de.fhbingen.binhungrig.server.data.RatingRepository;
import de.fhbingen.binhungrig.server.data.SequenceRepository;

/**
 * REST Controller for fetching plan and handling ratings/photos/complains
 * 
 * @author tknapp
 *
 */
@RestController
public class ClientRestController {
		
	/**
	 * Determines and returns sequence-based changes for all provided buildings.
	 * 
	 * changes?seq=1703    Clients global sequence is 1703
	 *        &2=80        Clients sequence of building 2 is 80
	 *        &4=0         Clients sequence of building 4 is 0
	 *        &6=1703      Clients sequence of building 6 is 1703
	 *   
	 * @param allParams
	 * @return Changes Container Object (Convertet to JSON via Jackson)
	 */
	@RequestMapping("/changes")
	public Changes changes(
			@RequestParam Map<String, String> allParams
			){
		// Extract client sequence (global)
		final long seq = Long.parseLong(allParams.get("seq"));
		allParams.remove("seq");
		
		System.out.println(String.format("Global Sequence is %d", seq));
		
		Changes result = new Changes();
		
		// Global stuff: Sequence, Dates, Buildings, Deletes and Ingredients are delivered always
		// Clients need to pull changes on all buildings to build list
		// Clients need to pull dates independent from building id
		// Clients need to pull ingredients
		// Sequence is updated at the beginning to ensure no data is skipped
		// TODO: Alternative: User Database Transactions
		result.sequence = seqRepo.getLast();
		
		result.dates = dateRepo.findBySeqGreaterThanAndDateGreaterThanEqual(
				seq, new Date(System.currentTimeMillis()-86400000));
		
		result.buildings = buildingRepo.findBySeqGreaterThan(seq);
		
		result.ingredients = ingredientRepo.findBySeqGreaterThan(seq);
		
		// Skip deletes if new device 
		if(seq != 0){
			result.deletes = deleteRepo.findBySeqGreaterThan(seq);
		}
				
		long buildingId;
		long buildingSeq;
		for(Map.Entry<String, String> entry : allParams.entrySet()){
			System.out.println(String.format("buildingId: %s with buildingSeq: %s", entry.getKey(), entry.getValue()));
			buildingId  = Long.parseLong(entry.getKey());
			buildingSeq = Long.parseLong(entry.getValue());
			
			// Dishes
			result.dishes.addAll(dishRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
			
			// Ratings
			result.ratings.addAll(ratingRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
			
			// Photos
			result.photos.addAll(photoRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
			
			// OfferedAt
			result.offeredAt.addAll(offeredAtRepo.findByBuildingIdAndSeqGreaterThan(buildingId, buildingSeq));
		}
		
		result.needToUpdate = (
				   !result.dishes.isEmpty() 
				|| !result.ratings.isEmpty() 
				|| !result.offeredAt.isEmpty()
				|| !result.buildings.isEmpty()
				|| !result.dates.isEmpty()
				|| !result.deletes.isEmpty()
				|| !result.photos.isEmpty()
				|| result.sequence.getLastSequence() > seq );
				
		return result;
	}
	
	
	//TODO: Check if available, means not "enough" complains
	/**
	 * Returns a full sized photo identified by photoId
	 * 
	 * @param photoId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/photos/{photoId}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getPhoto(
			@PathVariable("photoId") final long photoId) throws IOException {
		
		final Photo dbPhoto = photoRepo.findByPhotoId(photoId);
		if(dbPhoto == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ByteArrayOutputStream bao = null;
		try {
			bao = getBAOForFile(config.getPhotoPath() + photoId);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.contentLength(bao.size())
				.body(bao.toByteArray());
	}
	
	//TODO: Check if available, means not "enough" complains
	/**
	 * Returns a thumb sized photo identified by photoId
	 * 
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value="/photos/{photoId}/thumb", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getPhotoThumb(
			@PathVariable("photoId") final long photoId) {
		
		final Photo dbPhoto = photoRepo.findByPhotoId(photoId);
		if(dbPhoto == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.contentLength(dbPhoto.getThumb().length)
				.body(dbPhoto.getThumb());
	}
		
	// Post Rating
	/**
	 * Saves a Rating object to DB
	 * 
	 * @param rating
	 * @return
	 */
	@RequestMapping(value = "/ratings", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Rating> postRating(@RequestBody final Rating rating){
		//Check wheather building exists and is open now
    	final Dish dbDish = dishRepo.findBydishId(rating.getDishId());
    	if(dbDish == null){
    		//return "No dish found";
    		System.out.println("Trying to insert rating for not existing dish");
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	final Building dbBuilding = buildingRepo.findByBuildingId(dbDish.getBuildingId());
    	if(dbBuilding == null){
    		//return "No building found";
    		System.out.println("Trying to insert rating for dish with no building.");
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	if(!dbBuilding.isOpenNow()){
    		//return "Building closed";
    		System.out.println("Trying to uinsert rating where building is closed.");
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
				
		final Rating newRating = ratingRepo.save(rating);
		
		//TODO: Fix seq is 0 in response (bc. it is generated by db trigger)
		return new ResponseEntity<Rating>(
				ratingRepo.findByratingId(newRating.getRatingId()),
				HttpStatus.CREATED
		);
	}
	
	/**
	 * Returns a Rating object identified by ratingId
	 * 
	 * @param rating
	 * @return
	 */
	@RequestMapping(value = "/ratings/{ratingId}", method = RequestMethod.GET)
	@ResponseBody
	public Rating getRating(@PathVariable("ratingId") final long rating){
		return ratingRepo.findByratingId(rating);
	}
	
	/**
	 * Saves a Photo object for a dish to database
	 * 
	 * @param file
	 * @param dishId
	 * @return
	 */
	// Post Photo
	@RequestMapping(value="/photos", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Photo> postPhoto(
    		@RequestParam("file") MultipartFile file,
    		@RequestParam("dishId") long dishId
	){
        if (!file.isEmpty()) {
        	        	
        	//Check wheather building exists and is open now
        	final Dish dbDish = dishRepo.findBydishId(dishId);
        	if(dbDish == null){
        		//return "No dish found";
        		System.out.println("Trying to upload photo for not existing dish");
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	final Building dbBuilding = buildingRepo.findByBuildingId(dbDish.getBuildingId());
        	if(dbBuilding == null){
        		//return "No building found";
        		System.out.println("Trying to upload photo for dish with no building.");
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}       	
        	
        	if(!dbBuilding.isOpenNow()){
        		//return "Building closed";
        		System.out.println("Trying to upload photo where building is closed.");
        		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        	}
        	
        	//Generate new Photo Entity
        	final Photo newPhoto = new Photo();
        	newPhoto.setDishId(dishId);
        	newPhoto.setDateUpload(new Date(System.currentTimeMillis()));
        	
        	final Photo dbPhoto = photoRepo.save(newPhoto);
        	final String fileName = config.getPhotoPath() + dbPhoto.getPhotoId();
        	
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                		new BufferedOutputStream(new FileOutputStream(fileName));
                stream.write(bytes);
                stream.close();
                
                //Create Thumb 128x128 px
                final InputStream fullSizeInputStream = new ByteArrayInputStream(bytes);
            	final BufferedImage thumb = Scalr.resize(ImageIO.read(fullSizeInputStream), 128, Scalr.OP_ANTIALIAS);
            	
            	// Save bytes to file
            	//ImageIO.write(thumb, "jpg", new File(config.getPhotoThumbPath() + dbPhoto.getPhotoId()));
                
            	// Save bytes to db
            	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	ImageIO.write(thumb, "jpg", baos);
            	newPhoto.setThumb(baos.toByteArray());
            	photoRepo.save(newPhoto);
            	
                System.out.println("You successfully uploaded " + fileName + "!");
            	//return "You successfully uploaded " + fileName + "!";
                final Photo result = photoRepo.findByPhotoId(dbPhoto.getPhotoId());
                return new ResponseEntity<Photo>(result, HttpStatus.CREATED);
                
            } catch (Exception e) {
            	//e.printStackTrace();
                //return "You failed to upload " + fileName + " => " + e.getMessage();
                System.out.println("You failed to upload " + fileName + " => " + e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            //return "You failed to upload because the file was empty.";
        	System.out.println("You failed to upload because the file was empty.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
	//putComplain
	/**
	 * Adds a complain to a photo.
	 * If config.getMaxComplains is exceeded, file is moved to config.getPhotoComplainedDir()
	 * 
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value="/photos/{photoId}/complain", method=RequestMethod.POST)
	public ResponseEntity<String> postComplain(@PathVariable("photoId") final long photoId){
		final Photo dbPhoto = photoRepo.findByPhotoId(photoId);
		if(dbPhoto != null){
			dbPhoto.setComplains(dbPhoto.getComplains() + 1);
			
			if(dbPhoto.getComplains() > config.getMaxComplains()){
				try {
					Files.move(
							new File(config.getPhotoPath() + photoId).toPath(), 
							new File(config.getPhotoComplainedDir() + photoId + "_" + dbPhoto.getDishId() + ".jpg").toPath(), 
							StandardCopyOption.ATOMIC_MOVE
					);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				photoRepo.delete(dbPhoto);
			} else {
				photoRepo.save(dbPhoto);
			}
		} else {
			System.out.println("postComplain() try to complain not existing photo : " + photoId);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	
	//
	// Private
	//
	
	/**
	 * Returns an ByteArrayOutputStream of a picture stored on the server
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private ByteArrayOutputStream getBAOForFile(final String filePath) throws IOException{
		// Retrieve image 
        InputStream is = new FileInputStream(filePath); 

        // Prepare buffered image.
        BufferedImage img = ImageIO.read(is);
        
        // Create a byte array output stream.
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        
        // Write to output stream
        ImageIO.write(img, "jpg", bao);	
        
        return bao;
	}
	
	@Autowired
	private BuildingRepository buildingRepo;
	
	@Autowired
	private DeleteRepository deleteRepo;
	
	@Autowired
	private DishRepository dishRepo;
	
	@Autowired
	private SequenceRepository seqRepo;
	
	@Autowired
	private DateRepository dateRepo;
	
	@Autowired
	private RatingRepository ratingRepo;
	
	@Autowired
	private OfferedAtRepository offeredAtRepo;
	
	@Autowired
	private PhotoRepository photoRepo;
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
	@Autowired
	private Configuration config;

}
