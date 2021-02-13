package augusto.laf.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import augusto.laf.models.Produto;
import augusto.laf.repositorios.ProdutoRepositorio;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class ProdutoController {
	
	@GetMapping("/produto")
	public JSONArray index() {
		JSONArray produtos = new JSONArray();
		try {
			ProdutoRepositorio repositorio = ProdutoRepositorio.getInstancia();
			for (Produto produto : repositorio.listar()) produtos.add(produto.toJson());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}

	@GetMapping("/produto/inserir")
	public JSONObject inserir(
			@RequestParam(value = "nome") String nome,
			@RequestParam(value = "preco") float preco,
			@RequestParam(value = "estoque") int estoque
	) {
		try {
			ProdutoRepositorio repositorio = ProdutoRepositorio.getInstancia();
			
			// Verificar se preço é válido
			if ((preco <= 0f)) throw new Exception("O preço do produto é inválido.");
			
			// Inserir novo produto
			Produto produto = new Produto(nome, preco, estoque);
			boolean status = repositorio.inserir(produto);
			if ((!status) || (!produto.isPersistido())) throw new Exception("Produto não pôde ser inserido.");
			
			// Transformar em JSON
			return produto.toJson();
		}
		catch (Exception e) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}

	@GetMapping("/produto/atualizar/{id}")
	public JSONObject atualizar(
			@RequestParam(value = "nome") String nome,
			@RequestParam(value = "preco") float preco,
			@RequestParam(value = "estoque") int estoque,
			@PathVariable int id
	) {
		try {
			ProdutoRepositorio repositorio = ProdutoRepositorio.getInstancia();
			
			// Verificar se preço é válido
			if ((preco <= 0f)) throw new Exception("O preço do produto é inválido.");
			
			// Verificar se produto existe
			Produto produto = repositorio.buscar(id);
			if ((produto == null)) throw new Exception("Produto não encontrado.");
			
			// Atualizar produto
			produto = new Produto(produto.getId(), nome, preco, estoque);
			boolean status = repositorio.atualizar(produto);
			if ((!status)) throw new Exception("Produto não pôde ser atualizado.");
			
			// Transformar em JSON
			return produto.toJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}

}
