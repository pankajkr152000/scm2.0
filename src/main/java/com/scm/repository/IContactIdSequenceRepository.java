package com.scm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.entity.ContactIdSequence;

import jakarta.persistence.LockModeType;

public interface IContactIdSequenceRepository {
    /* =====================================================
     * BASIC READ
     * ===================================================== */

    /**
     * Find sequence row by userId (WITHOUT lock)
     * Used for read-only operations
     */
    Optional<ContactIdSequence> findByUserId(String userId);


    /* =====================================================
     * LOCKED READ (CRITICAL)
     * ===================================================== */

    /**
     * Find sequence row by userId WITH row-level lock
     * Used while generating new contact ID
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT s FROM ContactIdSequence s
        WHERE s.userId = :userId
    """)
    Optional<ContactIdSequence> findByUserIdForUpdate(
            @Param("userId") String userId
    );


    /* =====================================================
     * EXISTENCE CHECK
     * ===================================================== */

    /**
     * Check if sequence exists for a user
     */
    boolean existsByUserId(String userId);


    /* =====================================================
     * BULK / ADMIN (FUTURE)
     * ===================================================== */

    /**
     * Fetch sequences for multiple users
     */
    List<ContactIdSequence> findByUserIdIn(List<String> userIds);


    /* =====================================================
     * REPORTING / DEBUG
     * ===================================================== */

    /**
     * Get current sequence value for a user
     */
    @Query("""
        SELECT s.currentValue
        FROM ContactIdSequence s
        WHERE s.userId = :userId
    """)
    Long getCurrentValueByUserId(
            @Param("userId") String userId
    );
}

