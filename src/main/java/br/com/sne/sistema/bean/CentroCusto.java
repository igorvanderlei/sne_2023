package br.com.sne.sistema.bean;

public class CentroCusto implements Comparable<CentroCusto>{
	private long id;
	private String nome;
	private String observacoes;
	
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
	public CentroCusto() {
	}

	public String toString() {
		return nome;
	}
	
	public int compareTo(CentroCusto grp) {
		return this.getNome().compareToIgnoreCase(grp.getNome());
	}
	public boolean equals(Object obj) {
		if(obj instanceof CentroCusto)
			return this.getId() == ((CentroCusto) obj).getId();
		return false;
	}
	
}
