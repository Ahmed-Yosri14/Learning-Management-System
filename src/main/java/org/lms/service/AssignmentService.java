package org.lms.service;

import org.lms.entity.Assignment;
import org.lms.entity.Course;
import org.lms.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private NotificationService notificationService;

    public boolean existsById(Long courseId, Long id) {
        Course course = courseService.getById(courseId);
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        return assignmentRepository.existsById(id)
                && courseService.existsById(id)
                && course.getId().equals(assignment.getCourse().getId());
    }

    public boolean create(Long courseId,Assignment assignment)
    {
        try{
            assert courseService.existsById(courseId);
            Course course = courseService.getById(courseId);

            assignment.setCourse(course);
            assignmentRepository.save(assignment);
            notificationService.createToAllEnrolled(
                    courseId,
                    "New Assignment",
                    "New quiz was just added in \'" + course.getName() + "\'!"
            );
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean update(Long courseId, Long id, Assignment assignment)
    {
        try{
            assert existsById(courseId, id);

            Assignment oldAssignment = assignmentRepository.findById(id).get();
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

    public Assignment getById(Long id)
    {
        return assignmentRepository.findById(id).orElse(null);
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
            return assignmentRepository.findAllByCourse(course);
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