package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PasswordResetRequestDTO;
import com.ascentt.bankingservice.model.dto.UserRequestDTO;
import com.ascentt.bankingservice.model.dto.UserResponseDTO;
import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("Iniciando sesión para: " + userRequestDTO.getEmail());
        try {
            System.out.println("Datos recibidos: " + userRequestDTO.getEmail() + " - " + userRequestDTO.getPassword());
            Optional<User> userOptional = userService.findByEmail(userRequestDTO.getEmail());
            System.out.println("Usuario encontrado: " + userOptional.isPresent());

            if (!userOptional.isPresent()) {
                System.out.println("Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            Optional<String> validationResult = userService.validarUser(userRequestDTO.getEmail(), userRequestDTO.getPassword());
            System.out.println("Resultado de validación: " + validationResult);

            if (validationResult.isPresent()) {
                System.out.println("Validación fallida: " + validationResult.get());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validationResult.get());
            }

            User user = userOptional.get();
            UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getEmail(), user.getUsername());
            System.out.println("Autenticación exitosa, usuario: " + userResponseDTO);
            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante el proceso de autenticación");
        }
    }


    @PostMapping("/signout")
    public ResponseEntity<Object> signOut() {
        return ResponseEntity.status(HttpStatus.OK).body("Sesión cerrada exitosamente");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO) {
        try {
            boolean result = userService.resetPassword(passwordResetRequestDTO.getEmail(), passwordResetRequestDTO.getNewPassword());
            if (result) {
                return ResponseEntity.ok("Contraseña actualizada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante el proceso de recuperación de contraseña");
        }
    }
}
