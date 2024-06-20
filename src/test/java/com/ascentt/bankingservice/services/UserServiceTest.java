package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidarUser_CorrectCredentials() {
        String email = "test@example.com";
        String password = "password";
        User user = new User(1L, email, password, "username");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<String> result = userService.validarUser(email, password);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testValidarUser_IncorrectPassword() {
        String email = "test@example.com";
        String password = "password";
        User user = new User(1L, email, "wrongpassword", "username");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<String> result = userService.validarUser(email, password);

        assertTrue(result.isPresent());
        assertEquals("Contraseña incorrecta", result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testValidarUser_EmailNotFound() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<String> result = userService.validarUser(email, password);

        assertTrue(result.isPresent());
        assertEquals("Correo no encontrado", result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testResetPassword_Success() {
        String email = "test@example.com";
        String newPassword = "newpassword";
        User user = new User(1L, email, "oldpassword", "username");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        boolean result = userService.resetPassword(email, newPassword);

        assertTrue(result);
        assertEquals(newPassword, user.getPassword());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testResetPassword_EmailNotFound() {
        String email = "test@example.com";
        String newPassword = "newpassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = userService.resetPassword(email, newPassword);

        assertFalse(result);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(0)).save(any(User.class));
    }
}
