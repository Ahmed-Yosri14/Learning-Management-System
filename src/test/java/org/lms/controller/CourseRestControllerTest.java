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

    @Test
    public void testUpdateCourse() throws Exception {
        when(courseService.existsById(1L)).thenReturn(true);
        when(authorizationManager.isInstructor(1L)).thenReturn(false);
        when(courseService.update(eq(1L), any(Course.class))).thenReturn(true);

        mockMvc.perform(patch("/api/course/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Course\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Course updated successfully!"));

        verify(courseService, times(1)).update(eq(1L), any(Course.class));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        when(courseService.existsById(1L)).thenReturn(true);
        when(authorizationManager.isInstructor(1L)).thenReturn(false);
        when(courseService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/course/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Course deleted successfully!"));

        verify(courseService, times(1)).delete(1L);
    }
}
