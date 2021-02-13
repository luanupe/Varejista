package augusto.laf.contratos;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public abstract class RepositorioAbstract<M> {
	
	private final Map<Integer, M> cache = new HashMap<Integer, M>();
	
	protected final M cacheBuscar(int id) {
		synchronized (this.cache) {
			return this.cache.get(id);
		}
	}
	
	protected final void cacheAdicionar(int id, M model) {
		synchronized (this.cache) {
			this.cache.put(id, model);
		}
	}
	
	protected final void cacheRemover(int id) {
		synchronized (this.cache) {
			this.cache.remove(id);
		}
	}
	
	public abstract List<M> listar();
	
	public abstract M buscar(int id);
	
	public abstract boolean atualizar(M model);
	
	public abstract boolean inserir(M model);
	
	public abstract boolean deletar(M model);

	public final M deletar(int id) {
		M model = this.buscar(id);
		if ((model != null)) this.deletar(model);
		return model;
	}

}
