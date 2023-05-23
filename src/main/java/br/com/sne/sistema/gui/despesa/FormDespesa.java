package br.com.sne.sistema.gui.despesa;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import java.io.File;
import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.FontePagadora;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.Freelancer;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Recibo;
import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.OpcaoPagamento;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.os.FormOrdemServico;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.BordedPanel;
import br.com.sne.sistema.gui.util.components.DateCellRenderer;
import br.com.sne.sistema.gui.util.components.ImageFilter;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.MoedaCellRenderer;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.components.ToggleStatus;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchOS;
import br.com.sne.sistema.gui.util.form.ZebraDecorator;


public class FormDespesa extends DefaultForm<Despesa, DespesaTableModel> {
	private static final long serialVersionUID = 1L;

	private OrdemServico ordemServico;
	
	private JTextField fieldID;
	private JTextField fieldDescricao;

	private JComboBox fieldCentroCusto;
	private JComboBox fieldFornecedor;
	private JComboBox fieldEmpresa;
	private JComboBox fieldFontePagadora;
	private JTextField fieldOpcaoPag;
	private JMoedaRealTextField fieldValor;
	private JDateChooser fieldDataVencimento;
	private JTextField fieldDataPagamento;
	
	private JTextArea fieldObservacoes;
	private Despesa despesa;
	private JButton botaoPagarDespesa;
	private JButton botaoCancelarPagamento;
	private JButton botaoExibirRecibo;
	private JIntField fieldRepetirDespesa;
	
	private JPanel jPanelStatus; // Aberta, Paga
	private JToggleButton botaoStatusAberta;
	private JToggleButton botaoStatusPaga;
	
	private JTextField fieldOrdemServicoID;
	private JTextField fieldOrdemServicoOrdemServico;
	private JTextField fieldOrdemServicoDataInicio;
	private JTextField fieldOSVendedor;
	private JTextField fieldOrdemServicoContato;
	private JTextField fieldOrdemServicoCelular;
	private JTextField fieldOrdemServicoEvento;
	
	private JTable tabelaDespesaLote;
	private DespesaLoteTableModel tableModelDespesaLote;
	private JTextField jTextFielBuscaDespesa;
	private JComboBox jComboBoxFiltroDespesa;
	private TableRowSorter<DespesaLoteTableModel> sorter;
	
	private JDateChooser fieldFiltroDataInicio;
	private JDateChooser fieldFiltroDataFim;

	private JButton botaoImportarDespesa;
	
	private JRadioButton fieldTipoFisica;
	private JRadioButton fieldTipoJuridica;
	
	public void setVisible(boolean aFlag) {
		if(aFlag && fieldFornecedor != null && fieldEmpresa != null && fieldFontePagadora != null) {
			carregarComboCentroCusto();
			carregarComboFornecedor();
			carregarComboEmpresa();
			carregarComboFontePagadora();
		}
		super.setVisible(aFlag);
	}

	public FormDespesa() {
		super(new DespesaTableModel(), "/images/icon_pagar_18.png", "Despesas");
		getJTable().getColumnModel().getColumn(5).setCellRenderer(new MoedaCellRenderer());
		getJTable().getColumnModel().getColumn(2).setCellRenderer(new DateCellRenderer());
		getJTable().getColumnModel().getColumn(3).setCellRenderer(new DateCellRenderer());
		getJTabbedPane().addTab("Pagamento em Lote", new ImageIcon(getClass().getResource("/images/lot_12.png")), getPanelDespesasLote(), null);
	}
	
	
	private JPanel getPanelDespesasLote() {
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

		
		jPanelRelatorio = new JPanel();
		jPanelRelatorio.setLayout(new GridBagLayout());
		jPanelRelatorio.add(getJScrollTabelaDespesas(), gridBagConstraintsTabela);
		jPanelRelatorio.add(getJTabbedPaneStatusLote(), gridBagConstraints1Botoes);
		jPanelRelatorio.add(getBotaoPagarDespesaLote(), new RestricaoLayout(2,0,1,true,false));
		
		carregarComboDespesaLote();

		return jPanelRelatorio;
	}
	
	private JScrollPane getJScrollTabelaDespesas() {
		JScrollPane jScrollPane;
		jScrollPane = new JScrollPane();
		jScrollPane.setName("jScrollPane");
		jScrollPane.setViewportView(getJTableDespesaLote());
		return jScrollPane;
	}
	
	private JTabbedPane getJTabbedPaneStatusLote() {
		JTabbedPane jTabbedPaneStatus = null;
		if (jTabbedPaneStatus == null) {
			jTabbedPaneStatus = new JTabbedPane();
			jTabbedPaneStatus.setName("jTabbedPaneStatus");
			jTabbedPaneStatus.addTab("Filtro", null, getJPanelContemPaneisFiltroDespesaLote(), null);
		}
		return jTabbedPaneStatus;
	}
	
