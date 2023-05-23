package br.com.sne.sistema.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recolhimento implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	
	private List<OrdemServico> ordemServico;
	private Funcionario responsavel;
	private String veiculo;
	private Date data;
	private List<EquipamentoEnviado> equipamentosBipados;
	private boolean deletado;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<OrdemServico> getOrdemServico() {
		return ordemServico;
	}
	public void setOrdemServico(List<OrdemServico> ordemServico) {
		this.ordemServico = ordemServico;
	}
	public Funcionario getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}
	public String getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public List<EquipamentoEnviado> getEquipamentosBipados() {
		if(equipamentosBipados == null)
			equipamentosBipados = new ArrayList<EquipamentoEnviado>();
		return equipamentosBipados;
	}
	public void setEquipamentosBipados(List<EquipamentoEnviado> equipamentosBipados) {
		this.equipamentosBipados = equipamentosBipados;
	}
	public boolean isDeletado() {
		return deletado;
	}
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	
	public String toString() {
		return responsavel.getNome();
	}
}
