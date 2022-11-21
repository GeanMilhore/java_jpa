package br.com.alura.loja.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CategoriaId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String tipo;
	
	public CategoriaId() {}
	
	public CategoriaId(String nome, String tipo) {
		this.setNome(nome);
		this.setTipo(tipo);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	@Override
	public boolean equals(Object obj) {
		CategoriaId cat = (CategoriaId) obj;
		if(!cat.getNome().equals(this.nome)) return false;
		if(!cat.getTipo().equals(this.tipo)) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(this.nome + this.tipo).hashCode();
	}
}
