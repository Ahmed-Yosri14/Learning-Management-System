package org.lms.service;

import org.lms.entity.Course;
import org.lms.entity.Assessment.Quiz;
import org.lms.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationService notificationService;

    public boolean create(Long courseId, Quiz quiz)
    {
        try{
            assert courseService.existsById(courseId);
            Course course = courseService.getById(courseId);
            quiz.setCourse(course);
            quizRepository.save(quiz);
            notificationService.createToAllEnrolled(
                    courseId,
                    "New Quiz",
                    "New quiz just started in \'" + course.getName() + "\'!"
            );
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean update(Long courseid,Long id,Quiz quiz)
    {
        try{
            Quiz oldQuiz = quizRepository.findById(id).get();
            Course oldCourse = courseService.getById(courseid);
            if(oldCourse == null || !Objects.equals(oldCourse.getId(), oldQuiz.getCourse().getId())){
                return false;
            }
            if (quiz.getTitle() != null){
                oldQuiz.setTitle(quiz.getTitle());
            }
            if (quiz.getDescription() != null){
                oldQuiz.setDescription(quiz.getDescription());
            }
            if (quiz.getDuration() != null){
                oldQuiz.setDuration(quiz.getDuration());
            }
            quizRepository.save(oldQuiz);
            return true;
        }
        catch (Exception e){}
        return false;
    }

    public Quiz getById(Long courseId,Long id)
    {
        try {
            Quiz quiz = quizRepository.findById(id).get();
            if (quiz.getCourse() != null && quiz.getCourse().getId().equals(courseId)) {
                return quiz;
            } else {
                System.out.println("quiz not found or does not belong to the specified course.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving quiz: " + e.getMessage());
            return null;
        }
    }
    public List<Quiz> getAll(Long courseId){
        try {
            Course course = courseService.getById(courseId);
            if(course == null){
                return null;
            }
            return quizRepository.findAllByCourse(course);
        } catch (Exception e) {
            System.out.println("Error fetching quiz for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    public boolean delete(Long courseId, Long id) {
        try {
            if (courseService.getById(courseId)==null || !quizRepository.existsById(id)) {
                return false;
            }
            Quiz quiz = quizRepository.findById(id).orElse(null);
            if (quiz == null || !quiz.getCourse().getId().equals(courseId)) {
                return false;
            }
            quizRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Double getFullMark(Long id){
        return quizRepository.findFullMark(id);
    }
}