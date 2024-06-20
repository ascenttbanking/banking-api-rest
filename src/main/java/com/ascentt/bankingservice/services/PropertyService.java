package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileStorageService fileStorageService;

    public PropertyDTO addProperty(PropertyDTO propertyDto) {
        Property property = modelMapper.map(propertyDto, Property.class);

        if (propertyDto.getPhotos() != null) {
            property.setPhotos(propertyDto.getPhotos().stream()
                    .map(fileStorageService::storeFile)
                    .collect(Collectors.joining(", ")));
        }

        property = propertyRepository.save(property);
        return modelMapper.map(property, PropertyDTO.class);
    }

    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        modelMapper.map(propertyDto, property);

        if (propertyDto.getPhotos() != null) {
            property.setPhotos(propertyDto.getPhotos().stream()
                    .map(fileStorageService::storeFile)
                    .collect(Collectors.joining(", ")));
        }

        property = propertyRepository.save(property);
        return modelMapper.map(property, PropertyDTO.class);
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
