package org.lms.entity.Answer;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("MCQ")
public class MCQAnswer extends AnswerFormat {

    private String[] options; // Options for multiple-choice questions
}
