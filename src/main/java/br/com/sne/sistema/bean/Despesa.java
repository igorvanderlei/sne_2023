package br.com.sne.sistema.bean;

import java.math.BigDecimal;
import java.util.Date;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.OpcaoPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Despesa {
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	private OpcaoPagamento opcaoPag;
	
	
	private String descricao;
	private String observacoes;
	private BigDecimal valor;
	private Date dataCadastro;
	private Date dataVencimento;
	private Date dataPagamento;
	private BigDecimal valorPago;
	private boolean situacao; 
	private CentroCusto centroCusto;
	private FontePagadora fontePagadora;
	private Fornecedor fornecedor;
	private Unidade empresa;
	private OrdemServico ordemServico;

	private Funcionario funcionarioCadastro;
	private Freelancer freelancer;
	
	private String tipo; // F-Pessoa Física; J-Pessoa Jurídica
	
	public Freelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	private boolean comissao = false;
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public BigDecimal getValorPago() {
		return valorPago;
	}
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	public boolean isSituacao() {
		return situacao;
	}
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	public CentroCusto getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Funcionario getFuncionarioCadastro() {
		return funcionarioCadastro;
	}
	public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public String toString() {
		return descricao;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Unidade getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Unidade empresa) {
		this.empresa = empresa;
	}

	public FontePagadora getFontePagadora() {
		return fontePagadora;
	}

	public void setFontePagadora(FontePagadora fontePagadora) {
		this.fontePagadora = fontePagadora;
	}

	public OpcaoPagamento getOpcaoPag() {
		return opcaoPag;
	}

	public void setOpcaoPag(OpcaoPagamento opcaoPag) {
		this.opcaoPag = opcaoPag;
	}

	public boolean isComissao() {
		return comissao;
	}

	public void setComissao(boolean comissao) {
		this.comissao = comissao;
	}
}