package com.scm.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scm.validation.DOBStringValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DOBStringValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDOBString {

    String message() default "Invalid date of birth";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minAge() default 0;
    int maxAge() default 150;
}

