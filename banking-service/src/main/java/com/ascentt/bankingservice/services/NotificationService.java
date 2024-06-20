package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.entities.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Eliminar o comentar las referencias a JavaMailSender
    // @Autowired
    // private JavaMailSender emailSender;

    public void sendPaymentConfirmation(User user, String transactionId) {
        // Eliminar o comentar el cuerpo del método si es necesario
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(user.getEmail());
        // message.setSubject("Payment Confirmation");
        // message.setText("Dear " + user.getName() + ",\n\nYour payment with transaction ID " + transactionId + " has been successfully processed.");
        // emailSender.send(message);

        // Puedes dejar una implementación de prueba o un log
        System.out.println("Payment confirmation sent to user: " + user.getEmail());
    }
}
