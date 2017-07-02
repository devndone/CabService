package org.dev.ecommerce.cabservice.service;

import java.io.IOException;
import java.util.List;

import org.dev.ecommerce.cabservice.model.Cab;
import org.dev.ecommerce.cabservice.model.City;
import org.springframework.data.domain.Sort.Direction;

public interface CabRegistrationService {

	Iterable<Cab> findAll(int page, int count, Direction direction, String sortProperty);

	Cab save(Cab detail);

	Cab findOne(String id);

	void delete(String id);

	Cab update(String id, Cab cab) throws IOException;

	List<Cab> search(String queryTerm);
	
	List<Cab> find(City city);

}
