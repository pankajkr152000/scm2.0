package com.scm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.scm.constants.Providers;

@Service
public class CurrentUserService {
    final static Logger log = LoggerFactory.getLogger(CurrentUserService.class);
    @SuppressWarnings("null")
    public String getEmailOfLoggedinUser(Authentication authentication) {
        String loggedinEmail = null;
        if(authentication instanceof OAuth2AuthenticationToken oauth2AuthenticationToken) {
            // identify the provider
            var authorizedClientProviderName = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oAuth2User = (OAuth2User)authentication.getPrincipal();
            
            if(Providers.GOOGLE.valueOf().equalsIgnoreCase(authorizedClientProviderName)) {
                if(oAuth2User.getAttribute("email") != null) {
                    loggedinEmail = oAuth2User.getAttribute("email").toString();
                    log.info("Google Loggedin Email -> {}", loggedinEmail);
                }
            }
           else if(Providers.GITHUB.valueOf().equalsIgnoreCase(authorizedClientProviderName)) {
                if (oAuth2User.getAttributes().get("email") != null) {
                    loggedinEmail = oAuth2User.getAttributes().get("email").toString();
                    log.info("Github Logged in Email -> {}",loggedinEmail);
                } else if (oAuth2User.getAttributes().get("login") != null) {
                    loggedinEmail = oAuth2User.getAttributes().get("login").toString() + "@gmail.com";
                    log.info("Github Logged in Email -> {}", loggedinEmail);
                }
            }
        } else {
            loggedinEmail = authentication.getName();
            log.info("Email logged in email : {}", loggedinEmail);
        }


        return loggedinEmail;
    }
}
