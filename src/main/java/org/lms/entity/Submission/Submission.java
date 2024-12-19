package org.lms.entity.Submission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.User.Student;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;
}