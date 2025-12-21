package com.scm.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.constants.ErrorCodes;
import com.scm.exception.AppRuntimeException;
import com.scm.repository.IUserRepository;

@Service
public class SecurityCustomUserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepositories;

    public SecurityCustomUserDetailsServiceImpl(IUserRepository userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we have to load the user
        return userRepositories.findByEmail(username.toLowerCase())
                .orElseThrow(() -> new AppRuntimeException(ErrorCodes.USER_NOT_FOUND));
    }
}
