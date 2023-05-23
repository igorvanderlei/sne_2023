package br.com.sne.sistema.gui.receita;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.ibm.icu.text.DateFormat;
import com.toedter.calendar.JDateChooser;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.AlertaOSEmergencial;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.OrdemServicoSemEquipamento;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusCliente;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.orcamento.RecursosSolicitadosTableModel;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.DateCellRenderer;
import br.com.sne.sistema.gui.util.components.FlashingTab;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorUnbounded;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.MoedaCellEditor;
import br.com.sne.sistema.gui.util.components.MoedaCellRenderer;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.RestricaoLayoutH;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.StringChooserDialog;
import br.com.sne.sistema.gui.util.form.ZebraDecorator;

public class FormPagamentoOS extends DefaultForm<OrdemServico, OrdemServicoTableModel> {
	private static final long serialVersionUID = 1L;

	private Cliente cliente = null;

	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;
	private JTextField fieldClienteCnpj;
	private JTextField fieldClienteTelefone;
	private JTextField fieldClienteRamal;
	private JTextField fieldClienteContato;
	private JTextField fieldClienteCelular;
	private JCheckBox fieldEmpenho;
	private JCheckBox fieldEmpenhoParcelas;

	private JTextField fieldNomeEvento;
	private JTextField fieldFuncionario;
	private JTextField fieldDataInicio;
	private JTextField fieldDataFim;
	private JTextField fieldDataMontagem;
	private JTextArea fieldObservacoes;
	private JTextArea fieldObservacoesLocalEvento;
	private JTextArea fieldObservacoesFaturamento;
	private JTextField fieldStatusOS;

	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;
	
	private JCheckBox fieldConfirmacaoFaturamento;

	private JTextField fieldLocalNome;

	private JTable tabelaRecursosSolicitados;
	private RecursosSolicitadosTableModel modelRecurso;

	private JTabbedPane tabDetalhes;
	private JLabel fieldPrecoTotal;
	
	
	private JTable tabelaParcelas;
	private ReceitaTableModel modelParcelas;
	private JTextField fieldQuantidadeParcela;
	private JDateChooser fieldDataPrimeiraParcela;
	private JButton botaoGerarParcelas;
	
	private JMoedaRealTextField fieldValorParcela;
	private JDateChooser fieldDataParcela;
	private JTextField fieldDescricaoParcela;
	private JButton botaoAdicionarParcela;
	
	private JPopupMenu menuTabela;

	private OrdemServico ordemServ = null;
	
	private List<Receita> parcelasRemovidas;
	
	private TableRowSorter<OrdemServicoTableModel> sorter;
	
	private JDateChooser fieldFiltroDataInicio;
	private JDateChooser fieldFiltroDataFim;
	private JComboBox fieldFiltroStatus;
	
	private List<OrdemServico> listaCache;
	
