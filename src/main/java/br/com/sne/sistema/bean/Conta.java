package br.com.sne.sistema.bean;

import java.util.Date;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoConta;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Conta {
	
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoConta tipoConta;
	
	private String descricao;
	private float valor;
	private Date data;
	private boolean situacao; // true = ocorreu, false = n�o ocorreu
	private Categoria categoria;
	

	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
		
	public Conta(Long id, String descricao, float valor, Date data,
			boolean situacao, Categoria categoria, TipoConta tipoConta) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		this.situacao = situacao;
		this.categoria = categoria;
		this.tipoConta = tipoConta;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public boolean isSituacao() {
		return situacao;
	}
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public TipoConta getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}
	
	
}
