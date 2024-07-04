package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.UserRequestDTO;
import com.ascentt.bankingservice.model.dto.UserResponseDTO;
import com.ascentt.bankingservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        if (userResponseDTO != null) {
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<UserResponseDTO> editUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.editUser(userRequestDTO);
        if (userResponseDTO != null) {
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
