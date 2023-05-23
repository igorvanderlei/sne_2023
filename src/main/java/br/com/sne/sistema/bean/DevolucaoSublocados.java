package br.com.sne.sistema.bean;

import java.util.Date;
import java.util.List;

public class DevolucaoSublocados {
	private long id;
	private Funcionario funcionario;
	private Fornecedor fornecedor;
	private List<EquipamentoSublocado> sublocados;
	private Date data; 
	private boolean deletado;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public List<EquipamentoSublocado> getSublocados() {
		return sublocados;
	}
	public void setSublocados(List<EquipamentoSublocado> sublocados) {
		this.sublocados = sublocados;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public boolean isDeletado() {
		return deletado;
	}
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String toString() {
		return "" + id;
	}
}
