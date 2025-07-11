package com.scm.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scm.entities.Users;
import com.scm.services.IUserSignupFormServices;


@Service
public class UserSignupFormServicesImpl implements IUserSignupFormServices {

    @Override
    public Users saveUser(Users user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Users getUserById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Users updateUser(Users user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteUser(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isUserExitsById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Users> getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "UserSignupFormServicesImpl []";
    }

}
