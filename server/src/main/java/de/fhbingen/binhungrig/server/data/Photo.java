package de.fhbingen.binhungrig.server.data;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Photos")
public class Photo {

	@Id
	@GeneratedValue
	private long photoId;
	
	private long seq;
	
	private Date dateUpload;
	
	private int complains;
	
	@Column(name = "fk_dishId")
	private long dishId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_dishId", insertable = false, updatable = false)
	@JsonIgnore
	private Dish dish;

	public long getPhotoId() {
		return photoId;
	}

	public long getSeq() {
		return seq;
	}

	public Date getDateUpload() {
		return dateUpload;
	}

	public int getComplains() {
		return complains;
	}

	public long getDishId() {
		return dishId;
	}

	public Dish getDish() {
		return dish;
	}
		
}
