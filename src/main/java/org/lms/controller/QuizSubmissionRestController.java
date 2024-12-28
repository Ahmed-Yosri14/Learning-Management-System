package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.service.Submission.QuizSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/course/{courseid}/quiz/{quizid}/submission")
public class QuizSubmissionRestController {

    @Autowired
    private QuizSubmissionService quizSubmissionService;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private EntityMapper entityMapper;


    @GetMapping("/mine")
    public ResponseEntity<Object> getMySubmission(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId) {
        if (!authorizationManager.isEnrolled(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        QuizSubmission submitedForm = quizSubmissionService.getSubmition(courseId, quizId, authorizationManager.getCurrentUserId());
        if (submitedForm != null) {
            return ResponseEntity.ok(entityMapper.map(submitedForm));
        }
        return ResponseEntity.status(404).body(null);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentSubmission(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId, @PathVariable("id") Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).body(null);
        }

        QuizSubmission submitedForm = quizSubmissionService.getSubmition(courseId, quizId, studentId);
        if (submitedForm != null) {
            return ResponseEntity.ok(entityMapper.map(submitedForm));
        }
        return ResponseEntity.status(404).body(null);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @GetMapping("")
    public ResponseEntity<Object> getSubmissions(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<QuizSubmission> submitedForms = quizSubmissionService.getAllSubmitions(courseId, quizId);
        if (submitedForms != null && !submitedForms.isEmpty()) {
            return ResponseEntity.ok(entityMapper.map(new ArrayList<>(submitedForms)));
        }
        return ResponseEntity.ok().body(submitedForms);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("")
    public ResponseEntity<String> submit(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId, @RequestBody QuizSubmission quizSubmission) {
        if (!authorizationManager.isEnrolled(courseId)) {
            return ResponseEntity.status(403).body("You are not allowed to make this operation");
        }
        if (quizSubmissionService.existsByStudentIdAndQuizId(authorizationManager.getCurrentUserId(), quizId)) {
            throw new IllegalStateException("You have already submitted this quiz.");
        }
        if (quizSubmissionService.create(courseId, quizId, authorizationManager.getCurrentUserId(), quizSubmission)) {

            Double studentMark = quizSubmissionService.grading(quizSubmission, quizId, authorizationManager.getCurrentUserId(), courseId);
            return ResponseEntity.ok().body(studentMark.toString());
        }
        return ResponseEntity.badRequest().body("Something went wrong during submission");
    }
}