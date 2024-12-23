package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Answer.AnswerFormat;
import org.lms.entity.Assessment.Quiz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map toMap(UserRole role)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("questionStatement", questionStatement);
        response.put("answerFormat", answerFormat.toMap(role));
        return response;
    }

}
