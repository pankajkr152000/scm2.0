package com.scm.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.scm.constants.MessageType;
import com.scm.constants.Providers;
import com.scm.constants.SCMConstants;
import com.scm.dto.ContactFormDTO;
import com.scm.dto.SocialLinkDTO;
import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.repository.IContactRepository;
import com.scm.service.ContactImageService;
import com.scm.service.CurrentUserService;
import com.scm.service.IContactService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")

public class ContactController {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    
    private final IContactService contactService;
    private final CurrentUserService currentUserService;
    private final ContactImageService contactImageService;
    private final IContactRepository contactRepository;

    

    public ContactController(IContactService contactService, CurrentUserService currentUserService, ContactImageService contactImageService,
                                        IContactRepository contactRepository) {
        this.contactService = contactService;
        this.currentUserService = currentUserService;
        this.contactImageService = contactImageService;
        this.contactRepository = contactRepository;
    }

    
    /**
     * Centralize user fetch via @ModelAttribute (Very Useful)
     * If many methods need the user, do this once:
     */
    @ModelAttribute("currentUser")
    public User addCurrentUser(Authentication authentication) {
        return currentUserService.getCurrentUser(authentication);
    }

    @GetMapping("/add")
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
    public String addNewContact(@ModelAttribute("currentUser") User user, 
                                @Valid @ModelAttribute ContactFormDTO contactFormDTO, 
                                @RequestParam(name = "picture", required = false) MultipartFile picture,
                                BindingResult bindingResults, 
                                HttpSession session
        ) {
        log.info("Entering to Add new Contact Form");

        if (bindingResults.hasErrors()) {
            log.warn("Contact Form details filled wrongly somewhere");
            session.setAttribute("errorMessage", "Please fix the errors and try again");
            return "user/addUserContacts";
        }
        
        //MultipartFile image = contactFormDTO.getPicture();
        if (picture != null && !picture.isEmpty()) {
            log.info("File name: {}", picture.getOriginalFilename());
            log.info("File size: {}kB", picture.getSize()/(1024));
            log.info("Content type: {}", picture.getContentType());
        }
        //Get the current user from Authentication 
        // user = currentUserService.getCurrentUser(authentication);

        // ✅ Save Contact if no duplicates
        Contact contact = contactService.createContact(user, contactFormDTO);

        // image handling must NOT throw unchecked exception
        if (picture != null && !picture.isEmpty()) {
            try {
                String path = contactImageService.saveProfileImage(picture, contact);
                contact.setPicture(path);
                contactRepository.save(contact);
            } catch (IOException e) {
                // OPTION 1: rethrow → rollback (clean)
                throw new RuntimeException("Image upload failed", e);

                // OPTION 2 (if image optional):
                // log.warn("Image upload failed", e);
            }
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

    /**
     * get all contacts of the user
     */

    @GetMapping("/allContacts")
    public String listContacts(
            @ModelAttribute("currentUser") User user,
            @RequestParam(defaultValue = SCMConstants.ZERO) int page,
            @RequestParam(defaultValue = SCMConstants.CONTACT_PAGES) int size,
            Model model
    ) {

        Page<Contact> contactPage =
                contactService.getAllContactsListByUser(user, page, size);

        model.addAttribute("contacts", contactPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactPage.getTotalPages());

        return "contact";
    }
}
