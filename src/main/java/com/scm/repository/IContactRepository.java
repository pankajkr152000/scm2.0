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

    Optional<Contact> findByIdAndDeletedFalse(Long id);

    Optional<Contact> findByUserAndContactCodeAndDeletedFalse(
            User user,
            String contactCode
    );

    List<Contact> findByUserAndDeletedFalse(User user);


    /* =====================================================
     * PAGINATION
     * ===================================================== */

    Page<Contact> findByUserAndDeletedFalse(
            User user,
            Pageable pageable
    );


    /* =====================================================
     * SEARCH
     * ===================================================== */

    @Query("""
        SELECT c FROM contacts c
        WHERE c.user = :user
          AND c.deleted = false
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

    List<Contact> findByUserAndIsFavoriteContactTrueAndDeletedFalse(User user);

    long countByUserAndIsFavoriteContactTrueAndDeletedFalse(User user);


    /* =====================================================
     * COUNTS / STATS
     * ===================================================== */

    long countByUserAndDeletedFalse(User user);

    long countByUser(User user);


    /* =====================================================
     * SOFT DELETE / RESTORE
     * ===================================================== */

    List<Contact> findByIdIn(List<Long> ids);


    /* =====================================================
     * OWNERSHIP / SECURITY
     * ===================================================== */

    boolean existsByIdAndUser(Long contactId, User user);

    boolean existsByIdAndUserAndDeletedFalse(Long contactId, User user);


    /* =====================================================
     * SEQUENCE / FUTURE REDIS SUPPORT
     * ===================================================== */

    @Query("""
        SELECT MAX(c.contactSequence)
        FROM contacts c
        WHERE c.user = :user
    """)
    Long findMaxContactSequenceByUser(@Param("user") User user);


    /* =====================================================
     * BULK / ADMIN / REPORTING (FUTURE)
     * ===================================================== */

    List<Contact> findByUser(User user);

    Page<Contact> findByUser(User user, Pageable pageable);

}
