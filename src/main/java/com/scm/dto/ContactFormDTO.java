package com.scm.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scm.constants.Gender;
import com.scm.validation.annotation.ContactNumberValidator;
import com.scm.validation.annotation.ValidDOBString;
import com.scm.validation.annotation.ValidImageFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactFormDTO {

    /** Username or display name chosen by the user */
    @NotBlank(message="Name Required")
    @Size(min=3 , message="Minimum 3 characters")
    private String fullName;

    /** Phone number of the contact */
    @ContactNumberValidator
    @NotBlank(message="Contact Number Required")
    @Size(min=10, max=10)
    private String contactNumber;

    /** Email address of the contact */
    @Email(message="Invalid Email Address")
    private String email;
    
    @ValidDOBString(message="Invalid DOB")
    private String dateOfBirth;

    @Size(max=100, message="Your contact address is exceeding!!")
    private String address;

    @Size(max=100, message="Your contact description is exceeding!!")
    private String description;

    @Builder.Default
    private boolean favoriteContact = true;

    private String image;

    @Builder.Default
    // 👇 OPTIONAL (default MALE)
    private Gender gender = Gender.UNKNOWN;


    // 👇 IMPORTANT
    //@ValidProfileLink(message="Invalid/Broken Profile Link")
    @Builder.Default
    private List<SocialLinkDTO> socialLinks = new ArrayList<>();


}

