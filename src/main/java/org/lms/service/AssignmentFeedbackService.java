package org.lms.service;

import org.lms.entity.AssignmentFeedback;
import org.lms.repository.AssignmentFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentFeedbackService extends FeedbackService {

    @Autowired
    private AssignmentFeedbackRepository assignmentFeedbackRepository;

    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;


    public boolean existsById(
            Long courseId,
            Long assignmentId,
            Long assignmentSubmissionId,
            Long id
    ){

        AssignmentFeedback assignmentFeedback = (AssignmentFeedback)getById(id);
        return existsById(id)
                && assignmentSubmissionService.existsById(courseId, assignmentId, assignmentSubmissionId)
                && assignmentFeedback.getAssignmentSubmission().getId().equals(assignmentSubmissionId);
    }


    public AssignmentFeedback getById(
            Long courseId,
            Long assignmentId,
            Long assignmentSubmissionId,
            Long id
    ){
        try {
            assert existsById(courseId, assignmentId, assignmentSubmissionId, id);

            return (AssignmentFeedback)getById(id);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<AssignmentFeedback> getAllByAssignmentSubmissionId(
            Long courseId,
            Long assignmentId,
            Long assignmentSubmissionId
    ){
        try {
            assert assignmentSubmissionService.existsById(courseId, assignmentId, assignmentSubmissionId);

            return assignmentFeedbackRepository.findAllByAssignmentSubmissionId(assignmentSubmissionId);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean create(
            Long courseId,
            Long assignmentId,
            Long assignmentSubmissionId,
            AssignmentFeedback assignmentFeedback
    ){

        try {
            assert assignmentSubmissionService.existsById(courseId, assignmentId, assignmentSubmissionId);

            assignmentFeedback.setAssignmentSubmission(
                    assignmentSubmissionService.getById(assignmentSubmissionId));

            assignmentFeedbackRepository.save(assignmentFeedback);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean update(
            Long courseId,
            Long assignmentId,
            Long assignmentSubmissionId,
            Long id,
            AssignmentFeedback assignmentFeedback
    ){

        try {
            assert existsById(courseId, assignmentId, assignmentSubmissionId, id);

            return update(id, assignmentFeedback);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean delete(
            Long courseId,
            Long assignmentId,
            Long assignmentSubmissionId,
            Long id
    ){

        try {
            assert existsById(courseId, assignmentId, assignmentSubmissionId, id);

            return delete(id);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}