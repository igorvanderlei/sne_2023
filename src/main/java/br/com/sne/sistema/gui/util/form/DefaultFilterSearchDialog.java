package br.com.sne.sistema.gui.util.form;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.SizedTableModel;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public abstract class DefaultFilterSearchDialog<T, TModel extends SizedTableModel<T>> extends JDialog {
	private static final long serialVersionUID = 1L;
    private T value = null;
    private List<T> data = null;
    private TModel tableModel; 
    private TableRowSorter<TModel> sorter;
    
    private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable tabela = null;
	
	private JTabbedPane jTabbedPaneStatus = null;
	private JPanel jPanelRelatorio = null;

	private JPanel jPanelContemPaneisFiltro = null;
	private JComponent jPanelBusca = null;
	private JTextField jTextFielBusca = null;
	private JButton jButtonLimpaFiltro = null;
	private JComboBox<String> jComboBoxFiltro = null;
    
    public DefaultFilterSearchDialog(Component comp, String title, TModel tableModel) {
    	super();
    	setTitle(title);
        setSize(920, 400);   
        
        
        setIconImage((new ImageIcon(getClass().getResource("/images/find_18.png"))).getImage());
        
    	setModalityType(DEFAULT_MODALITY_TYPE);
    	setLocationRelativeTo(null);
    	this.tableModel = tableModel;
        Container contentPane = getContentPane();
        contentPane.add(getJContentPane(), BorderLayout.CENTER);
    	carregarComboListar();
    }

    public T showDialog(Component comp) {
        
        setVisible(true);
        value = init(value);
        return value;
    }
    
    public abstract T init(T value);
    
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
		if (tabela == null) {
			sorter = new TableRowSorter<TModel>(tableModel);
	        tabela = new JTable(tableModel);
	        tabela.setRowSorter(sorter);
			tabela.setFillsViewportHeight(true);
			tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			TableColumn column = null;
			int columnWidth[] = tableModel.getColumnWidth();
			for (int i = 0; i < columnWidth.length; i++) {
			    column = tabela.getColumnModel().getColumn(i);
		        column.setPreferredWidth(columnWidth[i]/2);
			}			
			
			tabela.getSelectionModel().addListSelectionListener(
	                new ListSelectionListener() {
	                    @SuppressWarnings("unchecked")
						public void valueChanged(ListSelectionEvent event) {
	                        int viewRow = tabela.getSelectedRow();
	                        if (viewRow >= 0) {
								value = (T) tabela.getValueAt(viewRow,tableModel.getObjectIndex());
	                        }
	                    }
	                }
	        );
			
			tabela.addMouseListener(new MouseAdapter() {
				@SuppressWarnings("unchecked")
				public void mouseClicked(MouseEvent e){
					if (e.getClickCount() == 2){
						int viewRow = tabela.getSelectedRow();
						if(viewRow >= 0) {
							value = (T) tabela.getValueAt(viewRow,tableModel.getObjectIndex());
							setVisible(false);
						}
					}
				}
			} );
		}
		return tabela;
	}
	
     private void newFilter() {
        RowFilter<TModel, Object> rf2 = null;
        try {
        	
        	String recText = (String) jComboBoxFiltro.getSelectedItem();
        	
        	String col[] = tableModel.getFilterColumnName();
        	for(int i=0; i < col.length; i++) {
        		if(recText.equalsIgnoreCase(col[i])==true){
        			rf2 = RowFilter.regexFilter("(?i)" + jTextFielBusca.getText(), i);
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
	
	
	@SuppressWarnings("unchecked")
	protected void carregarTabela() {
		jTextFielBusca.setText("");
		TModel modelo = (TModel) tabela.getModel();
		modelo.removeAllElements();
		for(T t : data) {
			modelo.addElement(t);
		}
		tabela.updateUI();
	}
	
	private void carregarComboListar(){
		jComboBoxFiltro.removeAllItems();
		String [] col = tableModel.getFilterColumnName();
		jComboBoxFiltro.addItem(" ");
		for(String c: col) {
			if(!c.equals("X")) {
				jComboBoxFiltro.addItem(c);
			}
		}
	}

	private JPanel getPainelRelatorio() {
		if (jPanelRelatorio == null) {
			jPanelRelatorio = new JPanel();
			jPanelRelatorio.setLayout(new GridBagLayout());
			jPanelRelatorio.add(getJScrollTabela(), new RestricaoLayout(0,0, 1,1, true, true));
			jPanelRelatorio.add(getJTabbedPaneStatus(), new RestricaoLayout(1,0,1,true, false));
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
			gridBagConstraints17.ipadx = 339;
			gridBagConstraints17.ipady = 12;
			gridBagConstraints17.weightx = 0.2;
			gridBagConstraints17.insets = new Insets(0, 3, 6, 3);
			
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.ipadx = 444;
			gridBagConstraints16.weightx = 0.2;
			gridBagConstraints16.insets = new Insets(0, 3, 6, 1);
			
			jPanelContemPaneisFiltro = new JPanel();
			jPanelContemPaneisFiltro.setLayout(new GridBagLayout());
			jPanelContemPaneisFiltro.add(getJPanelBusca(), gridBagConstraints16);
			jPanelContemPaneisFiltro.add(new TitledPanel("Selecione a Opção de Filtro",  getJComboBoxFiltro()), gridBagConstraints17);
		}
		return jPanelContemPaneisFiltro;
	}

	private JComponent getJPanelBusca() {
		if (jPanelBusca == null) {
			JPanel miolo = new JPanel();
			miolo.setLayout(new BoxLayout(miolo, BoxLayout.X_AXIS));
			miolo.add(getJTextFielBusca(), null);
			miolo.add(getJButtonLimpaFiltro(), null);
			jPanelBusca = new TitledPanel("Filtro", miolo);
		}
		return jPanelBusca;
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
			jButtonLimpaFiltro.setIcon(new ImageIcon(getClass().getResource("/images/find.png")));//images/Kde_crystalsvg_eraser32.png"));
			jButtonLimpaFiltro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//jTextFielBusca.setText("");
					//jComboBoxFiltro.setSelectedIndex(0);
					Facade.getInstance().beginTransaction();
					data = listAll();
					carregarTabela();
					Facade.getInstance().commit();
				}
			});
		}
		return jButtonLimpaFiltro;
	}
	
	protected abstract  List<T> listAll();

	protected JComboBox<String> getJComboBoxFiltro() {
		if (jComboBoxFiltro == null) {
			jComboBoxFiltro = new JComboBox<String>();
			jComboBoxFiltro.setName("jComboBox");
		}
		return jComboBoxFiltro;
	}
    
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new CardLayout());
			jContentPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jContentPane.add(getPainelRelatorio(), "Listagem");
		}
		return jContentPane;
	}

    protected JRootPane createRootPane() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        JRootPane rootPane = new JRootPane();
        rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        return rootPane;
    }
    
}