package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long duration;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Course course;
}