package org.lms.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // A special field to indicate the subtype
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutomatedFeedback.class, name = "automated"),
        @JsonSubTypes.Type(value = ManualFeedback.class, name = "manual")
})
@Entity
public abstract class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Getter
    @Setter
    @NotNull
    private String feedback;

    @Setter
    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    private Grade grade;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}