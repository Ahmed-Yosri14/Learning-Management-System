package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
@Entity
public class Submission {
    @Id
    long id;

    @OneToMany
    ArrayList<AnsweredQuestion>answers;

    public ArrayList<AnsweredQuestion> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnsweredQuestion> answers) {
        this.answers = answers;
    }
}
