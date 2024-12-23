package org.lms.entity.Assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Question;
import org.lms.entity.User.Student;

import java.util.List;

@Getter
@Setter
@Entity
public class StudentQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToMany
    @JoinTable(
            name = "student_quiz_questions",
            joinColumns = @JoinColumn(name = "student_quiz_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;
}
