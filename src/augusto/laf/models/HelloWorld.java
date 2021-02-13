package augusto.laf.models;

import org.joda.time.DateTime;

public class HelloWorld {
	
	private String nome;
	private String sobrenome;
	private DateTime tempo;
	
	public HelloWorld(String nome, String sobrenome) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.tempo = DateTime.now();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getSobrenome() {
		return this.sobrenome;
	}
	
	public DateTime getTempo() {
		return this.tempo;
	}

}
