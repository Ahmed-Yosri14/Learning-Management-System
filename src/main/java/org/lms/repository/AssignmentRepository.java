package org.lms.repository;

import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Course;
import org.lms.entity.MappableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    List<Assignment> findAllByCourse(Course course);
}