package org.lms.entity.Feedback;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@Entity
public abstract class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double grade;

    @Column(nullable = false)
    private Double maxGrade;

    @Column(nullable = false)
    private String comment;
}