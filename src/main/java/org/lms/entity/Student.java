package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Student extends AppUser {
    @ManyToMany(mappedBy = "students")
    List<Course> courses;

    public Student() {
        super();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
