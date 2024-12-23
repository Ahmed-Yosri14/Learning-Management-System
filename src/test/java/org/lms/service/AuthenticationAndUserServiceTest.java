package org.lms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lms.dao.request.SignUpRequest;
import org.lms.dao.request.SigninRequest;
import org.lms.dao.response.JwtAuthenticationResponse;
import org.lms.entity.User.Admin;
import org.lms.entity.User.AppUser;
import org.lms.entity.User.Instructor;
import org.lms.entity.User.Student;
import org.lms.entity.UserRole;
import org.lms.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationAndUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @InjectMocks
    private AppUserService appUserService;

    private SignUpRequest signUpRequest;
    private SigninRequest signinRequest;
    private AppUser user;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");
        signUpRequest.setEmail("john.doe@example.com");
        signUpRequest.setPassword("password123");

        signinRequest = new SigninRequest();
        signinRequest.setEmail("john.doe@example.com");
        signinRequest.setPassword("password123");

        user = new Student();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");
        user.setRoles(UserRole.STUDENT);
    }

    @Test
    void signup_ShouldCreateStudent_WhenRoleIsStudent() {
        signUpRequest.setRole(UserRole.STUDENT);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        assertNotNull(response);
        assertNotNull(response.getToken());
        verify(userRepository).save(any(Student.class));
    }

    @Test
    void signup_ShouldCreateInstructor_WhenRoleIsInstructor() {
        signUpRequest.setRole(UserRole.INSTRUCTOR);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new Instructor());
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        assertNotNull(response);
        verify(userRepository).save(any(Instructor.class));
    }

    @Test
    void signup_ShouldCreateAdmin_WhenRoleIsAdmin() {
        signUpRequest.setRole(UserRole.ADMIN);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new Admin());
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        assertNotNull(response);
        verify(userRepository).save(any(Admin.class));
    }

    @Test
    void signin_ShouldReturnToken_WhenCredentialsAreValid() {
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(user, null)
        );
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);

        assertNotNull(response);
        assertNotNull(response.getToken());
    }

    @Test
    void update_ShouldUpdateUserDetails_WhenSuccessful() {
        AppUser updatedUser = new Student();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Smith");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        boolean result = appUserService.update(1L, updatedUser);

        assertTrue(result);
        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void delete_ShouldDeleteUser_WhenExists() {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = appUserService.delete(1L);

        assertTrue(result);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void getById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        AppUser result = appUserService.getById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void getAll_ShouldReturnAllUsers_WhenNoRoleSpecified() {
        List<AppUser> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<AppUser> result = appUserService.getAll(null);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAll_ShouldReturnUsersByRole_WhenRoleSpecified() {
        List<AppUser> users = Arrays.asList(user);
        when(userRepository.findAllByRole(UserRole.STUDENT.toString())).thenReturn(users);

        List<AppUser> result = appUserService.getAll(UserRole.STUDENT);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void signin_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                authenticationService.signin(signinRequest)
        );
    }

    @Test
    void update_ShouldReturnFalse_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = appUserService.update(1L, user);

        assertFalse(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenExceptionOccurs() {
        doThrow(new RuntimeException()).when(userRepository).deleteById(1L);

        boolean result = appUserService.delete(1L);

        assertFalse(result);
    }
}