package org.lms.service;

import org.lms.AuthorizationManager;
import org.lms.entity.Assignment;
import org.lms.entity.AssignmentSubmission;
import org.lms.entity.Student;
import org.lms.repository.AssignmentSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private AppUserService appUserService;

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
            AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
            assignmentSubmission.setAssignment(assignment);
            assignmentSubmission.setStudent((Student)appUserService.getById(studentId));
            assignmentSubmission.setFilePath(filePath);

            assignmentSubmissionRepository.save(assignmentSubmission);
            return true;
        }
        catch (Exception e) {
            System.out.println("Error during submission: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSubmission(Long submissionId, Long studentId) {
        try {
            Optional<AssignmentSubmission> submissionOpt = assignmentSubmissionRepository.findById(submissionId);
            if (submissionOpt.isPresent()) {
                AssignmentSubmission assignmentSubmission = submissionOpt.get();
                if (!assignmentSubmission.getStudent().getId().equals(studentId)) {
                    throw new IllegalAccessException("You are not authorized to delete this submission.");
                }
                assignmentSubmissionRepository.deleteById(submissionId);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error deleting submission: " + e.getMessage());
            return false;
        }
    }

    //we have to restrict this method
    public List<AssignmentSubmission> getSubmissions(Long courseId, Long assignmentId) {
        try {
            Assignment assignment = assignmentService.getById(courseId, assignmentId);
            if (assignment == null) {
                throw new IllegalArgumentException("Assignment not found for the given course.");
            }
            return assignmentSubmissionRepository.findAllByAssignment(assignment);
        } catch (Exception e) {
            System.err.println("Error fetching submissions: " + e.getMessage());
            return null;
        }
    }

}
