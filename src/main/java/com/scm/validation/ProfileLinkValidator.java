package com.scm.validation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import com.scm.validation.annotation.ValidProfileLink;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProfileLinkValidator
        implements ConstraintValidator<ValidProfileLink, String> {

    // Allowed domains (you can extend later)
    private static final Set<String> ALLOWED_DOMAINS = Set.of(
            "linkedin.com",
            "github.com",
            "twitter.com",
            "facebook.com",
            "instagram.com"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Allow empty (use @NotBlank if required)
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            URI uri = new URI(value);

            // Must be http or https
            if (!("http".equalsIgnoreCase(uri.getScheme())
               || "https".equalsIgnoreCase(uri.getScheme()))) {
                return false;
            }

            String host = uri.getHost();
            if (host == null) {
                return false;
            }

            // Check allowed domains
            return ALLOWED_DOMAINS.stream()
                    .anyMatch(domain -> host.endsWith(domain));

        } catch (URISyntaxException e) {
            return false;
        }
    }
}
