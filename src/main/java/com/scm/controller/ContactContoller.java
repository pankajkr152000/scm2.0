package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.scm.constants.Providers;
import com.scm.dto.ContactFormDTO;
import com.scm.dto.SocialLinkDTO;
import com.scm.entity.User;
import com.scm.service.CurrentUserService;
import com.scm.service.impl.ContactServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")

public class ContactContoller {
    private static final Logger log = LoggerFactory.getLogger(ContactContoller.class);
    
    private final ContactServiceImpl contactServiceImpl;
    private final CurrentUserService currentUserService;

    public ContactContoller(ContactServiceImpl contactServiceImpl, CurrentUserService currentUserService) {
        this.contactServiceImpl = contactServiceImpl;
        this.currentUserService = currentUserService;
    }

    @RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addUserContact(Model model) {
        log.info("Entering into Contact form ");
        ContactFormDTO contactFormDTO = new ContactFormDTO();

        // Title is set 
        SocialLinkDTO websiteLink = new SocialLinkDTO();
        SocialLinkDTO linkedInLink = new SocialLinkDTO();
        websiteLink.setTitle(Providers.WEBSITE.toString());
        linkedInLink.setTitle(Providers.LINKEDIN.toString());
        contactFormDTO.getSocialLinks().add(websiteLink); // index 0
        contactFormDTO.getSocialLinks().add(linkedInLink); // index 1

        model.addAttribute("contactFormDTO", contactFormDTO);
        return "user/addUserContacts";
    }
    
    @PostMapping("/addNewContact")
    public String addNewContact(@Valid @ModelAttribute ContactFormDTO contactFormDTO, BindingResult bindingResults,
            HttpSession session, Authentication authentication) {
        log.info("Entering to Add new Contact Form");

        MultipartFile image = contactFormDTO.getPicture();
        if (image != null && !image.isEmpty()) {
            log.info("File name: {}", image.getOriginalFilename());
            log.info("File size: {}mb", image.getSize()/(1024*1024));
            log.info("Content type: {}", image.getContentType());
        }
        if(bindingResults.hasErrors()) {
            session.setAttribute("message", MessageType.ERROR.getDisplayValue());
            log.error("Contact Form details filled wrongly somewhere");
            return "user/addUserContacts";
        }
        //Get the current user from Authentication 
        User user = currentUserService.getCurrentUser(authentication);
        // ✅ Save Contact if no duplicates
        contactServiceImpl.createContact(user, contactFormDTO);

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
