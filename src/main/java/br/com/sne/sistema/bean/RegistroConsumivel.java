package br.com.sne.sistema.bean;

import java.util.Date;

public class RegistroConsumivel {
	private int id;
	private int quantidade;
	private BemConsumivel bem;
	private OrdemServico os;
	private Funcionario funcionarioEstoque;
	private Funcionario funcionarioEvento;
	private Date data;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BemConsumivel getBem() {
		return bem;
	}
	public void setBem(BemConsumivel bem) {
		this.bem = bem;
	}
	public OrdemServico getOs() {
		return os;
	}
	public void setOs(OrdemServico os) {
		this.os = os;
	}
	public Funcionario getFuncionarioEstoque() {
		return funcionarioEstoque;
	}
	public void setFuncionarioEstoque(Funcionario funcionarioEstoque) {
		this.funcionarioEstoque = funcionarioEstoque;
	}
	public Funcionario getFuncionarioEvento() {
		return funcionarioEvento;
	}
	public void setFuncionarioEvento(Funcionario funcionarioEvento) {
		this.funcionarioEvento = funcionarioEvento;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
