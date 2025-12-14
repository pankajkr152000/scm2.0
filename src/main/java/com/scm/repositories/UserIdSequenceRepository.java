package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scm.entities.UserIdSequence;

@Repository
public interface UserIdSequenceRepository extends JpaRepository<UserIdSequence, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_id_sequence VALUES (NULL)", nativeQuery = true)
    void increment();

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getCurrentSequence();
}

