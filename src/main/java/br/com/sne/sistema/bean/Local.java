package br.com.sne.sistema.bean;

import java.util.ArrayList;
import java.util.List;

public class Local {
	private long id;
	private String nome;
	private Endereco endereco;
	private String observacoes;
	
	
	private List<SalaLocal> salaLocals;
	
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
	
	private List<Ambiente> ambiente;
	 
	public Local(LocalEvento l) {
		this.nome = l.getNome();
		this.observacoes = l.getObservacoes();
		if(l.getEndereco() != null)
			this.endereco = (Endereco) l.getEndereco().clone();
		
	}
	public Local(Local l) {
		this.nome = l.getNome();
		this.observacoes = l.getObservacoes();
		if(l.getEndereco() != null)
			this.endereco = (Endereco) l.getEndereco().clone();
		if(l.getSalaLocals() != null) {
			List<SalaLocal> lista = new ArrayList<SalaLocal>();
			for(SalaLocal s: l.getSalaLocals()) {
				lista.add((SalaLocal)s.clone());
			}
			this.salaLocals = lista;
		}
	}
	
	public String toString() {
		return nome;
	}

	public void adicionarAmbiente(Ambiente a) {
		ambiente.add(a);
	}
	 
	public void removerAmbiente(Ambiente a) {
		ambiente.remove(a);
	}
	 
	public List<Ambiente> listarAmbientes() {
		return ambiente;
	}

	public Local(String nome, Endereco endereco) {
		this.nome = nome;
		this.endereco = endereco;
	}

	public Local() {
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

	public List<Ambiente> getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(List<Ambiente> ambiente) {
		this.ambiente = ambiente;
	}

	public List<SalaLocal> getSalaLocals() {
		return salaLocals;
	}

	public void setSalaLocals(List<SalaLocal> salaLocals) {
		this.salaLocals = salaLocals;
	}

	 
}
 
