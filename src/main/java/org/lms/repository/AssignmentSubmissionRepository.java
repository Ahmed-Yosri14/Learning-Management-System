package org.lms.repository;

import org.lms.entity.Submission.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    @Query("SELECT l FROM AssignmentSubmission l WHERE l.assignment.id = :assignmentId")
    List<AssignmentSubmission> findAllByAssignmentId(Long assignmentId);

    @Query("SELECT l FROM AssignmentSubmission l WHERE l.assignment.course.id = :courseId")
    List<AssignmentSubmission> findByCourseId(@Param("courseId") Long courseId);
}