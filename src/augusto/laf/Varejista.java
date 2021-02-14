package augusto.laf;

import java.util.Map;
import java.util.HashMap;

import augusto.laf.comprar.Carrinho;

public class Varejista {
	
	private static Varejista INSTANCIA;
	
	public synchronized static Varejista getInstancia() {
		if ((Varejista.INSTANCIA == null)) {
			Varejista.INSTANCIA = new Varejista();
		}
		return Varejista.INSTANCIA;
	}
	
	// singleton
	
	public Map<String, Carrinho> carrinhos;
	
	private Varejista() {
		this.carrinhos = new HashMap<String, Carrinho>();
	}
	
	public Carrinho getCarrinho(String cookie) {
		synchronized (this.carrinhos) {
			Carrinho carrinho = this.carrinhos.get(cookie);
			if ((carrinho == null)) {
				carrinho = new Carrinho();
				this.carrinhos.put(cookie, carrinho);
			}
			return carrinho;
		}
	}
	

}
