package org.lms.service.Submission;

import lombok.Getter;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.entity.User.Student;
import org.lms.repository.AssignmentSubmissionRepository;
import org.lms.service.AppUserService;
import org.lms.service.Assessment.AssignmentService;
import org.lms.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Getter
    @Autowired
    public AssignmentService assignmentService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AppUserService appUserService;

    public boolean existsById(Long courseId, Long assignmentId, Long id) {
        try {
            AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(id).orElse(null);
            return assignmentSubmissionRepository.existsById(id)
                    && assignmentService.existsById(courseId, assignmentId)
                    && assignmentSubmission != null
                    && assignmentSubmission.getAssignment().getId().equals(assignmentId);
        } catch (Exception e) {
            return false;
        }
    }

    public AssignmentSubmission getById(Long id) {
        try {
            return assignmentSubmissionRepository.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public AssignmentSubmission getById(Long courseId, Long assignmentId, Long id) {
        try {
            if (!existsById(courseId, assignmentId, id)) {
                return null;
            }
            return getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<AssignmentSubmission> getAllByAssignmentId(Long courseId, Long assignmentId) {
        try {
            if (!assignmentService.existsById(courseId, assignmentId)) {
                return null;
            }
            return assignmentSubmissionRepository.findAllByAssignmentId(assignmentId);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean create(Long courseId, Long assignmentId, Long studentId, MultipartFile file) {
        try {
            if (!assignmentService.existsById(courseId, assignmentId)) {
                return false;
            }

            Assignment assignment = assignmentService.getById(courseId, assignmentId);
            if (assignment == null) {
                return false;
            }

            String filePath = fileStorageService.storeFile(file);
            AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
            assignmentSubmission.setAssignment(assignment);
            assignmentSubmission.setStudent((Student)appUserService.getById(studentId));
            assignmentSubmission.setFilePath(filePath);

            assignmentSubmissionRepository.save(assignmentSubmission);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Long courseId, Long assignmentId, Long id) {
        try {
            if (!existsById(courseId, assignmentId, id)) {
                return false;
            }
            assignmentSubmissionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}