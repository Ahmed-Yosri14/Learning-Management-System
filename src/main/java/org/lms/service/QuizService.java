package org.lms.service;

import org.lms.entity.Quiz;
import org.lms.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id).get();
    }

    public List<Quiz> getQuizzesByCourseId(Long courseId) {
        return quizRepository.findAll();
    }

}
