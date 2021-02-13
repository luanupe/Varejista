package augusto.laf.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONObject;

@RestController
public class IndexController {

	@GetMapping("/")
	public JSONObject greeting(
			@RequestParam(value = "nome", defaultValue = "Hello") String nome,
			@RequestParam(value = "sobrenome", defaultValue = "World") String sobrenome
	) {
		JSONObject response = new JSONObject();
		response.put("nome", nome);
		response.put("sobrenome", sobrenome);
		return response;
	}

}
