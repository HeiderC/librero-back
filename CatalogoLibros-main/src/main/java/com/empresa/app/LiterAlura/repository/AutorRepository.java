package com.empresa.app.LiterAlura.repository;

import com.empresa.app.LiterAlura.model.Autor;
import com.empresa.app.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AutorRepository  extends JpaRepository<Autor,Long> {


}
