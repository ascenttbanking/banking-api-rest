package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.entities.Mensaje;
import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.services.MensajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MensajeControllerTest {

    @Mock
    private MensajeService mensajeService;

    @InjectMocks
    private MensajeController mensajeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnviarMensaje() {
        Mensaje mensaje = new Mensaje(1L, new User(), new User(), "Contenido", LocalDateTime.now());
        when(mensajeService.enviarMensaje(mensaje)).thenReturn(mensaje);

        ResponseEntity<Mensaje> response = mensajeController.enviarMensaje(mensaje);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mensaje, response.getBody());
    }

    @Test
    void testObtenerMensajesRecibidos() {
        Long destinatarioId = 1L;
        Mensaje mensaje1 = new Mensaje(1L, new User(), new User(), "Contenido 1", LocalDateTime.now());
        Mensaje mensaje2 = new Mensaje(2L, new User(), new User(), "Contenido 2", LocalDateTime.now());
        List<Mensaje> mensajes = Arrays.asList(mensaje1, mensaje2);

        when(mensajeService.obtenerMensajesRecibidos(destinatarioId)).thenReturn(mensajes);

        ResponseEntity<List<Mensaje>> response = mensajeController.obtenerMensajesRecibidos(destinatarioId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensajes, response.getBody());
    }

    @Test
    void testObtenerMensajesEnviados() {
        Long remitenteId = 1L;
        Mensaje mensaje1 = new Mensaje(1L, new User(), new User(), "Contenido 1", LocalDateTime.now());
        Mensaje mensaje2 = new Mensaje(2L, new User(), new User(), "Contenido 2", LocalDateTime.now());
        List<Mensaje> mensajes = Arrays.asList(mensaje1, mensaje2);

        when(mensajeService.obtenerMensajesEnviados(remitenteId)).thenReturn(mensajes);

        ResponseEntity<List<Mensaje>> response = mensajeController.obtenerMensajesEnviados(remitenteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensajes, response.getBody());
    }
}
