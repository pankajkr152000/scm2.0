package com.scm.services.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.scm.constants.MessageType;
import com.scm.dto.UserSignupFormRequestTO;
import com.scm.services.impl.UserSignupFormServicesImpl;

@Component
public class ExistingUserAttributeCheck {

    private static final Logger log = LoggerFactory.getLogger(ExistingUserAttributeCheck.class);

    private final UserSignupFormServicesImpl userSignupServicesImpl;

    public ExistingUserAttributeCheck(UserSignupFormServicesImpl userSignupServicesImpl) {
        this.userSignupServicesImpl = userSignupServicesImpl;
    }

    /**
     * Checks duplicates and returns corresponding message type.
     * Returns null if no duplicates found.
     */
    public MessageType checkDuplicates(UserSignupFormRequestTO requestTO) {
        boolean emailExists = userSignupServicesImpl.isUserExistsByEmail(requestTO.getEmail());
        boolean contactExists = userSignupServicesImpl.isUserExistsByContactNumber(requestTO.getContactNumber());

        if (emailExists && contactExists) {
            log.warn("Email and Contact already exist: {}, {}", requestTO.getEmail(), requestTO.getContactNumber());
            return MessageType.EMAIL_AND_CONTACT_NUMBER_ALREADY_EXISTS;
        } else if (emailExists) {
            log.warn("Email already exists: {}", requestTO.getEmail());
            return MessageType.EMAIL_ALREADY_EXISTS;
        } else if (contactExists) {
            log.warn("Contact number already exists: {}", requestTO.getContactNumber());
            return MessageType.CONTACT_NUMBER_ALREADY_EXISTS;
        }
        return null; // ✅ no duplicates
    }
}
