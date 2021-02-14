package augusto.laf.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import augusto.laf.Varejista;
import augusto.laf.comprar.Carrinho;
import augusto.laf.comprar.CarrinhoProduto;
import augusto.laf.models.Cupom;
import augusto.laf.models.Produto;
import augusto.laf.repositorios.CupomRepositorio;
import augusto.laf.repositorios.ProdutoRepositorio;
import net.sf.json.JSONObject;

@RestController
public class CarrinhoController {
	
	/*
	 * Buscar sessão do usuário ou algo que identifique ele.
	 * Coloquei para retornar algo padrão, uma vez que não há gerenciamento de sessões/usuário
	 */
	private String getUsuario() {
		return "JSESSIONID";
	}
	
	@GetMapping("/carrinho")
	public JSONObject index() {
		Varejista varejista = Varejista.getInstancia();
		Carrinho carrinho = varejista.getCarrinho(this.getUsuario());
		return carrinho.toJson();
	}
	
	@GetMapping("/carrinho/cupom/aplicar")
	public JSONObject cupomAplicar(
			@RequestParam(value = "codigo") String codigo
	) {
		try {
			Varejista varejista = Varejista.getInstancia();
			CupomRepositorio repositorio = CupomRepositorio.getInstancia();
			Carrinho carrinho = varejista.getCarrinho(this.getUsuario());
			
			// Buscar cupom
			Cupom cupom = repositorio.buscarPeloCodigo(codigo);
			if ((cupom == null)) throw new Exception("Cupom não encontrado.");
			
			// Aplicar cupom
			carrinho.aplicarCupom(cupom);
			return cupom.toJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}
	
	@GetMapping("/carrinho/cupom/remover")
	public JSONObject cupomRemover() {
		try {
			Varejista varejista = Varejista.getInstancia();
			Carrinho carrinho = varejista.getCarrinho(this.getUsuario());
			
			// Remover cupom
			Cupom cupom = carrinho.getCupom();
			carrinho.aplicarCupom(null);
			
			// Havia cupom aplicado
			if ((cupom != null)) return cupom.toJson();
			
			// Não havia cupom
			JSONObject response = new JSONObject();
			response.put("codigo", "undefined");
			return response;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}
	
	@GetMapping("/carrinho/produto/adicionar/{id}")
	public JSONObject produtoAdicionar(
			@RequestParam(value = "quantidade", defaultValue = "1") int quantidade,
			@PathVariable int id
	) {
		try {
			Varejista varejista = Varejista.getInstancia();
			ProdutoRepositorio repositorio = ProdutoRepositorio.getInstancia();
			Carrinho carrinho = varejista.getCarrinho(this.getUsuario());
			
			// Buscar produto
			Produto produto = repositorio.buscar(id);
			if ((produto == null)) throw new Exception("Produto não encontrado.");
			
			// Adicionar produto ao carrinho
			CarrinhoProduto item = carrinho.adicionarProduto(produto, quantidade);
			return item.toJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}
	
	@GetMapping("/carrinho/produto/remover/{id}")
	public JSONObject produtoRemover(
			@RequestParam(value = "quantidade", defaultValue = "1") int quantidade,
			@PathVariable int id
	) {
		try {
			Varejista varejista = Varejista.getInstancia();
			ProdutoRepositorio repositorio = ProdutoRepositorio.getInstancia();
			Carrinho carrinho = varejista.getCarrinho(this.getUsuario());
			
			// Buscar produto
			Produto produto = repositorio.buscar(id);
			if ((produto == null)) throw new Exception("Produto não encontrado.");
			
			// Remover produto do carrinho
			CarrinhoProduto item = carrinho.removerProduto(produto, quantidade);
			return item.toJson();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, e.getMessage()
			);
		}
	}
	
}
