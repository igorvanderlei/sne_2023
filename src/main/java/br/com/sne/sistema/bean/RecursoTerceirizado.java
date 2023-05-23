package br.com.sne.sistema.bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class RecursoTerceirizado implements Comparable<RecursoTerceirizado>,Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String nome;
	private String descricao;
	private String observacoes;
	private BigDecimal precoEmpresa;
	private BigDecimal precoFornecedor;
	private Grupo grupo;
	private FornecedorTerceirizado fornecedorTerceirizado;
	private String codigo;
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
	public RecursoTerceirizado(String nome, String descricao, BigDecimal precoEmpresa) {
		this.nome = nome;
		this.descricao = descricao;
		this.setPrecoEmpresa(precoEmpresa);
	}
	public RecursoTerceirizado() {
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
	
	public int compareTo(RecursoTerceirizado outro){
    	return this.getNome().compareToIgnoreCase(outro.getNome());
    }
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getPrecoEmpresa() {
		return precoEmpresa;
	}

	public void setPrecoEmpresa(BigDecimal precoEmpresa) {
		this.precoEmpresa = precoEmpresa;
	}

	public BigDecimal getPrecoFornecedor() {
		return precoFornecedor;
	}

	public void setPrecoFornecedor(BigDecimal precoFornecedor) {
		this.precoFornecedor = precoFornecedor;
	}

	public FornecedorTerceirizado getFornecedorTerceirizado() {
		return fornecedorTerceirizado;
	}

	public void setFornecedorTerceirizado(FornecedorTerceirizado fornecedorTerceirizado) {
		this.fornecedorTerceirizado = fornecedorTerceirizado;
	}

	public boolean isCalcularDiarias() {
		return calcularDiarias;
	}

	public void setCalcularDiarias(boolean calcularDiarias) {
		this.calcularDiarias = calcularDiarias;
	}
	
	
	
	 
}
 
