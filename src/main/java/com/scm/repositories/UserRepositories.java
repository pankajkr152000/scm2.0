package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.Users;


@Repository
// repository is used to interact with database
// JpaRepository takes two arguments [1.] entity name [2.] entity primary key id datatype
public interface  UserRepositories extends JpaRepository<Users, String> {
    // extra methods db relatedoperations
    // custom query methods
    // custom finder methods
    // 

}
