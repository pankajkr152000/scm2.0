package com.scm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scm.entity.ContactIdSequence;
import com.scm.repository.IContactIdSequenceRepository;
import com.scm.service.IContactIdSequenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactIdSequenceServiceImpl implements IContactIdSequenceService{

     /**
     * Logger instance for logging important events and errors.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final IContactIdSequenceRepository contactIdSequenceRepository;



    // ================= NEXT SEQUENCE =================
    @Override
    @Transactional
    public Long getNextSequence(String userId) {

        ContactIdSequence seq = contactIdSequenceRepository
                .findByUserIdForUpdate(userId)
                .orElseGet(() -> {
                    log.info("Creating sequence for user {}", userId);
                    ContactIdSequence s = new ContactIdSequence();
                    s.setUserId(userId);
                    s.setCurrentValue(0L);
                    return s;
                });

        Long next = seq.getCurrentValue() + 1;
        seq.setCurrentValue(next);
        contactIdSequenceRepository.save(seq);

        return next;
    }

    // ================= INITIALIZE =================
    @Override
    @Transactional
    public void initializeSequenceForUser(String userId) {

        if (!contactIdSequenceRepository.existsByUserId(userId)) {
            ContactIdSequence seq = new ContactIdSequence();
            seq.setUserId(userId);
            seq.setCurrentValue(0L);
            contactIdSequenceRepository.save(seq);

            log.info("Sequence initialized for user {}", userId);
        }
    }

    // ================= READ ONLY =================
    @Override
    @Transactional(readOnly = true)
    public Long getCurrentSequence(String userId) {
        return contactIdSequenceRepository.getCurrentValueByUserId(userId);
    }

}
