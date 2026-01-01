package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.constants.MessageType;
import com.scm.dto.ContactFormDTO;
import com.scm.dto.SocialLinkDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")

public class ContactContoller {
    private static final Logger log = LoggerFactory.getLogger(ContactContoller.class);
    
    @RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addUserContact(Model model) {
        log.info("Entering into Contact form ");
        ContactFormDTO contactFormDTO = new ContactFormDTO();

        contactFormDTO.getSocialLinks().add(new SocialLinkDTO()); // index 0
        contactFormDTO.getSocialLinks().add(new SocialLinkDTO()); // index 1

        model.addAttribute("contactFormDTO", contactFormDTO);
        return "user/addUserContacts";
    }
    
    @PostMapping("/addNewContact")
    public String addNewContact(@Valid @ModelAttribute ContactFormDTO contactFormDTO, BindingResult bindingResults, HttpSession session) {
        log.info("Entering to Add new Contact Form");
        if(bindingResults.hasErrors()) {
            log.error("Contact Form details filled wrongly somewhere");
            return "user/addUserContacts";
        }
        session.setAttribute("message", MessageType.REGISTRATION_SUCCESSFULL.getDisplayValue());
        return "redirect:/user/contacts/add";
    }
}
