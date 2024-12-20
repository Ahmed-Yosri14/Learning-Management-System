package org.lms.controller;

import org.lms.AuthorizationManager;
import org.lms.service.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/course/{courseId}/visualize")
public class VisualizationRestController {
    @Autowired
    private VisualizationService visualizationService;
    @Autowired
    private AuthorizationManager authorizationManager;
    @GetMapping("")
    ResponseEntity<String> getVisualization(@PathVariable("courseId") Long courseId) throws IOException {
        try{
            if(authorizationManager.isAdminOrInstructor(courseId)){
                String filePath = visualizationService.generatePieChart(courseId);
                return ResponseEntity.ok("Pie chart is generated successfully at " + filePath);
            }
            else{
                return ResponseEntity.status(403).body("You are not allowed to generate sheets");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong while creating the PieChart.");
        }
    }
}
