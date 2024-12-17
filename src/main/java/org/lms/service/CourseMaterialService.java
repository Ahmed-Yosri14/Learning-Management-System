package org.lms.service;

import org.lms.entity.Course;
import org.lms.entity.CourseMaterial;
import org.lms.repository.CourseMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CourseMaterialService {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private NotificationService notificationService;

    public CourseMaterial getById(Long id){
        return courseMaterialRepository.findById(id).orElse(null);
    }
    public List<CourseMaterial> getAllByCourseId(Long courseId){
        try {
            assert courseService.existsById(courseId);
            return courseMaterialRepository.findAllByCourseId(courseId);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public boolean existsById(Long courseId, Long id){
        CourseMaterial courseMaterial = getById(id);
        return courseService.existsById(courseId)
                && courseMaterialRepository.existsById(id)
                && courseMaterial.getCourse().getId().equals(courseId);
    }
    public boolean create(Long courseId, MultipartFile file){
        try {
            assert courseService.existsById(courseId);

            Course course = courseService.getById(courseId);
            String filePath = fileStorageService.storeFile(file);
            CourseMaterial courseMaterial = new CourseMaterial();
            courseMaterial.setFileName(file.getOriginalFilename());
            courseMaterial.setFilePath(filePath);
            courseMaterial.setCourse(course);
            courseMaterialRepository.save(courseMaterial);
            notificationService.createToAllEnrolled(
                    courseId,
                    "New material uploaded",
                    "New material was just added to \'" + course.getName() + "\'!"
            );
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean delete(Long courseId, Long id){
        try {
            assert existsById(courseId, id);
            courseMaterialRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}
