package br.com.sne.sistema.bean;

import java.io.Serializable;

public class LocalEvento implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String observacoes;
	
	private Local local;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String getObservacoes() {
		return observacoes;
	}


	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	private Endereco endereco;
	
	
	public LocalEvento(Local l) {
		this.nome = l.getNome();
		this.endereco = (Endereco) l.getEndereco().clone();
		this.setLocal(l);
	}
	
	
	public String toString() {
		return nome;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public LocalEvento() {
		super();
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
	
	
}
