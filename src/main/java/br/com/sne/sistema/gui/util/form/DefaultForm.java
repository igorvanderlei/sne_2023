package br.com.sne.sistema.gui.util.form;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.RowFilter.ComparisonType;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.ibm.icu.text.NumberFormat;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.ActionPanel;
import br.com.sne.sistema.gui.util.components.ActionPanelListener;
import br.com.sne.sistema.gui.util.components.FlashingTab;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.SizedTableModel;


//.PhysicalNamingStrategyStandardImpl;

//.ImplicitNamingStrategyLegacyJpaImpl;

public abstract class DefaultForm<T, TModel extends SizedTableModel<T>> extends JInternalFrame  implements ActionPanelListener{
	private static final long serialVersionUID = 1L;
	
    private TModel tableModel = null;	
    private TableRowSorter<TModel> sorter;
    
    private JPanel jContentPane = null;
	private JPanel jPanelDadosCadastrais = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
    private JPanel painelBotoesAcao = null;
	
	private JTabbedPane jTabbedPaneStatus = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelCadastro = null;
	private JPanel jPanelRelatorio = null;
	
	private JPanel panelFiltro = null;

	private JPanel jPanelContemPaneisFiltro = null;
	private JTextField jTextFielBusca = null;
	private JButton jButtonLimpaFiltro = null;
	private JComboBox jComboBoxFiltro = null;
	
	private ActionPanel painelComandos;
	
