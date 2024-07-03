package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PropertyDTO addProperty(PropertyDTO propertyDTO) {
        Property property = modelMapper.map(propertyDTO, Property.class);
        Property savedProperty = propertyRepository.save(property);
        return modelMapper.map(savedProperty, PropertyDTO.class);
    }

    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            property.setPrice(propertyDTO.getPrice());
            property.setRooms(propertyDTO.getRooms());
            property.setAddress(propertyDTO.getAddress());
            property.setLocation(propertyDTO.getLocation());
            property.setSize(propertyDTO.getSize());
            property.setFeatures(propertyDTO.getFeatures());
            Property updatedProperty = propertyRepository.save(property);
            return modelMapper.map(updatedProperty, PropertyDTO.class);
        }
        return null;
    }

    public PropertyDTO getPropertyById(Long id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            return modelMapper.map(propertyOptional.get(), PropertyDTO.class);
        }
        return null;
    }

    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        return properties.stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteProperty(Long id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        propertyOptional.ifPresent(propertyRepository::delete);
    }
}
