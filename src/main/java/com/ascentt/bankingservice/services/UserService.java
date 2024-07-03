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
        System.out.println("Validando usuario: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            System.out.println("Usuario no encontrado en validación");
            return Optional.of("Correo no encontrado");
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            System.out.println("Contraseña incorrecta en validación");
            return Optional.of("Contraseña incorrecta");
        }
        System.out.println("Usuario validado correctamente");
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        System.out.println("Buscando usuario por email: " + email);
        return userRepository.findByEmail(email);
    }

    public boolean resetPassword(String email, String newPassword) {
        System.out.println("Restableciendo contraseña para: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            System.out.println("Correo no encontrado en restablecimiento de contraseña");
            return false;
        }
        User user = userOptional.get();
        user.setPassword(newPassword);
        userRepository.save(user);
        System.out.println("Contraseña actualizada correctamente para: " + email);
        return true;
    }
}
