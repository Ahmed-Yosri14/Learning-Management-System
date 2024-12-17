package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;

    @Column(nullable = false)
    private String filePath;
}
