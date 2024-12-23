package org.lms.service.Assessment;

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

    public boolean create(Long courseId, Assignment assignment) {
        try {
            if (super.createAssessment(courseId, assignment)) {
                Course course = courseService.getById(courseId);
                notificationService.createToAllEnrolled(
                        courseId,
                        "New Assignment",
                        "New assignment was just added in '" + course.getName() + "'!"
                );
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean update(Long courseId, Long id, Assignment assignment) {
        return super.updateAssessment(courseId, id, assignment);
    }

    public boolean deleteAssessment(Long courseId, Long id) {
        return super.deleteAssessment(courseId, id);
    }

    public Assignment getById(Long courseId, Long id) {
        try {
            if (!super.existsById(courseId, id)) {
                return null;
            }
            return (Assignment) super.getById(id);
        } catch (Exception e) {
            System.out.println("Error retrieving assignment: " + e.getMessage());
            return null;
        }
    }

    public List<Assignment> getAll(Long courseId) {
        try {
            if (!courseService.existsById(courseId)) {
                return null;
            }
            Course course = courseService.getById(courseId);
            return assignmentRepository.findAllByCourse(course);
        } catch (Exception e) {
            System.out.println("Error fetching assignments for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
}