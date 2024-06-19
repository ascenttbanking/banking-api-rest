package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProperty() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPrice(800.0);
        propertyDTO.setRooms(2);
        propertyDTO.setAddress("Av VillaReal 1105");
        propertyDTO.setLocation("Trujillo");
        propertyDTO.setSize(120);
        propertyDTO.setFeatures("Alquilo Casa 2 habitaciones");

        Property property = new Property();
        when(modelMapper.map(propertyDTO, Property.class)).thenReturn(property);
        when(propertyRepository.save(property)).thenReturn(property);
        when(modelMapper.map(property, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO result = propertyService.addProperty(propertyDTO);

        assertNotNull(result);
        assertEquals(800.0, result.getPrice());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    public void testUpdateProperty() {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPrice(800.0);
        propertyDTO.setRooms(2);
        propertyDTO.setAddress("Av VillaReal 1105");
        propertyDTO.setLocation("Trujillo");
        propertyDTO.setSize(120);
        propertyDTO.setFeatures("Alquilo Casa 2 habitaciones");

        Property property = new Property();
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(property)).thenReturn(property);
        when(modelMapper.map(property, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO result = propertyService.updateProperty(1L, propertyDTO);

        assertNotNull(result);
        assertEquals(800.0, result.getPrice());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    public void testGetPropertyById() {
        Property property = new Property();
        property.setId(1L);
        property.setPrice(800.0);

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPrice(800.0);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(modelMapper.map(property, PropertyDTO.class)).thenReturn(propertyDTO);

        PropertyDTO result = propertyService.getPropertyById(1L);

        assertNotNull(result);
        assertEquals(800.0, result.getPrice());
        verify(propertyRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteProperty() {
        Property property = new Property();
        property.setId(1L);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        doNothing().when(propertyRepository).delete(property);

        propertyService.deleteProperty(1L);

        verify(propertyRepository, times(1)).delete(property);
    }
}
