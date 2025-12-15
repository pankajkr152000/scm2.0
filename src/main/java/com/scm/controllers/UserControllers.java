package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserControllers {
    final static Logger log = LoggerFactory.getLogger(UserControllers.class);
    // user dashboard
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        return "user/userDashboard";
    }

    // user profile
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfile(Authentication authentication) {
        String loggedInUserName = UserControllersHelper.getEmailOfLoggedinUser(authentication);
        log.info("Username of logged in user : {}", loggedInUserName);
        
        return "user/userProfile";
    }
    


}
