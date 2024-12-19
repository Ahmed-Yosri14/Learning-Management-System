package org.lms.entity.Assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Question;

import java.util.List;

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
}
