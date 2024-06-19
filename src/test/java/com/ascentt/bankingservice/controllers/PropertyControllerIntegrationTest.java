package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.services.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyService propertyService;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        // Eliminar las dependencias primero
        entityManager.createQuery("DELETE FROM PropertyReview").executeUpdate();
        entityManager.createQuery("DELETE FROM Property").executeUpdate();

        PropertyDTO propertyDTO1 = new PropertyDTO();
        propertyDTO1.setPrice(800.0);
        propertyDTO1.setRooms(2);
        propertyDTO1.setAddress("Av VillaReal 1105");
        propertyDTO1.setLocation("Trujillo");
        propertyDTO1.setSize(120);
        propertyDTO1.setFeatures("Alquilo Casa 2 habitaciones");

        PropertyDTO propertyDTO2 = new PropertyDTO();
        propertyDTO2.setPrice(1200.0);
        propertyDTO2.setRooms(3);
        propertyDTO2.setAddress("Av Arequipa 320");
        propertyDTO2.setLocation("Lima");
        propertyDTO2.setSize(150);
        propertyDTO2.setFeatures("Alquilo Departamento 3 habitaciones");

        propertyService.addProperty(propertyDTO1);
        propertyService.addProperty(propertyDTO2);
    }

    @Test
    public void testGetProperties() throws Exception {
        mockMvc.perform(get("/properties/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"price\":800.0,\"rooms\":2,\"address\":\"Av VillaReal 1105\",\"location\":\"Trujillo\",\"size\":120,\"features\":\"Alquilo Casa 2 habitaciones\"},{\"price\":1200.0,\"rooms\":3,\"address\":\"Av Arequipa 320\",\"location\":\"Lima\",\"size\":150,\"features\":\"Alquilo Departamento 3 habitaciones\"}]"));
    }
}
