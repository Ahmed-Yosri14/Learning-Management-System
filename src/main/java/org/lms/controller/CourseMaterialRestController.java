package org.lms.controller;
import org.lms.AuthorizationManager;
import org.lms.entity.CourseMaterial;
import org.lms.repository.CourseMaterialRepository;
import org.lms.repository.CourseRepository;
import org.lms.entity.Course;
import org.lms.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/course")
public class CourseMaterialRestController {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private AuthorizationManager authorizationManager;

    @PostMapping("/{courseId}/materials")
    public ResponseEntity<String> addMaterial(@PathVariable Long courseId, @RequestParam("file") MultipartFile file){
        try {
            if(!authorizationManager.checkCourseEdit(courseId)){
                throw(new RuntimeException("User does not have permission to add material to this course"));
            }
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            String filePath = fileStorageService.storeFile(file);

            CourseMaterial material = new CourseMaterial();
            material.setFileName(file.getOriginalFilename());
            material.setFilePath(filePath);
            material.setCourse(course);

            courseMaterialRepository.save(material);

            return ResponseEntity.ok("Material uploaded successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{courseId}/{materialsId}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long courseId, @PathVariable Long materialsId){
        try {
            if (!authorizationManager.checkCourseEdit(courseId)) {
                throw new RuntimeException("User does not have permission to delete material from this course");
            }

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            CourseMaterial material = courseMaterialRepository.findById(materialsId)
                    .orElseThrow(() -> new RuntimeException("Material not found"));

            if (!material.getCourse().getId().equals(courseId)) {
                throw new RuntimeException("Material does not belong to the specified course");
            }

            courseMaterialRepository.delete(material);

            return ResponseEntity.ok("Material deleted successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/{courseId}/materials")
    public ResponseEntity<List<CourseMaterial>> getMaterialsByCourse(@PathVariable Long courseId) {
        List<CourseMaterial> materials = courseMaterialRepository.findByCourseId(courseId);

        if (materials.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(materials);
    }

}
