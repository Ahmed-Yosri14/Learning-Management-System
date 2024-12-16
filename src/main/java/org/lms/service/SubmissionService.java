package org.lms.service;

import org.lms.AuthorizationManager;
import org.lms.entity.Assignment;
import org.lms.entity.Submission;
import org.lms.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AuthorizationManager authorizationManager;

    public boolean submit(Long courseId, Long assignmentId, Long studentId, MultipartFile file) {
        try {
            boolean isAuthorized = authorizationManager.checkCourseView(courseId);
            if (!isAuthorized)
            {
                throw new IllegalAccessException("Student is not enrolled in the course.");
            }

            Assignment assignment = assignmentService.getById(courseId, assignmentId);
            if (assignment == null)
            {
                throw new IllegalArgumentException("Assignment not found for the given course.");
            }

            String filePath = fileStorageService.storeFile(file);
            Submission submission = new Submission();
            submission.setAssignment(assignment);
            submission.setStudentId(studentId);
            submission.setFilePath(filePath);

            submissionRepository.save(submission);
            return true;
        }
        catch (Exception e) {
            System.out.println("Error during submission: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSubmission(Long submissionId, Long studentId) {
        try {
            Optional<Submission> submissionOpt = submissionRepository.findById(submissionId);
            if (submissionOpt.isPresent()) {
                Submission submission = submissionOpt.get();
                if (!submission.getStudentId().equals(studentId)) {
                    throw new IllegalAccessException("You are not authorized to delete this submission.");
                }
                submissionRepository.deleteById(submissionId);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error deleting submission: " + e.getMessage());
            return false;
        }
    }

    //we have to restrict this method
    public List<Submission> getSubmissions(Long courseId, Long assignmentId) {
        try {
            Assignment assignment = assignmentService.getById(courseId, assignmentId);
            if (assignment == null) {
                throw new IllegalArgumentException("Assignment not found for the given course.");
            }
            return submissionRepository.findByAssignment(assignment);
        } catch (Exception e) {
            System.err.println("Error fetching submissions: " + e.getMessage());
            return null;
        }
    }

}
