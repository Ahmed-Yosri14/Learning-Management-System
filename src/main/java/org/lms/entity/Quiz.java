package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Course course;
}
