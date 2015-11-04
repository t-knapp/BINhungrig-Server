package de.fhbingen.binhungrig.server.data;

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
	private long deleteSeqNumber;
	
	public long getSeq() {
		return seq;
	}
	public String getTableName() {
		return tableName;
	}
	public long getDeleteSeqNumber() {
		return deleteSeqNumber;
	}

}
