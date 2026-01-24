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
     * vvi : Disable binding for currentUser because current user details changed with contact details that's why binding == false
     * it is better to remove this method this method is very dangerous
     */
    // @ModelAttribute(name = "currentUser", binding = false)
    // public User addCurrentUser(Authentication authentication) {
    //     return currentUserService.getCurrentUser(authentication);
    // }

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
    public String addNewContact(Authentication authentication, 
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
        
        //Get the current user from Authentication 
        User user = currentUserService.getCurrentUser(authentication);

        log.info("Current User email : {} and user_id : {}", user.getEmail(), user.getUserId());
        // ✅ Save Contact if no duplicates
        Contact contact = contactService.createContact(user, contactFormDTO);
        log.info("Current User email : {} and user_id : {}", user.getEmail(), user.getUserId());
        // image handling must NOT throw unchecked exception and image upload cannot came in transactional process
        setContactPicture(contact, picture); 
        
        contactRepository.save(contact);
        log.info("Current User email : {} and user_id : {}", user.getEmail(), user.getUserId());

        session.setAttribute("message", MessageType.CONTACT_SAVED.getDisplayValue());
        return "redirect:/user/contacts/add";
    }
    /**
     * set the contact image if photo is not provided then it will take default image according to gender selection
     */
    public void setContactPicture(Contact contact, MultipartFile picture) {
        if (picture != null && !picture.isEmpty()) {
            try {  
                //MultipartFile image = contactFormDTO.getPicture();
                log.info("File name: {}", picture.getOriginalFilename());
                log.info("File size: {}kB", picture.getSize()/(1024));
                log.info("Content type: {}", picture.getContentType());
        
                String path = contactImageService.saveProfileImage(picture, contact);
                log.info("File path : {}", path);
                contact.setPicture(path);
                
            } catch (IOException e) {
                // OPTION 1: rethrow → rollback (clean)
                throw new RuntimeException("Image upload failed", e);

                // OPTION 2 (if image optional):
                // log.warn("Image upload failed", e);
            }
        } else {
            if(null == contact.getGender()) {
                contact.setPicture(SCMConstants.DEFAULT_IMAGE);
            } else switch (contact.getGender()) {
                case MALE -> {
                    contact.setPicture(SCMConstants.DEFAULT_MALE);
                }
                case FEMALE -> {
                    contact.setPicture(SCMConstants.DEFAULT_FEMALE);
                }
                default -> {
                    contact.setPicture(SCMConstants.DEFAULT_IMAGE);
                }
            }
        }
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
            Authentication authentication,
            @RequestParam(defaultValue = SCMConstants.ZERO) int page,
            @RequestParam(defaultValue = SCMConstants.CONTACT_PAGES) int size,
            Model model
    ) {
                
        //Get the current user from Authentication 
        User user = currentUserService.getCurrentUser(authentication);
        
        Page<Contact> contactPage =
                contactService.getAllContactsListByUser(user, page, size);

        model.addAttribute("contacts", contactPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactPage.getTotalPages());

        return "contact";
    }
}
