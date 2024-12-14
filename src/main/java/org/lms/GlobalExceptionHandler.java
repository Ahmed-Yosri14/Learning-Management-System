package org.lms;

import org.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private CourseService courseService;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleInvalidCourse(@PathVariable("courseId") Long courseId) {
        if (courseService.getById(courseId) == null) {
            return ResponseEntity.status(404).body("Course not found");
        }
        return ResponseEntity.status(400).body("Invalid request");
    }
}