	private void carregarComboDespesaLote(){
		jComboBoxFiltroDespesa.removeAllItems();
		String [] col = tableModelDespesaLote.getFilterColumnName();
		jComboBoxFiltroDespesa.addItem(" ");
		for(String c: col) {
			if(!c.equals("X"))
				jComboBoxFiltroDespesa.addItem(c);
		}
	}
	
	protected JTable getJTableDespesaLote() {
		tableModelDespesaLote = new DespesaLoteTableModel();;
		if (tabelaDespesaLote == null) {
			sorter = new TableRowSorter<DespesaLoteTableModel>(tableModelDespesaLote);
	        tabelaDespesaLote = new JTable(tableModelDespesaLote);
	        tabelaDespesaLote.setRowSorter(sorter);
			tabelaDespesaLote.setFillsViewportHeight(true);
			tabelaDespesaLote.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			ZebraDecorator zebra = new ZebraDecorator();
			for(int i=0; i < tabelaDespesaLote.getColumnCount(); i++) {
				tabelaDespesaLote.getColumnModel().getColumn(i).setCellRenderer(zebra);
			}
			tabelaDespesaLote.getColumnModel().getColumn(2).setCellRenderer(new DateCellRenderer());
			
			TableColumn column = null;
			int columnWidth[] = tableModelDespesaLote.getColumnWidth();
			for (int i = 0; i < columnWidth.length; i++) {
			    column = tabelaDespesaLote.getColumnModel().getColumn(i);
			    try {
			    	column.setPreferredWidth(columnWidth[i]);
			    } catch(RuntimeException errr){
			    	errr.printStackTrace();
			    }
			}
			
		}
		return tabelaDespesaLote;
	}
	
	private JPanel getJPanelContemPaneisFiltroDespesaLote() {
		JPanel jPanelContemPaneisFiltro = null;
		if (jPanelContemPaneisFiltro == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 1;
			gridBagConstraints17.weightx = 1;
			gridBagConstraints17.insets = new Insets(0, 3, 6, 3);
			
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.gridy = 1;
			gridBagConstraints16.weightx = 1;
			gridBagConstraints16.insets = new Insets(0, 3, 6, 3);

			
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.NONE;
			gridBagConstraints18.gridx = 2;
			gridBagConstraints18.gridy = 1;
			gridBagConstraints18.insets = new Insets(0, 3, 6, 3);
			
			
			jPanelContemPaneisFiltro = new JPanel();
			jPanelContemPaneisFiltro.setLayout(new GridBagLayout());
			
			jPanelContemPaneisFiltro.add(getPanelListar(), new RestricaoLayout(0,0, 3, true, false));
			jPanelContemPaneisFiltro.add(getJComboBoxFiltroDespesaLote(), gridBagConstraints17);
			jPanelContemPaneisFiltro.add(getJTextFieldBuscaDespesaLote(), gridBagConstraints16);
			jPanelContemPaneisFiltro.add(getJButtonLimpaFiltroDespesaLote(), gridBagConstraints18);
			
		}
		return jPanelContemPaneisFiltro;
	}
	
