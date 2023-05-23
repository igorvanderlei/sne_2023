package br.com.sne.sistema.gui.equipamentoenviado;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Freelancer;
import br.com.sne.sistema.bean.Funcao;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.PessoalAlocado;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RegistroFreelancer;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.osdepassagem.FormOsPassagem;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.Beep;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DialogSearchEquipamento;
import br.com.sne.sistema.gui.util.form.DialogSearchOS;
import br.com.sne.sistema.gui.util.form.DialogSearchOS2;

import com.toedter.calendar.JDateChooser;

public class FormEnviarEquipamento extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JDateChooser fieldData;
	private JTable tabelaOS;
	private OrdemServicoTableModel modelOS;
	private JComboBox comboFiltroTipo;
	private JTextField fieldFiltroValor;
	private JTabbedPane tabRecursos;
	private JBarCodeInputField fieldIdEquipamento;

	private TableRowSorter<OrdemServicoTableModel> sorter;

	private JTable tabelaRecursosSolicitados;
	private RecursoSolicitadoTableModel modelRecursos;

	private JTable tabelaEquipamentosRegistrados;
	private EquipamentosRegistradosTableModel modelEquipamentoResgistrado;

	private JTable tabelaPessoalJaAlocado;
	private PessoalAlocadoTableModel modelPessoalJaAlocado;

	private JTable tabelaFreelancerJaAlocado;
	private RegistroFreelancerTableModel modelFreelancerJaAlocado;

	private JTable tabelaRecursoHumanoSolicitado;
	private JTable tabelaRecursoHumanoSolicitadoFreelancer;
	private RecursoHumanoSolicitadoTableModel modelRecursoHumano;


	private JTable tabelaEquipamentoEnviado;
	private OSEquipamentoEnviadoTableModel modelEquipamento;
	private JComboBox fieldFuncionarioResponsavel;
	private OrdemServico os;

	private JComboBox fieldPessoalAlocar;
	private JDateChooser fieldPessoalAlocarDataInicio;
	private JDateChooser fieldPessoalAlocarDataFim;

	private JComboBox fieldFreelancerAlocar;
	private JDateChooser fieldFreelancerAlocarDataInicio;
	private JDateChooser fieldFreelancerAlocarDataFim;


	private JPopupMenu menuTabelaEquipamentos;
	private JPopupMenu menuTabelaPessoal;
	private JPopupMenu menuTabelaFreelancer;

	private JButton botaoAddPessoal;
	private JButton botaoSalvar;
	private JButton botaoImprimir;
	private JButton botaoAddFreelancer;
	private JButton botaoImprimirSubgrupos;
	
	private JButton botalLocalizarOS;

	public FormEnviarEquipamento() {
		super("Enviar Equipamento");
		setFrameIcon(new ImageIcon(getClass().getResource("/images/icon_enviar_18.png")));
		initialize();
		desabilitarRecursos();
	}

	private void initialize() {
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new GridBagLayout());
		conteudo.add(new TitledPanel("Ordem de Serviço", getPanelOs()), new RestricaoLayout(0,0,1,1,true,true));
		conteudo.add(getTabRecursos(), new RestricaoLayout(1,0,1,1,true,true));
		conteudo.add(new TitledPanel("Responsável", fieldFuncionarioResponsavel), new RestricaoLayout(2,0,1,true,false));
		conteudo.add(getPanelConfirmar(), new RestricaoLayout(3,0,1,true, false));
		this.setContentPane(conteudo);
	}

	private JTabbedPane getTabRecursos() {
		tabRecursos = new JTabbedPane();
		tabRecursos.addTab("Equipamentos", getPanelRecursoEquipamento());
		tabRecursos.addTab("Funcionários", getPanelPessoal());
		tabRecursos.addTab("Freelancers", getPanelTabelaFreelancer());
		return tabRecursos;
	}

	private JPanel getPanelOs() {
		JPanel panelOs = new JPanel();
		panelOs.setLayout(new GridBagLayout());
		fieldData = new JDateChooser();
		comboFiltroTipo = new JComboBox();
		fieldFiltroValor = new JTextField();
		fieldData.setDate(new Date());
		fieldData.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldData.getDate() != null) {
							Facade.getInstance().beginTransaction();
							carregarTabelaOS();
							Facade.getInstance().commit();
						}
					}
				}
		);


		panelOs.add(new TitledPanel("Data de Montagem", fieldData), new RestricaoLayout(0, 0, 1, true, false));
		panelOs.add(new TitledPanel("Selecione a opção de filtro", comboFiltroTipo), new RestricaoLayout(0, 1, 2, true, false));
		panelOs.add(new TitledPanel("Filtro", fieldFiltroValor), new RestricaoLayout(0, 3, 2, true, false));
		panelOs.add(getBotalLocalizarOS(), new RestricaoLayout(0, 5, false, false));
		panelOs.add(getTabelaOS(), new RestricaoLayout(1,0,6,1,true,true));

		fieldFiltroValor.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent e) {
				filtrarTabelaOS();
			}

			public void insertUpdate(DocumentEvent e){
				filtrarTabelaOS();
			}

			public void removeUpdate(DocumentEvent e){
				filtrarTabelaOS();
			}

		});

		return panelOs;
	}


	private JComponent getTabelaOS() {
		JScrollPane panelTabela = new JScrollPane();
		modelOS = new OrdemServicoTableModel();
		sorter = new TableRowSorter<OrdemServicoTableModel>(modelOS);
		tabelaOS = new JTable(modelOS);
		tabelaOS.setRowSorter(sorter);
		tabelaOS.setFillsViewportHeight(true);
		tabelaOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumn column = null;
		int columnWidth[] = modelOS.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaOS.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}			

		tabelaOS.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = tabelaOS.getSelectedRow();

						if (viewRow < 0) {
							os = null;
							desabilitarRecursos();
						} else {
							Facade.getInstance().beginTransaction();
							OrdemServico selected = (OrdemServico) tabelaOS.getValueAt(viewRow,modelOS.getObjectIndex());
							os = selected;
							habilitarRecursos();
							carregarTabelaRecursos(selected);
							carregarTabelaEquipamentosRegistrados(selected);
							carregarTabelaPessoalAlocado(selected);
							carregarFreelancerAlocado(selected);
							fieldIdEquipamento.requestFocus();
							Facade.getInstance().commit();
						}
					}
				}
		);

		panelTabela.setViewportView(tabelaOS);
		return panelTabela;
	}

	private JComponent getTabelaRecursos() {
		JScrollPane panelTabela = new JScrollPane();

		modelRecursos = new RecursoSolicitadoTableModel();
		tabelaRecursosSolicitados = new JTable(modelRecursos);
		tabelaRecursosSolicitados.setFillsViewportHeight(true);
		tabelaRecursosSolicitados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumn column = null;
		int columnWidth[] = modelRecursos.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaRecursosSolicitados.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	

		panelTabela.setViewportView(tabelaRecursosSolicitados);
		return panelTabela;
	}

	private JComponent getTabelaEquipamentosRegistrados() {
		JScrollPane panelTabela = new JScrollPane();
		modelEquipamentoResgistrado = new EquipamentosRegistradosTableModel();
		tabelaEquipamentosRegistrados = new JTable(modelEquipamentoResgistrado);
		tabelaEquipamentosRegistrados.setFillsViewportHeight(true);
		tabelaEquipamentosRegistrados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumn column = null;
		int columnWidth[] = modelEquipamentoResgistrado.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamentosRegistrados.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	

		panelTabela.setViewportView(tabelaEquipamentosRegistrados);
		return panelTabela;
	}

	private JComponent getTabelaPessoalJaAlocado() {
		JScrollPane panelTabela = new JScrollPane();
		modelPessoalJaAlocado = new PessoalAlocadoTableModel();
		tabelaPessoalJaAlocado = new JTable(modelPessoalJaAlocado);
		tabelaPessoalJaAlocado.setFillsViewportHeight(true);
		tabelaPessoalJaAlocado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		menuTabelaPessoal = new JPopupMenu();
		menuTabelaPessoal.add(getMenuRemoverFuncionario());

		tabelaPessoalJaAlocado.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaPessoalJaAlocado.getSelectedRow() >= 0 )
								menuTabelaPessoal.show(tabelaPessoalJaAlocado, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Funcionário para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		);

		TableColumn column = null;
		int columnWidth[] = modelPessoalJaAlocado.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaPessoalJaAlocado.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	

		panelTabela.setViewportView(tabelaPessoalJaAlocado);
		return panelTabela;
	}

	private JComponent getTabelaFreelancerJaAlocado() {
		JScrollPane panelTabela = new JScrollPane();
		modelFreelancerJaAlocado = new RegistroFreelancerTableModel();
		tabelaFreelancerJaAlocado = new JTable(modelFreelancerJaAlocado);
		tabelaFreelancerJaAlocado.setFillsViewportHeight(true);
		tabelaFreelancerJaAlocado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		menuTabelaFreelancer = new JPopupMenu();
		menuTabelaFreelancer.add(getMenuRemoverFreelancer());

		tabelaFreelancerJaAlocado.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaFreelancerJaAlocado.getSelectedRow() >= 0 )
								menuTabelaFreelancer.show(tabelaFreelancerJaAlocado, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Freelancer para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}

		);



		TableColumn column = null;
		int columnWidth[] = modelFreelancerJaAlocado.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaFreelancerJaAlocado.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	

		panelTabela.setViewportView(tabelaFreelancerJaAlocado);
		return panelTabela;
	}

	private JComponent getTabelaEquipamento() {
		JScrollPane panelTabela = new JScrollPane();

		modelEquipamento = new OSEquipamentoEnviadoTableModel();
		tabelaEquipamentoEnviado = new JTable(modelEquipamento);
		tabelaEquipamentoEnviado.setFillsViewportHeight(true);
		tabelaEquipamentoEnviado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuTabelaEquipamentos = new JPopupMenu();
		menuTabelaEquipamentos.add(getMenuRemover());

		tabelaEquipamentoEnviado.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaEquipamentoEnviado.getSelectedRow() >= 0 )
								menuTabelaEquipamentos.show(tabelaEquipamentoEnviado, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Recurso para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}

		);

		TableColumn column = null;
		int columnWidth[] = modelEquipamento.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamentoEnviado.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	
		panelTabela.setViewportView(tabelaEquipamentoEnviado);
		return panelTabela;
	}


	protected void carregarTabelaOS() {

		fieldFiltroValor.setText("");
		OrdemServicoTableModel modelo = (OrdemServicoTableModel) tabelaOS.getModel();
		modelo.removeAllElements();
		modelRecursos.removeAllElements();
		modelEquipamento.removeAllElements();
		modelEquipamentoResgistrado.removeAllElements();
		modelPessoalJaAlocado.removeAllElements();
		modelRecursoHumano.removeAllElements();
		modelFreelancerJaAlocado.removeAllElements();
		fieldFuncionarioResponsavel.setSelectedItem(null);

		os = null;
		desabilitarRecursos();
		if(fieldData.getDate() != null) {
			List<OrdemServico> listElements = Facade.getInstance().listarOrdemServicos(fieldData.getDate());
			for(OrdemServico o : listElements) {
				modelo.addElement(o);
			}
			tabelaOS.updateUI();
		}
		
	}

	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Equipamento");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelEquipamento.removeRow(tabelaEquipamentoEnviado.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}

	private JMenuItem getMenuRemoverFreelancer() {
		JMenuItem menuRemover = new JMenuItem("Remover Freelancer");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelFreelancerJaAlocado.removeRow(tabelaFreelancerJaAlocado.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}
	
	private JMenuItem getMenuRemoverFuncionario() {
		JMenuItem menuRemover = new JMenuItem("Remover Funcionário");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelPessoalJaAlocado.removeRow(tabelaPessoalJaAlocado.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}

	private void carregarTabelaRecursos(OrdemServico selected) {
		modelRecursos.removeAllElements();
		modelRecursoHumano.removeAllElements();
		fieldPessoalAlocarDataInicio.setDate(selected.getDataInicio());
		fieldFreelancerAlocarDataInicio.setDate(selected.getDataInicio());
		fieldPessoalAlocarDataFim.setDate(selected.getDataFim());
		fieldFreelancerAlocarDataFim.setDate(selected.getDataFim());

		fieldPessoalAlocarDataInicio.setMinSelectableDate(selected.getDataMontagem());
		fieldPessoalAlocarDataInicio.setMaxSelectableDate(selected.getDataFim());
		fieldPessoalAlocarDataFim.setMinSelectableDate(selected.getDataMontagem());
		fieldPessoalAlocarDataFim.setMaxSelectableDate(selected.getDataFim());	

		fieldFreelancerAlocarDataInicio.setMinSelectableDate(selected.getDataMontagem());
		fieldFreelancerAlocarDataInicio.setMaxSelectableDate(selected.getDataFim());
		fieldFreelancerAlocarDataFim.setMinSelectableDate(selected.getDataMontagem());
		fieldFreelancerAlocarDataFim.setMaxSelectableDate(selected.getDataFim());

		List<Recurso> lista = Facade.getInstance().listarRecursos();
		List<Recurso> listaFisico = new ArrayList<Recurso>();
		List<Recurso> listaHumano = new ArrayList<Recurso>();
		for(Recurso r : lista){
			if(r instanceof Funcao){
				listaHumano.add(r);
			} else{
				listaFisico.add(r);
			}
		}
		lista = null;

		for(RecursoSolicitado r: selected.getRecursoSolicitado()) {

			if(listaFisico.contains(r.getRecurso())) {
				int cont = 0;
				for(EquipamentoEnviado ev: selected.getEquipamentoEnviado()){
					if(ev.getEquipamento().getDescricaoEquipamento().getNome().equals(r.getRecurso().getNome())){
						cont ++;
					}
				}
				modelRecursos.addElement(r,cont); 				
			} else {
				modelRecursoHumano.addElement(r);
			}
		}
	}

	private void carregarTabelaEquipamentosRegistrados(OrdemServico selected) {
		modelEquipamentoResgistrado.removeAllElements();
		for(EquipamentoEnviado r: selected.getEquipamentoEnviado()) {
			modelEquipamentoResgistrado.addElement(r);
		}
	}	

	private void carregarTabelaPessoalAlocado(OrdemServico selected) {
		modelPessoalJaAlocado.removeAllElements();
		for(PessoalAlocado r: selected.getPessoalAlocado()) {
			modelPessoalJaAlocado.addElement(r);
		}
	}	
	
	private void carregarFreelancerAlocado(OrdemServico selected) {
		modelFreelancerJaAlocado.removeAllElements();
		for(RegistroFreelancer r: selected.getFreelancerAlocado()) {
			modelFreelancerJaAlocado.addElement(r);
		}
	}

	
	
	private void filtrarTabelaOS() {
		RowFilter<OrdemServicoTableModel, Object> rf2 = null;
		try {

			String recText = (String) comboFiltroTipo.getSelectedItem();

			String col[] = modelOS.getFilterColumnName();
			for(int i=0; i < col.length; i++) {
				if(recText.equalsIgnoreCase(col[i])==true){
					rf2 = RowFilter.regexFilter("(?i)" + fieldFiltroValor.getText(), i);
					break;
				}
			}

			if(recText.equalsIgnoreCase(" ") == true){
				JOptionPane.showMessageDialog(null," Selecione uma das opcoes para realizacao o filtro ");
				return;
			}

		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf2);
	}

	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		if (tabelaOS != null) {
			carregarTabelaOS();
		}
		if (comboFiltroTipo!= null) {
			carregarComboFiltro();
		}
		if(fieldFuncionarioResponsavel != null)
			carregarComboFuncionario();
		if(fieldPessoalAlocar != null)
			carregarComboFuncionarioAlocar();
		if(fieldFreelancerAlocar != null)
			carregarComboFreelancer();
	}

	private void carregarComboFiltro(){
		comboFiltroTipo.removeAllItems();
		String [] col = modelOS.getFilterColumnName();
		comboFiltroTipo.addItem(" ");
		for(String c: col) {
			if(!c.equals("X"))
				comboFiltroTipo.addItem(c);
		}
	}

	private JPanel getPanelRecursoEquipamento() {
		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());
		panelRecursos.add(new TitledPanel("Recursos Solicitados", getTabelaRecursos()), new RestricaoLayout(0,0,1,1,true, true));
		panelRecursos.add(new TitledPanel("Equipamentos Já Registrados", getTabelaEquipamentosRegistrados()),  new RestricaoLayout(0,1,1,1,true, true));
		panelRecursos.add(new TitledPanel("Equipamentos a Enviar", getPanelEquipamentoEnviado()),  new RestricaoLayout(1,0,2,1,true, true));
		return panelRecursos;	
	}

	private void desabilitarRecursos() {
		tabelaEquipamentoEnviado.setEnabled(false);
		tabelaEquipamentosRegistrados.setEnabled(false);
		tabelaPessoalJaAlocado.setEnabled(false);
		tabelaRecursoHumanoSolicitado.setEnabled(false);
		tabelaRecursosSolicitados.setEnabled(false);
		fieldIdEquipamento.setEnabled(false);
		fieldFuncionarioResponsavel.setEnabled(false);
		fieldPessoalAlocar.setEnabled(false);
		fieldPessoalAlocarDataFim.setEnabled(false);
		fieldPessoalAlocarDataInicio.setEnabled(false);
		fieldFreelancerAlocar.setEnabled(false);
		fieldFreelancerAlocarDataInicio.setEnabled(false);
		fieldFreelancerAlocarDataFim.setEnabled(false);
		botaoAddPessoal.setEnabled(false);
		botaoSalvar.setEnabled(false);
		botaoImprimir.setEnabled(false);
		botaoImprimirSubgrupos.setEnabled(false);
		botaoAddFreelancer.setEnabled(false);
	}
	private void habilitarRecursos() {
		tabelaEquipamentoEnviado.setEnabled(true);
		tabelaEquipamentosRegistrados.setEnabled(true);
		tabelaPessoalJaAlocado.setEnabled(true);
		tabelaRecursoHumanoSolicitado.setEnabled(true);
		tabelaRecursosSolicitados.setEnabled(true);
		fieldIdEquipamento.setEnabled(true);
		fieldFuncionarioResponsavel.setEnabled(true);
		fieldPessoalAlocar.setEnabled(true);
		fieldPessoalAlocarDataFim.setEnabled(true);
		fieldPessoalAlocarDataInicio.setEnabled(true);
		fieldFreelancerAlocar.setEnabled(true);
		fieldFreelancerAlocarDataInicio.setEnabled(true);
		fieldFreelancerAlocarDataFim.setEnabled(true);
		botaoAddPessoal.setEnabled(true);
		botaoSalvar.setEnabled(true);
		botaoImprimir.setEnabled(true);
		botaoImprimirSubgrupos.setEnabled(true);
		botaoAddFreelancer.setEnabled(true);
	}

	private JPanel getPanelEquipamentoEnviado() {
		JPanel equipamento = new JPanel();
		fieldFuncionarioResponsavel = new JComboBox();
		fieldIdEquipamento = new JBarCodeInputField();
		fieldIdEquipamento.setListener(
				new BarCodeListener() {
					public void barCodeEntered(String code) {
						Equipamento eq = Facade.getInstance().localizarEquipamentoCodigo(code);
						if(eq != null) {
							System.out.println(code);
							Beep bp = new Beep();
							switch(eq.getStatus()) {
							case DISPONIVEL:
								if( Facade.getInstance().localizarEquipamentoEnviado(eq.getNumeroSerie()) == null) {
									if(!modelEquipamento.getEquipamentos().contains(eq))
										modelEquipamento.addElement(eq);
								}
								break;
							case LOCADO:
								bp.start();
								JOptionPane.showMessageDialog(null, "Este equipamento já se encontra locado.\nRegistre o retorno deste equipamento antes de lançá-lo em outra Ordem de Serviço.", "ERRO", JOptionPane.ERROR_MESSAGE);
								break;
							case INATIVO:
								bp.start();
								JOptionPane.showMessageDialog(null, "Este equipamento foi marcado como indisponível.\nInforme ao gerente do estoque.", "ERRO", JOptionPane.ERROR_MESSAGE);
								break;
							case MANUTENCAO_CORRETIVA:
							case MANUTENCAO_PREVENTIVA:
								bp.start();								
								JOptionPane.showMessageDialog(null, "Este equipamento se encontra em manutenção.\nVerifique se o mesmo está funcionando corretamente e registre o seu retorno.", "ERRO", JOptionPane.ERROR_MESSAGE);
								break;
							default:
								java.awt.Toolkit.getDefaultToolkit().beep();	
							}
						}
					}
				}
		);
		
		carregarComboFuncionario();
		equipamento.setLayout(new GridBagLayout());
		equipamento.add(fieldIdEquipamento, new RestricaoLayout(0,0,1,true,false));
		equipamento.add(getBotaoProcurarEquipamento(), new RestricaoLayout(0,1,false,false));
		equipamento.add(getTabelaEquipamento(), new RestricaoLayout(1,0,2,1,true, true));
		return equipamento;

	}
	
	public JButton getBotalLocalizarOS() {
		botalLocalizarOS = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botalLocalizarOS.setHorizontalAlignment(SwingConstants.CENTER);
		botalLocalizarOS.setVerticalAlignment(SwingConstants.CENTER);
		botalLocalizarOS.setPreferredSize(new Dimension(20,20));
		
		botalLocalizarOS.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
							List<OrdemServico> listaEquipamento = Facade.getInstance().listarOrdemServicosEnviarEquipamento();
							DialogSearchOS2 teste = new DialogSearchOS2(FormEnviarEquipamento.this, listaEquipamento);
							OrdemServico c = teste.showDialog(FormEnviarEquipamento.this);
							
							if(c != null) {
								fieldData.setDate(null);
								carregarTabelaOS();
								OrdemServicoTableModel modelo = (OrdemServicoTableModel) tabelaOS.getModel();
								modelo.addElement(c);
							}
					}
				}	
		
		);
		return botalLocalizarOS;
	}

	public JButton getBotaoProcurarEquipamento() {
		JButton botaoProcurarEquipamento = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarEquipamento.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarEquipamento.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarEquipamento.setPreferredSize(new Dimension(20,20));

		botaoProcurarEquipamento.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
							Set<StatusEquipamento> status = new HashSet<StatusEquipamento>();
							status.add(StatusEquipamento.DISPONIVEL);
							List<Equipamento> listaEquipamento = Facade.getInstance().listarEquipamentosDisponiveis();
							listaEquipamento.removeAll(modelEquipamento.getEquipamentos());
							DialogSearchEquipamento teste = new DialogSearchEquipamento(FormEnviarEquipamento.this, listaEquipamento);
							Equipamento c = teste.showDialog(FormEnviarEquipamento.this);
							
							if(c != null) {
								Equipamento eq = Facade.getInstance().carregarEquipamento(c.getId());
								if( Facade.getInstance().localizarEquipamentoEnviado(c.getNumeroSerie()) == null) {
									switch(eq.getStatus()) {
									case DISPONIVEL:
										if(!modelEquipamento.getEquipamentos().contains(eq))
											modelEquipamento.addElement(c);
										break;
									case LOCADO:
										JOptionPane.showMessageDialog(null, "Este equipamento já se encontra locado.\nRegistre o retorno deste equipamento antes de lançá-lo em outra Ordem de Serviço.", "ERRO", JOptionPane.ERROR_MESSAGE);
										break;
									case INATIVO:
										JOptionPane.showMessageDialog(null, "Este equipamento foi marcado como indisponível.\nInforme ao gerente do estoque.", "ERRO", JOptionPane.ERROR_MESSAGE);
										break;
									case MANUTENCAO_CORRETIVA:
									case MANUTENCAO_PREVENTIVA:
										JOptionPane.showMessageDialog(null, "Este equipamento se encontra em manutenção.\nVerifique se o mesmo está funcionando corretamente e registre o seu retorno.", "ERRO", JOptionPane.ERROR_MESSAGE);
										break;
									default:
										java.awt.Toolkit.getDefaultToolkit().beep();	
									}
								}

							}
					}
				}	
		);
		return botaoProcurarEquipamento;
	}
	
	
	private JPanel getPanelPessoal() {
		JPanel panelPessoal = new JPanel();
		panelPessoal.setLayout(new GridBagLayout());
		modelRecursoHumano = new RecursoHumanoSolicitadoTableModel();
		tabelaRecursoHumanoSolicitado = new JTable(modelRecursoHumano);
		JScrollPane scTabelaRecursoHumano = new JScrollPane();
		scTabelaRecursoHumano.setViewportView(tabelaRecursoHumanoSolicitado);
		panelPessoal.add(new TitledPanel("Recursos Humanos Solicitados", scTabelaRecursoHumano), new RestricaoLayout(0, 0, 3, 1, true, true));
		panelPessoal.add(new TitledPanel("Registrar Pessoas", getPanelPessoalAlocar()), new RestricaoLayout(0,3,1,1,true,true));

		TableColumn column = null;
		int columnWidth[] = modelRecursoHumano.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaRecursoHumanoSolicitado.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}			


		return panelPessoal;
	}

	private JPanel getPanelTabelaFreelancer() {
		JPanel panelFreelancer = new JPanel();
		panelFreelancer.setLayout(new GridBagLayout());

		tabelaRecursoHumanoSolicitadoFreelancer = new JTable(modelRecursoHumano);
		JScrollPane scTabelaRecursoHumano = new JScrollPane();
		scTabelaRecursoHumano.setViewportView(tabelaRecursoHumanoSolicitadoFreelancer);
		panelFreelancer.add(new TitledPanel("Recursos Humanos Solicitados", scTabelaRecursoHumano), new RestricaoLayout(0, 0, 3, 1, true, true));
		panelFreelancer.add(new TitledPanel("Registrar Pessoas", getPanelFreelancer()), new RestricaoLayout(0,3,1,1,true,true));

		TableColumn column = null;
		int columnWidth[] = modelRecursoHumano.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaRecursoHumanoSolicitadoFreelancer.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}			


		return panelFreelancer;
	}

	private void carregarComboFuncionarioAlocar() {
		fieldPessoalAlocar.removeAllItems();
		fieldPessoalAlocar.addItem("Selecione o Funcionário");
		for(Funcionario f: Facade.getInstance().listarFuncionarios()) {
			fieldPessoalAlocar.addItem(f);
		}
	}
	
	private void carregarComboFreelancer() {
		fieldFreelancerAlocar.removeAllItems();
		fieldFreelancerAlocar.addItem("Selecione o Freelancer");
		for(Freelancer f: Facade.getInstance().listarFreelancers()) {
			fieldFreelancerAlocar.addItem(f);
		}
	}

	private JPanel getPanelPessoalAlocar() {
		fieldPessoalAlocar = new JComboBox();
		fieldPessoalAlocarDataInicio = new JDateChooser();
		fieldPessoalAlocarDataFim = new JDateChooser();

		JPanel pessoalAlocar = new JPanel();
		pessoalAlocar.setLayout(new GridBagLayout());

		pessoalAlocar.setLayout(new GridBagLayout());

		pessoalAlocar.add(new TitledPanel("Funcionário", fieldPessoalAlocar), new RestricaoLayout(0, 0, 2, true, false));
		pessoalAlocar.add(new TitledPanel("Data Inicial", fieldPessoalAlocarDataInicio), new RestricaoLayout(0, 2, 1, true, false));
		pessoalAlocar.add(new TitledPanel("Data Final", fieldPessoalAlocarDataFim), new RestricaoLayout(0, 3, 1, true, false));
		pessoalAlocar.add(new TitledPanel(" ", getBotaoAdicionarPessoalAlocar()) , new RestricaoLayout(0, 4, false, false));
		pessoalAlocar.add(new TitledPanel("Pessoal Alocado", getTabelaPessoalJaAlocado()),  new RestricaoLayout(1,0,5,1,true, true));
		return pessoalAlocar;
	}

	private JPanel getPanelFreelancer() {
		fieldFreelancerAlocar = new JComboBox();
		fieldFreelancerAlocarDataInicio = new JDateChooser();
		fieldFreelancerAlocarDataFim = new JDateChooser();

		JPanel pessoalAlocar = new JPanel();
		pessoalAlocar.setLayout(new GridBagLayout());

		pessoalAlocar.setLayout(new GridBagLayout());

		pessoalAlocar.add(new TitledPanel("Funcionário", fieldFreelancerAlocar), new RestricaoLayout(0, 0, 2, true, false));
		pessoalAlocar.add(new TitledPanel("Data Inicial", fieldFreelancerAlocarDataInicio), new RestricaoLayout(0, 2, 1, true, false));
		pessoalAlocar.add(new TitledPanel("Data Final", fieldFreelancerAlocarDataFim), new RestricaoLayout(0, 3, 1, true, false));
		pessoalAlocar.add(new TitledPanel(" ", getBotaoAdicionarFreelancer()) , new RestricaoLayout(0, 4, false, false));
		pessoalAlocar.add(new TitledPanel("Pessoal Alocado", getTabelaFreelancerJaAlocado()),  new RestricaoLayout(1,0,5,1,true, true));
		return pessoalAlocar;
	}

	private JButton getBotaoAdicionarPessoalAlocar() {
		botaoAddPessoal = new JButton("", new ImageIcon("images/add_12.png"));
		botaoAddPessoal.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAddPessoal.setVerticalAlignment(SwingConstants.CENTER);
		botaoAddPessoal.setPreferredSize(new Dimension(18,18));
		botaoAddPessoal.setMaximumSize(new Dimension(22,22));

		botaoAddPessoal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(os == null){
					JOptionPane.showMessageDialog(null, "Selecione uma Ordem de Servico", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(fieldPessoalAlocar.getSelectedItem() instanceof Funcionario){
					PessoalAlocado pa = new PessoalAlocado();
					pa.setFuncionario((Funcionario) fieldPessoalAlocar.getSelectedItem());
					pa.setDataFim(fieldPessoalAlocarDataFim.getDate());
					pa.setDataInicio(fieldPessoalAlocarDataInicio.getDate());
					modelPessoalJaAlocado.addElement(pa);
					limparFuncionario();
				} 

			}
		});
		return botaoAddPessoal;
	}

	private JButton getBotaoAdicionarFreelancer() {
		botaoAddFreelancer = new JButton("", new ImageIcon("images/add_12.png"));
		botaoAddFreelancer.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAddFreelancer.setVerticalAlignment(SwingConstants.CENTER);
		botaoAddFreelancer.setPreferredSize(new Dimension(18,18));
		botaoAddFreelancer.setMaximumSize(new Dimension(22,22));

		botaoAddFreelancer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(os == null){
					JOptionPane.showMessageDialog(null, "Selecione uma Ordem de Servico", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(fieldFreelancerAlocar.getSelectedItem() instanceof Freelancer){
					RegistroFreelancer pa = new RegistroFreelancer();
					pa.setFreelancer((Freelancer) fieldFreelancerAlocar.getSelectedItem());
					pa.setDataFim(fieldFreelancerAlocarDataFim.getDate());
					pa.setDataInicio(fieldFreelancerAlocarDataInicio.getDate());
					modelFreelancerJaAlocado.addElement(pa);
					limparFreelancer();
				} 

			}
		});
		return botaoAddFreelancer;
	}

	public void carregarComboFuncionario() {
		fieldFuncionarioResponsavel.removeAllItems();
		fieldFuncionarioResponsavel.addItem("Selecione o Responsável");
		for(Funcionario f: Facade.getInstance().listarFuncionarios()) {
			fieldFuncionarioResponsavel.addItem(f);
		}
	}



	private void limparFuncionario() {
		fieldPessoalAlocar.setSelectedIndex(0);
		if(os != null) {
			fieldPessoalAlocarDataInicio.setDate(os.getDataInicio());
			fieldPessoalAlocarDataFim.setDate(os.getDataFim());
		}
	}

	
	private void limparFreelancer() {
		fieldFreelancerAlocar.setSelectedIndex(0);
		if(os != null) {
			fieldFreelancerAlocarDataInicio.setDate(os.getDataInicio());
			fieldFreelancerAlocarDataFim.setDate(os.getDataFim());
		}
	}
	

	public JPanel getPanelConfirmar() {
		JPanel confirmar = new JPanel();
		confirmar.setLayout(new GridBagLayout());
		confirmar.add(new JPanel(), new RestricaoLayout(0,0,1,true, false));
		
		confirmar.add(getBotaoImprimirSubgrupos(), new RestricaoLayout(0,1,1,true, false));
		confirmar.add(getBotaoSalvarOS(), new RestricaoLayout(0,2,1,true, false));
		confirmar.add(getBotaoImpimirOS(), new RestricaoLayout(0,3,1,true, false));

		confirmar.add(new JPanel(), new RestricaoLayout(0,4,1,true, false));
		return confirmar;
	}

	public JButton getBotaoSalvarOS() {
		botaoSalvar = new JButton("Registrar Equipamentos / Profissionais", new ImageIcon("images/save_24.png"));
		botaoSalvar.setHorizontalAlignment(SwingConstants.CENTER);
		botaoSalvar.setVerticalAlignment(SwingConstants.CENTER);
		botaoSalvar.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(os == null) {
							JOptionPane.showMessageDialog(null, "Selecione uma Ordem de Serviço.", "ERRO!", JOptionPane.ERROR_MESSAGE );
							return;
						}
						if(modelEquipamento.getEquipamentos().size() == 0 && modelPessoalJaAlocado.getPessoalAlocado().size() == os.getPessoalAlocado().size()
								&& modelFreelancerJaAlocado.getFreelancerAlocado().size() == os.getFreelancerAlocado().size()) {
							JOptionPane.showMessageDialog(null, "Adicione os equipamentos, profissionais ou freelancers à Ordem de Serviço.", "ERRO!", JOptionPane.ERROR_MESSAGE );
							return;
						}

						if(fieldFuncionarioResponsavel.getSelectedItem() == "Selecione o Responsável" || 
								fieldFuncionarioResponsavel.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Selecione o Responsável.", "ERRO!", JOptionPane.ERROR_MESSAGE );
							return;
						}

						List<String> listaId = new ArrayList<String>();


						List<EquipamentoEnviado> lista = new ArrayList<EquipamentoEnviado>();
						for(Equipamento e: modelEquipamento.getEquipamentos()) {
							EquipamentoEnviado eqe = new EquipamentoEnviado();
							eqe.setDataSaida(new Date());
							eqe.setEquipamento(e);
							eqe.setUsuario(Facade.getInstance().getUsuarioLogado());
							eqe.setFuncionarioEntrega((Funcionario) fieldFuncionarioResponsavel.getSelectedItem());
							lista.add(eqe);
							listaId.add(e.getNumeroSerie());
						}
						if(os.getEquipamentoEnviado() == null){
							os.setEquipamentoEnviado(lista);
						} else {
							os.getEquipamentoEnviado().addAll(lista);
						}
						
						if(os.getStatus() == StatusOS.OS_EMERGENCIAL || os.getStatus() == StatusOS.OS_EMERGENCIAL_INICIADA)
							os.setStatus(StatusOS.OS_EMERGENCIAL_INICIADA);
						else
							os.setStatus(StatusOS.EM_REALIZACAO);
						
						os.setPessoalAlocado(modelPessoalJaAlocado.getPessoalAlocado());
						os.setFreelancerAlocado(modelFreelancerJaAlocado.getFreelancerAlocado());
						
						os.setResponsavelEquipamento((Funcionario) fieldFuncionarioResponsavel.getSelectedItem());
						Facade.getInstance().atualizarOrdemServico(os);

						//imprimir aqui

						JOptionPane.showMessageDialog(null,"Ordem de Serviço atualizada com sucesso!");		
						int result = JOptionPane.showConfirmDialog(null,"Deseja visualizar o relatório de equipamentos enviados?", "Impressão de Dados", JOptionPane.YES_NO_OPTION);

						if (result == JOptionPane.YES_OPTION) {
							HashMap<String, Object> hm = new HashMap<String, Object>();
							hm.put("id", os.getId());
							hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
							hm.put("listaEquipamentos", listaId);
							hm.put("responsavelEstoque", Facade.getInstance().getUsuarioLogado().getFuncionario().getNome());
							hm.put("responsavelMotorista", ((Funcionario)fieldFuncionarioResponsavel.getSelectedItem()).getNome());

							try {
								Connection c  = Facade.getInstance().getConnection() ;
								URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordemServicoEquipamentosEnviadosParcial.jasper");  
								JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
								JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
								JasperViewer.viewReport(impressao,false);
								c.close();
							} catch (JRException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
						fieldData.setDate(new Date());
					}
				}
		);
		return botaoSalvar;
	}

	public JButton getBotaoImpimirOS() {
		botaoImprimir = new JButton("Imprimir Equipamentos Enviados", new ImageIcon("images/imprimir_24.png"));
		botaoImprimir.setHorizontalAlignment(SwingConstants.CENTER);
		botaoImprimir.setVerticalAlignment(SwingConstants.CENTER);
		botaoImprimir.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(os == null) {
							JOptionPane.showMessageDialog(null, "Selecione uma Ordem de Serviço.", "ERRO!", JOptionPane.ERROR_MESSAGE );
							return;
						}

						if(os.getEquipamentoEnviado() == null || os.getEquipamentoEnviado().size() == 0) {
							JOptionPane.showMessageDialog(null, "A Ordem de Serviço selecionada não possui equipamentos enviados.", "ERRO!", JOptionPane.ERROR_MESSAGE );
							return;
						}
						HashMap<String, Object> hm = new HashMap<String, Object>();
						hm.put("id", os.getId());
						hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
						hm.put("responsavelEstoque", Facade.getInstance().getUsuarioLogado().getFuncionario().getNome());
						hm.put("responsavelMotorista", "");

						try {
							Connection c  = Facade.getInstance().getConnection() ;
							URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordemServicoEquipamentosEnviados.jasper");  
							JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
							JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
							JasperViewer.viewReport(impressao,false);
						} catch (JRException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		);
		return botaoImprimir;
	}
	
	
	public JButton getBotaoImprimirSubgrupos() {
		botaoImprimirSubgrupos = new JButton("Imprimir Subgrupos Solicitados", new ImageIcon("images/imprimir_24.png"));
		botaoImprimirSubgrupos.setHorizontalAlignment(SwingConstants.CENTER);
		botaoImprimirSubgrupos.setVerticalAlignment(SwingConstants.CENTER);
		botaoImprimirSubgrupos.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(os == null) {
							JOptionPane.showMessageDialog(null, "Selecione uma Ordem de Serviço.", "ERRO!", JOptionPane.ERROR_MESSAGE );
							return;
						}

						HashMap<String, Object> hm = new HashMap<String, Object>();
						hm.put("id", os.getId());
						hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

						try {
							Connection c  = Facade.getInstance().getConnection() ;
							URL arquivo = null;
							
							if(os.getStatus() == StatusOS.OS_EMERGENCIAL || os.getStatus() == StatusOS.OS_EMERGENCIAL_INICIADA || os.getStatus() == StatusOS.OS_EMERGENCIAL_CONCLUIDA){
								arquivo = getClass().getResource("/br/com/sne/sistema/report/ordemServicoEmergencial.jasper");
							} else {
								arquivo = getClass().getResource("/br/com/sne/sistema/report/ordem_servico.jasper");
							}
							
							JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
							JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
							JasperViewer.viewReport(impressao,false);
						} catch (JRException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		);
		return botaoImprimirSubgrupos;
	}
	
	
}
