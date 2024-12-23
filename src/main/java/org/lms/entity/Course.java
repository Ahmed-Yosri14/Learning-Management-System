package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.User.Instructor;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@Entity
public class Course {
    public Course() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Long duration;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Instructor instructor;

    public Map<String, Object> toMap(){
        Map<String, Object> data = new HashMap<>();
        data.put("id", getId());
        data.put("name", getName());
        data.put("description", getDescription());
        data.put("duration", getDuration());
        data.put("instructorName", getInstructor().getFirstName() + " " + getInstructor().getLastName());
        return data;
    }
}
