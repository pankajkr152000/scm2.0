package com.scm.security.handlers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.constants.Providers;
import com.scm.entity.User;
import com.scm.service.impl.UserSignupFormServiceImpl;
import com.scm.utils.Utility;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final User userToStore = new User();

    final static Logger log = LoggerFactory.getLogger(OAuthSuccessHandler.class);

    private final UserSignupFormServiceImpl userSignupFormServiceImpl;
    public OAuthSuccessHandler(UserSignupFormServiceImpl userSignupFormServiceImpl) {
        this.userSignupFormServiceImpl = userSignupFormServiceImpl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("OAuthSuccessHandler");
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        // identify the provider
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientProviderName = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        log.info("provider name : {}", authorizedClientProviderName);

        // check for the attributes which we can get from the User
        // user.getAttributes().forEach((key, value) -> {
        // log.info("{} -> {}", key, value);
        // });
        // user.getAuthorities().forEach((value) -> {
        // log.info("Authority -> {}", value);
        // });

        // before redirect , data must be saved into database
        // Users userToStore = new Users();
        if (Providers.GOOGLE.valueOf().equalsIgnoreCase(authorizedClientProviderName)) {
            populateGoogleuser(user);

        } else if (Providers.GITHUB.valueOf().equalsIgnoreCase(authorizedClientProviderName)) {
            populateGithubUser(user);

        } else if (Providers.FACEBOOK.valueOf().equalsIgnoreCase(authorizedClientProviderName)) {

        } else if (Providers.LINKEDIN.valueOf().equalsIgnoreCase(authorizedClientProviderName)) {

        } else {
            log.info("Unknown OauthProvider");
        }

        // check for user with this email is already exists or not
        String userToStoreEmail = userToStore.getEmail().toLowerCase();
        boolean isUserEmailPresent = userSignupFormServiceImpl.isUserExistsByEmail(userToStoreEmail);
        if(!isUserEmailPresent) {
            userSignupFormServiceImpl.saveUser(userToStore);
            log.info("User saved : {}", userToStoreEmail);
        } 
        // save the user into database

        // redirect to user dashdashboard after successful login
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

    // populate the google user
    public void populateGoogleuser(DefaultOAuth2User user) {
        if (user.getAttributes().get("given_name") != null) {
            userToStore.setFirstName(user.getAttributes().get("given_name").toString());
            log.info("First name -> {}", user.getAttributes().get("given_name").toString());
        }

        if (user.getAttributes().get("family_name") != null) {
            userToStore.setLastName(user.getAttributes().get("family_name").toString());
            log.info("Last Name -> {}", user.getAttributes().get("family_name").toString());
        }
        if (user.getAttributes().get("email") != null) {
            userToStore.setEmail(user.getAttributes().get("email").toString());
            log.info("Email -> {}", user.getAttributes().get("email").toString());
        }
        if (user.getAttributes().get("picture") != null) {
            userToStore.setProfilePic(user.getAttributes().get("picture").toString());
            log.info("Profile -> {}", user.getAttributes().get("picture").toString());
        }
        if (user.getName() != null) {
            userToStore.setProviderUserId(user.getName());
            log.info("Provider User id -> {}", user.getName());
        }
        userToStore.setProvider(Providers.GOOGLE);
        userToStore.setEmailVerified(true);
        userToStore.setAbout("This user is created from GOOGLE");
        userToStore.setEnabled(true);
        userToStore.setPassword("password");

        // userToStore.setUserCreationRecordDate(SCMDate.getBusinessDate());
        // userToStore.setUserId(authorizedClientProviderName);
        // userToStore.setRoleList();
    }

    // populate Github User
    public void populateGithubUser(DefaultOAuth2User user) {
        if (user.getAttributes().get("name") != null) {
            userToStore.setFirstName(Utility.firstNameFromString(user.getAttributes().get("name").toString()));
            log.info("First name -> {}", Utility.firstNameFromString(user.getAttributes().get("name").toString()));
        } else if (user.getAttributes().get("login") != null) {
            userToStore.setFirstName(Utility.firstNameFromString(user.getAttributes().get("login").toString()));
            log.info("First name -> {}", Utility.firstNameFromString(user.getAttributes().get("login").toString()));
        }

        if (user.getAttributes().get("name") != null) {
            userToStore.setLastName(Utility.lastNameFromString(user.getAttributes().get("name").toString()));
            log.info("Last Name -> {}", Utility.lastNameFromString(user.getAttributes().get("name").toString()));
        }
        if (user.getAttributes().get("email") != null) {
            userToStore.setEmail(user.getAttributes().get("email").toString());
            log.info("Email -> {}", user.getAttributes().get("email").toString());
        } else if (user.getAttributes().get("login") != null) {
            userToStore.setEmail(user.getAttributes().get("login").toString() + "@gmail.com");
            log.info("Email -> {}", (user.getAttributes().get("login").toString() + "@gmail.com"));
        }

        if (user.getAttributes().get("avatar_url") != null) {
            userToStore.setProfilePic(user.getAttributes().get("avatar_url").toString());
            log.info("Profile -> {}", user.getAttributes().get("avatar_url").toString());
        }
        if (user.getName() != null) {
            userToStore.setProviderUserId(user.getName());
            log.info("Provider User id -> {}", user.getName());
        }
        userToStore.setProvider(Providers.GITHUB);
        userToStore.setEmailVerified(true);
        userToStore.setAbout("This user is created from GITHUB");
        userToStore.setEnabled(true);
        userToStore.setPassword("password");
    }
}
