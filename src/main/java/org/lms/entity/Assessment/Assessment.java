package org.lms.entity.Assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Course;

import java.time.LocalDateTime;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
