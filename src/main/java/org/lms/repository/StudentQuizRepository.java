package org.lms.repository;

import org.lms.entity.Assessment.StudentQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentQuizRepository extends JpaRepository<StudentQuiz, Long> {

    Optional<StudentQuiz> findByStudentIdAndQuizId(Long studentId, Long quizId);
}
