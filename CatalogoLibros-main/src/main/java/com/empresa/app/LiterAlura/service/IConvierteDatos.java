package com.empresa.app.LiterAlura.service;

public interface IConvierteDatos {

    <T> T obtnerDatos(String Json, Class<T> clase);

}
