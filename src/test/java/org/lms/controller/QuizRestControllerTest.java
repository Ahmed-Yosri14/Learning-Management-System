package org.lms.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.service.Assessment.QuizService;
import org.lms.AuthorizationManager;
import org.lms.service.QuestionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class QuizRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuizService quizService;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuizRestController quizRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initializes mocks
        mockMvc = MockMvcBuilders.standaloneSetup(quizRestController).build();
    }


    @Test
    public void testRemoveQuestionFromQuiz() throws Exception {
        List<Long> questionIds = Arrays.asList(1L, 2L);

        when(quizService.existsById(1L, 1L)).thenReturn(true);
        when(authorizationManager.isInstructor(1L)).thenReturn(true);
        when(quizService.removeQuestionsFromQuiz(1L, 1L, questionIds)).thenReturn(true);

        mockMvc.perform(delete("/api/course/{courseid}/quiz/{quizId}/deletequestions", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2]")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Questions removed from quiz successfully!"));

        verify(quizService, times(1)).removeQuestionsFromQuiz(1L, 1L, questionIds);
    }

    @Test
    public void testAddQuestionsToQuiz() throws Exception {
        List<Long> questionIds = Arrays.asList(1L, 2L);

        when(quizService.existsById(1L, 1L)).thenReturn(true);
        when(authorizationManager.isInstructor(1L)).thenReturn(true);
        when(quizService.addQuestionstoQuiz(1L, 1L, questionIds)).thenReturn(true);

        mockMvc.perform(put("/api/course/{courseid}/quiz/{quizId}/addquestions", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2]")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Question added to quiz successfully!"));

        verify(quizService, times(1)).addQuestionstoQuiz(1L, 1L, questionIds);
    }

    @Test
    public void testDeleteQuiz() throws Exception {
        when(quizService.existsById(1L, 1L)).thenReturn(true);
        when(authorizationManager.isInstructor(1L)).thenReturn(true);
        when(quizService.delete(1L, 1L)).thenReturn(true);

        mockMvc.perform(delete("/api/course/{courseid}/quiz/{id}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("All good!"));

        verify(quizService, times(1)).delete(1L, 1L);
    }
}
