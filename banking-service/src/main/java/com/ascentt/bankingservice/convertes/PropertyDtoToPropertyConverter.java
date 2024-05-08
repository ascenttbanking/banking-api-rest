package com.ascentt.bankingservice.converters;

import com.ascentt.bankingservice.model.dto.PropertyDTO;
import com.ascentt.bankingservice.model.entities.Property;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PropertyDtoToPropertyConverter implements Converter<PropertyDTO, Property> {

    @Override
    public Property convert(PropertyDTO source) {
        Property property = new Property();
        property.setId(source.getId());
        property.setAddress(formatAddress(source.getAddress()));
        property.setPrice(source.getPrice());
        property.setRooms(source.getRooms());
        return property;
    }

    private String formatAddress(String address) {
        if (address == null) return null;
        // Convierte todas las letras a mayúsculas y reemplaza abreviaturas comunes
        return address.toUpperCase()
                .replace("C.", "CALLE")
                .replace("AV.", "AVENIDA");
    }
}
