package org.dev.ecommerce.cabservice.repository;

import java.util.List;

import org.dev.ecommerce.cabservice.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, String> {

	@Query("select c from City c where UPPER(c.cityName) like UPPER(?1) or "
			+ "UPPER(c.description) like UPPER(?1)")
	public List<City> search(String term);
	
}
