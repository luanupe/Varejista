package augusto.laf;

import java.util.LinkedList;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

import augusto.laf.contratos.MigrationAbstract;
import augusto.laf.migrations.*;

import augusto.laf.repositorios.CupomRepositorio;
import augusto.laf.repositorios.ProdutoRepositorio;

import augusto.laf.models.Produto;
import augusto.laf.models.Cupom;

public class Database {
	
	private static Database INSTANCIA;
	
	public synchronized static Database getInstancia() {
		if ((Database.INSTANCIA == null)) {
			try {
				Path caminho = FileSystems.getDefault().getPath("db.sqlite");
				Database.INSTANCIA = new Database(caminho.toAbsolutePath().toString());
				Database.INSTANCIA.iniciar();
				Database.INSTANCIA.migrate();
				// Database.INSTANCIA.popular();
			}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return Database.INSTANCIA;
	}
	
	// Singleton
	
	private LinkedList<MigrationAbstract> migrations;
	private Connection conexao;
	
	private Database(String caminho) throws SQLException {
		this.migrations = new LinkedList<MigrationAbstract>();
		this.conexao = DriverManager.getConnection("jdbc:sqlite:" + caminho);
	}
	
	private void iniciar() {
		this.migrations.add(new CriarTabelaProdutoMigration());
		this.migrations.add(new CriarTabelaCupomMigration());
	}
	
	private void migrate() throws SQLException {
		for (MigrationAbstract migration : this.migrations) migration.migrate();
	}
	
	private void popular() {
		this.popularProdutos();
		this.popularCupons();
	}
	
	private void popularProdutos() {
		ProdutoRepositorio repositorio = ProdutoRepositorio.getInstancia();
		repositorio.inserir(new Produto("Peito de Frango (1kg)", 12.99f, 1000));
		repositorio.inserir(new Produto("Bata Doce (1kg)", 2.99f, 1500));
		repositorio.inserir(new Produto("Aveia (100g)", 2.39f, 800));
		repositorio.inserir(new Produto("Pão Integral (490g)", 5.69f, 100));
		repositorio.inserir(new Produto("Café Vácuo (250g)", 6.59f, 2000));
		repositorio.inserir(new Produto("Energético sem Açucar", 9.99f, 700));
	}
	
	private void popularCupons() {
		CupomRepositorio repositorio = CupomRepositorio.getInstancia();
		repositorio.inserir(new Cupom("50R$ OFF", 50f));
		repositorio.inserir(new Cupom("70R$ OFF", 700f));
		repositorio.inserir(new Cupom("100R$ OFF", 100f));
	}
	
	public Connection getConexao() {
		return this.conexao;
	}

}
