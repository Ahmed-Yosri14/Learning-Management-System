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
            if (courseService.createCourse(course)){
                return "Course created successfully";
            }
            else {
                return "Course could not be created";
            }
        }
    }
    @PatchMapping("/{id}")
    public String updateCourse(@PathVariable("id") Long id, @Valid Course course, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().toString();
        }
        else {
            course.setId(id);
            if (courseService.updateCourse(course)){
                return "Course updated successfully!";
            }
            else {
                return "Course not found!";
            }
        }
    }
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        if (courseService.deleteCourse(id)){
            return "Course deleted successfully!";
        }
        else {
            return "Course not found!";
        }
    }
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }
    @GetMapping("/")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }
}
