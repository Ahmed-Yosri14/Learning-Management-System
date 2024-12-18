package org.lms.repository;

import org.lms.entity.Attendance;
import org.lms.entity.Lesson;
import org.lms.entity.User.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByLesson(Lesson lesson);

    Attendance findByStudentAndLesson(Student student, Lesson lesson);

    @Query("SELECT l FROM Attendance l WHERE l.lesson.course.id = :courseId")
    List<Attendance> findByCourseId(@Param("courseId") Long courseId);
}