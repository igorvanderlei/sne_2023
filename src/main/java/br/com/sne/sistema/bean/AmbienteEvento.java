package br.com.sne.sistema.bean;

import java.math.BigDecimal;
import java.util.Date;

public class AmbienteEvento {
	private long id;
	private String nome;
	private Date dataInicio;
	private Date dataFim;
	private BigDecimal descontoEquipamento;
	private BigDecimal descontoTerceirizado;
	private BigDecimal descontoCenografia;
	
	public AmbienteEvento(String nome, Date dataInicio, Date dataFim) {
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		setDescontoEquipamento(BigDecimal.ZERO);
		setDescontoTerceirizado(BigDecimal.ZERO);
		setDescontoCenografia(BigDecimal.ZERO);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public String toString() {
		return nome;
	}
	public AmbienteEvento() {
		setDescontoEquipamento(BigDecimal.ZERO);
		setDescontoTerceirizado(BigDecimal.ZERO);
		setDescontoCenografia(BigDecimal.ZERO);
		
	}
	public BigDecimal getDescontoEquipamento() {
		return descontoEquipamento;
	}
	public void setDescontoEquipamento(BigDecimal descontoEquipamento) {
		this.descontoEquipamento = descontoEquipamento;
	}
	public BigDecimal getDescontoTerceirizado() {
		return descontoTerceirizado;
	}
	public void setDescontoTerceirizado(BigDecimal descontoTerceirizado) {
		this.descontoTerceirizado = descontoTerceirizado;
	}
	public BigDecimal getDescontoCenografia() {
		return descontoCenografia;
	}
	public void setDescontoCenografia(BigDecimal descontoCenografia) {
		this.descontoCenografia = descontoCenografia;
	}
	
	
}
