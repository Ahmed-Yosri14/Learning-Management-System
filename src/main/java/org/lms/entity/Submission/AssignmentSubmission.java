package org.lms.entity.Submission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.UserRole;

import java.util.Map;

@Getter
@Setter
@Entity
public class AssignmentSubmission extends Submission {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Assignment assignment;

    @Column(nullable = false)
    private String filePath;

    public Map<String, Object> toMap(UserRole role) {
        Map<String, Object> map = super.toMap(role);
        map.put("filePath", filePath);
        map.put("assignmentId", getAssignment().getId());
        return map;
    }
}