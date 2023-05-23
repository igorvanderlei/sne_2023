package br.com.sne.sistema.bean;

public class FontePagadora {
	private Long id;
	private String nome;
	private String observacoes;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getNome() {
		return nome;
	}
	
	public String toString() {
		return nome.toString();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}