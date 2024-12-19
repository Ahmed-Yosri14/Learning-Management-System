package org.lms.service;
import org.lms.entity.Course;
import org.lms.entity.Lesson;
import org.lms.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationService notificationService;

    boolean existsById(Long id, Long courseId) {
        Course course = courseService.getById(courseId);
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        return lessonRepository.existsById(id)
                && courseService.existsById(id)
                && course.getId().equals(lesson.getCourse().getId());
    }
    public boolean create(Lesson lesson, Long courseId) {
        try {
            Course course = courseService.getById(courseId);
            lesson.setCourse(course);
            lesson = lessonRepository.save(lesson);
            generateOtp(courseId, lesson.getId());
            notificationService.createToAllEnrolled(
                    courseId,
                    "New Lesson",
                    "New lesson just started in \'" + course.getName() + "\'!"
            );
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean update(Long id, Lesson lesson, Long courseId) {
        try {
            assert existsById(id, courseId);

            Lesson oldLesson = lessonRepository.findById(id).get();
            if (lesson.getTitle() != null){
                oldLesson.setTitle(lesson.getTitle());
            }
            if (lesson.getDescription() != null){
                oldLesson.setDescription(lesson.getDescription());
            }
            if (lesson.getDuration() != null){
                oldLesson.setDuration(lesson.getDuration());
            }
            lessonRepository.save(oldLesson);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long courseId, Long id) {
        try {
            assert existsById(id, courseId);

            lessonRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Lesson getById(Long id) {
        try {
            return lessonRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println("Error retrieving lesson: " + e.getMessage());
            return null;
        }
    }
    public Lesson getById(Long courseId, Long id) {
        try {
            Lesson lesson = lessonRepository.findById(id).orElse(null);
            assert existsById(id, courseId);
            return lesson;
        } catch (Exception e) {
            System.out.println("Error retrieving lesson: " + e.getMessage());
            return null;
        }
    }
    public List<Lesson> getAll(Long courseId) {
        try {
            return lessonRepository.findAllByCourseId(courseId);
        } catch (Exception e) {
            System.out.println("Error fetching lessons for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    public String generateOtp(Long courseId, Long id){
        try {
            assert existsById(id, courseId);

            Lesson lesson = lessonRepository.findById(id).orElse(null);
            String otp = generateRandomNumber();
            while (lessonRepository.existsByOtp(otp)) {
                otp = generateRandomNumber();
            }
            lesson.setOtp(otp);
            lessonRepository.save(lesson);
            return otp;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    private String generateRandomNumber(){
        Random random = new Random();
        int number = random.nextInt(1000000);
        return String.format("%06d", number);
    }
}