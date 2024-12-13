package org.lms.entity;

import java.util.List;

public class Course {
    String name;
    String description;
    String ID;
    List<User> users;
    List<Assessment> assessments;
    List<Lesson> Lessons;
//    public Course(String Name, String Description, String ID)
//    {
//        this.Name = Name;
//        this.Description = Description;
//        this.ID = ID;
//    }
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
//    public void AddAssessment(Assessment assessment){
//        assessments.add(assessment);
//    }
//    public void DeleteAssessment(Assessment assessment){
//        assessments.remove(assessment);
//    }
//    public void AddLesson(Lesson lesson){
//        Lessons.add(lesson);
//    }
//    public void DeleteLesson(Lesson ){
//
//    }
    public String getID(){
        return ID;
    }
    public void setID(String ID){
        this.ID = ID;
    }



}
