package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FeedBack {
    @Id
    long id;
    String feedback;
    long assessmentID;

    public FeedBack() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public long getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(long assessmentID) {
        this.assessmentID = assessmentID;
    }
}
