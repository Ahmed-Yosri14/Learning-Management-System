package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.Lesson;
import org.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/course/{courseId}/lesson")
public class LessonRestController {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private AuthorizationManager authorizationManager;

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> create(@PathVariable("courseId") Long courseId,@RequestBody Lesson lesson) {
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (lessonService.create(lesson, courseId)){

            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("courseId") Long courseId, @PathVariable("id") Long id) {
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (lessonService.delete(courseId, id)) {
            return ResponseEntity.ok("Lesson deleted successfully!");
        }
        return ResponseEntity.badRequest().body("Failed to delete lesson.");
    }
    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("courseId") Long courseId,@PathVariable("id") Long id, @RequestBody Lesson lesson) {
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (lessonService.update(id, lesson,courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // all
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getById(@PathVariable("courseId") Long courseId, @PathVariable("id") Long id) {
        if (!authorizationManager.canViewCourse(courseId)){
            return ResponseEntity.status(403).build();
        }
        Lesson lesson = lessonService.getById(courseId,id);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lesson);
    }
    // all
    @GetMapping("")
    public ResponseEntity<List<Lesson>> getAll(@PathVariable("courseId") Long courseId) {
        if (!authorizationManager.canViewCourse(courseId)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(lessonService.getAll(courseId));
    }
}
