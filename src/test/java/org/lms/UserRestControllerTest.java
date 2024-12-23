package org.lms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lms.controller.UserRestController;
import org.lms.entity.User.AppUser;
import org.lms.entity.UserRole;
import org.lms.service.AppUserService;
import org.lms.AuthorizationManager;
import org.lms.EntityMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private AppUserService appUserService;

    @Mock
    private AuthorizationManager authorizationManager;

    @Mock
    private EntityMapper entityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProfile() {
        AppUser mockUser = new AppUser();
        mockUser.setId(1L);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("john.doe@example.com");

        when(authorizationManager.getCurrentUser()).thenReturn(mockUser);
        when(entityMapper.map(mockUser)).thenReturn(mockUser.toMap(UserRole.ADMIN));

        ResponseEntity<Object> response = userRestController.getProfile();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entityMapper.map(mockUser), response.getBody());
    }

    @Test
    void testUpdateProfileSuccess() {
        AppUser updatedUser = new AppUser();
        updatedUser.setFirstName("Jane");

        when(appUserService.update(anyLong(), any(AppUser.class))).thenReturn(true);
        when(authorizationManager.getCurrentUserId()).thenReturn(1L);

        ResponseEntity<Object> response = userRestController.updateProfile(updatedUser);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All good!", response.getBody());
        verify(appUserService).update(1L, updatedUser);
    }

    @Test
    void testUpdateProfileFailure() {
        AppUser updatedUser = new AppUser();
        updatedUser.setFirstName("Jane");

        when(appUserService.update(anyLong(), any(AppUser.class))).thenReturn(false);
        when(authorizationManager.getCurrentUserId()).thenReturn(1L);

        ResponseEntity<Object> response = userRestController.updateProfile(updatedUser);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Something went wrong", response.getBody());
    }


}
