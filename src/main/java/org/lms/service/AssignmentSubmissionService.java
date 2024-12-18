package org.lms.service;

import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.entity.User.Student;
import org.lms.repository.AssignmentSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AppUserService appUserService;

    public boolean existsById(
            Long courseId,
            Long assignmentId,
            Long id
    ){

        AssignmentSubmission assignmentSubmission = getById(id);
        return assignmentSubmissionRepository.existsById(id)
                && assignmentService.existsById(courseId, assignmentId)
                && assignmentSubmission.getAssignment().getId().equals(assignmentId);
    }

    public AssignmentSubmission getById(
            Long id
    ){
        return assignmentSubmissionRepository.findById(id).get();
    }

    public AssignmentSubmission getById(
            Long courseId,
            Long assignmentId,
            Long id
    ){

        try {
            assert existsById(courseId, assignmentId, id);
            return getById(id);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<AssignmentSubmission> getAllByAssignmentId(
            Long courseId,
            Long assignmentId
    ){

        try {
            assert assignmentService.existsById(courseId, assignmentId);

            return assignmentSubmissionRepository.findAllByAssignmentId(assignmentId);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean create (
            Long courseId,
            Long assignmentId,
            Long studentId,
            MultipartFile file
    ){
        try {
            assert assignmentService.existsById(courseId, assignmentId);

            Assignment assignment = assignmentService.getById(courseId, assignmentId);

            String filePath = fileStorageService.storeFile(file);
            AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
            assignmentSubmission.setAssignment(assignment);
            assignmentSubmission.setStudent((Student)appUserService.getById(studentId));
            assignmentSubmission.setFilePath(filePath);

            assignmentSubmissionRepository.save(assignmentSubmission);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean delete(
            Long courseId,
            Long assignmentId,
            Long id
    ){

        try {
            assert existsById(courseId, assignmentId, id);
            assignmentSubmissionRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}