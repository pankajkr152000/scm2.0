package com.scm.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scm.validation.SocialLinkValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })   // ✅ FIX HERE
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SocialLinkValidator.class)
public @interface ValidSocialLink {

    String message() default "Invalid social link";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}



