package org.lms.controller;

import org.lms.entity.Quiz;
import org.lms.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizRestController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable("id") Long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping("/course/{courseId}")
    public List<Quiz> getQuizzesByCourse(@PathVariable("courseId") Long courseId) {
        return quizService.getQuizzesByCourseId(courseId);
    }
}
