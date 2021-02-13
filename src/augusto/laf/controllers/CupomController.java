package augusto.laf.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import augusto.laf.repositorios.CupomRepositorio;
import augusto.laf.models.Cupom;

@RestController
public class CupomController {
	
	@GetMapping("/cupom")
	public JSONArray index() {
		JSONArray cupons = new JSONArray();
		try {
			CupomRepositorio repositorio = CupomRepositorio.getInstancia();
			for (Cupom cupom : repositorio.listar()) cupons.add(cupom.toJson());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cupons;
	}

	@GetMapping("/cupom/inserir")
	public JSONObject inserir(
			@RequestParam(value = "codigo") String codigo,
			@RequestParam(value = "valor") float valor
	) {
		try {
			CupomRepositorio repositorio = CupomRepositorio.getInstancia();
			
			// Verificar se já existe cupom
			Cupom cupom = repositorio.buscarPeloCodigo(codigo);
			if ((cupom != null)) throw new Exception("Já existe um cupom com o código especificado.");
			
			// Inserir novo cupom
			cupom = new Cupom(codigo, valor);
			boolean status = repositorio.inserir(cupom);
			if ((!status) || (!cupom.isPersistido())) throw new Exception("Cupom não pôde ser inserido.");
			
			// Transformar em JSON
			return cupom.toJson();
		}
		catch (Exception e) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}

	@GetMapping("/cupom/atualizar/{id}")
	public JSONObject atualizar(
			@RequestParam(value = "codigo") String codigo,
			@RequestParam(value = "valor") float valor,
			@PathVariable int id
	) {
		try {
			CupomRepositorio repositorio = CupomRepositorio.getInstancia();
			
			// Verificar se cupom existe
			Cupom cupom = repositorio.buscar(id);
			if ((cupom == null)) throw new Exception("Cupom não encontrado.");
			
			// Verificar se não existe outro cupom com o mesmo código
			Cupom check = repositorio.buscarPeloCodigo(codigo);
			if ((check != null) && (check.getId() != cupom.getId())) throw new Exception("Já exite um cupom com o código " + codigo);
			
			// Atualizar cupom
			cupom = new Cupom(cupom.getId(), codigo, valor);
			boolean status = repositorio.atualizar(cupom);
			if ((!status)) throw new Exception("Cupom não pôde ser atualizado.");
			
			// Transformar em JSON
			return cupom.toJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}

}
