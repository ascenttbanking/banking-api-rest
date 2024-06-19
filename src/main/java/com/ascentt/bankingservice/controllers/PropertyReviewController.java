package com.ascentt.bankingservice.controllers;

import com.ascentt.bankingservice.model.dto.PropertyReviewDTO;
import com.ascentt.bankingservice.services.PropertyReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/property-reviews")
@Validated
public class PropertyReviewController {

    @Autowired
    private PropertyReviewService reviewService;

    @PostMapping("/")
    public ResponseEntity<PropertyReviewDTO> addReview(@Valid @RequestBody PropertyReviewDTO reviewDTO) {
        try {
            PropertyReviewDTO savedReview = reviewService.addReview(reviewDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
        } catch (RuntimeException e) {
            // Retornar un mensaje de error claro cuando la propiedad no se encuentra
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyReviewDTO>> getReviewsByPropertyId(@PathVariable Long propertyId) {
        try {
            List<PropertyReviewDTO> reviews = reviewService.getReviewsByPropertyId(propertyId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
