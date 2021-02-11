package com.csv.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;


/**
 * The persistent class for the service_relationships database table.
 * 
 */
@Builder(toBuilder = true)
@Entity
@Table(name="service_relationships")
@NamedQuery(name="ServiceRelationship.findAll", query="SELECT s FROM ServiceRelationship s")
public class ServiceRelationship implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceRelationshipPK id;

	private BigDecimal impact;

	private String label;

	public ServiceRelationship() {
	}

	public ServiceRelationship(ServiceRelationshipPK id, BigDecimal impact, String label) {
		super();
		this.id = id;
		this.impact = impact;
		this.label = label;
	}



	public ServiceRelationshipPK getId() {
		return this.id;
	}

	public void setId(ServiceRelationshipPK id) {
		this.id = id;
	}

	public BigDecimal getImpact() {
		return this.impact;
	}

	public void setImpact(BigDecimal impact) {
		this.impact = impact;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}