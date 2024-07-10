package com.empresa.app.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {

    private ObjectMapper objectmaper = new ObjectMapper();


    @Override
    public <T> T obtnerDatos(String Json, Class<T> clase) {
        try {
            return objectmaper.readValue(Json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
