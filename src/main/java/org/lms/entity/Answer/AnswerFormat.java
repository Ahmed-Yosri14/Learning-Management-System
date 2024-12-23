package org.lms.entity.Answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.MappableEntity;
import org.lms.entity.UserRole;

import java.util.Map;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answerFormatType", discriminatorType = DiscriminatorType.STRING)
@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "answerFormatType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MCQAnswer.class, name = "MCQ"),
        @JsonSubTypes.Type(value = TrueFalseAnswer.class, name = "TRUE_FALSE"),
        @JsonSubTypes.Type(value = WrittenAnswer.class, name = "WRITTEN")
})
public abstract class AnswerFormat implements MappableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String correctAnswer;

    public abstract Map<String,Object> toMap(UserRole role);
}