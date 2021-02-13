package augusto.laf.contratos;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;

public abstract class ModelAbstract {
	
	/*
	 * Este atributo irá definir se o objeto foi previamente
	 * persistido ou não. id == NULL: não persistido
	 */
	private Integer id;
	
	
	/*
	 * Mesmo não podendo instanciar essa classe por ser abstract 
	 * optei por deixar protected, uma vez que apenas as 
	 * classes que extends terá acesso.
	 */
	protected ModelAbstract(Integer id) {
		this.setId(id);
	}
	
	public final Integer getId() {
		return this.id;
	}
	
	public final void setId(Integer id) {
		if ((this.id == null)) this.id = id;
	}
	
	/*
	 * Facilitar a posterior verificação se o model já está 
	 * persistido na base de dados em memória
	 */
	public final boolean isPersistido() {
		return (this.id != null);
	}
	
	public abstract void atualizar(ResultSet result) throws SQLException;
	
	public abstract JSONObject toJson();

}
