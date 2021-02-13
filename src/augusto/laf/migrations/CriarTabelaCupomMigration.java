package augusto.laf.migrations;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import augusto.laf.Database;
import augusto.laf.contratos.MigrationAbstract;

public class CriarTabelaCupomMigration extends MigrationAbstract {

	@Override
	public void migrate() throws SQLException {
		// Montar SQL
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS cupom (");
		sql.append("	id INTEGER PRIMARY KEY,");
		sql.append("	codigo TEXT NOT NULL,");
		sql.append("	valor DOUBLE NOT NULL");
		sql.append(");");
		
		// Executar SQL
		Database database = Database.getInstancia();
		PreparedStatement stmt = database.getConexao().prepareStatement(sql.toString());
		stmt.execute();
	}

}
