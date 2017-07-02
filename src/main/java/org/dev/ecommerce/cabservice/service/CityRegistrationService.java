package org.dev.ecommerce.cabservice.service;

import java.io.IOException;
import java.util.List;

import org.dev.ecommerce.cabservice.model.City;
import org.springframework.data.domain.Sort.Direction;

public interface CityRegistrationService {

	Iterable<City> findAll(int page, int count, Direction direction, String sortProperty);

	City save(City detail);

	City findOne(String id);

	void delete(String id);

	City update(String id, City City) throws IOException;

	List<City> search(String queryTerm);
	
}
