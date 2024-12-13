package org.lms.entity;

import jakarta.persistence.Entity;

import java.util.ArrayList;
@Entity
public class Mcq extends Question {
    int answer;

    public Mcq() {
        super();
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
