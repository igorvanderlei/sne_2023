package br.com.sne.sistema.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import br.com.sne.sistema.bean.*;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.dao.interfaces.*;
import br.com.sne.sistema.gui.main.InterfaceTelaPrincipal;

@Service
public class Facade implements ApplicationContextAware{
	@Autowired
	private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionStatus status;
    
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	Facade.applicationContext = applicationContext;

    }
    
    public ApplicationContext getApplicationContext() {
    	return applicationContext;
    	
    }
	
    public void beginTransaction() {
    	//if(status == null || status.isCompleted()) {
    		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    		status = transactionManager.getTransaction(definition);
    	//}
    }
    
    public void commit() {
    	if(!status.isCompleted())
    		transactionManager.commit(status);
    }
	
	private static Facade instance;
	private Usuario usuarioLogado;

	@Autowired
	private GrupoDaoInterface grupoDao;	
	@Autowired
	private UnidadeDaoInterface  unidadeDao;
	@Autowired
	private FuncaoDaoInterface  funcaoDao;
	@Autowired
	private FuncionarioDaoInterface  funcionarioDao;
	@Autowired
	private UsuarioDaoInterface  usuarioDao;
	@Autowired
	private TipoUsuarioDaoInterface  tipoUsuarioDao;
	@Autowired
	private RecursoDaoInterface  recursoDao;
	@Autowired
	private EquipamentoDaoInterface  equipamentoDao;
	@Autowired
	private ClienteDaoInterface  clienteDao;
	@Autowired
	private SalaLocalDaoInterface  salaLocalDao;
	@Autowired
	private AmbienteDaoInterface  ambienteDao;
	@Autowired
	private DadosBancariosDaoInterface  dadosBancariosDao;
	@Autowired
	private DescricaoEquipamentoDaoInterface  descricaoEquipamentoDao;
	@Autowired
	private EquipamentoSublocadoDaoInterface  equipamentoSublocadoDao;
	@Autowired
	private FornecedorDaoInterface  fornecedorDao;
	@Autowired
	private FornecedorTerceirizadoDaoInterface  fornecedorTerceirizadoDao;
	@Autowired
	private FreelancerDaoInterface  freelancerDao;
	@Autowired
	private LocalDaoInterface  localDao;
	@Autowired
	private OrdemServicoDaoInterface  ordemServicoDao;
	@Autowired
	private ComodatoDaoInterface  comodatoDao;
	@Autowired
	private RecursoTerceirizadoDaoInterface  recursoTerceirizadoDao;
	@Autowired
	private PautaDaoInterface pautaDao;
	@Autowired
	private HistoricoPautaDaoInterface hisPautaDao;
	@Autowired
	private ReciboDaoInterface  reciboDao;
	@Autowired
	private ContratoDaoInterface  contratoDao;
	@Autowired
	private OrcamentoDaoInterface orcamentoDao;
	@Autowired
	private OsDePassagemDaoInterface osDePassagemDao;
	@Autowired
	private CategoriaDaoInterface categoriaDao;
	@Autowired
	private ContaDaoInterface contaDao;
	@Autowired
	private ContagemDaoInterface contagemDao;
	@Autowired
	private DescarteEquipamentoDaoInterface descarteDao;
	@Autowired
	private CentroCustoDaoInterface centroCustoDao;
	@Autowired
	private ReceitaDaoInterface receitaDao;
	@Autowired
	private DespesaDaoInterface despesaDao;
	@Autowired
	private FontePagadoraDaoInterface fontePagadoraDao;
	@Autowired
	private ManutencaoPreventivaDaoInterface manutencaoPreventivaDao;
	@Autowired
	private ManutencaoCorretivaDaoInterface manutencaoCorretivaDao;
	@Autowired
	private DevolucaoSublocadosDaoInterface devolucaoSublocadosDao;
	@Autowired
	private HistoricoCancelamentoDaoInterface historicoCancelamento;
	@Autowired
	private RegistroSublocacaoDaoInterface registroSublocacaoDao;
	@Autowired
	private HistoricoAlteracaoOSInterface historicoAlteracaoOSDao;
	@Autowired
	private AlertaOSEmergencialDaoInterface alertaOSEmergencialDao;
	@Autowired
	private RecolhimentoDaoInterface recolhimentoDao;
	@Autowired
	private GrupoPautaDaoInterface grupoPautaDao;
	
	private InterfaceTelaPrincipal telaPrincipal;
	
	public GrupoPautaDaoInterface getGrupoPautaDao() {
		return grupoPautaDao;
	}

	public void setGrupoPautaDao(GrupoPautaDaoInterface grupoPautaDao) {
		this.grupoPautaDao = grupoPautaDao;
	}

	public static Facade getInstance() {
		if(instance == null) {
			instance = (Facade) applicationContext.getBean("facade");
		}
		return instance;
	}	

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ManutencaoPreventivaDaoInterface getManutencaoPreventivaDao() {
		return manutencaoPreventivaDao;
	}


	public void setManutencaoPreventivaDao(
			ManutencaoPreventivaDaoInterface manutencaoPreventivaDao) {
		this.manutencaoPreventivaDao = manutencaoPreventivaDao;
	}


	public ManutencaoCorretivaDaoInterface getManutencaoCorretivaDao() {
		return manutencaoCorretivaDao;
	}


	public void setManutencaoCorretivaDao(
			ManutencaoCorretivaDaoInterface manutencaoCorretivaDao) {
		this.manutencaoCorretivaDao = manutencaoCorretivaDao;
	}

	
	
	public HistoricoCancelamentoDaoInterface getHistoricoCancelamento() {
		return historicoCancelamento;
	}


	public void setHistoricoCancelamento(
			HistoricoCancelamentoDaoInterface historicoCancelamento) {
		this.historicoCancelamento = historicoCancelamento;
	}


	public CentroCustoDaoInterface getCentroCustoDao() {
		return centroCustoDao;
	}

	public RegistroSublocacaoDaoInterface getRegistroSublocacaoDao() {
		return registroSublocacaoDao;
	}


	public void setRegistroSublocacaoDao(
			RegistroSublocacaoDaoInterface registroSublocacaoDao) {
		this.registroSublocacaoDao = registroSublocacaoDao;
	}


	public void setCentroCustoDao(CentroCustoDaoInterface centroCustoDao) {
		this.centroCustoDao = centroCustoDao;
	}

	public Usuario localizarUsuarioPorLogin(String login) {
		return usuarioDao.localizarUsuarioPorLogin(login);
	}

	//Ambiente ---------------------------------------
	public void salvarAmbiente(Ambiente newInstance) {
		ambienteDao.salvarAmbiente(newInstance);
	}
	public void atualizarAmbiente(Ambiente transientObject) {
		ambienteDao.atualizarAmbiente(transientObject);
	}
	public Ambiente carregarAmbiente(long id) {
		return ambienteDao.carregarAmbiente(id);
	}
	public List<Ambiente> listarAmbientes() {
		return ambienteDao.listarAmbientes();
	}
	public void removerAmbiente(Ambiente persistentObject) {
		ambienteDao.removerAmbiente(persistentObject);
	}
	
	//Sala Local ---------------------------------------
		public void salvarSalaLocal(SalaLocal newInstance) {
			salaLocalDao.salvarSalaLocal(newInstance);
		}
		public void atualizarSalaLocal(SalaLocal transientObject) {
			salaLocalDao.atualizarSalaLocal(transientObject);
		}
		public SalaLocal carregarSalaLocal(long id) {
			return salaLocalDao.carregarSalaLocal(id);
		}
		public List<SalaLocal> listarSalaLocals() {
			return salaLocalDao.listarSalaLocals();
		}
		public List<SalaLocal> listarSalaLocalsPorLocalEvento() {
			return salaLocalDao.listarSalaLocals();
		}
		public void removerSalaLocal(SalaLocal persistentObject) {
			salaLocalDao.removerSalaLocal(persistentObject);
		}
	
