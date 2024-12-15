package org.lms.service;

import org.lms.entity.Assignment;
import org.lms.entity.Submission;
import org.lms.entity.AppUser;
import org.lms.repository.AssignmentRepository;
import org.lms.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private FileStorageService fileStorageService;


    public boolean submitAssignment(Long assignmentId, Long studentId, MultipartFile file) throws IOException {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        if (assignmentOptional.isEmpty()) {
            return false;
        }

        // Store the file
        String filePath = fileStorageService.storeFile(file);

        // Create submission
        Submission submission = new Submission();
        submission.setAssignment(assignmentOptional.get());

        AppUser student = new AppUser();
        student.setId(studentId);
        submission.setStudent(student);

        submission.setFilePath(filePath);

        submissionRepository.save(submission);
        return true;
    }
}
