package org.lms.service;

import org.lms.entity.Question;
import org.lms.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).get();
    }
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

}

