package org.lms.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Instructor extends AppUser {

    @ManyToMany(mappedBy = "instructors")
    List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Instructor() {
        super();
    }
}
