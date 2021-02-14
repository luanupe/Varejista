package augusto.laf.comprar;

import java.util.Map;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import augusto.laf.exceptions.CupomException;
import augusto.laf.exceptions.CarrinhoException;

import augusto.laf.models.Produto;
import augusto.laf.models.Cupom;

public class Carrinho {
	
	private Map<Integer, CarrinhoProduto> produtos;
	private Cupom cupom;
	
	public Carrinho() {
		this.produtos = new HashMap<Integer, CarrinhoProduto>();
	}
	
	public CarrinhoProduto adicionarProduto(Produto produto, int quantidade) throws CarrinhoException {
		// Não permitir valores negativos
		if ((quantidade <= 0)) throw new CarrinhoException("Quantidade inválida");
		
		// Buscar produto no carrinho
		CarrinhoProduto carrinho = this.getProduto(produto.getId());
		if ((carrinho == null)) carrinho = new CarrinhoProduto(produto);
		carrinho.adicionarQuantidade(quantidade);
		
		// Adicionar ao cache
		this.putProduto(carrinho);
		return carrinho;
	}
	
	public CarrinhoProduto removerProduto(Produto produto, int quantidade) throws CarrinhoException {
		// Verificar se o produto está no carrinho
		CarrinhoProduto carrinho = this.getProduto(produto.getId());
		if ((carrinho == null)) throw new CarrinhoException("Produto não encontrado no carrinho.");
		
		// Remover do carrinho
		carrinho.removerQuantidade(quantidade);
		if ((carrinho.getQuantidade() <= 0)) this.removeProduto(carrinho);
		return carrinho;
	}
	
	/*
	 * Este método será utilizado também para remover um cupom aplicado
	 * logo, pode vir um cupom null como parâmetro
	 */
	public void aplicarCupom(Cupom cupom) throws CupomException {
		// Verificar se já existe outro cupom aplicado
		if ((cupom != null) && (this.cupom != null))
		{
			if ((this.cupom.getId() == cupom.getId())) throw new CupomException("Este cupom já está aplicado.");
			if ((this.cupom.getValor() > cupom.getValor())) throw new CupomException("Este cupom possui um valor inferior.");
		}
		
		// Atualizar Cupom
		this.cupom = cupom;
	}
	
	public Cupom getCupom() {
		return this.cupom;
	}
	
	private CarrinhoProduto getProduto(int id) {
		synchronized (this.produtos) {
			return this.produtos.get(id);
		}
	}
	
	private void putProduto(CarrinhoProduto produto) {
		synchronized (this.produtos) {
			if ((produto.getQuantidade() > 0)) this.produtos.put(produto.getId(), produto);
		}
	}
	
	private void removeProduto(CarrinhoProduto produto) {
		synchronized (this.produtos) {
			this.produtos.remove(produto.getId());
		}
	}
	
	public float getTotal() {
		float total = 0;
		synchronized (this.produtos) {
			for (CarrinhoProduto produto : this.produtos.values()) total += produto.getTotal();
		}
		return total;
	}
	
	public float getTotalDesconto() {
		float total = 0;
		
		// Buscar preço já com desconto de 10 items
		synchronized (this.produtos) {
			for (CarrinhoProduto produto : this.produtos.values()) total += produto.getTotalDesconto();
		}
		
		// Aplicar desconto do cupom
		if ((this.cupom != null)) total -= this.cupom.getValor();
		
		// Valor calculado
		return ((total > 0) ? total : 0);
	}
	
	/*
	 * Apliquei o desconto progressivo verificando em cima do valor total do carrinho
	 * fiquei com dúvidas se aplicaria em cima dos valores já com descontos,
	 * no entanto APENAS os cupons não são cumulativos, uma vez que nada
	 * ficou explicitado a respeito dos benefícios, coloquei verificando de acordo
	 * com o valor total real.
	 */
	public float getTotalDescontoProgressivo() {
		float totalReal = this.getTotal();
		float totalDesconto = this.getTotalDesconto();

		// Aplicar descontos a partir de 10K
		if ((totalReal >= 10000f)) totalDesconto *= 0.9;
		else if ((totalReal >= 5000f)) totalDesconto *= 0.93;
		else if ((totalReal >= 1000f)) totalDesconto *= 0.95;
		
		// Valor calculado
		return totalDesconto;
	}
	
	public JSONObject toJson() {
		JSONArray produtos = new JSONArray();
		synchronized (this.produtos) {
			for (CarrinhoProduto produto : this.produtos.values()) produtos.add(produto.toJson());
		}
		
		JSONObject detalhes = new JSONObject();
		detalhes.put("produtos", produtos);
		detalhes.put("subtotal", this.getTotal());
		detalhes.put("total", this.getTotalDescontoProgressivo());
		if ((this.cupom != null)) detalhes.put("cupom", this.cupom.toJson());
		return detalhes; 
	}

}
