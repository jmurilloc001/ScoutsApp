package com.jmurilloc.pfc.scouts.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter( autoApply = true )
public class MaterialsConverter implements AttributeConverter<Map<String, Object>, String>
{
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    
    @Override
    public String convertToDatabaseColumn( Map<String, Object> attribute )
    {
        try
        {
            return objectMapper.writeValueAsString( attribute );
        }
        catch ( JsonProcessingException e )
        {
            throw new IllegalArgumentException( "Error al convertir el mapa a JSON", e );
        }
    }
    
    
    @Override
    public Map<String, Object> convertToEntityAttribute( String dbData )
    {
        try
        {
            return objectMapper.readValue( dbData, HashMap.class );
        }
        catch ( IOException e )
        {
            throw new IllegalArgumentException( "Error al convertir el JSON a un mapa", e );
        }
    }
}
