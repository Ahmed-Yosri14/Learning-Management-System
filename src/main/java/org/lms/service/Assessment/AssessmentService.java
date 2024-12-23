package org.lms.service.Assessment;

import org.lms.entity.Assessment.Assessment;
import org.lms.entity.Course;
import org.lms.repository.AssessmentRepository;
import org.lms.service.CourseService;
import org.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    public CourseService courseService;
    @Autowired
    protected NotificationService notificationService;

    public boolean existsById(Long courseId, Long id) {
        Course course = courseService.getById(courseId);
        Assessment assessment = assessmentRepository.findById(id).orElse(null);
        return assessmentRepository.existsById(id)
                && courseService.existsById(courseId)
                && course != null && assessment != null
                && course.getId().equals(assessment.getCourse().getId());
    }

    public boolean createAssessment(Long courseId, Assessment assessment) {
        try {
            if (!courseService.existsById(courseId)) {
                return false;
            }
            Course course = courseService.getById(courseId);
            assessment.setCourse(course);
            assessmentRepository.save(assessment);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateAssessment(Long courseId, Long id, Assessment assessment) {
        try {
            if (!existsById(courseId, id)) {
                return false;
            }

            Assessment oldAssessment = assessmentRepository.findById(id).get();
            if (assessment.getTitle() != null) {
                oldAssessment.setTitle(assessment.getTitle());
            }
            if (assessment.getDescription() != null) {
                oldAssessment.setDescription(assessment.getDescription());
            }
            if (assessment.getDuration() != null) {
                oldAssessment.setDuration(assessment.getDuration());
            }
            assessmentRepository.save(oldAssessment);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteAssessment(Long courseId, Long id) {
        try {
            if (!existsById(courseId, id)) {
                return false;
            }
            assessmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Assessment getById(Long id) {
        return assessmentRepository.findById(id).orElse(null);
    }
}