package br.com.sne.sistema.gui.agendarrecolhimento;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.OsDePassagem;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.manutencaoCorretiva.ManutencaoCorretivaTableModel;
import br.com.sne.sistema.gui.os.OrdemServicoEmergencialTableModel;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DialogSearchOS;
import br.com.sne.sistema.gui.util.form.DialogSearchOS2;

public class FormAgendarRecolhimento extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTable tabelaOS;
	private OrdemServicoEmergencialTableModel modeloTabela;
	private JComboBox fieldFuncionarioResponsavel;
	
	private JButton botaoAgendarRecolhimento;
	
	private JPopupMenu menuTabela;

	
	public FormAgendarRecolhimento() {
		setTitle("Agendar Recolhimento de Equipamentos");
		setFrameIcon(new ImageIcon(getClass().getResource("/images/icon_agendar_18.png")));
		createInputFields();
	}
	
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		if(fieldFuncionarioResponsavel != null)
			carregarComboFuncionario();
	}
	
	public void carregarComboFuncionario() {
		fieldFuncionarioResponsavel.removeAllItems();
		fieldFuncionarioResponsavel.addItem("Selecione o Responsável");
		for(Funcionario f: Facade.getInstance().listarFuncionarios()) {
			fieldFuncionarioResponsavel.addItem(f);
		}
	}
	
	public void createInputFields() {
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new GridBagLayout());
		
		fieldFuncionarioResponsavel = new JComboBox();
		carregarComboFuncionario();
		
		modeloTabela = new OrdemServicoEmergencialTableModel();
		tabelaOS = new JTable(modeloTabela);
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(tabelaOS);
		
		tabelaOS.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaOS.getSelectedRow() >= 0 )
								menuTabela.show(tabelaOS, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione uma Ordem de Serviço para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		
		conteudo.add(new TitledPanel("Responsável Recolhimento", fieldFuncionarioResponsavel), new RestricaoLayout(0,0,3,true,false));
		conteudo.add(new TitledPanel("Ordem de Serviço a Recolher", getBotaoProcurarOS()), new RestricaoLayout(1,0,3,true,false));
		conteudo.add(new TitledPanel("Ordens de Serviço Selecionadas", new JScrollPane(tabelaOS)), new RestricaoLayout(2,0,3,1,true,true));
		conteudo.add(new JPanel(), new RestricaoLayout(3,0,1,true,false));
		conteudo.add(getBotaoAgendarRecolhimento(), new RestricaoLayout(3,1,1,true,false));
		conteudo.add(new JPanel(), new RestricaoLayout(3,2,1,true,false));
		this.getContentPane().add(conteudo, BorderLayout.CENTER);
	}
	
	
	public JButton getBotaoProcurarOS() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));
		
		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						List<OrdemServico> listaOS;
						listaOS = Facade.getInstance().listarOrdemEmAndamento();
						listaOS.removeAll(modeloTabela.getListaOs());
						DialogSearchOS2 teste = new DialogSearchOS2(FormAgendarRecolhimento.this, listaOS);
						OrdemServico o = teste.showDialog(FormAgendarRecolhimento.this);
						if(o != null) 
							carregarOS(o);
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Ordem de Serviço");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modeloTabela.removeRow(tabelaOS.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}
	
	private void carregarOS(OrdemServico o) {
		modeloTabela.addElement(o);
	}
	
	public boolean print(OsDePassagem current) {
		return false;
	}
	
	public JButton getBotaoAgendarRecolhimento() {
		botaoAgendarRecolhimento = new JButton("Agendar Recolhimento");
		botaoAgendarRecolhimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(fieldFuncionarioResponsavel.getSelectedItem() instanceof Funcionario)) {
					JOptionPane.showMessageDialog(null, "Selecione o funcionário responsável pelo recolhimento");
					return;
				}
				if(modeloTabela.getListaOs().size() <= 0) {
					JOptionPane.showMessageDialog(null, "Selecione as Ordens de Serviço");
					return;
				}
				
				List<Long> listaId = new ArrayList<Long>();
				Funcionario f = (Funcionario) fieldFuncionarioResponsavel.getSelectedItem();
				for(OrdemServico ord : modeloTabela.getListaOs()){
					listaId.add(ord.getId());
					for(EquipamentoEnviado eqp: ord.getEquipamentoEnviado()) {
						if(!eqp.isStatus()) {
							eqp.setFuncionarioRecolhimento(f);
							Facade.getInstance().atualizarEquipamentoEnviado(eqp);
							//listaId.add(eqp.getEquipamento().getNumeroSerie());
						}
					}
				}

				JOptionPane.showMessageDialog(null,"Recolhimento agendado com sucesso!");		
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
				hm.put("listaEquipamentos", listaId);
				hm.put("responsavelEstoque", Facade.getInstance().getUsuarioLogado().getFuncionario().getNome());
				hm.put("responsavelMotorista", ((Funcionario)fieldFuncionarioResponsavel.getSelectedItem()).getNome());

				try {
					Connection c  = Facade.getInstance().getConnection() ;
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/agendaRecolhimento.jasper");  
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
		});
		
		return botaoAgendarRecolhimento;
	}

	
}
