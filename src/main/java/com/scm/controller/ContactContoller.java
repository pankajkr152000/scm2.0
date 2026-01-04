package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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


        MultipartFile image = contactFormDTO.getPicture();
        if (image != null && !image.isEmpty()) {
            log.info("File name: {}", image.getOriginalFilename());
            log.info("File size: {}", image.getSize());
            log.info("Content type: {}", image.getContentType());
        }
        if(bindingResults.hasErrors()) {
            log.error("Contact Form details filled wrongly somewhere");
            return "user/addUserContacts";
        }
        session.setAttribute("message", MessageType.CONTACT_SAVED.getDisplayValue());
        return "redirect:/user/contacts/add";
    }

    // 2️⃣ AJAX image upload (progress bar)
    @PostMapping("/upload-avatar")
    @ResponseBody
    public ResponseEntity<String> uploadAvatar(
            @RequestParam("picture") MultipartFile picture) {

        if (picture == null || picture.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        // save image (DB / FS / cloud)
        return ResponseEntity.ok("Uploaded");
    }
}
