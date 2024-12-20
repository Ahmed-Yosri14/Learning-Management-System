package org.lms.repository;

import org.lms.entity.Feedback.AssignmentFeedback;
import org.lms.entity.Feedback.QuizFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizFeedbackRepository extends JpaRepository<QuizFeedback, Long> {

    @Query("SELECT l FROM QuizFeedback l WHERE l.quizSubmission.id = :quizSubmissionId")
    List<QuizFeedback> findAllByQuizSubmissionId(Long quizSubmissionId);

    @Query("SELECT l FROM QuizFeedback l WHERE l.quizSubmission.quiz.course.id = :courseId")
    List<QuizFeedback> findAllByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT l FROM QuizFeedback l WHERE l.quizSubmission.quiz.course.id= :courseId and l.quizSubmission.student.id = :studentId")
    List<QuizFeedback> findByCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId")Long studentId);
}
