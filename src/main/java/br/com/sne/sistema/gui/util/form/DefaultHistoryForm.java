package br.com.sne.sistema.gui.util.form;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import br.com.sne.sistema.gui.util.components.SizedTableModel;

public abstract class DefaultHistoryForm<T, TModel extends SizedTableModel<T>> extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
    private TModel tableModel = null;	
    private TableRowSorter<TModel> sorter;
    
    private JPanel jContentPane = null;
	private JPanel jPanelDadosCadastrais = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	
	private JTabbedPane jTabbedPaneStatus = null;
	private JPanel jPanelRelatorio = null;

	private JPanel jPanelContemPaneisFiltro = null;
	private JTextField jTextFielBusca = null;
	private JButton jButtonLimpaFiltro = null;
	private JComboBox jComboBoxFiltro = null;
	
	public DefaultHistoryForm(TModel tableModel, String icon, String title) {
		super();
		this.tableModel = tableModel;
		this.setTitle(title);
		try {
			this.setFrameIcon(new ImageIcon(getClass().getResource(icon)));
		} catch(NullPointerException err) {}
		this.setSize(1100, 679);
		this.setContentPane(getJContentPane());
		carregarComboListar();
		carregarTabela();
	}
	
	
	
	public void setVisible(boolean arg0) {
		if(jTable != null && arg0)
			carregarTabela();
		super.setVisible(arg0);
	}



	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new CardLayout());
			jContentPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jContentPane.add(getPainelRelatorio(), "relatorio");
		}
		return jContentPane;
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
        

	private JTable getJTable() {
		if (jTable == null) {
			sorter = new TableRowSorter<TModel>(tableModel);
	        jTable = new JTable(tableModel);
	        jTable.setRowSorter(sorter);
			jTable.setFillsViewportHeight(true);
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			TableColumn column = null;
			int columnWidth[] = tableModel.getColumnWidth();
			for (int i = 0; i < columnWidth.length; i++) {
			    column = jTable.getColumnModel().getColumn(i);
		        column.setPreferredWidth(columnWidth[i]);
			}			
			
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
        			rf2 = RowFilter.regexFilter("(?i)" + jTextFielBusca.getText(), i);
        			break;
        		}
        	}
        	
        	if(campoFiltro.equalsIgnoreCase(" ")){
				JOptionPane.showMessageDialog(null," Selecione uma das opcoes para realizacao o filtro ");
				return;
			}
    
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf2);
	}
	
	protected void carregarTabela() {
		List<T> listElements = listAll();
		jTextFielBusca.setText("");
	
		@SuppressWarnings("unchecked")
		TModel modelo = (TModel) jTable.getModel();
		modelo.removeAllElements();
		for(T t : listElements) {
			modelo.addElement(t);
		}
		jTable.updateUI();
		System.out.println("Carregou");
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
			jPanelContemPaneisFiltro.add(getJComboBoxFiltro(), gridBagConstraints17);
			jPanelContemPaneisFiltro.add(getJTextFielBusca(), gridBagConstraints16);
			jPanelContemPaneisFiltro.add(getJButtonLimpaFiltro(), gridBagConstraints18);
			
		}
		return jPanelContemPaneisFiltro;
	}

	private JTextField getJTextFielBusca() {
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
			jButtonLimpaFiltro.setText("Limpar Filtro");
			jButtonLimpaFiltro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					limparFiltro();
				}
			});
		}
		return jButtonLimpaFiltro;
	}
	
	private void limparFiltro() {
		jTextFielBusca.setText("");
		jComboBoxFiltro.setSelectedIndex(0);		
	}

	private JComboBox getJComboBoxFiltro() {
		if (jComboBoxFiltro == null) {
			jComboBoxFiltro = new JComboBox();
			jComboBoxFiltro.setName("jComboBox");
		}
		return jComboBoxFiltro;
	}
	
	
	public abstract List<T> listAll();
	
} 