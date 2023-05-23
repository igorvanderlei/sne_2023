package br.com.sne.sistema.bean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.SituacaoOrcamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public class Orcamento {
	private long id;   
	private long idPai;
	
	@Enumerated(EnumType.ORDINAL)
	private SituacaoOrcamento situacao;
	
	private Date dataInicio;
	private Date dataFim;   
	private Date dataOrcamento;
	private Date dataMontagem;
	
	private boolean terceirizadoEmpresa;
	private boolean terceirizadoFornecedor;

	private boolean gerouOrdemServico;
	private boolean maisAtual;
	
	private Date horaInicio;
	private Date horaFim;
	private Date horaMontagem;
	
	private BigDecimal preco;
	private BigDecimal precoTerceirizado;
	private String observacoes;
	private String observacoesCliente;
	private String observacoesFinanceiras;
	private String detalhesEvento;
	private String nomeEvento;
	private LocalEvento local; 
	private List<RecursoSolicitado> recursoSolicitado;
	private List<RecursoTerceirizadoSolicitado> recursoTerceirizadoSolicitado;
	private Cliente cliente;
	private Funcionario funcionario;
	private Unidade unidade;
	private String condicoesPagamento;
	private BigDecimal desconto;

	private Funcionario vendedorConjunto;
	private String nomeProposta;
	private String cargoProposta;
	private String telefoneProposta;
	private String nomePropostaConjunta;
	private String cargoPropostaConjunta;
	private String telefonePropostaConjunta;
	
	private BigDecimal subtotalTerceirizadoEmpresa;
	private BigDecimal subtotalTerceirizadoForn;
	
	private List<AmbienteEvento> ambientes;
	
	private Orcamento orcOriginal;
	
	private boolean deletado;	 
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public String toString() {
		return nomeEvento;
	}

	public Orcamento() {
	}
	
	public Orcamento(Orcamento orc) {
		this.idPai = orc.getIdPai() != 0? orc.getIdPai():orc.getId();
		this.cliente = orc.getCliente();
		this.dataFim = orc.getDataFim();
		this.dataInicio = orc.getDataInicio();
		this.dataMontagem = orc.getDataMontagem();
		this.funcionario = orc.getFuncionario();
		this.nomeEvento = orc.getNomeEvento();
		this.observacoes = orc.getObservacoes();
		this.observacoesCliente = orc.getObservacoesCliente();
		this.observacoesFinanceiras = orc.getObservacoesFinanceiras();
		this.desconto = orc.getDesconto();
		this.preco = orc.getPreco();
		this.precoTerceirizado = orc.getPrecoTerceirizado();
		this.condicoesPagamento = orc.getCondicoesPagamento();
		this.vendedorConjunto = orc.getVendedorConjunto();
		this.terceirizadoEmpresa = orc.isTerceirizadoEmpresa();
		this.terceirizadoFornecedor = orc.isTerceirizadoFornecedor();
		this.orcOriginal = orc.getOrcOriginal();
		
		this.gerouOrdemServico = orc.isGerouOrdemServico();
		this.subtotalTerceirizadoEmpresa = orc.getSubtotalTerceirizadoEmpresa();
		this.subtotalTerceirizadoForn = orc.getSubtotalTerceirizadoForn();
		this.situacao = orc.getSituacao();
				
		this.nomeProposta = orc.getNomeProposta();
		this.cargoProposta = orc.getCargoProposta();
		this.telefoneProposta = orc.getTelefoneProposta();
		
		this.nomePropostaConjunta = orc.getNomePropostaConjunta();
		this.cargoPropostaConjunta = orc.getCargoPropostaConjunta();
		this.telefonePropostaConjunta = orc.getTelefonePropostaConjunta();
		
		this.horaFim = orc.getHoraFim();
		this.horaInicio = orc.getHoraInicio();
		this.horaMontagem = orc.getHoraMontagem();
		this.detalhesEvento = orc.getDetalhesEvento();
		
		this.local = orc.getLocal();

		HashMap<String, AmbienteEvento> hashAmbiente = new HashMap<String, AmbienteEvento>();
		ambientes = new ArrayList<AmbienteEvento>();
		for(AmbienteEvento amb : orc.getAmbientes()){
			AmbienteEvento novo = new AmbienteEvento(amb.getNome(), amb.getDataInicio(), amb.getDataFim());
			novo.setDescontoCenografia(amb.getDescontoCenografia());
			novo.setDescontoEquipamento(amb.getDescontoEquipamento());
			novo.setDescontoTerceirizado(amb.getDescontoTerceirizado());
			ambientes.add(novo);
			hashAmbiente.put(novo.getNome(), novo);
		}
		this.recursoSolicitado = new ArrayList<RecursoSolicitado>();
		for(RecursoSolicitado rs: orc.getRecursoSolicitado()) {
			RecursoSolicitado rs2 = new RecursoSolicitado(rs.getDataInicio(), rs.getDataFim(),
					 rs.getPrecoUnitario(),rs.getPrecoCusto(), rs.getQuantidade(),
					 rs.getRecurso());
			rs2.setAmbiente(hashAmbiente.get(rs.getAmbiente().getNome()));
			rs2.setDescricao(rs.getDescricao());
			this.recursoSolicitado.add(rs2);
		}
		
		this.recursoTerceirizadoSolicitado = new ArrayList<RecursoTerceirizadoSolicitado>();
		for(RecursoTerceirizadoSolicitado rs: orc.getRecursoTerceirizadoSolicitado()) {
			RecursoTerceirizadoSolicitado rs2 = new RecursoTerceirizadoSolicitado(rs.getDataInicio(), rs.getDataFim(),
					 rs.getPrecoUnitario(),rs.getPrecoEmpresa(), rs.getPrecoFornecedor(),
					 rs.getQuantidade(),rs.getRecurso());
			rs2.setAmbiente(hashAmbiente.get(rs.getAmbiente().getNome()));
			rs2.setDescricao(rs.getDescricao());
			this.recursoTerceirizadoSolicitado.add(rs2);
		}
		
		this.unidade = orc.getUnidade();	
	}
	
	public Orcamento(Date dataInicio, Date dataFim, String observacoes, String nomeEvento, Cliente cliente) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.dataOrcamento = new Date();
		this.observacoes = observacoes;
		this.nomeEvento = nomeEvento;
		this.cliente = cliente;
	}

