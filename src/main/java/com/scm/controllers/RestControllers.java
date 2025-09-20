package com.scm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.dto.UserSignupFormRequestTO;
import com.scm.entities.Users;
import com.scm.exception.ApiResponse;
import com.scm.services.IUserSignupFormServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RestControllers {

    private final IUserSignupFormServices userSignupFormServices;

    public RestControllers(IUserSignupFormServices userSignupFormServices) {
        this.userSignupFormServices = userSignupFormServices;
    }

    @GetMapping("/test")
    public String getMethodName() {
        return "Testing";
    }

    @PostMapping("/do-signup")
    public ApiResponse<Users> doSignup(@Valid @RequestBody UserSignupFormRequestTO request) {
        //preValidation(request);
        
        Users user = userSignupFormServices.createUser(request); // same service method
        return new ApiResponse<>("success", "User registered successfully!", user);
    }

}
