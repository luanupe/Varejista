package augusto.laf.comprar;

import augusto.laf.exceptions.CarrinhoException;
import augusto.laf.models.Produto;
import augusto.laf.repositorios.ProdutoRepositorio;

public class CarrinhoProduto {
	
	private Produto produto;
	private int quantidade;
	
	protected CarrinhoProduto(Produto produto) {
		this.produto = produto;
		this.quantidade = 0;
	}
	
	/*
	 * Quantidade em estoque também deve ser verificado ao checkout
	 * uma vez que outro usuário do sistema pode comprar o produto.
	 */
	protected void adicionarQuantidade(int quantidade) throws CarrinhoException {
		// Verificar quantidade em estoque
		quantidade = (this.quantidade + quantidade);
		if ((quantidade > this.getProduto().getEstoque())) throw new CarrinhoException("Não temos essa quantidade em estoque.");
	
		// Atualizar quantidade
		this.setQuantidade(quantidade);
	}
	
	protected void removerQuantidade(int quantidade) throws CarrinhoException {
		if ((quantidade > this.quantidade)) throw new CarrinhoException("Não é possível toda essa quantidade de produtos.");
		quantidade = (this.quantidade = quantidade);
		this.setQuantidade(quantidade);
	}
	
	private void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
		if ((this.quantidade < 0)) this.quantidade = 0;
	}
	
	/*
	 * ID não vai puder por ser PK
	 * evitar ficar consultando ao banco todas as vezes
	 */
	public int getId() {
		return this.produto.getId();
	}
	
	/*
	 * Coloquei para buscar o model do produto atualizado todas
	 * as vezes, caso o produto tenha sido removido ele vai utilizar
	 * o model antigo, tratar produtos inexistentes no carrinho no momento
	 * do checkout.
	 */
	public Produto getProduto() {
		Produto produto = ProdutoRepositorio.getInstancia().buscar(this.produto.getId());
		if ((produto == null)) produto = this.produto;
		return produto;
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}
	
	public float getTotal() {
		return (this.quantidade * this.getProduto().getPreco());
	}
	
	/*
	 * Aplicar 10% de desconto item por item é a mesma coisa
	 * que aplicar 10% no total.
	 */
	public float getTotalDesconto() {
		float total = this.getTotal();
		if ((this.quantidade > 0)) total = (total * 0.90f);
		return total;
	}

}
