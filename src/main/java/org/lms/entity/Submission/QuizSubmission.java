package org.lms.entity.Submission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.QuestionAnswer;
import org.lms.entity.UserRole;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@DiscriminatorValue("QUIZ")
public class QuizSubmission extends Submission {
    @ManyToOne
    @JoinColumn(nullable = false)
    public Quiz quiz;

    @ElementCollection
    @CollectionTable(name = "submitted_answers", joinColumns = @JoinColumn(name = "quiz_submission_id"))
    private List<QuestionAnswer> questionsAnswer;

    public Map<String, Object> toMap(UserRole role) {
        Map<String, Object> map = super.toMap(role);
        map.put("quizId", getQuiz().getId());
        return map;
    }
}