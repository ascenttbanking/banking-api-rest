package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<String> validarUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return Optional.of("Correo no encontrado");
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            return Optional.of("Contraseña incorrecta");
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
