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
import com.scm.dto.SocialLinkDTO;
import com.scm.entity.Contact;
import com.scm.entity.ContactIdSequence;
import com.scm.entity.GlobalContactSequence;
import com.scm.entity.SocialLink;
import com.scm.entity.User;
import com.scm.repository.IContactIdSequenceRepository;
import com.scm.repository.IContactRepository;
import com.scm.repository.IGlobalContactSequenceRepository;
import com.scm.service.IContactService;
import com.scm.utils.DateUtils;
import com.scm.utils.SCMUtilities;

@Service
public class ContactServiceImpl implements IContactService{

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);
    
    private static final int SEQ_LENGTH = 6;

    private final IContactIdSequenceRepository contactIdSequenceRepository;
    private final IGlobalContactSequenceRepository globalContactSequenceRepository;
    private final IContactRepository contactRepository;
    

    public ContactServiceImpl(IContactIdSequenceRepository contactIdSequenceRepository, IContactRepository contactRepository,
                                IGlobalContactSequenceRepository globalContactSequenceRepository) {
        this.contactIdSequenceRepository = contactIdSequenceRepository;
        this.contactRepository = contactRepository;
        this.globalContactSequenceRepository = globalContactSequenceRepository;
    }

    @Transactional
    @Override
    public Contact createContact(User user, ContactFormDTO contactFormDTO) {
        Contact contact = new Contact();
        populateContact(user, contactFormDTO, contact);
        // contactRepository.save(contact); // MUST happen first

        // image handling must NOT throw unchecked exception
        // if (imageFile != null && !imageFile.isEmpty()) {
        //     try {
        //         String path = contactImageService.saveProfileImage(imageFile, contact);
        //         contact.setPicture(path);
        //         contactRepository.save(contact);
        //     } catch (IOException e) {
        //         // OPTION 1: rethrow → rollback (clean)
        //         throw new RuntimeException("Image upload failed", e);

        //         // OPTION 2 (if image optional):
        //         // log.warn("Image upload failed", e);
        //     }
        // }

        contactRepository.save(contact); // update picture path
        return contact;
    }

    @Override
    public void saveContact(User user, Contact contact) {

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
        // 1️⃣ GLOBAL SEQUENCE (100% UNIQUE)
        GlobalContactSequence globalSeq = globalContactSequenceRepository.save(new GlobalContactSequence());
        long globalContactSeq = globalSeq.getId();

        // 2️⃣ PER-USER SEQUENCE
        Long nextPerUserContactSequence = getPerUserNextContactSequence(user);
        contact.setContactSequence(nextPerUserContactSequence);

        String contactFirstName = SCMUtilities.firstNameFromString(contactFormDTO.getFullName());
        String contactLastName = SCMUtilities.lastNameFromString(contactFormDTO.getFullName());

        // 3️⃣ NAME PREFIX
        String prefix = contactFirstName.length() >= 3 ? contactFirstName.substring(0, 3).toUpperCase() : contactFirstName.toUpperCase();

         // 4️⃣ CONTACT CODE
        String contactCode = String.format("%s/%07d/%06d", prefix, globalContactSeq, nextPerUserContactSequence);
        contact.setContactCode(contactCode);

        contact.setFirstName(contactFirstName);
        contact.setLastName(contactLastName);
        if(contactFormDTO.getEmail() != null)
            contact.setEmail(contactFormDTO.getEmail().strip().toLowerCase());
        contact.setContactNumber(contactFormDTO.getContactNumber().strip());
        if(contactFormDTO.getDescription() != null && StringUtils.hasText(contactFormDTO.getDescription())) {
            contact.setDescription(contactFormDTO.getDescription().strip());
        }
        if(contactFormDTO.getDateOfBirth() != null && StringUtils.hasText(contactFormDTO.getDateOfBirth())) {
            LocalDate contactDateOfBirth = DateUtils.stringToDate(contactFormDTO.getDateOfBirth(), SCMConstants.DD_MM_UUUU);
            contact.setDateOfBirth(contactDateOfBirth);
        }
        if(contactFormDTO.getAddress() != null && StringUtils.hasText(contactFormDTO.getAddress())) {
            contact.setAddress(contactFormDTO.getAddress().strip());
        }
        contact.setGender(contactFormDTO.getGender());
        contact.setFavoriteContact(contactFormDTO.isFavoriteContact());

        if (contactFormDTO.getSocialLinks() != null) {
            for (SocialLinkDTO dto : contactFormDTO.getSocialLinks()) {
                if(dto.getLink() != null && StringUtils.hasText(dto.getLink())) {
                    SocialLink link = SocialLink.builder()
                            .title(dto.getTitle())
                            .link(dto.getLink())
                            .socialLinkRecordDate(DateUtils.getBusinessDate())
                            .build();

                    contact.addSocialLink(link); // ✅ IMPORTANT
                }
            }
        }

        contact.setContactAdditionRecordDate(DateUtils.getBusinessDate());
        contact.setUser(user);
        if(contactFormDTO.getSocialLinks() != null){
            if(contactFormDTO.getSocialLinks().get(0).getLink() != null && StringUtils.hasText(contactFormDTO.getSocialLinks().get(0).getLink()))
                contact.setWebsiteLink(contactFormDTO.getSocialLinks().get(0).getLink());
            if(contactFormDTO.getSocialLinks().get(1).getLink() != null && StringUtils.hasText(contactFormDTO.getSocialLinks().get(1).getLink()))
                contact.setLinkedInLink(contactFormDTO.getSocialLinks().get(1).getLink());
        }

    }

    /**
     * contact id next sequence 
     */
   @Transactional
    public Long getPerUserNextContactSequence(User user) {

        ContactIdSequence seq = contactIdSequenceRepository
            .findByUserId(user.getUserId())
            .orElseGet(() -> {
                ContactIdSequence s = new ContactIdSequence();
                s.setUserId(user.getUserId());
                s.setCurrentValue(0L);
                return contactIdSequenceRepository.save(s); // ✅ SAVE HERE
            });

        long perUserNextSeq = seq.getCurrentValue() + 1;
        seq.setCurrentValue(perUserNextSeq);

        contactIdSequenceRepository.save(seq); // ✅ SAVE UPDATE
        return perUserNextSeq;
    }


}
