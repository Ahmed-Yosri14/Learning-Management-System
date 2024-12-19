package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Answer.AnswerFormat;
import org.lms.entity.Assessment.Quiz;

import java.util.List;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String questionStatement;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private AnswerFormat answerFormat;

    @ManyToMany(mappedBy = "questions", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Quiz> quizzes;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

}
