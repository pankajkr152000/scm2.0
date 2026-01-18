package com.scm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.GlobalContactSequence;

@Repository
public interface IGlobalContactSequenceRepository extends JpaRepository<GlobalContactSequence, Long> {
    
}

