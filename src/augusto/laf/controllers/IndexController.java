package augusto.laf.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import augusto.laf.models.HelloWorld;

@RestController
public class IndexController {

	@GetMapping("/")
	public HelloWorld greeting(
			@RequestParam(value = "nome", defaultValue = "Hello") String nome,
			@RequestParam(value = "sobrenome", defaultValue = "World") String sobrenome
	) {
		return new HelloWorld(nome, sobrenome);
	}

}
