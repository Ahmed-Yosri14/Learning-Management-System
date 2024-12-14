package org.lms.controller;

import org.lms.entity.Quiz;
import org.lms.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseid}/quiz")
public class QuizRestController {
    @Autowired
    private QuizService quizService;

    @PutMapping("/")
    public ResponseEntity<String> createQuiz(@PathVariable("courseid") Long courseId, @RequestBody Quiz quiz) {
        if (quizService.create(courseId,quiz)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<String> updateQuiz(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id, @RequestBody Quiz quiz, @PathVariable String courseid) {
        if (quizService.update(courseId, id,quiz)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{id}/")
    public ResponseEntity<Quiz> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        Quiz quiz = quizService.getById(courseId,id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quiz);
    }
    @GetMapping("/")
    public List<Quiz> getAll(@PathVariable("courseid") Long courseId) {
        return quizService.getAll(courseId);
    }
    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteQuiz(@PathVariable("courseid") Long courseId,@PathVariable("id") Long id) {
        if(quizService.delete(courseId,id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}