	private JPanel getPanelListar() {
		fieldFiltroDataInicio = new JDateChooser();
		fieldFiltroDataFim = new JDateChooser();
		JButton botaoFiltrar = new JButton("Listar");
		
		
		botaoFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				carregarTabelaLote2();
			}
		});
		
		JPanel painelFiltro = new JPanel();
		painelFiltro.setLayout(new GridBagLayout());
		
		painelFiltro.add(new TitledPanel("Data inicial", fieldFiltroDataInicio), new RestricaoLayout(0,0, 1, true, false));
		painelFiltro.add(new TitledPanel("Data Final", fieldFiltroDataFim), new RestricaoLayout(0,1,  1, true, false));
		painelFiltro.add(new TitledPanel(" ", botaoFiltrar), new RestricaoLayout(0,2, false, false));
		
		return painelFiltro;
		
	}
	
	private void carregarTabelaLote2() {
		List<Despesa> lista = new ArrayList<Despesa>();
		Facade.getInstance().beginTransaction();
		lista = Facade.getInstance().listarDespesasAbertas(fieldFiltroDataInicio.getDate(), fieldFiltroDataFim.getDate()); 
		carregarTabelaLote(lista);
		Facade.getInstance().commit();
	}
	
	protected void carregarTabelaLote(List<Despesa> listElements) {
		DespesaLoteTableModel modelo = (DespesaLoteTableModel) tabelaDespesaLote.getModel();
		modelo.removeAllElements();
		for(Despesa t : listElements) {
			modelo.addElement(t);
		}
		tabelaDespesaLote.updateUI();
	}
	
	
	
	private JTextField getJTextFieldBuscaDespesaLote() {
		jTextFielBuscaDespesa = null;
		if (jTextFielBuscaDespesa == null) {
			jTextFielBuscaDespesa = new JTextField();
			jTextFielBuscaDespesa.setName("jTextFielBusca");

		     jTextFielBuscaDespesa.getDocument().addDocumentListener(new DocumentListener(){
		    	 
		    	 public void changedUpdate(DocumentEvent e) {
		    		 newFilterDespesaLote();
				}
		    	 
		    	 public void insertUpdate(DocumentEvent e){
		    		 newFilterDespesaLote();
		    	 }
		    	 
		    	 public void removeUpdate(DocumentEvent e){
		    		 newFilterDespesaLote();
		    	 }
		     
		     });
		    	 
		}
		return jTextFielBuscaDespesa;
	}
	
	 private void newFilterDespesaLote() {
	        RowFilter<DespesaLoteTableModel, Object> rf2 = null;
	        try {
	        	
	        	String campoFiltro = (String) jComboBoxFiltroDespesa.getSelectedItem();
	        	
	        	String col[] = tableModelDespesaLote.getFilterColumnName();
	        	for(int i=0; i < col.length; i++) {

	        		if(campoFiltro.equalsIgnoreCase(col[i])){
	            		if(campoFiltro.toLowerCase().trim().startsWith("valor")) {
	            			try{
	            				NumberFormat formato = NumberFormat.getNumberInstance(); 
	            				Number numero = formato.parse(jTextFielBuscaDespesa.getText());
	            				rf2 = RowFilter.numberFilter(ComparisonType.EQUAL, numero , i);
	            			} catch (Exception excp) {}
	            			break;
	            		}
	        			
	        			rf2 = RowFilter.regexFilter("(?i)" + jTextFielBuscaDespesa.getText(), i);
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
	
	private JButton getJButtonLimpaFiltroDespesaLote() {
		JButton jButtonLimpaFiltro = null;
		if (jButtonLimpaFiltro == null) {
			jButtonLimpaFiltro = new JButton();
			jButtonLimpaFiltro.setName("jButtonLimpaFiltro");
		//	jButtonLimpaFiltro.setIcon(new ImageIcon("images/Kde_crystalsvg_eraser32.png"));
			jButtonLimpaFiltro.setText("Limpar Filtro");
			jButtonLimpaFiltro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					limparFiltroDespesaLote();
				}
			});
		}
		return jButtonLimpaFiltro;
	}
	
	private void limparFiltroDespesaLote() {
		jTextFielBuscaDespesa.setText("");
		jComboBoxFiltroDespesa.setSelectedIndex(0);		
	}
	
	private JComboBox getJComboBoxFiltroDespesaLote() {
		jComboBoxFiltroDespesa = null;
		if (jComboBoxFiltroDespesa == null) {
			jComboBoxFiltroDespesa = new JComboBox();
			jComboBoxFiltroDespesa.setName("jComboBox");
		}
		return jComboBoxFiltroDespesa;
	}
	
	public boolean save(Despesa current) {
		boolean s = false;
		try {
			int repetir = 1;
			try {
				repetir = Integer.parseInt(fieldRepetirDespesa.getText());
			} catch(NumberFormatException err) {
				err.printStackTrace();
			}
			
			
			GregorianCalendar dataParcela = new GregorianCalendar();
			dataParcela.setTime(current.getDataVencimento());
			
			for(int i=1; i <= repetir; i++) {
				Despesa desp = new Despesa();
				desp.setEmpresa(current.getEmpresa());
				desp.setOrdemServico(current.getOrdemServico());
				desp.setFontePagadora(current.getFontePagadora());
				desp.setTipo(current.getTipo());
				desp.setFreelancer(current.getFreelancer());
				desp.setDataCadastro(new Date());
				desp.setDataVencimento(new Date(dataParcela.getTimeInMillis()));
				desp.setCentroCusto(current.getCentroCusto());
				desp.setDescricao(current.getDescricao());
				desp.setFornecedor(current.getFornecedor());
				desp.setFuncionarioCadastro(current.getFuncionarioCadastro());
				desp.setObservacoes(current.getObservacoes());
				desp.setOpcaoPag(current.getOpcaoPag());
				desp.setValor(current.getValor());
				desp.setSituacao(false);
				Facade.getInstance().salvarDespesa(desp);
				dataParcela.add(GregorianCalendar.MONTH, 1);
			}
			clear();
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "A Despesa já se encontra cadastrada", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Despesa current) {
		boolean s = true;
		if(current.isSituacao()) {
			JOptionPane.showMessageDialog(this, "Não é permitido alterar uma despesa paga.", "ERRO", JOptionPane.ERROR_MESSAGE);
			s = false;
		} else {
			Facade.getInstance().atualizarDespesa(current);
		}
		return s;
	}
	
	public boolean remove(Despesa current) {
		boolean test = false;
		try {
			if(current.isSituacao()) {
				JOptionPane.showMessageDialog(this, "Não é permitido excluir uma despesa paga.", "ERRO", JOptionPane.ERROR_MESSAGE);
				test = false;
			} else {
				Facade.getInstance().removerDespesa(current);
				test = true;
			}
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Despesa. ", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldDescricao = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldCentroCusto = new JComboBox();
		fieldFornecedor = new JComboBox();
		fieldEmpresa = new JComboBox();
		fieldFontePagadora = new JComboBox();
		fieldValor = new JMoedaRealTextField();
		fieldDataVencimento = new JDateChooser();
		fieldDataPagamento = new JTextField();
		fieldRepetirDespesa = new JIntField();
		fieldRepetirDespesa.setText("1");
		fieldOpcaoPag = new JTextField();
		fieldOpcaoPag.setEditable(false);
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		fieldDataPagamento.setEditable(false);
		
		ButtonGroup bgroup = new ButtonGroup();
		fieldTipoFisica = new JRadioButton("Física");
		fieldTipoJuridica = new JRadioButton("Jurídica");
		bgroup.add(fieldTipoFisica);
		bgroup.add(fieldTipoJuridica);
		fieldTipoJuridica.setSelected(true);
		
		JPanel painelTipo = new JPanel();
		painelTipo.setLayout(new GridLayout(1,2));
		painelTipo.add(fieldTipoJuridica);
		painelTipo.add(fieldTipoFisica);
		
		fieldTipoFisica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ajustarFormulario();
			}
		});
		fieldTipoJuridica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ajustarFormulario();
			}
		});
		
		
		
		carregarComboCentroCusto();
		carregarComboFornecedor();
		carregarComboEmpresa();
		carregarComboFontePagadora();
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Unidade Empresarial", fieldEmpresa), new RestricaoLayout(0, 1, 1, true, false));
		this.addInputField(new TitledPanel("Tipo de Despesa(Pessoa)", painelTipo), new RestricaoLayout(0, 2, 1, true, false));
		this.addInputField(new TitledPanel("Fornecedor/Freelancer", fieldFornecedor), new RestricaoLayout(0, 3, 1, true, false));
		this.addInputField(new TitledPanel("Centro de Custo", fieldCentroCusto), new RestricaoLayout(0, 4, 1, true, false));
		this.addInputField(new TitledPanel("Fonte Pagadora", fieldFontePagadora), new RestricaoLayout(0, 5, 2, true, false));
		
		this.addInputField(new TitledPanel("Descrição", fieldDescricao), new RestricaoLayout(1, 0, 2, true, false));
		this.addInputField(new TitledPanel("Valor", fieldValor), new RestricaoLayout(1, 2, 1, true, false));
		this.addInputField(new TitledPanel("Repetir Vezes", fieldRepetirDespesa), new RestricaoLayout(1, 3, 1, true, false));
		this.addInputField(new TitledPanel("Vencimento", fieldDataVencimento), new RestricaoLayout(1, 5, 1, true, false));
		this.addInputField(new TitledPanel("Data de Pagamento", fieldDataPagamento), new RestricaoLayout(1, 6, 1, true, false));
		this.addInputField(new TitledPanel("Opção de Pagamento", fieldOpcaoPag), new RestricaoLayout(1, 4, 1, true, false));

		this.addInputField(getPanelOrdemServico(), new RestricaoLayout(2, 0, 7	, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(3, 0, 7, 2, true, true));

		
		this.addInputField(new TitledPanel(" ", getBotaoPagarDespesa()), new RestricaoLayout(5, 0, 2, true, false));
		this.addInputField(new TitledPanel(" ", getBotaoExibirRecibo()), new RestricaoLayout(5, 2, 2, true, false));	
		this.addInputField(new TitledPanel(" ", getBotaoCancelarPagamento()), new RestricaoLayout(5, 4, 1, true, false));
		this.addInputField(new TitledPanel(" ", getBotaoImportarDespesa()), new RestricaoLayout(5, 5, 2, true, false));
		
		this.addStatusTab("Status", getJPanelStatus());
	}
	
	private void ajustarFormulario() {
		if(fieldTipoJuridica.isSelected()) {
			carregarComboFornecedor();
		} else {
			carregarComboFreelancer();
		}
	}
	
	private JButton getBotaoImportarDespesa() {
		if(botaoImportarDespesa == null){
			botaoImportarDespesa = new JButton("Importar Despesas");
			botaoImportarDespesa.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							ImportarDespesas.main(null);
						}
					}
					);
		}
		return botaoImportarDespesa;
	}
	
	public JPanel getPanelOrdemServico() {
		JPanel panelOrdemServico = new JFormGroup("Dados da OS");
		panelOrdemServico.setLayout(new GridBagLayout());
		fieldOrdemServicoID = new JIntField();
		fieldOrdemServicoEvento = new JTextField();
		fieldOrdemServicoOrdemServico = new JTextField();
		fieldOrdemServicoDataInicio = new JTextField();
		fieldOSVendedor = new JTextField();
		fieldOrdemServicoContato = new JTextField();
		fieldOrdemServicoCelular = new JFoneField();

		fieldOrdemServicoOrdemServico.setEditable(false);
		fieldOrdemServicoDataInicio.setEditable(false);
		fieldOSVendedor.setEditable(false);
		fieldOrdemServicoContato.setEditable(false);
		fieldOrdemServicoCelular.setEditable(false);

		panelOrdemServico.add(new TitledPanel("Código", getPanelCodigoOrdemServico()), new RestricaoLayout(0, 0, false, false));
		panelOrdemServico.add(new TitledPanel("Nome do Evento", getPanelNomeOrdemServico()), new RestricaoLayout(0, 1, 4, true, false));
		panelOrdemServico.add(new TitledPanel("Data de Início", fieldOrdemServicoDataInicio), new RestricaoLayout(0, 5, 1, true, false));
		panelOrdemServico.add(new TitledPanel("Valor do Evento", fieldOrdemServicoOrdemServico), new RestricaoLayout(0, 6, 1, true, false));
		
		panelOrdemServico.add(new TitledPanel("Cliente", fieldOrdemServicoContato), new RestricaoLayout(1, 0, 2, true, false));
		panelOrdemServico.add(new TitledPanel("Celular", fieldOrdemServicoCelular), new RestricaoLayout(1, 2, 3, true, false));
		
		panelOrdemServico.add(new TitledPanel("Funcionário", fieldOSVendedor), new RestricaoLayout(1, 5, 2, true, false));
		
		

		return panelOrdemServico;
	}
	
	public JPanel getPanelNomeOrdemServico() {
		JPanel panelNomeOrdemServico = new JPanel();
		panelNomeOrdemServico.setLayout(new BoxLayout(panelNomeOrdemServico, BoxLayout.LINE_AXIS));
		JButton botaoOrdemServico = getBotaoAbrirOrdemServico();
		botaoOrdemServico.setPreferredSize(new Dimension(30,18));
		fieldOrdemServicoEvento = new JTextField();
		fieldOrdemServicoEvento.setEditable(false);
		panelNomeOrdemServico.add(fieldOrdemServicoEvento);
		panelNomeOrdemServico.add(botaoOrdemServico);
		return panelNomeOrdemServico;
	}
	
	public JButton getBotaoAbrirOrdemServico() {
		JButton botaoAbrirOrdemServico = new JButton("", new ImageIcon(getClass().getResource("/images/lente_24.png")));
		botaoAbrirOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAbrirOrdemServico.setVerticalAlignment(SwingConstants.CENTER);
		botaoAbrirOrdemServico.setPreferredSize(new Dimension(20,20));

		botaoAbrirOrdemServico.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(ordemServico != null) {
							FormOrdemServico telaOrdemServico = (FormOrdemServico) WindowFactory.createTelaOS(Facade.getInstance().getTelaPrincipal().getDesktop());
							telaOrdemServico.loadForm(ordemServico);
							telaOrdemServico.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(FormDespesa.this, "Nenhum OrdemServico selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
							
					}
				}	
		);
		return botaoAbrirOrdemServico;
	}
	
	public JPanel getPanelCodigoOrdemServico() {
		JPanel panelOrdemServico = new JPanel();
		panelOrdemServico.setLayout(new BoxLayout(panelOrdemServico, BoxLayout.LINE_AXIS));
		JButton botaoOrdemServico = getBotaoProcurarOrdemServico();

		fieldOrdemServicoID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idOrdemServico = Integer.parseInt(fieldOrdemServicoID.getText());
						OrdemServico c = Facade.getInstance().carregarOrdemServico(idOrdemServico);
						if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_ORCAMENTOS) ||
								Facade.getInstance().getUsuarioLogado().getFuncionario().getId() == c.getFuncionario().getId())
						carregarOrdemServico(c);
					} catch (Exception e){
						limparOrdemServico();
					}
				}
			}
		});

		botaoOrdemServico.setPreferredSize(new Dimension(30,18));

		panelOrdemServico.add(fieldOrdemServicoID);
		panelOrdemServico.add(botaoOrdemServico);
		return panelOrdemServico;
	}
	
	public void carregarOrdemServico(OrdemServico c) {
		ordemServico = c;
		if(c!=null){
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat formatador = new DecimalFormat("#,###,###,##0.00");
			fieldOrdemServicoID.setText("" + c.getId());
			fieldOrdemServicoEvento.setText(c.getNomeEvento());
			fieldOrdemServicoOrdemServico.setText("R$ " + formatador.format(c.getPreco()));
			fieldOrdemServicoDataInicio.setText(formato.format(c.getDataInicio()));
			fieldOSVendedor.setText(c.getFuncionario().getNome());
			fieldOrdemServicoContato.setText(c.getCliente().getNome());
			fieldOrdemServicoCelular.setText(c.getCliente().getCelular());
		} else {
			limparOrdemServico();
		}
	}
	
	public void limparOrdemServico() {
		fieldOrdemServicoID.setText("");
		fieldOrdemServicoEvento.setText("");
		fieldOrdemServicoOrdemServico.setText("");
		fieldOrdemServicoDataInicio.setText("");
		fieldOSVendedor.setText("");
		fieldOrdemServicoContato.setText("");
		fieldOrdemServicoCelular.setText("");
	}
	
	public JButton getBotaoProcurarOrdemServico() {
		JButton botaoProcurarOrdemServico = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarOrdemServico.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarOrdemServico.setPreferredSize(new Dimension(20,20));

		botaoProcurarOrdemServico.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchOS teste = new DialogSearchOS( FormDespesa.this);
						OrdemServico c = teste.showDialog(FormDespesa.this);
						if(c != null)
							carregarOrdemServico(c);
					}
				}	
		);
		return botaoProcurarOrdemServico;
	}

	public JButton getBotaoPagarDespesa() {
		if(botaoPagarDespesa == null){
			botaoPagarDespesa = new JButton("Registrar Pagamento", new ImageIcon(getClass().getResource("/images/check_24.png")));
			botaoPagarDespesa.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(despesa == null) {
								JOptionPane.showMessageDialog(FormDespesa.this, "Selecione uma despesa para registrar o pagamento", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							JFrame telaParametro = new TelaParamPagarDespesa(despesa,fieldObservacoes.getText());
							JDialog dialog = new JDialog(telaParametro, "Pagamento de despesa", true);
							dialog.setContentPane(telaParametro.getContentPane());  
							dialog.setBounds(telaParametro.getBounds());  
							dialog.setVisible(true); 
							clear(); 
						}
					}
			);
			botaoPagarDespesa.setEnabled(false);
			
		}
		return botaoPagarDespesa;
	}
	
	public JPanel getBotaoPagarDespesaLote() {
		JPanel painelPagar = new JPanel();
		painelPagar.setLayout(new GridBagLayout());
		
		JButton botaoPagarDespesaLote;
		botaoPagarDespesaLote = new JButton("Pagar Despesas Selecionadas", new ImageIcon(getClass().getResource("/images/check_24.png")));
		botaoPagarDespesaLote.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						List<Despesa> despesasSelecionadas = tableModelDespesaLote.getDespesasSelecionadas();
						if(despesasSelecionadas.size() <=0) {
							JOptionPane.showMessageDialog(FormDespesa.this, "Nenhuma despesa selecionada!", "ERRO", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						BigDecimal valorTotal = new BigDecimal(0);
						for(Despesa desp : despesasSelecionadas) {
							valorTotal = valorTotal.add(desp.getValor());
							
						}
						
						NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
						formato.setMinimumFractionDigits(2);
						formato.setMaximumFractionDigits(2);
						formato.setMaximumIntegerDigits(12);
						
						if(JOptionPane.showConfirmDialog(FormDespesa.this, "Confirma o pagamento de " + despesasSelecionadas.size() + " despesas \n" +
								"no valor total de R$ " + formato.format(valorTotal), "Confirmação", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
							
							JFileChooser fc = new JFileChooser();
							fc.setFileFilter(new ImageFilter());
							fc.setAcceptAllFileFilterUsed(false);
					        int returnVal = fc.showOpenDialog(FormDespesa.this);
					        
					        if(returnVal != JFileChooser.APPROVE_OPTION) {
					        	JOptionPane.showMessageDialog(FormDespesa.this, "Você não selecionou uma imagem.", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
					        }
							
					        
							for(Despesa dp: despesasSelecionadas) {
								dp.setDataPagamento(new Date());
								dp.setValorPago(dp.getValor());
								dp.setSituacao(true);
								Recibo recibo = new Recibo();
						        File file = fc.getSelectedFile();
						        recibo.alterarImagemRecibo(file.getAbsolutePath());
						        recibo.setDespesa(dp);
						        recibo.setCancelado(false);
						        Facade.getInstance().salvarRecibo(recibo);
								Facade.getInstance().atualizarDespesa(dp);
							}
							
							JOptionPane.showMessageDialog(null,"Pagamento realizado com sucesso!");
							carregarTabelaLote2();
							
						}

					}
				}
		);

		painelPagar.add(new JPanel(), new RestricaoLayout(0,0, 1, true, false));
		painelPagar.add(botaoPagarDespesaLote, new RestricaoLayout(0,1, 1, true, false));
		painelPagar.add(new JPanel(), new RestricaoLayout(0,2, 1, true, false));
		return painelPagar;
	}
	
	public JButton getBotaoCancelarPagamento() {
		if(botaoCancelarPagamento == null){
			botaoCancelarPagamento = new JButton("Cancelar Pagamento", new ImageIcon(getClass().getResource("/images/error_24.png")));
			botaoCancelarPagamento.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(despesa == null) {
								JOptionPane.showMessageDialog(FormDespesa.this, "Selecione uma despesa para cancelar o pagamento", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							if(JOptionPane.showConfirmDialog(FormDespesa.this, "Confirma o cancelamento do pagamento", "Confirmação", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
								despesa.setValorPago(new BigDecimal(0));
								despesa.setDataPagamento(null);
								despesa.setObservacoes(fieldObservacoes.getText());
								despesa.setSituacao(false);
								Recibo recibo = Facade.getInstance().localizarReciboPorDespesa(despesa);
								recibo.setCancelado(true);
								Facade.getInstance().atualizarRecibo(recibo);
								Facade.getInstance().atualizarDespesa(despesa);
								JOptionPane.showMessageDialog(null,"Cancelamento do pagamento realizado com sucesso!");	
								clear();
							}
						}
					}
			);
			botaoCancelarPagamento.setEnabled(false);
			
		}
		return botaoCancelarPagamento;
	}
	
	public JButton getBotaoExibirRecibo() {
		if(botaoExibirRecibo == null){
			botaoExibirRecibo = new JButton("Exibir Recibo", new ImageIcon(getClass().getResource("/images/lente_24.png")));
			botaoExibirRecibo.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(despesa == null) {
								JOptionPane.showMessageDialog(FormDespesa.this, "Selecione uma despesa para exibir o recibo", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							JFrame telaParametro = new TelaParamExibirRecibo(despesa);
							JDialog dialog = new JDialog(telaParametro, "Exibição de Recibo", true);
							dialog.setContentPane(telaParametro.getContentPane());  
							dialog.setBounds(telaParametro.getBounds());  
							dialog.setVisible(true); 
						}
					}
			);botaoExibirRecibo.setEnabled(false);
			
		}
		return botaoExibirRecibo;
	}
	
	
	public void carregarComboCentroCusto() {
		fieldCentroCusto.removeAllItems();
		fieldCentroCusto.addItem("Selecione um Centro de Custo");
		for(CentroCusto cc: Facade.getInstance().listarCentroCustos()) {
			fieldCentroCusto.addItem(cc);
		}
	}
	
	public void carregarComboEmpresa() {
		fieldEmpresa.removeAllItems();
		fieldEmpresa.addItem("Selecione uma Empresa");
		for(Unidade cc: Facade.getInstance().listarUnidade()) {
			fieldEmpresa.addItem(cc);
		}
	}
	
	public void carregarComboFornecedor() {
		fieldFornecedor.removeAllItems();
		fieldFornecedor.addItem("Selecione um Fornecedor");
		for(Fornecedor f: Facade.getInstance().listarFornecedores()) {
			fieldFornecedor.addItem(f);
		}
	}
	
	public void carregarComboFreelancer() {
		fieldFornecedor.removeAllItems();
		fieldFornecedor.addItem("Selecione um Freelancer");
		for(Freelancer f: Facade.getInstance().listarFreelancers()) {
			fieldFornecedor.addItem(f);
		}
	}
	
	
	public void carregarComboFontePagadora() {
		fieldFontePagadora.removeAllItems();
		fieldFontePagadora.addItem("Selecione uma Fonte Pagadora");
		for(FontePagadora f: Facade.getInstance().listarFontePagadoras()) {
			fieldFontePagadora.addItem(f);
		}
	}
	
	public Despesa newElement() {
		return new Despesa();
	}

	public void loadInputFields(Despesa despesa) {
		
		if(fieldTipoFisica.isSelected()) {
			despesa.setTipo("F");
			despesa.setFornecedor(null);
			despesa.setFreelancer((Freelancer)fieldFornecedor.getSelectedItem());
		} else {
			despesa.setTipo("J");
			despesa.setFornecedor((Fornecedor)fieldFornecedor.getSelectedItem());
			despesa.setFreelancer(null);
		}
		
		despesa.setOrdemServico(ordemServico);
		despesa.setObservacoes(fieldObservacoes.getText());
		despesa.setDescricao(fieldDescricao.getText());
		despesa.setCentroCusto((CentroCusto) fieldCentroCusto.getSelectedItem());
		
		despesa.setEmpresa((Unidade) fieldEmpresa.getSelectedItem());
		
		Object fonte = fieldFontePagadora.getSelectedItem();
		if(fonte instanceof FontePagadora )
			despesa.setFontePagadora((FontePagadora) fonte);
		else
			despesa.setFontePagadora(null);
		
		
		despesa.setDataVencimento(fieldDataVencimento.getDate());
		try {
			despesa.setValor(fieldValor.getValor());
		} catch(Exception e) {}
		
	}
	
	protected void clear() {
		limparOrdemServico();
		fieldID.setText("");
		fieldDescricao.setText("");
		fieldObservacoes.setText("");
		fieldRepetirDespesa.setText("1");
		fieldCentroCusto.setSelectedIndex(0);
		fieldFornecedor.setSelectedIndex(0);
		fieldEmpresa.setSelectedIndex(0);
		fieldOpcaoPag.setText("");
		fieldFontePagadora.setSelectedIndex(0);
		fieldValor.clear();
		fieldDataVencimento.setDate(null);
		despesa = null;
		botaoPagarDespesa.setEnabled(false);
		botaoExibirRecibo.setEnabled(false);
		botaoCancelarPagamento.setEnabled(false);
	}

	protected void loadForm(Despesa desp) {
		clear();
		if(desp.getTipo().equals("F")) {
			fieldTipoFisica.setSelected(true);
		} else {
			fieldTipoJuridica.setSelected(true);
		}
		ajustarFormulario();
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		fieldID.setText("" + desp.getId());
		fieldDescricao.setText(desp.getDescricao());
		fieldObservacoes.setText(desp.getObservacoes());
		fieldCentroCusto.setSelectedItem(desp.getCentroCusto());
		
		if(desp.getTipo().equals("F"))
			fieldFornecedor.setSelectedItem(desp.getFreelancer());
		else
			fieldFornecedor.setSelectedItem(desp.getFornecedor());
		
		fieldValor.setValor(desp.getValor());
		fieldDataVencimento.setDate(desp.getDataVencimento());
		if(desp.getOpcaoPag() != null) {
			fieldOpcaoPag.setText(desp.getOpcaoPag().name());
		}
		if(desp.getDataPagamento() != null) {
			fieldDataPagamento.setText(formato.format(desp.getDataPagamento()));
		}
		if(!desp.isSituacao()) {
			botaoPagarDespesa.setEnabled(true);
			botaoExibirRecibo.setEnabled(false);
			botaoCancelarPagamento.setEnabled(false);
		} else {
			botaoPagarDespesa.setEnabled(false);
			botaoExibirRecibo.setEnabled(true);
			if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.CANCELAR_PAGAMENTO_DESPESA)) {
				//botaoCancelarPagamento.setEnabled(true);
			}
		}

		System.out.println(desp.getEmpresa());
		System.out.println(desp.getFontePagadora());
		System.out.println(desp.getOrdemServico());
		
		
		
		if(desp.getOrdemServico() != null)
			carregarOrdemServico(desp.getOrdemServico());
		
		if(desp.getFontePagadora() != null) {
			//fieldFontePagadora.setSelectedItem(desp.getFontePagadora());
			for(int i = 0;i<fieldFontePagadora.getItemCount();i++) {
				if(fieldFontePagadora.getItemAt(i).toString().equals(desp.getFontePagadora().toString())) {
					fieldFontePagadora.setSelectedIndex(i);
					break;
				}
			}
		}
		
		if(desp.getEmpresa() != null) {
			for(int i = 0;i<fieldEmpresa.getItemCount();i++) {
				if(fieldEmpresa.getItemAt(i).toString().equals(desp.getEmpresa().toString())) {
					fieldEmpresa.setSelectedIndex(i);
					break;
				}
			}
			
		}
		
		despesa = desp;

	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		
		if(!(fieldEmpresa.getSelectedItem() instanceof Unidade)) {
			test = false;
			error += "\nSelecione uma Empresa";
		}

		if(!(fieldCentroCusto.getSelectedItem() instanceof CentroCusto)) {
			test = false;
			error += "\nSelecione o Centro de Custo";
		}
		if(fieldTipoJuridica.isSelected() &&
				!(fieldFornecedor.getSelectedItem() instanceof Fornecedor)) {
			test = false;
			error += "\nSelecione o Fornecedor";
		}
		
		if(fieldTipoFisica.isSelected() &&
				!(fieldFornecedor.getSelectedItem() instanceof Freelancer)) {
			test = false;
			error += "\nSelecione o Fornecedor";
		}
		
		
		
