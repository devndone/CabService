package org.dev.ecommerce.cabservice.repository;

import java.util.List;

import org.dev.ecommerce.cabservice.model.Cab;
import org.dev.ecommerce.cabservice.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabRepository extends PagingAndSortingRepository<Cab, String> {

	@Query("select c from Cab c where UPPER(c.cabStatus) like UPPER(?1) or "
			+ "UPPER(c.description) like UPPER(?1)")
	public List<Cab> search(String term);

	@Query("select c from Cab c where c.city like UPPER(?1)")
	public List<Cab> find(City city);
	
}
