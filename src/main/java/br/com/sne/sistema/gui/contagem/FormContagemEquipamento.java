package br.com.sne.sistema.gui.contagem;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;


import br.com.sne.sistema.bean.ContagemEstoque;
import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.rastreamento.FormRastreamento;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.BordedPanel;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormContagemEquipamento extends DefaultForm<ContagemEstoque, ContagemEstoqueTableModel>{
	private static final long serialVersionUID = 1L;

	private ContagemEstoque contagem;
	
	private JTable tabelaEquipamentosContados;
	private JTable tabelaEquipamentosDisponiveis;
	private JTable tabelaEquipamentosIndisponiveis;
	
	
	private ContagemEstoqueEquipamentoTableModel modelContagem;
	private ContagemEstoqueEquipamentoTableModel modelDisponiveis;
	private ContagemEstoqueEquipamentoTableModel modelIndisponiveis;
	
	private JComboBox fieldGrupo;
	private JComboBox fieldRecurso;
	private JButton botaoIniciar;
	private JPopupMenu menuTabela;
	
	private JBarCodeInputField fieldEquipamentoID;
	
	public FormContagemEquipamento(){
		super(new ContagemEstoqueTableModel(), "/images/icon_estoque_18.png", "Contagem de Estoque");
		desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
	}
	
	public void createInputFields(){
		fieldGrupo = new JComboBox();
		fieldGrupo.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object obj = fieldGrupo.getSelectedItem();
						if(obj instanceof Grupo) {
							carregarComboSubgrupo((Grupo) obj);
							carregarTabelas();
						}
					}
				}	
		);
		
		fieldRecurso = new JComboBox();
		fieldRecurso.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object obj = fieldRecurso.getSelectedItem();
						if(obj instanceof Recurso) {
							carregarTabelas();
						}
					}
				}
		);
		
		carregarComboGrupo();
		
		this.addInputField(new TitledPanel("Grupo", fieldGrupo), new RestricaoLayout(0, 0, 1, true, false));
		this.addInputField(new TitledPanel("Recurso", fieldRecurso), new RestricaoLayout(0, 1, 1, true, false));
		this.addInputField(new BordedPanel(getPanelEquipamento()), new RestricaoLayout(1,0,2,1,true,true));
	}
	
	private void carregarTabelas() {
		limparTabelas();
		
		Object grp = fieldGrupo.getSelectedItem();
		
		if(grp instanceof Grupo) {
			Grupo grupo = (Grupo) grp;
			Object recurso = fieldRecurso.getSelectedItem();

			List<Equipamento> lista;
			if(recurso instanceof DescricaoEquipamento) {
				lista = Facade.getInstance().listarEquipamentosPorRecurso((Recurso) recurso, false);
			} else {
				lista = Facade.getInstance().listarEquipamentosPorGrupo(grupo, false);
			}
			for(Equipamento eq: lista) {
				if(eq.getStatus() == StatusEquipamento.DISPONIVEL) {
					modelDisponiveis.addElement(eq);
				} else {
					modelIndisponiveis.addElement(eq);
				}
			}
		}

	}

	private void limparTabelas() {
		modelContagem.removeAllElements();
		modelDisponiveis.removeAllElements();
		modelIndisponiveis.removeAllElements();
	}
	
	
	private void carregarComboGrupo() {
		fieldGrupo.removeAllItems();
		fieldRecurso.removeAllItems();
		fieldGrupo.addItem("");
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		for(Grupo grp: lista) {
			fieldGrupo.addItem(grp);
		}
	}
	
	private void carregarComboSubgrupo(Grupo g) {
		List<Recurso> lista = Facade.getInstance().listarRecurso(g);
		fieldRecurso.removeAllItems();
		fieldRecurso.addItem("");
		for(Recurso rec: lista) {
			if(rec instanceof DescricaoEquipamento)
				fieldRecurso.addItem(rec);
		}
	}	
	private JPanel getPanelEquipamento() {
		JPanel equipamento = new JPanel();
		equipamento.setLayout(new GridBagLayout());
		equipamento.add(new TitledPanel("Registro do Equipamento", getPanelCodigoEquipamento()), new RestricaoLayout(0,0,3,true,false));
		equipamento.add(new TitledPanel("Equipamentos Disponíveis", getTabelaEquipamentoDisponiveis()), new RestricaoLayout(1,0,1,1,true, true));
		equipamento.add(new TitledPanel("Equipamentos Registrados", getTabelaEquipamento()), new RestricaoLayout(1,1,1,1,true, true));
		equipamento.add(new TitledPanel("Equipamentos Indisponíveis", getTabelaEquipamentoIndisponiveis()), new RestricaoLayout(1,2,1,1,true, true));
		return equipamento;

	}
	
	private JComponent getTabelaEquipamento() {
		JScrollPane panelTabela = new JScrollPane();
		modelContagem = new ContagemEstoqueEquipamentoTableModel();
		tabelaEquipamentosContados = new JTable(modelContagem);
		tabelaEquipamentosContados.setFillsViewportHeight(true);
		tabelaEquipamentosContados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumn column = null;
		int columnWidth[] = modelContagem.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamentosContados.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	
		panelTabela.setViewportView(tabelaEquipamentosContados);
		return panelTabela;
	}
	
	private JComponent getTabelaEquipamentoDisponiveis() {
		JScrollPane panelTabela = new JScrollPane();
		modelDisponiveis = new ContagemEstoqueEquipamentoTableModel();
		tabelaEquipamentosDisponiveis = new JTable(modelDisponiveis);
		tabelaEquipamentosDisponiveis.setFillsViewportHeight(true);
		tabelaEquipamentosDisponiveis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumn column = null;
		int columnWidth[] = modelDisponiveis.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamentosDisponiveis.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	

		menuTabela = new JPopupMenu();
		JMenuItem menuRastrear = new JMenuItem("Rastrear ...");
		menuRastrear.setIcon(new ImageIcon(getClass().getResource("/images/tracking-18.png")));
		menuTabela.add(menuRastrear);
		menuRastrear.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int linha = tabelaEquipamentosDisponiveis.getSelectedRow();
						if(linha >= 0) {
							String codigo = ""+tabelaEquipamentosDisponiveis.getValueAt(linha, 0);
							FormRastreamento telaRastreamento = (FormRastreamento) WindowFactory.createTelaRastreamento(Facade.getInstance().getTelaPrincipal().getDesktop());
							telaRastreamento.rastrear(codigo);
							telaRastreamento.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(FormContagemEquipamento.this, "Selecione um equipamento!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}						
					}
				}
		);
		
		tabelaEquipamentosDisponiveis.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							menuTabela.show(tabelaEquipamentosDisponiveis, arg0.getX(), arg0.getY());
						} 						
					}
				}
		);
		
		panelTabela.setViewportView(tabelaEquipamentosDisponiveis);
		return panelTabela;
	}
	
	private JComponent getTabelaEquipamentoIndisponiveis() {
		JScrollPane panelTabela = new JScrollPane();
		modelIndisponiveis = new ContagemEstoqueEquipamentoTableModel();
		tabelaEquipamentosIndisponiveis = new JTable(modelIndisponiveis);
		tabelaEquipamentosIndisponiveis.setFillsViewportHeight(true);
		tabelaEquipamentosIndisponiveis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumn column = null;
		int columnWidth[] = modelIndisponiveis.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamentosIndisponiveis.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	
		panelTabela.setViewportView(tabelaEquipamentosIndisponiveis);
		return panelTabela;
	}
	
	
	
	
	public JPanel getPanelCodigoEquipamento() {
		JPanel panelCodigoEquipamento = new JPanel();
		panelCodigoEquipamento.setLayout(new GridBagLayout());
		botaoIniciar = new JButton("Iniciar Contagem");
		botaoIniciar.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Object grp = fieldGrupo.getSelectedItem();
						if(grp instanceof Grupo) {
							fieldEquipamentoID.setEnabled(true);
							fieldGrupo.setEnabled(false);
							fieldRecurso.setEnabled(false);
							botaoIniciar.setEnabled(false);
							fieldEquipamentoID.requestFocus();
							
						} else {
							JOptionPane.showMessageDialog(null, "Selecione um grupo para iniciar a contagem!", "ERRO!", JOptionPane.ERROR_MESSAGE );
						}
					}
				}
		);
		
		
		fieldEquipamentoID = new JBarCodeInputField();
		fieldEquipamentoID.setEnabled(false);
		
		fieldEquipamentoID.setListener(
				new BarCodeListener() {
					public void barCodeEntered(String code) {
						try {
							Equipamento eq = Facade.getInstance().localizarEquipamentoCodigo(code);
							if(!modelContagem.getEquipamentos().contains(eq)){
								if(modelDisponiveis.getEquipamentos().contains(eq)) {
									modelContagem.addElement(eq);
									modelDisponiveis.removeElement(eq);
								}
								if(modelDisponiveis.getEquipamentos().size() == 0) {
									JOptionPane.showMessageDialog(null, "Contagem finalizada com sucesso!");
									addButtonClicked();
								}
							}
						} catch (Exception e){
							java.awt.Toolkit.getDefaultToolkit().beep();  
						}
					}
				}
		);
		
		panelCodigoEquipamento.add(botaoIniciar, new RestricaoLayout(0,0,false,false));
		panelCodigoEquipamento.add(fieldEquipamentoID, new RestricaoLayout(0,1,1,true,false));
		return panelCodigoEquipamento;
	}
	
	public List<ContagemEstoque> listAll() {
		return Facade.getInstance().listarContagens();
	}

	public boolean save(ContagemEstoque current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarContagem(current);
			contagem = current;
			s = true;
		} catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	@Override
	public boolean print(ContagemEstoque current) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadInputFields(ContagemEstoque rec) {
		rec.setDataContagem(new Date());
		rec.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		rec.setEquipamentosFaltantes(modelDisponiveis.getEquipamentos());
		rec.setGrupo((Grupo) fieldGrupo.getSelectedItem());
		Object re = fieldRecurso.getSelectedItem();
		if(re instanceof Recurso)
			rec.setRecurso((Recurso) re);
	}
	
	private void limparForm() {
		fieldEquipamentoID.setEnabled(false);
		fieldGrupo.setEnabled(true);
		fieldRecurso.setEnabled(true);
		botaoIniciar.setEnabled(true);
		carregarComboGrupo();
		limparTabelas();
	}

	public void cancelButtonClicked() {
		if(JOptionPane.showConfirmDialog(null, "Iniciar uma nova contagem irá apagar os dados da contagem em andamento! Deseja Continuar ?") == JOptionPane.OK_OPTION){
			contagem = null;
			super.cancelButtonClicked();
		}
	}
	
	protected void clear() {
		limparForm();
	}

	protected void loadForm(ContagemEstoque t) {
		limparForm();
		fieldGrupo.setSelectedItem(t.getGrupo());
		fieldRecurso.setSelectedItem(t.getRecurso());
		limparTabelas();
		for(Equipamento eq : t.getEquipamentosFaltantes()) {
			modelDisponiveis.addElement(eq);
		}
		fieldEquipamentoID.setEnabled(false);
		fieldGrupo.setEnabled(false);
		fieldRecurso.setEnabled(false);
		botaoIniciar.setEnabled(false);
	}

	protected boolean validateFormInsert() {
		boolean valid = !fieldGrupo.isEnabled() && (contagem == null);
		if(!valid)
			JOptionPane.showMessageDialog(this, "A contagem não foi iniciada!", "ERRO", JOptionPane.ERROR_MESSAGE);
		return valid;
	}

	protected boolean validateFormUpdate() {
		return false;
	}

	public boolean update(ContagemEstoque current) {
		return false;
	}
	
	public boolean remove(ContagemEstoque current) {
		return false;
	}

	public ContagemEstoque newElement() {
		return new ContagemEstoque();
	}	

}
