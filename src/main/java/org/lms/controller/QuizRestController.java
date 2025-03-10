package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.MappableEntity;
import org.lms.entity.Question;
import org.lms.service.Assessment.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course/{courseid}/quiz")
public class QuizRestController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;


    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> createQuiz(@PathVariable("courseid") Long courseId, @RequestBody Quiz quiz) {
        if (!quizService.courseExistsById(courseId)) {
            return ResponseEntity.status(404).body("Course not found.");
        }
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if (quizService.create(courseId,quiz)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{quizId}/deletequestions")
    public ResponseEntity<String> removeQuestionFromQuiz(@PathVariable("courseid") Long courseId, @PathVariable("quizId") Long quizId,@RequestBody List<Long>questions) {
        if (!quizService.existsById(courseId, quizId)) {
            return ResponseEntity.status(404).body("Quiz not found.");
        }
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if (quizService.removeQuestionsFromQuiz(courseId, quizId,questions)) {
            return ResponseEntity.ok("Questions removed from quiz successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong while removing the questions from the quiz.");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/{quizId}/addquestions")
    public ResponseEntity<String> addQuestionsToQuiz(@PathVariable("courseid") Long courseId, @PathVariable("quizId") Long quizId,@RequestBody List<Long>questions) {
        if (!quizService.existsById(courseId, quizId)) {
            return ResponseEntity.status(404).body("Quiz not found.");
        }
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if (quizService.addQuestionstoQuiz(courseId, quizId,questions)) {
            return ResponseEntity.ok("Question added to quiz successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong while question the questions from the quiz.");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id, @RequestBody Quiz quiz, @PathVariable String courseid) {
        if (!quizService.existsById(courseId, id)) {
            return ResponseEntity.status(404).body("Quiz not found.");
        }
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if (quizService.update(courseId, id,quiz)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!quizService.existsById(courseId, id)) {
            return ResponseEntity.status(404).body(null);
        }
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        Quiz quiz = quizService.getById(courseId,id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map((MappableEntity) quiz));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/{id}")
    public ResponseEntity<Object> getByIdForStudent(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!quizService.existsById(courseId, id)) {
            return ResponseEntity.status(404).body(null);
        }
        if (!authorizationManager.isEnrolled(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<Question> questions = quizService.getQuestionsForStudent(courseId,id);
        if (questions== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(new ArrayList<>(questions)));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll(@PathVariable("courseid") Long courseId) {
        if (!quizService.courseExistsById(courseId)) {
            return ResponseEntity.status(404).body(null);
        }
        if (!authorizationManager.hasAccess(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<Quiz> quizzes = quizService.getAll(courseId);
        if (quizzes != null && !quizzes.isEmpty()) {
            return ResponseEntity.ok(entityMapper.map(new ArrayList<>(quizzes)));
        }
        return ResponseEntity.ok().body(quizzes);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("courseid") Long courseId,@PathVariable("id") Long id) {
        if (!quizService.existsById(courseId, id)) {
            return ResponseEntity.status(404).body("Quiz not found.");
        }
        if (!authorizationManager.isInstructor(courseId)) {
            return ResponseEntity.status(403).body("You do not have permission to edit this course.");
        }
        if(quizService.delete(courseId,id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
}