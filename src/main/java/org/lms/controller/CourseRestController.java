package org.lms.controller;

import org.lms.entity.Course;
import org.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/course")
public class CourseRestController {
    @Autowired
    private CourseService courseService;


    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody Course course, @RequestParam Long instructorId) {
        if (courseService.create(course, instructorId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Course course) {
        if (courseService.update(id, course)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (courseService.delete(id)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable("id") Long id) {
        Course course = courseService.getById(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }
    @GetMapping("/")
    public List<Course> getAll() {
        return courseService.getAll();
    }
}
