package org.lms.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Course {
    public Course() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String name;
    private String description;
    private long duration;
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    @OneToMany(mappedBy = "courses")
    @JoinColumn(name = "course_id", nullable = false)
    private Instructor instructor;

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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
    public long getID(){
        return ID;
    }
    public void setID(long ID){
        this.ID = ID;
    }
}
