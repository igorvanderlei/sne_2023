package br.com.sne.sistema.bean;
import java.util.Date;

public class PessoalAlocado {
	private long id;
	private Date dataInicio;
	private Date dataFim;
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public PessoalAlocado(Date dataInicio, Date dataFim, Funcionario funcionario) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.funcionario = funcionario;
	}

	public PessoalAlocado() {
	}
	
	public String toString() {
		return funcionario.getNome();
	}
	
	 
}
 
