package org.lms.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Course {
    public Course() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long ID;

    String name;
    String description;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "course_instructor",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    List<Instructor> instructors;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

//    public List<Assessment> getAssessments() {
//        return assessments;
//    }
//    public void setAssessments(List<Assessment> assessments) {
//        this.assessments = assessments;
//    }
//    public List<Lesson> getLessons() {
//        return Lessons;
//    }
//    public void setLessons(List<Lesson> lessons) {
//        Lessons = lessons;
//    }
    public long getID(){
        return ID;
    }
    public void setID(long ID){
        this.ID = ID;
    }
}
