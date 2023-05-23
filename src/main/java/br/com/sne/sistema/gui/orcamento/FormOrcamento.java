package br.com.sne.sistema.gui.orcamento;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
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
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import javax.swing.JToggleButton;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.ibm.icu.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RecursoTerceirizado;
import br.com.sne.sistema.bean.RecursoTerceirizadoSolicitado;
import br.com.sne.sistema.bean.SalaLocal;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.SituacaoOrcamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.local.DialogSearchLocal;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.BordedPanel;
import br.com.sne.sistema.gui.util.components.DateCellRenderer;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorBounded;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.components.ToggleStatus;
import br.com.sne.sistema.gui.util.form.DateChooserDialog;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;
import br.com.sne.sistema.gui.util.form.DialogSearchOrcVirouOS;
import br.com.sne.sistema.gui.util.form.FormIntervalar;
import br.com.sne.sistema.gui.util.form.ZebraDecorator;

public class FormOrcamento extends DefaultForm<Orcamento, OrcamentoTableModel> implements FormIntervalar, Nota {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	private Cliente cliente = null;
	private Recurso recurso = null;
	private RecursoTerceirizado recursoTerceirizado = null;
	List<Recurso> listaRecursos = null;

	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;
	private JTextField fieldClienteTelefone;
	private JTextField fieldClienteContato;
	private JTextField fieldClienteCelular;
	
	private JTextField fieldNomeProposta;
	private JTextField fieldCargoProposta;
	private JTextField fieldTelefoneProposta;
	private JTextField fieldNomePropostaConjunta;
	private JTextField fieldCargoPropostaConjunta;
	private JTextField fieldTelefonePropostaConjunta;
	private JTextField fieldCondicoesPagamento;
	private JTextArea fieldObservacoesCliente;

	private JTextField fieldNomeEvento;
	
	@SuppressWarnings("rawtypes")
	private JComboBox fieldFuncionario;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldVendedorConjunto;
	private JDateChooser fieldDataInicio;
	private JDateChooser fieldDataFim;
	private JDateChooser fieldDataMontagem;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldAmbientesEvento;
	private JSpinner fieldHoraInicio;
	private JSpinner fieldHoraFim;
	private JSpinner fieldHoraMontagem;
	private JTextArea fieldObservacoes;
	private JTextArea fieldObservacoesLocalEvento;
	private JTextArea fieldObservacoesFinanceiras;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldTerceirizadoNome;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldTerceirizadoGrupo;
	private JTextField fieldTerceirizadoQuantidade;
	private JMoedaRealTextField fieldTerceirizadoPrecoEmpresa;
	private JMoedaRealTextField fieldTerceirizadoPrecoForn;

	private JDateChooser fieldTerceirizadoDataInicio;
	private JDateChooser fieldTerceirizadoDataFim;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldLogisticaNome;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldLogisticaGrupo;
	private JMoedaRealTextField fieldLogisticaPreco;
	private JTextField fieldLogisticaQuantidade;
	private JDateChooser fieldLogisticaDataInicio;
	private JDateChooser fieldLogisticaDataFim;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldEquipamentoNome;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldEquipeTecnicaNome;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldCenografiaNome;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldEquipamentoGrupo;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldEquipeTecnicaGrupo;
	@SuppressWarnings("rawtypes")
	private JComboBox fieldCenografiaGrupo;
	private JMoedaRealTextField fieldEquipamentoPreco;
	private JMoedaRealTextField fieldEquipeTecnicaPreco;
	private JMoedaRealTextField fieldCenografiaPreco;
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
	@SuppressWarnings("rawtypes")
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;
	private JTextArea fieldDetalhesEvento;
	private JTextField fieldLocalID;
	private JTextField fieldLocalNome;
	private JPopupMenu menuTabela;
	private JTabbedPane tabDetalhes;
	private JTabbedPane tabAmbientesEquipamento;
	private JTabbedPane tabAmbientesEquipeTecnica;
	private JTabbedPane tabAmbientesCenografia;
	private JTabbedPane tabAmbientesLogistica;
	private JTabbedPane tabAmbientesTerceirizados;
	private JLabel fieldPrecoTotal;
	private JLabel fieldPrecoSubtotal;
	private JLabel fieldPrecoCustoSubtotal;
	private JLabel fieldDesconto;
	private JTextField fieldAmbienteNome;
	private JDateChooser fieldAmbienteDataInicio;
	private JDateChooser fieldAmbienteDataFim;
	private JTable tabelaAmbiente;
	private AmbienteTableModel modelAmbiente;
	private JButton botaoAdicionarAmbiente;
	private JButton addEquipamentoButton;
	private JButton addEquipeTecnicaButton;
	private JButton addCenografiaButton;
	private JButton addTerceirizadoButton;
	private JButton addLogisticaButton;
	private JPanel jPanelSituacao;
	private JToggleButton botaoSituacaoAberto;
	private JToggleButton botaoSituacaoFechado;
	private JToggleButton botaoSituacaoCancelado;

	private JComboBox<SituacaoOrcamento> fieldStatusProposta;
	private JLabel fieldPrecoTerceirizado;
	private List<RecursoTerceirizado> listaRecursosTerceirizados;
	private JCheckBox fieldTerceirizadoEmp;
	private JCheckBox fieldTerceirizadoFor;

	private JMoedaRealTextField fieldCenografiaCusto;
	private JMoedaRealTextField fieldLogisticaCusto;
	private JMoedaRealTextField fieldEquipeTecnicaCusto;
	private JMoedaRealTextField fieldEquipamentoCusto;
	
	private JTextField fieldCenografiaDescricao;
	private JTextField fieldLogisticaDescricao;
	private JTextField fieldEquipeTecnicaDescricao;
	private JTextField fieldTerceirizadoDescricao;
	private JTextField fieldEquipamentoDescricao;
	private JTextField fieldAmbientePFixacao;
	private JTextField fieldAmbientePeDireito;
	private JTextField fieldAmbienteLargura;
	private JTextField fieldAmbienteComprimento;
	private JTextField fieldAmbienteArea;

	private HashMap<String, PanelSala> paineisEquipamento;
	private HashMap<String, PanelSala> paineisEquipeTecnica;
	private HashMap<String, PanelSala> paineisCenografia;
	private HashMap<String, PanelSala> paineisLogistica;
	private HashMap<String, PanelSalaTerceirizado> paineisTerceirizados;
	
	private JRadioButton orcNormalButton;
	private JRadioButton orcExtraButton;
	private JButton botaoOrcamento;

	private Orcamento orcOriginal = null;
	private Orcamento orc = null;
	private Local local = null;

	Date horaInicial = new Date(1000*60*3*60);
	
	private boolean disableDateEvent = false;

	public FormOrcamento() {
		super(new OrcamentoTableModel(), "/images/icon_orcamento_18.png", "Orçamentos");
		clear();
	}
	
