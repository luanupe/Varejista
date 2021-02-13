package augusto.laf.models;

import augusto.laf.contratos.ModelAbstract;
import net.sf.json.JSONObject;

public class Produto extends ModelAbstract {
	
	private String nome;
	private double preco;
	private int estoque;
	
	public Produto(Integer id, String nome, double preco, int estoque) {
		super(id);
		this.nome = nome;
		this.preco = preco;
		this.estoque = estoque;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	public int getEstoque() {
		return this.estoque;
	}
	
	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}

}
