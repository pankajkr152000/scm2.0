package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.scm.contants.Providers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a user entity in the system.
 *
 * <p>This entity is mapped to the <b>users</b> table in the database.</p>
 *
 * <p><b>Attributes:</b></p>
 * <ol>
 *   <li>{@code userId}        - {@link String} : Unique identifier of the user (Primary Key)</li>
 *   <li>{@code firstName}     - {@link String} : First name of the user (not null)</li>
 *   <li>{@code lastName}      - {@link String} : Last name of the user (optional)</li>
 *   <li>{@code email}         - {@link String} : Email address of the user (not null)</li>
 *   <li>{@code isEmailVerified} - {@code boolean} : Flag to indicate whether the email is verified</li>
 *   <li>{@code password}      - {@link String} : Encrypted password (not null)</li>
 *   <li>{@code about}         - {@link String} : Short bio or description (optional)</li>
 *   <li>{@code contactNumber} - {@link String} : User's phone number (not null)</li>
 *   <li>{@code isContactNumberVerified} - {@code boolean} : Flag to indicate if the phone number is verified</li>
 *   <li>{@code profilePic}    - {@link String} : Path/URL to profile picture (optional)</li>
 *   <li>{@code provider}      - {@link Providers} : Authentication provider (default SELF)</li>
 *   <li>{@code providerUserId} - {@link String} : Provider-specific user ID (for third-party logins)</li>
 *   <li>{@code contactList}   - {@link java.util.List}{@code <Contacts>} : List of contacts belonging to the user</li>
 * </ol>
 *
 * <p>Other notes:</p>
 * <ul>
 *   <li>Uses Lombok annotations {@code @Getter}, {@code @Setter}, {@code @NoArgsConstructor}, {@code @AllArgsConstructor}.</li>
 *   <li>Maintains a one-to-many relationship with {@link Contacts}.</li>
 * </ul>
 */
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    /**
     * Unique identifier for the user.
     * Acts as the primary key in the database.
     */
    @Id
    private String userId;

    /**
     * First name of the user.
     * Cannot be {@code null}.
     */
    @Column(name = "firstName", nullable = false)
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;

    /**
     * Email address of the user.
     * Cannot be {@code null}.
     */
    @Column(nullable = false)
    private String email;

    /**
     * Indicates whether the user's email is verified.
     */
    private boolean isEmailVerified = false;

    /**
     * Encrypted password of the user.
     * Cannot be {@code null}.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Short description or bio about the user.
     */
    private String about;

    /**
     * Contact number of the user.
     * Cannot be {@code null}.
     */
    @Column(nullable = false)
    private String contactNumber;

    /**
     * Indicates whether the user's contact number is verified.
     */
    private boolean isContactNumberVerified = false;

    /**
     * Path or URL of the user's profile picture.
     */
    private String profilePic;

    /**
     * Provider used for authentication (e.g., SELF, GOOGLE, FACEBOOK).
     */
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;

    /**
     * Provider-specific user ID (used when logged in via social login providers).
     */
    private String providerUserId;

    /**
     * List of contacts associated with the user.
     * <p>
     * - One user can have multiple contacts.<br>
     * - Cascade operations are applied.<br>
     * - Lazy fetching is used for optimization.<br>
     * - Orphan contacts are removed automatically.
     * </p>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contacts> contactList = new ArrayList<>();
}