	public JPanel carregarOrcButton() {
		ButtonGroup grupo = new ButtonGroup();
		JPanel panel = new JPanel();
		
		orcNormalButton = new JRadioButton("Normal");
		orcExtraButton = new JRadioButton("Extra");
				
		grupo.add(orcNormalButton);
		grupo.add(orcExtraButton);
				
		orcNormalButton.setSelected(true);
		
		orcExtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(orcExtraButton.isSelected()) {
					fieldNomeEvento.setEditable(false);
					fieldFuncionario.setEditable(false);
					fieldVendedorConjunto.setEditable(false);
					fieldDataInicio.setEnabled(false);
					fieldDataFim.setEnabled(false);
					fieldDataMontagem.setEnabled(false);
					
					fieldClienteID.setEditable(false);
					fieldClienteTelefone.setEditable(false);
					fieldClienteContato.setEditable(false);
					fieldClienteCelular.setEditable(false);
					
					fieldClienteRazao.setEditable(false);
					
					fieldAmbientePFixacao.setEditable(false);
					fieldAmbientePeDireito.setEditable(false);
					fieldAmbienteLargura.setEditable(false);
					fieldAmbienteComprimento.setEditable(false);
					fieldAmbienteArea.setEditable(false);
					fieldLocalID.setEditable(false);

					botaoOrcamento.setEnabled(true);

					fieldHoraInicio.setEnabled(false);
					fieldHoraFim.setEnabled(false);
					fieldHoraMontagem.setEnabled(false);
					fieldFuncionario.setEnabled(false);
					fieldDetalhesEvento.setEditable(false);


				}
				
			}
		}	
		);
		
		orcNormalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(orcNormalButton.isSelected()) {
					fieldNomeEvento.setEditable(true);
					fieldFuncionario.setEditable(true);
					fieldVendedorConjunto.setEditable(true);
					fieldDataInicio.setEnabled(true);
					fieldDataFim.setEnabled(true);
					fieldDataMontagem.setEnabled(true);
					
					fieldClienteID.setEditable(true);
					fieldClienteTelefone.setEditable(true);
					fieldClienteContato.setEditable(true);
					fieldClienteCelular.setEditable(true);
					
					fieldClienteRazao.setEditable(true);
					
					fieldAmbientePFixacao.setEditable(true);
					fieldAmbientePeDireito.setEditable(true);
					fieldAmbienteLargura.setEditable(true);
					fieldAmbienteComprimento.setEditable(true);
					fieldAmbienteArea.setEditable(true);
					fieldLocalID.setEditable(true);

					botaoOrcamento.setEnabled(false);

					fieldHoraInicio.setEnabled(true);
					fieldHoraFim.setEnabled(true);
					fieldHoraMontagem.setEnabled(true);
					fieldFuncionario.setEnabled(true);
					fieldDetalhesEvento.setEditable(true);

				}
				
			}
		}	
		);
		
		panel.add(orcNormalButton);
		panel.add(orcExtraButton);

		return panel;
	}

	private JButton getBotaoImportarOrcamento() {
		botaoOrcamento = new JButton("Importar Orçamento" , new ImageIcon(getClass().getResource("/images/Money-18x18.png")));
		botaoOrcamento.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchOrcVirouOS teste = new DialogSearchOrcVirouOS(FormOrcamento.this);
						Orcamento o = teste.showDialog(FormOrcamento.this);
						if(o != null) {						
							carregarOrcamento(o);
						}
					}
				}
		);
		return botaoOrcamento;
	}
	
	public void carregarOrcamento(Orcamento c) {
		orcOriginal = c;
		if(c!=null){
			fieldClienteID.setText("" + c.getCliente().getId());
			fieldClienteRazao.setText(c.getCliente().getNome());
			fieldClienteTelefone.setText(c.getCliente().getFone());
			fieldClienteContato.setText(c.getCliente().getContato());
			fieldClienteCelular.setText(c.getCliente().getCelular());
			
			fieldNomeEvento.setText("EXTRAS " + c.getNomeEvento());
			fieldFuncionario.setSelectedItem(c.getFuncionario());
			fieldDataInicio.setDate(c.getDataInicio());
			fieldHoraInicio.setValue(c.getHoraInicio());
			fieldDataFim.setDate(c.getDataFim());
			fieldHoraFim.setValue(c.getHoraFim());
			fieldDataMontagem.setDate(c.getDataMontagem());
			fieldHoraMontagem.setValue(c.getHoraMontagem());
			fieldDetalhesEvento.setText(c.getDetalhesEvento());
			cliente = c.getCliente();
			local = c.getLocal().getLocal();
			
			for(AmbienteEvento amb: c.getAmbientes()) {
				AmbienteEvento novo = new AmbienteEvento(amb.getNome(),amb.getDataInicio(),amb.getDataFim());
				
				if(amb.getNome().equals("LOGÍSTICA"))
					adicionarAmbienteLogistica(novo);
				else if(amb.getNome().equals("TERCEIRIZADOS"))
					adicionarAmbienteTerceirizado(novo);
				else
					adicionarAmbiente(novo);
			}
			
			adicionarAmbienteLogistica();
			adicionarAmbienteTerceirizado();
			
			carregarLocal(c.getLocal().getLocal(),c.getLocal());

		} else {
			limparCliente();
		}
	}
	
	public boolean save(Orcamento current) {
		boolean s = false;
		try {
			
			if(current.getDataInicio().compareTo(new Date()) < 0){
				int resp = JOptionPane.showConfirmDialog(null,"A data de início do orçamento é anterior a data atual.\n"
						+ "Tem certeza que deseja cadastrar um orçamento com a data anterior?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
				if(resp != JOptionPane.OK_OPTION){
					return false;
				}
			}
			
			if(orc != null) {
				//current = new Orcamento(current);
				//current = new Orcamento();
				loadInputFields(current);
				for(AmbienteEvento amb: current.getAmbientes()){
					amb.setId(0);
				}
				for(RecursoSolicitado recs: current.getRecursoSolicitado()) {
					recs.setId(0);
				}
				for(RecursoTerceirizadoSolicitado rcts: current.getRecursoTerceirizadoSolicitado()) {
					rcts.setId(0);
				}
				
				current.getLocal().setId(0);
			}
			current.setGerouOrdemServico(false);
			current.setDataOrcamento(new Date());
			
			if(current.getLocal().getLocal()!=null)
				if(current.getLocal().getLocal().getId() == 0)
					current.getLocal().setLocal(null);
			
			current.setMaisAtual(true);
			
			Facade.getInstance().salvarOrcamento(current);
			orc = current;
			
			int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir o Orçamento ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
			if( resp == JOptionPane.OK_OPTION) {
				print(orc);
				s = false;
			} else {
				s = true;
			}
			clear();
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Orcamento já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Orcamento current) {
		boolean s = true;
		System.out.println("id 1: " + current.getId());
		current = new Orcamento(current);
		System.out.println("id 2: " + current.getId());
		
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

		current.setDataOrcamento(new Date());
		
		
		if(current.getLocal().getLocal()!=null)
				if(current.getLocal().getLocal().getId() == 0)
					current.getLocal().setLocal(null);
		
		/*if(orc != null) {
			current = new Orcamento(current);
			loadInputFields(current);
			for(AmbienteEvento amb: current.getAmbientes()){
				amb.setId(0);
			}
			for(RecursoSolicitado recs: current.getRecursoSolicitado()) {
				recs.setId(0);
			}
			for(RecursoTerceirizadoSolicitado rcts: current.getRecursoTerceirizadoSolicitado()) {
				rcts.setId(0);
			}			
			current.getLocal().setId(0);
		}*/
		
		List<Orcamento> lista = Facade.getInstance().listarOrcamentos(current.getIdPai());

		for(Orcamento o : lista) {
			o.setMaisAtual(false);
			Facade.getInstance().atualizarOrcamento(o);
		}
		current.setMaisAtual(true);
		
		
		Facade.getInstance().salvarOrcamento(current);
		System.out.println("id 3: " + current.getId());
		
		int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir o Orçamento ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
		if( resp == JOptionPane.OK_OPTION) {
			orc = current;
			print(current);
			
			s = false;
		} 
		
		
		return s;
	}

	public boolean remove(Orcamento current) {
		boolean test = false;
		try {
			Facade.getInstance().removerOrcamento(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Orçamento. Verifique se existem Recursos cadastrados neste de Orçamento antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}
	
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		if(aFlag) {
			carregarFieldFuncionario();
			carregarFieldStatusProposta();
			carregarRecursos();
			carregarTerceirizados();
			verificarPermissao();
			carregarFieldRecursoGrupo();
			carregarFieldRecursoHumanoGrupo();
		}
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
		//this.addInputField(getPanelCliente(), new RestricaoLayout(0, 0, 2, true, false));
		this.addInputField(getTabDetalhes(), new RestricaoLayout(1, 0, 2, 1, true, true));
		this.addInputField(getPanelImportarOrcamento(), new RestricaoLayout(2,0,1,true,false));
		this.addInputField(getPanelPrecoTotal(), new RestricaoLayout(2,1,1,true,false));
		carregarRecursos();
		carregarTerceirizados();
		carregarFieldRecursoGrupo();
		carregarFieldFuncionario();
		carregarFieldStatusProposta();
		
		this.addStatusTab("Status", getJPanelSituacao());
	}
	
	private JPanel getJPanelSituacao() {
		if (jPanelSituacao == null) {
			jPanelSituacao = new JPanel();
			jPanelSituacao.setLayout(new BoxLayout(jPanelSituacao, BoxLayout.X_AXIS));
			jPanelSituacao.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			jPanelSituacao.setName("jPanelSituacao");

			botaoSituacaoAberto = new ToggleStatus("Aberto", "" );
			botaoSituacaoAberto.setIcon(new ImageIcon(getClass().getResource("/images/pendente20.png")));
			
			botaoSituacaoAberto.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			botaoSituacaoFechado = new ToggleStatus("Fechado", "" ); 
			botaoSituacaoFechado.setIcon(new ImageIcon(getClass().getResource("/images/check_24.png")));
			botaoSituacaoFechado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});
			
			botaoSituacaoCancelado = new ToggleStatus("Cancelado", "" ); 
			botaoSituacaoCancelado.setIcon(new ImageIcon(getClass().getResource("/images/error_24.png")));
			botaoSituacaoCancelado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			jPanelSituacao.add(new BordedPanel(botaoSituacaoAberto), null);
			jPanelSituacao.add(new BordedPanel(botaoSituacaoFechado), null);
			jPanelSituacao.add(new BordedPanel(botaoSituacaoCancelado), null);

		}
		return jPanelSituacao;
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
	
	public JButton getBotaoAbrirCliente() {
		JButton botaoAbrirCliente = new JButton("", new ImageIcon(getClass().getResource("/images/lente_24.png")));
		botaoAbrirCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setPreferredSize(new Dimension(30,18));

		botaoAbrirCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cliente != null) {
							FormCliente telaCliente = (FormCliente) WindowFactory.createTelaCliente(Facade.getInstance().getTelaPrincipal().getDesktop());
							Facade.getInstance().beginTransaction();
							//cliente = Facade.getInstance().carregarCliente(cliente.getId());
							telaCliente.loadForm(Facade.getInstance().carregarCliente(cliente.getId()));
							Facade.getInstance().commit();
							telaCliente.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(FormOrcamento.this, "Nenhum cliente selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
							
					}
				}	
		);
		return botaoAbrirCliente;
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

		//botaoCliente.setPreferredSize(new Dimension(30,18));

		panelCliente.add(fieldClienteID);
		panelCliente.add(botaoCliente);
		return panelCliente;
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
		tabDetalhes.addTab("Mapa do Evento", getPanelAmbientes());
		tabDetalhes.addTab("Equipamentos", getPanelEquipamentos());
		tabDetalhes.addTab("Cenografia - Estruturas",  getPanelCenografia());
		tabDetalhes.addTab("Equipe Técnica", getPanelEquipeTecnica());
		tabDetalhes.addTab("Logística",  getPanelLogistica());
		tabDetalhes.addTab("Terceirizados",  getPanelTerceirizados());
		tabDetalhes.addTab("Dados do Orçamento", getPanelDadosProposta());
		//tabDetalhes.addTab("Detalhes do Evento", getPanelDetalhesEvento());
		
		tabDetalhes.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						//Limpar o clipboard para não permitir copiar conteúdo entre abas diferentes
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(new StringSelection(""), null);
						System.out.println("tab : " + tabDetalhes.getSelectedIndex());
						
						if(tabDetalhes.getSelectedIndex() != 0) {
							if(fieldDataInicio.getDate() == null || fieldDataFim.getDate() == null) {
								tabDetalhes.setSelectedIndex(0);
								JOptionPane.showMessageDialog(null, "Preencha datas de início e fim do evento");
								return;
							}
							switch(tabDetalhes.getSelectedIndex()) {
								case 3:
									limparEquipamento();
									break;
								case 4:
									limparCenografia();
									break;
								case 5:
									limparEquipeTecnica();
									break;
								case 6:
									limparLogistica();
									break;
								case 7:
									limparTerceirizado();
									break;
							
							}
							
							//limparAmbiente();
							//limparSubgrupos();
						}
						
						if(tabDetalhes.getSelectedIndex() >= 3) {
							if(modelAmbiente.getAmbientes().size() == 0){
								tabDetalhes.setSelectedIndex(1);
								JOptionPane.showMessageDialog(null, "Insira pelo menos um ambiente para o evento");
								return;
							}
						}
					}
				}
		);
		return tabDetalhes;
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
		
		//endereco.add(getPanelAmbientes(), new RestricaoLayout(2,0,7,2, true, true));
		endereco.add(new TitledPanel("Observações do Local do Evento", scrollObservacoes), new RestricaoLayout(2,0,7,2, true, true));

		return endereco;
	}
	
	
	public JPanel getPanelAmbientes() {
		JPanel panelAmbiente = new JPanel(); //new JFormGroup("Ambientes do Evento(Auditório, Teatro, Sala, etc...)");  
		panelAmbiente.setLayout(new GridBagLayout());
		
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
		fieldAmbientesEvento = getFieldAmbientesEvento();
		
		
		
		modelAmbiente = new AmbienteTableModel();
		tabelaAmbiente = new JTable(modelAmbiente);
		
		
		
		
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
								JOptionPane.showMessageDialog(null, "Selecione um Ambiente", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		
		
	
		tabelaAmbiente.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				
				if(tabelaAmbiente.getSelectedRow() != -1 && ! tabelaAmbiente.isEditing()) {
					System.out.println("Atualiza a sala");
					AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
					PanelSala ps = paineisEquipamento.get(ambienteAlterar.getNome());
					PanelSala psh = paineisEquipeTecnica.get(ambienteAlterar.getNome());
					PanelSala psc = paineisCenografia.get(ambienteAlterar.getNome());
					
					System.out.println("Achou os paineis"  + ps + ", " + psh + ", " + psc);
					//AmbienteEvento amb = new AmbienteEvento(ambienteAlterar.getNome(), ambienteAlterar.getDataInicio(), ambienteAlterar.getDataFim());
	
					Date dataInicialMudanca = (Date) modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 1);
					Date dataFinalMudanca = (Date) modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 2);
					
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
		panelAmbiente.add(new TitledPanel("Ambientes(Auditório, Teatro, Sala, etc...)", fieldAmbientesEvento), new RestricaoLayout(0,0,1,true,false));
		panelAmbiente.add(new TitledPanel("Nome", fieldAmbienteNome), new RestricaoLayout(0,1,1,true,false));
		panelAmbiente.add(new TitledPanel("Data Início", fieldAmbienteDataInicio), new RestricaoLayout(0,2,1,true,false));
		panelAmbiente.add(new TitledPanel("Data Fim", fieldAmbienteDataFim), new RestricaoLayout(0,3,1,true,false));
		panelAmbiente.add(new TitledPanel(" ", botaoAdicionarAmbiente), new RestricaoLayout(0,4,true,false));
		
		panelAmbiente.add(new TitledPanel("Pontos de Fixação", fieldAmbientePFixacao), new RestricaoLayout(1,0,1,true,false));
		panelAmbiente.add(new TitledPanel("Pé Direito", fieldAmbientePeDireito), new RestricaoLayout(1,1,1,true,false));
		panelAmbiente.add(new TitledPanel("Largura", fieldAmbienteLargura), new RestricaoLayout(1,2,1,true,false));
		panelAmbiente.add(new TitledPanel("Comprimento", fieldAmbienteComprimento), new RestricaoLayout(1,3,1,true,false));
		panelAmbiente.add(new TitledPanel("Área", fieldAmbienteArea), new RestricaoLayout(1,4,1,true,false));
		
		panelAmbiente.add(scrollAmbiente, new RestricaoLayout(2,0,5,1,true,true));
		return panelAmbiente;
	}
	
	@SuppressWarnings("rawtypes" )
	private JComboBox getFieldAmbientesEvento() {
		if(fieldAmbientesEvento == null) {
			fieldAmbientesEvento = new JComboBox();
			fieldAmbientesEvento.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(fieldAmbientesEvento.getSelectedIndex() != 0) {
						SalaLocal salaLocal = (SalaLocal)fieldAmbientesEvento.getSelectedItem();
						if(salaLocal != null) {
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
		return fieldAmbientesEvento;
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
							JOptionPane.showMessageDialog(null, "Não se pode remover o nome do ambiente de terceirizados");
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
							} 
							 else {
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
	
	
	private JMenuItem getMenuDuplicarAmbiente() {
		JMenuItem menuAlterarNome = new JMenuItem("Duplicar Ambiente");
		menuAlterarNome.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						PanelSala ps = paineisEquipamento.get(ambienteAlterar.getNome());
						PanelSala psh = paineisEquipeTecnica.get(ambienteAlterar.getNome());
						PanelSala psc = paineisCenografia.get(ambienteAlterar.getNome());

						String nome = JOptionPane.showInputDialog(null, "Informe o nome do novo ambiente:", "Duplicar ambiente", JOptionPane.QUESTION_MESSAGE);
						if(ambienteAlterar.getNome().equals("LOGÍSTICA")) {
							JOptionPane.showMessageDialog(null, "Não se pode duplicar o ambiente de logística");
						}
						else if(ambienteAlterar.getNome().equals("EQUIPE TÉCNICA")) {
							JOptionPane.showMessageDialog(null, "Não se pode duplicar o ambiente de equipe técnica");
						}
						else if(ambienteAlterar.getNome().equals("TERCEIRIZADOS")) {
							JOptionPane.showMessageDialog(null, "Não se pode alterar o nome do ambiente de terceirizados");
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
		if(paineisLogistica.get(amb.getNome()) == null && paineisTerceirizados.get(amb.getNome()) == null ) {
			PanelSala pn = new PanelSala(amb, FormOrcamento.this, false, true,TipoRecurso.EQUIPAMENTO);
			PanelSala pnc = new PanelSala(amb, FormOrcamento.this, false, false,TipoRecurso.CENOGRAFIA);
			PanelSala pnet = new PanelSala(amb, FormOrcamento.this, false, false,TipoRecurso.EQUIPE_TECNICA);

			tabAmbientesEquipamento.addTab(amb.getNome(), pn);
			tabAmbientesCenografia.addTab(amb.getNome(), pnc);
			tabAmbientesEquipeTecnica.addTab(amb.getNome(), pnet);
			
			paineisEquipamento.put(amb.getNome(), pn);
			paineisCenografia.put(amb.getNome(), pnc);
			paineisEquipeTecnica.put(amb.getNome(), pnet);
			
			modelAmbiente.addElement(amb);
		}
		else {
			JOptionPane.showMessageDialog(null, "Não se pode criar o ambiente de logística ou de terceirizados");
		}
	}
	
	private void adicionarAmbienteLogistica(AmbienteEvento amb) {
		PanelSala pnl = new PanelSala(amb, FormOrcamento.this, false, false,TipoRecurso.LOGISTICA);
		tabAmbientesLogistica.addTab(amb.getNome(),pnl);
		paineisLogistica.put(amb.getNome(), pnl);	
	}
	
	private void adicionarAmbienteTerceirizado(AmbienteEvento amb) {
		PanelSalaTerceirizado pnl = new PanelSalaTerceirizado(amb, FormOrcamento.this, false, false,fieldTerceirizadoEmp.isSelected());
		tabAmbientesTerceirizados.addTab(amb.getNome(),pnl);
		paineisTerceirizados.put(amb.getNome(), pnl);	
	}
	
	private void adicionarAmbienteLogistica() {
		if(paineisLogistica.size() == 0) {
			AmbienteEvento amb = new AmbienteEvento("LOGÍSTICA",fieldDataMontagem.getDate(),fieldDataFim.getDate());
			//System.out.println(paineisLogistica.get(amb.getNome()));	
			PanelSala pnl = new PanelSala(amb, FormOrcamento.this, false, false,TipoRecurso.LOGISTICA);
			tabAmbientesLogistica.addTab(amb.getNome(),pnl);
			paineisLogistica.put(amb.getNome(), pnl);
		}
		//	modelAmbiente.addElement(amb);
		
	}
	
	private void adicionarAmbienteTerceirizado() {
		if(paineisTerceirizados.size() == 0) {
			try {
				Date dataInicio = formato.parse(formato.format(fieldDataMontagem.getDate()));
				Date dataFim = formato.parse(formato.format(fieldDataFim.getDate()));
				AmbienteEvento amb = new AmbienteEvento("TERCEIRIZADOS",dataInicio,dataFim);
				PanelSalaTerceirizado pnl = new PanelSalaTerceirizado(amb, FormOrcamento.this, false, false,fieldTerceirizadoEmp.isSelected());
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
		if(fieldAmbientesEvento.getItemCount() > 0)
			fieldAmbientesEvento.setSelectedIndex(0);
	}
	
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
						carregarLocal(l,null);
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
	
	public JButton getBotaoProcurarLocal() {
		JButton botaoProcurarLocal = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarLocal.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarLocal.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarLocal.setPreferredSize(new Dimension(30,18));

		botaoProcurarLocal.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchLocal teste = new DialogSearchLocal(FormOrcamento.this, Facade.getInstance().listarLocais());
						Local l = teste.showDialog(FormOrcamento.this);
						if(l != null) {
							carregarLocal(l,null);
							carregarFieldAmbientesEvento(l);
						}
					}
				}	
		);
		return botaoProcurarLocal;
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
	
	private void carregarLocal(Local l,LocalEvento le) {
		if(l!=null){
			local = l;
			fieldLocalID.setText("" + l.getId());
			if(le == null) {
				fieldEnderecoLogradouro.setText(l.getEndereco().getLogradouro());
				fieldEnderecoNumero.setText(l.getEndereco().getNumero());
				fieldEnderecoComplemento.setText(l.getEndereco().getComplemento());
				fieldEnderecoCEP.setText(l.getEndereco().getCep());
				fieldEnderecoBairro.setText(l.getEndereco().getBairro());
				fieldEnderecoCidade.setText(l.getEndereco().getCidade());
				fieldEnderecoEstado.setSelectedItem(l.getEndereco().getEstado());
				fieldEnderecoReferencia.setText(l.getEndereco().getPontoReferencia());
				fieldLocalNome.setText(l.getNome());
				fieldObservacoesLocalEvento.setText(l.getObservacoes());
			}
			else {
				fieldEnderecoLogradouro.setText(le.getEndereco().getLogradouro());
				fieldEnderecoNumero.setText(le.getEndereco().getNumero());
				fieldEnderecoComplemento.setText(le.getEndereco().getComplemento());
				fieldEnderecoCEP.setText(le.getEndereco().getCep());
				fieldEnderecoBairro.setText(le.getEndereco().getBairro());
				fieldEnderecoCidade.setText(le.getEndereco().getCidade());
				fieldEnderecoEstado.setSelectedItem(le.getEndereco().getEstado());
				fieldEnderecoReferencia.setText(le.getEndereco().getPontoReferencia());
				fieldLocalNome.setText(le.getNome());
				fieldObservacoesLocalEvento.setText(le.getObservacoes());
			}
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

	@SuppressWarnings("rawtypes")
	private JPanel getPanelDadosEvento() {
		fieldNomeEvento = new JTextField();
		
		fieldFuncionario = new JComboBox();
		fieldVendedorConjunto = new JComboBox();
		fieldDataInicio = new JDateChooser();
		fieldDataFim = new JDateChooser();
		fieldDataMontagem = new JDateChooser();
		fieldDetalhesEvento = new JTextArea();
		fieldDetalhesEvento.setWrapStyleWord(true);
		fieldDetalhesEvento.setLineWrap(true);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(fieldDetalhesEvento);
		
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
						atualizarDataMontagem();
					}
				}
			);
		
		
		fieldDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						atualizarDataInicio();
					}
				}
		);

		fieldDataFim.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						atualizarDataFim();
					}
				}
		);
		
		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());
		
		painelDadosEvento.add(getPanelCliente(), new RestricaoLayout(0, 0, 6, true, false));

		painelDadosEvento.add(new TitledPanel("Nome do Evento", fieldNomeEvento), new RestricaoLayout(1, 0, 4, true, false));
		painelDadosEvento.add(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayout(1, 4, 2, true, false));
		
		painelDadosEvento.add(new TitledPanel("Início do Evento", painelDataHoraInicio), new RestricaoLayout(2, 0, 2, true, false));
		painelDadosEvento.add(new TitledPanel("Fim do Evento", painelDataHoraFim), new RestricaoLayout(2,2,2,true, false));
		painelDadosEvento.add(new TitledPanel("Montagem do Evento", painelDataHoraMontagem), new RestricaoLayout(2,4,2,true, false));

		painelDadosEvento.add(new TitledPanel("Detalhes do Evento", scroll), new RestricaoLayout(3, 0,9,1, true, true));
		
		//painelDadosEvento.add(getPanelObservacoes(), new RestricaoLayout(3, 0, 6, 1, true, true));
		

		return painelDadosEvento;		
	}
	
	private void atualizarDataInicio() {
		if(fieldDataInicio.getDate() != null && ! disableDateEvent){
			try {
				Date dataInicio = formato.parse(formato.format(fieldDataInicio.getDate()));
				
				fieldCenografiaDataInicio.setDate(dataInicio);
				fieldEquipamentoDataInicio.setDate(dataInicio);
				fieldTerceirizadoDataInicio.setDate(dataInicio);
				fieldAmbienteDataInicio.setDate(dataInicio);
				
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
	
	private void atualizarDataFim() {
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
				fieldAmbienteDataFim.setDate(dataFim);
				
				fieldAmbienteDataInicio.setMaxSelectableDate(dataFim);
				fieldAmbienteDataFim.setMaxSelectableDate(dataFim);
				fieldEquipamentoDataInicio.setMaxSelectableDate(dataFim);
				fieldEquipamentoDataFim.setMaxSelectableDate(dataFim);
				fieldTerceirizadoDataInicio.setMaxSelectableDate(dataFim);
				fieldTerceirizadoDataFim.setMaxSelectableDate(dataFim);
				fieldEquipeTecnicaDataInicio.setMaxSelectableDate(dataFim);
				fieldEquipeTecnicaDataFim.setMaxSelectableDate(dataFim);
				fieldCenografiaDataInicio.setMaxSelectableDate(dataFim);
				fieldCenografiaDataFim.setMaxSelectableDate(dataFim);
				fieldLogisticaDataInicio.setMaxSelectableDate(dataFim);
				fieldLogisticaDataFim.setMaxSelectableDate(dataFim);
				
				
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
	
	private void atualizarDataMontagem() {
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
					//	a.setDataInicio(dataMontagem);
						
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

	@SuppressWarnings("rawtypes")
	public JPanel getPanelDadosProposta() {
		JPanel panelProposta = new JPanel();
		panelProposta.setLayout(new GridBagLayout());
		
		fieldStatusProposta = new JComboBox<SituacaoOrcamento>();
		fieldNomeProposta = new JTextField();
		fieldCargoProposta = new JTextField();
		fieldNomePropostaConjunta = new JTextField();
		fieldCargoPropostaConjunta = new JTextField();
		fieldTelefonePropostaConjunta = new JTextField();
		fieldTelefoneProposta = new JTextField();
		fieldCondicoesPagamento = new JTextField();
		fieldObservacoesCliente = new JTextArea();
		JScrollPane scrollObservacoesCliente = new JScrollPane();
		scrollObservacoesCliente.setViewportView(fieldObservacoesCliente);
		
		panelProposta.add(new TitledPanel("Venda Conjunta com", fieldVendedorConjunto), new RestricaoLayout(0,0,2,true,false));
		panelProposta.add(new TitledPanel("Situação do Evento", fieldStatusProposta), new RestricaoLayout(0,2,1,true,false));
		panelProposta.add(new TitledPanel(" ", getBotaoAtualizarStatus()), new RestricaoLayout(0,3,1,true,false));
		panelProposta.add(new TitledPanel("Condições de Pagamento", fieldCondicoesPagamento), new RestricaoLayout(1,0,4,true,false));
		panelProposta.add(new TitledPanel("Nome do Funcionário no Orçamento", fieldNomeProposta), new RestricaoLayout(2,0,2,true,false));
		panelProposta.add(new TitledPanel("Cargo do Funcionário no Orçamento", fieldCargoProposta), new RestricaoLayout(3,0,2,true,false));
		panelProposta.add(new TitledPanel("Telefone do Funcionário no Orçamento", fieldTelefoneProposta), new RestricaoLayout(4,0,2,true,false));
		panelProposta.add(new TitledPanel("Nome do Funcionário Conjunto no Orçamento", fieldNomePropostaConjunta), new RestricaoLayout(2,2,2,true,false));
		panelProposta.add(new TitledPanel("Cargo do Funcionário Conjunto no Orçamento", fieldCargoPropostaConjunta), new RestricaoLayout(3,2,2,true,false));
		panelProposta.add(new TitledPanel("Telefone do Funcionário Conjunto no Orçamento", fieldTelefonePropostaConjunta), new RestricaoLayout(4,2,2,true,false));
		panelProposta.add(new TitledPanel("Observações para o Cliente", scrollObservacoesCliente), new RestricaoLayout(5, 0, 4, 1, true, true));
		
		return panelProposta;
	}
	
	public JButton getBotaoAtualizarStatus() {
		JButton botaoAtualizarStatus = new JButton("Atualizar Status");
		botaoAtualizarStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(orc !=  null && orc.getId()!=0) {
					orc.setSituacao((SituacaoOrcamento) fieldStatusProposta.getSelectedItem());
					Facade.getInstance().atualizarOrcamento(orc);
					JOptionPane.showMessageDialog(FormOrcamento.this, "Status atualizado com sucesso!");
				} else {
					JOptionPane.showMessageDialog(FormOrcamento.this, "Nenhum orçamento selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		return botaoAtualizarStatus;
	}
	
	public JPanel getPanelEquipamentos() {
		fieldEquipamentoNome = getFieldEquipamentoNome();
		fieldEquipamentoGrupo = getFieldEquipamentoGrupo();
		carregarFieldRecursoGrupo();
		fieldEquipamentoDescricao = new JTextField();
		
		fieldEquipamentoPreco = new JMoedaRealTextField();
		fieldEquipamentoCusto = new JMoedaRealTextField();

		fieldEquipamentoDataInicio = new JDateChooser();
		fieldEquipamentoDataFim = new JDateChooser();
		fieldEquipamentoQuantidade = new JIntField();

		tabAmbientesEquipamento = new JTabbedPane();
		paineisEquipamento = new HashMap<String, PanelSala>();

		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());

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
					/*fieldEquipamentoDataInicio.setMinSelectableDate(salaSelecionada.getDataInicial());
					fieldEquipamentoDataInicio.setMaxSelectableDate(salaSelecionada.getDataFinal());
					fieldEquipamentoDataFim.setMinSelectableDate(salaSelecionada.getDataInicial());
					fieldEquipamentoDataFim.setMaxSelectableDate(salaSelecionada.getDataFinal());*/
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

		adicionar.add(new TitledPanel("Data Inicial", fieldEquipamentoDataInicio), new RestricaoLayout(0, 2,1, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldEquipamentoDataFim), new RestricaoLayout(0, 3,1, true, false));
		adicionar.add(new TitledPanel("Quant.", fieldEquipamentoQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecurso()) , new RestricaoLayout(0,5, false, false));
		adicionar.add(new TitledPanel("Descrição", fieldEquipamentoDescricao), new RestricaoLayout(1, 2,4, true, false));

		panelRecursos.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelRecursos.add(tabAmbientesEquipamento, new RestricaoLayout(1,0,1,1,true,true));
		
		limparPanelRecursos();
		return panelRecursos;		
	}
	
	@SuppressWarnings("rawtypes")
	private  JComboBox getFieldEquipamentoNome() {
		if(fieldEquipamentoNome == null) {
			fieldEquipamentoNome = new JComboBox();
			fieldEquipamentoNome.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(fieldEquipamentoNome.getSelectedItem() != " " && fieldEquipamentoNome.getSelectedItem() != null) {
						Recurso r = (Recurso) fieldEquipamentoNome.getSelectedItem();
						fieldEquipamentoPreco.setValor(r.getPrecoSugerido());
						fieldEquipamentoCusto.setValor(r.getPrecoCusto());

						fieldEquipamentoDescricao.setText(r.getNome());
						recurso = r;
						addEquipamentoButton.requestFocus();
					}
				}
			});
		}
		return fieldEquipamentoNome;
	} 
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldEquipamentoGrupo() {
		if(fieldEquipamentoGrupo == null) {
			fieldEquipamentoGrupo = new JComboBox();

			fieldEquipamentoGrupo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(fieldEquipamentoGrupo.getSelectedItem() != " " && fieldEquipamentoGrupo.getSelectedItem() != null) {
						carregarFieldRecursoNome();
					}
				}
			});
		}
		return fieldEquipamentoGrupo;
	}
	
	public JPanel getPanelTerceirizados() {
		fieldTerceirizadoNome = getFieldTerceirizadoNome();
		fieldTerceirizadoGrupo = getFieldTerceirizadoGrupo();
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
		adicionar.add(new TitledPanel("Data Inicial", fieldTerceirizadoDataInicio), new RestricaoLayout(0, 2,1, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldTerceirizadoDataFim), new RestricaoLayout(0, 3,1, true, false));
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
				if(salaSelecionada != null) {
					salaSelecionada.setEmpresa(fieldTerceirizadoEmp.isSelected());
					calcularTotal();
					calcularTotalTerceirizado();
				}

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
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldTerceirizadoGrupo (){
		if(fieldTerceirizadoGrupo == null) {
			fieldTerceirizadoGrupo = new JComboBox();
		fieldTerceirizadoGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldTerceirizadoGrupo.getSelectedItem() != " " && fieldTerceirizadoGrupo.getSelectedItem() != null) {
					carregarFieldTerceirizadoNome();
				}
			}
		});
		}
		return fieldTerceirizadoGrupo;
	}
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldTerceirizadoNome() {
		if(fieldTerceirizadoNome == null) {
			fieldTerceirizadoNome = new JComboBox();
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
		return fieldTerceirizadoNome;
		
	}
	
	public JPanel getPanelCenografia() {
		fieldCenografiaNome = getFieldCenografiaNome();
		fieldCenografiaGrupo = getFieldCenografiaGrupo();
		carregarFieldCenografia();
		
		fieldCenografiaPreco = new JMoedaRealTextField();
		fieldCenografiaDataInicio = new JDateChooser();
		fieldCenografiaDataFim = new JDateChooser();
		fieldCenografiaQuantidade = new JIntField();
		fieldCenografiaDescricao = new JTextField();
		fieldCenografiaCusto = new JMoedaRealTextField();


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
		adicionar.add(new TitledPanel("Data Inicial", fieldCenografiaDataInicio), new RestricaoLayout(0, 2,1, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldCenografiaDataFim), new RestricaoLayout(0, 3,1,true, false));
		adicionar.add(new TitledPanel("Quant.", fieldCenografiaQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarCenografia()) , new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel("Descrição", fieldCenografiaDescricao), new RestricaoLayout(1, 2,4, true, false));
		
		panelCenografia.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelCenografia.add(tabAmbientesCenografia, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelCenografia();
		return panelCenografia;		
	}	
	
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldCenografiaGrupo() {
		if(fieldCenografiaGrupo == null) {
			fieldCenografiaGrupo = new JComboBox();
			fieldCenografiaGrupo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(fieldCenografiaGrupo.getSelectedItem() != " " && fieldCenografiaGrupo.getSelectedItem() != null) {
						carregarFieldCenografiaNome();
					}
				}
			});
		}
		return fieldCenografiaGrupo;
	}
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldCenografiaNome() {
		if(fieldCenografiaNome == null) {
			fieldCenografiaNome = new JComboBox();
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
		return fieldCenografiaNome;
	}
	
	public JPanel getPanelLogistica() {
		fieldLogisticaNome = getFieldLogisticaNome();
		fieldLogisticaGrupo = getFieldLogisticaGrupo();
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
		adicionar.setLayout(new GridBagLayout());
		
		adicionar.add(new TitledPanel("Grupo", fieldLogisticaGrupo), new RestricaoLayout(0, 0, 1 , true, false));
		adicionar.add(new TitledPanel("Preço de Custo", fieldLogisticaCusto), new RestricaoLayout(1, 0, true, false));

		adicionar.add(new TitledPanel("Preço", fieldLogisticaPreco), new RestricaoLayout(1, 1, true, false));
		adicionar.add(new TitledPanel("Data Inicial", fieldLogisticaDataInicio), new RestricaoLayout(0, 2,1, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldLogisticaDataFim), new RestricaoLayout(0, 3,1, true, false));
		adicionar.add(new TitledPanel("Quant.", fieldLogisticaQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarLogistica()) , new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel("Subgrupo Por Período", fieldLogisticaNome), new RestricaoLayout(0, 1, 1, true, false));
		//adicionar.add(new TitledPanel("Subgrupo Por Diária", fieldLogisticaDiaria), new RestricaoLayout(1, 1, 1, true, false));

		adicionar.add(new TitledPanel("Descrição", fieldLogisticaDescricao), new RestricaoLayout(1, 2,4, true, false));

		panelLogistica.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelLogistica.add(tabAmbientesLogistica, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelLogistica();
		return panelLogistica;		
	}	
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldLogisticaNome() {
		if(fieldLogisticaNome == null) {
			fieldLogisticaNome = new JComboBox();
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
		}
		return fieldLogisticaNome;
	}
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldLogisticaGrupo() {
		if(fieldLogisticaGrupo == null) {
			fieldLogisticaGrupo = new JComboBox();
			fieldLogisticaGrupo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(fieldLogisticaGrupo.getSelectedItem() != " " && fieldLogisticaGrupo.getSelectedItem() != null) {
						carregarFieldLogisticaNome();
					}
				}
			});
		}
		return fieldLogisticaGrupo;
	}
	
	public JPanel getPanelEquipeTecnica() {
		fieldEquipeTecnicaNome = getFieldEquipeTecnicaNome();
		fieldEquipeTecnicaGrupo = getFieldEquipeTecnicaGrupo();
		carregarFieldRecursoHumanoGrupo();
		fieldEquipeTecnicaDescricao = new JTextField();

		fieldEquipeTecnicaPreco = new JMoedaRealTextField();
		fieldEquipeTecnicaCusto = new JMoedaRealTextField();

		fieldEquipeTecnicaDataInicio = new JDateChooser();
		fieldEquipeTecnicaDataFim = new JDateChooser();
		fieldEquipeTecnicaQuantidade = new JIntField();

		tabAmbientesEquipeTecnica = new JTabbedPane();
		paineisEquipeTecnica = new HashMap<String, PanelSala>();
		

		JPanel panelRecursosHumanos = new JPanel();
		panelRecursosHumanos.setLayout(new GridBagLayout());

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

		adicionar.add(new TitledPanel("Data Inicial", fieldEquipeTecnicaDataInicio), new RestricaoLayout(0,2,1, true, false));
		adicionar.add(new TitledPanel("Data Final", fieldEquipeTecnicaDataFim), new RestricaoLayout(0, 3,1, true, false));
		adicionar.add(new TitledPanel("Quant.", fieldEquipeTecnicaQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecursoHumano()) , new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel("Descrição", fieldEquipeTecnicaDescricao), new RestricaoLayout(1,2,4, true, false));

		panelRecursosHumanos.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelRecursosHumanos.add(tabAmbientesEquipeTecnica, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelRecursosHumanos();
		return panelRecursosHumanos;		
	}
	
	
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldEquipeTecnicaNome () {
		if(fieldEquipeTecnicaNome ==  null) {
			fieldEquipeTecnicaNome = new JComboBox();
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
		return fieldEquipeTecnicaNome;
	}

	@SuppressWarnings("rawtypes")
	private JComboBox getFieldEquipeTecnicaGrupo() {
		if(fieldEquipeTecnicaGrupo == null) {
			fieldEquipeTecnicaGrupo = new JComboBox();
			fieldEquipeTecnicaGrupo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(fieldEquipeTecnicaGrupo.getSelectedItem() != " " && fieldEquipeTecnicaGrupo.getSelectedItem() != null) {
						carregarFieldRecursoHumanoNome();
					}
				}
			});
		}
		return fieldEquipeTecnicaGrupo;
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

		
		fieldPrecoCustoSubtotal = new JLabel("R$ 0,00");
		fieldPrecoCustoSubtotal.setFont(new Font("Arial", Font.BOLD, 18));
		fieldPrecoCustoSubtotal.setForeground(Color.RED);
		
		fieldDesconto = new JLabel("R$ 0,00");
		fieldDesconto.setFont(new Font("Arial", Font.BOLD, 18));
		fieldDesconto.setForeground(new Color(0,150,0));

		
		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));
		total.add(new TitledPanel("Terceirizados",fieldPrecoTerceirizado));
		total.add(new TitledPanel("Custo", fieldPrecoCustoSubtotal));
		total.add(new TitledPanel("Subtotal", fieldPrecoSubtotal));
		total.add(new TitledPanel("Desconto", fieldDesconto));
		total.add(new TitledPanel("Total", fieldPrecoTotal));

		return total;
	}


	private JButton getBotaoAdicionarRecurso() {
		addEquipamentoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addEquipamentoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addEquipamentoButton.setVerticalAlignment(SwingConstants.CENTER);
		addEquipamentoButton.setPreferredSize(new Dimension(26,26));
		addEquipamentoButton.addActionListener(new java.awt.event.ActionListener() {
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
					JOptionPane.showMessageDialog(FormOrcamento.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addEquipamentoButton;
	}

	private JButton getBotaoAdicionarTerceirizado() {
		addTerceirizadoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addTerceirizadoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addTerceirizadoButton.setVerticalAlignment(SwingConstants.CENTER);
		addTerceirizadoButton.setPreferredSize(new Dimension(26,26));
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
						rec.setQuantidade(Integer.parseInt(fieldTerceirizadoQuantidade.getText()));
						PanelSalaTerceirizado salaSelecionada = (PanelSalaTerceirizado) tabAmbientesTerceirizados.getComponent(tabAmbientesTerceirizados.getSelectedIndex());
						salaSelecionada.adicionarRecurso(rec);
						calcularTotalTerceirizado();
						limparTerceirizado();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(FormOrcamento.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addTerceirizadoButton;
	}
	
	private JButton getBotaoAdicionarRecursoHumano() {
		addEquipeTecnicaButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addEquipeTecnicaButton.setHorizontalAlignment(SwingConstants.CENTER);
		addEquipeTecnicaButton.setVerticalAlignment(SwingConstants.CENTER);
		addEquipeTecnicaButton.setPreferredSize(new Dimension(26,26));
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
					JOptionPane.showMessageDialog(FormOrcamento.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addEquipeTecnicaButton;
	}
	
	private JButton getBotaoAdicionarCenografia() {
		addCenografiaButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addCenografiaButton.setHorizontalAlignment(SwingConstants.CENTER);
		addCenografiaButton.setVerticalAlignment(SwingConstants.CENTER);
		addCenografiaButton.setPreferredSize(new Dimension(26,26));
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
					JOptionPane.showMessageDialog(FormOrcamento.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addCenografiaButton;
	}
	
	private JButton getBotaoAdicionarLogistica() {
		addLogisticaButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addLogisticaButton.setHorizontalAlignment(SwingConstants.CENTER);
		addLogisticaButton.setVerticalAlignment(SwingConstants.CENTER);
		addLogisticaButton.setPreferredSize(new Dimension(26,26));
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
					JOptionPane.showMessageDialog(FormOrcamento.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addLogisticaButton;
	}
	
	public BigDecimal calcularTotalTerceirizado() {
		DecimalFormat formatador = new DecimalFormat("#,###,###,##0.00");
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		formato.setMaximumIntegerDigits(12);
		BigDecimal total = new BigDecimal(0);
		
		if(fieldTerceirizadoEmp.isSelected() && !fieldTerceirizadoFor.isSelected()){
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
		BigDecimal total = new BigDecimal(0);
		BigDecimal totalCusto = new BigDecimal(0);
		BigDecimal desconto = new BigDecimal(0);

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
		for(PanelSalaTerceirizado pns: paineisTerceirizados.values()) {
			desconto = desconto.add(pns.getAmbiente().getDescontoTerceirizado());
		}
		if(fieldTerceirizadoEmp.isSelected() && ! fieldTerceirizadoFor.isSelected()) {
			for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
				total = total.add(pnt.calcularSubTotal());
				totalCusto = totalCusto.add(pnt.calcularSubTotalFornecedor());
			}
		}
		
				
		fieldPrecoCustoSubtotal.setText("R$ " + formatador.format(totalCusto));
		fieldPrecoSubtotal.setText("R$ " + formatador.format(total));
		total = total.subtract(desconto);
		fieldPrecoTotal.setText("R$ " + formatador.format(total));
		fieldDesconto.setText("R$ "+ formatador.format(desconto));
		calcularTotalTerceirizado();
		
		return total;
	}

	private void limparPanelRecursos() {
		fieldEquipamentoGrupo.setSelectedItem(" ");
		fieldEquipamentoNome.removeAllItems();
		fieldEquipamentoPreco.setValor(new BigDecimal(0));
		fieldEquipamentoCusto.setValor(new BigDecimal(0));

		fieldEquipamentoQuantidade.setText("1");
		fieldEquipamentoDescricao.setText("");
	}
	
	private void limparPanelTerceirizado() {
		fieldTerceirizadoGrupo.setSelectedItem(" ");
		fieldTerceirizadoNome.removeAllItems();
		fieldTerceirizadoPrecoEmpresa.setValor(new BigDecimal(0));
		fieldTerceirizadoPrecoForn.setValor(new BigDecimal(0));

		fieldTerceirizadoQuantidade.setText("1");
		fieldTerceirizadoDescricao.setText("");
	}
	
	private void limparPanelRecursosHumanos() {
		fieldEquipeTecnicaGrupo.setSelectedItem(" ");
		fieldEquipeTecnicaNome.removeAllItems();
		fieldEquipeTecnicaPreco.setValor(new BigDecimal(0));
		fieldEquipeTecnicaCusto.setValor(new BigDecimal(0));

		fieldEquipeTecnicaQuantidade.setText("1");
		fieldEquipeTecnicaDescricao.setText("");

	}

	private void limparPanelCenografia() {
		fieldCenografiaGrupo.setSelectedItem(" ");
		fieldCenografiaNome.removeAllItems();
		fieldCenografiaPreco.setValor(new BigDecimal(0));
		fieldCenografiaCusto.setValor(new BigDecimal(0));

		fieldCenografiaQuantidade.setText("1");
		fieldCenografiaDescricao.setText("");
	}
	
	private void limparPanelLogistica() {
		fieldLogisticaGrupo.setSelectedItem(" ");
		fieldLogisticaNome.removeAllItems();
		fieldLogisticaPreco.setValor(new BigDecimal(0));
		fieldLogisticaCusto.setValor(new BigDecimal(0));

		fieldLogisticaQuantidade.setText("1");
		fieldLogisticaDescricao.setText("");
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


	public void limparCliente() {
		cliente = null;
		fieldClienteID.setText("");
		fieldClienteRazao.setText("");
		fieldClienteTelefone.setText("");
		fieldClienteContato.setText("");
		fieldClienteCelular.setText("");
	}

	public void limparEquipamento() {
		recurso = null;
		//fieldEquipamentoGrupo.setSelectedItem(" ");
		//fieldEquipamentoNome.removeAllItems();
		
		fieldEquipamentoPreco.setValor(new BigDecimal(0));
		fieldEquipamentoCusto.setValor(new BigDecimal(0));

//		fieldEquipamentoDataInicio.setDate(fieldDataInicio.getDate());
//		fieldEquipamentoDataFim.setDate(fieldDataFim.getDate());
		
		
		System.out.println("Aba: " + tabAmbientesEquipamento.getSelectedIndex()+ " "  + tabAmbientesEquipamento.getSelectedComponent());
		if(tabAmbientesEquipamento.getSelectedComponent() != null) {
			PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipamento.getComponent(tabAmbientesEquipamento.getSelectedIndex());
			System.out.println("Data Inicio Sala" + salaSelecionada.getDataInicial());
			fieldEquipamentoDataInicio.setDate(salaSelecionada.getDataInicial());
			fieldEquipamentoDataFim.setDate(salaSelecionada.getDataFinal());
		}
		
		
		fieldEquipamentoQuantidade.setText("1");
		fieldEquipamentoDescricao.setText("");
		fieldEquipamentoNome.requestFocus();
	}
	
	public void limparSubgrupos() {
		fieldEquipamentoNome.removeAllItems();
		fieldTerceirizadoNome.removeAllItems();
		fieldEquipeTecnicaNome.removeAllItems();
		fieldCenografiaNome.removeAllItems();
		fieldLogisticaNome.removeAllItems();
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
		
		fieldTerceirizadoNome.requestFocus();
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

		
		//if(tabAmbientesEquipeTecnica.getSelectedComponent() != null) {
		if(fieldDataMontagem.getDate() != null && fieldDataFim.getDate() != null) {
			//PanelSala salaSelecionada = (PanelSala) tabAmbientesEquipeTecnica.getComponent(tabAmbientesEquipeTecnica.getSelectedIndex());
			//fieldEquipeTecnicaDataInicio.setDate(salaSelecionada.getDataInicial());
			//fieldEquipeTecnicaDataFim.setDate(salaSelecionada.getDataFinal());
			
			fieldEquipeTecnicaDataInicio.setDate(fieldDataMontagem.getDate());
			fieldEquipeTecnicaDataFim.setDate(fieldDataFim.getDate());
		}
		
		fieldEquipeTecnicaNome.requestFocus();
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
		
		if(tabAmbientesCenografia.getSelectedComponent() != null) {
			PanelSala salaSelecionada = (PanelSala) tabAmbientesCenografia.getComponent(tabAmbientesCenografia.getSelectedIndex());
			fieldCenografiaDataInicio.setDate(salaSelecionada.getDataInicial());
			fieldCenografiaDataFim.setDate(salaSelecionada.getDataFinal());
		}
		
		
		fieldCenografiaNome.requestFocus();
	}
		
	public void limparLogistica() {
		recurso = null;
		//fieldLogisticaGrupo.setSelectedItem(" ");
//		fieldLogisticaNome.removeAllItems();
		fieldLogisticaPreco.setValor(new BigDecimal(0));
		fieldLogisticaCusto.setValor(new BigDecimal(0));

		fieldLogisticaDataInicio.setDate(fieldDataMontagem.getDate());
		fieldLogisticaDataFim.setDate(fieldDataFim.getDate());
		fieldLogisticaQuantidade.setText("1");
		fieldLogisticaDescricao.setText("");
		fieldLogisticaNome.requestFocus();
	}

	public JButton getBotaoProcurarCliente() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(30,18));
		
		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchClient teste = new DialogSearchClient(FormOrcamento.this);
						Cliente c = teste.showDialog(FormOrcamento.this);
						if(c != null)
							carregarCliente(c);
					}
				}	
		);
		return botaoProcurarCliente;
	}

	public Orcamento newElement() {
		return new Orcamento();
	}

	public void loadInputFields(Orcamento orcamento) {
		orcamento.setCliente(cliente);
		orcamento.setDataOrcamento(new Date());
		
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
		
		orcamento.setAmbientes(modelAmbiente.getAmbientes());
		for(PanelSala pns: paineisLogistica.values()) {
			if (pns.getRecursosSolicitados().size() > 0 )
				orcamento.getAmbientes().add(pns.getAmbiente());
		}	
		
		for(PanelSalaTerceirizado pns: paineisTerceirizados.values()) {
			if (pns.getRecursosTerceirizadosSolicitados().size() > 0 )
				orcamento.getAmbientes().add(pns.getAmbiente());
		}	
		
		orcamento.setTerceirizadoEmpresa(fieldTerceirizadoEmp.isSelected());
		orcamento.setTerceirizadoFornecedor(fieldTerceirizadoFor.isSelected());

		orcamento.setRecursoSolicitado(recursos);
		orcamento.setRecursoTerceirizadoSolicitado(recursosTerceirizados);
		
		orcamento.setNomeProposta(fieldNomeProposta.getText());
		orcamento.setCargoProposta(fieldCargoProposta.getText());
		orcamento.setTelefoneProposta(fieldTelefoneProposta.getText());
		
		orcamento.setNomePropostaConjunta(fieldNomePropostaConjunta.getText());
		orcamento.setCargoPropostaConjunta(fieldCargoPropostaConjunta.getText());
		orcamento.setTelefonePropostaConjunta(fieldTelefonePropostaConjunta.getText());
		
		orcamento.setNomeEvento(fieldNomeEvento.getText());
		
		if(orcExtraButton.isSelected() && orcOriginal != null)
			orcamento.setOrcOriginal(orcOriginal);
		
		if(fieldVendedorConjunto.getSelectedItem() instanceof Funcionario){
			orcamento.setVendedorConjunto((Funcionario) fieldVendedorConjunto.getSelectedItem());
		}
		
		
		orcamento.setSituacao((SituacaoOrcamento) fieldStatusProposta.getSelectedItem());
		
		/*
		 * if(fieldStatusProposta.getSelectedItem() == (SituacaoOrcamento.ABERTO)) {
		 * orcamento.setSituacao(SituacaoOrcamento.ABERTO); } else
		 * if(fieldStatusProposta.getSelectedItem() == (SituacaoOrcamento.FECHADO)){
		 * orcamento.setSituacao(SituacaoOrcamento.ABERTO); } else
		 * if(fieldStatusProposta.getSelectedItem() == (SituacaoOrcamento.CANCELADO)){
		 * orcamento.setSituacao(SituacaoOrcamento.CANCELADO); }
		 */
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
		orcamento.setLocal(e);
		
		BigDecimal totalEmpresa = new BigDecimal(0);
		BigDecimal totalForn = new BigDecimal(0);
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			totalEmpresa = totalEmpresa.add(pnt.calcularSubTotal());
		}
	
		for(PanelSalaTerceirizado pnt: paineisTerceirizados.values()) {
			totalForn = totalForn.add(pnt.calcularSubTotalFornecedor());
			
		}
		orcamento.setSubtotalTerceirizadoEmpresa(totalEmpresa);
		orcamento.setSubtotalTerceirizadoForn(totalForn);
		
		orcamento.setHoraInicio((Date) fieldHoraInicio.getValue());
		orcamento.setHoraFim((Date) fieldHoraFim.getValue());
		orcamento.setHoraMontagem((Date) fieldHoraMontagem.getValue());
		
		orcamento.setFuncionario((Funcionario) fieldFuncionario.getSelectedItem());
		orcamento.setDataInicio(fieldDataInicio.getDate());
		orcamento.setDataFim(fieldDataFim.getDate());
		orcamento.setDataMontagem(fieldDataMontagem.getDate());
		orcamento.setObservacoes(fieldObservacoes.getText());
		orcamento.setObservacoesFinanceiras(fieldObservacoesFinanceiras.getText());
		orcamento.setCondicoesPagamento(fieldCondicoesPagamento.getText());
		orcamento.setObservacoesCliente(fieldObservacoesCliente.getText());
				
		orcamento.setDetalhesEvento(fieldDetalhesEvento.getText());
		
		try {
			NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
			formato.setMinimumFractionDigits(2);
			formato.setMaximumFractionDigits(2);
			BigDecimal total = new BigDecimal(formato.parse((fieldPrecoSubtotal.getText()).replace("R$ ", "")).doubleValue());
			BigDecimal totalTerceirizado = new BigDecimal(formato.parse((fieldPrecoTerceirizado.getText()).replace("R$ ", "")).doubleValue());
			BigDecimal desconto = new BigDecimal(formato.parse((fieldDesconto.getText()).replace("R$ ", "")).doubleValue());
			orcamento.setPreco(total);
			orcamento.setPrecoTerceirizado(totalTerceirizado);
			orcamento.setDesconto(desconto);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void loadForm(Orcamento rec) {
		disableDateEvent = true;
		fieldNomeEvento.setText(rec.getNomeEvento());
		fieldFuncionario.setSelectedItem(rec.getFuncionario());
		fieldDataInicio.setDate(rec.getDataInicio());
		fieldDataFim.setDate(rec.getDataFim());
		fieldDataMontagem.setDate(rec.getDataMontagem());
		fieldTerceirizadoEmp.setSelected(rec.isTerceirizadoEmpresa());
		fieldTerceirizadoFor.setSelected(rec.isTerceirizadoFornecedor());
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
		
		fieldDetalhesEvento.setText(rec.getDetalhesEvento());
		
		if(rec.getOrcOriginal() == null)
			orcNormalButton.doClick();
		else
			orcExtraButton.doClick();
		
		orcNormalButton.setEnabled(false);
		orcExtraButton.setEnabled(false);
		
		carregarCliente(rec.getCliente());
		Local localReal = null;
		if(rec.getLocal().getLocal() != null) {
			localReal = Facade.getInstance().carregarLocal(rec.getLocal().getLocal().getId());
		}
		if(localReal != null)
			carregarLocal(localReal,rec.getLocal());
		else
			carregarLocal(new Local(rec.getLocal()),rec.getLocal());
		
		carregarFieldAmbientesEvento(localReal);
		modelAmbiente.removeAllElements();
		paineisEquipamento.clear();
		paineisEquipeTecnica.clear();
		paineisCenografia.clear();
		paineisLogistica.clear();
		paineisTerceirizados.clear();
		//List<DescricaoEquipamento> listaDE = Facade.getInstance().listarDescricoesEquipamentos();
		
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
		
		int i = 0;
		for(RecursoSolicitado r: rec.getRecursoSolicitado()) {
			
			try {
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
					//System.out.println("Logistica" + r.getRecurso().getNome() + psl);
					psl.adicionarRecurso(r);
					break;
				
			default:
				break;
			
			}
			} catch(NullPointerException e) { i++; }
			
			
			/*
			if(Hibernate.getClass(r.getRecurso()).getName().endsWith("Funcao")) {
				psh.adicionarRecurso(r);
			} else {
				ps.adicionarRecurso(r);
			}*/
			
			
		/*	boolean bool = false;
			for(DescricaoEquipamento de: listaDE) {
				if(de.getId() == r.getRecurso().getId()) {
					bool = true;
					break;
				}
			}
			if(ps != null && bool == true) {
				ps.adicionarRecurso(r);
			}
			else if(psh != null && bool == false) {
				psh.adicionarRecurso(r);
			}*/
			
		}
		
		System.out.println("Erros: " + i);
		
		for(RecursoTerceirizadoSolicitado r: rec.getRecursoTerceirizadoSolicitado()) {
			PanelSalaTerceirizado psl = paineisTerceirizados.get("TERCEIRIZADOS");
			
			psl.adicionarRecurso(r);	
		}
				
		calcularTotal();
		calcularTotalTerceirizado();
		
		


		if(rec.getVendedorConjunto() != null){
			fieldVendedorConjunto.setSelectedItem(rec.getVendedorConjunto());
		} else {
			fieldVendedorConjunto.setSelectedIndex(0);
		}
		
		fieldStatusProposta.setSelectedItem(rec.getSituacao());
		/*if(rec.getSituacao() == SituacaoOrcamento.FECHADO) {
			fieldStatusProposta.setSelectedItem(SituacaoOrcamento.FECHADO);
		}
		else if(rec.getSituacao() == SituacaoOrcamento.ABERTO) {
			fieldStatusProposta.setSelectedItem(SituacaoOrcamento.ABERTO);
		}
		else if(rec.getSituacao() == SituacaoOrcamento.CANCELADO) {
			fieldStatusProposta.setSelectedItem(SituacaoOrcamento.CANCELADO);
		}*/
		

		limparEquipamento();
		limparEquipeTecnica();
		limparCenografia();
		limparLogistica();
		limparTerceirizado();
		limparSubgrupos();
		orc = rec;
		disableDateEvent = false;
		
		atualizarDataInicio();
		atualizarDataFim();
		atualizarDataMontagem();
	}
	
	protected void clear() {
		limparCliente();
		limparEquipamento();
		limparEquipeTecnica();
		limparLogistica();
		limparCenografia();
		limparTerceirizado();
		limparSubgrupos();
		
		fieldDesconto.setText("R$ 0,00");
		
		limparLocal();
		fieldAmbientesEvento.removeAllItems();
		modelAmbiente.removeAllElements();
		paineisEquipamento.clear();
		paineisEquipeTecnica.clear();
		paineisCenografia.clear();
		paineisLogistica.clear();
		paineisTerceirizados.clear();
		
	
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
		
		//fieldObservacoes.setText("");
		//fieldObservacoesFinanceiras.setText("");
		fieldDetalhesEvento.setText("");
		fieldNomeEvento.setText("");
		fieldDataInicio.setDate(null);
		fieldDataFim.setDate(null);
		fieldDataMontagem.setDate(null);
		fieldCondicoesPagamento.setText("50% NO FECHAMENTO | RESTANTE PARA 15 DIAS APÓS EVENTO");
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
		fieldVendedorConjunto.setSelectedIndex(0);
		fieldStatusProposta.setSelectedIndex(0);	
		fieldPrecoTotal.setText( "R$ 0,00");
		orc = null;
		orcOriginal = null;
		tabDetalhes.setSelectedIndex(0);
		fieldStatusProposta.setSelectedItem(SituacaoOrcamento.ABERTO);
		
		orcNormalButton.doClick();
		orcNormalButton.setEnabled(true);
		orcExtraButton.setEnabled(true);
		
		fieldDataInicio.setMaxSelectableDate(null);
		fieldDataInicio.setMinSelectableDate(null);
		fieldDataFim.setMaxSelectableDate(null);
		fieldDataFim.setMinSelectableDate(null);
		fieldDataMontagem.setMaxSelectableDate(null);
		fieldDataMontagem.setMinSelectableDate(null);
	}

	protected boolean validateFormInsert() {
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
		
		if(orcExtraButton.isSelected() && orcOriginal == null) {
			test = false;
			error += "\nNão se pode criar um orçamento extra sem importar um orçamento";		
		}
		
		boolean possuiRecurso = false;
		boolean possuiEquipe = false;
		boolean possuiTerceirizados = false;
		
		List<RecursoSolicitado> lista = new ArrayList<RecursoSolicitado>();
		
		for(PanelSala pns: paineisEquipamento.values()) {
			lista.addAll(pns.getRecursosSolicitados());
			if(pns.getRecursosSolicitados().size()>0) {
				
				possuiRecurso = true;
				break;
			}
		}
		for(PanelSala pns: paineisEquipeTecnica.values()) {
			lista.addAll(pns.getRecursosSolicitados());			
			if(pns.getRecursosSolicitados().size()>0) {
				possuiEquipe = true;
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

		for(PanelSalaTerceirizado pns: paineisTerceirizados.values()) {
			if(pns.getRecursosTerceirizadosSolicitados().size()>0) {
				possuiRecurso = true;
				possuiTerceirizados = true;
				break;
			}
		}
		
		if(possuiTerceirizados && !fieldTerceirizadoEmp.isSelected() && !fieldTerceirizadoFor.isSelected()) {
			test = false;
			error += "\nSelecione a opção de fechamento dos terceirizados";
			
		}
		
		if(!possuiRecurso){
			test = false;
			error += "\nAdicione pelo menos um recurso";
		}
		
		if(orcNormalButton.isSelected())
			if(!possuiEquipe){
				test = false;
				error += "\nAdicione pelo menos um item de Equipe Técnica";
			}
		
		
		boolean possuiLogistica = false;
		
		for(PanelSala pns: paineisLogistica.values()) {
			lista.addAll(pns.getRecursosSolicitados());
			if(pns.getRecursosSolicitados().size()>0) {
				possuiLogistica = true;
				break;
			}
		}
		
		if(!possuiLogistica && orcNormalButton.isSelected()){
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
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Orcamento> listAll() {
		List<Orcamento> lista;
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		boolean aberto = botaoSituacaoAberto.isSelected();
		boolean fechado = botaoSituacaoFechado.isSelected();
		boolean cancelado = botaoSituacaoCancelado.isSelected();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Orcamento>();
		}
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_ORCAMENTOS)){
			lista = Facade.getInstance().listarOrcamentos(campo, texto);
		} else {
			lista = Facade.getInstance().listarOrcamentos(campo,texto,Facade.getInstance().getUsuarioLogado().getFuncionario());
		}
		
		List<Orcamento> resp = new ArrayList<Orcamento>();

		for(Orcamento c : lista) {
			if((c.getSituacao() == SituacaoOrcamento.FECHADO && fechado) ||
			(c.getSituacao() == SituacaoOrcamento.ABERTO && aberto)	|| 
			(c.getSituacao() == SituacaoOrcamento.CANCELADO && cancelado)) {
				resp.add(c);
			}
		}
		return resp;
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
	
	@SuppressWarnings("unchecked")
	private void carregarFieldAmbientesEvento(Local local) {
		if(fieldAmbientesEvento != null) {
			fieldAmbientesEvento.removeAllItems();
			fieldAmbientesEvento.addItem("Nenhum");
			if(local != null) {
				if(local.getSalaLocals() != null) {
					for(SalaLocal rec: local.getSalaLocals()) {
						fieldAmbientesEvento.addItem(rec);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldRecursoGrupo() {
		if(fieldEquipamentoGrupo != null) {
			fieldEquipamentoGrupo.removeAllItems();
			List<Grupo> lista = Facade.getInstance().listarGrupos();
			fieldEquipamentoGrupo.addItem(" ");
			for(Grupo rec: lista) {
				if(rec.getTipoRecurso() == TipoRecurso.EQUIPAMENTO) {
					fieldEquipamentoGrupo.addItem(rec);
				}
			}
			fieldEquipamentoGrupo.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldTerceirizadoGrupo() {
		if(fieldTerceirizadoGrupo != null) {
			fieldTerceirizadoGrupo.removeAllItems();
			List<Grupo> lista = Facade.getInstance().listarGrupos();
			fieldTerceirizadoGrupo.addItem(" ");
			for(Grupo rec: lista) {
				if(rec.getTipoRecurso() == TipoRecurso.TERCEIRIZADO) {
					fieldTerceirizadoGrupo.addItem(rec);
				}
			}
			fieldTerceirizadoGrupo.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldCenografia() {
		if(fieldCenografiaGrupo != null) {
			fieldCenografiaGrupo.removeAllItems();
			List<Grupo> lista = Facade.getInstance().listarGrupos();
			fieldCenografiaGrupo.addItem(" ");
			for(Grupo rec: lista) {
				if(rec.getTipoRecurso() == TipoRecurso.CENOGRAFIA) {
					fieldCenografiaGrupo.addItem(rec);
				}
			}
			fieldCenografiaGrupo.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldLogistica() {
		if(fieldLogisticaGrupo != null) {
			fieldLogisticaGrupo.removeAllItems();
			List<Grupo> lista = Facade.getInstance().listarGrupos();
			fieldLogisticaGrupo.addItem(" ");
			for(Grupo rec: lista) {
				if(rec.getTipoRecurso() == TipoRecurso.LOGISTICA) {
					fieldLogisticaGrupo.addItem(rec);
				}
			}
			fieldLogisticaGrupo.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldRecursoHumanoGrupo() {
		if(fieldEquipeTecnicaGrupo != null) {
			fieldEquipeTecnicaGrupo.removeAllItems();
			List<Grupo> lista = Facade.getInstance().listarGrupos();
			fieldEquipeTecnicaGrupo.addItem(" ");
			for(Grupo rec: lista) {
				if(rec.getTipoRecurso() == TipoRecurso.EQUIPE_TECNICA) {
					fieldEquipeTecnicaGrupo.addItem(rec);
				}
			}
		}
	}
		
	private void carregarRecursos() {
		List<Recurso> lista = Facade.getInstance().listarRecursos();
		listaRecursos = lista;
	}
	
	private void carregarTerceirizados() {
		List<RecursoTerceirizado> lista = Facade.getInstance().listarRecursoTerceirizados();
		listaRecursosTerceirizados = lista;
	}
	
	
	@SuppressWarnings("unchecked")
	private void carregarFieldRecursoNome() {
		if(fieldEquipamentoNome != null) {
			fieldEquipamentoNome.removeAllItems();
			fieldEquipamentoNome.addItem(" ");
			for(Recurso rec: listaRecursos) {
				if(rec.getGrupo().getId() == ((Grupo)fieldEquipamentoGrupo.getSelectedItem()).getId()){
					fieldEquipamentoNome.addItem(rec);
				}

			}
			fieldEquipamentoNome.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldTerceirizadoNome() {
		if(fieldTerceirizadoNome != null) {
			fieldTerceirizadoNome.removeAllItems();
			fieldTerceirizadoNome.addItem(" ");
			for(RecursoTerceirizado rec: listaRecursosTerceirizados) {
				if(rec.getGrupo().getId() == ((Grupo)fieldTerceirizadoGrupo.getSelectedItem()).getId()){
					fieldTerceirizadoNome.addItem(rec);
				}
			}
			fieldTerceirizadoNome.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldRecursoHumanoNome() {
		if(fieldEquipeTecnicaNome != null) {
			fieldEquipeTecnicaNome.removeAllItems();
			fieldEquipeTecnicaNome.addItem(" ");
			for(Recurso rec: listaRecursos) {
				if(rec.getGrupo().getId() == ((Grupo)fieldEquipeTecnicaGrupo.getSelectedItem()).getId()) {
					fieldEquipeTecnicaNome.addItem(rec);
				}
			}
			fieldEquipeTecnicaNome.setSelectedItem(" ");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void carregarFieldCenografiaNome() {
		if(fieldCenografiaNome != null) {
			fieldCenografiaNome.removeAllItems();
			fieldCenografiaNome.addItem(" ");
			for(Recurso rec: listaRecursos) {
				if(rec.getGrupo().getId() == ((Grupo)fieldCenografiaGrupo.getSelectedItem()).getId()) {
					fieldCenografiaNome.addItem(rec);
				}

			}
			fieldCenografiaNome.setSelectedItem(" ");
		}
	}	
	
	@SuppressWarnings("unchecked")
	private void carregarFieldLogisticaNome() {
		if(fieldLogisticaNome != null) {
			fieldLogisticaNome.removeAllItems();
			fieldLogisticaNome.addItem(" ");
			for(Recurso rec: listaRecursos) {
				if(rec.getGrupo().getId() == ((Grupo)fieldLogisticaGrupo.getSelectedItem()).getId()) {
					fieldLogisticaNome.addItem(rec);
				}
			}
			fieldLogisticaNome.setSelectedItem(" ");

		}
	}	

	
	@SuppressWarnings("unchecked")
	private void carregarFieldFuncionario() {
		if(fieldFuncionario != null) {
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
	}
	
	
	@SuppressWarnings("unchecked")
	private void carregarFieldStatusProposta() {
		fieldStatusProposta.removeAllItems();
		for(SituacaoOrcamento f: SituacaoOrcamento.values()) {
			fieldStatusProposta.addItem(f);
		}
		fieldStatusProposta.setSelectedItem(SituacaoOrcamento.ABERTO);
	}


	public boolean print(Orcamento current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(orc != null) {
			hm.put("id", orc.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			hm.put("cidade", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
			ImageIcon logos =  new ImageIcon(getClass().getResource("/images/logos.png"));
			
			hm.put("logos", logos.getImage());
			hm.put("SUBREPORT_DIR",getClass().getResource("/br/com/sne/sistema/report/").getPath().replace("%20"," "));

			
			try {
				Connection c  = Facade.getInstance().getConnection();
				//URL arquivo = getClass().getResource("/br/com/sne/sistema/report/orcamento_" + Facade.getInstance().getUsuarioLogado().getUnidade().getCodigo()+ ".jasper");
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/orcamento_01.jasper");
				
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
			JOptionPane.showMessageDialog(this, "Selecione um Orçamento para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}
	
	private JPanel getPanelImportarOrcamento() {
		JPanel pOrcamento = new JPanel();
		pOrcamento.setLayout(new FlowLayout(FlowLayout.LEFT));
		pOrcamento.add(new TitledPanel("Orçamento", carregarOrcButton()), new RestricaoLayout(0, 0, 1, true, false));
		pOrcamento.add(new TitledPanel(" ", getBotalAlterarDatInicial()) );
		pOrcamento.add(new TitledPanel(" ", getBotaoImportarOrcamento()));
		return pOrcamento;
	}
	
	
	private JButton getBotalAlterarDatInicial() {
		JButton botaoAlterarDataInicial = new JButton("Alterar Datas", new ImageIcon(getClass().getResource("/images/calendar_18.png")));
		botaoAlterarDataInicial.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						disableDateEvent = true;
						
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
						disableDateEvent = false;
						atualizarDataInicio();
						atualizarDataFim();
						atualizarDataMontagem();
					}
				}
		);
		return botaoAlterarDataInicial;
	}
}

