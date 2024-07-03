package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.entities.Mensaje;
import com.ascentt.bankingservice.services.MensajeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    @PostMapping("/enviar")
    public ResponseEntity<Mensaje> enviarMensaje(@RequestBody Mensaje mensaje) {
        Mensaje nuevoMensaje = mensajeService.enviarMensaje(mensaje);
        return new ResponseEntity<>(nuevoMensaje, HttpStatus.CREATED);
    }

    @GetMapping("/recibidos/{destinatarioId}")
    public ResponseEntity<List<Mensaje>> obtenerMensajesRecibidos(@PathVariable Long destinatarioId) {
        List<Mensaje> mensajes = mensajeService.obtenerMensajesRecibidos(destinatarioId);
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    @GetMapping("/enviados/{remitenteId}")
    public ResponseEntity<List<Mensaje>> obtenerMensajesEnviados(@PathVariable Long remitenteId) {
        List<Mensaje> mensajes = mensajeService.obtenerMensajesEnviados(remitenteId);
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }
}
