package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.Users;


// @Repository
// // repository is used to interact with database
// // JpaRepository takes two arguments [1.] entity name [2.] entity primary key id datatype
// public interface  IUserRepositories extends JpaRepository<Users, String> {
//     // extra methods db relatedoperations
//     // custom query methods
//     // custom finder methods
//     // 

    
    // /**
    // //  * @param user
    // //  * @return {@code Users}
    // //  */
    // Users saveUser(Users user);

    // Users findByEmail(String email);

// }


/**
 * Repository interface for {@link Users} entity.
 *
 * <p>This repository extends {@link JpaRepository} to provide built-in CRUD operations 
 * and query execution on the <b>users</b> table in the database.</p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Extends {@code JpaRepository<Users, String>} where {@code Users} is the entity 
 *       and {@code String} is the primary key type.</li>
 *   <li>Provides default CRUD methods like {@code save()}, {@code findById()}, 
 *       {@code findAll()}, {@code deleteById()}, etc.</li>
 *   <li>Can define additional custom finder methods following Spring Data JPA naming conventions.</li>
 * </ul>
 *
 * <p><b>Custom Methods:</b></p>
 * <ul>
 *   <li>{@link #findByEmail(String)} : Finds a user by their email address.</li>
 * </ul>
 */
@Repository
public interface IUserRepositories extends JpaRepository<Users, String> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user
     * @return the {@link Users} entity if found, otherwise {@code null}
     */
    Users findByEmail(String email);
}

