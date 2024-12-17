package org.lms.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("WRITTEN")
public class WrittenAnswer extends AnswerFormat {

    private String correctAnswer; // Override to be a String

    @Override
    public String getCorrectAnswer() {
        return super.getCorrectAnswer();
    }

    @Override
    public void setCorrectAnswer(String correctAnswer) {
        super.setCorrectAnswer(correctAnswer);
    }
}
