package org.lms.repository;


import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.entity.User.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    List<QuizSubmission> findByQuiz(Quiz quiz);
    QuizSubmission findByQuizAndStudent(Quiz quiz, Student student);
    boolean existsByStudentIdAndQuizId(Long studentId, Long quizId);

    @Query("SELECT l FROM QuizSubmission l WHERE l.quiz.course.id = :courseId")
    List<QuizSubmission> findByCourseId(@Param("courseId") Long studentId);
}