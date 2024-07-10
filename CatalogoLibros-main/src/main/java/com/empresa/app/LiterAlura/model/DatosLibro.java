package com.empresa.app.LiterAlura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// por defecto todos lo datos ee intentar leer

// y si solo intentas leer tres sale error
// debes de poner ignoreUnknown = true para que solo se lean los que 3 disponibles
//para este caso
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(

        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") String numeroDescargas

) {
}