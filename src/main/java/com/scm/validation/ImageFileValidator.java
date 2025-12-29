package com.scm.validation;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validation.annotation.ValidImageFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageFileValidator
        implements ConstraintValidator<ValidImageFile, MultipartFile> {

    private static final long MAX_SIZE = 2 * 1024 * 1024; // 2 MB

    @Override
    public boolean isValid(MultipartFile file,
                           ConstraintValidatorContext context) {

        // ✔ Allow empty (optional upload)
        if (file == null || file.isEmpty()) {
            return true;
        }

        // ✔ Check file size
        if (file.getSize() > MAX_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Image size must be less than 2 MB"
            ).addConstraintViolation();
            return false;
        }

        // ✔ Check content type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only image files are allowed"
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
