package com.scm.customAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scm.validation.ContactNumberValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Constraint(validatedBy = ContactNumberValidation.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumberValidator {

    String message() default "Invalid Contact Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
