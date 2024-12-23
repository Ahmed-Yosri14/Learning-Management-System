package org.lms.entity.Feedback;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Submission.AssignmentSubmission;
import org.lms.entity.UserRole;

import java.util.Map;

@Getter
@Setter
@Entity
public class AssignmentFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(nullable = false)
    private AssignmentSubmission assignmentSubmission;

    public Map<String, Object> toMap(UserRole role){
        Map<String, Object> map = super.toMap(role);
        map.put("assignmentSubmissionId", getAssignmentSubmission().getId());
        return map;
    }
}