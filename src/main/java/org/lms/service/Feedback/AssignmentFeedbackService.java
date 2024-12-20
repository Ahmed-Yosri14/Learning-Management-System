package org.lms.service.Feedback;

import lombok.Getter;
import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.repository.AssignmentFeedbackRepository;
import org.lms.service.Submission.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentFeedbackService extends FeedbackService {

    @Autowired
    private AssignmentFeedbackRepository assignmentFeedbackRepository;

    @Getter
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


    public AssignmentFeedback getById(Long id){
        try {
            return (AssignmentFeedback)super.getById(id);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<AssignmentFeedback> getAllByAssignmentSubmissionId(Long assignmentSubmissionId){
        try {
            return assignmentFeedbackRepository.findAllByAssignmentSubmissionId(assignmentSubmissionId);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean create(Long assignmentSubmissionId, AssignmentFeedback assignmentFeedback
    ){

        try {
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

    public boolean update(Long id, AssignmentFeedback assignmentFeedback){
        return super.update(id, assignmentFeedback);
    }

    public boolean delete(Long id){
        return super.delete(id);
    }
}