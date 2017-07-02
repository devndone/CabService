package org.dev.ecommerce.cabservice.service;

import java.io.IOException;
import java.util.List;

import org.dev.ecommerce.cabservice.model.City;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("cityRegistrationService")
public class CityRegistrationServiceImpl implements CityRegistrationService {

	@Override
	public Iterable<City> findAll(int page, int count, Direction direction, String sortProperty) {
		return null;
	}

	@Override
	public City save(City detail) {
		return null;
	}

	@Override
	public City findOne(String id) {
		return null;
	}

	@Override
	public void delete(String id) {
		
	}

	@Override
	public City update(String id, City City) throws IOException {
		return null;
	}

	@Override
	public List<City> search(String queryTerm) {
		return null;
	}

}
