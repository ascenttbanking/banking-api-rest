package com.ascentt.bankservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ascentt.bankservice.model.entities.User;

@Service
public class NotificationService {

    public void notifyPaymentSuccess(User user, String message) {
        // Enviar notificación de éxito de pago
        // Aquí se podría integrar con un sistema de mensajería por email o SMS
    }

    public void notifyPaymentFailure(User user, String message) {
        // Enviar notificación de fallo de pago
    }
}
