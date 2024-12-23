package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Answer.AnswerFormat;

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

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;
}