/*	public BigDecimal calcularTotal() {
		BigDecimal total = new BigDecimal(0);
		for(RecursoSolicitado r: recursoSolicitado) {
			total = total.add(r.getSubTotal());
		}
		return total;
	}*/
	
	public void adicionarRecurso(RecursoSolicitado r) {
		recursoSolicitado.add(r);
	}
	 
	public void removerRecurso(RecursoSolicitado r) {
		recursoSolicitado.remove(r);
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
	public List<RecursoSolicitado> getRecursoSolicitado() {
		return recursoSolicitado;
	}
	
	public List<RecursoTerceirizadoSolicitado> getRecursoTerceirizadoSolicitado() {
		return recursoTerceirizadoSolicitado;
	}

	public void setRecursoSolicitado(List<RecursoSolicitado> recursoSolicitado) {
		this.recursoSolicitado = recursoSolicitado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}


	public Date getDataOrcamento() {
		return dataOrcamento;
	}


	public void setDataOrcamento(Date dataOrcamento) {
		this.dataOrcamento = dataOrcamento;
	}

	public Date getDataMontagem() {
		return dataMontagem;
	}

	public void setDataMontagem(Date dataMontagem) {
		this.dataMontagem = dataMontagem;
	}

	public LocalEvento getLocal() {
		return local;
	}

	public void setLocal(LocalEvento local) {
		this.local = local;
	}
	
	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Date getHoraMontagem() {
		return horaMontagem;
	}

	public void setHoraMontagem(Date horaMontagem) {
		this.horaMontagem = horaMontagem;
	}

	public String getObservacoesCliente() {
		return observacoesCliente;
	}

	public void setObservacoesCliente(String observacoesCliente) {
		this.observacoesCliente = observacoesCliente;
	}

	public String getCondicoesPagamento() {
		return condicoesPagamento;
	}

	public void setCondicoesPagamento(String condicoesPagamento) {
		this.condicoesPagamento = condicoesPagamento;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Funcionario getVendedorConjunto() {
		return vendedorConjunto;
	}

	public void setVendedorConjunto(Funcionario vendedorConjunto) {
		this.vendedorConjunto = vendedorConjunto;
	}

	public String getNomeProposta() {
		return nomeProposta;
	}

	public void setNomeProposta(String nomeProposta) {
		this.nomeProposta = nomeProposta;
	}

	public String getCargoProposta() {
		return cargoProposta;
	}

	public void setCargoProposta(String cargoProposta) {
		this.cargoProposta = cargoProposta;
	}

	public String getTelefoneProposta() {
		return telefoneProposta;
	}

	public void setTelefoneProposta(String telefoneProposta) {
		this.telefoneProposta = telefoneProposta;
	}

	public List<AmbienteEvento> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(List<AmbienteEvento> ambientes) {
		this.ambientes = ambientes;
	}

	public String getObservacoesFinanceiras() {
		return observacoesFinanceiras;
	}

	public void setObservacoesFinanceiras(String observacoesFinanceiras) {
		this.observacoesFinanceiras = observacoesFinanceiras;
	}

	public String getDetalhesEvento() {
		return detalhesEvento;
	}

	public void setDetalhesEvento(String detalhesEvento) {
		this.detalhesEvento = detalhesEvento;
	}

	public SituacaoOrcamento getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoOrcamento situacao) {
		this.situacao = situacao;
	}

	public void setRecursoTerceirizadoSolicitado(List<RecursoTerceirizadoSolicitado> recursoSolicitado) {
		this.recursoTerceirizadoSolicitado = recursoSolicitado;
	}

	public BigDecimal getPrecoTerceirizado() {
		return precoTerceirizado;
	}

	public void setPrecoTerceirizado(BigDecimal precoTerceirizado) {
		this.precoTerceirizado = precoTerceirizado;
	}

	public boolean isTerceirizadoEmpresa() {
		return terceirizadoEmpresa;
	}

	public void setTerceirizadoEmpresa(boolean terceirizadoEmpresa) {
		this.terceirizadoEmpresa = terceirizadoEmpresa;
	}

	public boolean isGerouOrdemServico() {
		return gerouOrdemServico;
	}

	public void setGerouOrdemServico(boolean gerouOrdemServico) {
		this.gerouOrdemServico = gerouOrdemServico;
	}

	public BigDecimal getSubtotalTerceirizadoEmpresa() {
		return subtotalTerceirizadoEmpresa;
	}

	public void setSubtotalTerceirizadoEmpresa(BigDecimal subtotalTerceirizadoEmpresa) {
		this.subtotalTerceirizadoEmpresa = subtotalTerceirizadoEmpresa;
	}

	public BigDecimal getSubtotalTerceirizadoForn() {
		return subtotalTerceirizadoForn;
	}

	public void setSubtotalTerceirizadoForn(BigDecimal subtotalTerceirizadoForn) {
		this.subtotalTerceirizadoForn = subtotalTerceirizadoForn;
	}

	public long getIdPai() {
		return idPai;
	}

	public void setIdPai(long idPai) {
		this.idPai = idPai;
	}

	public boolean isMaisAtual() {
		return maisAtual;
	}

	public void setMaisAtual(boolean atual) {
		this.maisAtual = atual;
	}

	public String getNomePropostaConjunta() {
		return nomePropostaConjunta;
	}

	public void setNomePropostaConjunta(String nomePropostaConjunta) {
		this.nomePropostaConjunta = nomePropostaConjunta;
	}

	public String getCargoPropostaConjunta() {
		return cargoPropostaConjunta;
	}

	public void setCargoPropostaConjunta(String cargoPropostaConjunta) {
		this.cargoPropostaConjunta = cargoPropostaConjunta;
	}

	public String getTelefonePropostaConjunta() {
		return telefonePropostaConjunta;
	}

	public void setTelefonePropostaConjunta(String telefonePropostaConjunta) {
		this.telefonePropostaConjunta = telefonePropostaConjunta;
	}

	public boolean isTerceirizadoFornecedor() {
		return terceirizadoFornecedor;
	}

	public void setTerceirizadoFornecedor(boolean terceirizadoFornecedor) {
		this.terceirizadoFornecedor = terceirizadoFornecedor;
	}

	public Orcamento getOrcOriginal() {
		return orcOriginal;
	}

	public void setOrcOriginal(Orcamento orcOriginal) {
		this.orcOriginal = orcOriginal;
	}

	

	
	
}
 
