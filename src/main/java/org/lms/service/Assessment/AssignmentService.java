package org.lms.service.Assessment;

import io.jsonwebtoken.lang.Assert;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Course;
import org.lms.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService extends AssessmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;


    public boolean create(Long courseId,Assignment assignment)
    {
        try{
            Assert.isTrue(super.createAssessment(courseId, assignment));
            Course course = courseService.getById(courseId);
            notificationService.createToAllEnrolled(
                    courseId,
                    "New Assignment",
                    "New assignment was just added in \'" + course.getName() + "\'!"
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
            Assert.isTrue(super.updateAssessment(courseId, id, assignment));
            return true;
        }
        catch (Exception e){}
        return false;
    }
    public boolean deleteAssessment(Long courseId, Long id) {
        try {
            Assert.isTrue(super.deleteAssessment(courseId, id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Assignment getById(Long courseId,Long id)
    {
        try {
            Assert.isTrue(super.existsById(courseId, id));
            return (Assignment) super.getById(id);
        } catch (Exception e) {
            System.out.println("Error retrieving assignment: " + e.getMessage());
            return null;
        }
    }
    public List<Assignment> getAll(Long courseId){
        try {
            Assert.isTrue(courseService.existsById(courseId));
            Course course = courseService.getById(courseId);

            return assignmentRepository.findAllByCourse(course);
        } catch (Exception e) {
            System.out.println("Error fetching assignments for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
}