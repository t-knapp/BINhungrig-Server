package de.fhbingen.binhungrig.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JPA Entity for Deletes
 * 
 * Deletes are not 'replicated' on clientside, they are applied.
 * Means every tuple in table is searched on clientside and will 
 * be deleted on device.
 * 
 * @author tknapp
 *
 */
@Entity
@Table(name="_deletes")
public class Delete {
	
	@Id
	private long seq;
	
	private String tableName;
	
	@Column(name = "id")
	private long delId;
	
	public long getSeq() {
		return seq;
	}
	public String getTableName() {
		return tableName;
	}
	public long getDeleteId() {
		return delId;
	}

}
