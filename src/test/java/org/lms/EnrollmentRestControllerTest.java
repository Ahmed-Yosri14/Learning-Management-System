package org.lms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.controller.EnrollmentRestController;
import org.lms.service.EnrollmentService;
import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EnrollmentRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private EnrollmentRestController enrollmentRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentRestController).build();
    }

    @Test
    public void testEnrollStudent() throws Exception {
        when(authorizationManager.getCurrentUserId()).thenReturn(1L);
        when(enrollmentService.create(1L, 101L)).thenReturn(true);

        mockMvc.perform(put("/api/course/101/enrollment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All good!"));

        verify(enrollmentService, times(1)).create(1L, 101L);
    }

    @Test
    public void testUnenrollStudent() throws Exception {
        when(authorizationManager.getCurrentUserId()).thenReturn(1L);
        when(enrollmentService.delete(1L, 101L)).thenReturn(true);

        mockMvc.perform(delete("/api/course/101/enrollment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All good!"));

        verify(enrollmentService, times(1)).delete(1L, 101L);
    }

    @Test
    public void testDeleteEnrollmentByInstructor() throws Exception {
        when(authorizationManager.isInstructor(101L)).thenReturn(true);
        when(enrollmentService.delete(2L, 101L)).thenReturn(true);

        mockMvc.perform(delete("/api/course/101/enrollment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All good!"));

        verify(enrollmentService, times(1)).delete(2L, 101L);
    }

    @Test
    public void testDeleteEnrollmentByNonInstructor() throws Exception {
        when(authorizationManager.isInstructor(101L)).thenReturn(false);

        mockMvc.perform(delete("/api/course/101/enrollment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(enrollmentService, never()).delete(anyLong(), anyLong());
    }

    @Test
    public void testGetEnrollmentByStudentIdAsAuthorizedUser() throws Exception {
        when(authorizationManager.isAdminOrInstructor(101L)).thenReturn(true);
        when(enrollmentService.checkStudentId(2L, 101L)).thenReturn(true);

        mockMvc.perform(get("/api/course/101/enrollment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Student is enrolled in the course!"));

        verify(enrollmentService, times(1)).checkStudentId(2L, 101L);
    }

    @Test
    public void testGetEnrollmentByStudentIdAsUnauthorizedUser() throws Exception {
        when(authorizationManager.isAdminOrInstructor(101L)).thenReturn(false);

        mockMvc.perform(get("/api/course/101/enrollment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(enrollmentService, never()).checkStudentId(anyLong(), anyLong());
    }

    @Test
    public void testGetAllEnrollmentsAsAuthorizedUser() throws Exception {
        when(authorizationManager.isAdminOrInstructor(101L)).thenReturn(true);
        when(enrollmentService.getByCourseId(101L)).thenReturn(Collections.emptyList());
        when(entityMapper.map(anyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/course/101/enrollment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        verify(enrollmentService, times(1)).getByCourseId(101L);
        verify(entityMapper, times(1)).map(anyList());
    }

    @Test
    public void testGetAllEnrollmentsAsUnauthorizedUser() throws Exception {
        when(authorizationManager.isAdminOrInstructor(101L)).thenReturn(false);

        mockMvc.perform(get("/api/course/101/enrollment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(enrollmentService, never()).getByCourseId(anyLong());
        verify(entityMapper, never()).map(anyList());
    }
}
