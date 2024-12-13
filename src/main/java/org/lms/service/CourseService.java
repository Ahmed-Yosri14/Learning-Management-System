package org.lms.service;

import org.lms.entity.Course;
import org.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public void createCourse(Course course) {
        courseRepository.save(course);
    }
    public void editCourse(Course course) {
        if (courseRepository.findById(course.getId()) == null){
            return;
        }
        courseRepository.save(course);
    }
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
    public Course getCourse(Long id) {
        return courseRepository.findById(id).get();
    }
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
