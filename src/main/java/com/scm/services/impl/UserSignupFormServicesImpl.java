package com.scm.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.scm.config.UnifiedQueryCapture;
import com.scm.constants.AccountsRole;
import com.scm.constants.ExceptionCollection;
import com.scm.dto.UserSignupFormRequestTO;
import com.scm.entities.Users;
import com.scm.exception.AppRuntimeException;
import com.scm.exception.ResourceNotFoundException;
import com.scm.repositories.IUserRepositories;
import com.scm.services.IUserSignupFormServices;
import com.scm.services.UserIdGeneratorService;
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
    private final PasswordEncoder passwordEncoder;
    private final UserIdGeneratorService userIdGeneratorService;
    public UserSignupFormServicesImpl(IUserRepositories userRepositories, PasswordEncoder passwordEncoder, UserIdGeneratorService userIdGeneratorService) {
        this.userRepository = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.userIdGeneratorService = userIdGeneratorService;
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
    @Transactional
    @Override
    public Users saveUser(Users user) {

        if(isUserExistsByEmail(user.getEmail())) {
            log.error("Email already exists : {}", user.getEmail());
            throw new AppRuntimeException(ExceptionCollection.USER_EMAIL_ALREADY_EXISTS);
        }
        
        if(isUserExistsByContactNumber(user.getContactNumber())) {
            log.error("Contact Number already exists : {}", user.getContactNumber());
            throw new AppRuntimeException(ExceptionCollection.USER_PHONE_NUMBER_ALREADY_EXISTS);
        }

        log.info("Saving new user: {}", user.getEmail());

        //get last user id
        // Optional<String> lastUserIdOptional = userRepository.getLastSavedUserId();
        // String lastUserId = lastUserIdOptional.orElse(null);
        // lastUserIdOptional.ifPresent(id -> log.info("Last userId: {}", id));
        
        //generate new user id
        String id = userIdGeneratorService.generateUserId();
        user.setUserId(id);
        log.info("UserId of {} : {}", user.getEmail(), id);

        //ecode Password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // set user role
        user.setRoleList(List.of(AccountsRole.ROLE_USER.toString()));
        // set user creation time
        user.setUserCreationRecordDate(SCMDate.getBusinessDate());
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
                .orElseThrow(() -> new AppRuntimeException(ExceptionCollection.RESOURCE_NOT_FOUND));

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
     * Checks whether a user exists by their contactNumber.
     *
     * @param contactNumber the unique identifier of the user
     * @return {@code true} if the user contactNumber, otherwise {@code false}
     */
    @Override
    public boolean isUserExistsByContactNumber(String contactNumber) {

        Long count = userRepository.countByContactNumber(contactNumber);
         ////////  Generated SQL Query from Event Listenser and HTTPSession  ////////
        String sql = UnifiedQueryCapture.getLastQuery();
        String params = UnifiedQueryCapture.getLastParamsAsString();

        System.out.println("Captured SQL: " + sql);
        System.out.println("Captured Params: " + params);
        //////////                                           /////////////////////////
        return count > 0;
    }

    /**
     * Checks whether a user exists by their email address.
     *
     * @param email the email address of the user
     * @return {@code true} if a user with the given email exists, otherwise {@code false}
     */
    @Override
    public boolean isUserExistsByEmail(String email) {
        ////////  Generated SQL Query from Event Listenser and HTTPSession  ////////
       String sql = UnifiedQueryCapture.getLastQuery();
       String params = UnifiedQueryCapture.getLastParamsAsString();

       System.out.println("Captured SQL: " + sql);
       System.out.println("Captured Params: " + params);
       //////////                                           /////////////////////////

        boolean isUserExistBySameEmail = userRepository.findByEmail(email).isPresent();


        return isUserExistBySameEmail;
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

    @Transactional
    @Override
    public Users createUser(UserSignupFormRequestTO request) {
        Users user = new Users();
        user.setFirstName(Utility.firstNameFromString(request.getFullName()));
        user.setLastName(Utility.lastNameFromString(request.getFullName()));
        user.setEmail(request.getEmail().strip());
        user.setPassword(request.getPassword());
        user.setContactNumber(request.getContactNumber().strip());
        if(request.getAbout() != null && StringUtils.hasText(request.getAbout())) {
            user.setAbout(request.getAbout().strip());
        }
        user.setProfilePic("https://www.vectorstock.com/royalty-free-vector/avatar-photo-default-user-icon-picture-face-vector-48139643");
        //user.setUserCreationRecordDate(SCMDate.getBusinessDate());
        Users savedUser =  this.saveUser(user);
        // Users savedUser =  user;
        
        System.out.println("User Saved : " + user);

        ////////  Generated SQL Query from Event Listenser and HTTPSession  ////////
        String sql = UnifiedQueryCapture.getLastQuery();
        String params = UnifiedQueryCapture.getLastParamsAsString();

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

