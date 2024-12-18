package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
}