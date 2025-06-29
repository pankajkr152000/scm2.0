package com.scm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestControllers {

    @GetMapping("/test")
    public String getMethodName() {
        return "Testing";
    }
    

}
