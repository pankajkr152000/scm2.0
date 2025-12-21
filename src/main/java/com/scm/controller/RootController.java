package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entity.User;
import com.scm.service.CurrentUserService;
import com.scm.service.IUserSignupFormService;

@ControllerAdvice
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    private final IUserSignupFormService userSignupFormService;
    private final CurrentUserService currentUserService;

    public RootController(IUserSignupFormService userSignupFormService, CurrentUserService currentUserService) {
        this.userSignupFormService = userSignupFormService;
        this.currentUserService = currentUserService;
    }

    @ModelAttribute("loggedInUser")
    public User addLoggedinUserInformation(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String loggedInEmail = currentUserService.getEmailOfLoggedinUser(authentication);
        log.info("Logged-in user email: {}", loggedInEmail);

        return userSignupFormService
                .getUserByEmail(loggedInEmail)
                .orElse(null);
    }
}


