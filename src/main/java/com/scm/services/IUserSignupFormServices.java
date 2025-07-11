package com.scm.services;

import java.util.List;

import com.scm.entities.Users;

public interface IUserSignupFormServices {

    // save user request
    Users saveUser(Users user);
    
    // get the user by id
    Users getUserById(String id);

    // update the user
    Users updateUser(Users user);

    // delete user by id
    void deleteUser(String id);

    // is user exists by id
    boolean isUserExitsById(String id);

    // is user exist or not by using email
    boolean isUserExistsByEmail(String email);

    // get all users list
    List<Users> getAllUsers();
}
