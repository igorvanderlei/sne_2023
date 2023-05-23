package br.com.sne.sistema.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class Recurso implements Comparable<Recurso>, Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String descricao;
	private String observacoes;
	private BigDecimal precoSugerido;
	private BigDecimal valorMinimo;
	private Grupo grupo;
	private String codigo;
	private BigDecimal precoCusto;
	private boolean calcularDiarias;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPrecoSugerido() {
		return precoSugerido;
	}
	public void setPrecoSugerido(BigDecimal precoSugerido) {
		this.precoSugerido = precoSugerido;
	}
	public Recurso(String nome, String descricao, BigDecimal precoSugerido) {
		this.nome = nome;
		this.descricao = descricao;
		this.precoSugerido = precoSugerido;
	}
	public Recurso() {
	}

	public String toString() {
		return nome.toString();
	}
	
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}	
	
	public int compareTo(Recurso outro){
    	return this.getNome().compareToIgnoreCase(outro.getNome());
    }
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public boolean isCalcularDiarias() {
		return calcularDiarias;
	}

	public void setCalcularDiarias(boolean calcularDiarias) {
		this.calcularDiarias = calcularDiarias;
	}
	
	
	
	 
}
 
