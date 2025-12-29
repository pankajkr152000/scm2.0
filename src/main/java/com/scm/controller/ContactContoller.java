package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactContoller {
    @RequestMapping("/add")
    public String addUserContact() {
        return "user/addUserContacts";
    }
}
