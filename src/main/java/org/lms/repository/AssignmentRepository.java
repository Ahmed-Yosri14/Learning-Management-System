package org.lms.repository;

import org.lms.entity.Assignment;
import org.lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findAllByCourse(Course course);
}