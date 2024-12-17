package org.lms.service;

import org.lms.entity.Submission;
import org.lms.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public boolean existsById(Long id){
        return submissionRepository.existsById(id);
    }
    public Submission getById(Long id){
        return submissionRepository.findById(id).orElse(null);
    }
}
