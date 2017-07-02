package org.dev.ecommerce.cabservice.validator;

import org.dev.ecommerce.cabservice.model.Cab;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CabServiceValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Cab.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Cab detail = (Cab) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cabId", "", "error.cabId.notnull");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cabName", "", "error.cabName.notnull");
		if (detail.getCabId().length() != 11) {
			errors.rejectValue("cabId", "", "error.cabId.size");
		}
	}
	
}
