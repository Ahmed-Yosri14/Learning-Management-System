package org.lms.controller;

import org.lms.entity.Course;
import org.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/course")
public class CourseRestController {
    @Autowired
    private CourseService courseService;

    @PutMapping("/")
    public void createCourse(Course course) {
        courseService.createCourse(course);
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
