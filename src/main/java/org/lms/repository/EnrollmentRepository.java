package org.lms.repository;

import org.lms.entity.Course;
import org.lms.entity.Enrollment;
import org.lms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByCourse(Course course);
    Enrollment findByStudentAndCourse(Student student, Course course);
}