package org.dev.ecommerce.cabservice.repository;

import org.dev.ecommerce.cabservice.model.Booking;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, String> {

}
