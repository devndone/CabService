package org.dev.ecommerce.cabservice.service;

import java.io.IOException;

import org.dev.ecommerce.cabservice.model.Booking;
import org.dev.ecommerce.cabservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;

	@Autowired
	public BookingServiceImpl(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	public Iterable<Booking> findAll(int page, int count, Direction direction, String sortProperty) {
		Page<Booking> result = bookingRepository
				.findAll(new PageRequest(page, count, new Sort(direction, sortProperty)));
		return result.getContent();
	}

	@Override
	public Booking save(Booking detail) {
		return bookingRepository.save(detail);
	}

	@Override
	public Booking findOne(String id) {
		return bookingRepository.findOne(id);
	}

	@Override
	public void delete(String id) {
		Booking existing = findOne(id);
		if (existing == null) {
			return;
		}
		bookingRepository.delete(existing);
	}

	@Override
	public Booking update(String id, Booking booking) throws IOException {
		Booking existing = findOne(id);
		if (existing == null) {
			return null;
		}
		existing = booking;
		return bookingRepository.save(existing);
	}

}
