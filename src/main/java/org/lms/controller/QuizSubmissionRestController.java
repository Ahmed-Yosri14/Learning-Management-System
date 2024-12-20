package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.Submission.QuizSubmission;
import org.lms.service.Submission.QuizSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/course/{courseid}/quiz/{quizid}/submission")
public class QuizSubmissionRestController {
    @Autowired
    private QuizSubmissionService quizSubmissionService;
    @Autowired
    private AuthorizationManager authorizationManager;

    @GetMapping("/mine")
    public ResponseEntity<QuizSubmission> getMySubmition(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId) {
        if (!authorizationManager.isEnrolled(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        QuizSubmission submitedForm = quizSubmissionService.getSubmition(courseId, quizId, authorizationManager.getCurrentUserId());
        if (submitedForm != null) {
            return ResponseEntity.ok(submitedForm);
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmission> getStudentSubmition(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId, @PathVariable("id") Long studentId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).body(null);
        }

        QuizSubmission submitedForm = quizSubmissionService.getSubmition(courseId, quizId, studentId);
        if (submitedForm != null) {
            return ResponseEntity.ok(submitedForm);
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("")
    public ResponseEntity<List<QuizSubmission>> getSubmitions(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId) {
        if (!authorizationManager.isAdminOrInstructor(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<QuizSubmission> submitedForms = quizSubmissionService.getAllSubmitions(courseId, quizId);
        if (submitedForms != null && !submitedForms.isEmpty()) {
            return ResponseEntity.ok(submitedForms);
        }
        return ResponseEntity.ok().body(submitedForms);
    }

    @PutMapping("")
    public ResponseEntity<String> submit(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId, @RequestBody QuizSubmission quizSubmission) {
        if (!authorizationManager.isEnrolled(courseId)) {
            return ResponseEntity.status(403).body("You are not allowed to make this operation");
        }
        System.out.println(quizSubmission);
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