package com.scm.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.constants.ExceptionCollection;
import com.scm.exception.AppRuntimeException;
import com.scm.repositories.IUserRepositories;

@Service
public class SecurityCustomUserDetailsService implements UserDetailsService {

    private final IUserRepositories userRepositories;

    public SecurityCustomUserDetailsService(IUserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we have to load the user
        return userRepositories.findByEmail(username)
                .orElseThrow(() -> new AppRuntimeException(ExceptionCollection.USER_NOT_FOUND));
    }
}
