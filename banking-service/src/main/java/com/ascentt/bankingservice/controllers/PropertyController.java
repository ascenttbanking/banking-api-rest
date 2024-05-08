package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/properties")
@Validated
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/")
    public ResponseEntity<PropertyDTO> addProperty(@Valid @RequestBody PropertyDTO propertyDto) {
        PropertyDTO savedProperty = propertyService.addProperty(propertyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyDTO> editProperty(@PathVariable Long id, @Valid @RequestBody PropertyDTO propertyDto) {
        PropertyDTO updatedProperty = propertyService.editProperty(id, propertyDto);
        return ResponseEntity.ok(updatedProperty);
    }

    @GetMapping("/")
    public ResponseEntity<List<PropertyDTO>> getProperties() {
        List<PropertyDTO> properties = propertyService.getProperties();
        return ResponseEntity.ok(properties);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok().build();  // Retorna una respuesta vacía con estado 200 OK
    }
}
