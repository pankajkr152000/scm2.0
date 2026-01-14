package com.scm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.scm.entity.Contact;
import com.scm.entity.SocialLink;
import com.scm.repository.ISocialLinkRepository;
import com.scm.service.ISocialLinkService;
import com.scm.utils.DateUtils;

@Service
public class SocialLinkServiceImpl implements ISocialLinkService{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ISocialLinkRepository socialLinkRepository;

    public SocialLinkServiceImpl(ISocialLinkRepository socialLinkRepository) {
        this.socialLinkRepository = socialLinkRepository;
    }

    @Override
    public void createSocialLink(SocialLink socialLink, Contact contact) {
        socialLink.setContact(contact);
        saveSocialLink(socialLink);
    }

    @Override
    public void saveSocialLink(SocialLink socialLink) {
        socialLink.setSocialLinkRecordDate(DateUtils.getBusinessDate());
        socialLinkRepository.save(socialLink);
    }

    @Override
    public SocialLink updateSocialLink(SocialLink socialLink) {
        throw new UnsupportedOperationException("Unimplemented method 'updateSocialLink'");
    }

    @Override
    public List<SocialLink> saveAllSocialLinks(List<SocialLink> socialLinks) {
        throw new UnsupportedOperationException("Unimplemented method 'saveAllSocialLinks'");
    }

    @Override
    public Optional<SocialLink> getSocialLinkById(Long socialLinkId) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinkById'");
    }

    @Override
    public Optional<SocialLink> getSocialLinkByIdAndContact(Long socialLinkId, Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinkByIdAndContact'");
    }

    @Override
    public List<SocialLink> getAllSocialLinks() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllSocialLinks'");
    }

    @Override
    public List<SocialLink> getSocialLinksByContact(Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinksByContact'");
    }

    @Override
    public boolean socialLinkExists(Contact contact, String link) {
        throw new UnsupportedOperationException("Unimplemented method 'socialLinkExists'");
    }

    @Override
    public boolean socialLinkTitleExists(Contact contact, String title) {
        throw new UnsupportedOperationException("Unimplemented method 'socialLinkTitleExists'");
    }

    @Override
    public void deleteSocialLinkById(Long socialLinkId) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteSocialLinkById'");
    }

    @Override
    public void deleteSocialLinkByIdAndContact(Long socialLinkId, Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteSocialLinkByIdAndContact'");
    }

    @Override
    public void deleteAllSocialLinksByContact(Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllSocialLinksByContact'");
    }

    @Override
    public List<SocialLink> getSocialLinksSortedByDate(Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinksSortedByDate'");
    }

    @Override
    public List<SocialLink> getSocialLinksSortedByTitle(Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinksSortedByTitle'");
    }

    @Override
    public List<SocialLink> getSocialLinksAddedAfter(Contact contact, Date date) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinksAddedAfter'");
    }

    @Override
    public List<SocialLink> getSocialLinksAddedBetween(Contact contact, Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Unimplemented method 'getSocialLinksAddedBetween'");
    }

    @Override
    public List<SocialLink> searchSocialLinksByTitle(Contact contact, String keyword) {
        throw new UnsupportedOperationException("Unimplemented method 'searchSocialLinksByTitle'");
    }

    @Override
    public long countSocialLinksByContact(Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'countSocialLinksByContact'");
    }

}
