package org.lms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.controller.AttendanceRestController;
import org.lms.dao.request.AttendanceRequest;
import org.lms.service.AttendanceService;
import org.lms.AuthorizationManager;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AttendanceRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private AuthorizationManager authorizationManager;

    @InjectMocks
    private AttendanceRestController attendanceRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceRestController).build();
    }

    @Test
    public void testAttendLesson() throws Exception {
        AttendanceRequest request = new AttendanceRequest();
        request.setOtp("1234");

        when(authorizationManager.isEnrolled(1L)).thenReturn(true);
        when(attendanceService.tryRecord(1L, 2L, 1L, "1234")).thenReturn(true);
        when(authorizationManager.getCurrentUserId()).thenReturn(1L);

        mockMvc.perform(put("/api/course/{courseId}/lesson/{lessonId}/attendance", 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"otp\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Lesson Attended"));

        verify(attendanceService, times(1)).tryRecord(1L, 2L, 1L, "1234");
    }

    @Test
    public void testGetAttendanceByStudentId() throws Exception {
        when(authorizationManager.isAdminOrInstructor(1L)).thenReturn(true);
        when(attendanceService.checkStudentId(3L, 2L, 1L)).thenReturn(true);

        mockMvc.perform(get("/api/course/{courseId}/lesson/{lessonId}/attendance/{studentId}", 1L, 2L, 3L))
                .andExpect(status().isOk())
                .andExpect(content().string("Student has attended the lesson!"));

        verify(attendanceService, times(1)).checkStudentId(3L, 2L, 1L);
    }

}
