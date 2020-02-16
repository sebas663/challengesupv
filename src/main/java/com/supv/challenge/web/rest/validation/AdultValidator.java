package com.supv.challenge.web.rest.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {
	protected long minAge;

	@Override
	public void initialize(Adult ageValue) {
		this.minAge = ageValue.value();
	}

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
		// null values are valid
		if (date == null) {
			return true;
		}
		LocalDate today = LocalDate.now();
		
		Period period = Period.between(date, today);

		return period.getYears()  >= minAge;
	}

}
