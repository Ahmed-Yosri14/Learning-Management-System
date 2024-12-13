package org.lms.entity;

public class Grade {
    String studentID;
    String assessmentID;
    double grade;
    public  Grade(String studentID, String assessmentID, double grade) {
        this.studentID = studentID;
        this.assessmentID = assessmentID;
    }
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getAssessmentID() {
        return assessmentID;
    }
    public void setAssessmentID(String assessmentID) {
        this.assessmentID = assessmentID;
    }
    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }
}
