package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ManualFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Instructor instructor;
}