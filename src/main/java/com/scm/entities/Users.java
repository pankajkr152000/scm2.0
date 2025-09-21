package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scm.constants.Providers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {

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
    @Builder.Default
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
    @Builder.Default
    private boolean isContactNumberVerified = false;

    /**
     * Indicates whether the user is active or inactive.
     */
    @Builder.Default
    private boolean isEnabled = true;

    /**
     * Path or URL of the user's profile picture.
     */
    private String profilePic;

    /**
     * Provider used for authentication (e.g., SELF, GOOGLE, FACEBOOK).
     */
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;

    /**
     * Provider-specific user ID (used when logged in via social login providers).
     */
    private String providerUserId;

    /**
     * store the time of user creation
     */
    private Date userCreationRecordDate;

    /**
     * List of contacts associated with the user.
     * <p>
     * - One user can have multiple contacts.<br>
     * - Cascade operations are applied.<br>
     * - Lazy fetching is used for optimization.<br>
     * - Orphan contacts are removed automatically.
     * </p>
     */
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contacts> contactList = new ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // List of roles[USER,ADMIN]
        // collection of SimpleGrantedAuthority[roles{ADMIN, USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
