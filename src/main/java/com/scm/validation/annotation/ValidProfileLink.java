package com.scm.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.scm.validation.ProfileLinkValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ProfileLinkValidator.class)
public @interface ValidProfileLink {

    String message() default "Invalid profile link";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
