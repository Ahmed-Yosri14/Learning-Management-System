package org.lms.service;

import org.lms.entity.Assignment;
import org.lms.entity.Course;
import org.lms.entity.Lesson;
import org.lms.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseService courseService;
    public boolean create(Long courseId,Assignment assignment)
    {
        try{
            if(courseService.getById(courseId) == null){
                System.out.println("course not found");
                return false;
            }
            System.out.println(assignment);
            assignment.setCourse(courseService.getById(courseId));
            assignmentRepository.save(assignment);
            return true;
        }catch (Exception e){
        }
        return false;
    }

    public boolean update(Long courseid,Long id,Assignment assignment)
    {
        try{
            Assignment oldAssignment = assignmentRepository.findById(id).get();
            Course oldCourse = courseService.getById(courseid);
            if(oldCourse == null || !Objects.equals(oldCourse.getId(), oldAssignment.getCourse().getId())){
                return false;
            }
            if (assignment.getTitle() != null){
                oldAssignment.setTitle(assignment.getTitle());
            }
            if (assignment.getDescription() != null){
                oldAssignment.setDescription(assignment.getDescription());
            }
            if (assignment.getDuration() != null){
                oldAssignment.setDuration(assignment.getDuration());
            }
            if(assignment.getContent() != null){
                oldAssignment.setContent(assignment.getContent());
            }
            assignmentRepository.save(oldAssignment);
            return true;
        }
        catch (Exception e){}
        return false;
    }

    public Assignment getById(Long courseId,Long id)
    {
        try {
            Assignment assignment = assignmentRepository.findById(id).get();
            if (assignment != null && assignment.getCourse() != null && assignment.getCourse().getId().equals(courseId)) {
                return assignment;
            } else {
                System.out.println("assignment not found or does not belong to the specified course.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving assignment: " + e.getMessage());
            return null;
        }
    }
    public List<Assignment> getAll(Long courseId){
        try {
            Course course = courseService.getById(courseId);
            if(course == null){
                return null;
            }
            return assignmentRepository.findByCourse(course);
        } catch (Exception e) {
            System.out.println("Error fetching assignments for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    public boolean delete(Long courseId, Long id) {
        try {
            if (courseService.getById(courseId)==null || !assignmentRepository.existsById(id)) {
                return false;
            }
            Assignment assignment = assignmentRepository.findById(id).orElse(null);
            if (assignment == null || !assignment.getCourse().getId().equals(courseId)) {
                return false;
            }
            assignmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
