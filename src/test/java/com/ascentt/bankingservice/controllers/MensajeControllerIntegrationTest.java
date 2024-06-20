package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.entities.Mensaje;
import com.ascentt.bankingservice.model.entities.User;
import com.ascentt.bankingservice.repository.MensajeRepository;
import com.ascentt.bankingservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class MensajeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UserRepository userRepository;

    private User remitente;
    private User destinatario;

    @BeforeEach
    void setUp() {
        mensajeRepository.deleteAll();
        userRepository.deleteAll();

        remitente = userRepository.save(new User(null, "remitente@example.com", "password", "remitente"));
        destinatario = userRepository.save(new User(null, "destinatario@example.com", "password", "destinatario"));
    }

    @Test
    void testEnviarMensaje() throws Exception {
        Mensaje mensaje = new Mensaje(null, remitente, destinatario, "Contenido del mensaje", LocalDateTime.now());

        mockMvc.perform(post("/api/mensajes/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"remitente\": {\"id\": " + remitente.getId() + "}, \"destinatario\": {\"id\": " + destinatario.getId() + "}, \"contenido\": \"Contenido del mensaje\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contenido").value("Contenido del mensaje"));
    }

    @Test
    void testObtenerMensajesRecibidos() throws Exception {
        Mensaje mensaje1 = new Mensaje(null, remitente, destinatario, "Contenido 1", LocalDateTime.now());
        Mensaje mensaje2 = new Mensaje(null, remitente, destinatario, "Contenido 2", LocalDateTime.now());
        mensajeRepository.save(mensaje1);
        mensajeRepository.save(mensaje2);

        mockMvc.perform(get("/api/mensajes/recibidos/" + destinatario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].contenido").value("Contenido 1"))
                .andExpect(jsonPath("$[1].contenido").value("Contenido 2"));
    }

    @Test
    void testObtenerMensajesEnviados() throws Exception {
        Mensaje mensaje1 = new Mensaje(null, remitente, destinatario, "Contenido 1", LocalDateTime.now());
        Mensaje mensaje2 = new Mensaje(null, remitente, destinatario, "Contenido 2", LocalDateTime.now());
        mensajeRepository.save(mensaje1);
        mensajeRepository.save(mensaje2);

        mockMvc.perform(get("/api/mensajes/enviados/" + remitente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].contenido").value("Contenido 1"))
                .andExpect(jsonPath("$[1].contenido").value("Contenido 2"));
    }
}
