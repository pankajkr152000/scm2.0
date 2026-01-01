package com.scm.validation;

import java.net.URI;
import java.net.URISyntaxException;

import com.scm.dto.SocialLinkDTO;
import com.scm.validation.annotation.ValidSocialLink;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SocialLinkValidator
        implements ConstraintValidator<ValidSocialLink, SocialLinkDTO> {
    @Override
    public boolean isValid(SocialLinkDTO dto, ConstraintValidatorContext context) {

        if (dto == null || dto.getLink() == null || dto.getLink().isBlank()) {
            return true;
        }

        URI uri;
        try {
            uri = new URI(dto.getLink());
        } catch (URISyntaxException e) {
            return false;
        }

        String host = uri.getHost();
        if (host == null) return false;

        host = host.toLowerCase();
        String title = dto.getTitle();

        if (title == null) return true;

        return switch (title.toLowerCase()) {

            case "linkedin"   -> host.contains("linkedin.com");
            case "github"     -> host.contains("github.com");
            case "twitter"    -> host.contains("twitter.com") || host.contains("x.com");
            case "facebook"   -> host.contains("facebook.com");
            case "instagram"  -> host.contains("instagram.com");

            case "website"    -> true; // ✅ allow any valid website

            default -> true;
        };
    }


}


