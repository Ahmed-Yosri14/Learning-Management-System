package org.lms.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Course {
    public Course() {

    }
    String name;
    String description;
    @Id
    long ID;
    @ManyToMany
    List<User> users;
    @OneToMany
    List<Assessment> assessments;
    List<Lesson> Lessons;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }



    public List<Lesson> getLessons() {
        return Lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        Lessons = lessons;
    }
    public long getID(){
        return ID;
    }
    public void setID(long ID){
        this.ID = ID;
    }

}
