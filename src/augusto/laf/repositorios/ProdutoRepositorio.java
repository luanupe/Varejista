package augusto.laf.repositorios;

import java.util.List;
import java.util.LinkedList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import augusto.laf.models.Produto;
import augusto.laf.Database;
import augusto.laf.contratos.RepositorioAbstract;

public class ProdutoRepositorio extends RepositorioAbstract<Produto> {
	
	private static ProdutoRepositorio INSTANCIA;
	
	public synchronized static ProdutoRepositorio getInstancia() {
		if ((ProdutoRepositorio.INSTANCIA == null)) {
			ProdutoRepositorio.INSTANCIA = new ProdutoRepositorio();
		}
		return ProdutoRepositorio.INSTANCIA;
	}
	
	private ProdutoRepositorio() {
		// Singleton
	}
	
	private Produto buscar(ResultSet result) throws SQLException {
		// Buscar na cache ou instanciar
		Produto produto = super.cacheBuscar(result.getInt("id"));
		if ((produto == null)) produto = new Produto();
		
		// Atualizar dados
		produto.atualizar(result);
		super.cacheAdicionar(produto.getId(), produto);
		return produto;
	}
	
	@Override
	public List<Produto> listar() {
		List<Produto> produtos = new LinkedList<Produto>();
		try {
			Database db = Database.getInstancia();
			PreparedStatement stmt = db.getConexao().prepareStatement("SELECT * FROM produto ORDER BY id ASC");
			
			ResultSet result = stmt.executeQuery();
			while (result.next()) produtos.add(this.buscar(result)); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}

	@Override
	public Produto buscar(int id) {
		try {
			Database db = Database.getInstancia();
			PreparedStatement stmt = db.getConexao().prepareStatement("SELECT * FROM produto WHERE ( id = ? )");
			stmt.setInt(1, id);
			
			ResultSet result = stmt.executeQuery();
			if ((result.next())) return this.buscar(result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Produto buscarPeloNome(String nome) {
		try {
			// Buscar produto com o nome exato
			Database db = Database.getInstancia();
			PreparedStatement stmt = db.getConexao().prepareStatement("SELECT * FROM produto WHERE ( nome = ? )");
			stmt.setString(1, nome);
			
			// Se encontrar ao menos um, retorna model
			ResultSet result = stmt.executeQuery();
			if ((result.next())) return this.buscar(result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Produto> listarPeloNome(String nome) {
		List<Produto> produtos = new LinkedList<Produto>();
		try {
			// Buscar todos os produtos com o nome LIKE
			Database db = Database.getInstancia();
			PreparedStatement stmt = db.getConexao().prepareStatement("SELECT * FROM produto WHERE ( nome LIKE ? )");
			stmt.setString(1, "%" + nome + "%");
			
			// Adiciona todos os models para a array
			ResultSet result = stmt.executeQuery();
			while ((result.next())) produtos.add(this.buscar(result)); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}

	@Override
	public boolean inserir(Produto model) {
		if (!(model.isPersistido()))
		{
			try {
				// Montar statement
				Database db = Database.getInstancia();
				PreparedStatement stmt = db.getConexao().prepareStatement("INSERT INTO produto ( nome, preco, estoque ) VALUES ( ?, ?, ? );");
				stmt.setString(1, model.getNome());
				stmt.setFloat(2, model.getPreco());
				stmt.setInt(3, model.getEstoque());
				
				// Inserir Registro
				boolean status = (stmt.executeUpdate() > 0);
				if ((status))
				{
					// Buscar inserted keys
					ResultSet result = stmt.getGeneratedKeys();
					if ((result.next()))
					{
						model.setId(result.getInt(1));
						super.cacheAdicionar(model.getId(), model);
					}
				}
				return status;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean atualizar(Produto model) {
		if ((model.isPersistido()))
		{
			try {
				// Montar statement
				Database db = Database.getInstancia();
				PreparedStatement stmt = db.getConexao().prepareStatement("UPDATE produto SET nome = ?, preco = ?, estoque = ? WHERE ( id = ? )");
				stmt.setString(1, model.getNome());
				stmt.setFloat(2, model.getPreco());
				stmt.setFloat(3, model.getEstoque());
				stmt.setInt(4, model.getId());
				
				// Atualizar Registro
				boolean status = (stmt.executeUpdate() > 0);
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
	public boolean deletar(Produto model) {
		if ((model.isPersistido()))
		{
			try {
				// Preparar statement
				Database db = Database.getInstancia();
				PreparedStatement stmt = db.getConexao().prepareStatement("DELETE FROM produto WHERE ( id = ? )");
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
