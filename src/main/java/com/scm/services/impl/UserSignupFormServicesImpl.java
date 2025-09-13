package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.scm.config.QueryCaptureEventListener;
import com.scm.dto.UserSignupFormRequest;
import com.scm.entities.Users;
import com.scm.repositories.IUserRepositories;
import com.scm.services.IUserSignupFormServices;
import com.scm.services.helpers.ResourceNotFoundException;
import com.scm.utils.SCMDate;
import com.scm.utils.Utility;


// @Service // spring will find that it is a Service , spring will automatically creates an object of this class

/**
 * Implementation of {@link IUserSignupFormServices}.
 *
 * <p>This service class handles business logic related to user management, such as
 * saving, retrieving, updating, and deleting users. It interacts with the database
 * through the {@link IUserRepositories} repository.</p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Annotated with {@code @Service} so Spring can automatically detect and manage it.</li>
 *   <li>Uses {@code @Autowired} to inject the {@link IUserRepositories} dependency.</li>
 *   <li>Handles exceptions and logs events using SLF4J {@link Logger}.</li>
 * </ul>
 */
@Service
public class UserSignupFormServicesImpl implements IUserSignupFormServices {

    /**
     * Repository for interacting with the {@link Users} entity in the database.
     */
    private final IUserRepositories userRepository;
    public UserSignupFormServicesImpl(IUserRepositories userRepositories) {
        this.userRepository = userRepositories;
    }

    /**
     * Logger instance for logging important events and errors.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Saves a new user in the system.
     *
     * @param user the {@link Users} entity to save
     * @return the saved {@link Users} entity
     */
    @Override
    public Users saveUser(Users user) {
        log.info("Saving new user: {}", user.getEmail());
        //generate user id
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        //ecode Password
        //user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    @Override
    public Optional<Users> getUserById(String id) {
        log.debug("Fetching user with ID: {}", id);
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user’s details.
     *
     * @param newUser the {@link Users} entity with updated details
     * @return an {@link Optional} containing the updated user if successful
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public Optional<Users> updateUser(Users newUser) {
        log.info("Updating user with ID: {}", newUser.getUserId());

        Users existingUser = userRepository.findById(newUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        // update fields
        existingUser.setFirstName(newUser.getFirstName());
        existingUser.setLastName(newUser.getLastName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setEmailVerified(newUser.isEmailVerified());
        existingUser.setContactNumber(newUser.getContactNumber());
        existingUser.setContactNumberVerified(newUser.isContactNumberVerified());
        existingUser.setAbout(newUser.getAbout());
        existingUser.setPassword(newUser.getPassword());
        existingUser.setProfilePic(newUser.getProfilePic());
        existingUser.setProvider(newUser.getProvider());
        existingUser.setProviderUserId(newUser.getProviderUserId());

        //save  updated user into database
        Users updatedUser = userRepository.save(existingUser);

        return Optional.ofNullable(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the unique identifier of the user
     */
    @Override
    public void deleteUser(String id) {
        log.warn("Deleting user with ID: {}", id);
        // userRepository.deleteById(id);
        Users deleteUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        userRepository.delete(deleteUser);
    }

    /**
     * Checks whether a user exists by their ID.
     *
     * @param id the unique identifier of the user
     * @return {@code true} if the user exists, otherwise {@code false}
     */
    @Override
    public boolean isUserExitsById(String id) {
        return userRepository.existsById(id);
    }

    /**
     * Checks whether a user exists by their email address.
     *
     * @param email the email address of the user
     * @return {@code true} if a user with the given email exists, otherwise {@code false}
     */
    @Override
    public boolean isUserExistsByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Retrieves all users from the system.
     *
     * @return a list of all {@link Users} entities
     */
    @Override
    public List<Users> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Users createUser(UserSignupFormRequest request) {
        // commenting this bcz it did not populating the default data for provider
        // Users user = Users.builder()
        //         .firstName(Utility.firstNameFromString(request.getUsername()))
        //         .lastName(Utility.lastNameFromString(request.getUsername()))
        //         .email(request.getEmail())
        //         .password(request.getPassword())
        //         .contactNumber(request.getContactNumber())
        //         .about(request.getAbout())
        //         .profilePic("https://www.vectorstock.com/royalty-free-vector/avatar-photo-default-user-icon-picture-face-vector-48139643")
        //         .build();
        
        Users user = new Users();
        user.setFirstName(Utility.firstNameFromString(request.getUsername()));
        user.setLastName(Utility.lastNameFromString(request.getUsername()));
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setContactNumber(request.getContactNumber());
        user.setAbout(request.getAbout());
        user.setProfilePic("https://www.vectorstock.com/royalty-free-vector/avatar-photo-default-user-icon-picture-face-vector-48139643");
        user.setUserCreationRecordDate(SCMDate.getBusinessDate());
        System.out.println("User Saved : " + user);
        Users savedUser =  this.saveUser(user);
        // Users savedUser =  user;

        ////////  Generated SQL Query from Event Listenser and HTTPSession  ////////
        String sql = QueryCaptureEventListener.getLastQuery();
        String params = QueryCaptureEventListener.getLastParamsAsString();

        System.out.println("Captured SQL: " + sql);
        System.out.println("Captured Params: " + params);
        //////////                                           /////////////////////////
        return savedUser;
    }

    @Override
    public String toString() {
        return "UserSignupFormServicesImpl []";
    }
}

