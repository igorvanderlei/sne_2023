package br.com.sne.sistema.bean;

import java.util.Date;
import java.util.List;


public class ContagemEstoque {
	private long id;
	private Funcionario funcionario;
	private Date dataContagem;
	private Grupo grupo;
	private Recurso recurso;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	private List<Equipamento> equipamentosFaltantes;
	
	public ContagemEstoque(){
	}
	
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
	public Date getDataContagem() {
		return dataContagem;
	}
	public void setDataContagem(Date dataContagem) {
		this.dataContagem = dataContagem;
	}
	public List<Equipamento> getEquipamentosFaltantes() {
		return equipamentosFaltantes;
	}
	public void setEquipamentosFaltantes(List<Equipamento> equipamentosFaltantes) {
		this.equipamentosFaltantes = equipamentosFaltantes;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	
	public String toString() {
		return funcionario.getNome();
	}
	
}
