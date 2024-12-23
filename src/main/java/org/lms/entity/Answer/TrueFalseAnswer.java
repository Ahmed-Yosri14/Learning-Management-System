package org.lms.entity.Answer;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.UserRole;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRUE_FALSE")
public class TrueFalseAnswer extends AnswerFormat {

    @Override
    public Map<String, Object> toMap(UserRole role) {
        Map<String, Object> response = new HashMap<>();
        if (!UserRole.STUDENT.equals(role)) {
            response.put("correct answer", getCorrectAnswer());
        }
        return response;
    }
}
