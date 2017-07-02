package org.dev.ecommerce.cabservice.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.dev.ecommerce.cabservice.common.CabStatus;
import org.dev.ecommerce.cabservice.model.Booking;
import org.dev.ecommerce.cabservice.model.Cab;
import org.dev.ecommerce.cabservice.model.City;
import org.dev.ecommerce.cabservice.model.User;
import org.dev.ecommerce.cabservice.service.BookingService;
import org.dev.ecommerce.cabservice.service.CabRegistrationService;
import org.dev.ecommerce.cabservice.service.CabTrackingService;
import org.dev.ecommerce.cabservice.service.CityRegistrationService;
import org.dev.ecommerce.cabservice.validator.CabServiceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cabs", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabServiceController {

	public static final Logger logger = LoggerFactory.getLogger(CabServiceController.class);

	private final BookingService bookingService;
	private final CityRegistrationService cityRegistrationService;
	private final CabRegistrationService cabRegistrationService;
	private final CabTrackingService cabTrackingService;
	private final CabServiceValidator cabValidator;

	@Autowired
	public CabServiceController(BookingService bookingService, CabTrackingService cabTrackingService,
			CityRegistrationService cityRegistrationService, CabRegistrationService cabRegistrationService,
			CabServiceValidator CabValidator) {
		this.bookingService = bookingService;
		this.cabTrackingService = cabTrackingService;
		this.cityRegistrationService = cityRegistrationService;
		this.cabRegistrationService = cabRegistrationService;
		this.cabValidator = CabValidator;
	}

	@InitBinder
	protected void dataBinding(WebDataBinder binder) {
		binder.addValidators(cabValidator);
	}

	@RequestMapping(value = "/bookCab", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public Booking bookCab(@RequestBody @Valid City city, @RequestBody @Valid User user) {

		// search for all the cab within a city
		List<Cab> cabs = cabTrackingService.findAvailableCabs(city);
		Cab selectableCab = null;
		String selectableCabId = null;

		// optimize the cab to select
		for (Cab c : cabs) {
			if (c.getCabStatus() == CabStatus.IDLE) {
				selectableCab = c;
				selectableCabId = c.getCabId();
				break;
			}
		}

		selectableCab.setCabStatus(CabStatus.ON_TRIP);

		// book and revert with the cab model
		try {
			selectableCab = cabTrackingService.updateTrackingInfo(selectableCabId, selectableCab);
		} catch (IOException e) {
			selectableCab = null;
			logger.error("Cab update tracking failed, Can't assign the cab!");
		}
		Booking booking = new Booking();
		booking.setCab(selectableCab);
		booking.setCity(city);
		booking.setUser(user);
		bookingService.save(booking);

		return booking;
	}

	@RequestMapping(value = "/cabreg", method = RequestMethod.GET)
	public Iterable<Cab> findAllCab(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "count", defaultValue = "10", required = false) int count,
			@RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction,
			@RequestParam(value = "sort", defaultValue = "cabName", required = false) String sortProperty) {
		return cabRegistrationService.findAll(page, count, direction, sortProperty);
	}

	@RequestMapping(value = "/cabreg/{id}", method = RequestMethod.GET)
	public HttpEntity<?> findCab(@PathVariable String id) {
		Cab detail = cabRegistrationService.findOne(id);
		if (detail == null) {
			logger.error("Cab with id {} not found.", id);
			return new ResponseEntity<CabServiceException>(new CabServiceException("Cab with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cab>(detail, HttpStatus.OK);
	}

	@RequestMapping(value = "/cabreg", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public Cab createCab(@RequestBody @Valid Cab detail) {
		return cabRegistrationService.save(detail);
	}

	@RequestMapping(value = "/cabreg/{id}", method = RequestMethod.PUT)
	public HttpEntity<?> updateCab(@PathVariable String id, @Valid @RequestBody Cab cab) throws IOException {
		Cab updated = cabRegistrationService.update(id, cab);// request.getReader());
		if (updated == null) {
			logger.error("Cab with id {} not found.", id);
			return new ResponseEntity<CabServiceException>(new CabServiceException("Cab with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cab>(updated, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/cabreg/{id}", method = RequestMethod.DELETE)
	public HttpEntity<?> deleteCab(@PathVariable String id) {
		cabRegistrationService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/cityreg", method = RequestMethod.GET)
	public Iterable<City> findAllCity(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "count", defaultValue = "10", required = false) int count,
			@RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction,
			@RequestParam(value = "sort", defaultValue = "cityName", required = false) String sortProperty) {
		return cityRegistrationService.findAll(page, count, direction, sortProperty);
	}

	@RequestMapping(value = "/cityreg/{id}", method = RequestMethod.GET)
	public HttpEntity<?> findCity(@PathVariable String id) {
		City detail = cityRegistrationService.findOne(id);
		if (detail == null) {
			logger.error("Cab with id {} not found.", id);
			return new ResponseEntity<CabServiceException>(new CabServiceException("Cab with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<City>(detail, HttpStatus.OK);
	}

	@RequestMapping(value = "/cityreg", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public City createCity(@RequestBody @Valid City detail) {
		return cityRegistrationService.save(detail);
	}

	@RequestMapping(value = "/cityreg/{id}", method = RequestMethod.PUT)
	public HttpEntity<?> updateCity(@PathVariable String id, @Valid @RequestBody City city) throws IOException {
		City updated = cityRegistrationService.update(id, city);// request.getReader());
		if (updated == null) {
			logger.error("Cab with id {} not found.", id);
			return new ResponseEntity<CabServiceException>(new CabServiceException("Cab with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<City>(updated, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/cityreg/{id}", method = RequestMethod.DELETE)
	public HttpEntity<?> deleteCity(@PathVariable String id) {
		cityRegistrationService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/error");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	static class CabServiceException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String errorMessage;

		public CabServiceException(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		@Override
		public StackTraceElement[] getStackTrace() {
			// logger.error(Arrays.toString(super.getStackTrace()));
			return null;
		}
	}

}