package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserControllers {

    // user dashboard
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        return "user/userDashboard";
    }

    // user profile
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfile() {
        return "user/userProfile";
    }
    


}
