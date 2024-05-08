package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.entities.Property;
import com.ascentt.bankingservice.exceptions.NotFoundException;
import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public PropertyDTO addProperty(PropertyDTO propertyDto) {
        if (!isAvailable(propertyDto.getId(), someStartDate, someEndDate)) {
            throw new BadRequestException("Property is not available for the selected dates.");
        }
        double dynamicPrice = calculateDynamicPrice(propertyDto.getPrice(), someDate);
        propertyDto.setPrice(dynamicPrice);

        Property property = convertToEntity(propertyDto);
        Property savedProperty = propertyRepository.save(property);
        return convertToDto(savedProperty);
    }

    @Transactional
    public PropertyDTO editProperty(Long id, PropertyDTO propertyDto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found with ID: " + id));
        modelMapper.map(propertyDto, property);
        Property updatedProperty = propertyRepository.save(property);
        return modelMapper.map(updatedProperty, PropertyDTO.class);
    }

    @Transactional(readOnly = true)
    public List<PropertyDTO> getProperties() {
        return propertyRepository.findAll().stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new NotFoundException("Property not found with ID: " + id);
        }
        propertyRepository.deleteById(id);
    }

}