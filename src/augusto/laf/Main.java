package augusto.laf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	
	public static void main(String[] args) {
		// Iniciar SQLite
		Database.getInstancia();
		
		// Iniciar aplicação
		SpringApplication.run(Main.class, args);
	}
	
}
