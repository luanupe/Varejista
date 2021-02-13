package augusto.laf.migrations;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import augusto.laf.Database;
import augusto.laf.contratos.MigrationAbstract;

public class CriarTabelaProdutoMigration extends MigrationAbstract {

	@Override
	public void migrate() throws SQLException {
		// Montar SQL
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS produto (");
		sql.append("	id INTEGER PRIMARY KEY,");
		sql.append("	nome TEXT NOT NULL,");
		sql.append("	preco DOUBLE NOT NULL,");
		sql.append("	estoque INTEGER NOT NULL");
		sql.append(");");
		
		// Executar SQL
		Database database = Database.getInstancia();
		PreparedStatement stmt = database.getConexao().prepareStatement(sql.toString());
		stmt.execute();
	}

}
