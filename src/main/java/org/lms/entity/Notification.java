package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Notification {
    @Id
    private int id;
    private String studentId;
    private String title;
    private String content;
    public Notification() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
