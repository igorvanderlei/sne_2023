package br.com.sne.sistema.bean;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;

public class Grupo implements Comparable<Grupo>{
	private long id;
	
	private String nome;
	private String observacoes;
	private String codigo;
	
	private TipoRecurso tipoRecurso;
	
	public TipoRecurso getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(TipoRecurso tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
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
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Grupo() {
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String toString() {
		return nome;
	}
	
	public int compareTo(Grupo grp) {
		return this.getNome().compareToIgnoreCase(grp.getNome());
	}
	public boolean equals(Object obj) {
		if(obj instanceof Grupo)
			return this.getId() == ((Grupo) obj).getId();
		return false;
	}

}