//Pauta ---------------------------------------
	public void salvarPauta(Pauta newInstance) {
		pautaDao.salvarPauta(newInstance);
	}
	public void atualizarPauta(Pauta transientObject) {
		pautaDao.atualizarPauta(transientObject);
	}

	public void removerPauta(Pauta newInstance) {
		pautaDao.removerPauta(newInstance);
	}
	public List<Pauta> listarPautas(int campo, String texto) {
		return pautaDao.listarPautas(campo,texto);
	}
	public List<Pauta> listarPautas(int campo, String texto,Funcionario f) {
		return pautaDao.listarPautas(campo,texto,f);
	}
	public List<Pauta> listarPautas() {
		return pautaDao.listarPautas();
	}
	public List<Pauta> listarPautas(Funcionario f) {
		return pautaDao.listarPautas(f);
	}
	public void listarPautasCanceladas() {
		pautaDao.listarPautasCanceladas();
	}
	public void restaruarPauta(Pauta persistentObject) {
		pautaDao.restaurarPauta(persistentObject);
	}
	
	//HistoricoPauta ---------------------------------------
	public void salvarHistoricoPauta(HistoricoPauta newInstance) {
		hisPautaDao.salvarHistoricoPauta(newInstance);
	}
	public List<HistoricoPauta> listarHistoricoPautas(Pauta pauta) {
		return hisPautaDao.listarHistoricoPautas(pauta);
	}
	public HistoricoPauta carregarHistoricoPauta(long id) {
		return hisPautaDao.carregarHistoricoPauta(id);
	}
	
	//Registro de Subloca��o

	public void atualizarRegistroSublocacao(RegistroSublocacao transientObject)
	 {
		registroSublocacaoDao.atualizarRegistroSublocacao(transientObject);
	}

	

	public List<RegistroSublocacao> listarRegistroSublocacaosPendentesPorFornecedor(
			Fornecedor f)  {
		return registroSublocacaoDao
				.listarRegistroSublocacaosPendentesPorFornecedor(f);
	}


	public RegistroSublocacao carregarRegistroSublocacao(long id)
	 {
		return registroSublocacaoDao.carregarRegistroSublocacao(id);
	}


	public List<RegistroSublocacao> listarRegistroSublocacaos()
	 {
		return registroSublocacaoDao.listarRegistroSublocacaos();
	}


	public void removerRegistroSublocacao(RegistroSublocacao persistentObject)
	 {
		for(EquipamentoSublocado eqs: persistentObject.getEquipamentos()) {
			if(eqs.getStatus() != StatusEquipamento.DISPONIVEL) {
				throw new RuntimeException("O registro de sublocação não pode ser removido pois possui equipamentos locados");
			}
			
		}
			
		registroSublocacaoDao.removerRegistroSublocacao(persistentObject);
		for(EquipamentoSublocado eqs: persistentObject.getEquipamentos())
			equipamentoSublocadoDao.removerEquipamentoSublocado(eqs);
	}


	public void salvarRegistroSublocacao(RegistroSublocacao newInstance)
	 {
		registroSublocacaoDao.salvarRegistroSublocacao(newInstance);
	}


	public void restaurarRegistroSublocacao(RegistroSublocacao persistentObject)
	 {
		registroSublocacaoDao.restaurarRegistroSublocacao(persistentObject);
		for(EquipamentoSublocado eqs: persistentObject.getEquipamentos())
			equipamentoSublocadoDao.restaurarEquipamentoSublocado(eqs);
	}


	public List<RegistroSublocacao> listarRegistroSublocacaosExcluidos()
	 {
		return registroSublocacaoDao.listarRegistroSublocacaosExcluidos();
	}


	//Cliente---------------------------------------	
	public void salvarCliente(Cliente c) {
		if(clienteDao.listarClientes(c.getCnpj(), c.getNome(), c.getContato()).size() > 0)
			throw new DuplicatedRegisterException();
		clienteDao.salvarCliente(c);
	}



	public void atualizarCliente(Cliente transientObject) {
		clienteDao.atualizarCliente(transientObject);
	}
	public Cliente carregarCliente(long id) {
		return clienteDao.carregarCliente(id);
	}
	public List<Cliente> listarClientes(int campo, String texto) {
		return clienteDao.listarClientes(campo, texto);
	}
	public List<Cliente> listarClientes(int campo, String texto, Funcionario f) {
		return clienteDao.listarClientes(campo,texto, f);
	}
	public void removerCliente(Cliente persistentObject) {
		clienteDao.removerCliente(persistentObject);
	}
	
	//Dados Bancarios---------------------------------------	
		public void salvarDadosBancarios(DadosBancarios c) {
			
			dadosBancariosDao.salvarDadosBancarios(c);
		}



		public void atualizarDadosBancarios(DadosBancarios transientObject) {
			dadosBancariosDao.atualizarDadosBancarios(transientObject);
		}
		public DadosBancarios carregarDadosBancarios(long id) {
			return dadosBancariosDao.carregarDadosBancarios(id);
		}
		public List<DadosBancarios> listarDadosBancarios(int campo, String texto) {
			return dadosBancariosDao.listarDadosBancarios(campo, texto);
		}
		public void removerDadosBancarios(DadosBancarios persistentObject) {
			dadosBancariosDao.removerDadosBancarios(persistentObject);
		}

	//Descrição de Equipamento-----------------------------------------------	
	public void salvarDescricaoEquipamento(DescricaoEquipamento newInstance) {
		descricaoEquipamentoDao.salvarDescricaoEquipamento(newInstance);
	}
	public void atualizarDescricaoEquipamento(DescricaoEquipamento transientObject) {
		descricaoEquipamentoDao.atualizarDescricaoEquipamento(transientObject);
	}
	public DescricaoEquipamento carregarDescricaoEquipamento(long id) {
		return descricaoEquipamentoDao.carregarDescricaoEquipamento(id);
	}
	public List<DescricaoEquipamento> listarDescricoesEquipamentos() {
		return descricaoEquipamentoDao.listarDescricoesEquipamentos();
	}
	public void removerDescricaoEquipamento(DescricaoEquipamento persistentObject) {
		descricaoEquipamentoDao.removerDescricaoEquipamento(persistentObject);
	}
	public List<Equipamento> listarEquipamento(Set<StatusEquipamento> status) {
		return equipamentoDao.listarEquipamentosPorStatus(status);
	}

	public List<Equipamento> listarEquipamentosPorStatus(
			Set<StatusEquipamento> status)  {
		return equipamentoDao.listarEquipamentosPorStatus(status);
	}

	public List<Equipamento> listarEquipamentosPorGrupo(Grupo g, boolean incluirSublocados) {
		return equipamentoDao.listarEquipamentosPorGrupo(g, incluirSublocados);
	}

	public List<Equipamento> listarEquipamentosPorRecurso(Recurso r, boolean incluirSublocados) {
		return equipamentoDao.listarEquipamentosPorRecurso(r, incluirSublocados);
	}


	public int contarEquipamentos(DescricaoEquipamento desc,
			StatusEquipamento status) {
		return descricaoEquipamentoDao.contarEquipamentos(desc, status);
	}
	public int contarTodosEquipamentos(DescricaoEquipamento desc) {
		return descricaoEquipamentoDao.contarTodosEquipamentos(desc);
	}
	public int contarTodosFuncionario(Funcao func) {
		return descricaoEquipamentoDao.contarTodosFuncionario(func);
	}

	//Equipamento-------------------------------------------
	public void salvarEquipamento(Equipamento newInstance) {
		Equipamento e = equipamentoDao.localizarEquipamentoCodigo(newInstance.getNumeroSerie());
		if(e != null)
			throw new DuplicatedRegisterException();
		equipamentoDao.salvarEquipamento(newInstance);
	}
	public void atualizarEquipamento(Equipamento transientObject, String serieAntigo) {
		if(transientObject.getNumeroSerie().equals(serieAntigo)){
			equipamentoDao.atualizarEquipamento(transientObject, serieAntigo);
		}else{
			Equipamento e = equipamentoDao.localizarEquipamentoCodigo(transientObject.getNumeroSerie());
			if(e != null){
				throw new DuplicatedRegisterException();
			}else{
				equipamentoDao.atualizarEquipamento(transientObject, serieAntigo);
			}

		}			 
	}

	public void atualizarEquipamentoDevolucao(Equipamento e) {
		equipamentoDao.atualizarEquipamentoDevolucao(e);
	}


	public Equipamento carregarEquipamento(long id) {
		return equipamentoDao.carregarEquipamento(id);
	}

	public Equipamento localizarEquipamentoCodigo(String codigo)
	 {
		return equipamentoDao.localizarEquipamentoCodigo(codigo);
	}

	public EquipamentoEnviado localizarEquipamentoEnviado(String codigo) 
	{
		return equipamentoDao.localizarEquipamentoEnviado(codigo);
	}

	public List<Equipamento> listarEquipamento() {
		return equipamentoDao.listarEquipamento();
	}
	public List<Equipamento> listarEquipamentosDisponiveis() {
		return equipamentoDao.listarEquipamentosDisponiveis();
	}
	public void removerEquipamento(Equipamento persistentObject) {
		if(persistentObject.getStatus() != StatusEquipamento.DISPONIVEL) {
			throw new RuntimeException("Erro ao remover o Equipamento");
		}
		if(localizarEquipamentoEnviadoRastrear(persistentObject.getNumeroSerie()) != null) {
			throw new RuntimeException("Erro ao remover o Equipamento");
		}
		equipamentoDao.removerEquipamento(persistentObject);
	}

	//EquipamentoSublocado--------------------------------------------------
	public void salvarEquipamentoSublocado(EquipamentoSublocado newInstance) {
		equipamentoSublocadoDao.salvarEquipamentoSublocado(newInstance);
	}
	public void atualizarEquipamentoSublocado(EquipamentoSublocado transientObject) {
		equipamentoSublocadoDao.atualizarEquipamentoSublocado(transientObject);
	}
	public EquipamentoSublocado carregarEquipamentoSublocado(long id) {
		return equipamentoSublocadoDao.carregarEquipamentoSublocado(id);
	}
	public List<EquipamentoSublocado> listarEquipamentosSublocados() {
		return equipamentoSublocadoDao.listarEquipamentosSublocados();
	}
	public void removerEquipamentoSublocado(EquipamentoSublocado persistentObject) {
		equipamentoSublocadoDao.removerEquipamentoSublocado(persistentObject);
	}

	//Fornecedor-----------------------------------------
	public void salvarFornecedor(Fornecedor newInstance) {
		Unidade un = unidadeDao.localizarUnidadePorCodigo(newInstance.getCodigo());
		Fornecedor forn = fornecedorDao.localizarFornecedorPorCodigo(newInstance.getCodigo());
		
		if(un != null || forn != null) {
			throw new DuplicatedRegisterException();
		}
		fornecedorDao.salvarFornecedor(newInstance);
	}
	public void atualizarFornecedor(Fornecedor transientObject) {
		fornecedorDao.atualizarFornecedor(transientObject);
	}
	public Fornecedor carregarFornecedor(long id) {
		return fornecedorDao.carregarFornecedor(id);
	}
	public List<Fornecedor> listarFornecedores() {
		return fornecedorDao.listarFornecedores();
	}
	public Fornecedor carregarFornecedor(DadosBancarios dados) {
		return fornecedorDao.carregarFornecedor(dados);
	}
	public void removerFornecedor(Fornecedor persistentObject) {
		if(registroSublocacaoDao.listarRegistroSublocacaosPendentesPorFornecedor(persistentObject).size() > 0)
			throw new RuntimeException("Erro ao remover Fornecedor");
		fornecedorDao.removerFornecedor(persistentObject);
	}
	
	//Fornecedor Terceirizado-----------------------------------------
		public void salvarFornecedorTerceirizado(FornecedorTerceirizado newInstance) {
			Unidade un = unidadeDao.localizarUnidadePorCodigo(newInstance.getCodigo());
			FornecedorTerceirizado forn = fornecedorTerceirizadoDao.localizarFornecedorTerceirizadoPorCodigo(newInstance.getCodigo());
			
			if(un != null || forn != null) {
				throw new DuplicatedRegisterException();
			}
			fornecedorTerceirizadoDao.salvarFornecedorTerceirizado(newInstance);
		}
		public void atualizarFornecedorTerceirizado(FornecedorTerceirizado transientObject) {
			fornecedorTerceirizadoDao.atualizarFornecedorTerceirizado(transientObject);
		}
		public FornecedorTerceirizado carregarFornecedorTerceirizado(long id) {
			return fornecedorTerceirizadoDao.carregarFornecedorTerceirizado(id);
		}
		public List<FornecedorTerceirizado> listarFornecedorTerceirizadoes() {
			return fornecedorTerceirizadoDao.listarFornecedorTerceirizados();
		}
		public void removerFornecedorTerceirizado(FornecedorTerceirizado persistentObject) {
			fornecedorTerceirizadoDao.removerFornecedorTerceirizado(persistentObject);
		}

	//Freelancer-----------------------------------------
	public void salvarFreelancer(Freelancer newInstance) {
		freelancerDao.salvarFreelancer(newInstance);
	}
	public void atualizarFreelancer(Freelancer transientObject) {
		freelancerDao.atualizarFreelancer(transientObject);
	}
	public Freelancer carregarFreelancer(long id) {
		return freelancerDao.carregarFreelancer(id);
	}
	public List<Freelancer> listarFreelancers() {
		return freelancerDao.listarFreelancers();
	}
	public void removerFreelancer(Freelancer persistentObject) {
		freelancerDao.removerFreelancer(persistentObject);
	}

	//Fun�ao--------------------------------------
	public void salvarFuncao(Funcao newInstance) {
		funcaoDao.salvarFuncao(newInstance);
	}
	public void atualizarFuncao(Funcao transientObject) {
		funcaoDao.atualizarFuncao(transientObject);
	}
	public Funcao carregarFuncao(long id) {
		return funcaoDao.carregarFuncao(id);
	}
	public List<Funcao> listarFuncoes() {
		return funcaoDao.listarFuncoes();
	}
	public void removerFuncao(Funcao persistentObject) {
		funcaoDao.removerFuncao(persistentObject);
	}

	//Funcion�rio-------------------------------------------
	public void salvarFuncionario(Funcionario newInstance) {
		funcionarioDao.salvarFuncionario(newInstance);
	}
	public void atualizarFuncionario(Funcionario transientObject) {
		funcionarioDao.atualizarFuncionario(transientObject);
	}
	public Funcionario carregarFuncionario(long id) {
		return funcionarioDao.carregarFuncionario(id);
	}
	public List<Funcionario> listarFuncionarios() {
		return funcionarioDao.listarFuncionarios();
	}
	public void removerFuncionario(Funcionario persistentObject) {
		if(usuarioDao.listarUsuarios(persistentObject).size() > 0)
			throw new RuntimeException("Erro ao remover Funcionario");
		funcionarioDao.removerFuncionario(persistentObject);
	}
	public List<Funcionario> procurarFuncionarioPorNome (String arg0) {
		return funcionarioDao.procurarFuncionarioPorNome(arg0);		
	}

	//Local--------------------------------------
	public void salvarLocal(Local newInstance) {
		localDao.salvarLocal(newInstance);
	}
	public void atualizarLocal(Local transientObject) {
		localDao.atualizarLocal(transientObject);
	}
	public Local carregarLocal(long id) {
		return localDao.carregarLocal(id);
	}
	public List<Local> listarLocais() {
		return localDao.listarLocais();
	}

	public void removerLocal(Local persistentObject) {
		localDao.removerLocal(persistentObject);
	}

	//Ordem de Servi�o---------------------------------------
	public void salvarOrdemServico(OrdemServico newInstance) {
		ordemServicoDao.salvarOrdemServico(newInstance);
	}
	public void atualizarOrdemServico(OrdemServico transientObject) {
		ordemServicoDao.atualizarOrdemServico(transientObject);
	}
	public OrdemServico carregarOrdemServico(long id) {
		return ordemServicoDao.carregarOrdemServico(id);
	}
	public List<OrdemServico> listarOrdemServicos(int campo, String texto) {
		return ordemServicoDao.listarOrdemServicos(campo,texto);
	}
	
	public List<OrdemServico> listarOrdemServicosAprovadas(int campo, String texto){
		return ordemServicoDao.listarOrdemServicosAprovadas(campo, texto);
	}
	
	public void apagarOS(Date dataLimite) {
		ordemServicoDao.apagarOS(dataLimite);
	}

	public List<OrdemServico> listarOrdemServicosEnviarEquipamento()
			 {
		return ordemServicoDao.listarOrdemServicosEnviarEquipamento();
	}
	
	

	public List<OrdemServico> listarOrdemServicosPagamento(Date inicio, Date fim, boolean osExtra)
			 {
		return ordemServicoDao.listarOrdemServicosPagamento(inicio, fim,osExtra);
	}
	
	public List<OrdemServico> listarOrdemServicosPagamento(Date inicio, Date fim, Funcionario f,boolean osExtra)
			 {
		return ordemServicoDao.listarOrdemServicosPagamento(inicio, fim,f,osExtra);
	}
	
	

	public List<OrdemServico> listarOrdemServicosPagamento(StatusOS status)
			 {
		return ordemServicoDao.listarOrdemServicosPagamento(status);
	}

	public List<OrdemServico> listarOrdemServicosPagamento(Date inicio,
			Date fim, StatusOS status)  {
		return ordemServicoDao
				.listarOrdemServicosPagamento(inicio, fim, status);
	}

	public List<OrdemServicoSemEquipamento> listarOrdemServicoSemEquipamento()
			 {
		return ordemServicoDao.listarOrdemServicoSemEquipamento();
	}

	public List<OrdemServico> listarOrdemServicosEmergencial()
			 {
		return ordemServicoDao.listarOrdemServicosEmergencial();
	}
	
	


	public List<OrdemServico> listarOrdemServicosEmergencialAberta()
			 {
		return ordemServicoDao.listarOrdemServicosEmergencialAberta();
	}

	public List<OrdemServico> listarOrdemServicosPagamento()
			 {
		return ordemServicoDao.listarOrdemServicosPagamento();
	}
	
	


	public List<OrdemServico> listarOrdemServicosEstornada()
			 {
		return ordemServicoDao.listarOrdemServicosEstornada();
	}


	public List<OrdemServico> listarOrdemServicos(int campo, String texto,Funcionario f)
	 {
		return ordemServicoDao.listarOrdemServicos(campo,texto,f);
	}

	public List<OrdemServico> listarOrdemServicosPorEquipamento(long equipamento ) 
	 {
		return ordemServicoDao.listarOrdemServicosPorEquipamento(equipamento);
	}
	
	


	public List<OrdemServico> listarOrdemServicosEmergencialAlertaAberto()
			 {
		return ordemServicoDao.listarOrdemServicosEmergencialAlertaAberto();
	}

	public List<OrdemServico> listarOrdemEmAndamento()  {
		return ordemServicoDao.listarOrdemEmAndamento();
	}


	
	
	
	public List<OrdemServico> listarOrdemAtiva()  {
		return ordemServicoDao.listarOrdemAtiva();
	}


	public List<OrdemServico> listarOrdemServicos(Date d)  {
		return ordemServicoDao.listarOrdemServicos(d);
	}

	public OrdemServico localizaOrdemServicosPorEquipamento(long equipamento) 
	{
		return ordemServicoDao.localizaOrdemServicosPorEquipamento(equipamento);
	}

	
	
	
	
	public EquipamentoEnviado localizarEquipamentoEnviadoRastrear(String codigo)
			 {
		return equipamentoDao.localizarEquipamentoEnviadoRastrear(codigo);
	}


	public void removerOrdemServico(OrdemServico persistentObject) {
		ordemServicoDao.removerOrdemServico(persistentObject);
	}

	public void atualizarEquipamentoEnviado(EquipamentoEnviado transientObject) 
	{
		ordemServicoDao.atualizarEquipamentoEnviado(transientObject);
	}

	//Recurso---------------------------------------
	public void salvarRecurso(Recurso newInstance) {
		List<Recurso> subgrupos = recursoDao.listarRecurso(newInstance.getGrupo());
		for(Recurso r:subgrupos) {
			if(r.getCodigo().equals(newInstance.getCodigo())) {
				throw new DuplicatedRegisterException();
			}

		}
		recursoDao.salvarRecurso(newInstance);
	}
	public void atualizarRecurso(Recurso transientObject) {
		recursoDao.atualizarRecurso(transientObject);
	}
	public Recurso carregarRecurso(long id) {
		return recursoDao.carregarRecurso(id);
	}
	public List<Recurso> listarRecursos() {
		return recursoDao.listarRecursos();
	}
	public List<Recurso> listarRecurso(Grupo grp) {
		return recursoDao.listarRecurso(grp);
	}
	public List<Recurso> listarRecursosAtivos() {
		return recursoDao.listarRecursosAtivos();
	}
	public void removerRecurso(Recurso persistentObject) {
		List<Equipamento> equipamentos = listarEquipamentosPorRecurso(persistentObject, true);
		if(equipamentos.size() > 0) {
			throw new RuntimeException("Não é possível remover o Subgrupo selecionado pois existem Equipamentos cadastrados no mesmo!");
		}

		recursoDao.removerRecurso(persistentObject);
	}
	
	//Recurso Terceirizado---------------------------------------
		public void salvarRecursoTerceirizado(RecursoTerceirizado newInstance) {
			List<RecursoTerceirizado> subgrupos = recursoTerceirizadoDao.listarRecursoTerceirizado(newInstance.getGrupo());
			for(RecursoTerceirizado r:subgrupos) {
				if(r.getCodigo().equals(newInstance.getCodigo())) {
					throw new DuplicatedRegisterException();
				}

			}
			recursoTerceirizadoDao.salvarRecursoTerceirizado(newInstance);
		}
		public void atualizarRecursoTerceirizado(RecursoTerceirizado transientObject) {
			recursoTerceirizadoDao.atualizarRecursoTerceirizado(transientObject);
		}
		public RecursoTerceirizado carregarRecursoTerceirizado(long id) {
			return recursoTerceirizadoDao.carregarRecursoTerceirizado(id);
		}
		public List<RecursoTerceirizado> listarRecursoTerceirizados() {
			return recursoTerceirizadoDao.listarRecursoTerceirizados();
		}
		public List<RecursoTerceirizado> listarRecursoTerceirizado(Grupo grp) {
			return recursoTerceirizadoDao.listarRecursoTerceirizado(grp);
		}
		public List<RecursoTerceirizado> listarRecursoTerceirizadosAtivos() {
			return recursoTerceirizadoDao.listarRecursoTerceirizadosAtivos();
		}
		public void removerRecursoTerceirizado(RecursoTerceirizado persistentObject) {
			recursoTerceirizadoDao.removerRecursoTerceirizado(persistentObject);
		}


	//Tipo de Usu�rio---------------------------------------
	public void salvarTipoUsuario(TipoUsuario newInstance) {
		if(tipoUsuarioDao.procurarTipoUsuarios(newInstance.getNome()).size() > 0)
			throw new DuplicatedRegisterException();
		tipoUsuarioDao.salvarTipoUsuario(newInstance);
	}
	public void atualizarTipoUsuario(TipoUsuario transientObject) {
		tipoUsuarioDao.atualizarTipoUsuario(transientObject);
	}
	public TipoUsuario carregarTipoUsuario(long id) {
		return tipoUsuarioDao.carregarTipoUsuario(id);
	}
	public List<TipoUsuario> listarTipoUsuarios() {
		return tipoUsuarioDao.listarTipoUsuarios();
	}
	public void removerTipoUsuario(TipoUsuario persistentObject) {
		List<Usuario> lista = listarUsuarios(persistentObject);
		if(lista.size() > 0) {
			throw new RuntimeException("Erro ao remover Tipo de Usuário");
		}
		tipoUsuarioDao.removerTipoUsuario(persistentObject);
	}

	//Unidade---------------------------------------
	public void salvarUnidade(Unidade newInstance) {
		//Fornecedor forn = fornecedorDao.localizarFornecedorPorCodigo(newInstance.getCodigo());
		Unidade un = unidadeDao.localizarUnidadePorCodigo(newInstance.getCodigo());
		if(un != null) {// || forn != null) {
			throw new DuplicatedRegisterException();
		}
		unidadeDao.salvarUnidade(newInstance);
	}
	public void atualizarUnidade(Unidade transientObject) {
		unidadeDao.atualizarUnidade(transientObject);
	}
	public Unidade carregarUnidade(long id) {
		return unidadeDao.carregarUnidade(id);
	}
	public List<Unidade> listarUnidade() {
		return unidadeDao.listarUnidade();
	}
	public void removerUnidade(Unidade persistentObject) {
		boolean existeDependencia = false;
		for(Usuario u : usuarioDao.listarUsuarios()) {
			if(u.getUnidade().getId() == persistentObject.getId()) {
				existeDependencia = true;
				break;
			}
		}
		
		for(Equipamento eq:equipamentoDao.listarEquipamento()) {
			if(eq.getUnidade().getId() == persistentObject.getId()) {
				existeDependencia = true;
				break;
			}
		}
		if(existeDependencia)
			throw new RuntimeException("Erro ao remover unidade!");		
		unidadeDao.removerUnidade(persistentObject);
	}
	
	//Recibo---------------------------------------
		public void salvarRecibo(Recibo newInstance) {
			reciboDao.salvarRecibo(newInstance);
		}
		public void atualizarRecibo(Recibo transientObject) {
			reciboDao.atualizarRecibo(transientObject);
		}
		public Recibo carregarRecibo(long id) {
			return reciboDao.carregarRecibo(id);
		}
		public List<Recibo> listarRecibo() {
			return reciboDao.listarRecibo();
		}
		public void cancelarRecibo(Recibo persistentObject) {
			reciboDao.cancelarRecibo(persistentObject);
		}
		public Recibo localizarReciboPorDespesa(Despesa despesa) {
			return reciboDao.localizarReciboPorDespesa(despesa);
		}
		public Recibo localizarReciboPorReceita(Receita receita) {
			return reciboDao.localizarReciboPorReceita(receita);
		}
		
		//Contrato---------------------------------------
				public void salvarContrato(Contrato newInstance) {
					contratoDao.salvarContrato(newInstance);
				}
				public void atualizarContrato(Contrato transientObject) {
					contratoDao.atualizarContrato(transientObject);
				}
				public Contrato carregarContrato(long id) {
					return contratoDao.carregarContrato(id);
				}
				public List<Contrato> listarContrato(int campo, String texto) {
					return contratoDao.listarContrato(campo,texto);
				}
				public List<Contrato> listarContrato(int campo, String texto,Funcionario f) {
					return contratoDao.listarContrato(campo,texto,f);
				}
				public void removerContrato(Contrato persistentObject)  {
					contratoDao.removerContrato(persistentObject);
				}

	//Usu�rio ----------------------------------------
	public void salvarUsuario(Usuario newInstance) {
		if(usuarioDao.localizarUsuarioPorLogin(newInstance.getLogin()) != null)
			throw new DuplicatedRegisterException();
		usuarioDao.salvarUsuario(newInstance);
	}
	public void atualizarUsuario(Usuario transientObject) {
		usuarioDao.atualizarUsuario(transientObject);
	}
	public Usuario carregarUsuario(long id) {
		return usuarioDao.carregarUsuario(id);
	}
	
	
	public List<Usuario> listarUsuarios() {
		return usuarioDao.listarUsuarios();
	}
	
	
	
	
	public List<Usuario> listarUsuarios(Funcionario func)  {
		return usuarioDao.listarUsuarios(func);
	}


	public List<Usuario> listarUsuarios(TipoUsuario tipo)  {
		return usuarioDao.listarUsuarios(tipo);
	}


	public void removerUsuario(Usuario persistentObject) {
		usuarioDao.removerUsuario(persistentObject);
	}

	//Grupo-----------------------------
	public void atualizarGrupo(Grupo u) {
		grupoDao.atualizarGrupo(u);
	}
	public Grupo carregarGrupo(long id) {
		return grupoDao.carregarGrupo(id);
	}
	public List<Grupo> listarGrupos() {
		return grupoDao.listarGrupos();
	}
	public void removerGrupo(Grupo u) {
		List<Recurso> subgrupos = listarRecurso(u);
		if(subgrupos.size() > 0) {
			throw new RuntimeException("Não é possível remover o Grupo selecionado, pois existem Subgrupos cadastrados no mesmo!");
		}
		grupoDao.removerGrupo(u);
	}
	public void salvarGrupo(Grupo u) {
		Grupo g = grupoDao.localizarGrupoPorCodigo(u.getCodigo());
		Grupo g2 = grupoDao.localizarGrupoPorNome(u.getNome());
		if(g != null || g2 != null)
			throw new DuplicatedRegisterException();
		grupoDao.salvarGrupo(u);
	}
	public Grupo localizarGrupoPorCodigo(String codigo)  {
		return grupoDao.localizarGrupoPorCodigo(codigo);
	}
	public Grupo localizarGrupoPorNome(String nome)  {
		return grupoDao.localizarGrupoPorNome(nome);
	}

	//Or�amento ------------------------------------
	public OrcamentoDaoInterface getOrcamentoDao() {
		return orcamentoDao;
	}
	public void setOrcamentoDao(OrcamentoDaoInterface orcamentoDao) {
		this.orcamentoDao = orcamentoDao;
	}
	public void atualizarOrcamento(Orcamento u) {
		orcamentoDao.atualizarOrcamento(u);
	}
	public Orcamento carregarOrcamento(long id) {
		return orcamentoDao.carregarOrcamento(id);
	}
	public List<Orcamento> listarOrcamentos(int campo, String texto) {
		return orcamentoDao.listarOrcamentos(campo,texto);
	}
	
	public List<Orcamento> listarOrcamentos(long idPai) {
		return orcamentoDao.listarOrcamentos(idPai);
	}

	public List<Orcamento> listarOrcamentos(int campo, String texto,Funcionario f)  {
		return orcamentoDao.listarOrcamentos(campo,texto,f);
	}
	
	public void removerOrcamento(Orcamento u) {
		orcamentoDao.removerOrcamento(u);
	}
	public void salvarOrcamento(Orcamento u) {
		orcamentoDao.salvarOrcamento(u);
	}
	
	public List<Orcamento> listarOrcamentos(Date inicio, Date fim)
			 {
		return orcamentoDao.listarOrcamentos(inicio, fim);
	}
	
	public List<Orcamento> listarOrcamentos(Date inicio, Date fim, Funcionario f)
			 {
		return orcamentoDao.listarOrcamentos(inicio, fim,f);
	}

	//CATEGORIA  ----------------------------------
	public void atualizarCategoria(Categoria transientObject)
	 {
		categoriaDao.atualizarCategoria(transientObject);
	}
	public Categoria carregarCategoria(long id)  {
		return categoriaDao.carregarCategoria(id);
	}
	public List<Categoria> listarCategorias()  {
		return categoriaDao.listarCategorias();
	}
	public void removerCategoria(Categoria persistentObject)
	 {
		categoriaDao.removerCategoria(persistentObject);
	}
	public void salvarCategoria(Categoria newInstance)  {
		categoriaDao.salvarCategoria(newInstance);
	}


	//Descarte de Equipamento----------------------------------






	//OsDePassagem
	public void atualizarOsDePassagem(OsDePassagem o)  {
		osDePassagemDao.atualizarOsDePassagem(o);
	}
	public void atualizarDescarteEquipamento(DescarteEquipamento transientObject)
	 {
		descarteDao.atualizarDescarteEquipamento(transientObject);
	}


	public DescarteEquipamento carregarDescarteEquipamento(long id)
	 {
		return descarteDao.carregarDescarteEquipamento(id);
	}


	public List<DescarteEquipamento> listarDescarteEquipamentos()
	 {
		return descarteDao.listarDescarteEquipamentos();
	}


	public void removerDescarteEquipamento(DescarteEquipamento persistentObject)
	 {
		descarteDao.removerDescarteEquipamento(persistentObject);
	}

	public void salvarDescarteEquipamento(DescarteEquipamento newInstance)
	 {
		descarteDao.salvarDescarteEquipamento(newInstance);
	}


	public OsDePassagem carregarOsDePassagem(long id)  {
		return osDePassagemDao.carregarOsDePassagem(id);
	}
	public List<OsDePassagem> listarOsDePassagem()  {
		return osDePassagemDao.listarOsDePassagem();
	}
	public List<OsDePassagem> listarOsDePassagem(Funcionario f)
	 {
		return osDePassagemDao.listarOsDePassagem(f);
	}
	public void removerOsDePassagem(OsDePassagem persistentObject)
	 {
		osDePassagemDao.removerOsDePassagem(persistentObject);
	}
	public void salvarOsDePassagem(OsDePassagem newInstance)
	 {
		osDePassagemDao.salvarOsDePassagem(newInstance);
	}

	//Contagem de Estoque -------------------------------
	public void atualizarContagem(ContagemEstoque c){
		contagemDao.atualizarContagem(c);
	}

	public ContagemEstoque carregarContagem(long id) {
		return contagemDao.carregarContagem(id);
	}

	public List<ContagemEstoque> listarContagens()  {
		return contagemDao.listarContagens();
	}

	public void removerContagem(ContagemEstoque persistentObject) 
	 {
		contagemDao.removerContagem(persistentObject);
	}

	public void salvarContagem(ContagemEstoque newInstance) 
	 {
		contagemDao.salvarContagem(newInstance);
	}



	//GETTERS e SETTERS----------------------------------
	public void setAmbienteDao(AmbienteDaoInterface Dao) {
		this.ambienteDao = Dao;
	}
	
	public void setSalaLocalDao(SalaLocalDaoInterface Dao) {
		this.salaLocalDao = Dao;
	}

	public void atualizarConta(Conta transientObject)  {
		contaDao.atualizarConta(transientObject);
	}


	public Conta carregarConta(long id)  {
		return contaDao.carregarConta(id);
	}


	public List<Conta> listarContas()  {
		return contaDao.listarContas();
	}

	public void removerConta(Conta persistentObject)  {
		contaDao.removerConta(persistentObject);
	}
	public void salvarConta(Conta newInstance)  {
		contaDao.salvarConta(newInstance);
	}


	
	
	
	
	
	
	
	
	
	
	
	public void atualizarManutencaoPreventiva(
			ManutencaoPreventiva transientObject)  {
		manutencaoPreventivaDao.atualizarManutencaoPreventiva(transientObject);
	}


	public ManutencaoPreventiva carregarManutencaoPreventiva(long id)
			 {
		return manutencaoPreventivaDao.carregarManutencaoPreventiva(id);
	}


	public List<ManutencaoPreventiva> listarManutencaoPreventivas()
			 {
		return manutencaoPreventivaDao.listarManutencaoPreventivas();
	}


	public List<ManutencaoPreventiva> listarManutencaoPreventivasExcluidos()
			 {
		return manutencaoPreventivaDao.listarManutencaoPreventivasExcluidos();
	}


	public ManutencaoPreventiva localizarManutencaoPreventivaPorEquipamento(
			Equipamento eq)  {
		return manutencaoPreventivaDao
				.localizarManutencaoPreventivaPorEquipamento(eq);
	}


	public List<ManutencaoPreventiva> localizarManutencaoPreventivaPorTecnico(
			Funcionario tec)  {
		return manutencaoPreventivaDao
				.localizarManutencaoPreventivaPorTecnico(tec);
	}


	public void removerManutencaoPreventiva(
			ManutencaoPreventiva persistentObject)  {
		manutencaoPreventivaDao.removerManutencaoPreventiva(persistentObject);
	}


	public void restaurarManutencaoPreventiva(
			ManutencaoPreventiva persistentObject)  {
		manutencaoPreventivaDao.restaurarManutencaoPreventiva(persistentObject);
	}


	public void salvarManutencaoPreventiva(ManutencaoPreventiva newInstance)
			 {
		manutencaoPreventivaDao.salvarManutencaoPreventiva(newInstance);
	}


	public void atualizarManutencaoCorretiva(ManutencaoCorretiva transientObject)
			 {
		manutencaoCorretivaDao.atualizarManutencaoCorretiva(transientObject);
	}


	public ManutencaoCorretiva carregarManutencaoCorretiva(long id)
			 {
		return manutencaoCorretivaDao.carregarManutencaoCorretiva(id);
	}


	public List<ManutencaoCorretiva> listarManutencaoCorretiva()
			 {
		return manutencaoCorretivaDao.listarManutencaoCorretiva();
	}


	public List<ManutencaoCorretiva> listarManutencaoCorretivaExcluidos()
			 {
		return manutencaoCorretivaDao.listarManutencaoCorretivaExcluidos();
	}


	public ManutencaoCorretiva localizarManutencaoCorretivaPorEquipamento(
			Equipamento eq)  {
		return manutencaoCorretivaDao
				.localizarManutencaoCorretivaPorEquipamento(eq);
	}


	public List<ManutencaoCorretiva> localizarManutencaoCorretivaPorAssistencia(
			String ass)  {
		return manutencaoCorretivaDao
				.localizarManutencaoCorretivaPorAssistencia(ass);
	}


	public void removerManutencaoCorretiva(ManutencaoCorretiva persistentObject)
			 {
		manutencaoCorretivaDao.removerManutencaoCorretiva(persistentObject);
	}


	public void restaurarManutencaoCorretiva(
			ManutencaoCorretiva persistentObject)  {
		manutencaoCorretivaDao.restaurarManutencaoCorretiva(persistentObject);
	}


	public void salvarManutencaoCorretiva(ManutencaoCorretiva newInstance)
			 {
		manutencaoCorretivaDao.salvarManutencaoCorretiva(newInstance);
	}


	public void atualizarCentroCusto(CentroCusto transientObject)
	 {
		centroCustoDao.atualizarCentroCusto(transientObject);
	}


	public CentroCusto carregarCentroCusto(long id)  {
		return centroCustoDao.carregarCentroCusto(id);
	}


	public List<CentroCusto> listarCentroCustos()  {
		return centroCustoDao.listarCentroCustos();
	}


	public void removerCentroCusto(CentroCusto persistentObject)
	 {
		centroCustoDao.removerCentroCusto(persistentObject);
	}
	public void salvarCentroCusto(CentroCusto newInstance)  {
		CentroCusto cc = centroCustoDao.localizarCentroCustoPorNome(newInstance.getNome());
		if(cc != null)
			throw new DuplicatedRegisterException();
		centroCustoDao.salvarCentroCusto(newInstance);
	}


	public CentroCusto localizarCentroCustoPorNome(String nome)
	 {
		return centroCustoDao.localizarCentroCustoPorNome(nome);
	}


	public AmbienteDaoInterface getAmbienteDao() {
		return this.ambienteDao;
	}
	public SalaLocalDaoInterface getSalaLocalDao() {
		return this.salaLocalDao;
	}
	public void setClienteDao(ClienteDaoInterface Dao) {
		this.clienteDao = Dao;
	}
	public ClienteDaoInterface getClienteDao() {
		return this.clienteDao;
	}	
	public void setDadosBancariosDao(DadosBancariosDaoInterface Dao) {
		this.dadosBancariosDao = Dao;
	}
	public DadosBancariosDaoInterface getDadosBancariosDao() {
		return this.dadosBancariosDao;
	}	
	public void setDescricaoEquipamentoDao(DescricaoEquipamentoDaoInterface Dao) {
		this.descricaoEquipamentoDao = Dao;
	}
	public DescricaoEquipamentoDaoInterface getDescricaoEquipamentoDao() {
		return this.descricaoEquipamentoDao;
	}	
	public void setEquipamentoDao(EquipamentoDaoInterface Dao) {
		this.equipamentoDao = Dao;
	}
	public EquipamentoDaoInterface getEquipamentoDao() {
		return this.equipamentoDao;
	}
	public void setEquipamentoSublocadoDao(EquipamentoSublocadoDaoInterface Dao) {
		this.equipamentoSublocadoDao = Dao;
	}
	public EquipamentoSublocadoDaoInterface getEquipamentoSublocadoDao() {
		return this.equipamentoSublocadoDao;
	}
	public void setFornecedorDao(FornecedorDaoInterface Dao) {
		this.fornecedorDao = Dao;
	}
	public FornecedorDaoInterface getFornecedorDao() {
		return this.fornecedorDao;
	}	
	public void setFornecedorTerceirizadoDao(FornecedorTerceirizadoDaoInterface Dao) {
		this.fornecedorTerceirizadoDao = Dao;
	}
	public FornecedorTerceirizadoDaoInterface getFornecedorTerceirizadoDao() {
		return this.fornecedorTerceirizadoDao;
	}	
	public void setFreelancerDao(FreelancerDaoInterface Dao) {
		this.freelancerDao = Dao;
	}
	public FreelancerDaoInterface getFreelancerDao() {
		return this.freelancerDao;
	}	
	public void setFuncaoDao(FuncaoDaoInterface Dao) {
		this.funcaoDao = Dao;
	}
	public FuncaoDaoInterface getFuncaoDao() {
		return this.funcaoDao;
	}
	public void setFuncionarioDao(FuncionarioDaoInterface Dao) {
		this.funcionarioDao = Dao;
	}
	public FuncionarioDaoInterface getFuncionarioDao() {
		return this.funcionarioDao;
	}
	public void setLocalDao(LocalDaoInterface Dao) {
		this.localDao = Dao;
	}
	public LocalDaoInterface getLocalDao() {
		return this.localDao;
	}		
	public void setOrdemServicoDao(OrdemServicoDaoInterface Dao) {
		this.ordemServicoDao = Dao;
	}
	public OrdemServicoDaoInterface getOrdemServicoDao() {
		return this.ordemServicoDao;
	}
	public void setRecursoDao(RecursoDaoInterface Dao) {
		this.recursoDao = Dao;
	}
	public RecursoDaoInterface getRecursoDao() {
		return this.recursoDao;
	}	
	public void setRecursoTerceirizadoDao(RecursoTerceirizadoDaoInterface Dao) {
		this.recursoTerceirizadoDao = Dao;
	}
	public RecursoTerceirizadoDaoInterface getRecursoTerceirizadoDao() {
		return this.recursoTerceirizadoDao;
	}	
	public void setTipoUsuarioDao(TipoUsuarioDaoInterface Dao) {
		this.tipoUsuarioDao = Dao;
	}
	public TipoUsuarioDaoInterface getTipoUsuarioDao() {
		return this.tipoUsuarioDao;
	}	
	public void setUnidadeDao(UnidadeDaoInterface Dao) {
		this.unidadeDao = Dao;
	}
	public UnidadeDaoInterface getUnidadeDao() {
		return this.unidadeDao;
	}
	public void setReciboDao(ReciboDaoInterface Dao) {
		this.reciboDao = Dao;
	}
	public ReciboDaoInterface getReciboDao() {
		return this.reciboDao;
	}
	public void setContratoDao(ContratoDaoInterface Dao) {
		this.contratoDao = Dao;
	}
	public ContratoDaoInterface getContratoDao() {
		return this.contratoDao;
	}
	public void setUsuarioDao(UsuarioDaoInterface Dao) {
		this.usuarioDao = Dao;
	}
	public UsuarioDaoInterface getUsuarioDao() {
		return this.usuarioDao;
	}	
	public GrupoDaoInterface getGrupoDao() {
		return grupoDao;
	}
	public void setGrupoDao(GrupoDaoInterface grupoDao) {
		this.grupoDao = grupoDao;
	}

	public OsDePassagemDaoInterface getOsDePassagemDao() {
		return osDePassagemDao;
	}

	public void setOsDePassagemDao(OsDePassagemDaoInterface osDePassagem) {
		this.osDePassagemDao = osDePassagem;
	}

	public DescarteEquipamentoDaoInterface getDescarteDao() {
		return descarteDao;
	}


	public void setDescarteDao(DescarteEquipamentoDaoInterface descarteDao) {
		this.descarteDao = descarteDao;
	}


	public static void setInstance(Facade instance) {
		Facade.instance = instance;
	}


	public CategoriaDaoInterface getCategoriaDao() {
		return categoriaDao;
	}
	public void setCategoriaDao(CategoriaDaoInterface categoriaDao) {
		this.categoriaDao = categoriaDao;
	}
	public ContaDaoInterface getContaDao() {
		return contaDao;
	}
	public void setContaDao(ContaDaoInterface contaDao) {
		this.contaDao = contaDao;
	}
	public void setContagemDao(ContagemDaoInterface contagemDao){
		this.contagemDao = contagemDao;
	}
	public ContagemDaoInterface getContagemDao(){
		return this.contagemDao;
	}


	public void setTelaPrincipal(InterfaceTelaPrincipal telaPrincipal) {
		this.telaPrincipal = telaPrincipal;
	}


	public InterfaceTelaPrincipal getTelaPrincipal() {
		return telaPrincipal;
	}


	public ReceitaDaoInterface getReceitaDao() {
		return receitaDao;
	}


	public void setReceitaDao(ReceitaDaoInterface receitaDao) {
		this.receitaDao = receitaDao;
	}


	public DespesaDaoInterface getDespesaDao() {
		return despesaDao;
	}
	
	public FontePagadoraDaoInterface getFontePagadoraDao() {
		return fontePagadoraDao;
	}


	public void setDespesaDao(DespesaDaoInterface despesaDao) {
		this.despesaDao = despesaDao;
	}
	
	public void setFontePagadoraDao(FontePagadoraDaoInterface fontePagadoraDao) {
		this.fontePagadoraDao = fontePagadoraDao;
	}


	public void atualizarReceita(Receita transientObject)  {
		receitaDao.atualizarReceita(transientObject);
	}


	public Receita carregarReceita(long id)  {
		return receitaDao.carregarReceita(id);
	}


	public List<Receita> listarReceitas(int campo, String texto)  {
		return receitaDao.listarReceitas(campo,texto);
	}

	public List<Receita> listarReceitasPorOS(OrdemServico os)  {
		return receitaDao.listarReceitasPorOS(os);
	}

	public void removerReceita(Receita persistentObject)  {
		receitaDao.removerReceita(persistentObject);
	}

	public void salvarReceita(Receita newInstance)  {
		receitaDao.salvarReceita(newInstance);
	}

	//DESPESA
	public void atualizarDespesa(Despesa transientObject)  {
		despesaDao.atualizarDespesa(transientObject);
	}


	public Despesa buscarDespesaComissao(OrdemServico os) {
		return despesaDao.buscarDespesaComissao(os);
	}

	
	public Despesa carregarDespesa(long id)  {
		return despesaDao.carregarDespesa(id);
	}


	public List<Despesa> listarDespesas(int campo, String texto)  {
		return despesaDao.listarDespesas(campo,texto);
	}

	public List<Despesa> listarDespesas(OrdemServico os)  {
		return despesaDao.listarDespesas(os);
	}

	public List<Despesa> listarDespesasAbertas(Date inicio, Date fim)
			 {
		return despesaDao.listarDespesasAbertas(inicio, fim);
	}

	public void removerDespesa(Despesa persistentObject)  {
		despesaDao.removerDespesa(persistentObject);
	}

	public void salvarDespesa(Despesa newInstance)  {
		despesaDao.salvarDespesa(newInstance);
	}

	//FONTE PAGADORA
	public void atualizarFontePagadora(FontePagadora transientObject)  {
		fontePagadoraDao.atualizarFontePagadora(transientObject);
	}


	public FontePagadora carregarFontePagadora(long id)  {
		return fontePagadoraDao.carregarFontePagadora(id);
	}


	public List<FontePagadora> listarFontePagadoras(int campo, String texto)  {
		return fontePagadoraDao.listarFontePagadoras(campo,texto);
	}
	
	public List<FontePagadora> listarFontePagadoras()  {
		return fontePagadoraDao.listarFontePagadoras();
	}

	public void removerFontePagadora(FontePagadora persistentObject)  {
		fontePagadoraDao.removerFontePagadora(persistentObject);
	}

	public void salvarFontePagadora(FontePagadora newInstance)  {
		fontePagadoraDao.salvarFontePagadora(newInstance);
	}
	/////////////////////

	public void restaurarAmbiente(Ambiente persistentObject)
	 {
		ambienteDao.restaurarAmbiente(persistentObject);
	}
	
	public void restaurarSalaLocal(SalaLocal persistentObject)
			 {
				salaLocalDao.restaurarSalaLocal(persistentObject);
	}


	public void restaurarCliente(Cliente persistentObject)  {
		clienteDao.restaurarCliente(persistentObject);
	}

	public void restaurarDadosBancarios(DadosBancarios persistentObject)  {
		dadosBancariosDao.restaurarDadosBancarios(persistentObject);
	}

	public void restaurarDescricaoEquipamento(
			DescricaoEquipamento persistentObject)  {
		descricaoEquipamentoDao.restaurarDescricaoEquipamento(persistentObject);
	}


	public void restaurarEquipamento(Equipamento persistentObject)
	 {
		if(persistentObject.getDescricaoEquipamento().isDeletado()) {
			restaurarRecurso(persistentObject.getDescricaoEquipamento());
		}
		equipamentoDao.restaurarEquipamento(persistentObject);
	}


	public void restaurarEquipamentoSublocado(
			EquipamentoSublocado persistentObject)  {
		equipamentoSublocadoDao.restaurarEquipamentoSublocado(persistentObject);
	}


	public void restaurarFornecedor(Fornecedor persistentObject)
	 {
		fornecedorDao.restaurarFornecedor(persistentObject);
	}
	
	public void restaurarFornecedorTerceirizado(FornecedorTerceirizado persistentObject)
			 {
				fornecedorTerceirizadoDao.restaurarFornecedorTerceirizado(persistentObject);
			}


	public void restaurarFuncao(Funcao persistentObject)  {
		funcaoDao.restaurarFuncao(persistentObject);
	}


	public void restaurarFuncionario(Funcionario persistentObject)
	 {
		funcionarioDao.restaurarFuncionario(persistentObject);
	}


	public void restaurarLocal(Local persistentObject)  {
		localDao.restaurarLocal(persistentObject);
	}


	public void restaurarOrdemServico(OrdemServico persistentObject)
	 {
		ordemServicoDao.restaurarOrdemServico(persistentObject);
	}


	public void restaurarRecurso(Recurso persistentObject)  {
		if(persistentObject.getGrupo().isDeletado()) {
			restaurarGrupo(persistentObject.getGrupo());
		}
		recursoDao.restaurarRecurso(persistentObject);
	}


	public void restaurarTipoUsuario(TipoUsuario persistentObject)
	 {
		tipoUsuarioDao.restaurarTipoUsuario(persistentObject);
	}


	public void restaurarUnidade(Unidade persistentObject)  {
		unidadeDao.restaurarUnidade(persistentObject);
	}


	public void restaurarUsuario(Usuario persistentObject)  {
		usuarioDao.restaurarUsuario(persistentObject);
	}


	public void restaurarGrupo(Grupo persistentObject)  {
		grupoDao.restaurarGrupo(persistentObject);
	}


	public void restaurarOrcamento(Orcamento persistentObject)
	 {
		orcamentoDao.restaurarOrcamento(persistentObject);
	}


	public void restaurarOsDePassagem(OsDePassagem persistentObject)
	 {
		osDePassagemDao.restaurarOsDePassagem(persistentObject);
	}


	public void restaurarCategoria(Categoria persistentObject)
	 {
		categoriaDao.restaurarCategoria(persistentObject);
	}


	public void restaurarConta(Conta persistentObject)  {
		contaDao.restaurarConta(persistentObject);
	}


	public void restaurarContagem(ContagemEstoque persistentObject)
	 {
		contagemDao.restaurarContagem(persistentObject);
	}


	public void restaurarDescarteEquipamento(
			DescarteEquipamento persistentObject)  {
		descarteDao.restaurarDescarteEquipamento(persistentObject);
	}


	public void restaurarCentroCusto(CentroCusto persistentObject)
	 {
		centroCustoDao.restaurarCentroCusto(persistentObject);
	}


	public void restaurarReceita(Receita persistentObject)  {
		receitaDao.restaurarReceita(persistentObject);
	}


	public void restaurarDespesa(Despesa persistentObject)  {
		despesaDao.restaurarDespesa(persistentObject);
	}
	
	public void restaurarFontePagadora(FontePagadora persistentObject)  {
		fontePagadoraDao.restaurarFontePagadora(persistentObject);
	}


	public List<Cliente> listarClientesExcluidos()  {
		return clienteDao.listarClientesExcluidos();
	}
	
	public List<DadosBancarios> listarDadosBancariossExcluidos()  {
		return dadosBancariosDao.listarDadosBancariossExcluidos();
	}
	
	public List<Cliente> listarClientes(String cnpj, String razaoSocial,
			String contato) {
		return clienteDao.listarClientes(cnpj, razaoSocial, contato);
	}


	public List<Equipamento> listarEquipamentosExcluidos()  {
		return equipamentoDao.listarEquipamentosExcluidos();
	}


	public List<Fornecedor> listarFornecedoresExcluidos()  {
		return fornecedorDao.listarFornecedoresExcluidos();
	}
	
	public List<FornecedorTerceirizado> listarFornecedorTerceirizadosExcluidos()  {
		return fornecedorTerceirizadoDao.listarFornecedorTerceirizadosExcluidos();
	}


	public List<Freelancer> listarFreelancersExcluidos()  {
		return freelancerDao.listarFreelancersExcluidos();
	}


	public List<Funcao> listarFuncoesExcluidas()  {
		return funcaoDao.listarFuncoesExcluidas();
	}


	public List<Funcionario> listarFuncionariosExcluidos()  {
		return funcionarioDao.listarFuncionariosExcluidos();
	}


	public List<Local> listarLocaisExcluidos()  {
		return localDao.listarLocaisExcluidos();
	}


	public List<OrdemServico> listarOrdemServicosExcluidas()
	 {
		return ordemServicoDao.listarOrdemServicosExcluidas();
	}


	public List<Recurso> listarRecursosExcluidos()  {
		return recursoDao.listarRecursosExcluidos();
	}


	public List<TipoUsuario> listarTipoUsuariosExcluidos()  {
		return tipoUsuarioDao.listarTipoUsuariosExcluidos();
	}


	public List<Unidade> listarUnidadesExcluidas()  {
		return unidadeDao.listarUnidadesExcluidas();
	}


	public List<Usuario> listarUsuariosExcluidos()  {
		return usuarioDao.listarUsuariosExcluidos();
	}


	public List<Grupo> listarGruposExcluidos()  {
		return grupoDao.listarGruposExcluidos();
	}


	public List<Orcamento> listarOrcamentosExcluidos()  {
		return orcamentoDao.listarOrcamentosExcluidos();
	}


	public List<CentroCusto> listarCentroCustosExcluidos()  {
		return centroCustoDao.listarCentroCustosExcluidos();
	}


	public List<Receita> listarReceitasExcluidas()  {
		return receitaDao.listarReceitasExcluidas();
	}


	public List<Despesa> listarDespesasExcluidas()  {
		return despesaDao.listarDespesasExcluidas();
	}
	
	public List<FontePagadora> listarFontePagadorasExcluidas()  {
		return fontePagadoraDao.listarFontePagadorasExcluidas();
	}

	public void setDevolucaoSublocadosDao(DevolucaoSublocadosDaoInterface devolucaoSublocadosDao) {
		this.devolucaoSublocadosDao = devolucaoSublocadosDao;
	}

	public DevolucaoSublocadosDaoInterface getDevolucaoSublocadosDao() {
		return devolucaoSublocadosDao;
	}

	public void atualizarDevolucaoSublocados(DevolucaoSublocados transientObject)
			 {
		devolucaoSublocadosDao.atualizarDevolucaoSublocados(transientObject);
	}


	public DevolucaoSublocados carregarDevolucaoSublocados(long id)
			 {
		return devolucaoSublocadosDao.carregarDevolucaoSublocados(id);
	}


	public List<DevolucaoSublocados> listarDevolucaoSublocadoss()
			 {
		return devolucaoSublocadosDao.listarDevolucaoSublocadoss();
	}


	public List<DevolucaoSublocados> listarDevolucaoSublocadossExcluidos()
			 {
		return devolucaoSublocadosDao.listarDevolucaoSublocadossExcluidos();
	}


	public void removerDevolucaoSublocados(DevolucaoSublocados persistentObject)
			 {
		devolucaoSublocadosDao.removerDevolucaoSublocados(persistentObject);
	}


	public void restaurarDevolucaoSublocados(
			DevolucaoSublocados persistentObject)  {
		devolucaoSublocadosDao.restaurarDevolucaoSublocados(persistentObject);
	}


	public void salvarDevolucaoSublocados(DevolucaoSublocados newInstance)
			 {
		devolucaoSublocadosDao.salvarDevolucaoSublocados(newInstance);
	}


	public HistoricoCancelamento carregarHistoricoCancelamento(long id)
			 {
		return historicoCancelamento.carregarHistoricoCancelamento(id);
	}


	public List<HistoricoCancelamento> listarHistoricoCancelamentos()
			 {
		return historicoCancelamento.listarHistoricoCancelamentos();
	}


	public void salvarHistoricoCancelamento(HistoricoCancelamento newInstance)
			 {
		historicoCancelamento.salvarHistoricoCancelamento(newInstance);
	}


	public HistoricoAlteracaoOSInterface getHistoricoAlteracaoOSDao() {
		return historicoAlteracaoOSDao;
	}


	public void setHistoricoAlteracaoOSDao(
			HistoricoAlteracaoOSInterface historicoAlteracaoOSDao) {
		this.historicoAlteracaoOSDao = historicoAlteracaoOSDao;
	}


	public HistoricoAlteracaoOS carregarHistoricoAlteracaoOS(long id)
			 {
		return historicoAlteracaoOSDao.carregarHistoricoAlteracaoOS(id);
	}


	public List<HistoricoAlteracaoOS> listarHistoricoAlteracaoOSs()
			 {
		return historicoAlteracaoOSDao.listarHistoricoAlteracaoOSs();
	}


	public void salvarHistoricoAlteracaoOS(HistoricoAlteracaoOS newInstance)
			 {
		historicoAlteracaoOSDao.salvarHistoricoAlteracaoOS(newInstance);
	}

	public AlertaOSEmergencialDaoInterface getAlertaOSEmergencialDao() {
		return alertaOSEmergencialDao;
	}

	public void setAlertaOSEmergencialDao(
			AlertaOSEmergencialDaoInterface alertaOSEmergencialDao) {
		this.alertaOSEmergencialDao = alertaOSEmergencialDao;
	}

	public void atualizarAlertaOSEmergencial(AlertaOSEmergencial transientObject)
			 {
		alertaOSEmergencialDao.atualizarAlertaOSEmergencial(transientObject);
	}

	public AlertaOSEmergencial localizarAlertaPorEmergencial(
			OrdemServico emergencial)  {
		return alertaOSEmergencialDao
				.localizarAlertaPorEmergencial(emergencial);
	}

	public AlertaOSEmergencial carregarAlertaOSEmergencial(long id)
			 {
		return alertaOSEmergencialDao.carregarAlertaOSEmergencial(id);
	}

	public List<AlertaOSEmergencial> listarAlertaOSEmergencials()
			 {
		return alertaOSEmergencialDao.listarAlertaOSEmergencials();
	}

	public void salvarAlertaOSEmergencial(AlertaOSEmergencial newInstance)
			 {
		alertaOSEmergencialDao.salvarAlertaOSEmergencial(newInstance);
	}

	public RecolhimentoDaoInterface getRecolhimentoDao() {
		return recolhimentoDao;
	}

	public void setRecolhimentoDao(RecolhimentoDaoInterface recolhimentoDao) {
		this.recolhimentoDao = recolhimentoDao;
	}

	public void atualizarRecolhimento(Recolhimento transientObject)
			 {
		recolhimentoDao.atualizarRecolhimento(transientObject);
	}

	public Recolhimento carregarRecolhimento(long id)  {
		return recolhimentoDao.carregarRecolhimento(id);
	}

	public List<Recolhimento> listarRecolhimentos()  {
		return recolhimentoDao.listarRecolhimentos();
	}

	public List<Recolhimento> listarRecolhimentosExcluidos()
			 {
		return recolhimentoDao.listarRecolhimentosExcluidos();
	}

	public Recolhimento localizarRecolhimentoPorEquipamento(Equipamento eq)
			 {
		return recolhimentoDao.localizarRecolhimentoPorEquipamento(eq);
	}

	public List<Recolhimento> localizarRecolhimentoPorTecnico(Funcionario tec)
			 {
		return recolhimentoDao.localizarRecolhimentoPorTecnico(tec);
	}

	public void salvarRecolhimento(Recolhimento newInstance)
			 {
		recolhimentoDao.salvarRecolhimento(newInstance);
	}

	public ComodatoDaoInterface getComodatoDao() {
		return comodatoDao;
	}

	public void setComodatoDao(ComodatoDaoInterface comodatoDao) {
		this.comodatoDao = comodatoDao;
	}

	public void atualizarComodato(Comodato transientObject)  {
		comodatoDao.atualizarComodato(transientObject);
	}

	public Comodato carregarComodato(long id)  {
		return comodatoDao.carregarComodato(id);
	}

	public List<Comodato> listarComodatos()  {
		return comodatoDao.listarComodatos();
	}

	public void removerComodato(Comodato persistentObject)  {
		comodatoDao.removerComodato(persistentObject);
	}

	public void salvarComodato(Comodato newInstance)  {
		comodatoDao.salvarComodato(newInstance);
	}

	public List<Comodato> listarComodatosPorEquipamento(long equipamento)
			 {
		return comodatoDao.listarComodatosPorEquipamento(equipamento);
	}

	public Comodato localizaComodatosPorEquipamento(long equipamento)
			 {
		return comodatoDao.localizaComodatosPorEquipamento(equipamento);
	}

	public void restaurarComodato(Comodato persistentObject)
			 {
		comodatoDao.restaurarComodato(persistentObject);
	}

	public List<Comodato> listarComodatosExcluidas()  {
		return comodatoDao.listarComodatosExcluidas();
	}

	public List<Comodato> listarComodatosEnviarEquipamento()
			 {
		return comodatoDao.listarComodatosEnviarEquipamento();
	}

	public PautaDaoInterface getPautaDao() {
		return pautaDao;
	}

	public void setPautaDao(PautaDaoInterface pautaDao) {
		this.pautaDao = pautaDao;
	}

	public HistoricoPautaDaoInterface getHisPautaDao() {
		return hisPautaDao;
	}

	public void setHisPautaDao(HistoricoPautaDaoInterface hisPautaDao) {
		this.hisPautaDao = hisPautaDao;
	}

	public void atualizarGrupoPauta(GrupoPauta transientObject)  {
		grupoPautaDao.atualizarGrupoPauta(transientObject);
	}

	public GrupoPauta carregarGrupoPauta(long id)  {
		return grupoPautaDao.carregarGrupoPauta(id);
	}

	public List<GrupoPauta> listarGrupoPautas()  {
		return grupoPautaDao.listarGrupoPautas();
	}

	public List<GrupoPauta> listarGrupoPautas(Funcionario f)  {
		return grupoPautaDao.listarGrupoPautas(f);
	}

	public void salvarGrupoPauta(GrupoPauta newInstance)  {
		grupoPautaDao.salvarGrupoPauta(newInstance);
	}
	
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Object reload(Object selected) {
		if(selected instanceof Cliente)
			return clienteDao.carregarCliente(((Cliente) selected).getId());
		if(selected instanceof Grupo)
			return  grupoDao.carregarGrupo(((Grupo) selected).getId());
		if(selected instanceof Unidade)
			return   unidadeDao.carregarUnidade(((Unidade) selected).getId());
		if(selected instanceof Funcao)
			return   funcaoDao.carregarFuncao(((Funcao) selected).getId());
		if(selected instanceof Freelancer)
			return   freelancerDao.carregarFreelancer(((Freelancer) selected).getId());
		if(selected instanceof Funcionario)
			return   funcionarioDao.carregarFuncionario(((Funcionario) selected).getId());
		if(selected instanceof Usuario)
			return   usuarioDao.carregarUsuario(((Usuario) selected).getId());
		if(selected instanceof TipoUsuario)
			return   tipoUsuarioDao.carregarTipoUsuario(((TipoUsuario) selected).getId());
		if(selected instanceof Recurso)
			return   recursoDao.carregarRecurso(((Recurso) selected).getId());
		if(selected instanceof Equipamento)
			return   equipamentoDao.carregarEquipamento(((Equipamento) selected).getId());
		if(selected instanceof Cliente)
			return   clienteDao.carregarCliente(((Cliente) selected).getId());
		if(selected instanceof SalaLocal)
			return   salaLocalDao.carregarSalaLocal(((SalaLocal) selected).getId());
		if(selected instanceof Ambiente)
			return   ambienteDao.carregarAmbiente(((Ambiente) selected).getId());
		if(selected instanceof DadosBancarios)
			return   dadosBancariosDao.carregarDadosBancarios(((DadosBancarios) selected).getId());
		if(selected instanceof DescricaoEquipamento)
			return   descricaoEquipamentoDao.carregarDescricaoEquipamento(((DescricaoEquipamento) selected).getId());
		if(selected instanceof EquipamentoSublocado)
			return   equipamentoSublocadoDao.carregarEquipamentoSublocado(((EquipamentoSublocado) selected).getId());
		if(selected instanceof Fornecedor)
			return   fornecedorDao.carregarFornecedor(((Fornecedor) selected).getId());
		if(selected instanceof FornecedorTerceirizado)
			return   fornecedorTerceirizadoDao.carregarFornecedorTerceirizado(((FornecedorTerceirizado) selected).getId());

		if(selected instanceof Local)
			return   localDao.carregarLocal(((Local) selected).getId());
		if(selected instanceof OrdemServico)
			return   ordemServicoDao.carregarOrdemServico(((OrdemServico) selected).getId());
		if(selected instanceof Comodato)
			return   comodatoDao.carregarComodato(((Comodato) selected).getId());
		if(selected instanceof RecursoTerceirizado)
			return   recursoTerceirizadoDao.carregarRecursoTerceirizado(((RecursoTerceirizado) selected).getId());
		if(selected instanceof Pauta)
			return  pautaDao.carregarPauta(((Pauta) selected).getId());
		if(selected instanceof HistoricoPauta)
			return  hisPautaDao.carregarHistoricoPauta(((HistoricoPauta) selected).getId());
		if(selected instanceof Recibo)
			return   reciboDao.carregarRecibo(((Recibo) selected).getId());
		if(selected instanceof Contrato)
			return   contratoDao.carregarContrato(((Contrato) selected).getId());
		if(selected instanceof Orcamento)
			return  orcamentoDao.carregarOrcamento(((Orcamento) selected).getId());
		if(selected instanceof OsDePassagem)
			return  osDePassagemDao.carregarOsDePassagem(((OsDePassagem) selected).getId());
		if(selected instanceof Categoria)
			return  categoriaDao.carregarCategoria(((Categoria) selected).getId());
		if(selected instanceof Conta)
			return  contaDao.carregarConta(((Conta) selected).getId());
		if(selected instanceof ContagemEstoque)
			return  contagemDao.carregarContagem(((ContagemEstoque) selected).getId());
		if(selected instanceof DescarteEquipamento)
			return  descarteDao.carregarDescarteEquipamento(((DescarteEquipamento) selected).getId());
		if(selected instanceof CentroCusto)
			return  centroCustoDao.carregarCentroCusto(((CentroCusto) selected).getId());
		if(selected instanceof Receita)
			return  receitaDao.carregarReceita(((Receita) selected).getId());
		if(selected instanceof Despesa)
			return  despesaDao.carregarDespesa(((Despesa) selected).getId());
		if(selected instanceof FontePagadora)
			return  fontePagadoraDao.carregarFontePagadora(((FontePagadora) selected).getId());
		if(selected instanceof ManutencaoPreventiva)
			return  manutencaoPreventivaDao.carregarManutencaoPreventiva(((ManutencaoPreventiva) selected).getId());
		if(selected instanceof ManutencaoCorretiva)
			return  manutencaoCorretivaDao.carregarManutencaoCorretiva(((ManutencaoCorretiva) selected).getId());
		if(selected instanceof DevolucaoSublocados)
			return  devolucaoSublocadosDao.carregarDevolucaoSublocados(((DevolucaoSublocados) selected).getId());
		if(selected instanceof HistoricoCancelamento)
			return  historicoCancelamento.carregarHistoricoCancelamento(((HistoricoCancelamento) selected).getId());
		if(selected instanceof RegistroSublocacao)
			return  registroSublocacaoDao.carregarRegistroSublocacao(((RegistroSublocacao) selected).getId());
		if(selected instanceof HistoricoAlteracaoOS)
			return historicoAlteracaoOSDao.carregarHistoricoAlteracaoOS(((HistoricoAlteracaoOS) selected).getId());
		if(selected instanceof AlertaOSEmergencial)
			return  alertaOSEmergencialDao.carregarAlertaOSEmergencial(((AlertaOSEmergencial) selected).getId());
		if(selected instanceof Recolhimento)
			return  recolhimentoDao.carregarRecolhimento(((Recolhimento) selected).getId());
		if(selected instanceof GrupoPauta)
			return  grupoPautaDao.carregarGrupoPauta(((GrupoPauta) selected).getId());

		throw new RuntimeException("Reload not implemented " + selected.getClass());
	}
	
}
