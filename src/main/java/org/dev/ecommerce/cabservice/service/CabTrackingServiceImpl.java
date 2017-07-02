package org.dev.ecommerce.cabservice.service;

import java.io.IOException;
import java.util.List;

import org.dev.ecommerce.cabservice.model.Cab;
import org.dev.ecommerce.cabservice.model.City;
import org.springframework.stereotype.Service;

@Service("cabTrackingService")
public class CabTrackingServiceImpl implements CabTrackingService {

	private CabRegistrationService cabRegistrationService;

	public CabTrackingServiceImpl(CabRegistrationService cabRegistrationService) {
		this.cabRegistrationService = cabRegistrationService;
	}

	@Override
	public List<Cab> findAvailableCabs(City city) {
		return cabRegistrationService.find(city);
	}

	@Override
	public Cab updateTrackingInfo(String existingCabId, Cab updatedCab) throws IOException {
		return cabRegistrationService.update(existingCabId, updatedCab);
	}

}
