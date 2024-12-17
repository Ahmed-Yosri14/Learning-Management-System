package org.lms.entity;

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

    @Override
    public String getCorrectAnswer() {
        return super.getCorrectAnswer();
    }

    @Override
    public void setCorrectAnswer(String correctAnswer) {
        // Ensure that the correctAnswer for MCQ is a valid string, like the correct option
        super.setCorrectAnswer(correctAnswer);
    }
}
