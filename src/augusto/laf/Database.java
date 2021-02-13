package augusto.laf;

import java.util.LinkedList;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.FileSystems;
import augusto.laf.migrations.*;
import augusto.laf.contratos.MigrationAbstract;

public class Database {
	
	private static Database INSTANCIA;
	
	public synchronized static Database getInstancia() {
		if ((Database.INSTANCIA == null)) {
			try {
				Path caminho = FileSystems.getDefault().getPath("db.sqlite");
				Database.INSTANCIA = new Database(caminho.toAbsolutePath().toString());
				Database.INSTANCIA.iniciar();
				Database.INSTANCIA.migrate();
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
	
	public Connection getConexao() {
		return this.conexao;
	}

}