	public DefaultForm(TModel tableModel, String icon, String title) {
		super();
		this.tableModel = tableModel;
		this.setTitle(title);
		try {
			if(getClass().getResource(icon) != null)
				this.setFrameIcon(new ImageIcon(getClass().getResource(icon)));
			//setDesktopIcon(new JDesktopIcon(arg0));
		} catch(NullPointerException err) {}
		this.setSize(1100, 679);
		this.setContentPane(getJContentPane());
		createInputFields();
		carregarComboListar();
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				super.internalFrameClosing(e);
				dispose();
			}
			
		});
	}
	
	
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new CardLayout());
		//	jContentPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jContentPane.add(getJTabbedPane(), getJTabbedPane().getName());
		}
		return jContentPane;
	}

	private JPanel getJPanelDadosCadastrais() {
		if (jPanelDadosCadastrais == null) {
			jPanelDadosCadastrais = new JFormGroup("Dados Cadastrais");
			jPanelDadosCadastrais.setLayout(new GridBagLayout());
		}
		return jPanelDadosCadastrais;
	}
	
	public void addInputField(JComponent field, GridBagConstraints constraint) {
		jPanelDadosCadastrais.add(field, constraint);
	}
	
	public void addStatusTab(String title, JComponent tab){
		jTabbedPaneStatus.addTab(title, null, tab, null);
	}

	private JScrollPane getJScrollTabela() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setName("jScrollPane");
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
        
	public void selecionarLinha(T obj) {
		limparFiltro();
		carregarTabela();
		System.out.println("Selecionar Linha: " + obj);
		for(int i = 0; i < jTable.getRowCount(); i++) {
			if(obj.equals(tableModel.getValueAt(i, tableModel.getObjectIndex()))) {
				ListSelectionModel selectionModel = jTable.getSelectionModel();
				selectionModel.setSelectionInterval(i, i);
				break;
			} 
		}
	}

	protected JTable getJTable() {
		if (jTable == null) {
			sorter = new TableRowSorter<TModel>(tableModel);
	        jTable = new JTable(tableModel);
	        jTable.setRowSorter(sorter);
			jTable.setFillsViewportHeight(true);
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			ZebraDecorator zebra = new ZebraDecorator();
			for(int i=0; i < jTable.getColumnCount(); i++) {
				jTable.getColumnModel().getColumn(i).setCellRenderer(zebra);
			}
			
			TableColumn column = null;
			int columnWidth[] = tableModel.getColumnWidth();
			for (int i = 0; i < columnWidth.length; i++) {
			    column = jTable.getColumnModel().getColumn(i);
			    try {
			    	column.setPreferredWidth(columnWidth[i]);
			    } catch(RuntimeException errr){
			    	errr.printStackTrace();
			    }
			}			
			
			jTable.getSelectionModel().addListSelectionListener(
	                new ListSelectionListener() {
	                    public void valueChanged(ListSelectionEvent event) {
	                        int viewRow = jTable.getSelectedRow();

	                        if (viewRow < 0) {
	                        	clear();
	                        } else {
	                            @SuppressWarnings("unchecked")
								T selected = (T) jTable.getValueAt(viewRow,tableModel.getObjectIndex());
	                            if(selected != null) {
	                            	Facade.getInstance().beginTransaction();
	                            	loadForm((T) Facade.getInstance().reload(selected));
	                            	Facade.getInstance().commit();
	                            }
	                        }
	                    }
	                }
	        );
			
			jTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if (e.getClickCount() == 2){
						if(jTable.getSelectedRow() >=0)
						jTabbedPane.setSelectedIndex(0);
						
					}
				}
			} );
			
			jTable.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent arg0) {
							if(arg0.getButton() == MouseEvent.BUTTON3) {
									JPopupMenu popup = new JPopupMenu();
									JMenuItem menuCopiar = new JMenuItem("Copiar conteúdo");
									menuCopiar.setIcon(new ImageIcon(getClass().getResource("/images/copy.png")));
									menuCopiar.addActionListener(
											new ActionListener() {
												public void actionPerformed(ActionEvent arg0) {
													if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.COPIAR_CONTEUDO_TABELA)) {
														int numCol = jTable.getColumnCount();
														int numRow = jTable.getRowCount();
														StringBuilder buffer = new StringBuilder();
														
														
														for(int i=0; i<numCol;i++) {
															buffer.append(jTable.getModel().getColumnName(i));
															if (i<numCol-1)
																buffer.append("\t");
														}
														buffer.append(System.lineSeparator());

														for(int i=0; i<numRow;i++) {
															for(int j=0;j<numCol;j++) {
																if(jTable.getValueAt(i, j)!=null)
																{
																	buffer.append(jTable.getValueAt(i, j).toString().replaceAll("\\<.*?\\>", ""));
																}
																if (j<numCol-1)
																	buffer.append("\t");
															}
															buffer.append(System.lineSeparator());
														}
														StringSelection  stsel  = new StringSelection(buffer.toString());
														Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
														system.setContents(stsel,stsel);
													} else {
														JOptionPane.showMessageDialog(null, "O usuário logado não possui permissão para copiar o conteúdo da tabela.");

													}
												}
											}
									);
									popup.add(menuCopiar);
									popup.show(jTable, arg0.getX(), arg0.getY());
							} 						
						}
					}

			);
		}
		return jTable;
	}
	
     private void newFilter() {
        RowFilter<TModel, Object> rf2 = null;
        try {
        	
        	String campoFiltro = (String) jComboBoxFiltro.getSelectedItem();
        	
        	String col[] = tableModel.getFilterColumnName();
        	for(int i=0; i < col.length; i++) {

        		if(campoFiltro.equalsIgnoreCase(col[i])){
            		if(campoFiltro.toLowerCase().trim().startsWith("valor")) {
            			try{
            				NumberFormat formato = NumberFormat.getNumberInstance(); 
            				Number numero = formato.parse(jTextFielBusca.getText());
            				rf2 = RowFilter.numberFilter(ComparisonType.EQUAL, numero , i);
            			} catch (Exception excp) {}
            			break;
            		}
        			
        			rf2 = RowFilter.regexFilter("(?i)" + jTextFielBusca.getText(), i);
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
	
	
	protected void carregarTabela() {
		Facade.getInstance().beginTransaction();
		List<T> listElements = listAll();
		carregarTabela(listElements);
		Facade.getInstance().commit();
	}
	
	protected void carregarTabela(List<T> listElements) {
		//jTextFielBusca.setText("");
		
		@SuppressWarnings("unchecked")
		TModel modelo = (TModel) jTable.getModel();
		modelo.removeAllElements();
		if(listElements != null)
			for(T t : listElements) {
				modelo.addElement(t);
			}
		jTable.updateUI();
		System.out.println("Carregou");
	}
	
	private JPanel getPainelBotoesAcao() {
		if (painelBotoesAcao == null) {
			painelBotoesAcao = new JPanel();
			painelBotoesAcao.setLayout(new BoxLayout(getPainelBotoesAcao(), BoxLayout.X_AXIS));
			painelComandos = new ActionPanel(this);
			painelBotoesAcao.add(painelComandos, null);
		}
		return painelBotoesAcao;
	}

	private void carregarComboListar(){
		jComboBoxFiltro.removeAllItems();
		String [] col = tableModel.getFilterColumnName();
		jComboBoxFiltro.addItem(" ");
		for(String c: col) {
			if(!c.equals("X"))
				jComboBoxFiltro.addItem(c);
		}
	}

	protected JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new FlashingTab();
			jTabbedPane.setName("jTabbedPane");
			jTabbedPane.addTab("Cadastro",  new ImageIcon(getClass().getResource("/images/add_12.png")), getJPanelCadastro(), null);
			jTabbedPane.addTab("Listagem", new ImageIcon(getClass().getResource("/images/lista_12.png")), getPainelRelatorio(), null);
			jTabbedPane.setMnemonicAt(0, KeyEvent.VK_D);
			jTabbedPane.setMnemonicAt(1, KeyEvent.VK_L);
			
			jTabbedPane.addChangeListener(new ChangeListener() {
			    public void stateChanged(ChangeEvent evt) {
			        JTabbedPane pane = (JTabbedPane)evt.getSource();
			        int sel = pane.getSelectedIndex();
			        //limparFiltro();
			        if(sel == 1) {
			        	//carregarTabela();
			        } 
			    }
			});
		}
		return jTabbedPane;
	}

	private JPanel getJPanelCadastro() {
		if (jPanelCadastro == null) {

			GridBagConstraints gridBagConstraintsDados = new GridBagConstraints();
			gridBagConstraintsDados.insets = new Insets(0, 5, 0, 5);
			gridBagConstraintsDados.gridy = 0;
			gridBagConstraintsDados.gridx = 0;
			gridBagConstraintsDados.fill = GridBagConstraints.BOTH;
			gridBagConstraintsDados.weightx = 1;
			gridBagConstraintsDados.weighty = 1;

			GridBagConstraints gridBagConstraintsBotoes = new GridBagConstraints();
			gridBagConstraintsBotoes.insets = new Insets(0, 5, 5, 5);
			gridBagConstraintsBotoes.gridy = 3;
			gridBagConstraintsBotoes.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraintsBotoes.gridx = 0;

			jPanelCadastro = new JPanel();
			jPanelCadastro.setLayout(new GridBagLayout());
			jPanelCadastro.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
			//jPanelCadastro.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionText, 1));
			jPanelCadastro.add(getJPanelDadosCadastrais(), gridBagConstraintsDados);
			jPanelCadastro.add(getPainelBotoesAcao(), gridBagConstraintsBotoes);
		}
		return jPanelCadastro;
	}

	private JPanel getPainelRelatorio() {
		if (jPanelRelatorio == null) {
			
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
			jPanelRelatorio.add(getJScrollTabela(), gridBagConstraintsTabela);
			jPanelRelatorio.add(getJTabbedPaneStatus(), gridBagConstraints1Botoes);
		}
		return jPanelRelatorio;
	}

	private JTabbedPane getJTabbedPaneStatus() {
		if (jTabbedPaneStatus == null) {
			jTabbedPaneStatus = new JTabbedPane();
			jTabbedPaneStatus.setName("jTabbedPaneStatus");
			jTabbedPaneStatus.addTab("Filtro", null, getJPanelContemPaneisFiltro(), null);
			jTabbedPaneStatus.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if(jTabbedPaneStatus.getSelectedIndex() == 0){
						jTextFielBusca.setText("");
					}
				}
			});
		}
		return jTabbedPaneStatus;
	}



	private JPanel getJPanelContemPaneisFiltro() {
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
			jPanelContemPaneisFiltro.add(getPanelFiltro(), new RestricaoLayout(0, 0, 3, 1, true, false));
			jPanelContemPaneisFiltro.add(getJComboBoxFiltro(), gridBagConstraints17);
			jPanelContemPaneisFiltro.add(getJTextFielBusca(), gridBagConstraints16);
			jPanelContemPaneisFiltro.add(getJButtonLimpaFiltro(), gridBagConstraints18);
			
		}
		return jPanelContemPaneisFiltro;
	}

	protected JPanel getPanelFiltro() {
		if(panelFiltro == null) {
			panelFiltro = new JPanel();
		} 
		return panelFiltro;
		
	}
	
	protected JTextField getJTextFielBusca() {
		if (jTextFielBusca == null) {
			jTextFielBusca = new JTextField();
			jTextFielBusca.setName("jTextFielBusca");

		     jTextFielBusca.getDocument().addDocumentListener(new DocumentListener(){
		    	 
		    	 public void changedUpdate(DocumentEvent e) {
		    		 newFilter();
				}
		    	 
		    	 public void insertUpdate(DocumentEvent e){
		    		 newFilter();
		    	 }
		    	 
		    	 public void removeUpdate(DocumentEvent e){
		    		 newFilter();
		    	 }
		     
		     });
		    	 
		}
		return jTextFielBusca;
	}

	private JButton getJButtonLimpaFiltro() {
		if (jButtonLimpaFiltro == null) {
			jButtonLimpaFiltro = new JButton();
			jButtonLimpaFiltro.setName("jButtonLimpaFiltro");
		//	jButtonLimpaFiltro.setIcon(new ImageIcon("images/Kde_crystalsvg_eraser32.png"));
			jButtonLimpaFiltro.setText("Listar");
			jButtonLimpaFiltro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					limparFiltro();
				}
			});
		}
		return jButtonLimpaFiltro;
	}
	
	protected void limparFiltro() {
		carregarTabela();
		//jTextFielBusca.setText("");
		//jComboBoxFiltro.setSelectedIndex(0);		
	}

	protected JComboBox getJComboBoxFiltro() {
		if (jComboBoxFiltro == null) {
			jComboBoxFiltro = new JComboBox();
			jComboBoxFiltro.setName("jComboBox");
		}
		return jComboBoxFiltro;
	}

	
	public void addButtonClicked() {
		if(validateFormInsert()) {
			T current = newElement();
			loadInputFields(current);
			Facade.getInstance().beginTransaction();
			if(save(current)) {
				JOptionPane.showMessageDialog(null,"Registro cadastrado com sucesso!");
				clear();
			}
			Facade.getInstance().commit();
		}
	}
	
	public void deleteButtonClicked() {
		int linha = jTable.getSelectedRow();
		if(linha >= 0 ) {
			@SuppressWarnings("unchecked")
			T selected = (T) jTable.getValueAt(linha,tableModel.getObjectIndex());
			int resp = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir o registro Selecionado","Escolha uma opção" ,JOptionPane.OK_CANCEL_OPTION);
			if(resp == JOptionPane.OK_OPTION){
				Facade.getInstance().beginTransaction();
				if(remove(selected) ) {
					JOptionPane.showMessageDialog(null,"Registro removido com sucesso!");
					clear();
				}
				Facade.getInstance().commit();
			}
		
		     } else {
			      JOptionPane.showMessageDialog(null, "Selecione um Registro");
		     }		
	}

	public void updateButtonClicked() {
		int linha = jTable.getSelectedRow();
		if(linha >= 0 ) {
			@SuppressWarnings("unchecked")
			T current = (T) jTable.getValueAt(linha, tableModel.getObjectIndex());
			if(validateFormUpdate()){
				loadInputFields(current);
				int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Realizar Alteracoes no Registro Selecionado","Escolha uma opção" ,JOptionPane.OK_CANCEL_OPTION);
				if(resp == JOptionPane.OK_OPTION)    {
					Facade.getInstance().beginTransaction();
					if(update(current)) {
						JOptionPane.showMessageDialog(null,"Registro atualizado com sucesso!");
						clear();
						//jTable.clearSelection();
						TModel modelo = (TModel) jTable.getModel();
						modelo.removeAllElements();
					}
					Facade.getInstance().commit();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione um Registro");
		}		
	}
	

	public void cancelButtonClicked() {
		clear();
		jTable.clearSelection();
		setVisible(false);
		setVisible(true);
	}
	
	public void printButtonClicked() {
		int linha = jTable.getSelectedRow();
		T current = null;
		if(linha >= 0 ) {
			current = (T) jTable.getValueAt(linha, tableModel.getObjectIndex());
		}
		//loadInputFields(current);
		if(print(current)) {
		//	clear();
		//	JOptionPane.showMessageDialog(null,"Registro cadastrado com sucesso!");
			//setVisible(false);
			//setVisible(true);	
		}
	}
	
	
	public void exitButtonClicked() {
		setVisible(false);
	}
	
	public void desabilitarBotaoRemover() {
		painelComandos.getDeleteButton().setEnabled(false);
	}

	public void habilitarBotaoRemover() {
		painelComandos.getDeleteButton().setEnabled(true);
	}
	
	public void desabilitarBotaoAtualizar() {
		painelComandos.getUpdateButton().setEnabled(false);
	}

	public void habilitarBotaoAtualizar() {
		painelComandos.getUpdateButton().setEnabled(true);
	}
	
	public void desabilitarBotaoAdicionar() {
		painelComandos.getAddButton().setEnabled(false);
	}

	public void habilitarBotaoAdicionar() {
		painelComandos.getAddButton().setEnabled(true);
	}
	
	public void desabilitarBotaoNovo() {
		painelComandos.getCancelButton().setEnabled(false);
	}

	public void habilitarBotaoNovo() {
		painelComandos.getCancelButton().setEnabled(true);
	}
	
	public void desabilitarBotaoImprimir() {
		painelComandos.getPrintButton().setEnabled(false);
	}
	
	public void habilitarBotaoImprimir() {
		painelComandos.getPrintButton().setEnabled(true);
	}
	
	public abstract List<T> listAll();
	public abstract boolean save(T current);
	public abstract boolean update(T current);
	public abstract boolean remove(T current);
	public abstract boolean print(T current);
	public abstract void createInputFields();
	public abstract T newElement();
	public abstract void loadInputFields(T t);
	protected abstract void clear();	
	protected abstract void loadForm(T rec);
	protected abstract boolean validateFormInsert();
	protected abstract boolean validateFormUpdate();
} 
