package org.lms.entity;

public class Grade {
    String studentID;
    String assessmentID;
    public  Grade(String studentID, String assessmentID) {
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
}
