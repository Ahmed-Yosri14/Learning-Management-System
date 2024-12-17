package org.lms.entity;

import ch.qos.logback.core.joran.sanity.Pair;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class SubmitForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    public Quiz quiz;

    @ElementCollection
    @CollectionTable(name = "submitted_answers", joinColumns = @JoinColumn(name = "submit_form_id"))
    private List<QuestionAnswer> questionsAnswer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Student student;
}
