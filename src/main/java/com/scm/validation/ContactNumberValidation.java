package com.scm.validation;

import com.scm.customAnnotation.ContactNumberValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactNumberValidation implements ConstraintValidator<ContactNumberValidator, String> {

    // Either 10-digit (6–9 start) OR +91 followed by 10-digit (6–9 start)
    private static final String PHONE_PATTERN = "^(?:\\+91)?[6-9]\\d{9}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches(PHONE_PATTERN);
    }
}

