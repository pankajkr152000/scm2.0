package com.scm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scm.constants.Gender;
import com.scm.constants.SCMConstants;
import com.scm.entity.Contact;
import com.scm.repository.IContactRepository;
import com.scm.utils.ImageUtil;

@Service
public class ContactImageService {

    private final IContactRepository contactRepository;
    private final ImageUtil imageUtil;

    private final Object LOCK = new Object(); // fallback lock

    public ContactImageService(IContactRepository contactRepository, ImageUtil imageUtil) {
        this.contactRepository = contactRepository;
        this.imageUtil = imageUtil;
    }

    public String saveProfileImage(MultipartFile file, Contact contact) throws IOException {
        // 1️⃣ Decide sequence (THREAD SAFE)
        long sequence;
        try {
            sequence = contactRepository.getNextImageSequence();
        } catch (Exception e) {
            // fallback (learning safety)
            synchronized (LOCK) {
                sequence = contactRepository.count() + 1;
            }
        }

        // 2️⃣ Create contact-specific folder
        String contactDir = "uploads/contacts/" + contact.getId();
        Files.createDirectories(Paths.get(contactDir));

        // 3️⃣ Generate filename
        String contactFullname = contact.getFirstName();
        if(contact.getLastName() != null)
            contactFullname +=  SCMConstants.SPACE + contact.getLastName();
        
        String fileName = imageUtil.buildFileName(contactFullname, sequence, file.getOriginalFilename());

        // 4️⃣ Save file
        Path target = Paths.get(contactDir, fileName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // 5️⃣ Return relative path (stored in DB)
        return "uploads/contacts/" + contact.getId() + "/" + fileName;
    }

    // DEFAULT AVATAR
    public String getProfileImageOrDefault(String imagePath, Gender gender) {
        if (imagePath == null || imagePath.isBlank()) {
            if(null == gender)
                return SCMConstants.DEFAULT_IMAGE;
            else return switch (gender) {
                case MALE -> SCMConstants.DEFAULT_MALE;
                case FEMALE -> SCMConstants.DEFAULT_FEMALE;
                default -> SCMConstants.DEFAULT_IMAGE;
            };
        }
        return "/" + imagePath;
    }
}

