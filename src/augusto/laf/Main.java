package augusto.laf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
	
	public static void main(String[] args) {
		// Iniciar SQLite
		Database.getInstancia();
		
		// Iniciar aplicação
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
	public ServletContextInitializer servletContextInitializer() {
	    return new ServletContextInitializer() {

	        @Override
	        public void onStartup(ServletContext servletContext) throws ServletException {
	            servletContext.getSessionCookieConfig().setName("JSESSIONID");
	        }
	    };

	}
	
}
