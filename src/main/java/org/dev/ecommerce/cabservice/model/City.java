package org.dev.ecommerce.cabservice.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3505187812025809016L;

	@NotNull(message = "error.cityId.notnull")
	@Size(min = 11, max = 11, message = "error.cityId.size")
	@Id
	private String cityId;
	
	@NotNull(message = "error.cityName.notnull")
	private String cityName;
	
	private String description;

	public City() {}
	
	public City(String cityId, String cityName, String description) {
		this.cityId = cityId;
		this.cityName = cityName;
		this.description = description;
	}
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
