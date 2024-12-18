package org.lms.entity.Feedback;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Submission.AssignmentSubmission;

@Getter
@Setter
@Entity
public class AssignmentFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(nullable = false)
    private AssignmentSubmission assignmentSubmission;
}