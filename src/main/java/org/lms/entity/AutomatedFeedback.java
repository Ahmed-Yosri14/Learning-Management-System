package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class AutomatedFeedback extends Feedback {
    @NotNull
    @ManyToOne
    private Quiz quiz;

    @NotNull
    private double calculatedScore;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public double getCalculatedScore() {
        return calculatedScore;
    }

    public void setCalculatedScore(double calculatedScore) {
        this.calculatedScore = calculatedScore;
    }
}
