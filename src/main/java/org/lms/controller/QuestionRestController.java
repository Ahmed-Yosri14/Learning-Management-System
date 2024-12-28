package org.lms.controller;

import jakarta.validation.Valid;
import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Question;
import org.lms.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/course/{courseid}/question")
public class QuestionRestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> create(@PathVariable("courseid") Long courseId, @RequestBody Question question) {
        if(!(authorizationManager.isInstructor(courseId)))
        {
            return ResponseEntity.status(403).body("You are not allowed to edit this quiz");
        }
        if (questionService.create(courseId, question)) {
            return ResponseEntity.ok("Question created successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong while creating the question.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id) {
        if (!authorizationManager.hasAccess(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        Question question = questionService.getById(courseId,id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entityMapper.map(question));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll(@PathVariable("courseid") Long courseId) {
        if (!authorizationManager.hasAccess(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<Question> questions = questionService.getAll(courseId);
        if (questions != null && !questions.isEmpty()) {
            return ResponseEntity.ok(questions);
        }
        return ResponseEntity.ok().body(entityMapper.map(new ArrayList<>(questions)));
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("courseid") Long courseId, @PathVariable("id") Long id, @Valid @RequestBody Question question, BindingResult result) {
        if(result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body("Validation failed: \n" + errorMessages.toString());
        }
        if(!(authorizationManager.isInstructor(courseId)))
        {
            return ResponseEntity.status(403).body("You are not allowed to edit this question");
        }
        if (questionService.update(courseId,  id, question)) {
            return ResponseEntity.ok("Question updated successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong while updating the question.");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("courseid") Long courseId,  @PathVariable("id") Long id) {
        if(!(authorizationManager.isInstructor(courseId)))
        {
            return ResponseEntity.status(403).body("You are not allowed to edit this question");
        }
        if (questionService.delete(courseId, id)) {
            return ResponseEntity.ok("Question deleted successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong while deleting the question.");
    }
}
