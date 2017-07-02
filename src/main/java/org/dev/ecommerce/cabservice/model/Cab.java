package org.dev.ecommerce.cabservice.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.dev.ecommerce.cabservice.common.CabStatus;

@Entity
public class Cab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6092680786377511039L;

	@NotNull(message = "error.cabId.notnull")
	@Size(min = 11, max = 11, message = "error.cabId.size")
	@Id
	private String cabId;
	
	@NotNull(message = "error.cabStatus.notnull")
	private CabStatus cabStatus;
	
	private String description;
	
	private City city;

	public Cab() {}
	
	public Cab(String cabId, CabStatus cabName, String description) {
		this.cabId = cabId;
		this.cabStatus = cabName;
		this.description = description;
	}
	
	public String getCabId() {
		return cabId;
	}

	public void setCabId(String cabId) {
		this.cabId = cabId;
	}
	
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public CabStatus getCabStatus() {
		return cabStatus;
	}

	public void setCabStatus(CabStatus cabStatus) {
		this.cabStatus = cabStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
