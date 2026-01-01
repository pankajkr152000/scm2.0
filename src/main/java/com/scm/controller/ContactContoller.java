package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.dto.ContactFormDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")

public class ContactContoller {
    @RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addUserContact(@ModelAttribute ContactFormDTO contactFormDTO, BindingResult bindingResults, HttpSession session) {
        if(bindingResults.hasErrors()) {
            return "user/addUserContacts";
        }
        
        return "user/addUserContacts";
    }
}
