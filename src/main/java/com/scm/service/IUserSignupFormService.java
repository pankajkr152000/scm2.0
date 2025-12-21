package com.scm.service;

import java.util.List;
import java.util.Optional;

import com.scm.dto.UserSignupFormRequestDTO;
import com.scm.entity.User;

/**
 * Service interface for handling user signup and management operations.
 *
 * <p>This interface defines the core operations for managing {@link Users} entities
 * such as creating, retrieving, updating, and deleting users. It also provides
 * utility methods for checking user existence by ID or email.</p>
 *
 * <p><b>Methods:</b></p>
 * <ol>
 *   <li>{@link #saveUser(User)} : Saves a new user into the system.</li>
 *   <li>{@link #getUserById(String)} : Retrieves a user by their unique identifier.</li>
 *   <li>{@link #updateUser(Users)} : Updates an existing user’s details.</li>
 *   <li>{@link #deleteUser(String)} : Deletes a user from the system by ID.</li>
 *   <li>{@link #isUserExitsById(String)} : Checks if a user exists with the given ID.</li>
 *   <li>{@link #isUserExistsByEmail(String)} : Checks if a user exists with the given email.</li>
 *   <li>{@link #getAllUsers()} : Retrieves a list of all users.</li>
 * </ol>
 *
 * <p>Other notes:</p>
 * <ul>
 *   <li>Designed as a service interface to be implemented by concrete service classes.</li>
 *   <li>Encourages separation of concerns by abstracting business logic from controllers.</li>
 *   <li>Methods returning {@link Optional} are used for safe null handling.</li>
 * </ul>
 */
public interface IUserSignupFormService {

    /**
     * Saves a new user into the system.
     *
     * @param user the {@link Users} entity to be saved
     * @return the saved {@link Users} entity
     */
    User saveUser(User user);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> getUserById(String id);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param email the unique identifier of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Updates the details of an existing user.
     *
     * @param user the {@link Users} entity with updated details
     * @return an {@link Optional} containing the updated user if found, otherwise empty
     */
    Optional<User> updateUser(User user);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to be deleted
     */
    void deleteUser(String id);

    /**
     * Checks whether a user exists by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return {@code true} if the user exists, otherwise {@code false}
     */
    boolean isUserExitsById(String id);

    /**
     * Checks whether a user exists by their email address.
     *
     * @param email the email address of the user
     * @return {@code true} if a user exists with the given email, otherwise {@code false}
     */
    boolean isUserExistsByEmail(String email);
    
    /**
     * Checks whether a user exists by their email address.
     *
     * @param contactNumber the contactNumber of the user
     * @return {@code true} if a user exists with the given contactNumber, otherwise {@code false}
     */
    boolean isUserExistsByContactNumber(String contactNumber);


    /**
     * Retrieves all users from the system.
     *
     * @return a list of all {@link Users} entities
     */
    List<User> getAllUsers();
    
    /**
     * Create users from the system.
     *
     * @return a {@link Users} entities
     */
    User createUser(UserSignupFormRequestDTO request);
}

