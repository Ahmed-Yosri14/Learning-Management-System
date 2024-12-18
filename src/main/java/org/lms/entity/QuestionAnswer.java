package org.lms.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class QuestionAnswer {
    private Long questionId;
    private String answer;
}
