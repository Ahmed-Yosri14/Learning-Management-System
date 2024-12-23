package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.User.Student;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "lesson_id"})
        }
)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("lessonId", getLesson().getId());
        map.put("studentId", getStudent().getId());
        return map;
    }
}