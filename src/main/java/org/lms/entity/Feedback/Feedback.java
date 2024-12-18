package org.lms.entity.Feedback;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuizFeedback.class, name = "QUIZ"),
        @JsonSubTypes.Type(value = AssignmentFeedback.class, name = "ASSIGNMENT")
})

@Getter
@Setter
@Entity
public abstract class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double grade;

    @Column(nullable = false)
    private Double maxGrade;

    @Column(nullable = false)
    private String comment;
}