package org.lms.repository;

import org.lms.entity.Course;
import org.lms.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findAllByCourse(Course course);
}