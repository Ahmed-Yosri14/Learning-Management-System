package org.lms.controller;
import org.lms.entity.CourseMaterial;
import org.lms.repository.CourseMaterialRepository;
import org.lms.repository.CourseRepository;
import org.lms.repository.CourseRepository;
import org.lms.entity.Course;
import org.lms.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/courses")
public class CourseMaterialController {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/{courseId}/materials")
    public ResponseEntity<String> addMaterial(@PathVariable Long courseId, @RequestParam("file") MultipartFile file) {
        try {
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
}
