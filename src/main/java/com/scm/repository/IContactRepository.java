package com.scm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entity.Contact;
import com.scm.entity.User;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> {

    /* =====================================================
     * BASIC READ
     * ===================================================== */

    Optional<Contact> findByIdAndIsDeletedFalse(Long id);

    Optional<Contact> findByUserAndContactCodeAndIsDeletedFalse(
            User user,
            String contactCode
    );

    List<Contact> findByUserAndIsDeletedFalse(User user);


    /* =====================================================
     * PAGINATION
     * ===================================================== */

    Page<Contact> findByUserAndIsDeletedFalse(
            User user,
            Pageable pageable
    );


    /* =====================================================
     * SEARCH
     * ===================================================== */

    @Query("""
        SELECT c FROM Contact c
        WHERE c.user = :user
          AND c.isDeleted = false
          AND (
               lower(c.firstName) LIKE lower(concat('%', :keyword, '%'))
            OR lower(c.lastName) LIKE lower(concat('%', :keyword, '%'))
            OR c.contactNumber LIKE concat('%', :keyword, '%')
          )
    """)
    Page<Contact> searchActiveContacts(
            @Param("user") User user,
            @Param("keyword") String keyword,
            Pageable pageable
    );


    /* =====================================================
     * FAVORITES
     * ===================================================== */

    List<Contact> findByUserAndIsFavoriteContactTrueAndIsDeletedFalse(User user);

    long countByUserAndIsFavoriteContactTrueAndIsDeletedFalse(User user);


    /* =====================================================
     * COUNTS / STATS
     * ===================================================== */

    long countByUserAndIsDeletedFalse(User user);

    long countByUserAndIsDeletedTrue(User user);

    long countByUserAndIsDeletedFalseAndIsFavoriteContactTrue(User user);

    long countByUser(User user);

    @Query(value = "SELECT nextval('CONTACT_IMAGE_SEQ')", nativeQuery = true)
    long getNextImageSequence();


    /* =====================================================
     * SOFT DELETE / RESTORE
     * ===================================================== */

    List<Contact> findByIdIn(List<Long> ids);

    /**
     * get the deleted contact of the user
     */
    Page<Contact> findByUserAndIsDeletedTrue(User user, Pageable pageable);

    /* =====================================================
     * OWNERSHIP / SECURITY
     * ===================================================== */

    boolean existsByIdAndUser(Long contactId, User user);

    boolean existsByIdAndUserAndIsDeletedFalse(Long contactId, User user);


    /* =====================================================
     * SEQUENCE / FUTURE REDIS SUPPORT
     * ===================================================== */

    @Query("""
        SELECT MAX(c.contactSequence)
        FROM Contact c
        WHERE c.user = :user
    """)
    Long findMaxContactSequenceByUser(@Param("user") User user);


    /* =====================================================
     * BULK / ADMIN / REPORTING (FUTURE)
     * ===================================================== */

    List<Contact> findByUser(User user);

    Page<Contact> findByUser(User user, Pageable pageable);

    /* =========================
       SEARCH : ALL CONTACTS
       ========================= */
    @Query("""
        SELECT c FROM Contact c
        WHERE c.isDeleted = false
        AND (
            LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))
            OR c.contactNumber LIKE CONCAT('%', :query, '%')
        )
    """)
    Page<Contact> searchAll(
            @Param("query") String query,
            Pageable pageable
    );

    /* =========================
       SEARCH : ACTIVE CONTACTS
       ========================= */
    @Query("""
        SELECT c FROM Contact c
        WHERE c.isDeleted = false
        AND c.user = :user
        AND c.isActive = true
        AND (
            LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))
            OR c.contactNumber LIKE CONCAT('%', :query, '%')
        )
    """)
    Page<Contact> searchActive(
            @Param("user") User user,
            @Param("query") String query,
            Pageable pageable
    );

    /* =========================
       SEARCH : FAVORITE CONTACTS
       ========================= */
    @Query("""
        SELECT c FROM Contact c
        WHERE c.isDeleted = false
        AND c.user = :user
        AND c.isFavoriteContact = true
        AND (
            LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))
            OR c.contactNumber LIKE CONCAT('%', :query, '%')
        )
    """)
    Page<Contact> searchFavorite(
            @Param("user") User user,
            @Param("query") String query,
            Pageable pageable
    );

// ✅ (STRONGLY RECOMMENDED) — User-scoped search
// Since contacts belong to a user, you should never return another user’s contacts.

// 🔒 Safer version (with user filter)

        @Query("""
        SELECT c FROM Contact c
        WHERE c.isDeleted = false
        AND c.user = :user
        AND (
                LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%'))
                OR c.contactNumber LIKE CONCAT('%', :query, '%')
        )
        """)
        Page<Contact> searchAllByUser(
                @Param("user") User user,
                @Param("query") String query,
                Pageable pageable
        );



}
