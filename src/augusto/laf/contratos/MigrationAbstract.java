package augusto.laf.contratos;

import java.sql.SQLException;

public abstract class MigrationAbstract {
	
	public abstract void migrate() throws SQLException;
	
	public final String getNome() {
		return this.getClass().getCanonicalName();
	}
	
}
