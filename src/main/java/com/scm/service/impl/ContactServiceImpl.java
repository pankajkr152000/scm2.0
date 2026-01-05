package com.scm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.scm.dto.ContactFormDTO;
import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.service.IContactService;

@Service
public class ContactServiceImpl implements IContactService{

    @Override
    public Contact createContact(User user, ContactFormDTO contactFormDTO) {
        
        

        throw new UnsupportedOperationException("Not supported yet.");
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

}
