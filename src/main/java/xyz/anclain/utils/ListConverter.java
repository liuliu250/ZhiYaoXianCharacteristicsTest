package xyz.anclain.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Converter
public class ListConverter implements AttributeConverter<List<Double>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Double> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            return "[]";
        }
    }

    @Override
    public List<Double> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {return new ArrayList<>();}
            return mapper.readValue(dbData,
                    mapper.getTypeFactory().constructCollectionType(List.class, Double.class));
        }  catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
