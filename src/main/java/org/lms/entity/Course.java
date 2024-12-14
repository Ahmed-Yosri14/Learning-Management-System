package org.lms.entity;

import jakarta.persistence.*;


@Entity
public class Course {
    public Course() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long duration;

    @ManyToOne
    @JoinColumn
    private Instructor instructor;

    public void setDuration(long duration) {
        this.duration = duration;
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

    public Long getDuration() {
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

    public Long getId(){
        return id;
    }
    public void setId(Long ID){
        this.id = ID;
    }
}
