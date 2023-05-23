package br.com.sne.sistema.bean;

public class DadosBancarios {
	
	private long id;
	private String banco;
	private String agencia;
	private String conta;
	private boolean deletado;
	
	public DadosBancarios(String banco, String agencia, String conta) {
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.deletado = false;
	}
	
	public DadosBancarios() {
		this.deletado = false;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String toString() {
		return this.getBanco();
	}

}
