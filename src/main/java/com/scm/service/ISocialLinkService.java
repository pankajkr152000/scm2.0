package com.scm.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.scm.entity.Contact;
import com.scm.entity.SocialLink;

public interface ISocialLinkService {

    /* ---------------- CREATE & UPDATE ---------------- */

    void createSocialLink(SocialLink socialLink, Contact contact);

    SocialLink updateSocialLink(SocialLink socialLink);

    void saveSocialLink(SocialLink socialLink);

    List<SocialLink> saveAllSocialLinks(List<SocialLink> socialLinks);

    /* ---------------- FETCH ---------------- */

    Optional<SocialLink> getSocialLinkById(Long socialLinkId);

    Optional<SocialLink> getSocialLinkByIdAndContact(Long socialLinkId, Contact contact);

    List<SocialLink> getAllSocialLinks();

    List<SocialLink> getSocialLinksByContact(Contact contact);

    /* ---------------- EXISTENCE ---------------- */

    boolean socialLinkExists(Contact contact, String link);

    boolean socialLinkTitleExists(Contact contact, String title);

    /* ---------------- DELETE ---------------- */

    void deleteSocialLinkById(Long socialLinkId);

    void deleteSocialLinkByIdAndContact(Long socialLinkId, Contact contact);

    void deleteAllSocialLinksByContact(Contact contact);

    /* ---------------- SORTING ---------------- */

    List<SocialLink> getSocialLinksSortedByDate(Contact contact);

    List<SocialLink> getSocialLinksSortedByTitle(Contact contact);

    /* ---------------- DATE FILTER ---------------- */

    List<SocialLink> getSocialLinksAddedAfter(Contact contact, Date date);

    List<SocialLink> getSocialLinksAddedBetween(
            Contact contact, Date startDate, Date endDate);

    /* ---------------- SEARCH ---------------- */

    List<SocialLink> searchSocialLinksByTitle(
            Contact contact, String keyword);

    /* ---------------- COUNT ---------------- */

    long countSocialLinksByContact(Contact contact);

    
}
