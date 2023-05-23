package br.com.sne.sistema.bean;

import java.util.Date;


public class RegistroFreelancer {
	private long id;
	private Freelancer freelancer;
	private Date dataInicio;
	private Date dataFim;
	private Funcionario funcionarioRegistro;
	private boolean deletado;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Freelancer getFreelancer() {
		return freelancer;
	}
	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Funcionario getFuncionarioRegistro() {
		return funcionarioRegistro;
	}
	public void setFuncionarioRegistro(Funcionario funcionarioRegistro) {
		this.funcionarioRegistro = funcionarioRegistro;
	}
	public boolean isDeletado() {
		return deletado;
	}
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	public String toString() {
		return freelancer.getNome();
	}
}
