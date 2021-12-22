package com.jentrent.tracker.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

public class BaseService{

	private static final Validator VALIDATOR = Validation.byDefaultProvider().configure()
			.messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory().getValidator();

	protected void validate(Object o) throws TrackerException{

		Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(o);

		if(violations != null && violations.size() > 0){

			TrackerException e = new TrackerException();

			for(ConstraintViolation<Object> violation: violations){
				e.addError(violation.getMessage());
			}

			throw e;

		}

	}

}
