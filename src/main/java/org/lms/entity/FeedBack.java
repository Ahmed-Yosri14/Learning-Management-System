package org.lms.entity;

public class FeedBack {
    String feedback;
    String assessmentID;
    FeedBack(String feedback, String assessmentID) {
        this.feedback = feedback;
        this.assessmentID = assessmentID;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public String getAssessmentID() {
        return assessmentID;
    }
    public void setAssessmentID(String assessmentID) {
        this.assessmentID = assessmentID;
    }
}
