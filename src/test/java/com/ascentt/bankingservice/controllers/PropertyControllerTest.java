package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.services.FileStorageService;
import com.ascentt.bankingservice.services.PropertyService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Importar el método get aquí
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private FileStorageService fileStorageService;

    @Test
    public void testAddProperty() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPrice(800.0);
        propertyDTO.setRooms(2);
        propertyDTO.setAddress("Av VillaReal 1105");
        propertyDTO.setLocation("Trujillo");
        propertyDTO.setSize(120);
        propertyDTO.setFeatures("Alquilo Casa 2 habitaciones");

        when(propertyService.addProperty(ArgumentMatchers.any(PropertyDTO.class))).thenReturn(propertyDTO);

        MockMultipartFile[] photos = {
                new MockMultipartFile("photos", "photo1.jpg", MediaType.IMAGE_JPEG_VALUE, "photo1".getBytes()),
                new MockMultipartFile("photos", "photo2.jpg", MediaType.IMAGE_JPEG_VALUE, "photo2".getBytes())
        };

        mockMvc.perform(multipart("/properties/")
                        .file(photos[0])
                        .file(photos[1])
                        .param("price", "800")
                        .param("rooms", "2")
                        .param("address", "Av VillaReal 1105")
                        .param("location", "Trujillo")
                        .param("size", "120")
                        .param("features", "Alquilo Casa 2 habitaciones")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(800.0));
    }

    @Test
    public void testGetProperties() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPrice(800.0);
        propertyDTO.setRooms(2);
        propertyDTO.setAddress("Av VillaReal 1105");
        propertyDTO.setLocation("Trujillo");
        propertyDTO.setSize(120);
        propertyDTO.setFeatures("Alquilo Casa 2 habitaciones");

        when(propertyService.getAllProperties()).thenReturn(Collections.singletonList(propertyDTO));

        mockMvc.perform(get("/properties/") // Usar el método get importado aquí
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(800.0));
    }
}
