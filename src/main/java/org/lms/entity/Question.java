package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Answer.AnswerFormat;
import org.lms.entity.Assessment.Quiz;

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

    private Double mark;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Quiz quiz;
}
