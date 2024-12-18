package org.lms.repository;

import org.lms.entity.Course;
import org.lms.entity.Assessment.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findAllByCourse(Course course);
    @Query("SELECT SUM(t1.mark) FROM Question t1 JOIN Quiz t2 ON t1.quiz.id = t2.id")
    Double findFullMark(Long quizId);
}