package de.fhbingen.binhungrig.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

	@Value("${upload.photo.dir}")
	private String photoPath;
		
	@Value("${upload.photo.complained.dir}")
	private String photoComplainedDir;
	
	@Value("${upload.photo.maxComplains}")
	private int maxComplains;
		
	public int getMaxComplains() {
		return maxComplains;
	}

	public String getPhotoComplainedDir() {
		return photoComplainedDir;
	}

	public String getPhotoPath() {
		return photoPath;
	}

}
