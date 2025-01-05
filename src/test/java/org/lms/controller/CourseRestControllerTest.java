package org.lms.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.entity.Course;
import org.lms.service.CourseService;
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

public class CourseRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @Mock
    private AuthorizationManager authorizationManager;

    @InjectMocks
    private CourseRestController courseRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseRestController).build();
    }

    @Test
    public void testCreateCourse() throws Exception {
        when(courseService.create(any(Course.class), anyLong())).thenReturn(true);
        when(authorizationManager.getCurrentUserId()).thenReturn(1L);

        mockMvc.perform(put("/api/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Course\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Course created successfully"));

        verify(courseService, times(1)).create(any(Course.class), eq(1L));
    }




}
