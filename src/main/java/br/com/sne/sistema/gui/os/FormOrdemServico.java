package br.com.sne.sistema.gui.os;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.toedter.calendar.JDateChooser;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RecursoTerceirizado;
import br.com.sne.sistema.bean.RecursoTerceirizadoSolicitado;
import br.com.sne.sistema.bean.SalaLocal;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.SituacaoOrcamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.local.DialogSearchLocal;
import br.com.sne.sistema.gui.orcamento.AmbienteTableModel;
import br.com.sne.sistema.gui.orcamento.FormOrcamento;
import br.com.sne.sistema.gui.orcamento.Nota;
import br.com.sne.sistema.gui.orcamento.PanelSala;
import br.com.sne.sistema.gui.orcamento.PanelSalaTerceirizado;
import br.com.sne.sistema.gui.os.OrdemServicoTableModel;
import br.com.sne.sistema.gui.receita.ReceitaTableModel;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.DateCellRenderer;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorBounded;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorUnbounded;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.MoedaCellEditor;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DateChooserDialog;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;
import br.com.sne.sistema.gui.util.form.DialogSearchFornecedor;
import br.com.sne.sistema.gui.util.form.DialogSearchOrcamento2;
import br.com.sne.sistema.gui.util.form.DialogSearchRecurso;
import br.com.sne.sistema.gui.util.form.FormIntervalar;
import br.com.sne.sistema.gui.util.form.StringChooserDialog;
import br.com.sne.sistema.gui.util.form.UserChooserDialog;
import br.com.sne.sistema.gui.util.form.ZebraDecorator;

public class FormOrdemServico extends DefaultForm<OrdemServico, OrdemServicoTableModel> implements FormIntervalar, Nota {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	private Cliente cliente = null;
	private Recurso recurso = null;
	private RecursoTerceirizado recursoTerceirizado = null;
	List<Recurso> listaRecursos = null;
	List<RecursoTerceirizado> listaRecursosTerceirizados = null;

	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;
	private JTextField fieldClienteTelefone;
	private JTextField fieldClienteContato;
	private JTextField fieldClienteCelular;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldVendedorConjunto;
	
	private JTextField fieldNomeContatoEvento;
	private JTextField fieldTelefoneContatoEvento;
	
	private JTextField fieldNomeProposta;
	private JTextField fieldCargoProposta;
	private JTextField fieldTelefoneProposta;
	private JTextField fieldNomePropostaConjunta;
	private JTextField fieldCargoPropostaConjunta;
	private JTextField fieldTelefonePropostaConjunta;
	private JTextField fieldCondicoesPagamento;
	private JTextArea fieldObservacoesCliente;
	private JTextArea fieldObservacoesFinanceiras;
	private JComboBox fieldStatusOS;	

	private JTextField fieldNomeEvento;
	private JComboBox fieldFuncionario;
	private JDateChooser fieldDataInicio;
	private JDateChooser fieldDataFim;
	private JDateChooser fieldDataMontagem;
	
	private JSpinner fieldHoraInicio;
	private JSpinner fieldHoraFim;
	private JSpinner fieldHoraMontagem;
	
	private JTextArea fieldObservacoes;
	private JTextArea fieldObservacoesLocalEvento;
	private JTextArea fieldDetalhesEvento;

	private JComboBox fieldTerceirizadoNome;
	private JComboBox fieldTerceirizadoGrupo;
	private JTextField fieldTerceirizadoQuantidade;
	private JMoedaRealTextField fieldTerceirizadoPrecoEmpresa;
	private JMoedaRealTextField fieldTerceirizadoPrecoForn;

	private JDateChooser fieldTerceirizadoDataInicio;
	private JDateChooser fieldTerceirizadoDataFim;
	
	private JComboBox fieldLogisticaNome;
	private JComboBox fieldLogisticaGrupo;
	private JMoedaRealTextField fieldLogisticaPreco;
	private JTextField fieldLogisticaQuantidade;
	private JDateChooser fieldLogisticaDataInicio;
	private JDateChooser fieldLogisticaDataFim;
	
	private JComboBox fieldEquipamentoNome;
	private JComboBox fieldEquipeTecnicaNome;
	private JComboBox fieldCenografiaNome;

	private JComboBox fieldEquipamentoGrupo;
	private JComboBox fieldEquipeTecnicaGrupo;
	private JComboBox fieldCenografiaGrupo;

	private JMoedaRealTextField fieldEquipamentoPreco;
	private JMoedaRealTextField fieldEquipeTecnicaPreco;
	private JMoedaRealTextField fieldCenografiaPreco;

	private JMoedaRealTextField fieldCenografiaCusto;
	private JMoedaRealTextField fieldLogisticaCusto;
	private JMoedaRealTextField fieldEquipeTecnicaCusto;
	private JMoedaRealTextField fieldEquipamentoCusto;
	
	private JDateChooser fieldEquipamentoDataInicio;
	private JDateChooser fieldEquipeTecnicaDataInicio;
	private JDateChooser fieldCenografiaDataInicio;

	private JDateChooser fieldEquipamentoDataFim;
	private JDateChooser fieldEquipeTecnicaDataFim;
	private JDateChooser fieldCenografiaDataFim;

	private JTextField fieldEquipamentoQuantidade;
	private JTextField fieldEquipeTecnicaQuantidade;
	private JTextField fieldCenografiaQuantidade;
	
	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;

	private JComboBox fieldAmbientesEvento;

	private JTextField fieldLocalID;
	private JTextField fieldLocalNome;
	
	private JPopupMenu menuTabela;
	private JPopupMenu menuTabelaPagamento;

	private JTabbedPane tabDetalhes;
	private JTabbedPane tabAmbientesEquipamento;
	private JTabbedPane tabAmbientesEquipeTecnica;
	private JTabbedPane tabAmbientesCenografia;
	private JTabbedPane tabAmbientesLogistica;
	private JTabbedPane tabAmbientesTerceirizados;

	private JLabel fieldPrecoTotal;
	private JLabel fieldPrecoTerceirizado;
	private JLabel fieldPrecoSubtotal;
	private JLabel fieldDesconto;
	
	private JTextField fieldAmbienteNome;
	private JDateChooser fieldAmbienteDataInicio;
	private JDateChooser fieldAmbienteDataFim;
	private JTable tabelaAmbiente;
	private AmbienteTableModel modelAmbiente;
	private JButton botaoAdicionarAmbiente;
	
	private JButton addRecursoButton;
	private JButton addEquipeTecnicaButton;
	private JButton addCenografiaButton;	
	private JButton addLogisticaButton;
	private JButton addTerceirizadoButton;

	private HashMap<String, PanelSala> paineisEquipamento;
	private HashMap<String, PanelSala> paineisEquipeTecnica;
	private HashMap<String, PanelSala> paineisCenografia;
	private HashMap<String, PanelSala> paineisLogistica;
	private HashMap<String, PanelSalaTerceirizado> paineisTerceirizados;

	private JCheckBox fieldTerceirizadoEmp;
	private JCheckBox fieldTerceirizadoFor;
	
	private OrdemServico ordemServ = null;
	private Orcamento orcamento = null;
	private String planilha = null;

	Date horaInicial = new Date(1000*60*3*60);

	private ReceitaTableModel modelParcelas;

	private JTable tabelaParcelas;

	private JMoedaRealTextField fieldValorParcela;

	private JDateChooser fieldDataParcela;

	private JTextField fieldDescricaoParcela;

	private JButton botaoAdicionarParcela;

	private JCheckBox fieldEmpenho;

	private JIntField fieldQuantidadeParcela;

	private JDateChooser fieldDataPrimeiraParcela;

	private JCheckBox fieldEmpenhoParcelas;

	private JButton botaoGerarParcelas;

	private JCheckBox fieldConfirmacaoFaturamento;

	private JTextArea fieldObservacoesFaturamento;

	private JTabbedPane tabLiberacaoOS;

	private ArrayList<Receita> parcelasRemovidas;
	private JTextField fieldCenografiaDescricao;

	private JTextField fieldLogisticaDescricao;

	private JTextField fieldEquipeTecnicaDescricao;

	private JTextField fieldTerceirizadoDescricao;

	private JTextField fieldEquipamentoDescricao;

	private JLabel fieldPrecoCustoSubtotal;

	private JTextField fieldAmbientePFixacao;

	private JTextField fieldAmbientePeDireito;

	private JTextField fieldAmbienteLargura;

	private JTextField fieldAmbienteComprimento;

	private JTextField fieldAmbienteArea;

	protected Local local;

	private JComboBox fieldLogisticaDiaria;

	private JButton buttonSalvarPlanilha;
	private JButton buttonCarregarPlanilha;

	private JIntField fieldAgenciaID;

	private JTextField fieldAgenciaFornecedor;

	private Fornecedor agencia;

	private JIntField fieldPercentualComissao;

	private JRadioButton fieldValorIntegral;

	private JRadioButton fieldSemLogOp;

	private JDateChooser fieldDataVencimento;

	private JTextField fieldTotalComissao;

	private JButton botaoAgencia;
	
	private SwingController pdfController;

	private boolean disableDateEvent = false;
	
	public FormOrdemServico() {
		super(new OrdemServicoTableModel(), "/images/icon_ose_18.png", "Ordem de Serviço");
		clear();
	}
	
	public boolean save(OrdemServico current) {
		
		boolean s = false;
		try {
			NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
			formato.setMinimumFractionDigits(2);
			formato.setMaximumFractionDigits(2);
			
			if(current.getDataInicio().compareTo(new Date()) < 0){
				int resp = JOptionPane.showConfirmDialog(null,"A data de início da Ordem de Serviço é anterior à data atual.\nTem certeza que deseja cadastrar uma Ordem de Serviço com a data anterior?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
				if(resp != JOptionPane.OK_OPTION){
					return false;
				}
			}
				
			if(ordemServ != null) {
				current = new OrdemServico(current);
				loadInputFields(current);
				current.setStatus(StatusOS.PENDENTE);
				for(AmbienteEvento amb: current.getAmbientes()){
					amb.setId(0);
				}
				for(RecursoSolicitado recs: current.getRecursoSolicitado()) {
					recs.setId(0);
				}
				current.getLocal().setId(0);
			}
			
			current.setStatus(StatusOS.PENDENTE);
			boolean possuiDesconto = false;
			for(RecursoSolicitado rs:current.getRecursoSolicitado()){
				if(rs.getPrecoUnitario().compareTo(rs.getRecurso().getValorMinimo()) < 0){
					possuiDesconto = true;
					break;
				}
			}
			if(possuiDesconto && !Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.CADASTRAR_OS_DESCONTO_EQUIPAMENTO)){
				UserChooserDialog du = new UserChooserDialog("Autorização", "Autorizo o cadastro de Ordem de Serviço\ncom valores abaixo do mínimo.");
				Usuario autorizacao = du.showDialog(null);
				if(autorizacao == null || !autorizacao.getTipoUsuario().getPermissao().contains(permission.CADASTRAR_OS_DESCONTO_EQUIPAMENTO)) {
					JOptionPane.showMessageDialog(this, "Autorização inválida!\nCadastro não realizado", "ERRO", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			
			BigDecimal desconto = BigDecimal.ZERO;
			try {
				desconto = new BigDecimal(formato.parse((fieldDesconto.getText()).replace("R$ ", "")).doubleValue());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(desconto.compareTo(BigDecimal.ZERO) > 0 && !Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.CADASTRAR_OS_DESCONTO)) {
				UserChooserDialog du = new UserChooserDialog("Autorização", "Autorizo o cadastro de Ordem de Serviço com desconto.");
				Usuario autorizacao = du.showDialog(null);
				if(autorizacao == null || !autorizacao.getTipoUsuario().getPermissao().contains(permission.CADASTRAR_OS_DESCONTO)) {
					JOptionPane.showMessageDialog(this, "Autorização inválida!\nCadastro não realizado", "ERRO", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			current.setDataCadastro(new Date());
			
		//	List<RecursoSolicitado> lrs = current.getRecursoSolicitado();
		//	current.setRecursoSolicitado(new ArrayList<RecursoSolicitado>());
			
			//ordemServ.setObservacoesFaturamento(fieldObservacoesFaturamento.getText());
			//ordemServ.setConfirmacaoFaturamento(fieldConfirmacaoFaturamento.isSelected());
			current.setConfirmacaoFaturamento(false);
			current.setStatus(StatusOS.PENDENTE);
			
			if(current.getLocal().getLocal()!=null)
				if(current.getLocal().getLocal().getId() == 0)
					current.getLocal().setLocal(null);
			
			
			if(agencia != null) {
				current.setTotalAgencia(calcularComissao());
				current.setDadosAgencia(agencia);
				current.setPercentualAgencia(new Integer(fieldPercentualComissao.getText()));
				current.setVencimentoAgencia(fieldDataVencimento.getDate());
				current.setPrecoIntegralAgencia(fieldValorIntegral.isSelected());
			}
			
			
			
			Facade.getInstance().salvarOrdemServico(current);
			if(orcamento != null) {
				orcamento.setSituacao(SituacaoOrcamento.FECHADO);
				orcamento.setGerouOrdemServico(true);
				Facade.getInstance().atualizarOrcamento(orcamento);
			}
		//	current.setRecursoSolicitado(lrs);
		//	Facade.getInstance().atualizarOrdemServico(current);
			
			List<Receita> parcelas = modelParcelas.getListaReceita();
			for(Receita r: parcelas) {
				r.setOrdemServico(current);
				r.setId(null);
				//if(r.getId() == null) {
					Facade.getInstance().salvarReceita(r);
				//}
			}
			
			if(agencia != null) {
				Despesa receita = new Despesa();
				receita.setDataCadastro(new Date());
				receita.setDataVencimento(current.getVencimentoAgencia());
				receita.setDeletado(false);
				receita.setDescricao("PAGAMENTO DE BV EVENTO "+current.getNomeEvento()+" OS "+current.getId());
				receita.setEmpresa(Facade.getInstance().getUsuarioLogado().getUnidade());
				receita.setFornecedor(agencia);
				receita.setFuncionarioCadastro(Facade.getInstance().getUsuarioLogado().getFuncionario());
				receita.setOrdemServico(current);
				receita.setSituacao(false);
				receita.setComissao(true);
				receita.setValor(current.getTotalAgencia());
				receita.setCentroCusto(Facade.getInstance().carregarCentroCusto(33));
				Facade.getInstance().salvarDespesa(receita);
			}
			
			ordemServ = current;
			
			int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir a Ordem de Serviço ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
			if( resp == JOptionPane.OK_OPTION) {
				print(current);
				s = false;
			} else {
				s = true;
			}
			clear();
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Ordem de Serviço já se encontra cadastrada", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(OrdemServico current) {
		boolean s = true;
		if(current.getStatus() == StatusOS.ESTORNADA) {
			JOptionPane.showMessageDialog(this, "Não é possível modificar uma Ordem de Serviço estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(current.getStatus() != StatusOS.PENDENTE) {
			if(current.getStatus() == StatusOS.APROVADA && Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.ALTERAR_DATA_OS)) {
				OrdemServico os2 = Facade.getInstance().carregarOrdemServico(current.getId());
				if(os2.getPreco().compareTo(current.getPreco()) != 0){
					JOptionPane.showMessageDialog(this, "Não é possível modificar o VALOR de uma Ordem de Serviço aprovada." + os2.getPreco() + ", " + current.getPreco(), "ERRO", JOptionPane.ERROR_MESSAGE);
					return false;
				} 
			} else {
				JOptionPane.showMessageDialog(this, "Não é possível modificar uma Ordem de Serviço\nque já foi aprovada pelo financeiro.", "ERRO", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		

		Despesa comissaoAntiga = Facade.getInstance().buscarDespesaComissao(current);
		BigDecimal comissaoNova = calcularComissao();
		
		if(comissaoAntiga != null) {
			BigDecimal valorComissaoAntiga = comissaoAntiga.getValor();
			if(!valorComissaoAntiga.equals(comissaoNova) && comissaoAntiga.isSituacao()){
				JOptionPane.showMessageDialog(this, "Não é possível modificar o VALOR de uma Ordem de Serviço com comissão paga.", "ERRO", JOptionPane.ERROR_MESSAGE);
				return false;			
			}
		}
		
		List<Receita> parcelas = modelParcelas.getListaReceita();
		for(Receita r: parcelas) {
			if(r.getId() == null) {
				Facade.getInstance().salvarReceita(r);
			} else{
				Facade.getInstance().atualizarReceita(r);
			}
		}
		for(Receita r:parcelasRemovidas) {
			Facade.getInstance().removerReceita(r);
		}

		if(agencia != null) {
			current.setTotalAgencia(comissaoNova);
			current.setDadosAgencia(agencia);
			current.setPercentualAgencia(new Integer(fieldPercentualComissao.getText()));
			current.setVencimentoAgencia(fieldDataVencimento.getDate());
			current.setPrecoIntegralAgencia(fieldValorIntegral.isSelected());


			if(comissaoAntiga == null) {
				Despesa receita = new Despesa();
				receita.setDataCadastro(new Date());
				receita.setDataVencimento(current.getVencimentoAgencia());
				receita.setDeletado(false);
				receita.setDescricao("PAGAMENTO DE BV EVENTO "+current.getNomeEvento()+" OS "+current.getId());
				receita.setEmpresa(Facade.getInstance().getUsuarioLogado().getUnidade());
				receita.setCentroCusto(Facade.getInstance().carregarCentroCusto(33));
				receita.setFornecedor(agencia);
				receita.setFuncionarioCadastro(Facade.getInstance().getUsuarioLogado().getFuncionario());
				receita.setOrdemServico(current);
				receita.setSituacao(false);
				receita.setComissao(true);
				receita.setValor(current.getTotalAgencia());
				Facade.getInstance().salvarDespesa(receita);
			} else {
				comissaoAntiga.setDataCadastro(new Date());
				comissaoAntiga.setDataVencimento(current.getVencimentoAgencia());
				comissaoAntiga.setDeletado(false);
				comissaoAntiga.setDescricao("PAGAMENTO DE BV EVENTO "+current.getNomeEvento()+" OS "+current.getId());
				comissaoAntiga.setEmpresa(Facade.getInstance().getUsuarioLogado().getUnidade());
				comissaoAntiga.setFornecedor(agencia);
				comissaoAntiga.setFuncionarioCadastro(Facade.getInstance().getUsuarioLogado().getFuncionario());
				comissaoAntiga.setOrdemServico(current);
				comissaoAntiga.setSituacao(false);
				comissaoAntiga.setComissao(true);
				comissaoAntiga.setValor(current.getTotalAgencia());
				comissaoAntiga.setCentroCusto(Facade.getInstance().carregarCentroCusto(33));
				Facade.getInstance().atualizarDespesa(comissaoAntiga);

			}
		} else {
			//Apagar comissão Antiga - Tinha comissão, mas não tem mais
			if(comissaoAntiga != null) 
				Facade.getInstance().removerDespesa(comissaoAntiga);
		}
		
		BigDecimal totalEmpresa = new BigDecimal(0);
		BigDecimal totalForn = new BigDecimal(0);
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			totalEmpresa = totalEmpresa.add(pnt.calcularSubTotal());
		}
	
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			totalForn = totalForn.add(pnt.calcularSubTotalFornecedor());

		}
		current.setSubtotalTerceirizadoEmpresa(totalEmpresa);
		current.setSubtotalTerceirizadoForn(totalForn);

		if(current.getLocal().getLocal()!=null)
			if(current.getLocal().getLocal().getId() == 0)
				current.getLocal().setLocal(null);
		
		current.setStatus(StatusOS.PENDENTE);
		Facade.getInstance().atualizarOrdemServico(current);
		return s;
	}

	public boolean remove(OrdemServico current) {
		boolean test = false;
		try {
			if(current.getStatus() == StatusOS.PENDENTE || current.getStatus() == StatusOS.RECUSADA) {
				
				List<Receita> parcelas = Facade.getInstance().listarReceitasPorOS(current);
				for(Receita r:parcelas) {
					if(r.isSituacao() == true) {
						JOptionPane.showMessageDialog(this, "Erro ao remover a Ordem de Serviço. \n Não é possível remover a Ordem de Serviço, pois existem parcelas pagas.", "ERRO", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
				
				Despesa comissao = Facade.getInstance().buscarDespesaComissao(current);
				if(comissao != null && comissao.isSituacao() ==true) {
					JOptionPane.showMessageDialog(this, "Erro ao remover a Ordem de Serviço. \n Não é possível remover a Ordem de Serviço, pois o BV já foi pago.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return false;
				}
					
				if(comissao != null) 
					Facade.getInstance().removerDespesa(comissao);
				
				for(Receita r:parcelas) {
					Facade.getInstance().removerReceita(r);
				}
				
				Facade.getInstance().removerOrdemServico(current);
				test = true;
			} else {
				JOptionPane.showMessageDialog(this, "Erro ao remover a Ordem de Serviço. \n Não é possível remover a Ordem de Serviço que já foi aprovada ou iniciada. \n Tente primeiro estornar a Ordem de Serviço para removê-la em seguida", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Ordem de Serviço. Verifique se existem Recursos cadastrados nesta Ordem de Serviço antes de tentar removê-la.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void setVisible(boolean aFlag) {
		if (fieldFuncionario != null) {
			carregarFieldFuncionario();
			carregarRecursos();
			carregarTerceirizados();
			verificarPermissao();
			super.setVisible(aFlag);
			if(aFlag) {
				carregarFieldEquipamentoGrupo();
				carregarFieldEquipeTecnicaGrupo();
			}
		}
	}
	
	private void carregarFieldEquipeTecnicaGrupo() {
		fieldEquipeTecnicaGrupo.removeAllItems();
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		fieldEquipeTecnicaGrupo.addItem(" ");
		for(Grupo rec: lista) {
			if(rec.getTipoRecurso() == TipoRecurso.EQUIPE_TECNICA) {
				fieldEquipeTecnicaGrupo.addItem(rec);
			}
		}
		
		fieldEquipeTecnicaGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldEquipeTecnicaGrupo.getSelectedItem() != " " && fieldEquipeTecnicaGrupo.getSelectedItem() != null) {
	        		carregarFieldRecursoHumanoNome();
	        	}
	        }
	    });
	}
	
	private void carregarFieldRecursoHumanoNome() {
		fieldEquipeTecnicaNome.removeAllItems();
		fieldEquipeTecnicaNome.addItem(" ");
		for(Recurso rec: listaRecursos) {
			if(rec.getGrupo().getId() == ((Grupo)fieldEquipeTecnicaGrupo.getSelectedItem()).getId()) {
				fieldEquipeTecnicaNome.addItem(rec);
			}
			
		}
		
		fieldEquipeTecnicaNome.setSelectedItem(" ");
		
		fieldEquipeTecnicaNome.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldEquipeTecnicaNome.getSelectedItem() != " " && fieldEquipeTecnicaNome.getSelectedItem() != null) {
		        	Recurso r = (Recurso) fieldEquipeTecnicaNome.getSelectedItem();
		        	fieldEquipeTecnicaPreco.setValor(r.getPrecoSugerido());
					fieldEquipeTecnicaCusto.setValor(r.getPrecoCusto());

		        	fieldEquipeTecnicaDescricao.setText(r.getNome());
		        	recurso = r;
					addEquipeTecnicaButton.requestFocus();
	        	}
	        }
	    });
	}
	
	private void carregarFieldEquipamentoGrupo() {
		fieldEquipamentoGrupo.removeAllItems();
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		fieldEquipamentoGrupo.addItem(" ");
		for(Grupo rec: lista) {
			if(rec.getTipoRecurso() == TipoRecurso.EQUIPAMENTO) {
				fieldEquipamentoGrupo.addItem(rec);
			}
		}
		
		fieldEquipamentoGrupo.setSelectedItem(" ");
		
		fieldEquipamentoGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldEquipamentoGrupo.getSelectedItem() != " " && fieldEquipamentoGrupo.getSelectedItem() != null) {
	        		carregarFieldRecursoNome();
	        	}
	        }
	    });
	}
	
	private void carregarFieldRecursoNome() {
		fieldEquipamentoNome.removeAllItems();
		fieldEquipamentoNome.addItem(" ");
		for(Recurso rec: listaRecursos) {
			if(rec.getGrupo().getId() == ((Grupo)fieldEquipamentoGrupo.getSelectedItem()).getId()) {
				fieldEquipamentoNome.addItem(rec);
			}
			
		}
		
		fieldEquipamentoNome.setSelectedItem(" ");
		
		fieldEquipamentoNome.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldEquipamentoNome.getSelectedItem() != " " && fieldEquipamentoNome.getSelectedItem() != null) {
		        	Recurso r = (Recurso) fieldEquipamentoNome.getSelectedItem();
		        	fieldEquipamentoPreco.setValor(r.getPrecoSugerido());
					fieldEquipamentoCusto.setValor(r.getPrecoCusto());

		        	fieldEquipamentoDescricao.setText(r.getNome());
		        	recurso = r;
					addRecursoButton.requestFocus();
	        	}
	        }
	    });
	}
	
