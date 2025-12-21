package com.scm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.dto.ApiResponseDTO;
import com.scm.dto.UserSignupFormRequestDTO;
import com.scm.dto.UserSignupResponseDTO;
import com.scm.entity.User;
import com.scm.service.impl.UserSignupFormServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserSignupFormServiceImpl userSignupFormServiceImpl;

    public UserRestController(UserSignupFormServiceImpl userSignupFormServiceImpl) {
        this.userSignupFormServiceImpl = userSignupFormServiceImpl;
    }

    @GetMapping("/test")
    public String getMethodName() {
        return "Testing";
    }

    @PostMapping("/do-signup")
    public ApiResponseDTO<UserSignupResponseDTO> doSignup(@Valid @RequestBody UserSignupFormRequestDTO request) {
        //preValidation(request);
        
        User user = userSignupFormServiceImpl.createUser(request); // same service method

        // if user is created & saved into DB
        UserSignupResponseDTO userSignupResponseDTO = new UserSignupResponseDTO();
        if (user != null) {
            userSignupResponseDTO = UserSignupResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .build();
        }
        return new ApiResponseDTO<>("success", "User registered successfully!", userSignupResponseDTO);
    }

}
