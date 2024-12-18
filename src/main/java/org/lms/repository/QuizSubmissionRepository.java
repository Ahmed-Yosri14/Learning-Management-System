package org.lms.repository;


import org.lms.entity.Quiz;
import org.lms.entity.Student;
import org.lms.entity.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    List<QuizSubmission> findByQuiz(Quiz quiz);
    QuizSubmission findByQuizAndStudent(Quiz quiz, Student student);
    boolean existsByStudentIdAndQuizId(Long studentId, Long quizId);
}