package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.service.CurrentUserService;


@Controller
@RequestMapping("/user")
public class UserController {
    final static Logger log = LoggerFactory.getLogger(UserController.class);

    // private final RootController rootcontroller;
    private final CurrentUserService currentUserService;

    public UserController(/*RootController rootcontroller,*/ CurrentUserService currentUserService) {
        // this.rootcontroller = rootcontroller;
        this.currentUserService = currentUserService;
    }

    // user dashboard
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        log.info("user Dashboard page.");
        return "user/userDashboard";
    }

    // user profile
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfile(Model model, Authentication authentication) {
        log.info("User Profile page.");
        String loggedInUserName = currentUserService.getEmailOfLoggedinUser(authentication);
        log.info("Username of logged in user : {}", loggedInUserName);

        model.addAttribute("loggedInUserName", loggedInUserName);
        // ❌ DO NOT call RootController manually
        return "user/userProfile";
    }
    


}
