package org.lms.controller;

import org.lms.entity.Course;
import org.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/course/")
public class CourseRestController {
    @Autowired
    private CourseService courseService;


    // instructor
    @PutMapping("")
    public ResponseEntity<String> create(@RequestBody Course course, @RequestParam Long instructorId) {
        if (courseService.create(course, instructorId)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");

    }
    // instructor
    @PatchMapping("{id}/")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Course course) {
        if (courseService.update(id, course, 1L)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }
    // instructor
    @DeleteMapping("{id}/")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (courseService.delete(id, 1L)){
            return ResponseEntity.ok("All good!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    // all
    @GetMapping("{id}/")
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
