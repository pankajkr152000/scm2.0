package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.constants.MessageType;
import com.scm.dto.UserSignupFormRequestTO;
import com.scm.services.IUserSignupFormServices;
import com.scm.services.helpers.ExistingUserAttributeCheck;
import com.scm.services.impl.UserSignupFormServicesImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageControllers {

    // constructor injection of IUserSignupFormServices
    private final IUserSignupFormServices userSignupFormServices;
    private final ExistingUserAttributeCheck existingUserAttributeCheck;
    
    public PageControllers(UserSignupFormServicesImpl userSignupFormServices,
                           ExistingUserAttributeCheck existingUserAttributeCheck) {
        this.userSignupFormServices = userSignupFormServices;
        this.existingUserAttributeCheck = existingUserAttributeCheck;
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }
    

    @RequestMapping("/home")
    public String home(Model model) { // model is used to send dynamic dato to the html page
        System.out.println("My Home page Handler");
        model.addAttribute("name", "Pankaj Kumar");
        model.addAttribute("company", "TCSL");
        // here we are returning the html page direct from templates folder
        return "home"; // home is home.html page present inside templates folder
    }

    @RequestMapping("/about")
    public String about() {
        System.out.println("This is about page.");
        return "about";
    }

    @RequestMapping("/base")
    public String base() {
        System.out.println("This is base page.");
        return "base";
    }

    @RequestMapping("/services")
    public String services(Model model) {
        model.addAttribute("isTrue", false);
        System.out.println("This is services page.");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact(Model model) {
        // model.addAttribute("isTrue", false);
        System.out.println("This is contact page.");
        return "contact";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        // model.addAttribute("isTrue", false);
        System.out.println("This is login page.");
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        // model.addAttribute("isTrue", false);
        System.out.println("This is signup page.");
        UserSignupFormRequestTO userSignupFormRequestTO = new UserSignupFormRequestTO();
        // userSignupFormRequest.setFullName("Pankaj");
        model.addAttribute("userSignupFormRequestTO", userSignupFormRequestTO);
        return "signup";
    }

    // processing signup/register form
    @PostMapping()
    @RequestMapping(value = "/do-signup", method = RequestMethod.POST)
    // object will be automatically created & form data will come into userSignupFormRequestTO
    public String doSignup(
            @Valid @ModelAttribute UserSignupFormRequestTO userSignupFormRequestTO,
            BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // ✅ Check for duplicates
        MessageType duplicateMessage = existingUserAttributeCheck.checkDuplicates(userSignupFormRequestTO);
        if (duplicateMessage != null) {
            session.setAttribute("message", duplicateMessage.getDisplayValue());
            return "redirect:/signup";
        }

        // ✅ Save user if no duplicates
        userSignupFormServices.createUser(userSignupFormRequestTO);

        session.setAttribute("message", MessageType.REGISTRATION_SUCCESSFULL.getDisplayValue());
        return "redirect:/signup";
    }
}
