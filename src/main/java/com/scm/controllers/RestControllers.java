package com.scm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.dto.UserSignupFormRequestTO;
import com.scm.dto.UserSignupResponseDTO;
import com.scm.entities.Users;
import com.scm.exception.ApiResponse;
import com.scm.services.impl.UserSignupFormServicesImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RestControllers {

    private final UserSignupFormServicesImpl userSignupFormServicesImpl;

    public RestControllers(UserSignupFormServicesImpl userSignupFormServicesImpl) {
        this.userSignupFormServicesImpl = userSignupFormServicesImpl;
    }

    @GetMapping("/test")
    public String getMethodName() {
        return "Testing";
    }

    @PostMapping("/do-signup")
    public ApiResponse<UserSignupResponseDTO> doSignup(@Valid @RequestBody UserSignupFormRequestTO request) {
        //preValidation(request);
        
        Users user = userSignupFormServicesImpl.createUser(request); // same service method

        // if user is created & saved into DB
        UserSignupResponseDTO userSignupResponseDTO = new UserSignupResponseDTO();
        if (user != null) {
            userSignupResponseDTO = UserSignupResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .build();
        }
        return new ApiResponse<>("success", "User registered successfully!", userSignupResponseDTO);
    }

}
