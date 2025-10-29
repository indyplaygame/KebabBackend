package api.indy.kebab.persistence.converter;

import api.indy.kebab.model.Order;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Set;

@Converter
public class OrderConverter implements AttributeConverter<Set<Order>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Order> orders) { return null; }


    @Override
    public Set<Order> convertToEntityAttribute(String str) { return null; }

}
