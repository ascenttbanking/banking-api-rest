package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.UserRequestDTO;
import com.ascentt.bankingservice.model.dto.UserResponseDTO;
import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            Optional<User> userOptional = userService.findByEmail(userRequestDTO.getEmail());

            if (!userOptional.isPresent() || userService.validarUser(userRequestDTO.getEmail(), userRequestDTO.getPassword()).isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            User user = userOptional.get();
            UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getEmail(), user.getUsername());
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
}
