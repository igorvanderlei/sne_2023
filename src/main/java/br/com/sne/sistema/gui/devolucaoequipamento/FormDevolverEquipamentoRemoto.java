package br.com.sne.sistema.gui.devolucaoequipamento;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.ManutencaoCorretiva;
import br.com.sne.sistema.bean.ManutencaoPreventiva;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Recolhimento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.gui.equipamentoenviado.OSEquipamentoEnviadoTableModel;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;

public class FormDevolverEquipamentoRemoto extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JBarCodeInputField fieldCodigoEquipamento;
	
	private JTable tabelaEquipamento;
	private EquipamentoDevolvidoTableModel modelEquipamento;
	private OrdemServico os;
	
	private JTable tabelaOSPendente;
	private OrdemServicoTableModel modelOS;
	
	private JTable tabelaEquipamentoPendente;
	private OSEquipamentoEnviadoTableModel modelEquipamentoPendente;
	private TableRowSorter<OrdemServicoTableModel> sorter;
	
	private Recolhimento recolhimento;
	private File arquivo;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UIManager.put("swing.boldMetal", false);
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {} 

				FormDevolverEquipamentoRemoto frame = new FormDevolverEquipamentoRemoto();
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				frame.setVisible(true);

			}
		});
	}


	public FormDevolverEquipamentoRemoto() {
		super("Retorno de Equipamento");
		this.setIconImage(new ImageIcon("images/retornar_equipamento_20.png").getImage());
		initialize();
		this.setSize(800,600);
		this.setExtendedState(6);
	}
	
	
	private void initialize() {
		JScrollPane scrollTabelaEquipamentos = new JScrollPane();
		modelEquipamento = new EquipamentoDevolvidoTableModel();
		tabelaEquipamento = new JTable(modelEquipamento);
		
		
		scrollTabelaEquipamentos.setViewportView(tabelaEquipamento);
		
		JScrollPane scrollTabelaOSPendente = new JScrollPane();
		
		modelOS = new OrdemServicoTableModel();
		sorter = new TableRowSorter<OrdemServicoTableModel>(modelOS);
		tabelaOSPendente = new JTable(modelOS);
		tabelaOSPendente.setRowSorter(sorter);
		
		TableColumn column = null;
		int columnWidth[] = modelOS.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
		    column = tabelaOSPendente.getColumnModel().getColumn(i);
	        column.setPreferredWidth(columnWidth[i]);
		}	
		
		
		scrollTabelaOSPendente.setViewportView(tabelaOSPendente);
		
		
		
		tabelaOSPendente.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = tabelaOSPendente.getSelectedRow();

						if (viewRow < 0) {
							//os = null;
						} else {
							OrdemServico selected = (OrdemServico) tabelaOSPendente.getValueAt(viewRow,modelOS.getObjectIndex());
							os = selected;
							carregarTabelaEquipamentosPendentes(selected);
							fieldCodigoEquipamento.requestFocus();
						}
					}
				}
		);
		
		JScrollPane scrollTabelaEquipamentoPendente = new JScrollPane();
		modelEquipamentoPendente = new OSEquipamentoEnviadoTableModel();
		tabelaEquipamentoPendente = new JTable(modelEquipamentoPendente);
		scrollTabelaEquipamentoPendente.setViewportView(tabelaEquipamentoPendente);
		
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new GridBagLayout());
		conteudo.add(getPanelOs(), new RestricaoLayout(0,0,2,true,false));
		conteudo.add(new TitledPanel("Equipamentos Devolvidos", scrollTabelaEquipamentos), new RestricaoLayout(1,0,2,1,true,true));
		conteudo.add(new TitledPanel("OS Pendentes", scrollTabelaOSPendente), new RestricaoLayout(2,0,1,1,true,true));
		conteudo.add(new TitledPanel("Equipamentos Pendentes", scrollTabelaEquipamentoPendente), new RestricaoLayout(2,1,1,1,true,true));
		
		this.setContentPane(conteudo);
		
		this.setJMenuBar(getMenuBarr());
	}
	
	public JMenuBar getMenuBarr() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuArquivo = new JMenu("Arquivo");
		JMenuItem menuItemAbrir = new JMenuItem("Abrir ...");
		JMenuItem menuItemSalvar = new JMenuItem("Salvar");
		
		menuItemAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(FormDevolverEquipamentoRemoto.this);
				arquivo = fileChooser.getSelectedFile();

				if(arquivo != null) {
					try {
						ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo));
						recolhimento = (Recolhimento) entrada.readObject();
						entrada.close();
						carregarOSPendente(recolhimento.getOrdemServico());
						carregarEquipamentosBipados(recolhimento.getEquipamentosBipados());

					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(FormDevolverEquipamentoRemoto.this, "Erro!  Arquivo corrompido ou incorreto.", "ERRO", JOptionPane.ERROR_MESSAGE);	
						e.printStackTrace();
					}
				}
			}
		});
		
		menuItemSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arquivo != null) {
					try {
						ObjectOutputStream entrada = new ObjectOutputStream(new FileOutputStream(arquivo));
						entrada.writeObject(recolhimento);
						entrada.close();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				} else {
					JOptionPane.showMessageDialog(FormDevolverEquipamentoRemoto.this, "Erro!  Nenhum arquivo selecionado.", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		menuArquivo.add(menuItemAbrir);
		menuArquivo.add(menuItemSalvar);
		menuBar.add(menuArquivo);
		
		return menuBar;
	}
	
	private void carregarOSPendente (List<OrdemServico> lista) {
		modelOS.removeAllElements();
		modelEquipamentoPendente.removeAllElements();
		for(OrdemServico os : lista) {
			if(os.getStatus() != StatusOS.CONCLUIDA)
				modelOS.addElement(os);
		}
	}
	
	private void carregarEquipamentosBipados(List<EquipamentoEnviado> lista) {
		modelEquipamento.removeAllElements();
		for(EquipamentoEnviado eqv : lista)
			modelEquipamento.addElement(eqv.getEquipamento());
	}
	
	private void carregarTabelaEquipamentosPendentes(OrdemServico selected) {
		modelEquipamentoPendente.removeAllElements();
		for(EquipamentoEnviado eqv: selected.getEquipamentoEnviado()) {
			if(!eqv.isStatus()) {
				modelEquipamentoPendente.addElement(eqv.getEquipamento());
			}
		}
		
	}
	
	private JPanel getPanelOs() {
		JPanel panelOS = new JPanel();
		panelOS.setLayout(new GridBagLayout());
		fieldCodigoEquipamento = new JBarCodeInputField();
		fieldCodigoEquipamento.setListener(
				new BarCodeListener() {
					public void barCodeEntered(String code) {
						try {
							boolean achou = false;
							System.out.println("code:: " + code);
							for(OrdemServico os: recolhimento.getOrdemServico()){
								for(EquipamentoEnviado eqv : os.getEquipamentoEnviado()){
									if(eqv.getEquipamento().getNumeroSerie().equals(code)) {
										//Mudei aqui para evitar equipamentos duplicados.
										if(!modelEquipamento.contains(eqv.getEquipamento())) {
											modelEquipamento.addElement(eqv.getEquipamento());
											recolhimento.getEquipamentosBipados().add(eqv);
											eqv.setStatus(true);
											achou = true;
										}
										break;
									}
									
								}
								if(achou) {
									boolean finalizada = true;
									for(EquipamentoEnviado e: os.getEquipamentoEnviado()) {
										if(!e.isStatus()) {
											finalizada = false;
											break;
										}
									}
									if(finalizada){
										os.setStatus(StatusOS.CONCLUIDA);
									}
									carregarOSPendente(recolhimento.getOrdemServico());
									break;
								}
							}
						
						} catch (Exception e){
							e.printStackTrace();
							java.awt.Toolkit.getDefaultToolkit().beep();  
						}
					}
				}
		);
		
		
		fieldCodigoEquipamento.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					
				}
			}
		});
		
		panelOS.add(new TitledPanel("Código do Equipamento", fieldCodigoEquipamento), new RestricaoLayout(0, 0, 2, true, false));
		return panelOS;
	}
}
