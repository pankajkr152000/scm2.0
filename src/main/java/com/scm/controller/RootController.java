package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entity.User;
import com.scm.service.CurrentUserService;
import com.scm.service.IContactService;
import com.scm.service.IUserSignupFormService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * the method inside this class will be executed every time when a handler requests
 *  that is why we use @ControllerAdvice
 */
@ControllerAdvice
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    private final IUserSignupFormService userSignupFormService;
    private final CurrentUserService currentUserService;
    private final IContactService contactService;

    public RootController(IUserSignupFormService userSignupFormService, CurrentUserService currentUserService, IContactService contactService) {
        this.userSignupFormService = userSignupFormService;
        this.currentUserService = currentUserService;
        this.contactService = contactService;
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

      @ModelAttribute
    public void addSidebarCounts(Authentication authentication,HttpServletRequest request , Model model) {

        // not logged in
        if (authentication == null) {
            return;
        }

        User user = currentUserService.getCurrentUser(authentication);

        long totalContacts = contactService.countContactsAndIsDeletedFalse(user);
        long deletedContacts = contactService.countContactsAndIsDeletedTrue(user);

        // expose current path (VERY IMPORTANT)
        model.addAttribute("currentPath", request.getRequestURI());

        model.addAttribute("totalContacts", totalContacts);
        model.addAttribute("totalDeletedContacts", deletedContacts);
    }
}