	private Timer timer = new Timer(60000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Verificar OS Emergencial");
			verificarOSEmergencial();
		}
	});

	private JTable tabelaOSEmergencial;

	private OrdemServicoTableModel tableModelEmergencial;

	private JTextField jTextFielBuscaEmergencial;

	private JComboBox jComboBoxFiltroEmergencial;

	//private JPopupMenu menuExcluirEmergencial;
	
	
	private void verificarOSEmergencial(){
		List<AlertaOSEmergencial> listaEmergenciais = Facade.getInstance().listarAlertaOSEmergencials();
		if(listaEmergenciais.size() > 0) {
			((FlashingTab) getJTabbedPane()).flash(2);
			carregarTabelaEmergencial(listaEmergenciais);
		} else {
			((FlashingTab) getJTabbedPane()).clearFlashing();
			OrdemServicoTableModel modelo = (OrdemServicoTableModel) tabelaOSEmergencial.getModel();
			modelo.removeAllElements();
		}
		
	}
	
	
	private void inicializarCampoFiltro() {
		fieldFiltroDataInicio = new JDateChooser();
		fieldFiltroDataFim = new JDateChooser();
		fieldFiltroStatus = new JComboBox();
		JButton botaoFiltrar = new JButton("Listar");
		
		fieldFiltroStatus.addItem("Todos");
		fieldFiltroStatus.addItem(StatusOS.PENDENTE);
		fieldFiltroStatus.addItem(StatusOS.APROVADA);
		fieldFiltroStatus.addItem(StatusOS.CONCLUIDA);
		fieldFiltroStatus.addItem(StatusOS.EM_REALIZACAO);
		fieldFiltroStatus.addItem(StatusOS.OS_SEM_EQUIPAMENTO);
		fieldFiltroStatus.addItem(StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA);
		
		botaoFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<OrdemServico> lista = new ArrayList<OrdemServico>();
				if(fieldFiltroDataInicio.getDate() == null || fieldFiltroDataFim.getDate() == null) {
					if(fieldFiltroStatus.getSelectedItem() instanceof StatusOS) {
						lista = Facade.getInstance().listarOrdemServicosPagamento((StatusOS) fieldFiltroStatus.getSelectedItem()); 
					} else{
						lista = Facade.getInstance().listarOrdemServicosPagamento();
					}
				} else {
					if(fieldFiltroStatus.getSelectedItem() instanceof StatusOS) {
						StatusOS status = (StatusOS) fieldFiltroStatus.getSelectedItem(); 
						lista = Facade.getInstance().listarOrdemServicosPagamento(fieldFiltroDataInicio.getDate(), fieldFiltroDataFim.getDate(), status);
					} else {
						lista = Facade.getInstance().listarOrdemServicosPagamento(fieldFiltroDataInicio.getDate(), fieldFiltroDataFim.getDate(),true);
					}
				}
				listaCache = lista;
				carregarTabela(lista);
			}
		});
		
		JPanel painelFiltro = new JPanel();
		painelFiltro.setLayout(new GridBagLayout());
		
		painelFiltro.add(new TitledPanel("Data inicial", fieldFiltroDataInicio), new RestricaoLayout(0,0, false, false));
		painelFiltro.add(new TitledPanel("Data Final", fieldFiltroDataFim), new RestricaoLayout(0,1,  false, false));
		painelFiltro.add(new TitledPanel("Status", fieldFiltroStatus), new RestricaoLayout(0,2, 1, 1, true, false));
		painelFiltro.add(new TitledPanel(" ", botaoFiltrar), new RestricaoLayout(0,3, false, false));
		
		getPanelFiltro().setLayout(new BorderLayout());
		getPanelFiltro().add(painelFiltro, BorderLayout.CENTER);
		
	}
	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		
		if(timer != null) {
			if(!aFlag){
				timer.stop();
			} else {
				timer.start();
			}
		}
	}



	private void carregarTabelaEmergencial(List<AlertaOSEmergencial> listElements){
		jTextFielBuscaEmergencial.setText("");
		OrdemServicoTableModel modelo = (OrdemServicoTableModel) tabelaOSEmergencial.getModel();
		modelo.removeAllElements();
		for(AlertaOSEmergencial t : listElements) {
			modelo.addElement(t.getOsEmergencial());
		}
		tabelaOSEmergencial.updateUI();
	}

	public FormPagamentoOS() {
		super(new OrdemServicoTableModel(), "/images/produto20.png", "Liberação de Ordem de Serviço");
		desabilitarBotaoAdicionar();
		desabilitarBotaoNovo();
		desabilitarBotaoRemover();
		inicializarCampoFiltro();
		getJTable().getColumnModel().getColumn(5).setCellRenderer(new MoedaCellRenderer());
		getJTable().getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer());
		
		//getJTabbedPane().addTab("OS Emergencial", getPanelOSEmergencial());
		
		getJTabbedPane().addTab("Alerta de OS Emergencial", new ImageIcon(getClass().getResource("/images/alerta_12.png")), getPanelOSEmergencial(), null);
		verificarOSEmergencial();
		timer.start();
	}
	
	
	private JPanel getPanelOSEmergencial() {
		JPanel jPanelRelatorio;

		GridBagConstraints gridBagConstraints1Botoes = new GridBagConstraints();
		gridBagConstraints1Botoes.insets = new Insets(2, 5, 5, 5);
		gridBagConstraints1Botoes.ipady = 10;
		gridBagConstraints1Botoes.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1Botoes.weightx = 1;
		gridBagConstraints1Botoes.gridx = 0;
		gridBagConstraints1Botoes.gridy = 0;

		GridBagConstraints gridBagConstraintsTabela = new GridBagConstraints();
		gridBagConstraintsTabela.insets = new Insets(5, 5, 0, 5);
		gridBagConstraintsTabela.gridx = 0;
		gridBagConstraintsTabela.gridy = 1;
		gridBagConstraintsTabela.ipadx = 915;
		gridBagConstraintsTabela.ipady = 448;
		gridBagConstraintsTabela.fill = GridBagConstraints.BOTH;
		gridBagConstraintsTabela.weightx = 1;
		gridBagConstraintsTabela.weighty = 1;


		/*menuExcluirEmergencial = new JPopupMenu();
		menuExcluirEmergencial.add(getMenuExcluirOSEmergencial());*/
		
		jPanelRelatorio = new JPanel();
		jPanelRelatorio.setLayout(new GridBagLayout());
		jPanelRelatorio.add(getJScrollTabelaEmergencial(), gridBagConstraintsTabela);
		jPanelRelatorio.add(getJTabbedPaneStatusEmergencial(), gridBagConstraints1Botoes);
		carregarComboListarEmergencial();

		return jPanelRelatorio;
	}
	
