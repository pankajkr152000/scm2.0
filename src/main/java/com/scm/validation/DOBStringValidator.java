package com.scm.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.scm.validation.annotation.ValidDOBString;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DOBStringValidator
        implements ConstraintValidator<ValidDOBString, String> {

    private int minAge;
    private int maxAge;

    @Override
    public void initialize(ValidDOBString annotation) {
        this.minAge = annotation.minAge();
        this.maxAge = annotation.maxAge();
    }

    @Override
    public boolean isValid(String dobStr,
                           ConstraintValidatorContext context) {

        // ✅ optional field
        if (dobStr == null || dobStr.isBlank()) {
            return true;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(
                dobStr,
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            );
        } catch (DateTimeParseException ex) {
            return false;
        }

        LocalDate today = LocalDate.now();

        if (dob.isAfter(today) || dob.isEqual(today)) {
            return false;
        }

        int age = Period.between(dob, today).getYears();
        return age >= minAge && age <= maxAge;
    }
}

