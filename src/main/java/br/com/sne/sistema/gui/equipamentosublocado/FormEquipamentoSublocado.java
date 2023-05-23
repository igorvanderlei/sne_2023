package br.com.sne.sistema.gui.equipamentosublocado;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.EquipamentoSublocado;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.RegistroSublocacao;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchFornecedor;
import br.com.sne.sistema.gui.util.form.DialogSearchRecurso;

public class FormEquipamentoSublocado extends DefaultForm<RegistroSublocacao, RegistroSublocacaoTableModel> {
	private static final long serialVersionUID = 1L;

	private Recurso recurso = null;
	
	private JTextField fieldID;
	private JTextField fieldFornecedorID;
	private JTextField fieldFornecedor;
	private JTextArea fieldObservacoes;
	private JDateChooser fieldDataInicio;
	private JDateChooser fieldDataFim;
	private JMoedaRealTextField fieldPreco;
	
	
	private JTextField fieldRecursoID;
	private JTextField fieldRecursoNome;
	private JTextField fieldRecursoQuantidade;
	
	private JTable tabelaRecursosSolicitados;
	private EquipamentoSublocadoTableModel modelRecurso;
	private JPopupMenu menuTabela;
	
	
	private RegistroSublocacao equipSub;
	private Fornecedor fornecedor;
	
	public FormEquipamentoSublocado() {
		super(new RegistroSublocacaoTableModel(), "/images/icon_sublocado_18.png", "Equipamentos Sublocados");
		desabilitarBotaoAtualizar();
	}
	

