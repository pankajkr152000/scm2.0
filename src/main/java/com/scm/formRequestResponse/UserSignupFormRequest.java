package com.scm.formRequestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserSignupFormRequest {
    private String username;
    private String email;
    private String password;
    private String contactNumber;
    private String about;
}
