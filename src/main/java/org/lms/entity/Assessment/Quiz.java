package org.lms.entity.Assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Question;
import org.lms.entity.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
public class Quiz extends Assessment {


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "quizQuestion",
            joinColumns = @JoinColumn(name = "quizId"),
            inverseJoinColumns = @JoinColumn(name = "questionId")
    )
    private List<Question> questions;


    private Double questionNum;
    public Map<String,Object> toMap(UserRole role){
        Map<String, Object> response = new HashMap<>();
        response.put("question title", this.getTitle());
        response.put("number of questions", questionNum);
        response.put("duration", this.getDuration());
        response.put("start Date", this.getStartDate());
        for(Question question : questions){
            response.put(question.getId().toString(),question.toMap(role));
        }
        return response;
    }
}
