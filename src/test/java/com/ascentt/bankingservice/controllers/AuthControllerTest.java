package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PasswordResetRequestDTO;
import com.ascentt.bankingservice.model.dto.UserRequestDTO;
import com.ascentt.bankingservice.model.dto.UserResponseDTO;
import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignIn_Success() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("test@example.com", "password", "username");
        User user = new User(1L, "test@example.com", "password", "username");

        when(userService.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.of(user));
        when(userService.validarUser(userRequestDTO.getEmail(), userRequestDTO.getPassword())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = authController.signIn(userRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new UserResponseDTO(1L, "test@example.com", "username"), response.getBody());
    }

    @Test
    void testSignIn_InvalidCredentials() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("test@example.com", "password", "username");

        when(userService.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = authController.signIn(userRequestDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales incorrectas", response.getBody());
    }

    @Test
    void testSignOut() {
        ResponseEntity<Object> response = authController.signOut();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sesión cerrada exitosamente", response.getBody());
    }

    @Test
    void testResetPassword_Success() {
        PasswordResetRequestDTO passwordResetRequestDTO = new PasswordResetRequestDTO("test@example.com", "newpassword");

        when(userService.resetPassword(passwordResetRequestDTO.getEmail(), passwordResetRequestDTO.getNewPassword())).thenReturn(true);

        ResponseEntity<Object> response = authController.resetPassword(passwordResetRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Contraseña actualizada exitosamente", response.getBody());
    }

    @Test
    void testResetPassword_EmailNotFound() {
        PasswordResetRequestDTO passwordResetRequestDTO = new PasswordResetRequestDTO("test@example.com", "newpassword");

        when(userService.resetPassword(passwordResetRequestDTO.getEmail(), passwordResetRequestDTO.getNewPassword())).thenReturn(false);

        ResponseEntity<Object> response = authController.resetPassword(passwordResetRequestDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Correo no encontrado", response.getBody());
    }
}
