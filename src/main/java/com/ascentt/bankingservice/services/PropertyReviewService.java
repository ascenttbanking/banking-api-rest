package com.ascentt.bankingservice.services;

import com.ascentt.bankingservice.model.dto.PropertyReviewDTO;
import com.ascentt.bankingservice.model.entities.Property;
import com.ascentt.bankingservice.model.entities.PropertyReview;
import com.ascentt.bankingservice.repository.PropertyRepository;
import com.ascentt.bankingservice.repository.PropertyReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyReviewService {

    @Autowired
    private PropertyReviewRepository propertyReviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PropertyReviewDTO addReview(PropertyReviewDTO reviewDTO) {
        Property property = propertyRepository.findById(reviewDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property with ID " + reviewDTO.getPropertyId() + " not found"));

        PropertyReview review = modelMapper.map(reviewDTO, PropertyReview.class);
        review.setProperty(property);
        review.setCreatedAt(LocalDateTime.now());

        review = propertyReviewRepository.save(review);
        PropertyReviewDTO responseDTO = modelMapper.map(review, PropertyReviewDTO.class);
        responseDTO.setPropertyId(review.getProperty().getId());  // Ensure propertyId is set correctly
        return responseDTO;
    }

    public List<PropertyReviewDTO> getReviewsByPropertyId(Long propertyId) {
        return propertyReviewRepository.findByPropertyId(propertyId).stream()
                .map(review -> {
                    PropertyReviewDTO reviewDTO = modelMapper.map(review, PropertyReviewDTO.class);
                    reviewDTO.setPropertyId(propertyId);  // Ensure propertyId is set correctly
                    return reviewDTO;
                })
                .collect(Collectors.toList());
    }
}