/*	private JMenuItem getMenuExcluirOSEmergencial() {
		JMenuItem mnu = new JMenuItem("Excluir Alerta");
		mnu.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
		mnu.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				if(tabelaOSEmergencial.getSelectedRowCount() > 0) {
					OrdemServico current = (OrdemServico) tabelaOSEmergencial.getValueAt(tabelaOSEmergencial.getSelectedRow(), tableModelEmergencial.getObjectIndex());
					int resp = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir o alerta da OS emergencial selecionado","Escolha uma opção" ,JOptionPane.OK_CANCEL_OPTION);
					if(resp == JOptionPane.OK_OPTION){
						boolean podeExcluir = true;
						for(EquipamentoEnviado eqv : current.getEquipamentoEnviado()) {
							if(eqv.isStatus()) {
								podeExcluir = false;
								break;
							}
						}
						if(podeExcluir) {
							current.setStatus(StatusOS.OS_EMERGENCIAL_CONCLUIDA);
							Facade.getInstance().atualizarOrdemServico(current);
						
							verificarOSEmergencial();
							JOptionPane.showMessageDialog(null,"Alerta excluído com sucesso!");
						} else {
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Esta OS Emergencial não pode ser removida pois existem equipamentos enviados para a mesma! \n Solicite que seja  feita uma OS de passagem destes equipamentos para a OS definitiva!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			}
		});
		return mnu;
	}*/
	
	
	private void carregarComboListarEmergencial(){
		jComboBoxFiltroEmergencial.removeAllItems();
		String [] col = tableModelEmergencial.getFilterColumnName();
		jComboBoxFiltroEmergencial.addItem(" ");
		for(String c: col) {
			if(!c.equals("X"))
				jComboBoxFiltroEmergencial.addItem(c);
		}
	}
	
	private JScrollPane getJScrollTabelaEmergencial() {
		JScrollPane jScrollPane;
		jScrollPane = new JScrollPane();
		jScrollPane.setName("jScrollPane");
		jScrollPane.setViewportView(getJTableEmergencial());
		return jScrollPane;
	}
	
	private JTabbedPane getJTabbedPaneStatusEmergencial() {
		JTabbedPane jTabbedPaneStatus = null;
		if (jTabbedPaneStatus == null) {
			jTabbedPaneStatus = new JTabbedPane();
			jTabbedPaneStatus.setName("jTabbedPaneStatus");
			jTabbedPaneStatus.addTab("Filtro", null, getJPanelContemPaneisFiltroEmergencial(), null);
		}
		return jTabbedPaneStatus;
	}
	
	private JPanel getJPanelContemPaneisFiltroEmergencial() {
		JPanel jPanelContemPaneisFiltro = null;
		if (jPanelContemPaneisFiltro == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 0;
			gridBagConstraints17.weightx = 1;
			gridBagConstraints17.insets = new Insets(0, 3, 6, 3);
			
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.weightx = 1;
			gridBagConstraints16.insets = new Insets(0, 3, 6, 3);

			
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.NONE;
			gridBagConstraints18.gridx = 2;
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.insets = new Insets(0, 3, 6, 3);
			
			
			jPanelContemPaneisFiltro = new JPanel();
			jPanelContemPaneisFiltro.setLayout(new GridBagLayout());
			jPanelContemPaneisFiltro.add(getJComboBoxFiltroEmergencial(), gridBagConstraints17);
			jPanelContemPaneisFiltro.add(getJTextFieldBuscaEmergencial(), gridBagConstraints16);
			jPanelContemPaneisFiltro.add(getJButtonLimpaFiltroEmergencial(), gridBagConstraints18);
			
		}
		return jPanelContemPaneisFiltro;
	}
	
	private JButton getJButtonLimpaFiltroEmergencial() {
		JButton jButtonLimpaFiltro = null;
		if (jButtonLimpaFiltro == null) {
			jButtonLimpaFiltro = new JButton();
			jButtonLimpaFiltro.setName("jButtonLimpaFiltro");
		//	jButtonLimpaFiltro.setIcon(new ImageIcon("images/Kde_crystalsvg_eraser32.png"));
			jButtonLimpaFiltro.setText("Limpar Filtro");
			jButtonLimpaFiltro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					limparFiltroEmergencial();
				}
			});
		}
		return jButtonLimpaFiltro;
	}
	
	private void limparFiltroEmergencial() {
		jTextFielBuscaEmergencial.setText("");
		jComboBoxFiltroEmergencial.setSelectedIndex(0);		
	}
	
	private JComboBox getJComboBoxFiltroEmergencial() {
		jComboBoxFiltroEmergencial = null;
		if (jComboBoxFiltroEmergencial == null) {
			jComboBoxFiltroEmergencial = new JComboBox();
			jComboBoxFiltroEmergencial.setName("jComboBox");
		}
		return jComboBoxFiltroEmergencial;
	}
	
	protected JTable getJTableEmergencial() {
		tableModelEmergencial = new OrdemServicoTableModel();;
		if (tabelaOSEmergencial == null) {
			sorter = new TableRowSorter<OrdemServicoTableModel>(tableModelEmergencial);
	        tabelaOSEmergencial = new JTable(tableModelEmergencial);
	        tabelaOSEmergencial.setRowSorter(sorter);
			tabelaOSEmergencial.setFillsViewportHeight(true);
			tabelaOSEmergencial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			ZebraDecorator zebra = new ZebraDecorator();
			for(int i=0; i < tabelaOSEmergencial.getColumnCount(); i++) {
				tabelaOSEmergencial.getColumnModel().getColumn(i).setCellRenderer(zebra);
			}
			
			TableColumn column = null;
			int columnWidth[] = tableModelEmergencial.getColumnWidth();
			for (int i = 0; i < columnWidth.length; i++) {
			    column = tabelaOSEmergencial.getColumnModel().getColumn(i);
			    try {
			    	column.setPreferredWidth(columnWidth[i]);
			    } catch(RuntimeException errr){
			    	errr.printStackTrace();
			    }
			}
			
			/*tabelaOSEmergencial.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent arg0) {
							if(arg0.getButton() == MouseEvent.BUTTON3) {
								if(tabelaOSEmergencial.getSelectedRow() >= 0 )
									if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.FINALIZAR_OS_EMERGENCIAL))
										menuExcluirEmergencial.show(tabelaOSEmergencial, arg0.getX(), arg0.getY());
							} 						
						}
					}
			
			);*/
			
		}
		return tabelaOSEmergencial;
	}
	
	
	private JTextField getJTextFieldBuscaEmergencial() {
		jTextFielBuscaEmergencial = null;
		if (jTextFielBuscaEmergencial == null) {
			jTextFielBuscaEmergencial = new JTextField();
			jTextFielBuscaEmergencial.setName("jTextFielBusca");

		     jTextFielBuscaEmergencial.getDocument().addDocumentListener(new DocumentListener(){
		    	 
		    	 public void changedUpdate(DocumentEvent e) {
		    		 newFilterEmergencial();
				}
		    	 
		    	 public void insertUpdate(DocumentEvent e){
		    		 newFilterEmergencial();
		    	 }
		    	 
		    	 public void removeUpdate(DocumentEvent e){
		    		 newFilterEmergencial();
		    	 }
		     
		     });
		    	 
		}
		return jTextFielBuscaEmergencial;
	}
	
	  private void newFilterEmergencial() {
	        RowFilter<OrdemServicoTableModel, Object> rf2 = null;
	        try {
	        	
	        	String campoFiltro = (String) jComboBoxFiltroEmergencial.getSelectedItem();
	        	
	        	String col[] = tableModelEmergencial.getFilterColumnName();
	        	for(int i=0; i < col.length; i++) {

	        		if(campoFiltro.equalsIgnoreCase(col[i])){
	            		if(campoFiltro.toLowerCase().trim().startsWith("valor")) {
	            			try{
	            				NumberFormat formato = NumberFormat.getNumberInstance(); 
	            				Number numero = formato.parse(jTextFielBuscaEmergencial.getText());
	            				rf2 = RowFilter.numberFilter(ComparisonType.EQUAL, numero , i);
	            			} catch (Exception excp) {}
	            			break;
	            		}
	        			
	        			rf2 = RowFilter.regexFilter("(?i)" + jTextFielBuscaEmergencial.getText(), i);
	        			break;
	        		}

	        		
	        	}
	        	
	        	if(campoFiltro.equalsIgnoreCase(" ")){
					JOptionPane.showMessageDialog(null," Selecione uma das opcoes para realizacao o filtro ");
					return;
				}
	    
	        } catch (java.util.regex.PatternSyntaxException e) {
	        	e.printStackTrace();
	            return;
	        }
	        sorter.setRowFilter(rf2);
		}
	
	
	public boolean save(OrdemServico current) {
		return false;
	}

	public boolean update(OrdemServico current) {
		boolean s = true;
		List<Receita> parcelas = modelParcelas.getListaReceita();
		
		if(current.getStatus() == StatusOS.ESTORNADA) {
			JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível alterar uma Ordem de Serviço Estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Pega o status atual do cliente sem a necesidade de recarregar a OS selecionada
		Cliente cl2 = Facade.getInstance().carregarCliente(current.getCliente().getId());
		if(cl2.getStatus() != StatusCliente.ATIVO && current.getStatus() == StatusOS.PENDENTE) {
			JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível aprovar a realização desta OS, \npois o cliente não se encontra no status ATIVO. \n\nAtualize o cadastro de cliente antes de prosseguir.", "ERRO", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		
		for(Receita r: parcelas) {
			if(r.getId() == null) {
				Facade.getInstance().salvarReceita(r);
			} else{
				System.out.println("===================================>" + r.getValor() + ", "+ r.getDataVencimento());
				Facade.getInstance().atualizarReceita(r);
			}
		}
		for(Receita r:parcelasRemovidas) {
			Facade.getInstance().removerReceita(r);
		}
		current.setObservacoesFaturamento(fieldObservacoesFaturamento.getText());
		current.setConfirmacaoFaturamento(fieldConfirmacaoFaturamento.isSelected());
		if(current.getStatus() == StatusOS.PENDENTE){
			current.setStatus(StatusOS.APROVADA);
			current.setDataAprovacao(new Date());
			current.setFuncionarioAprovacao(Facade.getInstance().getUsuarioLogado().getFuncionario());
			
		}
		if(current.getStatus() == StatusOS.OS_SEM_EQUIPAMENTO){
			current.setStatus(StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA);
			current.setDataAprovacao(new Date());
			current.setFuncionarioAprovacao(Facade.getInstance().getUsuarioLogado().getFuncionario());
			
			
			OrdemServico emergencial = ((OrdemServicoSemEquipamento) current).getOrdemServicoEmergencial();
			
			AlertaOSEmergencial alerta = Facade.getInstance().localizarAlertaPorEmergencial(emergencial);
			if(alerta != null) {
				alerta.setOsSemEquipamento(current);
				alerta.setStatus(true);
				Facade.getInstance().atualizarAlertaOSEmergencial(alerta);
			} else {
				alerta = new AlertaOSEmergencial();
				alerta.setOsEmergencial(emergencial);
				alerta.setOsSemEquipamento(current);
				alerta.setStatus(true);
				Facade.getInstance().salvarAlertaOSEmergencial(alerta);
			}
			verificarOSEmergencial();
		}
		Facade.getInstance().atualizarOrdemServico(current);
		return s;
	}

	public boolean remove(OrdemServico current) {
		return false;
	}

	public void createInputFields() {
		this.addInputField(getPanelCliente(), new RestricaoLayout(0, 0, 2, true, false));
		this.addInputField(getTabDetalhes(), new RestricaoLayout(1, 0, 2, 1, true, true));
		this.addInputField(getPanelImportarOrcamento(), new RestricaoLayout(2,0,1,true,false));
		this.addInputField(getPanelPrecoTotal(), new RestricaoLayout(2,1,1,true,false));
	}

	public JPanel getPanelCliente() {
		JPanel panelCliente = new JFormGroup("Dados do Cliente");
		panelCliente.setLayout(new GridBagLayout());
		fieldClienteID = new JIntField();
		fieldClienteCnpj = new JCnpjField();
		fieldClienteTelefone = new JFoneField();
		fieldClienteRamal = new JTextField();
		fieldClienteContato = new JTextField();
		fieldClienteCelular = new JFoneField();

		fieldClienteCnpj.setEditable(false);
		fieldClienteTelefone.setEditable(false);
		fieldClienteRamal.setEditable(false);
		fieldClienteContato.setEditable(false);
		fieldClienteCelular.setEditable(false);

		panelCliente.add(new TitledPanel("Código", getPanelCodigoCliente()), new RestricaoLayout(0, 0, false, false));
		panelCliente.add(new TitledPanel("Razão Social", getPanelNomeCliente()), new RestricaoLayout(0, 1, 5, true, false));
		panelCliente.add(new TitledPanel("CNPJ", fieldClienteCnpj), new RestricaoLayout(0, 6, true, false));

		panelCliente.add(new TitledPanel("Telefone", fieldClienteTelefone), new RestricaoLayout(1, 0, 2, true, false));
		panelCliente.add(new TitledPanel("Ramal", fieldClienteRamal), new RestricaoLayout(1, 2, false, false));
		panelCliente.add(new TitledPanel("Contato", fieldClienteContato), new RestricaoLayout(1, 3, 2, true, false));
		panelCliente.add(new TitledPanel("Celular", fieldClienteCelular), new RestricaoLayout(1, 5, 2, true, false));

		return panelCliente;
	}

	public JPanel getPanelCodigoCliente() {
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(new BoxLayout(panelCliente, BoxLayout.LINE_AXIS));
		fieldClienteID.setEditable(false);
		panelCliente.add(fieldClienteID);
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
		tabDetalhes.addTab("Pagamento", getPanelPagamento());
		tabDetalhes.addTab("Faturamento", getPanelFaturamento());
		tabDetalhes.addTab("Dados do Evento", getPanelDadosEvento());
		tabDetalhes.addTab("Local do Evento", getPanelEndereco());		
		tabDetalhes.addTab("Recursos Solicitados", getPanelRecursos());
		return tabDetalhes;
	}
	
	private JPanel getPanelFaturamento() {
		JPanel painel = new JPanel();
		fieldConfirmacaoFaturamento = new JCheckBox("Confirmação de Faturamento");
		painel.setLayout(new BorderLayout());
		fieldObservacoesFaturamento = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(fieldObservacoesFaturamento);
		painel.add(scroll, BorderLayout.CENTER);
		painel.add(fieldConfirmacaoFaturamento, BorderLayout.SOUTH);
		return painel;
		
	}
	
	private JPanel getPanelPagamento() {
		JPanel panelPagamento = new JPanel();
		panelPagamento.setLayout(new GridBagLayout());
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		modelParcelas = new ReceitaTableModel();
		tabelaParcelas = new JTable(modelParcelas);
		tabelaParcelas.getColumnModel().getColumn(2).setCellEditor(new JDateChooserCellEditorUnbounded());
		tabelaParcelas.getColumnModel().getColumn(3).setCellEditor(new MoedaCellEditor());
		
		
		tabelaParcelas.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaParcelas.getSelectedRow() >= 0 )
								menuTabela.show(tabelaParcelas, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione uma Parcela para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}

		);
		
		
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
		panelParcelas.add(new TitledPanel("Descrição", fieldDescricaoParcela), new RestricaoLayout(1,2,1,true,false));
		panelParcelas.add(new TitledPanel(" ", fieldEmpenho), new RestricaoLayout(1,3,false,false));
		panelParcelas.add(new TitledPanel(" ", botaoAdicionarParcela), new RestricaoLayout(1,4,false,false));
		

		
		fieldQuantidadeParcela = new JIntField();
		fieldDataPrimeiraParcela = new JDateChooser();
		fieldEmpenhoParcelas = new JCheckBox("Empenho");
		
		panelPagamento.add(new TitledPanel("Quantidade de Parcelas", fieldQuantidadeParcela), new RestricaoLayout(0, 0, 1, true, false));
		panelPagamento.add(new TitledPanel("Data da Primeira Parcela", fieldDataPrimeiraParcela), new RestricaoLayout(0, 1, 1, true, false));
		panelPagamento.add(new TitledPanel(" ", fieldEmpenhoParcelas), new RestricaoLayout(0, 2, false, false));
		panelPagamento.add(new TitledPanel(" ", getBotaoGerarParcelas()), new RestricaoLayout(0, 3, false, false));
		
		panelPagamento.add(new TitledPanel("Parcelas", panelParcelas), new RestricaoLayout(1, 0, 4, 1, true, true));
		
		
		return panelPagamento;
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Parcela");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Receita rem = (Receita)modelParcelas.getValueAt(tabelaParcelas.getSelectedRow(), modelParcelas.getObjectIndex());
						if(ordemServ.getStatus() == StatusOS.ESTORNADA) {
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível alterar uma Ordem de Serviço Estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if(rem.isSituacao()) {
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível remover uma parcela paga", "ERRO", JOptionPane.ERROR_MESSAGE);
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
	
	private JButton getBotaoGerarParcelas() {
		botaoGerarParcelas = new JButton("Gerar Parcelas");
		botaoGerarParcelas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(ordemServ==null) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Selecione uma Ordem de Serviço", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(fieldQuantidadeParcela.getText().equals("") || Integer.parseInt(fieldQuantidadeParcela.getText()) <= 0) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Informe a quantidade de parcelas!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(fieldDataPrimeiraParcela.getDate() == null) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Informe a data da primeira parcela!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int quantidadeParcela = Integer.parseInt(fieldQuantidadeParcela.getText());
				
				
				GregorianCalendar dataParcela = new GregorianCalendar();
				dataParcela.setTime(fieldDataPrimeiraParcela.getDate());
				BigDecimal totalPagar;
				if(ordemServ.getDesconto() != null)
					totalPagar = ordemServ.getPreco().subtract(ordemServ.getDesconto());
				else
					totalPagar = ordemServ.getPreco().subtract(BigDecimal.ZERO);
				
				
				BigDecimal valorParcela = new BigDecimal((totalPagar.intValue()* 100 /quantidadeParcela)/100);
				BigDecimal valorUltimaParcela = totalPagar.subtract(valorParcela.multiply(new BigDecimal(quantidadeParcela - 1)));
				boolean empenho = fieldEmpenhoParcelas.isSelected();
				
				for(int i=1; i <= quantidadeParcela; i++) {
					Receita rec = new Receita();
					rec.setDataCadastro(new Date());
					rec.setDataVencimento(new Date(dataParcela.getTimeInMillis()));
					rec.setDescricao("Parcela " + i + " de " +  quantidadeParcela);
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
	
	private JButton getBotaoAdicionarParcela() {
		botaoGerarParcelas = new JButton("Adicionar Parcela");
		botaoGerarParcelas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(ordemServ==null) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Selecione uma Ordem de Serviço", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(ordemServ.getStatus() == StatusOS.ESTORNADA) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível adicionar parcelas em uma Ordem de Serviço Estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				BigDecimal valor= new BigDecimal(0);
				try {
					if(fieldValorParcela.getValor() == null) {
						JOptionPane.showMessageDialog(FormPagamentoOS.this, "Informe o valor da parcela", "ERRO", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						valor = fieldValorParcela.getValor();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Informe o valor da parcela", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(fieldDataParcela.getDate() == null) {
					JOptionPane.showMessageDialog(FormPagamentoOS.this, "Informe a data da parcela!", "ERRO", JOptionPane.ERROR_MESSAGE);
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
				botaoGerarParcelas.setEnabled(false);
				limparParcela();
			}
		});
		
		return botaoGerarParcelas;
		
	}
	

	private JPanel getPanelEndereco () {
		JPanel endereco = new JPanel();
		endereco.setLayout(new GridBagLayout());

		fieldEnderecoLogradouro = new JTextField();
		fieldEnderecoLogradouro.setEditable(false);
		fieldEnderecoNumero = new JTextField();
		fieldEnderecoNumero.setEditable(false);
		fieldEnderecoComplemento = new JTextField();
		fieldEnderecoComplemento.setEditable(false);
		fieldEnderecoCEP = new JCepField();
		fieldEnderecoCEP.setEditable(false);
		fieldEnderecoBairro = new JTextField();
		fieldEnderecoBairro.setEditable(false);
		fieldEnderecoCidade = new JTextField();
		fieldEnderecoCidade.setEditable(false);
		fieldEnderecoEstado = new JComboEstado();
		fieldEnderecoEstado.setEnabled(false);
		fieldEnderecoReferencia = new JTextField();
		fieldEnderecoReferencia.setEditable(false);
		fieldLocalNome = new JTextField();
		fieldLocalNome.setEditable(false);
		
		fieldObservacoesLocalEvento = new JTextArea();
		fieldObservacoesLocalEvento.setEditable(false);
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoesLocalEvento);
		
		endereco.add(new TitledPanel("Nome do Local", fieldLocalNome), new RestricaoLayout(0, 0, 6, true, false));

		endereco.add(new TitledPanel("Logradouro", fieldEnderecoLogradouro), new RestricaoLayout(1,0, 3, true, false));
		endereco.add(new TitledPanel("Numero", fieldEnderecoNumero), new RestricaoLayout(1,3, true, false));
		endereco.add(new TitledPanel("Complemento", fieldEnderecoComplemento), new RestricaoLayout(1,4, 1, true, false));
		endereco.add(new TitledPanel("CEP", fieldEnderecoCEP), new RestricaoLayout(1,5, 1, true, false));
		
		endereco.add(new TitledPanel("Bairro", fieldEnderecoBairro), new RestricaoLayout(2,0, 2, true, false));
		endereco.add(new TitledPanel("Cidade", fieldEnderecoCidade), new RestricaoLayout(2,2, 1, true, false));
		endereco.add(new TitledPanel("Estado", fieldEnderecoEstado), new RestricaoLayout(2,3, 1, true, false));
		endereco.add(new TitledPanel("Ponto de Referência", fieldEnderecoReferencia), new RestricaoLayout(2,4, 2, true, false));
		
		endereco.add(new TitledPanel("Observações do Local do Evento", scrollObservacoes), new RestricaoLayout(3,0,6,1, true, true));

		return endereco;
	}


	private JPanel getPanelDadosEvento() {
		fieldNomeEvento = new JTextField();
		fieldNomeEvento.setEditable(false);
		fieldFuncionario = new JTextField();
		fieldFuncionario.setEnabled(false);
		fieldDataInicio = new JTextField();
		fieldDataInicio.setEnabled(false);
		fieldDataFim = new JTextField();
		fieldDataFim.setEnabled(false);
		fieldDataMontagem = new JTextField();
		fieldDataMontagem.setEnabled(false);
		fieldObservacoes = new JTextArea();
		fieldObservacoes.setLineWrap(true);
		fieldObservacoes.setEditable(false);
		fieldStatusOS = new JTextField();
		fieldStatusOS.setEnabled(false);
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);

		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());

		painelDadosEvento.add(new TitledPanel("Nome do Evento", fieldNomeEvento), new RestricaoLayoutH(0, 0, 0.9f, true));
		painelDadosEvento.add(new TitledPanel("Início do Evento", fieldDataInicio), new RestricaoLayoutH(0, 1, 0.2f, true));
		painelDadosEvento.add(new TitledPanel("Fim do Evento", fieldDataFim), new RestricaoLayoutH(0, 2, 0.2f, true));
		painelDadosEvento.add(new TitledPanel("Montagem do Evento", fieldDataMontagem), new RestricaoLayoutH(0, 3, 0.2f, true));
		painelDadosEvento.add(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayoutH(0, 4, 0.6f, true));
		painelDadosEvento.add(new TitledPanel("Status", fieldStatusOS), new RestricaoLayoutH(0, 5, 0.1f, true));
		GridBagConstraints gbc = new RestricaoLayout(1, 0, 6, 1, true, true);
		gbc.weightx = 0;
		
		painelDadosEvento.add(new TitledPanel("Observações Financeiras", scrollObservacoes), gbc);

		return painelDadosEvento;		
	}

	public JPanel getPanelRecursos() {
		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());
		
		modelRecurso = new RecursosSolicitadosTableModel(true);
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());

		panelRecursos.add(new TitledPanel("Recursos Solicitados", scrollTabela), new RestricaoLayout(1,0,1,1,true,true));

		return panelRecursos;		
	}

	private JTable getTabelaRecursosSolicitados() {
		tabelaRecursosSolicitados = new JTable(modelRecurso);
		tabelaRecursosSolicitados.setEnabled(false);
		return tabelaRecursosSolicitados;
	}

	private JPanel getPanelPrecoTotal() {
		fieldPrecoTotal = new JLabel("R$ 0,00");
		fieldPrecoTotal.setFont(new Font("Arial", Font.BOLD, 24));
		fieldPrecoTotal.setForeground(Color.RED);

		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));
		total.add(fieldPrecoTotal);
		return total;
	}

	private JPanel getPanelImportarOrcamento() {
		JPanel pOrcamento = new JPanel();
		pOrcamento.setLayout(new FlowLayout(FlowLayout.LEFT));
		pOrcamento.add(getBotaoExtornar() );
		return pOrcamento;
	}
	
	private JButton getBotaoExtornar() {
		JButton botaoExtornar = new JButton("Estonar Ordem de Serviço" , new ImageIcon("images/Money-18x18.png"));
		botaoExtornar .addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						boolean possuiEquipamentoPendente = false;
						List<EquipamentoEnviado> equipamentos = ordemServ.getEquipamentoEnviado();
						for(EquipamentoEnviado eqv : equipamentos) {
							if(!eqv.isStatus()) {
								possuiEquipamentoPendente = true;
								break;
							}
						}
						if(possuiEquipamentoPendente){
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível estornar a Ordem de Serviço pois existem equipamentos pendentes.", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						boolean possuiParcelaPaga = false;
						List<Receita> receita = Facade.getInstance().listarReceitasPorOS(ordemServ);
						for(Receita rec: receita){
							if(rec.isSituacao()) {
								possuiParcelaPaga = true;
								break;
							}
						}
						if(possuiParcelaPaga) {
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Não é possível estornar a Ordem de Serviço pois existem parcelas pagas.", "ERRO", JOptionPane.ERROR_MESSAGE);
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
							
							Facade.getInstance().atualizarOrdemServico(ordemServ);
							JOptionPane.showMessageDialog(null, "Ordem de Serviço estornada com sucesso.");
							
						} else {
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Informe o motivo.\nA Ordem de Serviço não foi estornada.", "ERRO", JOptionPane.ERROR_MESSAGE);
							
						}

			
					}
				}
		);
		return botaoExtornar ;
	}

	private void calcularTotal(OrdemServico rec) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		if(rec.getPreco() != null) {
			if(rec.getDesconto() != null)
				fieldPrecoTotal.setText("R$ " + formato.format(rec.getPreco().subtract(rec.getDesconto())));
			else
				fieldPrecoTotal.setText("R$ " + formato.format(rec.getPreco()));
		}
		else
			fieldPrecoTotal.setText("R$ ");
	}

	public void carregarCliente(Cliente c) {
		cliente = c;
		if(c!=null){
			fieldClienteID.setText("" + c.getId());
			fieldClienteRazao.setText(c.getNome());
			fieldClienteCnpj.setText(c.getCnpj());
			fieldClienteTelefone.setText(c.getFone());
			fieldClienteRamal.setText(c.getRamal());
			fieldClienteContato.setText(c.getContato());
			fieldClienteCelular.setText(c.getCelular());
		} else {
			limparCliente();
		}
	}

	private void carregarLocal(Local l) {
		if(l!=null){
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
		} else {
			limparLocal();
		}
	}

	private void limparLocal() {
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("000000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");
		fieldLocalNome.setText("");
		fieldObservacoesLocalEvento.setText("");
	}

	public void limparCliente() {
		cliente = null;
		fieldClienteID.setText("");
		fieldClienteRazao.setText("");
		fieldClienteCnpj.setText("");
		fieldClienteTelefone.setText("");
		fieldClienteRamal.setText("");
		fieldClienteContato.setText("");
		fieldClienteCelular.setText("");
	}

	public JButton getBotaoAbrirCliente() {
		JButton botaoAbrirCliente = new JButton("", new ImageIcon("images/lente_24.png"));
		botaoAbrirCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setPreferredSize(new Dimension(20,20));

		botaoAbrirCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cliente != null) {
							FormCliente telaCliente = (FormCliente) WindowFactory.createTelaCliente(Facade.getInstance().getTelaPrincipal().getDesktop());
							telaCliente.setVisible(true);
							telaCliente.selecionarLinha(cliente);
							//telaCliente.loadForm(cliente);
						} else {
							JOptionPane.showMessageDialog(FormPagamentoOS.this, "Nenhum cliente selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
					}
				}	
		);
		return botaoAbrirCliente;
	}

	public OrdemServico newElement() {
		return new OrdemServico();
	}

	public void loadInputFields(OrdemServico os) {
	/*	os.setCliente(cliente);
		os.setRecursoSolicitado(modelRecurso.getRecursos());
		os.setStatus((StatusOS) fieldStatusOS.getSelectedItem());
		LocalEvento e = new LocalEvento();
		Endereco endereco=new Endereco();
		endereco.setLogradouro(fieldEnderecoLogradouro.getText());
		endereco.setNumero(fieldEnderecoNumero.getText());
		endereco.setComplemento(fieldEnderecoComplemento.getText());
		endereco.setCep(fieldEnderecoCEP.getText());
		endereco.setBairro(fieldEnderecoBairro.getText());
		endereco.setCidade(fieldEnderecoCidade.getText());
		endereco.setEstado((String) fieldEnderecoEstado.getSelectedItem());
		endereco.setPontoReferencia(fieldEnderecoReferencia.getText());
		e.setEndereco(endereco);
		e.setNome(fieldLocalNome.getText());
		e.setObservacoes(fieldObservacoesLocalEvento.getText());
		os.setLocal(e);
		os.setNomeEvento(fieldNomeEvento.getText());
		os.setFuncionario((Funcionario) fieldFuncionario.getSelectedItem());
		os.setDataInicio(fieldDataInicio.getDate());
		os.setDataFim(fieldDataFim.getDate());
		os.setDataMontagem(fieldDataMontagem.getDate());
		os.setObservacoes(fieldObservacoes.getText());
	*/}

	protected void loadForm(OrdemServico rec) {
		clear();
		parcelasRemovidas = new ArrayList<Receita>();
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
		carregarCliente(rec.getCliente());
		modelRecurso.removeAllElements();
		for(RecursoSolicitado r: rec.getRecursoSolicitado()) {
			modelRecurso.addElement(r);
		}
		carregarLocal(new Local(rec.getLocal()));
		calcularTotal(rec);
		DateFormat formatoData = DateFormat.getDateInstance();
		fieldConfirmacaoFaturamento.setSelected(rec.isConfirmacaoFaturamento());
		fieldNomeEvento.setText(rec.getNomeEvento());
		fieldFuncionario.setText(rec.getFuncionario().getNome());
		fieldDataInicio.setText(formatoData.format(rec.getDataInicio()));
		fieldDataFim.setText(formatoData.format(rec.getDataFim()));
		fieldDataMontagem.setText(formatoData.format(rec.getDataMontagem()));
		fieldObservacoes.setText(rec.getObservacoesFinanceiras());
		fieldStatusOS.setText(rec.getStatus().toString());
		fieldObservacoesFaturamento.setText(rec.getObservacoesFaturamento());
		setTitle("Liberação de Ordem de Serviço - NÚMERO DA OS: " + rec.getId() + " (" + rec.getNomeEvento() + ")");
		ordemServ = rec;
	}

	protected void clear() {
		limparCliente();
		limparLocal();
		modelRecurso.removeAllElements();
		modelParcelas.removeAllElements();
		fieldQuantidadeParcela.setText("");
		fieldDataPrimeiraParcela.setDate(null);
		fieldEmpenhoParcelas.setSelected(false);
		fieldObservacoes.setText("");
		fieldNomeEvento.setText("");
		fieldDataInicio.setText("");
		fieldDataFim.setText("");
		fieldDataMontagem.setText("");
		fieldPrecoTotal.setText( "R$ 0,00");
		fieldObservacoesFaturamento.setText("");
		ordemServ = null;
		setTitle("Liberação de Ordem de Serviço");
		limparParcela();
	}
	
	private void limparParcela() {
		fieldValorParcela.setValor(null);
		fieldDataParcela.setDate(null);
		fieldDescricaoParcela.setText("");
		fieldEmpenho.setSelected(false);
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		
		if(modelParcelas.getListaReceita().size() == 0) {
			test = false;
			error += "\nNenhuma parcela incluída";
		}
		if(!Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.CADASTRAR_RECEITA_DESCONTO)) {
			BigDecimal total = new BigDecimal(0);
			for(Receita r: modelParcelas.getListaReceita()) {
				total = total.add(r.getValor());
			}
			if((ordemServ.getPreco().subtract(ordemServ.getDesconto()).compareTo(total) > 0)){
				test = false;
				error += "\nO usuário logado não possui permissão para registrar o pagamento com valor abaixo do valor da OS";
			}
		}
		
	/*	if(modelRecurso.getRecursos().size() <= 0){
			test = false;
			error += "\nAdicione pelo menos um recurso";
		}*/
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<OrdemServico> listAll() {
		//listaOrdemServico = Facade.getInstance().listarOrdemServicosPagamento();
		if(listaCache == null)
			listaCache = new ArrayList<OrdemServico>();
		return listaCache;
	}


	public boolean print(OrdemServico current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(ordemServ != null) {
			hm.put("id", ordemServ.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordem_servico_financeiro.jasper");  
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

}
