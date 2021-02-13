package augusto.laf.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import augusto.laf.repositorios.CupomRepositorio;
import augusto.laf.repositorios.ProdutoRepositorio;
import net.sf.json.JSONObject;

@RestController
public class IndexController {

	@GetMapping("/")
	public JSONObject index() {
		CupomRepositorio cupoms = CupomRepositorio.getInstancia();
		ProdutoRepositorio produtos = ProdutoRepositorio.getInstancia();
		
		JSONObject response = new JSONObject();
		response.put("cupoms", cupoms.listar().size());
		response.put("produtos", produtos.listar().size());
		return response;
	}

}
