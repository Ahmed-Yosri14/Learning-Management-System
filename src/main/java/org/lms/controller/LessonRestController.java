package org.lms.controller;

import org.lms.entity.Lesson;
import org.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/course/{courseId}/lesson")
public class LessonRestController {
    @Autowired
    private LessonService lessonService;


    @PutMapping("/")
    public ResponseEntity<String> create(@PathVariable("courseId") Long courseId,@RequestBody Lesson lesson) {
        if (lessonService.create(lesson, courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    @DeleteMapping("/{id}/")
    public ResponseEntity<String> delete(@PathVariable("courseId") Long courseId, @PathVariable("id") Long id) {
        if (lessonService.delete(courseId, id)) {
            return ResponseEntity.ok("Lesson deleted successfully!");
        }
        return ResponseEntity.badRequest().body("Failed to delete lesson.");
    }
    @PatchMapping("/{id}/")
    public ResponseEntity<String> update(@PathVariable("courseId") Long courseId,@PathVariable("id") Long id, @RequestBody Lesson lesson) {
        if (lessonService.update(id, lesson,courseId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{id}/")
    public ResponseEntity<Lesson> getById(@PathVariable("courseId") Long courseId, @PathVariable("id") Long id) {
        Lesson lesson = lessonService.getById(courseId,id);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lesson);
    }
    @GetMapping("/")
    public List<Lesson> getAll(@PathVariable("courseId") Long courseId) {
        return lessonService.getAll(courseId);
    }
}
