package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Lesson {

    @Id
    private int id;
    private int courseId;
    private String title;
    private String description;
    private String opt;

    @OneToMany
    private List<Student> attendees;

    public Lesson(int id, int courseId, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Lesson() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public List<Student> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Student> attendees) {
        this.attendees = attendees;
    }
}
