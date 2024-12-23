package org.lms.entity.Assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Course;
import org.lms.entity.MappableEntity;
import org.lms.entity.UserRole;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Assessment implements MappableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_seq")
    @SequenceGenerator(name = "assessment_seq", sequenceName = "global_assessment_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Course course;

    public Map<String, Object> toMap(UserRole role){
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("title", getTitle());
        map.put("description", getDescription());
        map.put("duration", getDuration());
        map.put("startDate", getStartDate());
        map.put("courseId", getCourse().getId());
        return map;
    }
}