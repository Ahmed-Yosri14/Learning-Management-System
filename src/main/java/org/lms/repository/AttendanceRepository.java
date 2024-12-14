package org.lms.repository;

import org.lms.entity.Attendance;
import org.lms.entity.Lesson;
import org.lms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByLesson(Lesson lesson);
    Attendance findByStudentAndLesson(Student student, Lesson lesson);
}