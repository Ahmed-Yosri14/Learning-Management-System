package org.lms.repository;

import org.lms.entity.Course;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.MappableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findAllByCourse(Course course);
}