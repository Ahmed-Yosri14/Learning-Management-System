package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionStatement;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AnswerFormat answerFormat;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Quiz quiz;
}
