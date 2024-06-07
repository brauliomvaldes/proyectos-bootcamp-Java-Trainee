package bootcamp.consola;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bootcamp.consola.controller.ConsolaController;

@SpringBootApplication
public class ProyectoConsolaApplication implements CommandLineRunner{

	// se requiere inyectar la clase que hace de controlador para que traiga todos los componentes
	@Autowired
	ConsolaController consolaController;
	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoConsolaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// llama al método que procesa los algoritmos
		consolaController.menu();
	}

}