	private void carregarRecursos() {
		List<Recurso> lista = Facade.getInstance().listarRecursos();
		listaRecursos = lista;
	}
	
	private void carregarTerceirizados() {
		List<RecursoTerceirizado> lista = Facade.getInstance().listarRecursoTerceirizados();
		listaRecursosTerceirizados = lista;
	}

	private void verificarPermissao() {
		TipoUsuario tipoUsuarioLogado = Facade.getInstance().getUsuarioLogado().getTipoUsuario();
		if(tipoUsuarioLogado.getPermissao().contains(permission.ALTERAR_VENDEDOR)) {
			fieldFuncionario.setEnabled(true);
		} else {
			fieldFuncionario.setEnabled(false);
		}
	}

	public void createInputFields() {
		this.addInputField(getTabDetalhes(), new RestricaoLayout(1, 0, 2, 1, true, true));
		this.addInputField(getPanelImportarOrcamento(), new RestricaoLayout(2,0,1,true,false));
		this.addInputField(getPanelPrecoTotal(), new RestricaoLayout(2,1,1,true,false));
		carregarFieldFuncionario();
		carregarRecursos();
		carregarTerceirizados();
		carregarFieldEquipamentoGrupo();
	}

	public JPanel getPanelCliente() {
		JPanel panelCliente = new JFormGroup("Dados do Cliente");
		panelCliente.setLayout(new GridBagLayout());
		fieldClienteID = new JIntField();
		fieldClienteRazao = new JTextField();
		fieldClienteTelefone = new JFoneField();
		fieldClienteContato = new JTextField();
		fieldClienteCelular = new JFoneField();

		fieldClienteTelefone.setEditable(false);
		fieldClienteContato.setEditable(false);
		fieldClienteCelular.setEditable(false);

		panelCliente.add(new TitledPanel("Código", getPanelCodigoCliente()), new RestricaoLayout(0, 0, false, false));
		panelCliente.add(new TitledPanel("Razão Social", getPanelNomeCliente()), new RestricaoLayout(0, 1, 2, true, false));

		panelCliente.add(new TitledPanel("Telefone", fieldClienteTelefone), new RestricaoLayout(0, 3, 1, true, false));
		panelCliente.add(new TitledPanel("Contato", fieldClienteContato), new RestricaoLayout(0, 4, 1, true, false));
		panelCliente.add(new TitledPanel("Celular", fieldClienteCelular), new RestricaoLayout(0, 5, 1, true, false));

		return panelCliente;
	}

	public JPanel getPanelCodigoCliente() {
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(new BoxLayout(panelCliente, BoxLayout.LINE_AXIS));
		JButton botaoCliente = getBotaoProcurarCliente();

		fieldClienteID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idCliente = Integer.parseInt(fieldClienteID.getText());
						Cliente c = Facade.getInstance().carregarCliente(idCliente);
						if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_CLIENTES) ||
								Facade.getInstance().getUsuarioLogado().getFuncionario().getId() == c.getFuncionario().getId())
						carregarCliente(c);
					} catch (Exception e){
						limparCliente();
					}
				}
			}
		});

		botaoCliente.setPreferredSize(new Dimension(30,18));

		panelCliente.add(fieldClienteID);
		panelCliente.add(botaoCliente);
		return panelCliente;
	}

	public JPanel getPanelNomeCliente() {
		JPanel panelNomeCliente = new JPanel();
		panelNomeCliente.setLayout(new BoxLayout(panelNomeCliente, BoxLayout.LINE_AXIS));
		JButton botaoCliente = getBotaoAbrirCliente();
		botaoCliente.setPreferredSize(new Dimension(30,18));
		fieldClienteRazao = new JTextField();
		fieldClienteRazao.setEditable(false);
		panelNomeCliente.add(fieldClienteRazao);
		panelNomeCliente.add(botaoCliente);
		return panelNomeCliente;
	}
	
	private JTabbedPane getTabDetalhes() {
		tabDetalhes = new JTabbedPane();
		
		fieldObservacoes = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldObservacoes.setLineWrap(true);
		
		fieldObservacoesFinanceiras = new JTextArea();
		JScrollPane scrollObservacoesFinanceiras = new JScrollPane();
		scrollObservacoesFinanceiras.setViewportView(fieldObservacoesFinanceiras);
		fieldObservacoesFinanceiras.setLineWrap(true);
		
		tabDetalhes.addTab("Dados do Evento", getPanelDadosEvento());
		tabDetalhes.addTab("Local do Evento", getPanelEndereco());
		tabDetalhes.addTab("Equipamentos", getPanelEquipamento());
		tabDetalhes.addTab("Cenografia - Estruturas",  getPanelCenografia());
		tabDetalhes.addTab("Equipe Técnica", getPanelEquipeTecnica());
		tabDetalhes.addTab("Logística",  getPanelLogistica());
		tabDetalhes.addTab("Terceirizados",  getPanelTerceirizados());
		tabDetalhes.addTab("Dados do Orçamento", getPanelDadosProposta());
		//tabDetalhes.addTab("Detalhes do Evento", getPanelDetalhesEvento());
		tabDetalhes.addTab("Pagamento", getPanelLiberacaoOS());
		tabDetalhes.addTab("Anexo/Planilha", getPanelPlanilha());
		
		tabDetalhes.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(new StringSelection(""), null);
						
						if(tabDetalhes.getSelectedIndex() != 0) {
							if(fieldDataInicio.getDate() == null || fieldDataFim.getDate() == null) {
								tabDetalhes.setSelectedIndex(0);
								JOptionPane.showMessageDialog(null, "Preencha datas de início e fim do evento");
								return;
							}
							limparEquipamento();
							limparEquipeTecnica();
							limparCenografia();
							limparLogistica();
							limparAmbiente();
							limparTerceirizado();
							limparSubgrupos();
						}
						
						if(tabDetalhes.getSelectedIndex() >= 2) {
							if(modelAmbiente.getAmbientes().size() == 0){
								tabDetalhes.setSelectedIndex(1);
								JOptionPane.showMessageDialog(null, "Insira pelo menos um ambiente para o evento");
								return;
							}
						}
						if(tabDetalhes.getSelectedIndex() == 8) {
							calcularComissao();
						}
					}
				}
		);
		return tabDetalhes;
	}
	
/*	private JPanel getPanelFaturamento() {
		JPanel painel = new JPanel();
		fieldConfirmacaoFaturamento = new JCheckBox("Confirmação de Faturamento");
		painel.setLayout(new BorderLayout());
		fieldObservacoesFaturamento = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(fieldObservacoesFaturamento);
		painel.add(scroll, BorderLayout.CENTER);
		painel.add(fieldConfirmacaoFaturamento, BorderLayout.SOUTH);
		return painel;
		
	}*/
	
	private JPanel getPanelLiberacaoOS(){
		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());
		
		tabLiberacaoOS = new JTabbedPane();
		
		painelDadosEvento.add(tabLiberacaoOS,new RestricaoLayout(0, 0,4,7, true, true));
		
		tabLiberacaoOS.addTab("Pagamento", getPanelPagamento());
		tabLiberacaoOS.addTab("Comissão", getPanelComissao());

		//tabLiberacaoOS.addTab("Faturamento", getPanelFaturamento());
		painelDadosEvento.add(getBotaoExtornar(), new RestricaoLayout(7,0,false,false));
		return painelDadosEvento;	
		
	}
	
	private JButton getBotaoExtornar() {
		JButton botaoExtornar = new JButton("Estonar Ordem de Serviço" , new ImageIcon("images/Money-18x18.png"));
		botaoExtornar .addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(ordemServ==null) {
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione uma Ordem de Serviço", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						boolean possuiEquipamentoPendente = false;
						List<EquipamentoEnviado> equipamentos = ordemServ.getEquipamentoEnviado();
						for(EquipamentoEnviado eqv : equipamentos) {
							if(!eqv.isStatus()) {
								possuiEquipamentoPendente = true;
								break;
							}
						}
						if(possuiEquipamentoPendente){
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Não é possível estornar a Ordem de Serviço pois existem equipamentos pendentes.", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						boolean possuiParcelaPaga = false;
						List<Receita> receita = Facade.getInstance().listarReceitasPorOS(ordemServ);
						List<Despesa> despesa = Facade.getInstance().listarDespesas(ordemServ);

						for(Receita rec: receita){
							if(rec.isSituacao()) {
								possuiParcelaPaga = true;
								break;
							}
						}
						for(Despesa rec: despesa){
							if(rec.isSituacao()) {
								possuiParcelaPaga = true;
								break;
							}
						}
						if(possuiParcelaPaga) {
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Não é possível estornar a Ordem de Serviço pois existem parcelas ou despesas pagas.", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						StringChooserDialog dlg = new StringChooserDialog("Estorno de Ordem de Serviço", "Informe o motivo do estorno:");
						String motivo = dlg.showDialog(null);
						
						if(motivo != null && motivo.length() > 3) {
							
							SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
							String data = formato.format(new Date());
							
							
							ordemServ.setObservacoes(ordemServ.getObservacoes() + "\n\n" + "ORDEM DE SERVIÇO ESTORNADA POR "+ Facade.getInstance().getUsuarioLogado().getFuncionario().getNome() + " em " + data + ":\n" + motivo);
							ordemServ.setStatus(StatusOS.ESTORNADA);
							
							for(Receita r: receita){
								Facade.getInstance().removerReceita(r);
							}
							for(Despesa r: despesa){
								Facade.getInstance().removerDespesa(r);
							}
							
							Facade.getInstance().atualizarOrdemServico(ordemServ);
							JOptionPane.showMessageDialog(null, "Ordem de Serviço estornada com sucesso.");
							
						} else {
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Informe o motivo.\nA Ordem de Serviço não foi estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
							
						}

			
					}
				}
		);
		return botaoExtornar ;
	}

	private JPanel getPanelEndereco () { 
		JPanel endereco = new JPanel();
		endereco.setLayout(new GridBagLayout());

		fieldEnderecoLogradouro = new JTextField();
		fieldEnderecoNumero = new JTextField();
		fieldEnderecoComplemento = new JTextField();
		fieldEnderecoCEP = new JCepField();
		fieldEnderecoBairro = new JTextField();
		fieldEnderecoCidade = new JTextField();
		fieldEnderecoEstado = new JComboEstado();
		fieldEnderecoReferencia = new JTextField();
		fieldLocalNome = new JTextField();


		fieldObservacoesLocalEvento = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoesLocalEvento);
		
		endereco.add(new TitledPanel("Código", getPanelCodigoLocal()), new RestricaoLayout(0, 0, false, false));
		
		endereco.add(new TitledPanel("Nome do Local", fieldLocalNome), new RestricaoLayout(0, 1, 3, true, false));

		endereco.add(new TitledPanel("Logradouro", fieldEnderecoLogradouro), new RestricaoLayout(0,4, 2, true, false));
		endereco.add(new TitledPanel("Numero", fieldEnderecoNumero), new RestricaoLayout(0,6, 1, true, false));
		endereco.add(new TitledPanel("Complemento", fieldEnderecoComplemento), new RestricaoLayout(1,0, 2, true, false));
		endereco.add(new TitledPanel("CEP", fieldEnderecoCEP), new RestricaoLayout(1,2, 1, true, false));
		
		endereco.add(new TitledPanel("Bairro", fieldEnderecoBairro), new RestricaoLayout(1,3, 1, true, false));
		endereco.add(new TitledPanel("Cidade", fieldEnderecoCidade), new RestricaoLayout(1,4, 1, true, false));
		endereco.add(new TitledPanel("Estado", fieldEnderecoEstado), new RestricaoLayout(1,5, 1, true, false));
		endereco.add(new TitledPanel("Ponto de Referência", fieldEnderecoReferencia), new RestricaoLayout(1,6, 1,true, false));
		
		endereco.add(getPanelAmbientes(), new RestricaoLayout(2,0,7,2, true, true));
		endereco.add(new TitledPanel("Observações do Local do Evento", scrollObservacoes), new RestricaoLayout(4,0,7,1, true, true));

		return endereco;
	}
	
	public JPanel getPanelAmbientes() {
		JPanel panelAmbiente =new JFormGroup("Ambientes do Evento(Auditório, Teatro, Sala, etc...)"); 
		
		fieldAmbienteNome = new JTextField();
		fieldAmbienteDataInicio = new JDateChooser();
		fieldAmbienteDataFim = new JDateChooser();
		
		fieldAmbientePFixacao = new JTextField();
		fieldAmbientePeDireito = new JTextField();
		fieldAmbienteLargura = new JTextField();
		fieldAmbienteComprimento = new JTextField();
		fieldAmbienteArea = new JTextField();
		fieldAmbientePFixacao.setEditable(false);
		fieldAmbientePeDireito.setEditable(false);
		fieldAmbienteLargura.setEditable(false);
		fieldAmbienteComprimento.setEditable(false);
		fieldAmbienteArea.setEditable(false);
		
		modelAmbiente = new AmbienteTableModel();
		tabelaAmbiente = new JTable(modelAmbiente);
		fieldAmbientesEvento = new JComboBox();
		
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		menuTabela.add(getMenuAlterarNomeAmbiente());
		menuTabela.add(getMenuDuplicarAmbiente());
		
		
		carregarFieldAmbientesEvento(null);
		tabelaAmbiente.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaAmbiente.getSelectedRow() >= 0 )
								menuTabela.show(tabelaAmbiente, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Ambiente para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		
		tabelaAmbiente.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if(tabelaAmbiente.getSelectedRow() != -1 && ! tabelaAmbiente.isEditing()) {
					AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
					PanelSala ps = paineisEquipamento.get(ambienteAlterar.getNome());
					PanelSala psh = paineisEquipeTecnica.get(ambienteAlterar.getNome());
					PanelSala psc = paineisCenografia.get(ambienteAlterar.getNome());
					
					System.out.println("Achou os paineis"  + ps + ", " + psh + ", " + psc);
					//AmbienteEvento amb = new AmbienteEvento(ambienteAlterar.getNome(), ambienteAlterar.getDataInicio(), ambienteAlterar.getDataFim());
	
					Date dataInicialMudanca = (Date) modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 1);
					Date dataFinalMudanca = (Date) modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 2);
					AmbienteEvento amb = new AmbienteEvento(ambienteAlterar.getNome(), dataInicialMudanca, dataFinalMudanca);
					
					if( formato.format(ambienteAlterar.getDataInicio()).equals(formato.format(dataInicialMudanca)) && 
							formato.format(ambienteAlterar.getDataFim()).equals(formato.format(dataFinalMudanca))) {
						return;
					} 
					
					//alterar data de sala, tentativa de não mudar a ordem das salas
					ambienteAlterar.setDataInicio(dataInicialMudanca);
					ambienteAlterar.setDataFim(dataFinalMudanca);
					modelAmbiente.fireTableDataChanged();
					
					if(ps != null) {
						ps.getModel().alterarDatas(dataInicialMudanca, dataFinalMudanca);
						psh.getModel().alterarDatas(dataInicialMudanca, dataFinalMudanca);
						psc.getModel().alterarDatas(dataInicialMudanca, dataFinalMudanca);
						
						ps.calcularTotal();
						psh.calcularTotal();
						psc.calcularTotal();
					} 
					calcularTotal();
					calcularTotalTerceirizado();
					
				}
			}
			
		});		
		
		ZebraDecorator zebra = new ZebraDecorator();
		tabelaAmbiente.getColumnModel().getColumn(0).setCellRenderer(zebra);
		
		tabelaAmbiente.getColumnModel().getColumn(1).setCellEditor(new JDateChooserCellEditorBounded(this));
		tabelaAmbiente.getColumnModel().getColumn(2).setCellEditor(new JDateChooserCellEditorBounded(this));
		tabelaAmbiente.getColumnModel().getColumn(1).setCellRenderer(new DateCellRenderer());
		tabelaAmbiente.getColumnModel().getColumn(2).setCellRenderer(new DateCellRenderer());
		
		JScrollPane scrollAmbiente = new JScrollPane();
		scrollAmbiente.setViewportView(tabelaAmbiente);
		
		botaoAdicionarAmbiente = new JButton("Adicionar Ambiente");
		
		botaoAdicionarAmbiente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(fieldAmbienteNome.getText().length() < 3 && fieldAmbientesEvento.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Informe o nome do ambiente");
					return;
				}

				String nomeAmbiente = fieldAmbienteNome.getText();				
				if(fieldAmbientesEvento.getSelectedIndex() != 0) {
					if(fieldAmbienteNome.getText().length() < 3) {
						nomeAmbiente = ((SalaLocal)fieldAmbientesEvento.getSelectedItem()).getNome();
					}
				}
				if(paineisEquipamento.get(nomeAmbiente) != null) {
					JOptionPane.showMessageDialog(null, "O nome do ambiente já está cadastrado");
					return;
				}

				try {
					Date dataInicio = formato.parse(formato.format(fieldAmbienteDataInicio.getDate()));
					Date dataFim = formato.parse(formato.format(fieldAmbienteDataFim.getDate()));
					AmbienteEvento amb = new AmbienteEvento(nomeAmbiente, dataInicio, dataFim);
					adicionarAmbiente(amb);
					adicionarAmbienteLogistica();
					adicionarAmbienteTerceirizado();
					limparAmbiente();
				}					
				catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

		}
		);
			
		panelAmbiente.setLayout(new GridBagLayout());
		panelAmbiente.add(new TitledPanel("Ambientes Evento", fieldAmbientesEvento), new RestricaoLayout(0,0,1,true,false));
		panelAmbiente.add(new TitledPanel("Nome", fieldAmbienteNome), new RestricaoLayout(0,1,1,true,false));
		panelAmbiente.add(new TitledPanel("Data Início", fieldAmbienteDataInicio), new RestricaoLayout(0,2,1,true,false));
		panelAmbiente.add(new TitledPanel("Data Fim", fieldAmbienteDataFim), new RestricaoLayout(0,3,1,true,false));
		panelAmbiente.add(new TitledPanel("", botaoAdicionarAmbiente), new RestricaoLayout(0,4,true,false));
		
		panelAmbiente.add(new TitledPanel("Pontos de Fixação", fieldAmbientePFixacao), new RestricaoLayout(1,0,1,true,false));
		panelAmbiente.add(new TitledPanel("Pé Direito", fieldAmbientePeDireito), new RestricaoLayout(1,1,1,true,false));
		panelAmbiente.add(new TitledPanel("Largura", fieldAmbienteLargura), new RestricaoLayout(1,2,1,true,false));
		panelAmbiente.add(new TitledPanel("Comprimento", fieldAmbienteComprimento), new RestricaoLayout(1,3,1,true,false));
		panelAmbiente.add(new TitledPanel("Área", fieldAmbienteArea), new RestricaoLayout(1,4,1,true,false));
		
		panelAmbiente.add(new TitledPanel("", scrollAmbiente), new RestricaoLayout(2,0,5,1,true,true));
		return panelAmbiente;
	}
	
	private JMenuItem getMenuDuplicarAmbiente() {
		JMenuItem menuAlterarNome = new JMenuItem("Duplicar Ambiente");
		menuAlterarNome.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						PanelSala ps = paineisEquipamento.get(ambienteAlterar.getNome());
						PanelSala psh = paineisEquipeTecnica.get(ambienteAlterar.getNome());
						PanelSala psc = paineisCenografia.get(ambienteAlterar.getNome());

						String nome = JOptionPane.showInputDialog(null, "Informe o do novo ambiente:", "Duplicar ambiente", JOptionPane.QUESTION_MESSAGE);
						
						if(ambienteAlterar.getNome().equals("LOGÍSTICA")) {
							JOptionPane.showMessageDialog(null, "Não se pode duplicar o ambiente de logística");
						}
						else if(ambienteAlterar.getNome().equals("TERCEIRIZADOS")) {
							JOptionPane.showMessageDialog(null, "Não se pode duplicar o ambiente de terceirizados");
						}
						else if(nome.length() > 3) {
							AmbienteEvento amb = new AmbienteEvento(nome, ambienteAlterar.getDataInicio(), ambienteAlterar.getDataFim());
							amb.setDescontoCenografia(ambienteAlterar.getDescontoCenografia());
							amb.setDescontoEquipamento(ambienteAlterar.getDescontoEquipamento());
							amb.setDescontoTerceirizado(ambienteAlterar.getDescontoTerceirizado());
							
							if(paineisEquipamento.get(nome) == null) {
								if(ps != null) {
									//paineisEquipamento.remove(ambienteAlterar.getNome());
									//tabAmbientesEquipamento.remove(ps);
									adicionarAmbiente(amb);
									PanelSala ps2 = paineisEquipamento.get(amb.getNome());
									for(RecursoSolicitado rs : ps.getRecursosSolicitados()) {
										RecursoSolicitado rs2 = new RecursoSolicitado();
										rs2.setDataFim(rs.getDataFim());
										rs2.setDataInicio(rs.getDataInicio());
										rs2.setRecurso(rs.getRecurso());
										rs2.setQuantidade(rs.getQuantidade());
										rs2.setPrecoUnitario(rs.getPrecoUnitario());
										rs2.setDeletado(rs.isDeletado());
										rs2.setDescricao(rs.getDescricao());
										ps2.adicionarRecurso(rs2);
									}
									PanelSala psh2 = paineisEquipeTecnica.get(amb.getNome());
									for(RecursoSolicitado rs : psh.getRecursosSolicitados()) {
										RecursoSolicitado rs2 = new RecursoSolicitado();
										rs2.setDataFim(rs.getDataFim());
										rs2.setDataInicio(rs.getDataInicio());
										rs2.setRecurso(rs.getRecurso());
										rs2.setQuantidade(rs.getQuantidade());
										rs2.setPrecoUnitario(rs.getPrecoUnitario());
										rs2.setDeletado(rs.isDeletado());
										rs2.setDescricao(rs.getDescricao());
										psh2.adicionarRecurso(rs2);
									}
									
									PanelSala psc2 = paineisCenografia.get(amb.getNome());
									for(RecursoSolicitado rs : psc.getRecursosSolicitados()) {
										RecursoSolicitado rs2 = new RecursoSolicitado();
										rs2.setDataFim(rs.getDataFim());
										rs2.setDataInicio(rs.getDataInicio());
										rs2.setRecurso(rs.getRecurso());
										rs2.setQuantidade(rs.getQuantidade());
										rs2.setPrecoUnitario(rs.getPrecoUnitario());
										rs2.setDeletado(rs.isDeletado());
										rs2.setDescricao(rs.getDescricao());
										psc2.adicionarRecurso(rs2);
									}
									
									//modelAmbiente.removeRow(tabelaAmbiente.getSelectedRow());
								}
								calcularTotal();
								calcularTotalTerceirizado();
							} else 
								JOptionPane.showMessageDialog(null, "Nome inválido");
						}
					}
				}
					
		);
		return menuAlterarNome;
	}
	
	private JPanel getPanelPlanilha() {
		JPanel planilha = new JPanel();
		planilha.setLayout(new GridBagLayout());
		
		pdfController = new SwingController();
		SwingViewBuilder factory = new SwingViewBuilder(pdfController);
		JPanel viewerComponentPanel = factory.buildViewerPanel();
		
		planilha.add(getBotaoCarregarPlanilha(), new RestricaoLayout(0,0,1,true, false));
		//planilha.add(getBotaoSalvarPlanilha(), new RestricaoLayout(1,0,1, 1,true, true));
		planilha.add(viewerComponentPanel, new RestricaoLayout(1,0,1,1, true, true));

		return planilha;
	}
	
	private JButton getBotaoCarregarPlanilha() {
		buttonCarregarPlanilha = new JButton("Carregar Planilha (pdf) ...", new ImageIcon(getClass().getResource("/images/desc_18.png")));
		buttonCarregarPlanilha.setMinimumSize(new Dimension(20,20));
		buttonCarregarPlanilha.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser fc = new JFileChooser();
						fc.setFileFilter (new FileNameExtensionFilter("Documento pdf", "pdf"));
						fc.setAcceptAllFileFilterUsed(false);
				        int returnVal = fc.showOpenDialog(FormOrdemServico.this);
				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				        	File file = fc.getSelectedFile();
				        	FormOrdemServico.this.abrirPlanilha(OrdemServico.converterPlanilhaString(file));
				        	
				        	
				        } 
					}
				}
		);
		return buttonCarregarPlanilha;
	}
	
