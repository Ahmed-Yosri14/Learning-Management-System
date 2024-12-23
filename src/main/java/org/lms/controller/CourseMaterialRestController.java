package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.CourseMaterial;
import org.lms.service.CourseMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/course/{courseId}/material")
public class CourseMaterialRestController {
    @Autowired
    private CourseMaterialService courseMaterialService;

    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private EntityMapper entityMapper;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("")
    public ResponseEntity<String> addMaterial(@PathVariable Long courseId, @RequestParam("file") MultipartFile file){
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (courseMaterialService.create(courseId, file)){
            return ResponseEntity.ok("Material uploaded successfully!");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long courseId, @PathVariable Long id){
        if (!authorizationManager.isInstructor(courseId)){
            return ResponseEntity.status(403).build();
        }
        if (courseMaterialService.delete(courseId, id)) {
            return ResponseEntity.ok("Material deleted successfully!");
        }
        return ResponseEntity.badRequest().body("Failed to delete material.");
    }
    @PreAuthorize("hasRole('INSTRUCTOR')||hasRole('STUDENT')||hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Object> getMaterialsByCourse(@PathVariable Long courseId) {
        if (!authorizationManager.hasAccess(courseId)){
            return ResponseEntity.status(403).build();
        }
        List<CourseMaterial> courseMaterialList = courseMaterialService.getAllByCourseId(courseId);
        if (courseMaterialList != null) {
            return ResponseEntity.ok(entityMapper.map(new ArrayList<>(courseMaterialList)));
        }
        return ResponseEntity.notFound().build();
    }
}