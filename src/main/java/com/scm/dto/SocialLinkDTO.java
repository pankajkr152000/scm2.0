package com.scm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLinkDTO {

    private String title; // Facebook, GitHub, etc.

    @NotBlank(message = "Social link URL is required")
    private String link;
}

