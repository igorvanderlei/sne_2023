package br.com.sne.sistema.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusComodato;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Comodato implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;             
	
	@Enumerated(EnumType.ORDINAL)
	private StatusComodato status;
	
	private Date dataInicio;   
	private Date dataFim;      
	
	private Date dataCadastro;
	private Date dataAprovacao;
	
	private Funcionario funcionarioAprovacao;
	
	private String observacoes;
	private String observacoesFinanceiras;

	
	private List<RecursoSolicitado> recursoSolicitado;
	private LocalEvento local; 
	private Cliente cliente; 
	private List<EquipamentoEnviado> equipamentoEnviado;
	private Funcionario funcionario;
	private Funcionario responsavelEquipamento;
	private Unidade unidade;
	
	private List<AmbienteEvento> ambientes;
	
	private String responsavelCliente;
	private String telefoneResponsavel;
	
	public String getResponsavelCliente() {
		return responsavelCliente;
	}

	public void setResponsavelCliente(String responsavelCliente) {
		this.responsavelCliente = responsavelCliente;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	private boolean deletado;
	
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	
	public Funcionario getResponsavelEquipamento() {
		return responsavelEquipamento;
	}


	public void setResponsavelEquipamento(Funcionario responsavelEquipamento) {
		this.responsavelEquipamento = responsavelEquipamento;
	}


	 
	public void adicionarRecurso(RecursoSolicitado r) {
		recursoSolicitado.add(r);
	}
	 
	public void removerRecurso(RecursoSolicitado r) {
		recursoSolicitado.remove(r);
	}
	 
	public void adicionarEquipamento(EquipamentoEnviado e) {
		equipamentoEnviado.add(e);
	}
	 
	public void removerEquipamento(EquipamentoEnviado e) {
		equipamentoEnviado.remove(e);
	}
	 
	public void devolverEquipamento(EquipamentoEnviado e) {
		e.devolver();
	}
	 
	public List<AmbienteEvento> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(List<AmbienteEvento> ambientes) {
		this.ambientes = ambientes;
	}
	
	public Comodato() {
		recursoSolicitado = new ArrayList<RecursoSolicitado>();
		equipamentoEnviado = new ArrayList<EquipamentoEnviado>();
		ambientes = new ArrayList<AmbienteEvento>();
	}
	
	public Comodato(Comodato orc) {
		this.cliente = orc.getCliente();
		this.dataFim = orc.getDataFim();
		this.dataInicio = orc.getDataInicio();
		this.funcionario = orc.getFuncionario();
		this.observacoes = orc.getObservacoes();
		this.observacoesFinanceiras = orc.getObservacoesFinanceiras();
		
		this.local = orc.getLocal();
		//this.local.setEndereco(new Endereco());
		this.status = StatusComodato.PENDENTE;
		this.recursoSolicitado = new ArrayList<RecursoSolicitado>();

		HashMap<String, AmbienteEvento> hashAmbiente = new HashMap<String, AmbienteEvento>();
		ambientes = new ArrayList<AmbienteEvento>();
		for(AmbienteEvento amb : orc.getAmbientes()){
			AmbienteEvento novo = new AmbienteEvento(amb.getNome(), amb.getDataInicio(), amb.getDataFim());
			ambientes.add(novo);
			hashAmbiente.put(novo.getNome(), novo);
		}
		this.recursoSolicitado = new ArrayList<RecursoSolicitado>();
		for(RecursoSolicitado rs: orc.getRecursoSolicitado()) {
			RecursoSolicitado rs2 = new RecursoSolicitado(rs.getDataInicio(), rs.getDataFim(),
					 rs.getPrecoUnitario(),rs.getPrecoCusto(), rs.getQuantidade(),
					 rs.getRecurso());
			if(rs.getAmbiente() != null)
				rs2.setAmbiente(hashAmbiente.get(rs.getAmbiente().getNome()));
			this.recursoSolicitado.add(rs2);
		}

		this.unidade = orc.getUnidade();		
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

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public StatusComodato getStatus() {
		return status;
	}

	public void setStatus(StatusComodato status) {
		this.status = status;
	}

	public List<RecursoSolicitado> getRecursoSolicitado() {
		return recursoSolicitado;
	}

	public void setRecursoSolicitado(List<RecursoSolicitado> recursoSolicitado) {
		this.recursoSolicitado = recursoSolicitado;
	}

	public LocalEvento getLocal() {
		return local;
	}

	public void setLocal(LocalEvento local) {
		this.local = local;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<EquipamentoEnviado> getEquipamentoEnviado() {
		return equipamentoEnviado;
	}

	public void setEquipamentoEnviado(List<EquipamentoEnviado> equipamentoEnviado) {
		this.equipamentoEnviado = equipamentoEnviado;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public Funcionario getFuncionarioAprovacao() {
		return funcionarioAprovacao;
	}

	public void setFuncionarioAprovacao(Funcionario funcionarioAprovacao) {
		this.funcionarioAprovacao = funcionarioAprovacao;
	}

	public boolean equals(Object arg0) {
		if(arg0 instanceof Comodato) {
			return id == ((Comodato) arg0).getId();
		}
		return false;
	}

	public String getObservacoesFinanceiras() {
		return observacoesFinanceiras;
	}

	public void setObservacoesFinanceiras(String observacoesFinanceiras) {
		this.observacoesFinanceiras = observacoesFinanceiras;
	}

	public String toString() {
		return "" + id;
	}
}
 
