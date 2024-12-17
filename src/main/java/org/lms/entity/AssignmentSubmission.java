package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ASSIGNMENT")
public class AssignmentSubmission extends Submission {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Assignment assignment;

    @Column(nullable = false)
    private String filePath;
}
