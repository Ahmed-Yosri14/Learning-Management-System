package org.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Min(0)
    @Max(100)
    @NotNull
    private double value; // The numeric value of the grade

    @NotNull
    private String description; // A description or label for the grade (e.g., "A", "B", "Excellent")

    @ManyToOne
    private Feedback feedback; // Association with feedback (optional, depending on design)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Grade value must be between 0 and 100.");
        }
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
