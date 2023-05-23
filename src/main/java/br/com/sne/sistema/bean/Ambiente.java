package br.com.sne.sistema.bean;

public class Ambiente {
	private long id;
	private String nome;
	private int capacidade;
	private float tamanhoPalco;
	private float peDireito;
	private String estrutura;
	private String recursos;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	public Ambiente(String nome, int capacidade) {
		this.nome = nome;
		this.capacidade = capacidade;
	}
	
	public Ambiente(){}
	
	public void setRecursos(String recursos) {
		this.recursos = recursos;
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
	
	public String toString(){
		return (this != null )?this.getNome().toString():"" ;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRecursos() {
		return recursos;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public float getTamanhoPalco() {
		return tamanhoPalco;
	}

	public void setTamanhoPalco(float tamanhoPalco) {
		this.tamanhoPalco = tamanhoPalco;
	}

	public float getPeDireito() {
		return peDireito;
	}

	public void setPeDireito(float peDireito) {
		this.peDireito = peDireito;
	}

	public String getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}
	 
}
 
