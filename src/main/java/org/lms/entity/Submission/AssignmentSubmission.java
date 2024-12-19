package org.lms.entity.Submission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Assessment.Assignment;

@Getter
@Setter
@Entity
public class AssignmentSubmission extends Submission {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Assignment assignment;

    @Column(nullable = false)
    private String filePath;
}