package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.User.Student;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "course_id"})
        }
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;
}
