package augusto.laf.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import augusto.laf.contratos.ModelAbstract;
import net.sf.json.JSONObject;

public class Produto extends ModelAbstract {
	
	private String nome;
	private float preco;
	private int estoque;
	
	public Produto() {
		super(null);
	}
	
	public Produto(String nome, float preco, int estoque) {
		this(null, nome, preco, estoque);
	}
	
	public Produto(Integer id, String nome, float preco, int estoque) {
		super(id);
		this.nome = nome;
		this.preco = preco;
		this.estoque = estoque;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public float getPreco() {
		return this.preco;
	}
	
	public int getEstoque() {
		return this.estoque;
	}

	@Override
	public void atualizar(ResultSet result) throws SQLException {
		super.setId(result.getInt("id"));
		this.nome = result.getString("nome");
		this.preco = result.getFloat("preco");
		this.estoque = result.getInt("estoque");
	}

	@Override
	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}

}
