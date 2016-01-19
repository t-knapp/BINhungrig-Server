package de.fhbingen.binhungrig.server.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * JPA Entity for Dates
 * 
 * @author tknapp
 *
 */
@Entity
@Table(name = "Dates")
public class Date {

	public Date(){};
	
	public Date(final String date){
		this.date = java.sql.Date.valueOf(date);
	}
	
	@Id
	@GeneratedValue
	private long dateId;
	
	private long seq;
	
	private java.sql.Date date;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "offeredAt",
	    joinColumns = @JoinColumn(name = "fk_dateId", referencedColumnName = "dateId"),
	    inverseJoinColumns = @JoinColumn(name = "fk_dishId", referencedColumnName = "dishId"))
	@JsonIgnore
	private List<Dish> offeredDishes;
	
	@JsonIgnore
	public List<Dish> getOfferedDishes(){
		return offeredDishes;
	}
	
	public long getDateId() {
		return dateId;
	}
	public long getSeq() {
		return seq;
	}
	public java.sql.Date getDate() {
		return date;
	}
	
}
