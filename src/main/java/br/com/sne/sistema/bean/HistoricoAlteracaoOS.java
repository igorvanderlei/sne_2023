package br.com.sne.sistema.bean;

import java.util.Date;

public class HistoricoAlteracaoOS {
	private long id;
	private OrdemServico os;
	private Funcionario funcionario;
	private Date data;
	private float valorAntigo;
	private float novoValor;
	private String motivo;
	
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public OrdemServico getOs() {
		return os;
	}
	public void setOs(OrdemServico os) {
		this.os = os;
	}
	public float getValorAntigo() {
		return valorAntigo;
	}
	public void setValorAntigo(float valorAntigo) {
		this.valorAntigo = valorAntigo;
	}
	public float getNovoValor() {
		return novoValor;
	}
	public void setNovoValor(float novoValor) {
		this.novoValor = novoValor;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	
}
