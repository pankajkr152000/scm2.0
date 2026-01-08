package com.scm.service;

public interface IContactIdSequenceService {

    /**
     * Get next contact sequence number for a user
     * (DB-based, transactional, locked)
     */
    Long getNextSequence(String userId);

    /**
     * Initialize sequence for user (used during user creation)
     */
    void initializeSequenceForUser(String userId);

    /**
     * Get current sequence value (read-only)
     */
    Long getCurrentSequence(String userId);
}

