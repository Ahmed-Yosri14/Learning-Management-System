package org.lms.entity.Submission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.MappableEntity;
import org.lms.entity.User.Student;
import org.lms.entity.UserRole;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Submission implements MappableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Student student;


    @Override
    public Map<String, Object> toMap(UserRole role) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("studentId", getStudent().getId());
        return map;
    }
}