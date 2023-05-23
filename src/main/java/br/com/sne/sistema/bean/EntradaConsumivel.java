package br.com.sne.sistema.bean;

import java.util.Date;

public class EntradaConsumivel {
	private long id;
	private BemConsumivel bem;
	private int quantidade;
	private Date data;
	private Funcionario funcionario;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BemConsumivel getBem() {
		return bem;
	}
	public void setBem(BemConsumivel bem) {
		this.bem = bem;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
}
