package com.supv.challenge.web.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SexValidator implements ConstraintValidator<Sex, String> {

	@Override
	public void initialize(Sex sexValue) {
	}

	@Override
	public boolean isValid(String sex, ConstraintValidatorContext constraintValidatorContext) {
		return sex.equals("M") || sex.equals("H");
	}

}
