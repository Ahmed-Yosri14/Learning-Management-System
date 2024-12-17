package org.lms.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRUE_FALSE")
public class TrueFalseAnswer extends AnswerFormat {

}
