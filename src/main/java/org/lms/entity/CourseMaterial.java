package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
public class CourseMaterial implements MappableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    public Map<String, Object> toMap(UserRole role){
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("courseId", getCourse().getId());
        map.put("fileName", getFileName());
        map.put("filePath", getFilePath());
        return map;
    }
}