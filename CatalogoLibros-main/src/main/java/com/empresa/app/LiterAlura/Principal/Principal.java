package com.empresa.app.LiterAlura.Principal;

import com.empresa.app.LiterAlura.model.Autor;
import com.empresa.app.LiterAlura.model.Datos;
import com.empresa.app.LiterAlura.model.DatosLibro;
import com.empresa.app.LiterAlura.model.Libro;
import com.empresa.app.LiterAlura.repository.AutorRepository;
import com.empresa.app.LiterAlura.repository.LibroRepository;
import com.empresa.app.LiterAlura.service.ConsumoAPI;
import com.empresa.app.LiterAlura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private final String URL_BASE = "https://gutendex.com/books/";

  //  private final String API_KEY = "&apikey=4fc7c187";

    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepository;

    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository,AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;


    }

    public void muestraElMenu() throws JsonProcessingException {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listas autores registrado
                    4 - Buscar Series por titulo
                    5 - Listar Libros por Idiomas
                    6 - Listar los 5 libros mas descargados en nuestra data
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosDeterminadoAño();
                    break;
                case 5:
                    ListarLibrosPorIdioma();
                    break;
                case 6:
                    ListarLibrosMasDescargados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void ListarLibrosMasDescargados() {

        List<Libro> libros = libroRepository.findAll();
        System.out.println("Los 5 libros con mayores descargas");

        libros.stream()
                .sorted(Comparator.comparingInt(libro -> Integer.parseInt(((Libro) libro).getNumeroDescargas())).reversed())
                .limit(5)
                .forEach(this::mostrarLibros);

    }

    private void ListarLibrosPorIdioma() {

        System.out.println("Ingrese el Idioma del libro");
        System.out.println("en - Español");
        System.out.println("es - Ingles");
        System.out.println("fr - Frances");
        System.out.println("pt - Portugues");

        String idioma = teclado.nextLine();

        List<Libro> librosIdioma = libroRepository.findAll();

       librosIdioma.stream().filter(s->s.getIdiomas().contains(idioma)).forEach(this::mostrarLibros);

    }

    private void listarAutoresVivosDeterminadoAño() {

        System.out.println("Listar Autores Registrados vivos por año ingresado");

        int anio= teclado.nextInt();

        List<Autor> autores = autorRepository.findAll();

        autores.stream()
                .filter(s ->  s.getFechaFallecimiento() != 0 && s.getFechaNacimiento() <= anio)
                .filter(s ->  s.getFechaFallecimiento() != 0 && s.getFechaFallecimiento() >= anio)
                .forEach(s -> {
                    System.out.println(" ");
                    System.out.println("Autor: " + s.getNombre());
                    System.out.println("Fecha de nacimiento: " + s.getFechaNacimiento());
                    System.out.println("Fecha de fallecimiento: " + s.getFechaFallecimiento());
                    System.out.print("Libros: [");
                    s.getLibros().forEach(libro -> {
                        System.out.print(libro.getTitulo() + ", ");
                    });
                    System.out.println("]");
                    System.out.println(" ");
                });
    }



    private void mostrarAutoresRegistrados() {

        System.out.println("Listar Autores Registrados");

        List<Autor> autores = autorRepository.findAll();

        Map<String, List<String>> autoresMap = new HashMap<>();

        autores.stream().forEach(s->{

            List<String>  titulos =  s.getLibros().stream().map(Libro::getTitulo).collect(Collectors.toList());;
           autoresMap.merge(s.getNombre(), new ArrayList<>(titulos), (existingTitles, newTitles) -> {
               existingTitles.addAll(newTitles);
               return existingTitles;
           });
        });

        autoresMap.forEach((autor, titulos) -> {
            System.out.println(" ");
            System.out.println("Autor: " + autor);
            System.out.print("Libros: " + titulos);
            System.out.println(" ");
            System.out.println(" ");
        });


    }



    private DatosLibro getDatosLibros()  {

        // Ingresamos el nombre del libro a buscar

        System.out.println("Por favor Ingrese el Libro a buscar");
        // el nombre e la pelicula tiene ue estar bien escrito, uen o falte ni un caracter
        var nombreLibro = teclado.nextLine();

        String json = consumoAPI.obtenerLibros(URL_BASE + "?search=" + nombreLibro.replace(" ", "+").toLowerCase());

        List<DatosLibro> libros = conversor.obtnerDatos(json, Datos.class).resultados();

        Optional<DatosLibro> libro = libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();

        if (libro.isPresent()){

            return libro.get();

        }
        System.out.println("El libro no ha sido encontrado");
        return null;

    }

    private void buscarLibroWeb(){
        DatosLibro datosLibro = getDatosLibros();
        if (datosLibro != null){
            Optional<Libro>  autor = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
            if (autor.isPresent()){
                System.out.println("No se puede guardar el mismo libro 2 veces");
            }else{
                Libro libro = new Libro(datosLibro);
                libroRepository.save(libro);
                mostrarLibros(libro);
            }
        }
    }


    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();

//        libros.forEach(   s -> {
//            mostrarLibros(s);
//        });
        libros.forEach(this::mostrarLibros);

    }

    private void mostrarLibros(Libro s) {
        System.out.println(" ");
        System.out.println("--- LIBRO ---");
        System.out.println("Titulo: " + s.getTitulo());
        System.out.println("Autor: " + s.getAutor().getNombre());
        System.out.println("Idioma: " + s.getIdiomas().stream().findFirst().get());
        System.out.println("Numero de descargas: " + s.getNumeroDescargas());
        System.out.println("-------------");
        System.out.println(" ");
    }

}
