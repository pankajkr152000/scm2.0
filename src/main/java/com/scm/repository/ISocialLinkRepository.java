package com.scm.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.Contact;
import com.scm.entity.SocialLink;

@Repository
public interface ISocialLinkRepository extends JpaRepository<SocialLink, Long> {

    /* ---------------- BASIC FETCH ---------------- */

    List<SocialLink> findByContact(Contact contact);

    Optional<SocialLink> findByIdAndContact(Long id, Contact contact);

    /* ---------------- EXISTENCE CHECKS ---------------- */

    boolean existsByContactAndLink(Contact contact, String link);

    boolean existsByContactAndTitle(Contact contact, String title);

    /* ---------------- DELETE OPERATIONS ---------------- */

    void deleteByContact(Contact contact);

    void deleteByIdAndContact(Long id, Contact contact);

    /* ---------------- SORTING & FILTERING ---------------- */

    List<SocialLink> findByContactOrderBySocialLinkRecordDateDesc(Contact contact);

    List<SocialLink> findByContactOrderByTitleAsc(Contact contact);

    /* ---------------- DATE BASED ---------------- */

    List<SocialLink> findByContactAndSocialLinkRecordDateAfter(
            Contact contact, Date date);

    List<SocialLink> findByContactAndSocialLinkRecordDateBetween(
            Contact contact, Date startDate, Date endDate);

    /* ---------------- SEARCH ---------------- */

    List<SocialLink> findByContactAndTitleContainingIgnoreCase(
            Contact contact, String title);
}

