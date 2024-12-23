package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Long duration;

    @Column(unique = true)
    private String otp;

    @ManyToOne
    @JoinColumn
    private Course course;

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("title", getTitle());
        map.put("description", getDescription());
        map.put("duration", getDuration());
        map.put("otp", getOtp());
        map.put("courseId", getCourse().getId());
        return map;
    }
}
