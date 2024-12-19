package org.lms.repository;

import org.lms.entity.Feedback.AssignmentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentFeedbackRepository extends JpaRepository<AssignmentFeedback, Long> {

    @Query("SELECT l FROM AssignmentFeedback l WHERE l.assignmentSubmission.id = :assignmentSubmissionId")
    List<AssignmentFeedback> findAllByAssignmentSubmissionId(Long assignmentSubmissionId);

    @Query("SELECT l FROM AssignmentFeedback l WHERE l.assignmentSubmission.assignment.course.id= : courseId")
    List<AssignmentFeedback> findByCourseId(@Param("courseId") Long courseId);
}
