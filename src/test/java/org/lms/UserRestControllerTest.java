package org.lms;

import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.controller.UserRestController;
import org.lms.entity.UserRole;
import org.lms.service.AppUserService;
import org.lms.entity.User.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void testGetProfile() throws Exception {
        when(authorizationManager.getCurrentUser()).thenReturn(user);
        when(entityMapper.map(user)).thenReturn(user.toMap(UserRole.ADMIN));

        mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }



    @Test
    void testDeleteProfile() throws Exception {
        when(appUserService.delete(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("All good!"));
    }
}
