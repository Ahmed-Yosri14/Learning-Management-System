package org.lms.controller;

import jakarta.validation.Valid;
import org.lms.entity.Question;
import org.lms.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/")
    public String createQuestion(@Valid @RequestBody   Question question, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        else {
            questionService.saveQuestion(question);
            return "Question created successfully!";
        }
    }
    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable("id") Long id) {
        return questionService.getQuestionById(id);
    }
    @GetMapping("/")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
