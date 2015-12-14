package de.fhbingen.binhungrig.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="_deletes")
public class Delete {
	
	@Id
	private long seq;
	
	private String tableName;
	
	//TODO: Shorten (by mapping) name b.c. JSON
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
