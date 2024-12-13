package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Grade {
    @Id
    long id;
    long studentID;
    long assessmentID;
    double grade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public long getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(long assessmentID) {
        this.assessmentID = assessmentID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
