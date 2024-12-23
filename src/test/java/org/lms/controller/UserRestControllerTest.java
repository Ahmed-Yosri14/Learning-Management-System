package org.lms.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.lms.entity.User.AppUser;
import org.lms.service.AppUserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppUserService appUserService;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private UserRestController userRestController;

    private AppUser user;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }


    @Test
    void testDeleteProfile() throws Exception {
        when(appUserService.delete(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("All good!"));
    }
}
