package org.lms.repository;

import org.lms.entity.Assignment;
import org.lms.entity.Course;
import org.lms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.Assign;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findByCourse(Course course);
}