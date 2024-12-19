package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.entity.Course;
import org.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/course")
public class CourseRestController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthorizationManager authorizationManager;

    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> create(@RequestBody Course course) {
        if (courseService.create(course, authorizationManager.getCurrentUserId())){
            return ResponseEntity.ok("Course created successfully");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Course course) {
        if (!courseService.existsById(id)){
            return ResponseEntity.status(404).body("Course not found");
        }
        if (authorizationManager.isInstructor(id)){
            return ResponseEntity.status(403).body("You're not this course's instructor");
        }
        if (courseService.update(id, course)){
            return ResponseEntity.ok("Course updated successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // instructor
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (!courseService.existsById(id)){
            return ResponseEntity.status(404).body("Course not found");
        }
        if (authorizationManager.isInstructor(id)){
            return ResponseEntity.status(403).body("You're not this course's instructor");
        }
        if (courseService.delete(id)){
            return ResponseEntity.ok("Course deleted successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // all
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable("id") Long id) {
        Course course = courseService.getById(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }
    // all
    @GetMapping("")
    public List<Course> getAll() {
        return courseService.getAll();
    }
}
