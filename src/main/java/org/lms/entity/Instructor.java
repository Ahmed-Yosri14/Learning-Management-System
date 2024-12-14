package org.lms.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends AppUser {

//    @OneToMany(mappedBy = "instructor")
//    private List<Course> courses;
//
//    public List<Course> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }
}
