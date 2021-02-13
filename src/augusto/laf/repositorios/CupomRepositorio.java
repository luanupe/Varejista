package augusto.laf.repositorios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import augusto.laf.Database;
import augusto.laf.models.Cupom;
import augusto.laf.contratos.RepositorioAbstract;

public class CupomRepositorio extends RepositorioAbstract<Cupom> {
	
	private Cupom buscar(ResultSet result) throws SQLException {
		// Buscar na cache ou instanciar
		Cupom cupom = super.cacheBuscar(result.getInt("id"));
		if ((cupom == null)) cupom = new Cupom();
		
		// Atualizar dados
		cupom.atualizar(result);
		super.cacheAdicionar(cupom.getId(), cupom);
		return cupom;
	}
	
	public Cupom buscarPeloCodigo(String codigo) {
		try {
			Database db = Database.getInstancia();
			PreparedStatement stmt = db.getConexao().prepareStatement("SELECT * FROM cupom WHERE ( codigo LIKE ? )");
			stmt.setString(1, "%" + codigo + "%");
			
			ResultSet result = stmt.executeQuery();
			if ((result.next())) return this.buscar(result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Cupom buscar(int id) {
		try {
			Database db = Database.getInstancia();
			PreparedStatement stmt = db.getConexao().prepareStatement("SELECT * FROM cupom WHERE ( id = ? )");
			stmt.setInt(1, id);
			
			ResultSet result = stmt.executeQuery();
			if ((result.next())) return this.buscar(result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean inserir(Cupom model) {
		if (!(model.isPersistido()))
		{
			try {
				Database db = Database.getInstancia();
				PreparedStatement stmt = db.getConexao().prepareStatement("INSERT INTO cupom ( codigo, valor ) VALUES ( ?, ? );");
				stmt.setString(1, model.getCodigo());
				stmt.setFloat(2, model.getValor());
				
				// Inserir Registro
				stmt.executeUpdate();
				
				// Buscar inserted keys
				ResultSet result = stmt.getGeneratedKeys();
				if ((result.next()))
				{
					model.setId(result.getInt(1));
					super.cacheAdicionar(model.getId(), model);
					return true;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean atualizar(Cupom model) {
		if ((model.isPersistido()))
		{
			try {
				Database db = Database.getInstancia();
				PreparedStatement stmt = db.getConexao().prepareStatement("UPDATE cupom SET codigo = ?, valor = ? WHERE ( id = ? )");
				stmt.setString(1, model.getCodigo());
				stmt.setFloat(2, model.getValor());
				stmt.setInt(3, model.getId());
				
				// Atualizar Registro
				boolean status = stmt.execute();
				if ((status)) super.cacheAdicionar(model.getId(), model);
				return status;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean deletar(Cupom model) {
		if ((model.isPersistido()))
		{
			try {
				Database db = Database.getInstancia();
				PreparedStatement stmt = db.getConexao().prepareStatement("DELETE FROM cupom WHERE ( id = ? )");
				stmt.setInt(1, model.getId());
				
				// Remover Registro
				boolean status = stmt.execute();
				if ((status)) super.cacheRemover(model.getId());
				return status;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
