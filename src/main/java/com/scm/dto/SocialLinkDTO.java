package com.scm.dto;

import com.scm.validation.annotation.ValidSocialLink;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidSocialLink
public class SocialLinkDTO {

    private String title; // Facebook, GitHub, LinkedIn etc.

    @NotBlank(message = "Social link URL is required")
    private String link;
}

