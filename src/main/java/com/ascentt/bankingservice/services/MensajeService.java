package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.entities.Mensaje;
import com.ascentt.bankingservice.repository.MensajeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public Mensaje enviarMensaje(Mensaje mensaje) {
        mensaje.setFechaEnvio(LocalDateTime.now());
        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajesRecibidos(Long destinatarioId) {
        return mensajeRepository.findByDestinatarioId(destinatarioId);
    }

    public List<Mensaje> obtenerMensajesEnviados(Long remitenteId) {
        return mensajeRepository.findByRemitenteId(remitenteId);
    }
}

