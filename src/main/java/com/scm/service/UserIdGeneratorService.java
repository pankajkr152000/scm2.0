package com.scm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scm.repository.UserIdSequenceRepository;
import com.scm.utils.DateUtils;

@Service
public class UserIdGeneratorService {

    private static final String PREFIX = "SCM07";
    private static final int SEQ_LENGTH = 6;

    private final UserIdSequenceRepository sequenceRepository;

    public UserIdGeneratorService(UserIdSequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Transactional
    public String generateUserId() {

        sequenceRepository.increment();
        Long seq = sequenceRepository.getCurrentSequence();

        String year = DateUtils.getYear();
        String paddedSeq = String.format("%0" + SEQ_LENGTH + "d", seq);

        return PREFIX + "/" + year + "/" + paddedSeq;
    }
}

