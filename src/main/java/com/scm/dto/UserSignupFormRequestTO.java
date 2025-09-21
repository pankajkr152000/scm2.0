package com.scm.dto;

import com.scm.customAnnotation.ContactNumberValidator;
import com.scm.entities.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Request DTO for user signup operations.
 *
 * <p>
 * This class is used to transfer data from the client to the server
 * when a new user registers in the system. It is not an entity and is
 * not persisted in the database.
 * </p>
 *
 * <p>
 * <b>Attributes:</b>
 * </p>
 * <ol>
 * <li>{@code username} - {@link String} : Username or display name chosen by
 * the user</li>
 * <li>{@code email} - {@link String} : Email address of the user (required for
 * login)</li>
 * <li>{@code password} - {@link String} : Raw password entered by the user</li>
 * <li>{@code contactNumber} - {@link String} : Phone number of the user</li>
 * <li>{@code about} - {@link String} : Short bio or description provided by the
 * user (optional)</li>
 * </ol>
 *
 * <p>
 * Other notes:
 * </p>
 * <ul>
 * <li>Uses Lombok annotations {@code @Getter}, {@code @Setter},
 * {@code @NoArgsConstructor}, {@code @AllArgsConstructor}, {@code @Builder},
 * and {@code @ToString}.</li>
 * <li>Acts as a request object and should be validated before mapping to a
 * {@link Users} entity.</li>
 * <li>Typically used in controller endpoints handling signup/registration.</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserSignupFormRequestTO {

    /** Username or display name chosen by the user */
    @NotBlank(message="Name Required")
    @Size(min=3 , message="Minimum 3 characters")
    private String fullName;

    /** Email address of the user (required for login) */
    @Email(message="Invalid Email Address")
    @NotBlank(message="Email Required")
    private String email;

    /** Raw password entered by the user */
    @NotBlank(message="Password Required")
    @Size(min=6, max=16, message="Password length should be between 6 to 16 characters")
    private String password;

    /** Phone number of the user */
    @ContactNumberValidator
    @NotBlank(message="Contact Number Required")
    private String contactNumber;

    /** Short bio or description provided by the user */
    private String about;
}
