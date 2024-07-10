package com.empresa.app.LiterAlura.repository;

import com.empresa.app.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {


    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(concat('%', :titulo, '%'))")
    Optional<Libro>   findByTituloIgnoreCase( String titulo);



}
