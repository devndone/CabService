package org.dev.ecommerce.cabservice.service;

import java.io.IOException;
import java.util.List;

import org.dev.ecommerce.cabservice.model.Cab;
import org.dev.ecommerce.cabservice.model.City;

public interface CabTrackingService {

	List<Cab> findAvailableCabs(City cityId);

	Cab updateTrackingInfo(String existingCabId, Cab updatedCab) throws IOException;

}
