package com.ascentt.bankings.controller;

import com.ascentt.bankings.model.dto.UserRequestDTO;
import com.ascentt.bankings.model.dto.UserResponseDTO;
import com.ascentt.bankings.model.entity.User;
import com.ascentt.bankings.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.findByEmail(userRequestDTO.getEmail()).orElse(null);

        if (user == null || !user.getPassword().equals(userRequestDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getEmail(), user.getPassword(), user.getUsername());
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/signout")
    public ResponseEntity<Object> signOut() {
        return ResponseEntity.status(HttpStatus.OK).body("Sesión cerrada exitosamente");
    }
}