/*		if(!(fieldEmpresa.getSelectedItem() instanceof Unidade)) {
			test = false;
			error += "\nSelecione a Empresa";
		}
		if(!(fieldFontePagadora.getSelectedItem() instanceof FontePagadora)) {
			test = false;
			error += "\nSelecione a Fonte Pagadora";
		} */
		if(fieldValor.getValor() == null) {
			test = false;
			error += "\nInforme o valor";
		}

		if(fieldDataVencimento.getDate() == null) {
			test = false;
			error += "\nInforme a data do vencimento";
		}

		if(fieldDescricao.getText().length() <= 0) {
			test = false;
			error += "\nPreencha a descrição da Despesa";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Despesa> listAll() {
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Despesa>();
		}
		
		List<Despesa> lista = Facade.getInstance().listarDespesas(campo,texto);
		
		boolean aberta = botaoStatusAberta.isSelected();
		boolean paga = botaoStatusPaga.isSelected();

		List<Despesa> resp = new ArrayList<Despesa>();

		for(Despesa c : lista) {
			if((c.isSituacao() && paga) ||
					(!c.isSituacao() && aberta)	) {
				c.getCentroCusto().getNome();
				c.getFornecedor();
				c.getFreelancer();
				resp.add(c);
			}
		}
		return resp;
	}

	@Override
	public boolean print(Despesa current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(despesa != null) {
			hm.put("id", despesa.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/despesa.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um Despesa para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	
	private JPanel getJPanelStatus() {
		if (jPanelStatus == null) {
			jPanelStatus = new JPanel();
			jPanelStatus.setLayout(new BoxLayout(jPanelStatus, BoxLayout.X_AXIS));
			jPanelStatus.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			jPanelStatus.setName("jPanelStatus");

			botaoStatusAberta = new ToggleStatus("Aberta", "images/despesa.png" );
			botaoStatusAberta.setIcon(new ImageIcon(getClass().getResource("/images/despesa.png")));
			
			botaoStatusAberta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			botaoStatusPaga = new ToggleStatus("Paga", "" ); 
			botaoStatusPaga.setIcon(new ImageIcon(getClass().getResource("/images/despesa_paga.png")));
			botaoStatusPaga.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			jPanelStatus.add(new BordedPanel(botaoStatusAberta), null);
			jPanelStatus.add(new BordedPanel(botaoStatusPaga), null);
		}
		return jPanelStatus;
	}
	
	
}
