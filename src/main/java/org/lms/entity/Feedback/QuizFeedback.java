package org.lms.entity.Feedback;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.entity.UserRole;

import java.util.Map;

@Getter
@Setter
@Entity
public class QuizFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(nullable = false)
    private QuizSubmission quizSubmission;

    public Map<String, Object> toMap(UserRole role){
        Map<String, Object> map = super.toMap(role);
        map.put("quizSubmissionId", getQuizSubmission().getId());
        return map;
    }
}