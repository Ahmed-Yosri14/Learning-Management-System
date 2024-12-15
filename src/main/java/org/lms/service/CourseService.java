package org.lms.service;

import org.lms.entity.Course;
import org.lms.entity.Instructor;
import org.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;


    public boolean create(Course course, Long instructorId) {
        try{
            if (!(userService.getById(instructorId) instanceof Instructor)) {
                System.out.println("Instructor not found");
                return false;
            }
            course.setInstructor((Instructor)userService.getById(instructorId));
            courseRepository.save(course);
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean update(Long id, Course course, Long instructorId) {
        try {
            Course oldCourse = courseRepository.findById(id).get();
            assert oldCourse != null && oldCourse.getInstructor().getId() == instructorId;
            if (course.getName() != null){
                oldCourse.setName(course.getName());
            }
            if (course.getDescription() != null){
                oldCourse.setDescription(course.getDescription());
            }
            if (course.getDuration() != null){
                oldCourse.setDuration(course.getDuration());
            }
            courseRepository.save(oldCourse);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long id, Long instructorId) {
        try {
            Course oldCourse = courseRepository.findById(id).get();
            assert oldCourse != null && oldCourse.getInstructor().getId() == instructorId;
            courseRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public Course getById(Long id) {
        return courseRepository.findById(id).get();
    }
    public List<Course> getAll() {
        return courseRepository.findAll();
    }
}
