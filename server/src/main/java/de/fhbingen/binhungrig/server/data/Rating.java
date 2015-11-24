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
@Table(name = "Ratings")
public class Rating {

	@Id
	@GeneratedValue
	private long ratingId;
	
	private long seq;
	
	//TODO: Create and use FK on Dates.dateId
	private Date date;
	
	private short value;
	
	@Column(name = "fk_dishId")
	private long dishId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_dishId", insertable = false, updatable = false)
	@JsonIgnore
	private Dish dish;

	@JsonIgnore
	public Dish getDish(){
		return dish;
	}

	public long getRatingId() {
		return ratingId;
	}

	public long getSeq() {
		return seq;
	}

	public Date getDate() {
		return date;
	}

	public short getValue() {
		return value;
	}

	public long getDishId() {
		return dishId;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Rating: [ratingId: %d, seq: %d, date: %s, value: %d]"
				, ratingId
				, seq
				, date.toString()
				, value);
	}
}
