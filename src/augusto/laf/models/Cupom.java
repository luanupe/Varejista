package augusto.laf.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import augusto.laf.contratos.ModelAbstract;
import net.sf.json.JSONObject;

public class Cupom extends ModelAbstract {

	private String codigo;
	private float valor;
	
	public Cupom() {
		super(null);
	}
	
	public Cupom(String codigo, float valor) {
		this(null, codigo, valor);
	}
	
	public Cupom(Integer id, String codigo, float valor) {
		super(id);
		this.codigo = codigo;
		this.valor = valor;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public float getValor() {
		return this.valor;
	}

	@Override
	public void atualizar(ResultSet result) throws SQLException {
		super.setId(result.getInt("id"));
		this.codigo = result.getString("codigo");
		this.valor = result.getFloat("valor");
	}

	@Override
	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}

}
