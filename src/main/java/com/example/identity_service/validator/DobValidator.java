package com.example.identity_service.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
	private int min;

	@Override
	public boolean isValid(LocalDate arg0, ConstraintValidatorContext arg1) {
		if (Objects.isNull(arg0)) {
			return true;
		}

		long years = ChronoUnit.YEARS.between(arg0, LocalDate.now());

		return years >= min;
	}

	@Override
	public void initialize(DobConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.min = constraintAnnotation.min();
	}
}
