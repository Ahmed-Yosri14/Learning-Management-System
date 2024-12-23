package org.lms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.controller.AssignmentRestController;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Course;
import org.lms.service.Assessment.AssignmentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentRestControllerTest {

    @InjectMocks
    private AssignmentRestController assignmentRestController;

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private EntityMapper entityMapper;

    private Assignment mockAssignment;
    private Course mockCourse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCourse = new Course();
        mockCourse.setId(1L);
        mockCourse.setName("Mock Course");

        mockAssignment = new Assignment();
        mockAssignment.setId(1L);
        mockAssignment.setTitle("Test Assignment");
        mockAssignment.setDescription("Test Description");
        mockAssignment.setCourse(mockCourse);
    }

    @Test
    void testCreateAssignment() {
        Long courseId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(true);
        when(assignmentService.create(courseId, mockAssignment)).thenReturn(true);

        ResponseEntity<String> response = assignmentRestController.createAssignment(courseId, mockAssignment);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All good!", response.getBody());
    }

    @Test
    void testCreateAssignmentUnauthorized() {
        Long courseId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(false);

        ResponseEntity<String> response = assignmentRestController.createAssignment(courseId, mockAssignment);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("You do not have permission to edit this course.", response.getBody());
    }

    @Test
    void testGetById() {
        Long courseId = 1L;
        Long assignmentId = 1L;

        when(authorizationManager.hasAccess(courseId)).thenReturn(true);
        when(assignmentService.getById(courseId, assignmentId)).thenReturn(mockAssignment);
        when(entityMapper.map(mockAssignment)).thenReturn(mockAssignment.toMap(null)); // Adjust as needed for UserRole

        ResponseEntity<Object> response = assignmentRestController.getById(courseId, assignmentId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetByIdNotFound() {
        Long courseId = 1L;
        Long assignmentId = 1L;

        when(authorizationManager.hasAccess(courseId)).thenReturn(true);
        when(assignmentService.getById(courseId, assignmentId)).thenReturn(null);

        ResponseEntity<Object> response = assignmentRestController.getById(courseId, assignmentId);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllAssignments() {
        Long courseId = 1L;
        List<Assignment> assignments = new ArrayList<>();
        assignments.add(mockAssignment);

        when(authorizationManager.hasAccess(courseId)).thenReturn(true);
        when(assignmentService.getAll(courseId)).thenReturn(assignments);

        ResponseEntity<Object> response = assignmentRestController.getAll(courseId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteAssignment() {
        Long courseId = 1L;
        Long assignmentId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(true);
        when(assignmentService.deleteAssessment(courseId, assignmentId)).thenReturn(true);

        ResponseEntity<String> response = assignmentRestController.deleteAssignment(courseId, assignmentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All good!", response.getBody());
    }

    @Test
    void testDeleteAssignmentUnauthorized() {
        Long courseId = 1L;
        Long assignmentId = 1L;

        when(authorizationManager.isInstructor(courseId)).thenReturn(false);

        ResponseEntity<String> response = assignmentRestController.deleteAssignment(courseId, assignmentId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("You do not have permission to edit this course.", response.getBody());
    }
}