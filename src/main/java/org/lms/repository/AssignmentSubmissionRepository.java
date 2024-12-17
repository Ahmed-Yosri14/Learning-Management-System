package org.lms.repository;

import org.lms.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    @Query("SELECT l FROM AssignmentSubmission l WHERE l.assignment.id = :assignmentId")
    List<AssignmentSubmission> findAllByAssignmentId(Long assignmentId);
}
