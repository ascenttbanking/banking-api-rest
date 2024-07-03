package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/properties")
@Validated
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<PropertyDTO> addProperty(
            @RequestParam("price") double price,
            @RequestParam("rooms") int rooms,
            @RequestParam("address") String address,
            @RequestParam("location") String location,
            @RequestParam("size") int size,
            @RequestParam("features") String features,
            @RequestParam("photos") List<MultipartFile> photos) {
        PropertyDTO propertyDto = new PropertyDTO();
        propertyDto.setPrice(price);
        propertyDto.setRooms(rooms);
        propertyDto.setAddress(address);
        propertyDto.setLocation(location);
        propertyDto.setSize(size);
        propertyDto.setFeatures(features);
        propertyDto.setPhotos(photos);

        PropertyDTO savedProperty = propertyService.addProperty(propertyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<PropertyDTO> editProperty(
            @PathVariable Long id,
            @RequestParam("price") double price,
            @RequestParam("rooms") int rooms,
            @RequestParam("address") String address,
            @RequestParam("location") String location,
            @RequestParam("size") int size,
            @RequestParam("features") String features,
            @RequestParam("photos") List<MultipartFile> photos) {
        PropertyDTO propertyDto = new PropertyDTO();
        propertyDto.setPrice(price);
        propertyDto.setRooms(rooms);
        propertyDto.setAddress(address);
        propertyDto.setLocation(location);
        propertyDto.setSize(size);
        propertyDto.setFeatures(features);
        propertyDto.setPhotos(photos);

        PropertyDTO updatedProperty = propertyService.updateProperty(id, propertyDto);
        return ResponseEntity.ok(updatedProperty);
    }

    @GetMapping("/")
    public ResponseEntity<List<PropertyDTO>> getProperties() {
        List<PropertyDTO> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok().build();
    }
}
