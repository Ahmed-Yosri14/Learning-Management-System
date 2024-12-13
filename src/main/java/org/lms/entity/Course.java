package org.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@Entity
public class Course {
    public Course() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private long duration;

    @OneToMany(mappedBy = "course")
    List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn  // Foreign key column
    private Instructor instructor;



    public void setDuration(@NotNull long duration) {
        this.duration = duration;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
    public Long getId(){
        return id;
    }
    public void setId(Long ID){
        this.id = ID;
    }
}
