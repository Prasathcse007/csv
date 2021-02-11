package com.csv.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The primary key class for the service_relationships database table.
 * 
 */
@Builder(toBuilder = true)
@Embeddable
public class ServiceRelationshipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String parent;

	private String child;

	public ServiceRelationshipPK() {
	}
	
	
	public ServiceRelationshipPK(String parent, String child) {
		super();
		this.parent = parent;
		this.child = child;
	}


	public String getParent() {
		return this.parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getChild() {
		return this.child;
	}
	public void setChild(String child) {
		this.child = child;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ServiceRelationshipPK)) {
			return false;
		}
		ServiceRelationshipPK castOther = (ServiceRelationshipPK)other;
		return 
			this.parent.equals(castOther.parent)
			&& this.child.equals(castOther.child);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.parent.hashCode();
		hash = hash * prime + this.child.hashCode();
		
		return hash;
	}
}