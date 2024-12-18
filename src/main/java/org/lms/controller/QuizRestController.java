package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.Assessment.Quiz;
import org.lms.service.Assessment.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{courseid}/quiz")
public class QuizRestController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private AuthorizationManager authorizationManager;
    @PutMapping("")
    public ResponseEntity<String> createQuiz(@PathVariable("courseid") Long courseId, @RequestBody Quiz quiz) {
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if (quizService.create(courseId,quiz)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id, @RequestBody Quiz quiz, @PathVariable String courseid) {
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if (quizService.update(courseId, id,quiz)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!authorizationManager.hasAccess(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        Quiz quiz = quizService.getById(courseId,id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quiz);
    }
    @GetMapping("")
    public ResponseEntity<List<Quiz>> getAll(@PathVariable("courseid") Long courseId) {
        if (!authorizationManager.hasAccess(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<Quiz> quizzes = quizService.getAll(courseId);
        if (quizzes != null && !quizzes.isEmpty()) {
            return ResponseEntity.ok(quizzes);
        }
        return ResponseEntity.ok().body(quizzes);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("courseid") Long courseId,@PathVariable("id") Long id) {
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if(quizService.deleteAssessment(courseId,id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}