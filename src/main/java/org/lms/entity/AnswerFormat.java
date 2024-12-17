package org.lms.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
public abstract class AnswerFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correctAnswer; // Common field
}