	public boolean print(RegistroSublocacao current) {
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(equipSub != null){
		hm.put("id",  equipSub.getId());
		hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
		try {
			Connection c  = Facade.getInstance().getConnection() ;
			URL arquivo = getClass().getResource("/br/com/sne/sistema/report/registroSublocacao.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um Equipamento para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	
	public boolean save(RegistroSublocacao current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarRegistroSublocacao(current);
			equipSub = current;
			String codigo = fornecedor.getCodigo() + String.format("%07d", current.getId());
			int i = 1;
			List<EquipamentoSublocado> lista =  modelRecurso.getEquipamentos();
			for(EquipamentoSublocado eqs : lista) {
				eqs.setDataInicio(fieldDataInicio.getDate());
				eqs.setDataFim(fieldDataFim.getDate());
				eqs.setFornecedor(fornecedor);
				eqs.setNumeroSerie(codigo + String.format("%03d", i));
				eqs.setStatus(StatusEquipamento.DISPONIVEL);
				i++;
			}
			current.setEquipamentos(lista);
			current.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
			Facade.getInstance().atualizarRegistroSublocacao(current);
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Equipamento já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
			err.printStackTrace();
		}
		return s;
	}

	public boolean update(RegistroSublocacao current) {
		boolean s = true;
		//Facade.getInstance().atualizarRegistroSublocacao(current);
		return s;
	}
	
	public boolean remove(RegistroSublocacao current) {
		boolean test = false;
		try {
			Facade.getInstance().removerRegistroSublocacao(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Registro de Sublocação.\n Verifique se existem equipamentos associados à alguma ordem de serviço antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldPreco = new JMoedaRealTextField();
		fieldFornecedorID = new JTextField();
		fieldFornecedor = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldDataInicio = new JDateChooser();
		fieldDataFim = new JDateChooser();
		fieldFornecedor.setEditable(false);
		fieldDataInicio.setMinSelectableDate(new Date());
		fieldDataFim.setMinSelectableDate(new Date());
		
		fieldDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataInicio.getDate() != null)
							fieldDataFim.setMinSelectableDate(fieldDataInicio.getDate());
					}
				}
		);
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0,  false, false));
		this.addInputField(new TitledPanel("Fornecedor", getPanelCodigoFornecedor()), new RestricaoLayout(0, 1,2, true, false));
		this.addInputField(new TitledPanel("Data Inicial", fieldDataInicio), new RestricaoLayout(0, 3, 1, true, false));
		this.addInputField(new TitledPanel("Data Final", fieldDataFim), new RestricaoLayout(0, 4, 1, true, false));	
		this.addInputField(new TitledPanel("Preço", fieldPreco), new RestricaoLayout(0, 5, 1, true, false));
		
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(1, 0, 6, 1, true, true));
		this.addInputField(new TitledPanel("Equipamentos", getPanelRecursos()), new RestricaoLayout(2, 0, 6, 3, true, true));
		
		
	}
	
	
	
	public JPanel getPanelCodigoFornecedor() {
		JPanel panelFornecedor = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		panelFornecedor.setLayout(layout);
		panelFornecedor.setBorder(new EmptyBorder(0,0,0,0));
		
		JButton botaoFornecedor = getBotaoProcurarFornecedor();

		fieldFornecedorID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idFornecedor = Integer.parseInt(fieldFornecedorID.getText());
						Fornecedor f = Facade.getInstance().carregarFornecedor(idFornecedor);
						carregarFornecedor(f);
					} catch (Exception e){
						limparFornecedor();
					}
				}
			}
		});

		fieldFornecedorID.setPreferredSize(new Dimension(40,22));
		botaoFornecedor.setPreferredSize(new Dimension(22,22));
		
		panelFornecedor.add(fieldFornecedorID, new RestricaoLayout(0,0));
		panelFornecedor.add(botaoFornecedor, new RestricaoLayout(0,1));
		GridBagConstraints rl = new RestricaoLayout(0,2,1,true,false);
		rl.insets = new Insets(0,0,0,0);
		rl.ipady = 0;
		panelFornecedor.add(fieldFornecedor, rl);
		
		return panelFornecedor;
	}
	
	public JButton getBotaoProcurarFornecedor() {
		JButton botaoProcurarFornecedor = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));

		botaoProcurarFornecedor.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchFornecedor teste = new DialogSearchFornecedor(FormEquipamentoSublocado.this);
						Fornecedor f = teste.showDialog(FormEquipamentoSublocado.this);
						if(f != null)
							carregarFornecedor(f);
					}
				}	
		);
		return botaoProcurarFornecedor;
	}
	
	public void carregarFornecedor(Fornecedor f){
		if(f != null){
			fieldFornecedorID.setText(""+f.getId());
			fieldFornecedor.setText(f.getNome());
			fornecedor = f;
		}
		else
			limparFornecedor();
	}
	
	public void limparFornecedor(){
		fieldFornecedorID.setText("");
		fieldFornecedor.setText("");
		fornecedor = null;
	}
	
	
	public RegistroSublocacao newElement() {
		return new RegistroSublocacao();
	}

	public void loadInputFields(RegistroSublocacao registro) {
		String observacoes = fieldObservacoes.getText();
		Date inicio = fieldDataInicio.getDate();
		Date fim = fieldDataFim.getDate();
		
		registro.setObservacoes(observacoes);
		registro.setFornecedor(fornecedor);
		registro.setDataInicio(inicio);
		registro.setDataFim(fim);
		try {
			registro.setPreco(fieldPreco.getValor());
		} catch (Exception e) {}
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldPreco.clear();
		limparFornecedor();
		fieldDataInicio.setDate(null);
		fieldDataFim.setDate(null);
		fieldObservacoes.setText("");
		equipSub = null;
		modelRecurso.removeAllElements();
	}

	protected void loadForm(RegistroSublocacao rec) {
		fieldID.setText("" + rec.getId());
		fieldPreco.setValor(rec.getPreco());
		fieldFornecedor.setText(rec.getFornecedor().getNome());
		fieldObservacoes.setText(rec.getObservacoes());
		fieldFornecedorID.setText(""+rec.getFornecedor().getId());
		fieldFornecedor.setText(rec.getFornecedor().getNome());
		fieldDataInicio.setDate(rec.getDataInicio());
		fieldDataFim.setDate(rec.getDataFim());
		equipSub = rec;
		
		modelRecurso.removeAllElements();
		for(EquipamentoSublocado eqs:rec.getEquipamentos()) {
			modelRecurso.addElement(eqs);
		}
		
	}

	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		
		if(fornecedor == null) {
			test = false;
			error += "\nPreencha corretamente o fornecedor do equipamento";
		}
		
		if(modelRecurso.getEquipamentos().size() == 0) {
			test = false;
			error += "\nInforme os equipamentos sublocados";
		}
		if(fieldDataFim.getDate() == null){
			test = false;
			error += "\nInforme a data de fim da sublocação";
		}
		if(fieldDataInicio.getDate() == null){
			test = false;
			error += "\nInforme a data de início da sublocação";
		}
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<RegistroSublocacao> listAll() {
		return Facade.getInstance().listarRegistroSublocacaos();
	}
	
	public JPanel getPanelRecursos() {
		fieldRecursoNome = new JTextField();
		fieldRecursoNome.setEditable(false);
		fieldRecursoQuantidade = new JIntField();
		
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());

		modelRecurso = new EquipamentoSublocadoTableModel();
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());

		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());

		JPanel adicionar = new JFormGroup("Adicionar Recurso");
		adicionar.setLayout(new GridBagLayout());
		
		adicionar.add(new TitledPanel("Codigo", getPanelCodigoRecurso()), new RestricaoLayout(0, 0, false, false));
		adicionar.add(new TitledPanel("Recurso", fieldRecursoNome), new RestricaoLayout(0, 1, 1, true, false));
		adicionar.add(new TitledPanel("Quant.", fieldRecursoQuantidade), new RestricaoLayout(0, 2, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecurso()) , new RestricaoLayout(0, 6, false, false));
		
		panelRecursos.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelRecursos.add(new TitledPanel("Recursos Solicitados", scrollTabela), new RestricaoLayout(1,0,1,1,true,true));

		limparRecurso();
		return panelRecursos;		
	}
	
	public JPanel getPanelCodigoRecurso() {
		JPanel panelRecurso = new JPanel();
		panelRecurso.setLayout(new BoxLayout(panelRecurso, BoxLayout.LINE_AXIS));
		JButton botaoRecurso = getBotaoProcurarRecurso();
		fieldRecursoID = new JIntField();
		fieldRecursoID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idRecurso = Integer.parseInt(fieldRecursoID.getText());
						Recurso c = Facade.getInstance().carregarRecurso(idRecurso);
						carregarRecurso(c);
					} catch (Exception e){
						limparRecurso();
					}
				}
			}
		});

		botaoRecurso.setPreferredSize(new Dimension(30,18));
		panelRecurso.add(fieldRecursoID);
		panelRecurso.add(botaoRecurso);
		return panelRecurso;
	}
	
	public JButton getBotaoProcurarRecurso() {
		JButton botaoProcurarRecurso = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		
		botaoProcurarRecurso.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						List<Recurso> listaRecurso = new ArrayList<Recurso>();
						listaRecurso.addAll(Facade.getInstance().listarDescricoesEquipamentos());
						DialogSearchRecurso teste = new DialogSearchRecurso(FormEquipamentoSublocado.this, listaRecurso);
						Recurso r = teste.showDialog(FormEquipamentoSublocado.this);
						if(r != null)
							carregarRecurso(r);
					}
				}	
		);
		return botaoProcurarRecurso;
	}
	
	private void carregarRecurso(Recurso r) {
		recurso = r;
		if(r!=null){
			fieldRecursoID.setText("" + r.getId());
			fieldRecursoNome.setText(r.getNome());
		} else {
			limparRecurso();
		}
	}
	
	public void limparRecurso() {
		recurso = null;
		fieldRecursoID.setText("");
		fieldRecursoNome.setText("");
		fieldRecursoQuantidade.setText("1");
	}

	private JButton getBotaoAdicionarRecurso() {
		JButton addRecursoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addRecursoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addRecursoButton.setVerticalAlignment(SwingConstants.CENTER);
		addRecursoButton.setPreferredSize(new Dimension(20,20));
		addRecursoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recurso != null) {
					for(int i=0;  i < Integer.parseInt(fieldRecursoQuantidade.getText()); i++) {
						EquipamentoSublocado eq = new EquipamentoSublocado();
						eq.setDescricaoEquipamento((DescricaoEquipamento) recurso);
						eq.setGrupo(recurso.getGrupo());
						eq.setStatus(StatusEquipamento.DISPONIVEL);
						modelRecurso.addElement(eq);
					}
				}
				limparRecurso();
			}
		});
		return addRecursoButton;
	}

	
	private JTable getTabelaRecursosSolicitados() {
		tabelaRecursosSolicitados = new JTable(modelRecurso);
		
		TableColumn column = null;
		int columnWidth[] = modelRecurso.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
		    column = tabelaRecursosSolicitados.getColumnModel().getColumn(i);
	        column.setPreferredWidth(columnWidth[i]);
		}
		
		
		tabelaRecursosSolicitados.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaRecursosSolicitados.getSelectedRow() >= 0 )
								menuTabela.show(tabelaRecursosSolicitados, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Recurso para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		return tabelaRecursosSolicitados;
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Recurso");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelRecurso.removeRow(tabelaRecursosSolicitados.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}

}

