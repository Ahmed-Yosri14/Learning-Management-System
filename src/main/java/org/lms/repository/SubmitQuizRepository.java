package org.lms.repository;


import org.lms.entity.Quiz;
import org.lms.entity.Student;
import org.lms.entity.SubmitForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitQuizRepository extends JpaRepository<SubmitForm, Long> {
    List<SubmitForm> findByQuiz(Quiz quiz);
    SubmitForm findByQuizAndStudent(Quiz quiz, Student student);
    boolean existsByStudentIdAndQuizId(Long studentId, Long quizId);
}
