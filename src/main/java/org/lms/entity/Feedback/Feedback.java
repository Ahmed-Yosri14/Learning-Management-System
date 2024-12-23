package org.lms.entity.Feedback;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.MappableEntity;
import org.lms.entity.UserRole;

import java.util.HashMap;
import java.util.Map;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@Entity
public abstract class Feedback implements MappableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double grade;

    @Column(nullable = false)
    private Double maxGrade;

    @Column(nullable = false)
    private String comment;

    public Map<String, Object> toMap(UserRole role){
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("grade", getGrade());
        map.put("maxGrade", getMaxGrade());
        map.put("comment", getComment());
        return map;
    }
}