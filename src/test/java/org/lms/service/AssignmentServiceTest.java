package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.entity.Assessment.Assignment;
import org.lms.entity.Course;
import org.lms.entity.User.Instructor;
import org.lms.repository.AssessmentRepository;
import org.lms.repository.AssignmentRepository;
import org.lms.service.Assessment.AssignmentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AssignmentService assignmentService;

    private Course course;
    private Assignment assignment;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");

        course = new Course();
        course.setId(1L);
        course.setName("Java Programming");
        course.setDescription("Learn Java");
        course.setDuration(12L);
        course.setInstructor(instructor);

        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Java Basics Assignment");
        assignment.setDescription("Complete Java exercises");
        assignment.setDuration(60L);
        assignment.setCourse(course);
    }

    @Test
    void create_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        boolean result = assignmentService.create(1L, assignment);

        assertTrue(result);
        verify(assessmentRepository).save(any(Assignment.class));
        verify(notificationService).createToAllEnrolled(anyLong(), anyString(), anyString());
    }

    @Test
    void update_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.existsById(1L)).thenReturn(true);
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assessmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        Assignment updatedAssignment = new Assignment();
        updatedAssignment.setTitle("Updated Title");
        updatedAssignment.setDescription("Updated Description");

        boolean result = assignmentService.update(1L, 1L, updatedAssignment);

        assertTrue(result);
        verify(assessmentRepository).save(any(Assignment.class));
    }

    @Test
    void deleteAssessment_ShouldReturnTrue_WhenSuccessful() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.existsById(1L)).thenReturn(true);
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        doNothing().when(assessmentRepository).deleteById(1L);

        boolean result = assignmentService.deleteAssessment(1L, 1L);

        assertTrue(result);
        verify(assessmentRepository).deleteById(1L);
    }

    @Test
    void getById_ShouldReturnAssignment_WhenExists() {
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assessmentRepository.existsById(1L)).thenReturn(true);
        when(assessmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        Assignment result = assignmentService.getById(1L, 1L);

        assertNotNull(result);
        assertEquals(assignment.getId(), result.getId());
    }

    @Test
    void getAll_ShouldReturnListOfAssignments() {
        List<Assignment> assignments = Arrays.asList(assignment);
        when(courseService.existsById(1L)).thenReturn(true);
        when(courseService.getById(1L)).thenReturn(course);
        when(assignmentRepository.findAllByCourse(course)).thenReturn(assignments);

        List<Assignment> result = assignmentService.getAll(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void create_ShouldReturnFalse_WhenCourseDoesNotExist() {
        when(courseService.existsById(1L)).thenReturn(false);

        boolean result = assignmentService.create(1L, assignment);

        assertFalse(result);
        verify(assessmentRepository, never()).save(any(Assignment.class));
    }

    @Test
    void getAll_ShouldReturnNull_WhenCourseDoesNotExist() {
        when(courseService.existsById(1L)).thenReturn(false);

        List<Assignment> result = assignmentService.getAll(1L);

        assertNull(result);
    }

    @Test
    void create_ShouldReturnFalse_WhenExceptionOccurs() {
        when(courseService.existsById(1L)).thenThrow(new RuntimeException());

        boolean result = assignmentService.create(1L, assignment);

        assertFalse(result);
    }
}