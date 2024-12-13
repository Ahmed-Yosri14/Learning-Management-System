package org.lms.entity;

import jakarta.persistence.Entity;

@Entity
public class MCQQuestion extends Question {
    int answer;

    public MCQQuestion() {
        super();
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