/*	private JButton getBotaoSalvarPlanilha() {
		buttonSalvarPlanilha = new JButton("Copiar Planilha");

		buttonSalvarPlanilha.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(planilha != null) 
							OrdemServico.salvarPlanilha(planilha);
						else
							JOptionPane.showMessageDialog(null, "Esta ordem de serviço não contém planilha", "Atenção!",  JOptionPane.WARNING_MESSAGE);
								
					}
				}
			);
    	
    	return buttonSalvarPlanilha;
	}*/

	private JPanel getPanelPagamento() {
		JPanel panelPagamento = new JPanel();
		panelPagamento.setLayout(new GridBagLayout());
		menuTabelaPagamento = new JPopupMenu();
		menuTabelaPagamento.add(getMenuRemoverParcela());
		modelParcelas = new ReceitaTableModel();
		tabelaParcelas = new JTable(modelParcelas);
		tabelaParcelas.getColumnModel().getColumn(1).setCellEditor(new JDateChooserCellEditorUnbounded());
		tabelaParcelas.getColumnModel().getColumn(4).setCellEditor(new MoedaCellEditor());
				
		tabelaParcelas.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaParcelas.getSelectedRow() >= 0 )
								menuTabelaPagamento.show(tabelaParcelas, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione uma Parcela para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}

		);

		fieldObservacoesFinanceiras = new JTextArea();
		JScrollPane scrollObservacoesFinanceiras = new JScrollPane();
		scrollObservacoesFinanceiras.setViewportView(fieldObservacoesFinanceiras);
		fieldObservacoesFinanceiras.setLineWrap(true);
		
		fieldValorParcela = new JMoedaRealTextField();
		fieldDataParcela = new JDateChooser();
		fieldDescricaoParcela = new JTextField();
		botaoAdicionarParcela = getBotaoAdicionarParcela();
		fieldEmpenho = new JCheckBox("Empenho");
		JScrollPane painelTabela = new JScrollPane();
		painelTabela.setViewportView(tabelaParcelas);
		
		JPanel panelParcelas = new JPanel();
		panelParcelas.setLayout(new GridBagLayout());
		panelParcelas.add(painelTabela, new RestricaoLayout(0,0,5,1,true,true));
		panelParcelas.add(new TitledPanel("Valor", fieldValorParcela), new RestricaoLayout(1,0,1,true,false));
		panelParcelas.add(new TitledPanel("Vencimento", fieldDataParcela), new RestricaoLayout(1,1,1,true,false));
		panelParcelas.add(new TitledPanel("Descrição", fieldDescricaoParcela), new RestricaoLayout(1,2,2,true,false));
		//panelParcelas.add(new TitledPanel(" ", fieldEmpenho), new RestricaoLayout(1,3,false,false));
		panelParcelas.add(new TitledPanel(" ", botaoAdicionarParcela), new RestricaoLayout(1,4,false,false));
		
		fieldQuantidadeParcela = new JIntField();
		fieldDataPrimeiraParcela = new JDateChooser();
		fieldEmpenhoParcelas = new JCheckBox("Empenho");
		
		panelPagamento.add(new TitledPanel("Quantidade de Parcelas", fieldQuantidadeParcela), new RestricaoLayout(0, 0, 1, true, false));
		panelPagamento.add(new TitledPanel("Data da Primeira Parcela", fieldDataPrimeiraParcela), new RestricaoLayout(0, 1, 2, true, false));
		//panelPagamento.add(new TitledPanel(" ", fieldEmpenhoParcelas), new RestricaoLayout(0, 2, false, false));
		panelPagamento.add(new TitledPanel(" ", getBotaoGerarParcelas()), new RestricaoLayout(0, 3,2, true, false));
		panelPagamento.add(new TitledPanel("Observações Financeiras",scrollObservacoesFinanceiras), new RestricaoLayout(2,0,5,1,true,true));
		panelPagamento.add(new TitledPanel("Parcelas", panelParcelas), new RestricaoLayout(1, 0, 5, 1, true, true));
		
		
		return panelPagamento;
	}	
	
	private BigDecimal calcularComissao() {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		System.out.println("Calcular Total: ");
		try {
			BigDecimal total = ((new BigDecimal(formato.parse(fieldPercentualComissao.getText()).longValue())).divide(new BigDecimal(100)));
			if(fieldValorIntegral.isSelected())
				total = total.multiply(calcularTotal());
			else if(fieldSemLogOp.isSelected())
				total = total.multiply(calcularTotalSemLogOp());
			
			fieldTotalComissao.setText("R$ "+formato.format(total));
			System.out.println(total);
			return total;
		} catch (ParseException e) {
			
		}
		return null;
	}
	
	private JPanel getPanelComissao() {
		JPanel panelComissao = new JPanel();
		panelComissao.setLayout(new GridBagLayout());	
		
		fieldPercentualComissao = new JIntField();
		fieldValorIntegral = new JRadioButton("Valor Integral do Evento");
		fieldSemLogOp = new JRadioButton("Excluir Logística e Equipe Técnica");
		fieldDataVencimento = new JDateChooser();
		fieldTotalComissao = new JTextField();
		
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(fieldValorIntegral);
		bgroup.add(fieldSemLogOp);
		
		fieldDataVencimento.setDate(new Date());
		fieldValorIntegral.setSelected(true);
		fieldTotalComissao.setEditable(false);
		
		fieldPercentualComissao.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				calcularComissao();
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				calcularComissao();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				calcularComissao();
				
			}
		});
		
		fieldValorIntegral.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				calcularComissao();
			}
		});
		
		
		fieldSemLogOp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				calcularComissao();
			}
		});
		
		JPanel painelValor = new JPanel();
		painelValor.setLayout(new GridLayout(1,2));
		painelValor.add(fieldValorIntegral);
		painelValor.add(fieldSemLogOp);
		
		JButton botaoLimpar = new JButton("Remover Comissão");
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparAgencia();
				calcularComissao();
			}
		});
		
		panelComissao.setLayout(new GridBagLayout());
		panelComissao.add(getPanelAgencia(), new RestricaoLayout(0,0,6,true,false));
		panelComissao.add(new TitledPanel("Percentual Comissão (%)", fieldPercentualComissao), new RestricaoLayout(1,0,3,true,false));
		panelComissao.add(new TitledPanel("Valor", painelValor), new RestricaoLayout(1,3,1,true,false));
		//panelParcelas.add(new TitledPanel(" ", fieldEmpenho), new RestricaoLayout(1,3,false,false));
		panelComissao.add(new TitledPanel("Data Vencimento", fieldDataVencimento), new RestricaoLayout(2,0,1,true,false));
		panelComissao.add(new TitledPanel("Total da Comissão", fieldTotalComissao), new RestricaoLayout(2,1,3,true,false));
		panelComissao.add(botaoLimpar, new RestricaoLayout(3,0,2,true,false));
		panelComissao.add(new JPanel(), new RestricaoLayout(4,0,6,1,true,true));
		
		return panelComissao;
	}	
	
	public JPanel getPanelAgencia() {
		JPanel panelAgencia = new JFormGroup("Dados da Agência");
		panelAgencia.setLayout(new GridBagLayout());
		fieldAgenciaID = new JIntField();
		fieldAgenciaFornecedor = new JTextField();

		fieldAgenciaFornecedor.setEditable(false);
		fieldAgenciaID.setEditable(true);

		panelAgencia.add(new TitledPanel("Código", getPanelCodigoAgencia()), new RestricaoLayout(0, 0, false, false));
		panelAgencia.add(new TitledPanel("Fornecedor", fieldAgenciaFornecedor), new RestricaoLayout(0, 1, 1, 1, true, false));

		return panelAgencia;
	}
	
	public JPanel getPanelCodigoAgencia() {
		JPanel panelAgencia = new JPanel();
		panelAgencia.setLayout(new BoxLayout(panelAgencia, BoxLayout.LINE_AXIS));
		botaoAgencia = getBotaoProcurarAgencia();

		fieldAgenciaID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idAgencia = Integer.parseInt(fieldAgenciaID.getText());
						Fornecedor c = Facade.getInstance().carregarFornecedor(idAgencia);
						carregarAgencia(c);
					} catch (Exception e){
						limparAgencia();
					}
				}
			}
		});

		botaoAgencia.setPreferredSize(new Dimension(30,18));

		panelAgencia.add(fieldAgenciaID);
		panelAgencia.add(botaoAgencia);
		return panelAgencia;
	}
	
	public JButton getBotaoProcurarAgencia() {
		JButton botaoProcurarAgencia = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarAgencia.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarAgencia.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarAgencia.setPreferredSize(new Dimension(20,20));

		botaoProcurarAgencia.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchFornecedor teste = new DialogSearchFornecedor(FormOrdemServico.this);
						Fornecedor c = teste.showDialog(FormOrdemServico.this);
						if(c != null)
							carregarAgencia(c);
					}
				}	
		);
		return botaoProcurarAgencia;
	}
	
	private JMenuItem getMenuRemoverParcela() {
		JMenuItem menuRemover = new JMenuItem("Remover Parcela");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Receita rem = (Receita)modelParcelas.getValueAt(tabelaParcelas.getSelectedRow(), modelParcelas.getObjectIndex());
						if(ordemServ.getStatus() == StatusOS.ESTORNADA) {
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Não é possível alterar uma Ordem de Serviço Estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if(rem.isSituacao()) {
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Não é possível remover uma parcela paga", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(rem.getId() != null)
							parcelasRemovidas.add(rem);
						modelParcelas.removeRow(tabelaParcelas.getSelectedRow());
						if(modelParcelas.getListaReceita().size() == 0) {
							botaoGerarParcelas.setEnabled(true);
						}
					}
				}		
		);
		return menuRemover;
	}
	
	private JButton getBotaoAdicionarParcela() {
		botaoAdicionarParcela = new JButton("Adicionar Parcela");
		botaoAdicionarParcela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			/*	if(ordemServ==null) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione uma Ordem de Serviço", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}*/
				if(ordemServ != null && ordemServ.getStatus() == StatusOS.ESTORNADA) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Não é possível adicionar parcelas em uma Ordem de Serviço Estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				BigDecimal valor= new BigDecimal(0);
				try {
					if(fieldValorParcela.getValor() == null) {
						JOptionPane.showMessageDialog(FormOrdemServico.this, "Informe o valor da parcela", "ERRO", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						valor = fieldValorParcela.getValor();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Informe o valor da parcela", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(fieldDataParcela.getDate() == null) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Informe a data da parcela!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Receita rec = new Receita();
				rec.setDataCadastro(new Date());
				rec.setDataVencimento(fieldDataParcela.getDate());
				rec.setDescricao(fieldDescricaoParcela.getText());
				rec.setFuncionarioCadastro(Facade.getInstance().getUsuarioLogado().getFuncionario());
				rec.setOrdemServico(ordemServ);
				rec.setValor(valor);
				rec.setEmpenho(fieldEmpenho.isSelected());
				modelParcelas.addElement(rec);
				//botaoAdicionarParcela.setEnabled(false);
				limparParcela();
			}
		});
		
		return botaoAdicionarParcela;
		
	}
	
	private void limparParcela() {
		fieldValorParcela.setValor(null);
		fieldDataParcela.setDate(null);
		fieldDescricaoParcela.setText("");
		fieldEmpenho.setSelected(false);
	}
	
	private JButton getBotaoGerarParcelas() {
		botaoGerarParcelas = new JButton("Gerar Parcelas");
		botaoGerarParcelas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			/*	if(ordemServ==null) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione uma Ordem de Serviço", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}*/
				if(fieldQuantidadeParcela.getText().equals("") || Integer.parseInt(fieldQuantidadeParcela.getText()) <= 0) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Informe a quantidade de parcelas!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(fieldDataPrimeiraParcela.getDate() == null) {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Informe a data da primeira parcela!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int quantidadeParcela = Integer.parseInt(fieldQuantidadeParcela.getText());
				
				
				GregorianCalendar dataParcela = new GregorianCalendar();
				dataParcela.setTime(fieldDataPrimeiraParcela.getDate());
				BigDecimal totalPagar = calcularTotal();
				BigDecimal totalParcelas = calcularTotalParcelas();
				
				totalPagar = totalPagar.subtract(totalParcelas);
				
				
				BigDecimal valorParcela = new BigDecimal((totalPagar.intValue()* 100 /quantidadeParcela)/100);
				BigDecimal valorUltimaParcela = totalPagar.subtract(valorParcela.multiply(new BigDecimal(quantidadeParcela - 1)));
				boolean empenho = fieldEmpenhoParcelas.isSelected();
				
				for(int i=1; i <= quantidadeParcela; i++) {
					Receita rec = new Receita();
					rec.setDataCadastro(new Date());
					rec.setDataVencimento(new Date(dataParcela.getTimeInMillis()));
					rec.setDescricao("Parcela " + i + " de " +  quantidadeParcela + " do evento " + FormOrdemServico.this.getNomeEvento());
					rec.setFuncionarioCadastro(Facade.getInstance().getUsuarioLogado().getFuncionario());
					rec.setOrdemServico(ordemServ);
					rec.setEmpenho(empenho);
					if(i == quantidadeParcela) {
						rec.setValor(valorUltimaParcela);
					} else {
						rec.setValor(valorParcela);
					}
					rec.getValor();
					modelParcelas.addElement(rec);
					dataParcela.add(GregorianCalendar.MONTH, 1);
				}
				botaoGerarParcelas.setEnabled(false);
			}
		});
		
		return botaoGerarParcelas;
		
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Ambiente");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String nomeAbiente = "" + modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						if(nomeAbiente.equals("LOGÍSTICA")) {
							JOptionPane.showMessageDialog(null, "Não se pode remover o ambiente de logística");
						}
						else if(nomeAbiente.equals("TERCEIRIZADOS")) {
							JOptionPane.showMessageDialog(null, "Não se pode remover o ambiente de terceirizados");
						}
						else {
							PanelSala ps = paineisEquipamento.get(nomeAbiente);
							PanelSala psh = paineisEquipeTecnica.get(nomeAbiente);
							PanelSala psc = paineisCenografia.get(nomeAbiente);
							if(ps != null) {
								paineisEquipamento.remove(nomeAbiente);
								tabAmbientesEquipamento.remove(ps);
								
								paineisEquipeTecnica.remove(nomeAbiente);
								tabAmbientesEquipeTecnica.remove(psh);
								
								paineisCenografia.remove(nomeAbiente);
								tabAmbientesCenografia.remove(psc);
								
								modelAmbiente.removeRow(tabelaAmbiente.getSelectedRow());
								calcularTotal();
								calcularTotalTerceirizado();
							}
						}
					}
				}		
		);
		return menuRemover;
	}
	
	
	private JMenuItem getMenuAlterarNomeAmbiente() {
		JMenuItem menuAlterarNome = new JMenuItem("Alterar nome do Ambiente");
		menuAlterarNome.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						PanelSala ps = paineisEquipamento.get(ambienteAlterar.getNome());
						PanelSala psh = paineisEquipeTecnica.get(ambienteAlterar.getNome());
						PanelSala psc = paineisCenografia.get(ambienteAlterar.getNome());

						String nome = JOptionPane.showInputDialog(null, "Informe o novo nome:", "Alterar o nome do ambiente", JOptionPane.QUESTION_MESSAGE);
						
						if(nome.length() > 3) {
							AmbienteEvento amb = new AmbienteEvento(nome, ambienteAlterar.getDataInicio(), ambienteAlterar.getDataFim());
							amb.setDescontoCenografia(ambienteAlterar.getDescontoCenografia());
							amb.setDescontoEquipamento(ambienteAlterar.getDescontoEquipamento());
							amb.setDescontoTerceirizado(ambienteAlterar.getDescontoTerceirizado());
							
							if(ambienteAlterar.getNome().equals("LOGÍSTICA")) {
								JOptionPane.showMessageDialog(null, "Não se pode alterar o nome do ambiente de logística");
							}
							else if(ambienteAlterar.getNome().equals("TERCEIRIZADOS")) {
								JOptionPane.showMessageDialog(null, "Não se pode alterar o nome do ambiente de terceirizados");
							}
							else if(paineisEquipamento.get(nome) == null) {
								if(ps != null) {
									paineisEquipamento.remove(ambienteAlterar.getNome());
									tabAmbientesEquipamento.remove(ps);
									
									paineisEquipeTecnica.remove(ambienteAlterar.getNome());
									tabAmbientesEquipeTecnica.remove(psh);
									
									paineisCenografia.remove(ambienteAlterar.getNome());
									tabAmbientesCenografia.remove(psc);
									
									adicionarAmbiente(amb);

									PanelSala ps2 = paineisEquipamento.get(amb.getNome());
									for(RecursoSolicitado rs : ps.getRecursosSolicitados()) {
										ps2.adicionarRecurso(rs);
									}
									
									PanelSala psh2 = paineisEquipeTecnica.get(amb.getNome());
									for(RecursoSolicitado rs : psh.getRecursosSolicitados()) {
										psh2.adicionarRecurso(rs);
									}
									
									PanelSala psc2 = paineisCenografia.get(amb.getNome());
									for(RecursoSolicitado rs : psc.getRecursosSolicitados()) {
										psc2.adicionarRecurso(rs);
									}
																		
									modelAmbiente.removeRow(tabelaAmbiente.getSelectedRow());
								}
								calcularTotal();
								calcularTotalTerceirizado();
							} else {
								JOptionPane.showMessageDialog(null, "O nome do ambiente informado já está cadastrado");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Nome inválido");
						}
					}
				}		
		);
		return menuAlterarNome;
	}

	private void adicionarAmbiente(AmbienteEvento amb) {
		if(paineisLogistica.get(amb.getNome()) == null && paineisTerceirizados.get(amb.getNome()) == null) {
			PanelSala pn = new PanelSala(amb, FormOrdemServico.this, true, true,TipoRecurso.EQUIPAMENTO);
			PanelSala pnc = new PanelSala(amb, FormOrdemServico.this, false, false,TipoRecurso.CENOGRAFIA);
			PanelSala pnet = new PanelSala(amb, FormOrdemServico.this, false, false,TipoRecurso.EQUIPE_TECNICA);

			tabAmbientesEquipamento.addTab(amb.getNome(), pn);
			tabAmbientesCenografia.addTab(amb.getNome(), pnc);
			tabAmbientesEquipeTecnica.addTab(amb.getNome(), pnet);

			paineisEquipamento.put(amb.getNome(), pn);
			paineisCenografia.put(amb.getNome(), pnc);
			paineisEquipeTecnica.put(amb.getNome(),pnet);
			
			modelAmbiente.addElement(amb);
		}
		else {
			JOptionPane.showMessageDialog(null, "Não se pode criar o ambiente de logística ou terceirizados");
		}

	}
	
	private void adicionarAmbienteLogistica(AmbienteEvento amb) {
		PanelSala pnl = new PanelSala(amb, FormOrdemServico.this, false, false,TipoRecurso.LOGISTICA);
		tabAmbientesLogistica.addTab(amb.getNome(),pnl);
		paineisLogistica.put(amb.getNome(), pnl);	
	}
	
	
	private void adicionarAmbienteTerceirizado(AmbienteEvento amb) {
		PanelSalaTerceirizado pnl = new PanelSalaTerceirizado(amb, FormOrdemServico.this, false, false,fieldTerceirizadoEmp.isSelected());
		tabAmbientesTerceirizados.addTab(amb.getNome(),pnl);
		paineisTerceirizados.put(amb.getNome(), pnl);	
	}
	
	private void adicionarAmbienteLogistica() {
		if(paineisLogistica.size() == 0) {
			AmbienteEvento amb = new AmbienteEvento("LOGÍSTICA",fieldDataMontagem.getDate(),fieldDataFim.getDate());
			PanelSala pnl = new PanelSala(amb, FormOrdemServico.this, false, false,TipoRecurso.LOGISTICA);
			tabAmbientesLogistica.addTab(amb.getNome(),pnl);
			paineisLogistica.put(amb.getNome(), pnl);
		//	modelAmbiente.addElement(amb);
		}
	}
	
	private void adicionarAmbienteTerceirizado() {
		if(paineisTerceirizados.size() == 0) {
			try {
				Date dataInicio = formato.parse(formato.format(fieldDataMontagem.getDate()));
				Date dataFim = formato.parse(formato.format(fieldDataFim.getDate()));
				AmbienteEvento amb = new AmbienteEvento("TERCEIRIZADOS",dataInicio,dataFim);
				PanelSalaTerceirizado pnl = new PanelSalaTerceirizado(amb, FormOrdemServico.this, false, false,fieldTerceirizadoEmp.isSelected());
				tabAmbientesTerceirizados.addTab(amb.getNome(),pnl);
				paineisTerceirizados.put(amb.getNome(), pnl);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void limparAmbiente() {
		fieldAmbienteDataFim.setDate(fieldDataFim.getDate());
		fieldAmbienteDataInicio.setDate(fieldDataInicio.getDate());
		fieldAmbienteNome.setText("");
		fieldAmbientesEvento.setSelectedIndex(0);
	}


/*	private JButton getBotaoImportarEnderecoCliente() {
		JButton botaoImportarEnderecoCliente = new JButton("Utilizar Endereço do Cliente" );

		botaoImportarEnderecoCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cliente != null) {
							fieldEnderecoBairro.setText(cliente.getEndereco().getBairro());
							fieldEnderecoLogradouro.setText(cliente.getEndereco().getLogradouro());
							fieldEnderecoNumero.setText(cliente.getEndereco().getNumero());
							fieldEnderecoComplemento.setText(cliente.getEndereco().getComplemento());
							fieldEnderecoCidade.setText(cliente.getEndereco().getCidade());
							fieldEnderecoEstado.setSelectedItem(cliente.getEndereco().getEstado());
							fieldEnderecoReferencia.setText(cliente.getEndereco().getPontoReferencia());
							fieldEnderecoCEP.setText(cliente.getEndereco().getCep());
						}

					}
				}
		);
		return botaoImportarEnderecoCliente;
	}*/


	public JPanel getPanelCodigoLocal() {
		JPanel panelRecurso = new JPanel();
		panelRecurso.setLayout(new BoxLayout(panelRecurso, BoxLayout.LINE_AXIS));
		JButton botaoLocal = getBotaoProcurarLocal();
		fieldLocalID = new JIntField();
		fieldLocalID.setEditable(false);
		fieldLocalID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idLocal = Integer.parseInt(fieldLocalID.getText());
						Local l = Facade.getInstance().carregarLocal(idLocal);
						
						carregarLocal(l);
						carregarFieldAmbientesEvento(l);
					} catch (Exception e){
						e.printStackTrace();
						limparLocal();
					}
				}
			}
		});

		botaoLocal.setPreferredSize(new Dimension(30,18));
		panelRecurso.add(fieldLocalID);
		panelRecurso.add(botaoLocal);
		//panelRecurso.add(getBotaoImportarEnderecoCliente());
		return panelRecurso;
	}
	
	protected String getNomeEvento() {
		if(fieldNomeEvento != null)
			return fieldNomeEvento.getText();
		else
			return "";
	}

	private JPanel getPanelDadosEvento() {
		fieldNomeEvento = new JTextField();
		fieldFuncionario = new JComboBox();
		fieldVendedorConjunto = new JComboBox();
		fieldDataInicio = new JDateChooser();
		fieldDataFim = new JDateChooser();
		fieldDataMontagem = new JDateChooser();

		fieldNomeContatoEvento = new JTextField();
		fieldTelefoneContatoEvento = new JTextField();
		fieldDetalhesEvento = new JTextArea();
		

		
		fieldStatusOS = new JComboBox();
		fieldStatusOS.setEnabled(false);
		carregarFieldStatus();
		
		fieldHoraInicio = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorInicio = new JSpinner.DateEditor(fieldHoraInicio, "HH:mm");
		fieldHoraInicio.setEditor(timeEditorInicio);
		
		fieldHoraFim = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorFim = new JSpinner.DateEditor(fieldHoraFim, "HH:mm");
		fieldHoraFim.setEditor(timeEditorFim);
		
		fieldHoraMontagem = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorMontagem = new JSpinner.DateEditor(fieldHoraMontagem, "HH:mm");
		fieldHoraMontagem.setEditor(timeEditorMontagem);
		
		JPanel painelDataHoraInicio = new JPanel();
		painelDataHoraInicio.setLayout(new GridBagLayout());
		painelDataHoraInicio.add(fieldDataInicio, new RestricaoLayout(0,0,1,true,false));
		painelDataHoraInicio.add(fieldHoraInicio, new RestricaoLayout(0,1,1,true,false));
		
		JPanel painelDataHoraFim = new JPanel();
		painelDataHoraFim.setLayout(new GridBagLayout());
		painelDataHoraFim.add(fieldDataFim, new RestricaoLayout(0,0,1,true,false));
		painelDataHoraFim.add(fieldHoraFim, new RestricaoLayout(0,1,1,true,false));
		
		JPanel painelDataHoraMontagem = new JPanel();
		painelDataHoraMontagem.setLayout(new GridBagLayout());
		painelDataHoraMontagem.add(fieldDataMontagem, new RestricaoLayout(0,0,1,true,false));
		painelDataHoraMontagem.add(fieldHoraMontagem, new RestricaoLayout(0,1,1,true,false));
		
		fieldDataMontagem.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataMontagem.getDate() != null && ! disableDateEvent) {
							try {
								Date dataMontagem = formato.parse(formato.format(fieldDataMontagem.getDate()));
								fieldEquipeTecnicaDataInicio.setDate(dataMontagem);
								fieldLogisticaDataInicio.setDate(dataMontagem);
								//fieldEquipeTecnicaDataInicio.setMinSelectableDate(dataMontagem);
								//fieldEquipeTecnicaDataFim.setMinSelectableDate(dataMontagem);
								//fieldLogisticaDataInicio.setMinSelectableDate(dataMontagem);
								//fieldLogisticaDataFim.setMinSelectableDate(dataMontagem);
								
								PanelSala panelLogistica = paineisLogistica.get("LOGÍSTICA");
								if(panelLogistica != null && panelLogistica.getAmbiente() != null) {
									panelLogistica.getAmbiente().setDataInicio(dataMontagem);
									panelLogistica.getModel().alterarDatas(dataMontagem, panelLogistica.getAmbiente().getDataFim());
									panelLogistica.calcularTotal();
								}

								tabelaAmbiente.clearSelection();
								
								if(modelAmbiente != null) {
									for(AmbienteEvento a : modelAmbiente.getAmbientes()) {
										PanelSala psh = paineisEquipeTecnica.get(a.getNome());
										a.setDataInicio(dataMontagem);
										
										if(psh != null) {
											psh.getModel().alterarDatas(dataMontagem, a.getDataFim());
											psh.calcularTotal();
										} 
									}
								}
								
								calcularTotal();
								calcularTotalTerceirizado();
								
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						
					}
				}
			);
		
		
		
		fieldDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataInicio.getDate() != null && ! disableDateEvent) {
							try {
								Date dataInicio = formato.parse(formato.format(fieldDataInicio.getDate()));
								
								fieldCenografiaDataInicio.setDate(dataInicio);
								fieldEquipamentoDataInicio.setDate(dataInicio);
								fieldTerceirizadoDataInicio.setDate(dataInicio);
								
								fieldDataFim.setMinSelectableDate(dataInicio);
								
								fieldAmbienteDataInicio.setMinSelectableDate(dataInicio);
								fieldAmbienteDataFim.setMinSelectableDate(dataInicio);
								//fieldEquipamentoDataInicio.setMinSelectableDate(dataInicio);
								//fieldEquipamentoDataFim.setMinSelectableDate(dataInicio);
								
								//fieldTerceirizadoDataInicio.setMinSelectableDate(dataInicio);
								//fieldTerceirizadoDataFim.setMinSelectableDate(dataInicio);
								
								//fieldCenografiaDataInicio.setMinSelectableDate(dataInicio);
								//fieldCenografiaDataFim.setMinSelectableDate(dataInicio);
								
								
								//Long limiteMontagem = fieldDataInicio.getDate().getTime() - 7200000;
								fieldDataMontagem.setMaxSelectableDate(dataInicio);
								//fieldDataMontagem.setMaxSelectableDate(new Date(limiteMontagem));
								
								PanelSalaTerceirizado panelTerceirizados = paineisTerceirizados.get("TERCEIRIZADOS");
								if(panelTerceirizados!= null && panelTerceirizados.getAmbiente() != null) {
									panelTerceirizados.getAmbiente().setDataInicio(dataInicio);
									panelTerceirizados.getModel().alterarDatas(dataInicio, panelTerceirizados.getAmbiente().getDataFim());
									panelTerceirizados.calcularTotal(fieldTerceirizadoEmp.isSelected());
								}
								tabelaAmbiente.clearSelection();
								
								if(modelAmbiente != null) {
									modelAmbiente.alterarDataInicial(dataInicio);
								
									for(AmbienteEvento a : modelAmbiente.getAmbientes()) {
										PanelSala ps = paineisEquipamento.get(a.getNome());
										PanelSala psc = paineisCenografia.get(a.getNome());
										
										a.setDataInicio(dataInicio);
										
										if(ps != null) {
											ps.getModel().alterarDatas(dataInicio, a.getDataFim());
											psc.getModel().alterarDatas(dataInicio, a.getDataFim());
											ps.calcularTotal();
											psc.calcularTotal();
										} 
									}
								}
								
								calcularTotal();
								calcularTotalTerceirizado();
							
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
						}
							
					}
				}
		);

		fieldDataFim.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataFim.getDate() != null && ! disableDateEvent) {
							try {
								Date dataFim = formato.parse(formato.format(fieldDataFim.getDate()));
								
								fieldDataInicio.setMaxSelectableDate(dataFim);
								fieldDataMontagem.setMaxSelectableDate(dataFim);
								
								fieldCenografiaDataFim.setDate(dataFim);
								fieldEquipeTecnicaDataFim.setDate(dataFim);
								fieldEquipamentoDataFim.setDate(dataFim);
								fieldTerceirizadoDataFim.setDate(dataFim);
								fieldLogisticaDataFim.setDate(dataFim);
								
								fieldAmbienteDataInicio.setMaxSelectableDate(dataFim);
								fieldAmbienteDataFim.setMaxSelectableDate(dataFim);
								
								//fieldEquipamentoDataInicio.setMaxSelectableDate(dataFim);
								//fieldEquipamentoDataFim.setMaxSelectableDate(dataFim);
								//fieldTerceirizadoDataInicio.setMaxSelectableDate(dataFim);
								//fieldTerceirizadoDataFim.setMaxSelectableDate(dataFim);
								//fieldEquipeTecnicaDataInicio.setMaxSelectableDate(dataFim);
								//fieldEquipeTecnicaDataFim.setMaxSelectableDate(dataFim);
								//fieldCenografiaDataInicio.setMaxSelectableDate(dataFim);
								//fieldCenografiaDataFim.setMaxSelectableDate(dataFim);
								//fieldLogisticaDataInicio.setMaxSelectableDate(dataFim);
								//fieldLogisticaDataFim.setMaxSelectableDate(dataFim);
								
								
								PanelSala panelLogistica = paineisLogistica.get("LOGÍSTICA");
								if(panelLogistica != null && panelLogistica.getAmbiente() != null) {
									panelLogistica.getAmbiente().setDataFim(dataFim);
									panelLogistica.getModel().alterarDatas(panelLogistica.getAmbiente().getDataInicio(), dataFim);
									panelLogistica.calcularTotal();
								}
								
								PanelSalaTerceirizado panelTerceirizados = paineisTerceirizados.get("TERCEIRIZADOS");
								if(panelTerceirizados!= null && panelTerceirizados.getAmbiente() != null) {
									panelTerceirizados.getAmbiente().setDataFim(dataFim);
									panelTerceirizados.getModel().alterarDatas(panelTerceirizados.getAmbiente().getDataInicio(), dataFim);
									panelTerceirizados.calcularTotal(fieldTerceirizadoEmp.isSelected());
								}
								
								tabelaAmbiente.clearSelection();
								
								if(modelAmbiente != null) {
									modelAmbiente.alterarDataFinal(dataFim);
								
									for(AmbienteEvento a : modelAmbiente.getAmbientes()) {
										PanelSala ps = paineisEquipamento.get(a.getNome());
										PanelSala psh = paineisEquipeTecnica.get(a.getNome());
										PanelSala psc = paineisCenografia.get(a.getNome());

										a.setDataFim(dataFim);
										
										if(ps != null) {
											ps.getModel().alterarDatas(a.getDataInicio(), dataFim);
											psh.getModel().alterarDatas(a.getDataInicio(), dataFim);
											psc.getModel().alterarDatas(a.getDataInicio(), dataFim);
											ps.calcularTotal();
											psh.calcularTotal();
											psc.calcularTotal();
										} 
									}
								}
								
								calcularTotal();
								calcularTotalTerceirizado();
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}	
					}
				}
		);
		
		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());
		
		painelDadosEvento.add(getPanelCliente(), new RestricaoLayout(0, 0, 6, true, false));

		painelDadosEvento.add(new TitledPanel("Nome do Evento", fieldNomeEvento), new RestricaoLayout(1, 0, 2, true, false));
		
		painelDadosEvento.add(new TitledPanel("Contato no Evento", fieldNomeContatoEvento), new RestricaoLayout(1, 2, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Telefone do Contato", fieldTelefoneContatoEvento), new RestricaoLayout(1, 3, 1, true, false));
		
		
		painelDadosEvento.add(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayout(1, 4, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Status", fieldStatusOS), new RestricaoLayout(1, 5, 1, true, false));
		
		painelDadosEvento.add(new TitledPanel("Início do Evento", painelDataHoraInicio), new RestricaoLayout(2, 0, 2, true, false));
		painelDadosEvento.add(new TitledPanel("Fim do Evento", painelDataHoraFim), new RestricaoLayout(2,2,2,true, false));
		painelDadosEvento.add(new TitledPanel("Montagem do Evento", painelDataHoraMontagem), new RestricaoLayout(2,4,2,true, false));
		
		JPanel panelDetalhes = new JPanel();
		panelDetalhes.setLayout(new GridBagLayout());
		//panelDetalhes.setMinimumSize(new Dimension(500, 386));
		//panelDetalhes.setSize(new Dimension(247483647, 247483647));
		
		JScrollPane scroll = new JScrollPane();
		fieldDetalhesEvento.setWrapStyleWord(true);
		fieldDetalhesEvento.setLineWrap(true);
		scroll.setViewportView(fieldDetalhesEvento);
		
		
		panelDetalhes.add(scroll, new RestricaoLayout(0, 0, 1,1, true, true));
		/*panelDetalhes.add(getBotaoCarregarPlanilha(), new RestricaoLayout(1,0,1,true, false));
		
		JPanel pplanilha = new JPanel(new GridBagLayout());
		GridBagConstraints layoutDetalhes = new RestricaoLayout(0, 0,1,1, true, true);
		layoutDetalhes.weightx = 1.0;
		
		GridBagConstraints layoutPlanilha = new RestricaoLayout(0,1,1,1, true,true);
		layoutPlanilha.weightx = 1.0;
		
		pplanilha.add(new TitledPanel("Detalhes do Evento", panelDetalhes), layoutDetalhes);
		pplanilha.add(new TitledPanel("Planilha", getPanelPlanilha()), layoutPlanilha);*/ 
		
		painelDadosEvento.add(new TitledPanel("Detalhes do Evento", panelDetalhes), new RestricaoLayout(3,0,8,1, true, true));
		
		return painelDadosEvento;		
	}
	
	public JPanel getPanelObservacoes() {
		JPanel panelObservacoes = new JPanel();
		panelObservacoes.setLayout(new GridBagLayout());
		fieldObservacoes = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldObservacoes.setLineWrap(true);
		
		fieldObservacoesFinanceiras = new JTextArea();
		JScrollPane scrollObservacoesFinanceiras = new JScrollPane();
		scrollObservacoesFinanceiras.setViewportView(fieldObservacoesFinanceiras);
		fieldObservacoesFinanceiras.setLineWrap(true);

		panelObservacoes.add(new TitledPanel("Observações Internas", scrollObservacoes), new RestricaoLayout(0, 0, 1, 1, true, true));
		panelObservacoes.add(new TitledPanel("Observações Financeiras", scrollObservacoesFinanceiras), new RestricaoLayout(0, 1, 1, 1, true, true));
		return panelObservacoes;
	}
	
	public JPanel getPanelTerceirizados() {
		fieldTerceirizadoNome = new JComboBox();
		fieldTerceirizadoGrupo = new JComboBox();
		carregarFieldTerceirizadoGrupo();
		
		fieldTerceirizadoPrecoEmpresa = new JMoedaRealTextField();
		fieldTerceirizadoPrecoForn = new JMoedaRealTextField();

		fieldTerceirizadoDataInicio = new JDateChooser();
		fieldTerceirizadoDataFim = new JDateChooser();
		fieldTerceirizadoQuantidade = new JIntField();
		fieldTerceirizadoDescricao = new JTextField();

		tabAmbientesTerceirizados = new JTabbedPane();
		paineisTerceirizados = new HashMap<String, PanelSalaTerceirizado>();

		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());

		fieldTerceirizadoDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						fieldTerceirizadoDataFim.setMinSelectableDate(fieldTerceirizadoDataInicio.getDate());
					}
				}
		);
		
		tabAmbientesTerceirizados.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabAmbientesTerceirizados.getSelectedComponent() != null) {
					PanelSalaTerceirizado salaSelecionada = (PanelSalaTerceirizado) tabAmbientesTerceirizados.getComponent(tabAmbientesTerceirizados.getSelectedIndex());
					//fieldTerceirizadoDataInicio.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldTerceirizadoDataInicio.setMaxSelectableDate(salaSelecionada.getDataFinal());
					//fieldTerceirizadoDataFim.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldTerceirizadoDataFim.setMaxSelectableDate(salaSelecionada.getDataFinal());
					fieldTerceirizadoDataInicio.setDate(salaSelecionada.getDataInicial());
					fieldTerceirizadoDataFim.setDate(salaSelecionada.getDataFinal());
				}
			}
		});
		
		JPanel adicionar = new JFormGroup("Adicionar Equipamento Terceirizado");
		adicionar.setLayout(new GridBagLayout());

		adicionar.add(new TitledPanel("Fechamento", getFieldFechamento()), new RestricaoLayout(1, 2, 1 , true, false));
		adicionar.add(new TitledPanel("Grupo", fieldTerceirizadoGrupo), new RestricaoLayout(0, 0, 1 , true, false));
		adicionar.add(new TitledPanel("Subgrupo", fieldTerceirizadoNome), new RestricaoLayout(0, 1, 1, true, false));
		adicionar.add(new TitledPanel("Preço SNE", fieldTerceirizadoPrecoEmpresa), new RestricaoLayout(1, 1, true, false));
		adicionar.add(new TitledPanel("Preço Fornecedor", fieldTerceirizadoPrecoForn), new RestricaoLayout(1, 0, true, false));

		adicionar.add(new TitledPanel("Quant.", fieldTerceirizadoQuantidade), new RestricaoLayout(0, 4, true, false));
		adicionar.add(new TitledPanel("Data Inicial", fieldTerceirizadoDataInicio), new RestricaoLayout(0, 2, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldTerceirizadoDataFim), new RestricaoLayout(0, 3, true, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarTerceirizado()) , new RestricaoLayout(0, 5,  false, false));
		adicionar.add(new TitledPanel("Descrição", fieldTerceirizadoDescricao), new RestricaoLayout(1, 3,3, true, false));

		panelRecursos.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelRecursos.add(tabAmbientesTerceirizados, new RestricaoLayout(1,0,1,1,true,true));
		
		limparPanelTerceirizado();
		return panelRecursos;		
	}
	
	private JPanel getFieldFechamento() {
		JPanel panelFechamento = new JPanel();
		fieldTerceirizadoEmp = new JCheckBox("SNE");
		fieldTerceirizadoFor = new JCheckBox("Fornecedor");
		
		fieldTerceirizadoEmp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				PanelSalaTerceirizado salaSelecionada = (PanelSalaTerceirizado) tabAmbientesTerceirizados.getComponent(tabAmbientesTerceirizados.getSelectedIndex());
				salaSelecionada.setEmpresa(fieldTerceirizadoEmp.isSelected());
				calcularTotal();
				calcularTotalTerceirizado();

			}
		});
		
		fieldTerceirizadoFor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				calcularTotal();
				calcularTotalTerceirizado();
			}
		});
		
		panelFechamento.add(fieldTerceirizadoEmp, new RestricaoLayout(0, 0 , false, false));
		panelFechamento.add(fieldTerceirizadoFor, new RestricaoLayout(0, 1 , false, false));

		return panelFechamento;
	}
	
	public JPanel getPanelLogistica() {
		fieldLogisticaNome = new JComboBox();
		fieldLogisticaDiaria = new JComboBox();
		fieldLogisticaGrupo = new JComboBox();
		
		carregarFieldLogistica();
		
		fieldLogisticaPreco = new JMoedaRealTextField();
		fieldLogisticaCusto = new JMoedaRealTextField();

		fieldLogisticaDataInicio = new JDateChooser();
		fieldLogisticaDataFim = new JDateChooser();
		fieldLogisticaQuantidade = new JIntField();
		fieldLogisticaDescricao = new JTextField();

		tabAmbientesLogistica= new JTabbedPane();
		paineisLogistica = new HashMap<String, PanelSala>();
		
		JPanel panelLogistica= new JPanel();
		panelLogistica.setLayout(new GridBagLayout());

		fieldLogisticaDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						fieldLogisticaDataFim.setMinSelectableDate(fieldLogisticaDataInicio.getDate());
					}
				}
		);
		
		tabAmbientesLogistica.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabAmbientesLogistica.getSelectedComponent() != null) {
					PanelSala salaSelecionada = (PanelSala) tabAmbientesLogistica.getComponent(tabAmbientesLogistica.getSelectedIndex());
					//fieldLogisticaDataInicio.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldLogisticaDataInicio.setMaxSelectableDate(salaSelecionada.getDataFinal());
					//fieldLogisticaDataFim.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldLogisticaDataFim.setMaxSelectableDate(salaSelecionada.getDataFinal());
					fieldLogisticaDataInicio.setDate(salaSelecionada.getDataInicial());
					fieldLogisticaDataFim.setDate(salaSelecionada.getDataFinal());
				}
			}
		});
		
		JPanel adicionar = new JFormGroup("Adicionar Logística");

		adicionar.add(new TitledPanel("Grupo", fieldLogisticaGrupo), new RestricaoLayout(0, 0, 1 , true, false));
		adicionar.add(new TitledPanel("Preço de Custo", fieldLogisticaCusto), new RestricaoLayout(1, 0, true, false));

		adicionar.add(new TitledPanel("Preço", fieldLogisticaPreco), new RestricaoLayout(1, 1, true, false));
		adicionar.add(new TitledPanel("Data Inicial", fieldLogisticaDataInicio), new RestricaoLayout(0, 2, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldLogisticaDataFim), new RestricaoLayout(0, 3, true, false));
		adicionar.add(new TitledPanel("Quant.", fieldLogisticaQuantidade), new RestricaoLayout(0, 4, true, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarLogistica()) , new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel("Subgrupo Por Período", fieldLogisticaNome), new RestricaoLayout(0, 1, 1, true, false));
		//adicionar.add(new TitledPanel("Subgrupo Por Diária", fieldLogisticaDiaria), new RestricaoLayout(1, 1, 1, true, false));

		adicionar.add(new TitledPanel("Descrição", fieldLogisticaDescricao), new RestricaoLayout(1, 2,4, true, false));

		panelLogistica.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelLogistica.add(tabAmbientesLogistica, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelLogistica();
		return panelLogistica;		
	}	
	
	private JButton getBotaoAdicionarLogistica() {
		addLogisticaButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addLogisticaButton.setHorizontalAlignment(SwingConstants.CENTER);
		addLogisticaButton.setVerticalAlignment(SwingConstants.CENTER);
		addLogisticaButton.setPreferredSize(new Dimension(20,20));
		addLogisticaButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recurso != null) {
					try {
						Date dataInicio = formato.parse(formato.format(fieldLogisticaDataInicio.getDate()));
						Date dataFim = formato.parse(formato.format(fieldLogisticaDataFim.getDate()));
						RecursoSolicitado rec = new RecursoSolicitado();
						rec.setRecurso(recurso);
						rec.setDataFim(dataFim);
						rec.setDataInicio(dataInicio);
						try {
							rec.setPrecoUnitario(fieldLogisticaPreco.getValor());
							rec.setPrecoCusto(fieldLogisticaCusto.getValor());

						} catch(Exception err) {}
						rec.setQuantidade(Integer.parseInt(fieldLogisticaQuantidade.getText()));
						rec.setDescricao(fieldLogisticaDescricao.getText());
						PanelSala salaSelecionada = (PanelSala) tabAmbientesLogistica.getComponent(tabAmbientesLogistica.getSelectedIndex());
						salaSelecionada.adicionarRecurso(rec);
						calcularTotal();
						limparLogistica();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addLogisticaButton;
	}

	public JPanel getPanelDadosProposta() {
		JPanel panelProposta = new JPanel();
		panelProposta.setLayout(new GridBagLayout());
		
		fieldNomeProposta = new JTextField();
		fieldCargoProposta = new JTextField();
		fieldTelefoneProposta = new JTextField();
		fieldNomePropostaConjunta = new JTextField();
		fieldCargoPropostaConjunta = new JTextField();
		fieldTelefonePropostaConjunta = new JTextField();
		fieldCondicoesPagamento = new JTextField();
		fieldObservacoesCliente = new JTextArea();
		JScrollPane scrollObservacoesCliente = new JScrollPane();
		scrollObservacoesCliente.setViewportView(fieldObservacoesCliente);
		
		panelProposta.add(new TitledPanel("Venda Conjunta com", fieldVendedorConjunto), new RestricaoLayout(0,0,1,true,false));

		panelProposta.add(new TitledPanel("Condições de Pagamento", fieldCondicoesPagamento), new RestricaoLayout(1,0,2,true,false));
		panelProposta.add(new TitledPanel("Nome do Funcionário no Orçamento", fieldNomeProposta), new RestricaoLayout(2,0,1,true,false));
		panelProposta.add(new TitledPanel("Cargo do Funcionário no Orçamento", fieldCargoProposta), new RestricaoLayout(3,0,1,true,false));
		panelProposta.add(new TitledPanel("Telefone do Funcionário no Orçamento", fieldTelefoneProposta), new RestricaoLayout(4,0,1,true,false));
		panelProposta.add(new TitledPanel("Nome do Funcionário Conjunto no Orçamento", fieldNomePropostaConjunta), new RestricaoLayout(2,1,1,true,false));
		panelProposta.add(new TitledPanel("Cargo do Funcionário Conjunto no Orçamento", fieldCargoPropostaConjunta), new RestricaoLayout(3,1,1,true,false));
		panelProposta.add(new TitledPanel("Telefone do Funcionário Conjunto no Orçamento", fieldTelefonePropostaConjunta), new RestricaoLayout(4,1,1,true,false));
		panelProposta.add(new TitledPanel("Observações para o Cliente", scrollObservacoesCliente), new RestricaoLayout(5, 0, 2, 1, true, true));
		
		return panelProposta;
	}
	
	public JPanel getPanelEquipamento() {
		fieldEquipamentoNome = new JComboBox();
		fieldEquipamentoGrupo = new JComboBox();
		carregarFieldEquipamentoGrupo();
		fieldEquipamentoDescricao = new JTextField();
		
		fieldEquipamentoPreco = new JMoedaRealTextField();
		fieldEquipamentoCusto = new JMoedaRealTextField();

		fieldEquipamentoDataInicio = new JDateChooser();
		fieldEquipamentoDataFim = new JDateChooser();
		fieldEquipamentoQuantidade = new JIntField();

		tabAmbientesEquipamento = new JTabbedPane();
		paineisEquipamento = new HashMap<String, PanelSala>();

		JPanel panelEquipamento = new JPanel();
		panelEquipamento.setLayout(new GridBagLayout());

		fieldEquipamentoDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						fieldEquipamentoDataFim.setMinSelectableDate(fieldEquipamentoDataInicio.getDate());
					}
				}
		);
		
		tabAmbientesEquipamento.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabAmbientesEquipamento.getSelectedComponent() != null) {
					PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipamento.getComponent(tabAmbientesEquipamento.getSelectedIndex());
					//fieldEquipamentoDataInicio.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldEquipamentoDataInicio.setMaxSelectableDate(salaSelecionada.getDataFinal());
					//fieldEquipamentoDataFim.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldEquipamentoDataFim.setMaxSelectableDate(salaSelecionada.getDataFinal());
					fieldEquipamentoDataInicio.setDate(salaSelecionada.getDataInicial());
					fieldEquipamentoDataFim.setDate(salaSelecionada.getDataFinal());
				}
			}
		});
		
		tabAmbientesEquipamento.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int indice = tabAmbientesEquipamento.getSelectedIndex();
				tabAmbientesCenografia.setSelectedIndex(indice);
				tabAmbientesEquipeTecnica.setSelectedIndex(indice);
			}
		});	
		
		JPanel adicionar = new JFormGroup("Adicionar Equipamento");
		adicionar.setLayout(new GridBagLayout());
		

		adicionar.add(new TitledPanel("Grupo", fieldEquipamentoGrupo), new RestricaoLayout(0, 0, 1 , true, false));
		adicionar.add(new TitledPanel("Subgrupo", fieldEquipamentoNome), new RestricaoLayout(0, 1, 1, true, false));
		adicionar.add(new TitledPanel("Preço", fieldEquipamentoPreco), new RestricaoLayout(1, 1, true, false));
		adicionar.add(new TitledPanel("Preço de Custo", fieldEquipamentoCusto), new RestricaoLayout(1, 0, true, false));

		adicionar.add(new TitledPanel("Data Inicial", fieldEquipamentoDataInicio), new RestricaoLayout(0, 2, false, false));
		adicionar.add(new TitledPanel("Data Final", fieldEquipamentoDataFim), new RestricaoLayout(0, 3, false, false));
		adicionar.add(new TitledPanel("Quant.", fieldEquipamentoQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecurso()) , new RestricaoLayout(0,5, false, false));
		adicionar.add(new TitledPanel("Descrição", fieldEquipamentoDescricao), new RestricaoLayout(1, 2,4, true, false));

		panelEquipamento.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelEquipamento.add(tabAmbientesEquipamento, new RestricaoLayout(1,0,1,1,true,true));
		
		limparPanelEquipamento();
		return panelEquipamento;		
	}
	
	public JPanel getPanelCenografia() {
		fieldCenografiaNome = new JComboBox();
		fieldCenografiaGrupo = new JComboBox();
		carregarFieldCenografia();
		
		fieldCenografiaPreco = new JMoedaRealTextField();
		fieldCenografiaCusto = new JMoedaRealTextField();

		fieldCenografiaDataInicio = new JDateChooser();
		fieldCenografiaDataFim = new JDateChooser();
		fieldCenografiaQuantidade = new JIntField();
		fieldCenografiaDescricao = new JTextField();

		tabAmbientesCenografia= new JTabbedPane();
		paineisCenografia = new HashMap<String, PanelSala>();
		

		JPanel panelCenografia= new JPanel();
		panelCenografia.setLayout(new GridBagLayout());

		fieldCenografiaDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						fieldCenografiaDataFim.setMinSelectableDate(fieldCenografiaDataInicio.getDate());
					}
				}
		);
		
		tabAmbientesCenografia.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabAmbientesCenografia.getSelectedComponent() != null) {
					PanelSala salaSelecionada = (PanelSala) tabAmbientesCenografia.getComponent(tabAmbientesCenografia.getSelectedIndex());
					//fieldCenografiaDataInicio.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldCenografiaDataInicio.setMaxSelectableDate(salaSelecionada.getDataFinal());
					//fieldCenografiaDataFim.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldCenografiaDataFim.setMaxSelectableDate(salaSelecionada.getDataFinal());
					fieldCenografiaDataInicio.setDate(salaSelecionada.getDataInicial());
					fieldCenografiaDataFim.setDate(salaSelecionada.getDataFinal());
				}
			}
		});
		
		tabAmbientesCenografia.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int indice = tabAmbientesCenografia.getSelectedIndex();
				tabAmbientesEquipamento.setSelectedIndex(indice);
				tabAmbientesEquipeTecnica.setSelectedIndex(indice);
			}
		});	
		
		JPanel adicionar = new JFormGroup("Adicionar Cenografia");
		adicionar.setLayout(new GridBagLayout());

		adicionar.add(new TitledPanel("Grupo", fieldCenografiaGrupo), new RestricaoLayout(0, 0, 1 , true, false));
		adicionar.add(new TitledPanel("Subgrupo", fieldCenografiaNome), new RestricaoLayout(0, 1, 1, true, false));
		adicionar.add(new TitledPanel("Preço", fieldCenografiaPreco), new RestricaoLayout(1, 1, true, false));
		adicionar.add(new TitledPanel("Preço de Custo", fieldCenografiaCusto), new RestricaoLayout(1, 0, true, false));
		adicionar.add(new TitledPanel("Data Inicial", fieldCenografiaDataInicio), new RestricaoLayout(0, 2, false, false));
		adicionar.add(new TitledPanel("Data Final", fieldCenografiaDataFim), new RestricaoLayout(0, 3, false, false));
		adicionar.add(new TitledPanel("Quant.", fieldCenografiaQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarCenografia()) , new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel("Descrição", fieldCenografiaDescricao), new RestricaoLayout(1, 2,4, true, false));
		
		panelCenografia.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelCenografia.add(tabAmbientesCenografia, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelCenografia();
		return panelCenografia;		
	}	
	
	private JButton getBotaoAdicionarCenografia() {
		addCenografiaButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addCenografiaButton.setHorizontalAlignment(SwingConstants.CENTER);
		addCenografiaButton.setVerticalAlignment(SwingConstants.CENTER);
		addCenografiaButton.setPreferredSize(new Dimension(20,20));
		addCenografiaButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recurso != null) {
					try {
						Date dataInicio = formato.parse(formato.format(fieldCenografiaDataInicio.getDate()));
						Date dataFim = formato.parse(formato.format(fieldCenografiaDataFim.getDate()));
						RecursoSolicitado rec = new RecursoSolicitado();
						rec.setRecurso(recurso);
						rec.setDataFim(dataFim);
						rec.setDataInicio(dataInicio);
						try {
							rec.setPrecoUnitario(fieldCenografiaPreco.getValor());
							rec.setPrecoCusto(fieldCenografiaCusto.getValor());

						} catch(Exception err) {}
						rec.setQuantidade(Integer.parseInt(fieldCenografiaQuantidade.getText()));
						rec.setDescricao(fieldCenografiaDescricao.getText());
						PanelSala salaSelecionada = (PanelSala) tabAmbientesCenografia.getComponent(tabAmbientesCenografia.getSelectedIndex());
						salaSelecionada.adicionarRecurso(rec);
						calcularTotal();
						limparCenografia();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addCenografiaButton;
	}
	
	public JPanel getPanelEquipeTecnica() {
		fieldEquipeTecnicaNome = new JComboBox();
		fieldEquipeTecnicaGrupo = new JComboBox();
		carregarFieldEquipeTecnicaGrupo();
		
		fieldEquipeTecnicaPreco = new JMoedaRealTextField();
		fieldEquipeTecnicaCusto = new JMoedaRealTextField();

		fieldEquipeTecnicaDataInicio = new JDateChooser();
		fieldEquipeTecnicaDataFim = new JDateChooser();
		fieldEquipeTecnicaQuantidade = new JIntField();
		fieldEquipeTecnicaDescricao = new JTextField();
		
		tabAmbientesEquipeTecnica = new JTabbedPane();
		paineisEquipeTecnica = new HashMap<String, PanelSala>();

		JPanel panelEquipeTecnica = new JPanel();
		panelEquipeTecnica.setLayout(new GridBagLayout());

		fieldEquipeTecnicaDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						fieldEquipeTecnicaDataFim.setMinSelectableDate(fieldEquipeTecnicaDataInicio.getDate());
					}
				}
		);
		
		tabAmbientesEquipeTecnica.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabAmbientesEquipeTecnica.getSelectedComponent() != null) {
					PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipeTecnica.getComponent(tabAmbientesEquipeTecnica.getSelectedIndex());
					//fieldEquipeTecnicaDataInicio.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldEquipeTecnicaDataInicio.setMaxSelectableDate(salaSelecionada.getDataFinal());
					//fieldEquipeTecnicaDataFim.setMinSelectableDate(salaSelecionada.getDataInicial());
					//fieldEquipeTecnicaDataFim.setMaxSelectableDate(salaSelecionada.getDataFinal());
					fieldEquipeTecnicaDataInicio.setDate(salaSelecionada.getDataInicial());
					fieldEquipeTecnicaDataFim.setDate(salaSelecionada.getDataFinal());
				}
			}
		});
		
		tabAmbientesEquipeTecnica.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int indice = tabAmbientesEquipeTecnica.getSelectedIndex();
				tabAmbientesEquipamento.setSelectedIndex(indice);
				tabAmbientesCenografia.setSelectedIndex(indice);
			}
		});	
		
		JPanel adicionar = new JFormGroup("Adicionar Equipe Técnica");
		adicionar.setLayout(new GridBagLayout());

		adicionar.add(new TitledPanel("Grupo", fieldEquipeTecnicaGrupo), new RestricaoLayout(0, 0, 1 , true, false));
		adicionar.add(new TitledPanel("Subgrupo", fieldEquipeTecnicaNome), new RestricaoLayout(0, 1, 1, true, false));
		adicionar.add(new TitledPanel("Preço", fieldEquipeTecnicaPreco), new RestricaoLayout(1, 1, true, false));
		adicionar.add(new TitledPanel("Preço de Custo", fieldEquipeTecnicaCusto), new RestricaoLayout(1, 0, true, false));

		adicionar.add(new TitledPanel("Data Inicial", fieldEquipeTecnicaDataInicio), new RestricaoLayout(0,2, false, false));
		adicionar.add(new TitledPanel("Data Final", fieldEquipeTecnicaDataFim), new RestricaoLayout(0, 3, false, false));
		adicionar.add(new TitledPanel("Quant.", fieldEquipeTecnicaQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecursoHumano()) , new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel("Descrição", fieldEquipeTecnicaDescricao), new RestricaoLayout(1,2,4, true, false));

		panelEquipeTecnica.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelEquipeTecnica.add(tabAmbientesEquipeTecnica, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelEquipeTecnica();
		return panelEquipeTecnica;		
	}

	private JPanel getPanelPrecoTotal() {
		fieldPrecoTotal = new JLabel("R$ 0,00");
		fieldPrecoTotal.setFont(new Font("Arial", Font.BOLD, 18));
		fieldPrecoTotal.setForeground(Color.BLUE);
		
		fieldPrecoSubtotal = new JLabel("R$ 0,00");
		fieldPrecoSubtotal.setFont(new Font("Arial", Font.BOLD, 18));
		fieldPrecoSubtotal.setForeground(Color.BLUE);
		
		fieldPrecoTerceirizado = new JLabel("R$ 0,00");
		fieldPrecoTerceirizado.setFont(new Font("Arial", Font.BOLD, 18));
		fieldPrecoTerceirizado.setForeground(Color.RED);
		
		fieldDesconto = new JLabel("R$ 0,00");
		fieldDesconto.setFont(new Font("Arial", Font.BOLD, 18));
		fieldDesconto.setForeground(new Color(0,150,0));
		
		fieldPrecoCustoSubtotal = new JLabel("R$ 0,00");
		fieldPrecoCustoSubtotal.setFont(new Font("Arial", Font.BOLD, 18));
		fieldPrecoCustoSubtotal.setForeground(Color.RED);
		
		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));
		total.add(new TitledPanel("Terceirizados",fieldPrecoTerceirizado));
		total.add(new TitledPanel("Preço Custo",fieldPrecoCustoSubtotal));
		total.add(new TitledPanel("Subtotal", fieldPrecoSubtotal));
		total.add(new TitledPanel("Desconto", fieldDesconto));
		total.add(new TitledPanel("Total", fieldPrecoTotal));

		return total;
	}

	private JPanel getPanelImportarOrcamento() {
		JPanel pOrcamento = new JPanel();
		pOrcamento.setLayout(new FlowLayout(FlowLayout.LEFT));
		pOrcamento.add(getBotaoImportarOrcamento() );
		pOrcamento.add(getBotalAlterarDatInicial() );
		return pOrcamento;
	}
	
	

	private JButton getBotaoImportarOrcamento() {
		JButton botaoOrcamento = new JButton("Importar Orçamento" , new ImageIcon(getClass().getResource("/images/Money-18x18.png")));
		botaoOrcamento.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchOrcamento2 teste = new DialogSearchOrcamento2(FormOrdemServico.this);
						Orcamento o = teste.showDialog(FormOrdemServico.this);
						if(o != null) {
							OrdemServico os = new OrdemServico(o);
							loadForm2(os, false);
							orcamento = o;
						}
					}
				}
		);
		return botaoOrcamento;
	}
	
	private JButton getBotalAlterarDatInicial() {
		JButton botaoAlterarDataInicial = new JButton("Alterar Datas", new ImageIcon(getClass().getResource("/images/calendar_18.png")));
		botaoAlterarDataInicial.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DateChooserDialog dlg = new DateChooserDialog("Alterar as Datas do Evento", "Escolha a nova data inicial:");
						Date dd = dlg.showDialog(null);
						if(dd != null && fieldDataInicio.getDate() != null) {
							long diferenca = dd.getTime() - fieldDataInicio.getDate().getTime(); 
							fieldDataInicio.setDate(dd);
							if(fieldDataFim.getDate() != null) {
								fieldDataFim.setDate(new Date(fieldDataFim.getDate().getTime() + diferenca));
							}
							if(fieldDataMontagem.getDate() != null) {
								fieldDataMontagem.setDate(new Date(fieldDataMontagem.getDate().getTime() + diferenca));
							}
							
							List<AmbienteEvento> listaAmbientes = modelAmbiente.getAmbientes();
							modelAmbiente.removeAllElements();
							
							for(PanelSala amb : paineisLogistica.values()) {
								amb.getAmbiente().setDataInicio(new Date(amb.getAmbiente().getDataInicio().getTime() + diferenca));
								amb.getAmbiente().setDataFim(new Date(amb.getAmbiente().getDataFim().getTime() + diferenca));
								
								PanelSala psl = paineisLogistica.get(amb.getAmbiente().getNome());
								List<RecursoSolicitado> listaRecursosLogistica = psl.getRecursosSolicitados();
								psl.removeAllElements();
								for(RecursoSolicitado rec: listaRecursosLogistica) {
									rec.setDataInicio(new Date(rec.getDataInicio().getTime() + diferenca));
									rec.setDataFim(new Date(rec.getDataFim().getTime() + diferenca));
									psl.adicionarRecurso(rec);
								}
							}
						
							for(PanelSalaTerceirizado amb : paineisTerceirizados.values()) {
								amb.getAmbiente().setDataInicio(new Date(amb.getAmbiente().getDataInicio().getTime() + diferenca));
								amb.getAmbiente().setDataFim(new Date(amb.getAmbiente().getDataFim().getTime() + diferenca));
								
								PanelSalaTerceirizado psl = paineisTerceirizados.get(amb.getAmbiente().getNome());
								List<RecursoTerceirizadoSolicitado> listaRecursosTerceirizados = psl.getRecursosTerceirizadosSolicitados();
								psl.removeAllElements();
								for(RecursoTerceirizadoSolicitado rec: listaRecursosTerceirizados) {
									rec.setDataInicio(new Date(rec.getDataInicio().getTime() + diferenca));
									rec.setDataFim(new Date(rec.getDataFim().getTime() + diferenca));
									psl.adicionarRecurso(rec);
								}
							}
							
							for(AmbienteEvento amb : listaAmbientes) {
									amb.setDataInicio(new Date(amb.getDataInicio().getTime() + diferenca));
									amb.setDataFim(new Date(amb.getDataFim().getTime() + diferenca));
									modelAmbiente.addElement(amb);
									PanelSala ps = paineisEquipamento.get(amb.getNome());
									PanelSala psc = paineisCenografia.get(amb.getNome());
									PanelSala pset = paineisEquipeTecnica.get(amb.getNome());
									
									List<RecursoSolicitado> listaRecursos = ps.getRecursosSolicitados();
									List<RecursoSolicitado> listaRecursosCenografia = psc.getRecursosSolicitados();
									List<RecursoSolicitado> listaEquipeTecnica = pset.getRecursosSolicitados();
									
									ps.removeAllElements();
									psc.removeAllElements();
									pset.removeAllElements();
									
									for(RecursoSolicitado rec: listaRecursos) {
										rec.setDataInicio(new Date(rec.getDataInicio().getTime() + diferenca));
										rec.setDataFim(new Date(rec.getDataFim().getTime() + diferenca));
										ps.adicionarRecurso(rec);
									}
								
									for(RecursoSolicitado rec: listaRecursosCenografia) {
										rec.setDataInicio(new Date(rec.getDataInicio().getTime() + diferenca));
										rec.setDataFim(new Date(rec.getDataFim().getTime() + diferenca));
										psc.adicionarRecurso(rec);
									}
									
									for(RecursoSolicitado rec: listaEquipeTecnica) {
										rec.setDataInicio(new Date(rec.getDataInicio().getTime() + diferenca));
										rec.setDataFim(new Date(rec.getDataFim().getTime() + diferenca));
										pset.adicionarRecurso(rec);
									}
								}
						}
					}
				}
		);
		return botaoAlterarDataInicial;
		
	}

	private JButton getBotaoAdicionarTerceirizado() {
		addTerceirizadoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addTerceirizadoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addTerceirizadoButton.setVerticalAlignment(SwingConstants.CENTER);
		addTerceirizadoButton.setPreferredSize(new Dimension(20,20));
		addTerceirizadoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recursoTerceirizado != null) {
					try {
						Date dataInicio = formato.parse(formato.format(fieldTerceirizadoDataInicio.getDate()));
						Date dataFim = formato.parse(formato.format(fieldTerceirizadoDataFim.getDate()));
						RecursoTerceirizadoSolicitado rec = new RecursoTerceirizadoSolicitado();
						rec.setRecurso((RecursoTerceirizado)recursoTerceirizado);
						rec.setDataFim(dataFim);
						rec.setDataInicio(dataInicio);
						try {
							rec.setPrecoEmpresa(fieldTerceirizadoPrecoEmpresa.getValor());
							rec.setPrecoFornecedor(fieldTerceirizadoPrecoForn.getValor());
						} catch(Exception err) {}
						rec.setPrecoUnitario(((RecursoTerceirizado)recursoTerceirizado).getPrecoEmpresa());
						rec.setDescricao(fieldTerceirizadoDescricao.getText());
						rec.setPrecoFornecedor(((RecursoTerceirizado)recursoTerceirizado).getPrecoFornecedor());
						rec.setQuantidade(Integer.parseInt(fieldTerceirizadoQuantidade.getText()));
						PanelSalaTerceirizado salaSelecionada = (PanelSalaTerceirizado) tabAmbientesTerceirizados.getComponent(tabAmbientesTerceirizados.getSelectedIndex());
						salaSelecionada.adicionarRecurso(rec);
						calcularTotalTerceirizado();
						limparTerceirizado();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addTerceirizadoButton;
	}
	
	private JButton getBotaoAdicionarRecurso() {
		addRecursoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addRecursoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addRecursoButton.setVerticalAlignment(SwingConstants.CENTER);
		addRecursoButton.setPreferredSize(new Dimension(20,20));
		addRecursoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recurso != null) {
					try {
						Date dataInicio = formato.parse(formato.format(fieldEquipamentoDataInicio.getDate()));
						Date dataFim = formato.parse(formato.format(fieldEquipamentoDataFim.getDate()));
						RecursoSolicitado rec = new RecursoSolicitado();
						rec.setRecurso(recurso);
						rec.setDataFim(dataFim);
						rec.setDataInicio(dataInicio);
						try {
							rec.setPrecoUnitario(fieldEquipamentoPreco.getValor());
							rec.setPrecoCusto(fieldEquipamentoCusto.getValor());

						} catch(Exception err) {}
						rec.setQuantidade(Integer.parseInt(fieldEquipamentoQuantidade.getText()));
						rec.setDescricao(fieldEquipamentoDescricao.getText());
						PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipamento.getComponent(tabAmbientesEquipamento.getSelectedIndex());
						salaSelecionada.adicionarRecurso(rec);
						calcularTotal();
						limparEquipamento();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addRecursoButton;
	}
	
	private JButton getBotaoAdicionarRecursoHumano() {
		addEquipeTecnicaButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addEquipeTecnicaButton.setHorizontalAlignment(SwingConstants.CENTER);
		addEquipeTecnicaButton.setVerticalAlignment(SwingConstants.CENTER);
		addEquipeTecnicaButton.setPreferredSize(new Dimension(20,20));
		addEquipeTecnicaButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recurso != null) {
					RecursoSolicitado rec = new RecursoSolicitado();
					
					try {
						Date dataInicio = formato.parse(formato.format(fieldEquipeTecnicaDataInicio.getDate()));
						Date dataFim = formato.parse(formato.format(fieldEquipeTecnicaDataFim.getDate()));

						rec.setRecurso(recurso);
						rec.setDataFim(dataFim);
						rec.setDataInicio(dataInicio);
						try {
							rec.setPrecoUnitario(fieldEquipeTecnicaPreco.getValor());
							rec.setPrecoCusto(fieldEquipeTecnicaCusto.getValor());

						} catch(Exception err) {}
						rec.setQuantidade(Integer.parseInt(fieldEquipeTecnicaQuantidade.getText()));
						rec.setDescricao(fieldEquipeTecnicaDescricao.getText());

						PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipeTecnica.getComponent(tabAmbientesEquipeTecnica.getSelectedIndex());
						salaSelecionada.adicionarRecurso(rec);
						calcularTotal();
						
						System.out.println(dataInicio);
						System.out.println(dataFim);
						System.out.println(salaSelecionada.diarias);
						System.out.println(rec.getSubTotal(salaSelecionada.diarias));
						
						limparEquipeTecnica();
						
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(FormOrdemServico.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addEquipeTecnicaButton;
	}
	
	public BigDecimal calcularTotalTerceirizado() {
		DecimalFormat formatador = new DecimalFormat("#,###,###,##0.00");
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		formato.setMaximumIntegerDigits(12);
		BigDecimal total = new BigDecimal(0.00);
		
		if(fieldTerceirizadoEmp.isSelected()){
			for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
				total = total.add(pnt.calcularSubTotal());

			}

		}
		else {
			for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
				total = total.add(pnt.calcularSubTotalFornecedor());

			}

		}
		
		
		fieldPrecoTerceirizado.setText("R$ " + formatador.format(total));
		return total;
	}

	public BigDecimal calcularTotal() {
		DecimalFormat formatador = new DecimalFormat("#,###,###,##0.00");
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		formato.setMaximumIntegerDigits(12);
		BigDecimal total = new BigDecimal(0.00);
		BigDecimal totalCusto = new BigDecimal(0.00);
		BigDecimal desconto = new BigDecimal(0.00);

		for(PanelSala pns: paineisEquipamento.values()) {
			total = total.add(pns.calcularSubTotal());
			totalCusto = totalCusto.add(pns.calcularSubTotalPrecoCusto());
			desconto = desconto.add(pns.getAmbiente().getDescontoEquipamento());
		}
		for(PanelSala pns: paineisEquipeTecnica.values()) {
			total = total.add(pns.calcularSubTotal());
			totalCusto = totalCusto.add(pns.calcularSubTotalPrecoCusto());
		}
		for(PanelSala pns: paineisCenografia.values()) {
			total = total.add(pns.calcularSubTotal());
			totalCusto = totalCusto.add(pns.calcularSubTotalPrecoCusto());
			desconto = desconto.add(pns.getAmbiente().getDescontoCenografia());

		}
		for(PanelSala pns: paineisLogistica.values()) {
			total = total.add(pns.calcularSubTotal());
			totalCusto = totalCusto.add(pns.calcularSubTotalPrecoCusto());
		}
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			desconto = desconto.add(pnt.getAmbiente().getDescontoTerceirizado());
		}

		if(fieldTerceirizadoEmp.isSelected() && ! fieldTerceirizadoFor.isSelected()){
			for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
				total = total.add(pnt.calcularSubTotal());
				totalCusto = totalCusto.add(pnt.calcularSubTotalFornecedor());

			}
		}
				
		fieldPrecoCustoSubtotal.setText("R$ " + formatador.format(totalCusto));
		fieldPrecoSubtotal.setText("R$ " + formatador.format(total));
		total = total.subtract(desconto);
		fieldPrecoTotal.setText("R$ " + formatador.format(total));
		fieldDesconto.setText("R$ " + formatador.format(desconto));
		calcularTotalTerceirizado();
		
		return total;
	}
	
	public BigDecimal calcularTotalSemLogOp() {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		formato.setMaximumIntegerDigits(12);
		BigDecimal total = new BigDecimal(0.00);
		BigDecimal desconto = new BigDecimal(0.00);

		for(PanelSala pns: paineisEquipamento.values()) {
			total = total.add(pns.calcularSubTotal());
			desconto = desconto.add(pns.getAmbiente().getDescontoEquipamento());
		}
		for(PanelSala pns: paineisCenografia.values()) {
			total = total.add(pns.calcularSubTotal());
			desconto = desconto.add(pns.getAmbiente().getDescontoCenografia());

		}
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			desconto = desconto.add(pnt.getAmbiente().getDescontoTerceirizado());
		}

		if(fieldTerceirizadoEmp.isSelected()){
			for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
				total = total.add(pnt.calcularSubTotal());

			}
		}
		
		return total;
	}
	
	private void limparPanelTerceirizado() {
		fieldTerceirizadoGrupo.setSelectedItem(" ");
		fieldTerceirizadoNome.removeAllItems();
		fieldTerceirizadoQuantidade.setText("1");
		fieldTerceirizadoPrecoEmpresa.setValor(new BigDecimal(0));
		fieldTerceirizadoPrecoForn.setValor(new BigDecimal(0));

		fieldTerceirizadoDataInicio.setDate(fieldDataInicio.getDate()) ;
		fieldTerceirizadoDataFim.setDate(fieldDataFim.getDate()) ;
		fieldTerceirizadoDescricao.setText("");
	}

	private void limparPanelCenografia() {
		fieldCenografiaGrupo.setSelectedItem(" ");
		fieldCenografiaNome.removeAllItems();
		fieldCenografiaPreco.setValor(new BigDecimal(0));
		fieldCenografiaCusto.setValor(new BigDecimal(0));

		fieldCenografiaDataInicio.setDate(fieldDataInicio.getDate()) ;
		fieldCenografiaDataFim.setDate(fieldDataFim.getDate()) ;
		fieldCenografiaQuantidade.setText("1");
		fieldCenografiaDescricao.setText("");
	}
	
	private void limparPanelLogistica() {
		fieldLogisticaGrupo.setSelectedItem(" ");
		fieldLogisticaNome.removeAllItems();
		fieldLogisticaPreco.setValor(new BigDecimal(0));
		fieldLogisticaCusto.setValor(new BigDecimal(0));

		fieldLogisticaDataInicio.setDate(fieldDataInicio.getDate()) ;
		fieldLogisticaDataFim.setDate(fieldDataFim.getDate()) ;
		fieldLogisticaQuantidade.setText("1");
		fieldLogisticaDescricao.setText("");
	}
	
	private void limparPanelEquipamento() {
		fieldEquipamentoGrupo.setSelectedItem(" ");
		fieldEquipamentoNome.removeAllItems();
		fieldEquipamentoPreco.setValor(new BigDecimal(0));
		fieldEquipamentoCusto.setValor(new BigDecimal(0));

		fieldEquipamentoDataInicio.setDate(fieldDataInicio.getDate()) ;
		fieldStatusOS.setSelectedItem(StatusOS.PENDENTE);
		fieldEquipamentoDescricao.setText("");
		
	}
	
	private void limparPanelEquipeTecnica() {
		fieldEquipeTecnicaGrupo.setSelectedItem(" ");
		fieldEquipeTecnicaNome.removeAllItems();
		fieldEquipeTecnicaPreco.setValor(new BigDecimal(0));
		fieldEquipeTecnicaCusto.setValor(new BigDecimal(0));

		fieldEquipeTecnicaDataInicio.setDate(fieldDataInicio.getDate()) ;
		fieldStatusOS.setSelectedItem(StatusOS.PENDENTE);
		fieldEquipeTecnicaDescricao.setText("");

	}

	public void carregarCliente(Cliente c) {
		cliente = c;
		if(c!=null){
			fieldClienteID.setText("" + c.getId());
			fieldClienteRazao.setText(c.getNome());
			fieldClienteTelefone.setText(c.getFone());
			fieldClienteContato.setText(c.getContato());
			fieldClienteCelular.setText(c.getCelular());
		} else {
			limparCliente();
		}
	}
	
	public void carregarAgencia(Fornecedor c) {
		agencia = c;
		if(c!=null){
			fieldAgenciaID.setText("" + c.getId());
			fieldAgenciaFornecedor.setText(c.getNome());
		} else {
			limparAgencia();
		}
	}

	private void carregarRecurso(Recurso r) {
		recurso = r;
		if(r!=null){
			fieldEquipamentoPreco.setValor(r.getPrecoSugerido());
			addRecursoButton.requestFocus();
		} else {
			limparEquipamento();
		}
	}

	private void carregarLocal(Local l) {
		if(l!=null){
			local = l;
			fieldEnderecoLogradouro.setText(l.getEndereco().getLogradouro());
			fieldEnderecoNumero.setText(l.getEndereco().getNumero());
			fieldEnderecoComplemento.setText(l.getEndereco().getComplemento());
			fieldEnderecoCEP.setText(l.getEndereco().getCep());
			fieldEnderecoBairro.setText(l.getEndereco().getBairro());
			fieldEnderecoCidade.setText(l.getEndereco().getCidade());
			fieldEnderecoEstado.setSelectedItem(l.getEndereco().getEstado());
			fieldEnderecoReferencia.setText(l.getEndereco().getPontoReferencia());
			fieldLocalID.setText("" + l.getId());
			fieldLocalNome.setText(l.getNome());
			fieldObservacoesLocalEvento.setText(l.getObservacoes());
		} else {
			limparLocal();
		}
	}

	private void limparLocal() {
		local = null;
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("000000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("SP");
		fieldEnderecoReferencia.setText("");
		fieldLocalID.setText("");
		fieldLocalNome.setText("");
		fieldObservacoesLocalEvento.setText("");
	}

	public void limparCliente() {
		cliente = null;
		fieldClienteID.setText("");
		fieldClienteRazao.setText("");
		fieldClienteTelefone.setText("");
		fieldClienteContato.setText("");
		fieldClienteCelular.setText("");
	}
	
	public void limparAgencia() {
		agencia = null;
		fieldAgenciaID.setText("");
		fieldAgenciaFornecedor.setText("");
		//botaoAgencia.setEnabled(true);
		fieldDataVencimento.setDate(new Date());
		//fieldDataVencimento.setEnabled(true);
		fieldPercentualComissao.setText("");
		//fieldPercentualComissao.setEditable(true);
		fieldValorIntegral.setSelected(true);
		//fieldValorIntegral.setEnabled(true);
		//fieldSemLogOp.setEnabled(true);
		fieldTotalComissao.setText("");
	}
	
	public void limparTerceirizado() {
		recursoTerceirizado = null;
		//fieldTerceirizadoGrupo.setSelectedItem(" ");
		//fieldTerceirizadoNome.removeAllItems();
		fieldTerceirizadoPrecoEmpresa.setValor(new BigDecimal(0));
		fieldTerceirizadoPrecoForn.setValor(new BigDecimal(0));

		fieldTerceirizadoDataInicio.setDate(fieldDataInicio.getDate());
		fieldTerceirizadoDataFim.setDate(fieldDataFim.getDate());
		fieldTerceirizadoQuantidade.setText("1");
		//fieldTerceirizadoDataInicio.setMinSelectableDate(fieldDataInicio.getDate());
		//fieldTerceirizadoDataInicio.setMaxSelectableDate(fieldDataFim.getDate());
		fieldTerceirizadoDescricao.setText("");

		//fieldTerceirizadoDataFim.setMinSelectableDate(fieldDataInicio.getDate());
		//fieldTerceirizadoDataFim.setMaxSelectableDate(fieldDataFim.getDate());
		fieldTerceirizadoNome.requestFocus();
	}

	public void limparLogistica() {
		recurso = null;
		//fieldLogisticaGrupo.setSelectedItem(" ");
		//fieldLogisticaNome.removeAllItems();
		fieldLogisticaPreco.setValor(new BigDecimal(0));
		fieldLogisticaCusto.setValor(new BigDecimal(0));

		fieldLogisticaDataInicio.setDate(fieldDataInicio.getDate());
		fieldLogisticaDataFim.setDate(fieldDataFim.getDate());
		fieldLogisticaQuantidade.setText("1");
		fieldLogisticaDescricao.setText("");

		//fieldLogisticaDataInicio.setMinSelectableDate(fieldDataInicio.getDate());
		//fieldLogisticaDataInicio.setMaxSelectableDate(fieldDataFim.getDate());

		//fieldLogisticaDataFim.setMinSelectableDate(fieldDataFim.getDate());
		//fieldLogisticaDataFim.setMaxSelectableDate(fieldDataFim.getDate());
		fieldLogisticaNome.requestFocus();
	}
	
	public void limparCenografia() {
		recurso = null;
		//fieldCenografiaGrupo.setSelectedItem(" ");
		//fieldCenografiaNome.removeAllItems();
		fieldCenografiaPreco.setValor(new BigDecimal(0));
		fieldCenografiaCusto.setValor(new BigDecimal(0));

		//fieldCenografiaDataInicio.setDate(fieldDataInicio.getDate());
		//fieldCenografiaDataFim.setDate(fieldDataFim.getDate());
		fieldCenografiaQuantidade.setText("1");
		fieldCenografiaDescricao.setText("");

		//fieldCenografiaDataInicio.setMinSelectableDate(fieldDataInicio.getDate());
		//fieldCenografiaDataInicio.setMaxSelectableDate(fieldDataFim.getDate());

		//fieldCenografiaDataFim.setMinSelectableDate(fieldDataFim.getDate());
		//fieldCenografiaDataFim.setMaxSelectableDate(fieldDataFim.getDate());
		
		
		if(tabAmbientesCenografia.getSelectedComponent() != null) {
			PanelSala salaSelecionada = (PanelSala) tabAmbientesCenografia.getComponent(tabAmbientesCenografia.getSelectedIndex());
			fieldCenografiaDataInicio.setDate(salaSelecionada.getDataInicial());
			fieldCenografiaDataFim.setDate(salaSelecionada.getDataFinal());
		}
		fieldCenografiaNome.requestFocus();
	}
	
	public void limparSubgrupos() {
		fieldEquipamentoNome.removeAllItems();
		fieldTerceirizadoNome.removeAllItems();
		fieldEquipeTecnicaNome.removeAllItems();
		fieldCenografiaNome.removeAllItems();
		fieldLogisticaNome.removeAllItems();
	}
	
	public void limparEquipamento() {
		recurso = null;
		//fieldEquipamentoGrupo.setSelectedItem(" ");
		//fieldEquipamentoNome.removeAllItems();
		fieldEquipamentoPreco.setValor(new BigDecimal(0));
		fieldEquipamentoCusto.setValor(new BigDecimal(0));

		//fieldEquipamentoDataInicio.setDate(fieldDataInicio.getDate());
		//fieldEquipamentoDataFim.setDate(fieldDataFim.getDate());
		fieldEquipamentoQuantidade.setText("1");
		fieldEquipamentoDescricao.setText("");

		//fieldEquipamentoDataInicio.setMinSelectableDate(fieldDataInicio.getDate());
		//fieldEquipamentoDataInicio.setMaxSelectableDate(fieldDataFim.getDate());

		//fieldEquipamentoDataFim.setMinSelectableDate(fieldDataInicio.getDate());
		//fieldEquipamentoDataFim.setMaxSelectableDate(fieldDataFim.getDate());
		
		
		if(tabAmbientesEquipamento.getSelectedComponent() != null) {
			PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipamento.getComponent(tabAmbientesEquipamento.getSelectedIndex());
			fieldEquipamentoDataInicio.setDate(salaSelecionada.getDataInicial());
			fieldEquipamentoDataFim.setDate(salaSelecionada.getDataFinal());
		}
		
		
		fieldEquipamentoNome.requestFocus();
	}
	
	public void limparEquipeTecnica() {
		recurso = null;
		//fieldEquipeTecnicaGrupo.setSelectedItem(" ");
		//fieldEquipeTecnicaNome.removeAllItems();
		fieldEquipeTecnicaPreco.setValor(new BigDecimal(0));
		fieldEquipeTecnicaCusto.setValor(new BigDecimal(0));

		//fieldEquipeTecnicaDataInicio.setDate(fieldDataMontagem.getDate());
		//fieldEquipeTecnicaDataFim.setDate(fieldDataFim.getDate());
		fieldEquipeTecnicaQuantidade.setText("1");
		fieldEquipeTecnicaDescricao.setText("");

		//fieldEquipeTecnicaDataInicio.setMinSelectableDate(fieldDataMontagem.getDate());
		//fieldEquipeTecnicaDataInicio.setMaxSelectableDate(fieldDataFim.getDate());

		//fieldEquipeTecnicaDataFim.setMinSelectableDate(fieldDataMontagem.getDate());
		//fieldEquipeTecnicaDataFim.setMaxSelectableDate(fieldDataFim.getDate());
		
		if(tabAmbientesEquipeTecnica.getSelectedComponent() != null) {
			PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipeTecnica.getComponent(tabAmbientesEquipeTecnica.getSelectedIndex());
			fieldEquipeTecnicaDataInicio.setDate(salaSelecionada.getDataInicial());
			fieldEquipeTecnicaDataFim.setDate(salaSelecionada.getDataFinal());
		}
		
		fieldEquipeTecnicaNome.requestFocus();
	}

	public JButton getBotaoProcurarCliente() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));

		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchClient teste = new DialogSearchClient(FormOrdemServico.this);
						Cliente c = teste.showDialog(FormOrdemServico.this);
						if(c != null)
							carregarCliente(c);
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	
	public JButton getBotaoAbrirCliente() {
		JButton botaoAbrirCliente = new JButton("", new ImageIcon(getClass().getResource("/images/lente_24.png")));
		botaoAbrirCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setPreferredSize(new Dimension(20,20));

		botaoAbrirCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cliente != null) {
							FormCliente telaCliente = (FormCliente) WindowFactory.createTelaCliente(Facade.getInstance().getTelaPrincipal().getDesktop());
							telaCliente.loadForm(cliente);
							telaCliente.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(FormOrdemServico.this, "Nenhum cliente selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
							
					}
				}	
		);
		return botaoAbrirCliente;
	}

	public JButton getBotaoProcurarRecurso() {
		JButton botaoProcurarRecurso = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarRecurso.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarRecurso.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarRecurso.setPreferredSize(new Dimension(20,20));

		botaoProcurarRecurso.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchRecurso teste = new DialogSearchRecurso(FormOrdemServico.this, Facade.getInstance().listarRecursos());
						Recurso r = teste.showDialog(FormOrdemServico.this);
						if(r != null)
							carregarRecurso(r);
					}
				}	
		);
		return botaoProcurarRecurso;
	}

	public JButton getBotaoProcurarLocal() {
		JButton botaoProcurarLocal = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarLocal.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarLocal.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarLocal.setPreferredSize(new Dimension(20,20));

		botaoProcurarLocal.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchLocal teste = new DialogSearchLocal(FormOrdemServico.this, Facade.getInstance().listarLocais());
						Local l = teste.showDialog(FormOrdemServico.this);
						if(l != null) {
							carregarLocal(l);
							carregarFieldAmbientesEvento(l);
						}

					}
				}	
		);
		return botaoProcurarLocal;
	}

	public OrdemServico newElement() {
		return new OrdemServico();
	}

	public void loadInputFields(OrdemServico os) {
		os.setStatus((StatusOS) fieldStatusOS.getSelectedItem());
		os.setPlanilha(planilha);
		
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		os.setCliente(cliente);
		
		List<RecursoSolicitado> recursos = new ArrayList<RecursoSolicitado>();
		List<RecursoTerceirizadoSolicitado> recursosTerceirizados = new ArrayList<RecursoTerceirizadoSolicitado>();

		for(PanelSala pns: paineisEquipamento.values()) {
			recursos.addAll(pns.getRecursosSolicitados());
		}
		for(PanelSala pns: paineisEquipeTecnica.values()) {
			recursos.addAll(pns.getRecursosSolicitados());
		}
		for(PanelSala pns: paineisCenografia.values()) {
			recursos.addAll(pns.getRecursosSolicitados());
		}
		for(PanelSala pns: paineisLogistica.values()) {
			recursos.addAll(pns.getRecursosSolicitados());
		}
		for(PanelSalaTerceirizado pns: paineisTerceirizados.values()) {
			recursosTerceirizados.addAll(pns.getRecursosTerceirizadosSolicitados());
		}	
		if(fieldVendedorConjunto.getSelectedItem() instanceof Funcionario){
			os.setVendedorConjunto((Funcionario) fieldVendedorConjunto.getSelectedItem());
		}
		
		os.setTerceirizadoEmpresa(fieldTerceirizadoEmp.isSelected());
		os.setTerceirizadoFornecedor(fieldTerceirizadoFor.isSelected());

		os.setAmbientes(modelAmbiente.getAmbientes());
		
		for(PanelSala pns: paineisLogistica.values()) {
			if (pns.getRecursosSolicitados().size() > 0 )
				os.getAmbientes().add(pns.getAmbiente());
		}	
		

		for(PanelSalaTerceirizado pns: paineisTerceirizados.values()) {
			if (pns.getRecursosTerceirizadosSolicitados().size() > 0 )
				os.getAmbientes().add(pns.getAmbiente());
		}
		
		os.setRecursoSolicitado(recursos);
		os.setRecursoTerceirizadoSolicitado(recursosTerceirizados);
		
		os.setNomeProposta(fieldNomeProposta.getText());
		os.setCargoProposta(fieldCargoProposta.getText());
		os.setTelefoneProposta(fieldTelefoneProposta.getText());
		
		os.setNomePropostaConjunta(fieldNomePropostaConjunta.getText());
		os.setCargoPropostaConjunta(fieldCargoPropostaConjunta.getText());
		os.setTelefonePropostaConjunta(fieldTelefonePropostaConjunta.getText());
		
		os.setTelefoneContatoEvento(fieldTelefoneContatoEvento.getText());
		os.setContatoEvento(fieldNomeContatoEvento.getText());
		
		os.setNomeEvento(fieldNomeEvento.getText());
		
		BigDecimal totalEmpresa = new BigDecimal(0);
		BigDecimal totalForn = new BigDecimal(0);
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			totalEmpresa = totalEmpresa.add(pnt.calcularSubTotal());

		}
	
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			totalForn = totalForn.add(pnt.calcularSubTotalFornecedor());

		}
		os.setSubtotalTerceirizadoEmpresa(totalEmpresa);
		os.setSubtotalTerceirizadoForn(totalForn);
		LocalEvento e;

		if(local != null) 
			e = new LocalEvento(local);
		else
			e = new LocalEvento();
		Endereco endereco;
		//if(fieldLocalID.getText() != null) {
		//	Local local = Facade.getInstance().carregarLocal(new Integer(fieldLocalID.getText()));
		//	endereco = local.getEndereco();
		//}
		//else {
			endereco = new Endereco();
			endereco.setLogradouro(fieldEnderecoLogradouro.getText());
			endereco.setNumero(fieldEnderecoNumero.getText());
			endereco.setComplemento(fieldEnderecoComplemento.getText());
			endereco.setCep(fieldEnderecoCEP.getText());
			endereco.setBairro(fieldEnderecoBairro.getText());
			endereco.setCidade(fieldEnderecoCidade.getText());
			endereco.setEstado((String) fieldEnderecoEstado.getSelectedItem());
			endereco.setPontoReferencia(fieldEnderecoReferencia.getText());
		//}
		e.setEndereco(endereco);
		e.setNome(fieldLocalNome.getText());
		e.setObservacoes(fieldObservacoesLocalEvento.getText());
		os.setLocal(e);
		
		os.setHoraInicio((Date) fieldHoraInicio.getValue());
		os.setHoraFim((Date) fieldHoraFim.getValue());
		os.setHoraMontagem((Date) fieldHoraMontagem.getValue());
		
		os.setFuncionario((Funcionario) fieldFuncionario.getSelectedItem());
		os.setDataInicio(fieldDataInicio.getDate());
		os.setDataFim(fieldDataFim.getDate());
		os.setDataMontagem(fieldDataMontagem.getDate());
		os.setObservacoes(fieldObservacoes.getText());
		os.setObservacoesFinanceiras(fieldObservacoesFinanceiras.getText());
		os.setCondicoesPagamento(fieldCondicoesPagamento.getText());
		os.setObservacoesCliente(fieldObservacoesCliente.getText());
		os.setDetalhesEvento(fieldDetalhesEvento.getText());
		
		try {
			
			BigDecimal total = new BigDecimal(formato.parse((fieldPrecoSubtotal.getText()).replace("R$ ", "")).doubleValue());
			BigDecimal totalTerceirizado = new BigDecimal(formato.parse((fieldPrecoTerceirizado.getText()).replace("R$ ", "")).doubleValue());
			BigDecimal desconto = new BigDecimal(formato.parse((fieldDesconto.getText()).replace("R$ ", "")).doubleValue());

			os.setPreco(total);
			os.setPrecoTerceirizado(totalTerceirizado);
			os.setDesconto(desconto);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void loadForm(OrdemServico rec) {
		loadForm2(rec, true);
	}
	
	public void loadForm2(OrdemServico rec, boolean travarBotaoAdicionar) {
		disableDateEvent = true;
		clear();
		
		//fieldConfirmacaoFaturamento.setSelected(rec.isConfirmacaoFaturamento());
		fieldNomeEvento.setText(rec.getNomeEvento());
		fieldFuncionario.setSelectedItem(rec.getFuncionario());
		fieldDataInicio.setDate(rec.getDataInicio());
		fieldDataFim.setDate(rec.getDataFim());
		fieldDataMontagem.setDate(rec.getDataMontagem());
		//fieldObservacoesFaturamento.setText(rec.getObservacoesFaturamento());
		fieldTerceirizadoEmp.setSelected(rec.isTerceirizadoEmpresa());
		fieldTerceirizadoFor.setSelected(rec.isTerceirizadoFornecedor());

		if(rec.getVendedorConjunto() != null){
			fieldVendedorConjunto.setSelectedItem(rec.getVendedorConjunto());
		} else {
			fieldVendedorConjunto.setSelectedIndex(0);
		}
		
		fieldTelefoneContatoEvento.setText(rec.getTelefoneContatoEvento());
		fieldNomeContatoEvento.setText(rec.getContatoEvento());
		
		fieldHoraInicio.setValue(rec.getHoraInicio());
		fieldHoraFim.setValue(rec.getHoraFim());
		fieldHoraMontagem.setValue(rec.getHoraMontagem());
		
		fieldObservacoes.setText(rec.getObservacoes());
		fieldObservacoesFinanceiras.setText(rec.getObservacoesFinanceiras());	
		
		fieldCondicoesPagamento.setText(rec.getCondicoesPagamento());
		fieldObservacoesCliente.setText(rec.getObservacoesCliente());
		
		fieldNomeProposta.setText(rec.getNomeProposta());
		fieldCargoProposta.setText(rec.getCargoProposta());
		fieldTelefoneProposta.setText(rec.getTelefoneProposta());

		fieldNomePropostaConjunta.setText(rec.getNomePropostaConjunta());
		fieldCargoPropostaConjunta.setText(rec.getCargoPropostaConjunta());
		fieldTelefonePropostaConjunta.setText(rec.getTelefonePropostaConjunta());
		
		fieldStatusOS.setSelectedItem(rec.getStatus());
		fieldDetalhesEvento.setText(rec.getDetalhesEvento());
		
		setTitle("Ordem de Serviço - NÚMERO DA OS: " + rec.getId() + " (" + rec.getNomeEvento() + ")");
		
		carregarCliente(rec.getCliente());
		Local localReal = null;
		if(rec.getLocal().getLocal() != null) {
			localReal = Facade.getInstance().carregarLocal(rec.getLocal().getLocal().getId());
		}
		if(localReal != null)
			carregarLocal(localReal);
		else
			carregarLocal(new Local(rec.getLocal()));
		
		carregarFieldAmbientesEvento(localReal);

		List<Receita> parcelas = Facade.getInstance().listarReceitasPorOS(rec);
		modelParcelas.removeAllElements();
		
		for(Receita rc: parcelas) {
			modelParcelas.addElement(rc);
		}
		
		if(parcelas.size() == 0){
			botaoGerarParcelas.setEnabled(true);
		} else {
			botaoGerarParcelas.setEnabled(false);
		}

		for(AmbienteEvento amb: rec.getAmbientes()) {
			if(amb.getNome().equals("LOGÍSTICA"))
				adicionarAmbienteLogistica(amb);
			else if(amb.getNome().equals("TERCEIRIZADOS"))
				adicionarAmbienteTerceirizado(amb);
			else
				adicionarAmbiente(amb);
		}
		
		adicionarAmbienteLogistica();
		adicionarAmbienteTerceirizado();


		for(RecursoSolicitado r: rec.getRecursoSolicitado()) {
			switch(r.getRecurso().getGrupo().getTipoRecurso()) {
				case EQUIPAMENTO:
					PanelSala ps = paineisEquipamento.get(r.getAmbiente().getNome());
					ps.adicionarRecurso(r);
					break;
				case EQUIPE_TECNICA:
					PanelSala psh = paineisEquipeTecnica.get(r.getAmbiente().getNome());
					psh.adicionarRecurso(r);
					break;
				case CENOGRAFIA:
					PanelSala psc = paineisCenografia.get(r.getAmbiente().getNome());
					psc.adicionarRecurso(r);
					break;
				case LOGISTICA:
					
					PanelSala psl = paineisLogistica.get("LOGÍSTICA");
					psl.adicionarRecurso(r);
					break;
				
			default:
				break;
			
			}
			
		}
		
		for(RecursoTerceirizadoSolicitado r: rec.getRecursoTerceirizadoSolicitado()) {
			PanelSalaTerceirizado psl = paineisTerceirizados.get("TERCEIRIZADOS");
			
			psl.adicionarRecurso(r);	
		}
				
		calcularTotal();
		calcularTotalTerceirizado();
		

		
		this.abrirPlanilha(rec.getPlanilha());
		
		ordemServ = rec;
		
		Despesa comissao = Facade.getInstance().buscarDespesaComissao(rec);
		
		if(rec.getDadosAgencia() != null && comissao != null) {
			agencia = rec.getDadosAgencia();
			fieldAgenciaID.setText(""+agencia.getId());
			fieldAgenciaFornecedor.setText(agencia.getNome());
			fieldPercentualComissao.setText(""+rec.getPercentualAgencia());
			//fieldPercentualComissao.setEditable(false);
			fieldDataVencimento.setDate(rec.getVencimentoAgencia());
			//fieldDataVencimento.setEnabled(false);
			//botaoAgencia.setEnabled(false);
			//fieldValorIntegral.setEnabled(false);
			//fieldSemLogOp.setEnabled(false);
			if(!rec.isPrecoIntegralAgencia()){
				fieldValorIntegral.setSelected(false);
				fieldSemLogOp.setSelected(true);

			}
			fieldTotalComissao.setText("R$ "+rec.getTotalAgencia());

		}
		if(travarBotaoAdicionar)
			desabilitarBotaoAdicionar();

		disableDateEvent = false;
	}

	private void abrirPlanilha(String planilha) {
		this.planilha = planilha;
		if(this.planilha != null && ! this.planilha.trim().equals("")) { 
			byte buffer[] = OrdemServico.converterStringPlanilha(planilha);
			pdfController.openDocument(buffer, 0, buffer.length, "teste", null);
		}
	}
	
	protected void clear() {
		limparCliente();
		limparAgencia();
		limparEquipamento();
		limparEquipeTecnica();
		limparLogistica();
		limparCenografia();
		limparTerceirizado();
		limparParcela();
		limparSubgrupos();
		
		habilitarBotaoAdicionar();
		
		fieldDesconto.setText( "R$ 0,00");
		parcelasRemovidas = new ArrayList<Receita>();
		
		limparLocal();
		fieldAmbientesEvento.removeAllItems();
		modelAmbiente.removeAllElements();
		paineisEquipamento.clear();
		paineisEquipeTecnica.clear();	
		paineisTerceirizados.clear();
		paineisCenografia.clear();
		paineisLogistica.clear();
		
		while(tabAmbientesEquipamento.getTabCount() > 0) {
			tabAmbientesEquipamento.remove(0);
		}
		
		while(tabAmbientesEquipeTecnica.getTabCount() > 0) {
			tabAmbientesEquipeTecnica.remove(0);
		}
		while(tabAmbientesCenografia.getTabCount() > 0) {
			tabAmbientesCenografia.remove(0);
		}
		while(tabAmbientesLogistica.getTabCount() > 0) {
			tabAmbientesLogistica.remove(0);
		}
		while(tabAmbientesTerceirizados.getTabCount() > 0) {
			tabAmbientesTerceirizados.remove(0);
		}
		
		fieldVendedorConjunto.setSelectedIndex(0);	
		//fieldObservacoes.setText("");
		fieldObservacoesFinanceiras.setText("");
		fieldDetalhesEvento.setText("");
		fieldNomeEvento.setText("");
		fieldDataInicio.setDate(null);
		fieldDataFim.setDate(null);
		fieldDataMontagem.setDate(null);
		fieldCondicoesPagamento.setText("");
		fieldNomeProposta.setText(Facade.getInstance().getUsuarioLogado().getFuncionario().getNome());
		fieldCargoProposta.setText(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getNome());
		fieldTelefoneProposta.setText(Facade.getInstance().getUsuarioLogado().getFuncionario().getCelular());
		fieldNomePropostaConjunta.setText("");
		fieldCargoPropostaConjunta.setText("");
		fieldTelefonePropostaConjunta.setText("");
		fieldObservacoesCliente.setText("");
		fieldHoraInicio.setValue(horaInicial);
		fieldHoraFim.setValue(horaInicial);
		fieldHoraMontagem.setValue(horaInicial);
		
		fieldTelefoneContatoEvento.setText("");
		fieldNomeContatoEvento.setText("");
		
		fieldPrecoTotal.setText( "R$ 0,00");
		tabDetalhes.setSelectedIndex(0);

		fieldStatusOS.setSelectedItem(StatusOS.PENDENTE);
		modelParcelas.removeAllElements();
		fieldQuantidadeParcela.setText("");
		fieldDataPrimeiraParcela.setDate(null);
		fieldEmpenhoParcelas.setSelected(false);
		fieldObservacoes.setText("");
		fieldNomeEvento.setText("");
		//fieldObservacoesFaturamento.setText("");
		ordemServ = null;
		planilha = null;
		pdfController.closeDocument();
		setTitle("Ordem de Serviço");
		botaoGerarParcelas.setEnabled(true);
		fieldDataInicio.setMaxSelectableDate(null);
		fieldDataInicio.setMinSelectableDate(null);
		fieldDataFim.setMaxSelectableDate(null);
		fieldDataFim.setMinSelectableDate(null);
		fieldDataMontagem.setMaxSelectableDate(null);
		fieldDataMontagem.setMinSelectableDate(null);
	}

	protected boolean validateFormInsert() {
		if(ordemServ != null && ordemServ.getId() != 0) {
			JOptionPane.showMessageDialog(this, "ATENÇÃO:\nNão é possível duplicar uma Ordem de Serviço.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return false;
		} else
			return validateFormUpdate();
	}
	
	private BigDecimal calcularTotalParcelas() {
		List<Receita> parcelas = modelParcelas.getListaReceita();
		BigDecimal totalParcelas = new BigDecimal(0);
		for(Receita r : parcelas) {
			totalParcelas = totalParcelas.add(r.getValor());
		}
		return totalParcelas;
	}

	protected boolean validateFormUpdate() {
		boolean test = true;
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		String error = "ATENÇÃO:";
		if(cliente == null) {
			test = false;
			error += "\nSelecione o Cliente";

		}
		if(fieldDataInicio.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Início do Evento";			

		}
		if(fieldDataFim.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Fim do Evento";			

		}
		if(fieldDataMontagem.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Montagem do Evento";			
		}
		
		boolean possuiRecurso = false;
		boolean possuiTerceirizado = false;
		List<RecursoSolicitado> lista = new ArrayList<RecursoSolicitado>();
		for(PanelSala pns: paineisEquipamento.values()) {
			lista.addAll(pns.getRecursosSolicitados());
			if(pns.getRecursosSolicitados().size()>0) {
				possuiRecurso = true;
				break;
			}
		}
		for(PanelSala pns: paineisCenografia.values()) {
			lista.addAll(pns.getRecursosSolicitados());
			if(pns.getRecursosSolicitados().size()>0) {
				possuiRecurso = true;
				break;
			}
		}
		for(PanelSala pns: paineisEquipeTecnica.values()) {
			lista.addAll(pns.getRecursosSolicitados());
			if(pns.getRecursosSolicitados().size()>0) {
				possuiRecurso = true;
				break;
			}
		}
		for(PanelSalaTerceirizado pns: paineisTerceirizados.values()) {
			
			if(pns.getRecursosTerceirizadosSolicitados().size()>0) {
				possuiRecurso = true;
				possuiTerceirizado = true;
				break;
			}
		}
		
		if(possuiTerceirizado) {
			if(fieldTerceirizadoEmp.isSelected() && fieldTerceirizadoFor.isSelected()) {
				test = false;
				error += "\nSelecione apenas uma forma de fechamento dos terceirizados";
			}
			if(!fieldTerceirizadoEmp.isSelected() && !fieldTerceirizadoFor.isSelected()) {
				test = false;
				error += "\nSelecione uma forma de fechamento dos terceirizados";
			}
		}
		
		
		if(!possuiRecurso){
			test = false;
			error += "\nAdicione pelo menos um recurso";
		}
		
		boolean possuiLogistica = false;
		
		for(PanelSala pns: paineisLogistica.values()) {
			lista.addAll(pns.getRecursosSolicitados());
			if(pns.getRecursosSolicitados().size()>0) {
				possuiLogistica = true;
				break;
			}
		}
		
		if(!possuiLogistica){
			test = false;
			error += "\nAdicione pelo menos ítem de logística";
		}		
		
		
		boolean possuiPalco = false;
		boolean possuiEscada = false;
		boolean possuiCarpete = false;
		for(RecursoSolicitado r: lista) {
			String nome = r.getDescricao().toLowerCase().trim();
			if(nome.startsWith("palco"))
				possuiPalco = true;
			if(nome.contains("escada"))
				possuiEscada = true;
			if(nome.contains("carpete"))
				possuiCarpete = true;			
		}
		
		if(possuiPalco)
			if(!possuiEscada || !possuiCarpete) {
				test = false;
				error += "\nQuando se adiciona o palco, é obrigatória a inclusão de escada e carpete";
			}
		
		
		if(ordemServ != null && ordemServ.getId() != 0 && ordemServ.getStatus() == StatusOS.ESTORNADA) {
			error += "\nNão é possível alterar uma Ordem de Serviço Estornada.";
			test = false;
		}
		
		if(calcularTotalParcelas().compareTo(calcularTotal()) != 0) {
			error += "\nO valor total das parcelas não corresponde ao valor total do evento.\n Total do evento: " + calcularTotal() + "\n Total das Parcelas: " + calcularTotalParcelas();
			System.out.println(calcularTotalParcelas() + " ?= " + calcularTotal());
			test = false;
		}
		try {
			BigDecimal precoSubTotal = new BigDecimal((Long) formato.parse((fieldPrecoSubtotal.getText()).replace("R$ ", "").replace(".", "").replace(",", ".")));
			BigDecimal precoCustoSt = new BigDecimal((Long) formato.parse((fieldPrecoCustoSubtotal.getText()).replace("R$ ", "").replace(".", "").replace(",", ".")));
			if(precoSubTotal.compareTo(precoCustoSt) < 0) {
				test = false;
				error += "\nO subtotal é menor do que o valor de custo";			
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(agencia != null) {
			try {
				new Integer(fieldPercentualComissao.getText());
			} catch (Exception e) {
				test = false;
				error += "\nEspecifique o percentual da comissão ";
			}
			
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
		
	}

	public List<OrdemServico> listAll() {
		List<OrdemServico> lista;
		List<OrdemServico> listaSExtras = new ArrayList<OrdemServico>();
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<OrdemServico>();
		}
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODAS_OS)){
			lista = Facade.getInstance().listarOrdemServicos(campo, texto);
		} else {
			lista = Facade.getInstance().listarOrdemServicos(campo,texto,Facade.getInstance().getUsuarioLogado().getFuncionario());
		}
		for(OrdemServico os: lista) {
			if(os.getOSOriginal() == null)
				listaSExtras.add(os);
		}
		return listaSExtras;
	}

	public Date getDataInicial() {
		if(fieldDataInicio != null)
			return fieldDataInicio.getDate();
		return null;
	}

	public Date getDataFinal() {
		if(fieldDataFim != null)
			return fieldDataFim.getDate();		
		return null;
	}
	
	private void carregarFieldAmbientesEvento(Local local) {
		fieldAmbientesEvento.removeAllItems();
		fieldAmbientesEvento.addItem("Nenhum");
		if(local != null) {
			if(local.getSalaLocals() != null) {
				for(SalaLocal rec: local.getSalaLocals()) {
					fieldAmbientesEvento.addItem(rec);
					
				}
				
			}
		}
		fieldAmbientesEvento.addItemListener(new ItemListener() {
			@SuppressWarnings("deprecation")
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldAmbientesEvento.getSelectedIndex() != 0) {
					SalaLocal salaLocal = (SalaLocal)fieldAmbientesEvento.getSelectedItem();
					if(salaLocal != null) {
						DecimalFormat df2 = new DecimalFormat("#.##");
						fieldAmbienteNome.setText(((SalaLocal)fieldAmbientesEvento.getSelectedItem()).getNome());
						fieldAmbientePFixacao.setText(new Integer(salaLocal.getPontosFixacaoAerea()).toString());
						fieldAmbientePeDireito.setText(String.format("%.2f",salaLocal.getPeDireito())+" m");
						fieldAmbienteComprimento.setText(String.format("%.2f",salaLocal.getComprimento())+" m");
						fieldAmbienteLargura.setText(String.format("%.2f",salaLocal.getLargura())+" m");
						fieldAmbienteArea.setText(String.format("%.2f",salaLocal.getAreaTotal())+" m");
					}
	        	}
				else {
					fieldAmbienteNome.setEditable(true);
					fieldAmbienteNome.setText("");
					fieldAmbientePFixacao.setText("");
					fieldAmbientePeDireito.setText("");
					fieldAmbienteComprimento.setText("");
					fieldAmbienteComprimento.setText("");
					fieldAmbienteLargura.setText("");
					fieldAmbienteArea.setText("");
				}
	        }
	    });
	}
	
	private void carregarFieldLogistica() {
		fieldLogisticaGrupo.removeAllItems();
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		fieldLogisticaGrupo.addItem(" ");
		for(Grupo rec: lista) {
			if(rec.getTipoRecurso() == TipoRecurso.LOGISTICA) {
				fieldLogisticaGrupo.addItem(rec);
			}
		}
		
		fieldLogisticaGrupo.setSelectedItem(" ");
		
		fieldLogisticaGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldLogisticaGrupo.getSelectedItem() != " " && fieldLogisticaGrupo.getSelectedItem() != null) {
	        		carregarFieldLogisticaNome();
	        	}
	        }
	    });
	}
	
	private void carregarFieldTerceirizadoGrupo() {
		fieldTerceirizadoGrupo.removeAllItems();
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		fieldTerceirizadoGrupo.addItem(" ");
		for(Grupo rec: lista) {
			if(rec.getTipoRecurso() == TipoRecurso.TERCEIRIZADO) {
				fieldTerceirizadoGrupo.addItem(rec);
			}
		}
		
		fieldTerceirizadoGrupo.setSelectedItem(" ");
		
		fieldTerceirizadoGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldTerceirizadoGrupo.getSelectedItem() != " " && fieldTerceirizadoGrupo.getSelectedItem() != null) {
	        		carregarFieldTerceirizadoNome();
	        	}
	        }
	    });
	}
	
	private void carregarFieldTerceirizadoNome() {
		fieldTerceirizadoNome.removeAllItems();
		fieldTerceirizadoNome.addItem(" ");
		for(RecursoTerceirizado rec: listaRecursosTerceirizados) {
			if(rec.getGrupo().getId() == ((Grupo)fieldTerceirizadoGrupo.getSelectedItem()).getId()){
				fieldTerceirizadoNome.addItem(rec);
			}
			
		}
		
		fieldTerceirizadoNome.setSelectedItem(" ");
		
		fieldTerceirizadoNome.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldTerceirizadoNome.getSelectedItem() != " " && fieldTerceirizadoNome.getSelectedItem() != null) {
		        	RecursoTerceirizado r = (RecursoTerceirizado) fieldTerceirizadoNome.getSelectedItem();
		        	fieldTerceirizadoPrecoEmpresa.setValor(r.getPrecoEmpresa());
					fieldTerceirizadoPrecoForn.setValor(r.getPrecoFornecedor());

		        	fieldTerceirizadoDescricao.setText(r.getNome());
		        	recursoTerceirizado = r;
					addTerceirizadoButton.requestFocus();
	        	}
	        }
	    });
	}
	
	private void carregarFieldLogisticaNome() {
		fieldLogisticaNome.removeAllItems();
		fieldLogisticaDiaria.removeAllItems();
		fieldLogisticaNome.addItem(" ");
		fieldLogisticaDiaria.addItem(" ");

		for(Recurso rec: listaRecursos) {
			if(rec.getGrupo().getId() == ((Grupo)fieldLogisticaGrupo.getSelectedItem()).getId()) {
				if(rec.isCalcularDiarias()) {
					fieldLogisticaDiaria.addItem(rec);
				}
				else {
					fieldLogisticaNome.addItem(rec);

				}
			}
			
		}
		
		fieldLogisticaDiaria.setSelectedItem(" ");
		fieldLogisticaNome.setSelectedItem(" ");
		
		fieldLogisticaNome.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldLogisticaNome.getSelectedItem() != " " && fieldLogisticaNome.getSelectedItem() != null) {
		        	Recurso r = (Recurso) fieldLogisticaNome.getSelectedItem();
		        	fieldLogisticaPreco.setValor(r.getPrecoSugerido());
					fieldLogisticaCusto.setValor(r.getPrecoCusto());

		        	fieldLogisticaDescricao.setText(r.getNome());
		        	recurso = r;
					addLogisticaButton.requestFocus();
	        	}
	        }
	    });
		
		fieldLogisticaDiaria.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldLogisticaDiaria.getSelectedItem() != " " && fieldLogisticaDiaria.getSelectedItem() != null) {
		        	Recurso r = (Recurso) fieldLogisticaDiaria.getSelectedItem();
		        	fieldLogisticaPreco.setValor(r.getPrecoSugerido());
		        	fieldLogisticaDescricao.setText(r.getNome());
		        	recurso = r;
					addLogisticaButton.requestFocus();
	        	}
	        }
	    });
	}	
	
	private void carregarFieldCenografia() {
		fieldCenografiaGrupo.removeAllItems();
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		fieldCenografiaGrupo.addItem(" ");
		for(Grupo rec: lista) {
			if(rec.getTipoRecurso() == TipoRecurso.CENOGRAFIA) {
				fieldCenografiaGrupo.addItem(rec);
			}
		}
		
		fieldCenografiaGrupo.setSelectedItem(" ");
		
		fieldCenografiaGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldCenografiaGrupo.getSelectedItem() != " " && fieldCenografiaGrupo.getSelectedItem() != null) {
	        		carregarFieldCenografiaNome();
	        	}
	        }
	    });
	}
	
	private void carregarFieldCenografiaNome() {
		fieldCenografiaNome.removeAllItems();
		fieldCenografiaNome.addItem(" ");
		for(Recurso rec: listaRecursos) {
			if(rec.getGrupo().getId() == ((Grupo)fieldCenografiaGrupo.getSelectedItem()).getId()) {
				fieldCenografiaNome.addItem(rec);
			}
			
		}
		
		fieldCenografiaNome.setSelectedItem(" ");
		
		fieldCenografiaNome.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldCenografiaNome.getSelectedItem() != " " && fieldCenografiaNome.getSelectedItem() != null) {
		        	Recurso r = (Recurso) fieldCenografiaNome.getSelectedItem();
		        	fieldCenografiaPreco.setValor(r.getPrecoSugerido());
					fieldCenografiaCusto.setValor(r.getPrecoCusto());

		        	fieldCenografiaDescricao.setText(r.getNome());
		        	recurso = r;
					addCenografiaButton.requestFocus();
	        	}
	        }
	    });
	}	

	private void carregarFieldFuncionario() {
		fieldFuncionario.removeAllItems();
		List<Funcionario> lista = Facade.getInstance().listarFuncionarios();
		fieldFuncionario.removeAllItems();
		fieldVendedorConjunto.removeAllItems();
		fieldVendedorConjunto.addItem("Ninguém");
		fieldFuncionario.addItem(" ");
		for(Funcionario rec: lista) {
			fieldFuncionario.addItem(rec);
			fieldVendedorConjunto.addItem(rec);
		}

		fieldFuncionario.setSelectedItem((Funcionario) Facade.getInstance().getUsuarioLogado().getFuncionario());
	
		fieldVendedorConjunto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldVendedorConjunto.getSelectedItem() instanceof Funcionario) {
					Funcionario f = (Funcionario)fieldVendedorConjunto.getSelectedItem();
					fieldNomePropostaConjunta.setText(f.getNome());
					if(f.getFuncao() != null)
						fieldCargoPropostaConjunta.setText(f.getFuncao().toString());
					fieldTelefonePropostaConjunta.setText(f.getCelular());
				}
				else {
					fieldNomePropostaConjunta.setText("");
					fieldCargoPropostaConjunta.setText("");
					fieldTelefonePropostaConjunta.setText("");
				}
			}
		}
		);
	}


	public boolean print(OrdemServico current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(ordemServ != null) {
			hm.put("id", ordemServ.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			hm.put("cidade",Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
			ImageIcon logos =  new ImageIcon(getClass().getResource("/images/logos.png"));
			hm.put("logos", logos.getImage());
			try {
				Connection c  = Facade.getInstance().getConnection();
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordem_servico.jasper");  
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
				JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);   
				JasperViewer.viewReport(impressao,false);
				c.close();
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}

	private void carregarFieldStatus() {
		fieldStatusOS.removeAllItems();
		fieldStatusOS.addItem("");
		for(StatusOS st : StatusOS.values()) {
			if(st != StatusOS.OS_EMERGENCIAL && st != StatusOS.OS_EMERGENCIAL_CONCLUIDA && st != StatusOS.OS_EMERGENCIAL_INICIADA)
				fieldStatusOS.addItem(st);
			
		}
		fieldStatusOS.setSelectedItem(StatusOS.PENDENTE);
	}

}