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
    @JoinColumn
    public Quiz quiz;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn())
    private List<QuestionAnswer> questionsAnswer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Student student;
}