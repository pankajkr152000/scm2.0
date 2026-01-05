package com.scm.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.scm.dto.ContactFormDTO;
import com.scm.entity.Contact;
import com.scm.entity.User;

public interface IContactService {

    /* =====================================================
     * CREATE
     * ===================================================== */

    /**
     * Create a new contact for a user
     */
    Contact createContact(User user, ContactFormDTO contactFormDTO);

    /**
     * Save a new contact for a user
     */
    Contact saveContact(User user, Contact contact);


    /* =====================================================
     * READ (SINGLE)
     * ===================================================== */

    /**
     * Get a contact by its technical ID
     */
    Optional<Contact> getContactById(String contactId);

    /**
     * Get a contact by contact code (CNT-001)
     */
    Optional<Contact> getContactByContactCode(User user, String contactCode);


    /* =====================================================
     * READ (LIST)
     * ===================================================== */

    /**
     * Get all active contacts of a user
     */
    List<Contact> getAllActiveContacts(User user);

    /**
     * Get contacts with pagination and optional search
     */
    Page<Contact> getContacts(
            User user,
            String keyword,
            int page,
            int size
    );


    /* =====================================================
     * UPDATE
     * ===================================================== */

    /**
     * Update an existing contact
     */
    Contact updateContact(String contactId, Contact updatedContact);


    /* =====================================================
     * DELETE (SOFT DELETE)
     * ===================================================== */

    /**
     * Soft delete a contact
     */
    void deleteContact(String contactId);

    /**
     * Restore a soft-deleted contact
     */
    void restoreContact(String contactId);


    /* =====================================================
     * FAVORITES
     * ===================================================== */

    /**
     * Mark a contact as favorite
     */
    void markAsFavorite(String contactId);

    /**
     * Remove contact from favorites
     */
    void removeFromFavorite(String contactId);

    /**
     * Get all favorite contacts of a user
     */
    List<Contact> getFavoriteContacts(User user);


    /* =====================================================
     * COUNT / STATS
     * ===================================================== */

    /**
     * Count total active contacts of a user
     */
    long countActiveContacts(User user);

    /**
     * Count favorite contacts of a user
     */
    long countFavoriteContacts(User user);


    /* =====================================================
     * BULK / ADVANCED (FOR LATER)
     * ===================================================== */

    /**
     * Soft delete multiple contacts at once
     */
    void deleteContactsInBulk(List<String> contactIds);

    /**
     * Check if contact belongs to a user
     */
    boolean isContactOwnedByUser(String contactId, User user);
}

