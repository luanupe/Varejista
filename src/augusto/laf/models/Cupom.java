package augusto.laf.models;

import augusto.laf.contratos.ModelAbstract;
import net.sf.json.JSONObject;

public class Cupom extends ModelAbstract {

	private String codigo;
	private double valor;
	
	public Cupom(Integer id, String codigo, double valor) {
		super(id);
		this.codigo = codigo;
		this.valor = valor;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}

}
