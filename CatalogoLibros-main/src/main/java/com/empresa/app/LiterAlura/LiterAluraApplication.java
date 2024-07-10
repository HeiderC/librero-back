package com.empresa.app.LiterAlura;

import com.empresa.app.LiterAlura.repository.AutorRepository;
import com.empresa.app.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.empresa.app.LiterAlura.Principal.Principal;



@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void run(String... args) throws Exception {
		//	System.out.println("Hola Mundo desde Spring");

		Principal principal = new Principal(libroRepository,autorRepository);

		principal.muestraElMenu();


		}
	}
