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


    public boolean create(Lesson lesson, Long courseId) {
        try{
            if (courseService.getById(courseId) == null) {
                System.out.println("Instructor not found");
                return false;
            }
            lesson.setCourse((Course) courseService.getById(courseId));
            lessonRepository.save(lesson);
            return true;
        }
        catch(Exception e){}
        return false;
    }
    public boolean update(Long id, Lesson lesson,Long courseId) {
        try {
            Lesson oldLesson = lessonRepository.findById(id).get();
            Course oldCourse = courseService.getById(courseId);
            if(oldCourse.getId()!=oldLesson.getCourse().getId()){
                return false;
            }
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
            if (courseService.getById(courseId) == null || !lessonRepository.existsById(id)) {
                return false;
            }
            Lesson lesson = lessonRepository.findById(id).orElse(null);
            if (lesson == null || !lesson.getCourse().getId().equals(courseId)) {
                return false;
            }
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
            if (lesson != null && lesson.getCourse() != null && lesson.getCourse().getId().equals(courseId)) {
                return lesson;
            } else {
                System.out.println("Lesson not found or does not belong to the specified course.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving lesson: " + e.getMessage());
            return null;
        }
    }

    public List<Lesson> getAll(Long courseId) {
        try {
            return lessonRepository.findByCourseId(courseId);
        } catch (Exception e) {
            System.out.println("Error fetching lessons for course ID " + courseId + ": " + e.getMessage());
            return null;
        }
    }
    public String generateOtp(Long courseId, Long id){
        try {
            Lesson lesson = lessonRepository.findById(id).orElse(null);
            assert lesson != null && lesson.getCourse().getId().equals(courseId);
            Random random = new Random();
            String otp = generateRandomNumber();
            while (lessonRepository.existsByOtp(otp)) {
                otp = generateRandomNumber();
            }
            lesson.setOpt(otp);
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
    public boolean checkLessonOtp(Long id, Long otp){
        try {
            assert otp != null;
            Lesson lesson = lessonRepository.findById(id).orElse(null);
            return lesson.getOpt().equals(otp);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}
