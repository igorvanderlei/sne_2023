package br.com.sne.sistema.bean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.SpreadsheetFilter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrdemServico implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;    
	
	@Enumerated(EnumType.ORDINAL)
	private StatusOS status;
	
	private Date dataInicio;   
	private Date dataFim;      
	private Date dataMontagem;
	private Date dataHoraDesmontagem;
	
	private boolean terceirizadoEmpresa;
	private boolean terceirizadoFornecedor;
	
	private Date horaInicio;
	private Date horaFim;
	private Date horaMontagem;
	private BigDecimal desconto;
	
	private Date dataCadastro;
	private Date dataAprovacao;
	
	private Funcionario funcionarioAprovacao;
	
	private BigDecimal preco;
	private BigDecimal precoTerceirizado;

	private String observacoes;
	private String observacoesFinanceiras;
	
	private String nomeEvento;
	private String formaPagamento;
	private String condicoesPagamento;
	private String planilha;
	
	private BigDecimal totalAgencia;
	private Date vencimentoAgencia;
	private Fornecedor dadosAgencia;
	private int percentualAgencia;
	private boolean precoIntegralAgencia;

	private String contatoEvento;
	private String telefoneContatoEvento;
	
	private Funcionario vendedorConjunto;
	private String nomeProposta;
	private String cargoProposta;
	private String telefoneProposta;
	private String nomePropostaConjunta;
	private String cargoPropostaConjunta;
	private String telefonePropostaConjunta;
	private String observacoesCliente;
	private String detalhesEvento;
	private String observacoesFaturamento;
	private boolean confirmacaoFaturamento;
	
	private List<RecursoSolicitado> recursoSolicitado;
	private List<RecursoTerceirizadoSolicitado> recursoTerceirizadoSolicitado;

	private LocalEvento local; 
	private Cliente cliente; 
	private List<EquipamentoEnviado> equipamentoEnviado;
	private List<PessoalAlocado> pessoalAlocado;
	private List<RegistroFreelancer> freelancerAlocado;
	private List<AmbienteEvento> ambientes;
	private Funcionario funcionario;
	private Funcionario responsavelEquipamento;
	private Unidade unidade;
	
	private BigDecimal subtotalTerceirizadoEmpresa;
	private BigDecimal subtotalTerceirizadoForn;
	
	private OrdemServico OSOriginal;
	
	private boolean deletado;	 
	private boolean gerouContrato = false;
	
	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	
	public OrdemServico(Orcamento orc) {
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
		this.setPrecoTerceirizado(orc.getPrecoTerceirizado());
		this.horaFim = orc.getHoraFim();
		this.horaInicio = orc.getHoraInicio();
		this.horaMontagem = orc.getHoraMontagem();
		this.condicoesPagamento = orc.getCondicoesPagamento();
		this.vendedorConjunto = orc.getVendedorConjunto();
		this.detalhesEvento = orc.getDetalhesEvento();
		this.terceirizadoEmpresa = orc.isTerceirizadoEmpresa();
		this.terceirizadoFornecedor = orc.isTerceirizadoFornecedor();
		this.subtotalTerceirizadoEmpresa = orc.getSubtotalTerceirizadoEmpresa();
		this.subtotalTerceirizadoForn = orc.getSubtotalTerceirizadoForn();

		this.nomeProposta = orc.getNomeProposta();
		this.cargoProposta = orc.getCargoProposta();
		this.telefoneProposta = orc.getTelefoneProposta();
		
		this.nomePropostaConjunta = orc.getNomePropostaConjunta();
		this.cargoPropostaConjunta = orc.getCargoPropostaConjunta();
		this.telefonePropostaConjunta = orc.getTelefonePropostaConjunta();
		
		this.local = orc.getLocal();
		//this.local.setEndereco(new Endereco());
		this.status = StatusOS.PENDENTE;
		
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
	
	
	public OrdemServico(OrdemServico orc) {
		this.planilha = orc.getPlanilha();
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
		this.horaFim = orc.getHoraFim();
		this.horaInicio = orc.getHoraInicio();
		this.horaMontagem = orc.getHoraMontagem();
		this.preco = orc.getPreco();
		this.precoTerceirizado = orc.getPrecoTerceirizado();
		this.condicoesPagamento = orc.getCondicoesPagamento();
		this.vendedorConjunto = orc.getVendedorConjunto();
		this.detalhesEvento = orc.getDetalhesEvento();
		this.terceirizadoEmpresa = orc.isTerceirizadoEmpresa();
		this.terceirizadoFornecedor = orc.isTerceirizadoFornecedor();

		this.subtotalTerceirizadoEmpresa = orc.getSubtotalTerceirizadoEmpresa();
		this.subtotalTerceirizadoForn = orc.getSubtotalTerceirizadoForn();
		this.OSOriginal = orc.getOSOriginal();
		
		this.totalAgencia = orc.getTotalAgencia();
		this.vencimentoAgencia = orc.getVencimentoAgencia();
		this.dadosAgencia = orc.getDadosAgencia();
		this.percentualAgencia = orc.getPercentualAgencia();
		this.precoIntegralAgencia = orc.isPrecoIntegralAgencia();
		
		this.nomeProposta = orc.getNomeProposta();
		this.cargoProposta = orc.getCargoProposta();
		this.telefoneProposta = orc.getTelefoneProposta();
		
		this.nomePropostaConjunta = orc.getNomePropostaConjunta();
		this.cargoPropostaConjunta = orc.getCargoPropostaConjunta();
		this.telefonePropostaConjunta = orc.getTelefonePropostaConjunta();
		
		this.local = orc.getLocal();
		//this.local.setEndereco(new Endereco());
		this.status = StatusOS.PENDENTE;
		this.recursoSolicitado = new ArrayList<RecursoSolicitado>();

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
			if(rs.getAmbiente() != null)
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

/*	public BigDecimal calcularTotal() {
		BigDecimal total = new BigDecimal(0);
		for(RecursoSolicitado r: recursoSolicitado) {
			total = total.add(r.getSubTotal());
		}
		return total;
	}*/
	
	
	public Funcionario getResponsavelEquipamento() {
		return responsavelEquipamento;
	}


	public void setResponsavelEquipamento(Funcionario responsavelEquipamento) {
		this.responsavelEquipamento = responsavelEquipamento;
	}


	public Date getDataMontagem() {
		return dataMontagem;
	}

	public void setDataMontagem(Date dataHoraMontagem) {
		this.dataMontagem = dataHoraMontagem;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Date getDataHoraDesmontagem() {
		return dataHoraDesmontagem;
	}

	public void setDataHoraDesmontagem(Date dataHoraDesmontagem) {
		this.dataHoraDesmontagem = dataHoraDesmontagem;
	}

	public String toString() {
		return nomeEvento;
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
	 
	public void adicionarPessoa(PessoalAlocado p) {
		pessoalAlocado.add(p);
	}
	 
	public void removerPessoa(PessoalAlocado p) {
		pessoalAlocado.remove(p);
	}

	public OrdemServico() {
		recursoSolicitado = new ArrayList<RecursoSolicitado>();
		recursoTerceirizadoSolicitado = new ArrayList<RecursoTerceirizadoSolicitado>();
		equipamentoEnviado = new ArrayList<EquipamentoEnviado>();
		pessoalAlocado = new ArrayList<PessoalAlocado>();
		ambientes = new ArrayList<AmbienteEvento>();
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

	public StatusOS getStatus() {
		return status;
	}

	public void setStatus(StatusOS status) {
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

	public List<PessoalAlocado> getPessoalAlocado() {
		return pessoalAlocado;
	}

	public void setPessoalAlocado(List<PessoalAlocado> pessoalAlocado) {
		this.pessoalAlocado = pessoalAlocado;
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

	public List<RegistroFreelancer> getFreelancerAlocado() {
		return freelancerAlocado;
	}

	public void setFreelancerAlocado(List<RegistroFreelancer> freelancerAlocado) {
		this.freelancerAlocado = freelancerAlocado;
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

	public String getObservacoesCliente() {
		return observacoesCliente;
	}

	public void setObservacoesCliente(String observacoesCliente) {
		this.observacoesCliente = observacoesCliente;
	}

	public String getContatoEvento() {
		return contatoEvento;
	}

	public void setContatoEvento(String contatoEvento) {
		this.contatoEvento = contatoEvento;
	}

	public String getTelefoneContatoEvento() {
		return telefoneContatoEvento;
	}

	public void setTelefoneContatoEvento(String telefoneContatoEvento) {
		this.telefoneContatoEvento = telefoneContatoEvento;
	}

	public boolean equals(Object arg0) {
		if(arg0 instanceof OrdemServico) {
			return id == ((OrdemServico) arg0).getId();
		}
		return false;
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
	

	public boolean isConfirmacaoFaturamento() {
		return confirmacaoFaturamento;
	}

	public void setConfirmacaoFaturamento(boolean confirmacaoFaturamento) {
		this.confirmacaoFaturamento = confirmacaoFaturamento;
	}

	public String getObservacoesFaturamento() {
		return observacoesFaturamento;
	}

	public void setObservacoesFaturamento(String observacoesFaturamento) {
		this.observacoesFaturamento = observacoesFaturamento;
	}

	public BigDecimal getPrecoTerceirizado() {
		return precoTerceirizado;
	}

	public void setPrecoTerceirizado(BigDecimal precoTerceirizado) {
		this.precoTerceirizado = precoTerceirizado;
	}

	public List<RecursoTerceirizadoSolicitado> getRecursoTerceirizadoSolicitado() {
		return recursoTerceirizadoSolicitado;
	}

	public void setRecursoTerceirizadoSolicitado(List<RecursoTerceirizadoSolicitado> recursoTerceirizadoSolicitado) {
		this.recursoTerceirizadoSolicitado = recursoTerceirizadoSolicitado;
	}

	public boolean isTerceirizadoEmpresa() {
		return terceirizadoEmpresa;
	}

	public void setTerceirizadoEmpresa(boolean terceirizadoEmpresa) {
		this.terceirizadoEmpresa = terceirizadoEmpresa;
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

	public String getPlanilha() {
		return planilha;
	}

	public void setPlanilha(String planilha) {
		this.planilha = planilha;
	}
	
	public void alterarPlanilha(File arquivo) {
		this.planilha = converterPlanilhaString(arquivo);
	}
	
	public static String converterPlanilhaString(File arquivo) {
        try {
        	FileInputStream fin = new FileInputStream(arquivo);
        	byte buffer[];
            byte conteudo[] = new byte[(int)arquivo.length()];

            fin.read(conteudo);
            fin.close();
            
            buffer = conteudo;
    		String objetoStr = Base64.getEncoder().encodeToString(buffer);
    		return objetoStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

	}
	
	public static byte[] converterStringPlanilha(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	/*public static void salvarPlanilha(String planilha) {
		if(planilha != null) {
        	try {
        		JFileChooser chooser = new JFileChooser();
	            chooser.setFileFilter (new SpreadsheetFilter());
	   	     	int retrival = chooser.showSaveDialog(null); //Substitui o null pelo "nome da janela".this
	   	     
	   	     	if (retrival == JFileChooser.APPROVE_OPTION) {      
		   	     	File arquivo = chooser.getSelectedFile();
		        	FileOutputStream fw = new FileOutputStream(arquivo);
		            fw.write(converterStringPlanilha(planilha));
		            fw.close();
	   	     	}
	            
        	} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}*/

	public BigDecimal getTotalAgencia() {
		return totalAgencia;
	}

	public void setTotalAgencia(BigDecimal totalAgencia) {
		this.totalAgencia = totalAgencia;
	}

	public Date getVencimentoAgencia() {
		return vencimentoAgencia;
	}

	public void setVencimentoAgencia(Date vencimentoAgencia) {
		this.vencimentoAgencia = vencimentoAgencia;
	}

	public Fornecedor getDadosAgencia() {
		return dadosAgencia;
	}

	public void setDadosAgencia(Fornecedor dadosAgencia) {
		this.dadosAgencia = dadosAgencia;
	}

	public int getPercentualAgencia() {
		return percentualAgencia;
	}

	public void setPercentualAgencia(int percentualAgencia) {
		this.percentualAgencia = percentualAgencia;
	}

	public boolean isPrecoIntegralAgencia() {
		return precoIntegralAgencia;
	}

	public void setPrecoIntegralAgencia(boolean precoIntegralAgencia) {
		this.precoIntegralAgencia = precoIntegralAgencia;
	}

	public OrdemServico getOSOriginal() {
		return OSOriginal;
	}

	public void setOSOriginal(OrdemServico oSOriginal) {
		OSOriginal = oSOriginal;
	}

	public boolean isGerouContrato() {
		return gerouContrato;
	}

	public void setGerouContrato(boolean gerouContrato) {
		this.gerouContrato = gerouContrato;
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

}

 
