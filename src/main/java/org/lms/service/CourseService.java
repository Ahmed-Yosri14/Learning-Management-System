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
    public boolean createCourse(Course course) {
        try{
            courseRepository.save(course);
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean updateCourse(Course course) {
        try {
            if (courseRepository.existsById(course.getId())) {
                courseRepository.save(course);
            }
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean deleteCourse(Long id) {
        try {
            if (courseRepository.existsById(id)) {
                courseRepository.deleteById(id);
            }
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
