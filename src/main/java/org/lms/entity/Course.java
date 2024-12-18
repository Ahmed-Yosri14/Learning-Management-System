package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.User.Instructor;


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
}
