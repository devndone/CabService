package org.dev.ecommerce.cabservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dev.ecommerce.cabservice.common.CabStatus;
import org.dev.ecommerce.cabservice.model.Cab;
import org.dev.ecommerce.cabservice.model.City;
import org.dev.ecommerce.cabservice.repository.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("cabRegistrationService")
public class CabRegistrationServiceImpl implements CabRegistrationService {

	private final CabRepository cabRepository;

	@Autowired
	public CabRegistrationServiceImpl(CabRepository cabRepository) {
		this.cabRepository = cabRepository;
	}

	@Override
	public Iterable<Cab> findAll(int page, int count, Direction direction, String sortProperty) {
		Page<Cab> result = cabRepository.findAll(new PageRequest(page, count, new Sort(direction, sortProperty)));
		return result.getContent();
	}
	
	@Override
	public List<Cab> find(City city) {
		return cabRepository.find(city);
	}

	@Override
	public Cab save(Cab detail) {
		return cabRepository.save(detail);
	}

	@Override
	public Cab findOne(String id) {
		return cabRepository.findOne(id);
	}

	@Override
	public void delete(String id) {
		Cab existing = findOne(id);
		if (existing == null) {
			return;
		}
		cabRepository.delete(existing);
	}

	@Override
	public Cab update(String id, Cab cab) throws IOException {
		Cab existing = findOne(id);
		if (existing == null) {
			return null;
		}
		if(validateStateTransition(existing, cab)) {
			existing = cab;
			return cabRepository.save(existing);
		}
		return null;
	}

	private boolean validateStateTransition(Cab existing, Cab cab) {
		if((existing.getCabStatus() == CabStatus.ON_TRIP && cab.getCabStatus() == CabStatus.IDLE)
				|| (existing.getCabStatus() == CabStatus.IDLE && cab.getCabStatus() == CabStatus.ON_TRIP)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Cab> search(String queryTerm) {
		List<Cab> cabDetails = cabRepository.search(queryTerm);
		return cabDetails == null ? new ArrayList<>() : cabDetails;
	}

}
