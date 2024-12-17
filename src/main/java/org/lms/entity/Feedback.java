package org.lms.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // A special field to indicate the subtype
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutomatedFeedback.class, name = "automated"),
        @JsonSubTypes.Type(value = ManualFeedback.class, name = "manual")
})

@Getter
@Setter
@Entity
public abstract class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String feedback;
}