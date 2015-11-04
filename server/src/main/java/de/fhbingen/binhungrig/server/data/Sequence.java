package de.fhbingen.binhungrig.server.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "_sequence")
public class Sequence {
	
	@Id
	private String seq_name;
	
	private long seq_val;
	
	public long getLastSequence(){
		return seq_val;
	}
	
}
