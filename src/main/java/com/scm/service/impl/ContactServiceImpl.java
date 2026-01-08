package com.scm.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.scm.constants.SCMConstants;
import com.scm.dto.ContactFormDTO;
import com.scm.entity.Contact;
import com.scm.entity.ContactIdSequence;
import com.scm.entity.SocialLink;
import com.scm.entity.User;
import com.scm.repository.IContactIdSequenceRepository;
import com.scm.service.IContactService;
import com.scm.service.ISocialLinkService;
import com.scm.utils.DateUtils;
import com.scm.utils.SCMUtilities;

@Service
public class ContactServiceImpl implements IContactService{

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);
    
    private static final int SEQ_LENGTH = 6;

    private final IContactIdSequenceRepository contactIdSequenceRepository;
    private final ISocialLinkService socialLinkService;

    public ContactServiceImpl(IContactIdSequenceRepository contactIdSequenceRepository, ISocialLinkService socialLinkService) {
        this.contactIdSequenceRepository = contactIdSequenceRepository;
        this.socialLinkService = socialLinkService;
    }

    @Override
    public void createContact(User user, ContactFormDTO contactFormDTO) {
        Contact contact = new Contact();
        populateContact(user, contactFormDTO, contact);

        saveContact(user, contact);
    }

    @Override
    public Contact saveContact(User user, Contact contact) {
        


        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Contact> getContactById(String contactId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Contact> getContactByContactCode(User user, String contactCode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getAllActiveContacts(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Page<Contact> getContacts(User user, String keyword, int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Contact updateContact(String contactId, Contact updatedContact) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteContact(String contactId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void restoreContact(String contactId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void markAsFavorite(String contactId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeFromFavorite(String contactId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getFavoriteContacts(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long countActiveContacts(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long countFavoriteContacts(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteContactsInBulk(List<String> contactIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isContactOwnedByUser(String contactId, User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void populateContact(User user, ContactFormDTO contactFormDTO, Contact contact) { 
        Long nextContactSequence = getNextContactSequence(user);
        contact.setContactSequence(nextContactSequence);

        String contactFirstName = SCMUtilities.firstNameFromString(contactFormDTO.getFullName());
        String contactLastName = SCMUtilities.lastNameFromString(contactFormDTO.getFullName());

        String prefix = contactFirstName.substring(0, 3).toUpperCase();
        String year = DateUtils.getYear();
        String paddedSeq = String.format("%0" + SEQ_LENGTH + "d", (nextContactSequence-1));
        contact.setContactCode(prefix + SCMConstants.UNDERSCORE + year + SCMConstants.UNDERSCORE + paddedSeq);

        contact.setFirstName(contactFirstName);
        contact.setLastName(contactLastName);
        if(contactFormDTO.getEmail() != null)
            contact.setEmail(contactFormDTO.getEmail().strip().toLowerCase());
        contact.setContactNumber(contactFormDTO.getContactNumber().strip());
        if(contactFormDTO.getDescription() != null && StringUtils.hasText(contactFormDTO.getDescription())) {
            contact.setDescription(contactFormDTO.getDescription().strip());
        }
        if(contactFormDTO.getDateOfBirth() != null) {
            LocalDate contactDateOfBirth = DateUtils.stringToDate(contactFormDTO.getDateOfBirth(), SCMConstants.DD_MM_UUUU);
            contact.setDateOfBirth(contactDateOfBirth);
        }
        if(contactFormDTO.getAddress() != null && StringUtils.hasText(contactFormDTO.getAddress())) {
            contact.setAddress(contactFormDTO.getAddress().strip());
        }
        contact.setGender(contactFormDTO.getGender());
        contact.setFavoriteContact(contactFormDTO.isFavoriteContact());

        // social links if the contact
        SocialLink websiteSocialLink = new SocialLink();
        websiteSocialLink.setTitle(contactFormDTO.getSocialLinks().get(0).getTitle());
        websiteSocialLink.setLink(contactFormDTO.getSocialLinks().get(0).getLink());
        socialLinkService.createSocialLink(websiteSocialLink, contact);

        SocialLink linkedInSocialLink = new SocialLink();
        linkedInSocialLink.setTitle(contactFormDTO.getSocialLinks().get(0).getTitle());
        linkedInSocialLink.setLink(contactFormDTO.getSocialLinks().get(0).getLink());
        socialLinkService.createSocialLink(linkedInSocialLink, contact);

        

    }

    /**
     * contact id next sequence 
     */
    @Transactional
    public Long getNextContactSequence(User user) {

        ContactIdSequence seq = contactIdSequenceRepository
            .findByUserId(user.getUserId())
            .orElseGet(() -> {
                ContactIdSequence s = new ContactIdSequence();
            s.setUserId(user.getUserId());
            s.setCurrentValue(0L);
            return s;
            });

        long next = seq.getCurrentValue() + 1;
        seq.setCurrentValue(next);
        //contactIdSequenceRepository.save(seq);
        return next;
    }

}
