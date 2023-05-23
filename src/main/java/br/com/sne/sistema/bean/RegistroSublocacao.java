package br.com.sne.sistema.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RegistroSublocacao {
	private long id;            
	private Date dataInicio;
	private Date dataFim;   
	private BigDecimal preco;
	private String observacoes;
	private Fornecedor fornecedor;
	
	private List<EquipamentoSublocado> equipamentos;
	private Funcionario funcionario;
	private Unidade unidade;
	
	private boolean deletado;
	private boolean finalizada;
	
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
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public List<EquipamentoSublocado> getEquipamentos() {
		return equipamentos;
	}
	public void setEquipamentos(List<EquipamentoSublocado> equipamentos) {
		this.equipamentos = equipamentos;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public RegistroSublocacao() {
		
	}
	public boolean isDeletado() {
		return deletado;
	}
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	public boolean isFinalizada() {
		return finalizada;
	}
	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}
	public String toString(){
		return this.fornecedor.getNome();
	}
	
	
	
}
