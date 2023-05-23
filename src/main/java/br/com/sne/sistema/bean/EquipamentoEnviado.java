package br.com.sne.sistema.bean;
import java.io.Serializable;
import java.util.Date;

public class EquipamentoEnviado implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private boolean status;
	
	private Date dataSaida;
	private Date dataDevolucao;
	
	private Equipamento equipamento;
	private Usuario usuario;
	private Funcionario funcionarioEntrega;
	private Funcionario funcionarioDevolucao;
	private Funcionario funcionarioRecolhimento;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	 
	public void devolver() {
		status = true;
	}
	
	public String toString() {
		return equipamento.getNumeroSerie();
	}

	public EquipamentoEnviado(Date dataSaida, Date dataDevolucao, 
			Equipamento equipamento, Usuario usuario) {
		this.dataSaida = dataSaida;
		this.dataDevolucao = dataDevolucao;
		this.equipamento = equipamento;
		this.usuario = usuario;
		this.status = false;
	}

	public EquipamentoEnviado() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}


	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getFuncionarioEntrega() {
		return funcionarioEntrega;
	}

	public void setFuncionarioEntrega(Funcionario funcionarioEntrega) {
		this.funcionarioEntrega = funcionarioEntrega;
	}

	public Funcionario getFuncionarioDevolucao() {
		return funcionarioDevolucao;
	}

	public void setFuncionarioDevolucao(Funcionario funcionarioDevolucao) {
		this.funcionarioDevolucao = funcionarioDevolucao;
	}

	public Funcionario getFuncionarioRecolhimento() {
		return funcionarioRecolhimento;
	}

	public void setFuncionarioRecolhimento(Funcionario funcionarioRecolhimento) {
		this.funcionarioRecolhimento = funcionarioRecolhimento;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EquipamentoEnviado))
			return false;
		EquipamentoEnviado other = (EquipamentoEnviado) obj;
		if (id != other.id)
			return false;
		return true;
	}


	
}
 
