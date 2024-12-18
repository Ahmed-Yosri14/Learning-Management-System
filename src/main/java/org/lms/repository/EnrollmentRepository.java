package org.lms.repository;

import org.lms.entity.Course;
import org.lms.entity.Enrollment;
import org.lms.entity.User.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT l FROM Enrollment l WHERE l.course.id = :courseId")
    List<Enrollment> findAllByCourseId(Long courseId);
    Enrollment findByStudentAndCourse(Student student, Course course);
}