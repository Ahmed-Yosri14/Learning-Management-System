package org.lms.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ASSIGNMENT")
public class AssignmentFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Assignment assignment;
}