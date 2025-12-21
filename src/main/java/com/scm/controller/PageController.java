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
import com.scm.dto.UserSignupFormRequestDTO;
import com.scm.service.IUserSignupFormService;
import com.scm.service.UserValidationService;
import com.scm.service.impl.UserSignupFormServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
    final static Logger log = LoggerFactory.getLogger(PageController.class);
    // constructor injection of IUserSignupFormService
    private final IUserSignupFormService userSignupFormService;
    private final UserValidationService userValidationService;
    
    public PageController(UserSignupFormServiceImpl userSignupFormService,
                           UserValidationService userValidationService) {
        this.userSignupFormService = userSignupFormService;
        this.userValidationService = userValidationService;
    }

    @RequestMapping("/")
    public String index() {
        log.info("Index page.");
        return "redirect:/home";
    }
    

    @RequestMapping("/home")
    public String home(Model model) { // model is used to send dynamic dato to the html page
        // System.out.println("My Home page Handler");
        model.addAttribute("name", "Pankaj Kumar");
        model.addAttribute("company", "TCSL");
        // here we are returning the html page direct from templates folder
        log.info("Home page.");
        return "home"; // home is home.html page present inside templates folder
    }

    @RequestMapping("/about")
    public String about() {
        // System.out.println("This is about page.");
        log.info("About page.");
        return "about";
    }

    @RequestMapping("/base")
    public String base() {
        // System.out.println("This is base page.");
        log.info("Base page.");
        return "base";
    }

    @RequestMapping("/services")
    public String services(Model model) {
        model.addAttribute("isTrue", false);
        // System.out.println("This is services page.");
        log.info("Services page.");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact(Model model) {
        // model.addAttribute("isTrue", false);
        // System.out.println("This is contact page.");
        log.info("Contact page.");
        return "contact";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        // model.addAttribute("isTrue", false);
        // System.out.println("This is login page.");
        log.info("Login page.");
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        log.info("Signup page.");
        // model.addAttribute("isTrue", false);
        // System.out.println("This is signup page.");
        UserSignupFormRequestDTO userSignupFormRequestDTO = new UserSignupFormRequestDTO();
        // userSignupFormRequest.setFullName("Pankaj");
        model.addAttribute("userSignupFormRequestDTO", userSignupFormRequestDTO);

        return "signup";
    }

    // processing signup/register form
    @PostMapping()
    @RequestMapping(value = "/do-signup", method = RequestMethod.POST)
    // object will be automatically created & form data will come into userSignupFormRequestTO
    public String doSignup(
            @Valid @ModelAttribute UserSignupFormRequestDTO userSignupFormRequestTO,
            BindingResult bindingResult,
            HttpSession session) {
        log.info("do-Signup page.");
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // ✅ Check for duplicates
        MessageType duplicateMessage = userValidationService.checkDuplicates(userSignupFormRequestTO);
        if (duplicateMessage != null) {
            session.setAttribute("message", duplicateMessage.getDisplayValue());
            return "redirect:/signup";
        }

        // ✅ Save user if no duplicates
        userSignupFormService.createUser(userSignupFormRequestTO);

        session.setAttribute("message", MessageType.REGISTRATION_SUCCESSFULL.getDisplayValue());
        return "redirect:/signup";
    }
}
