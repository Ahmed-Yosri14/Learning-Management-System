package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.SubmitForm;
import org.lms.service.SubmitQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/course/{courseid}/quiz/{quizid}/submit")
public class SubmitQuizController {
    @Autowired
    private SubmitQuizService submitQuizService;
    @Autowired
    private AuthorizationManager authorizationManager;

    @GetMapping("/student/getsubmition")
    public ResponseEntity<SubmitForm> getMySubmition(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId) {
        if (!authorizationManager.checkCourseStudent(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        SubmitForm submitedForm = submitQuizService.getSubmition(courseId, quizId, authorizationManager.getCurrentUserId());
        if (submitedForm != null) {
            return ResponseEntity.ok(submitedForm);
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/getsubmition/{id}")
    public ResponseEntity<SubmitForm> getStudentSubmition(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId, @PathVariable("id") Long studentId) {
        if (!authorizationManager.checkCourseViewConfidential(courseId)) {
            return ResponseEntity.status(403).body(null);
        }

        SubmitForm submitedForm = submitQuizService.getSubmition(courseId, quizId, studentId);
        if (submitedForm != null) {
            return ResponseEntity.ok(submitedForm);
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/getsubmitions")
    public ResponseEntity<List<SubmitForm>> getSubmitions(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId) {
        if (!authorizationManager.checkCourseViewConfidential(courseId)) {
            return ResponseEntity.status(403).body(null);
        }
        List<SubmitForm> submitedForms = submitQuizService.getAllSubmitions(courseId, quizId);
        if (submitedForms != null && !submitedForms.isEmpty()) {
            return ResponseEntity.ok(submitedForms);
        }
        return ResponseEntity.ok().body(submitedForms);
    }

    @PutMapping("")
    public ResponseEntity<String> submit(@PathVariable("courseid") Long courseId, @PathVariable("quizid") Long quizId, @RequestBody SubmitForm submitForm) {
        if (!authorizationManager.checkCourseStudent(courseId)) {
            return ResponseEntity.status(403).body("You are not allowed to make this operation");
        }
        System.out.println(submitForm);
        if (submitQuizService.existsByStudentIdAndQuizId(authorizationManager.getCurrentUserId(), quizId)) {
            throw new IllegalStateException("You have already submitted this quiz.");
        }
        if (submitQuizService.create(courseId, quizId, authorizationManager.getCurrentUserId(), submitForm)) {

            Double studentMark = submitQuizService.grading(submitForm, quizId, authorizationManager.getCurrentUserId(), courseId);
            return ResponseEntity.ok().body(studentMark.toString());
        }
        return ResponseEntity.badRequest().body("Something went wrong during submission");
    }


}
