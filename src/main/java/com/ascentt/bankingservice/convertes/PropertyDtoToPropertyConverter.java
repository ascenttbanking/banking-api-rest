package com.ascentt.bankingservice.convertes;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Component
public class PropertyDtoToPropertyConverter implements Converter<PropertyDTO, Property> {

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Property convert(PropertyDTO source) {
        Property property = new Property();
        property.setId(source.getId());
        property.setLocation(source.getLocation());
        property.setSize(source.getSize());
        property.setFeatures(source.getFeatures());
        property.setPrice(source.getPrice());
        property.setRooms(source.getRooms());
        property.setAddress(source.getAddress());

        if (source.getPhotos() != null) {
            // Convert MultipartFile to String (e.g., URLs or paths after saving files)
            property.setPhotos(source.getPhotos().stream()
                    .map(photo -> fileStorageService.storeFile(photo)) // Use the FileStorageService to save the file and return the URL or path
                    .collect(Collectors.joining(", "))); // Assuming photos are stored as a comma-separated string
        }

        return property;
    }
}

