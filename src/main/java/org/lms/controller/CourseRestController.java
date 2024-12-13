package org.lms.controller;

import jakarta.validation.Valid;
import org.lms.entity.Course;
import org.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/course")
public class CourseRestController {
    @Autowired
    private CourseService courseService;

    @PutMapping("/")
    public String createCourse(@Valid Course course, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        else {
            courseService.createCourse(course);
            return "Course created successfully!";
        }
    }
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        return courseService.getCourse(id);
    }
    @GetMapping("/")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }
}
