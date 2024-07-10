package com.empresa.app.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private int fechaNacimiento;

    private  int fechaFallecimiento;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
    private List<Libro> libros ;

    public Autor(DatosAutor autor) {
        this.nombre = autor.nombre();
        this.fechaNacimiento = Integer.parseInt(autor.fechaNacimiento());
        this.fechaFallecimiento = Integer.parseInt(autor.fechaFallecimiento());
    }



    public Autor(String nombre) {
        this.nombre = nombre;

    }

    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {

        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(libro -> {
            libro.setAutor(this); // Establecer la relación de autor en cada libro
        });
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", fechaFallecimiento='" + fechaFallecimiento + '\'' +
               ", libros=" + libros +
                '}';
    }
}
