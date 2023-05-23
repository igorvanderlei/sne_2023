package br.com.sne.sistema.gui.devolucaoequipamento;

import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.ManutencaoCorretiva;
import br.com.sne.sistema.bean.ManutencaoPreventiva;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.equipamentoenviado.OSEquipamentoEnviadoTableModel;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;

public class FormDevolverEquipamento extends JInternalFrame {
	
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
	
	
	public FormDevolverEquipamento() {
		super("Retorno de Equipamento");
		setFrameIcon(new ImageIcon(getClass().getResource("/images/icon_devolucao_18.png")));
		initialize();
	}
	
	public void setVisible(boolean arg0) {
		if(arg0) {
			modelEquipamento.removeAllElements();
			carregarOSPendente();
		}
		super.setVisible(arg0);
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
		
		carregarOSPendente();
		
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new GridBagLayout());
		conteudo.add(getPanelOs(), new RestricaoLayout(0,0,2,true,false));
		conteudo.add(new TitledPanel("Equipamentos Devolvidos", scrollTabelaEquipamentos), new RestricaoLayout(1,0,2,1,true,true));
		conteudo.add(new TitledPanel("OS Pendentes", scrollTabelaOSPendente), new RestricaoLayout(2,0,1,1,true,true));
		conteudo.add(new TitledPanel("Equipamentos Pendentes", scrollTabelaEquipamentoPendente), new RestricaoLayout(2,1,1,1,true,true));
		
		this.setContentPane(conteudo);
	}
	
	private void carregarOSPendente () {
		List<OrdemServico> lista = Facade.getInstance().listarOrdemEmAndamento();
		modelOS.removeAllElements();
		modelEquipamentoPendente.removeAllElements();
		for(OrdemServico os : lista)
			modelOS.addElement(os);
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
							EquipamentoEnviado eq = Facade.getInstance().localizarEquipamentoEnviado(code);
							if(eq != null) {
								modelEquipamento.addElement(eq.getEquipamento());
								
								eq.setDataDevolucao(new Date());
								eq.setStatus(true);
								eq.setFuncionarioDevolucao(Facade.getInstance().getUsuarioLogado().getFuncionario());
								eq.getEquipamento().setStatus(StatusEquipamento.DISPONIVEL);
								
								Facade.getInstance().atualizarEquipamentoDevolucao(eq.getEquipamento());
								Facade.getInstance().atualizarEquipamentoEnviado(eq);
								os = Facade.getInstance().localizaOrdemServicosPorEquipamento(eq.getId());
								boolean finalizada = true;
								for(EquipamentoEnviado e: os.getEquipamentoEnviado()) {
									if(!e.isStatus()) {
										finalizada = false;
										break;
									}
								}
								if(finalizada){
									System.out.println("Status: " + os.getStatus());
									if(os.getStatus() == StatusOS.OS_EMERGENCIAL_INICIADA || 
										os.getStatus() == StatusOS.OS_EMERGENCIAL ||
										os.getStatus() == StatusOS.OS_EMERGENCIAL_CONCLUIDA )
										
										os.setStatus(StatusOS.OS_EMERGENCIAL_CONCLUIDA);
									else
										os.setStatus(StatusOS.CONCLUIDA);
									Facade.getInstance().atualizarOrdemServico(os);
								}
								carregarOSPendente();
							}
							else {
								Equipamento eqp = Facade.getInstance().localizarEquipamentoCodigo(code);
								if(eqp != null) {
									switch(eqp.getStatus()) {
										case MANUTENCAO_PREVENTIVA:
											ManutencaoPreventiva manut = Facade.getInstance().localizarManutencaoPreventivaPorEquipamento(eqp);
											manut.setDataDevolucao(new Date());
											manut.setStatus(true);
											eqp.setStatus(StatusEquipamento.DISPONIVEL);
											modelEquipamento.addElement(eqp);
											Facade.getInstance().atualizarEquipamentoDevolucao(eqp);
											Facade.getInstance().atualizarManutencaoPreventiva(manut);

											break;
										case MANUTENCAO_CORRETIVA:
											ManutencaoCorretiva manuc = Facade.getInstance().localizarManutencaoCorretivaPorEquipamento(eqp);
											manuc.setDataDevolucao(new Date());
											manuc.setStatus(true);
											modelEquipamento.addElement(eqp);
											eqp.setStatus(StatusEquipamento.DISPONIVEL);
											Facade.getInstance().atualizarEquipamentoDevolucao(eqp);
											Facade.getInstance().atualizarManutencaoCorretiva(manuc);
											break;
										case LOCADO:
											eqp.setStatus(StatusEquipamento.DISPONIVEL);
											Facade.getInstance().atualizarEquipamentoDevolucao(eqp);
											modelEquipamento.addElement(eqp);
											break;
											
										case DISPONIVEL:
											JOptionPane.showMessageDialog(null, "O equipamento já est� no estoque!", "ERRO!", JOptionPane.ERROR_MESSAGE );
											break;
									}
									
								} else {
									JOptionPane.showMessageDialog(null, "Equipamento não localizado!", "ERRO!", JOptionPane.ERROR_MESSAGE );
								}
								
							}
							fieldCodigoEquipamento.setText("");
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
