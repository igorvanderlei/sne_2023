package br.com.sne.sistema.bean;

import java.util.Date;
import java.util.List;


public class OsDePassagem {
	private long id;               
	private Date data;   
	private List<EquipamentoEnviado> equipamentoEnviado;
	private OrdemServico origem;
	private OrdemServico destino;
	private Funcionario funcionario;
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public List<EquipamentoEnviado> getEquipamentoEnviado() {
		return equipamentoEnviado;
	}
	public void setEquipamentoEnviado(List<EquipamentoEnviado> equipamentoEnviado) {
		this.equipamentoEnviado = equipamentoEnviado;
	}
	public OrdemServico getOrigem() {
		return origem;
	}
	public void setOrigem(OrdemServico origem) {
		this.origem = origem;
	}
	public OrdemServico getDestino() {
		return destino;
	}
	public void setDestino(OrdemServico destino) {
		this.destino = destino;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public OsDePassagem() {
	}
	
	public String toString() {
		return "" + id;
	}
}
