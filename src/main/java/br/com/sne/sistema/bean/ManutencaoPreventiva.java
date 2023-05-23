package br.com.sne.sistema.bean;

import java.util.Date;

public class ManutencaoPreventiva {
	private long id;
	private Date dataManutencao;
	private Date dataDevolucao;
	private String descricaoProblema;
	private String parecerTecnico;
	private Funcionario funcionario;
	private Funcionario tecnicoResponsavel;
	private Equipamento equipamento;
	private boolean status;
	
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
	public Date getDataManutencao() {
		return dataManutencao;
	}
	public void setDataManutencao(Date dataManutencao) {
		this.dataManutencao = dataManutencao;
	}
	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	public String getDescricaoProblema() {
		return descricaoProblema;
	}
	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}
	public String getParecerTecnico() {
		return parecerTecnico;
	}
	public void setParecerTecnico(String parecerTecnico) {
		this.parecerTecnico = parecerTecnico;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	public Funcionario getTecnicoResponsavel() {
		return tecnicoResponsavel;
	}
	public void setTecnicoResponsavel(Funcionario tecnicoResponsavel) {
		this.tecnicoResponsavel = tecnicoResponsavel;
	}
	
	public String toString() {
		return equipamento.getNumeroSerie();
	}

}
