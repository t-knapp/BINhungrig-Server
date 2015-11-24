package de.fhbingen.binhungrig.server.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Ingredients")
public class Ingredient {

	private String key;
	
	private String description;
	
	@Id
	private long seq;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSeq() {
		return seq;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Ingredient [key: %s, description: %s, seq: %d]"
				, key
				, description
				, seq
		);
	}
}
