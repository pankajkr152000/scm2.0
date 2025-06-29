package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageContollers {

    @RequestMapping("/home")
    public String home(Model model) { //model is used to send dynamic dato to the html page
        System.out.println("My Home page Handler");
        model.addAttribute("name","Pankaj Kumar");
        model.addAttribute("company", "TCSL");
        //here we are returning the html page direct from templates folder
        return "home"; //home is home.html page present inside templates folder
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

}
