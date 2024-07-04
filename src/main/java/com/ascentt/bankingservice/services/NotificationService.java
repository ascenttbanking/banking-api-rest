package com.ascentt.bankingservice.services;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String recipient, String message) {
        // Implementación futura para el envío de notificaciones reales.
        System.out.println("Enviando notificación a " + recipient + ": " + message);
    }
}
