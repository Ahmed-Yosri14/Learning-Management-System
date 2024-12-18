package org.lms.repository;

import org.lms.entity.Feedback.QuizFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizFeedbackRepository extends JpaRepository<QuizFeedback, Long> {

    @Query("SELECT l FROM QuizFeedback l WHERE l.quizSubmission.id = :quizSubmissionId")
    List<QuizFeedback> findAllByQuizSubmissionId(Long quizSubmissionId);
}
