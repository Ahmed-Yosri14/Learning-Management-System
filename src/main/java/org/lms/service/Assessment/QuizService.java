package org.lms.service.Assessment;

import io.jsonwebtoken.lang.Assert;
import org.lms.entity.Assessment.Quiz;
import org.lms.entity.Course;
import org.lms.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService extends AssessmentService {
    @Autowired
    private QuizRepository quizRepository;

    public boolean create(Long courseId, Quiz quiz)
    {
        try{
            Assert.isTrue(super.createAssessment(courseId, quiz));
            Course course = courseService.getById(courseId);
            notificationService.createToAllEnrolled(
                    courseId,
                    "New Quiz",
                    "New quiz was just added in \'" + course.getName() + "\'!"
            );
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean update(Long courseId, Long id, Quiz quiz)
    {
        try{
            Assert.isTrue(super.updateAssessment(courseId, id, quiz));
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
    public Quiz getById(Long courseId, Long id)
    {
        try {
            Assert.isTrue(super.existsById(courseId, id));
            return (Quiz) super.getById(id);
        } catch (Exception e) {
            System.out.println("Error retrieving quiz: " + e.getMessage());
            return null;
        }
    }
    public List<Quiz> getAll(Long courseId){
        try {
            Assert.isTrue(courseService.existsById(courseId));
            Course course = courseService.getById(courseId);

            return quizRepository.findAllByCourse(course);
        } catch (Exception e) {
            System.out.println("Error fetching quiz's for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    public Double getFullMark(Long id){
        return quizRepository.findFullMark(id);
    }
}