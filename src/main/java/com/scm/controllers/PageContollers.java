package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.constants.MessageType;
import com.scm.dto.UserSignupFormRequestTO;
import com.scm.services.IUserSignupFormServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageContollers {

    // constructor injection of IUserSignupFormServices
    private final IUserSignupFormServices userSignupFormServices;

    public PageContollers(IUserSignupFormServices userSignupFormServices) {
        this.userSignupFormServices = userSignupFormServices;
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
        //userSignupFormRequest.setFullName("Pankaj");
        model.addAttribute("userSignupFormRequest", userSignupFormRequestTO);
        return "signup";
    }

    // processing signup/register form
    @PostMapping()
    @RequestMapping(value = "/do-signup", method = RequestMethod.POST)
    // object will be automatically created & form data will come into userSignupFormRequest
    public String doSignup(@ModelAttribute UserSignupFormRequestTO userSignupFormRequestTO, HttpSession session) {
        System.out.println("Processing Signup .... ");
        System.out.println(userSignupFormRequestTO);
        // TODOFetch form data

        // TODOValidate form data
        // TODOSave the data into database
        // form(signup form) data comes into userSignupFormRequest
        // we will save user data from signup form from userSignupFormRequest into User
        userSignupFormServices.createUser(userSignupFormRequestTO);

        // TODOmessage : "Registration Successful"
        String msgType = MessageType.REGISTRATION_SUCCESSFULL.getDisplayValue();
        session.setAttribute("message", msgType);
        // TODORedirect the form
        return "redirect:/signup";
    }
}
