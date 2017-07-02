package org.dev.ecommerce.cabservice.service;

import java.io.IOException;

import org.dev.ecommerce.cabservice.model.Booking;
import org.springframework.data.domain.Sort.Direction;

public interface BookingService {

	Iterable<Booking> findAll(int page, int count, Direction direction, String sortProperty);

	Booking save(Booking detail);

	Booking findOne(String id);

	void delete(String id);

	Booking update(String id, Booking booking) throws IOException;

}
