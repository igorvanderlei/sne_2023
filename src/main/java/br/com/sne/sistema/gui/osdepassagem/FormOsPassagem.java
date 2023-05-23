package br.com.sne.sistema.gui.osdepassagem;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

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
import br.com.sne.sistema.gui.equipamentoenviado.OSEquipamentoEnviadoTableModel;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchEquipamento;
import br.com.sne.sistema.gui.util.form.DialogSearchOS;
import br.com.sne.sistema.gui.util.form.DialogSearchOS2;

public class FormOsPassagem extends DefaultForm<OsDePassagem, OsPassagemTableModel> {
	private static final long serialVersionUID = 1L;
	private JTextField fieldEventoNomeOrigem;
	private JTextField fieldEventoLocalOrigem;
	private JTextField fieldEventoInicioOrigem;
	private JTextField fieldEventoFimOrigem;

	private JTextField fieldEventoNomeDestino;
	private JTextField fieldEventoLocalDestino;
	private JTextField fieldEventoInicioDestino;
	private JTextField fieldEventoFimDestino;
	private JComboBox fieldFuncionarioResponsavel;
	
	private JTable tabelaEquipamento;
	private OSEquipamentoEnviadoTableModel modelEquipamento;
	private OrdemServico osOrigem;
	private OrdemServico osDestino;
	private OsDePassagem osPassagem;
	
	private JPopupMenu menuTabela;

	
	public FormOsPassagem() {
		super(new OsPassagemTableModel(), "/images/icon_passagem_18.png", "OS de Passagem");
		desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
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
		fieldFuncionarioResponsavel = new JComboBox();
		carregarComboFuncionario();
		
		this.addInputField(new TitledPanel("Ordem de Serviço de Origem", getPanelOs()), new RestricaoLayout(0,0,1,true,false));
		this.addInputField(new TitledPanel("Ordem de Serviço de Destino", getPanelOsDestino()), new RestricaoLayout(1,0,1,true,false));
		this.addInputField(new TitledPanel("Responsável", fieldFuncionarioResponsavel), new RestricaoLayout(2,0,1,true,false));
		this.addInputField(new TitledPanel("Equipamentos", getPanelRecursosEquipamento()), new RestricaoLayout(3,0,1,2,true,true));
	}
	
	private JPanel getPanelOs() {
		JPanel panelOS = new JPanel();
		panelOS.setLayout(new GridBagLayout());
		fieldEventoNomeOrigem = new JTextField();
		fieldEventoLocalOrigem = new JTextField();
		fieldEventoInicioOrigem = new JTextField();
		fieldEventoFimOrigem = new JTextField();
		
		fieldEventoNomeOrigem.setEditable(false);
		fieldEventoLocalOrigem.setEditable(false);
		fieldEventoInicioOrigem.setEditable(false);
		fieldEventoFimOrigem.setEditable(false);
		
		panelOS.add(new TitledPanel("Código", getBotaoProcurarOS()), new RestricaoLayout(0, 0, false, false));
		panelOS.add(new TitledPanel("Evento", fieldEventoNomeOrigem), new RestricaoLayout(0, 1, 2, true, false));
		panelOS.add(new TitledPanel("Local", fieldEventoLocalOrigem), new RestricaoLayout(0, 3, 1, true, false));
		panelOS.add(new TitledPanel("Data do Início", fieldEventoInicioOrigem), new RestricaoLayout(0, 4, 1, true, false));
		panelOS.add(new TitledPanel("Data do Fim", fieldEventoFimOrigem), new RestricaoLayout(0, 5, 1, true, false));
		return panelOS;
	}
	
	private JPanel getPanelOsDestino() {
		JPanel panelOS = new JPanel();
		panelOS.setLayout(new GridBagLayout());
		fieldEventoNomeDestino = new JTextField();
		fieldEventoLocalDestino = new JTextField();
		fieldEventoInicioDestino = new JTextField();
		fieldEventoFimDestino = new JTextField();
		
		fieldEventoNomeDestino.setEditable(false);
		fieldEventoLocalDestino.setEditable(false);
		fieldEventoInicioDestino.setEditable(false);
		fieldEventoFimDestino.setEditable(false);
		
		panelOS.add(new TitledPanel("Código", getBotaoProcurarOSDestino()), new RestricaoLayout(0, 0, false, false));
		panelOS.add(new TitledPanel("Evento", fieldEventoNomeDestino), new RestricaoLayout(0, 1, 2, true, false));
		panelOS.add(new TitledPanel("Local", fieldEventoLocalDestino), new RestricaoLayout(0, 3, 1, true, false));
		panelOS.add(new TitledPanel("Data do Início", fieldEventoInicioDestino), new RestricaoLayout(0, 4, 1, true, false));
		panelOS.add(new TitledPanel("Data do Fim", fieldEventoFimDestino), new RestricaoLayout(0, 5, 1, true, false));
		return panelOS;
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
						DialogSearchOS2 teste = new DialogSearchOS2(FormOsPassagem.this, listaOS);
						OrdemServico o = teste.showDialog(FormOsPassagem.this);
						if(o != null) 
							carregarOS(o);
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	public JButton getBotaoProcurarOSDestino() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));
		
		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						List<OrdemServico> listaOS;
						listaOS = Facade.getInstance().listarOrdemAtiva();
						DialogSearchOS2 teste = new DialogSearchOS2(FormOsPassagem.this, listaOS);
						OrdemServico o = teste.showDialog(FormOsPassagem.this);
						if(o != null) 
							carregarOSDestino(o);
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	private JPanel getPanelRecursosEquipamento() {
		JPanel equipamento = new JPanel();
		equipamento.setLayout(new GridBagLayout());
		
		equipamento.add(new TitledPanel("Equipamento", getBotaoProcurarEquipamento()), new RestricaoLayout(0,0,1,true,false));
		equipamento.add(getTabelaEquipamento(), new RestricaoLayout(1,0,1,1,true, true));
		return equipamento;
	}
	

	public JButton getBotaoProcurarEquipamento() {
		JButton botaoProcurarEquipamento = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarEquipamento.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarEquipamento.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarEquipamento.setPreferredSize(new Dimension(20,20));

		botaoProcurarEquipamento.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(osOrigem != null && osDestino != null) {
							List<Equipamento> listaEquipamento = new ArrayList<Equipamento>();
							for(EquipamentoEnviado eq: osOrigem.getEquipamentoEnviado()) {
								if(!eq.isStatus()){
									listaEquipamento.add(eq.getEquipamento());
								}
							}
							listaEquipamento.removeAll(modelEquipamento.getEquipamentos());
							DialogSearchEquipamento teste = new DialogSearchEquipamento(FormOsPassagem.this, listaEquipamento);
							Equipamento c = teste.showDialog(FormOsPassagem.this);
							registrarEquipamento(c);
						} else {
							JOptionPane.showMessageDialog(null, "Selecione as Ordens de Serviço de Origem e Destino", "ERRO!", JOptionPane.ERROR_MESSAGE);
						}
							
					}
				}	
		);
		return botaoProcurarEquipamento;
	}
	
	private void registrarEquipamento(Equipamento e) {
		if(e != null) {
			modelEquipamento.addElement(e);
		} else {
			java.awt.Toolkit.getDefaultToolkit().beep();  
		}
	}
	
	private JComponent getTabelaEquipamento() {
		JScrollPane panelTabela = new JScrollPane();

		modelEquipamento = new OSEquipamentoEnviadoTableModel();
		tabelaEquipamento = new JTable(modelEquipamento);
		tabelaEquipamento.setFillsViewportHeight(true);
		tabelaEquipamento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		
		tabelaEquipamento.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaEquipamento.getSelectedRow() >= 0 )
								menuTabela.show(tabelaEquipamento, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Recurso para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);

		TableColumn column = null;
		int columnWidth[] = modelEquipamento.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamento.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	
		panelTabela.setViewportView(tabelaEquipamento);
		return panelTabela;
	}

	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Recurso");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelEquipamento.removeRow(tabelaEquipamento.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}
	
	private void carregarOS(OrdemServico o) {
		if(osDestino == null || !osDestino.equals(o)) {
			osOrigem = o;
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			fieldEventoNomeOrigem.setText( o.getNomeEvento());
			fieldEventoLocalOrigem.setText(o.getLocal().getNome());
			fieldEventoInicioOrigem.setText(formato.format(o.getDataInicio()));
			fieldEventoFimOrigem.setText(formato.format(o.getDataFim()));
		} else {
			JOptionPane.showMessageDialog(null, "As Ordens de serviço de origem e destino não podem ser a mesma");
		}
	}
	
	private void carregarOSDestino(OrdemServico o) {
		if(osOrigem == null || !osOrigem.equals(o)) {
			osDestino = o;
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			fieldEventoNomeDestino.setText( o.getNomeEvento());
			fieldEventoLocalDestino.setText(o.getLocal().getNome());
			fieldEventoInicioDestino.setText(formato.format(o.getDataInicio()));
			fieldEventoFimDestino.setText(formato.format(o.getDataFim()));
		} else {
			JOptionPane.showMessageDialog(null, "As Ordens de serviço de origem e destino não podem ser a mesma");
		}
	}
	
	private void limparDadosOS() {
		osOrigem = null;
		fieldEventoNomeOrigem.setText("");
		fieldEventoLocalOrigem.setText("");
		fieldEventoInicioOrigem.setText("");
		fieldEventoFimOrigem.setText("");	
	}
	
	private void limparDadosOSDestino() {
		osDestino = null;
		fieldEventoNomeDestino.setText("");
		fieldEventoLocalDestino.setText("");
		fieldEventoInicioDestino.setText("");
		fieldEventoFimDestino.setText("");	
	}

	public List<OsDePassagem> listAll() {
		return Facade.getInstance().listarOsDePassagem();
	}

	public boolean save(OsDePassagem current) {
		Funcionario responsavel = (Funcionario) fieldFuncionarioResponsavel.getSelectedItem();
		for(Equipamento eq : modelEquipamento.getEquipamentos()) {
			for(EquipamentoEnviado eqv : osOrigem.getEquipamentoEnviado()) {
				if(eqv.getEquipamento().getNumeroSerie().equals(eq.getNumeroSerie())) {
					eqv.setDataDevolucao(new Date());
					eqv.setStatus(true);
					Facade.getInstance().atualizarEquipamentoEnviado(eqv);
					
					EquipamentoEnviado eqv2 = new EquipamentoEnviado();
					eqv2.setDataSaida(new Date());
					eqv2.setEquipamento(eqv.getEquipamento());
					eqv2.setStatus(false);
					eqv2.setFuncionarioEntrega(responsavel);
					eqv2.setUsuario(Facade.getInstance().getUsuarioLogado());
					
					osDestino.getEquipamentoEnviado().add(eqv2);
					
					
				}
			}
		}
		
		boolean finalizada = true;
		for(EquipamentoEnviado e: osOrigem.getEquipamentoEnviado()) {
			if(!e.isStatus()) {
				finalizada = false;
				break;
			}
		}
		if(finalizada){
			if(osOrigem.getStatus() == StatusOS.OS_EMERGENCIAL_INICIADA)
				osOrigem.setStatus(StatusOS.OS_EMERGENCIAL_CONCLUIDA);
			else
				osOrigem.setStatus(StatusOS.CONCLUIDA);
			Facade.getInstance().atualizarOrdemServico(osOrigem);
		}
		
		
		if(osDestino.getStatus() == StatusOS.OS_EMERGENCIAL || osDestino.getStatus() == StatusOS.OS_EMERGENCIAL_INICIADA)
			osDestino.setStatus(StatusOS.OS_EMERGENCIAL_INICIADA);
		else
			osDestino.setStatus(StatusOS.EM_REALIZACAO);
		
		Facade.getInstance().atualizarOrdemServico(osDestino);
		
		for(Equipamento eq : modelEquipamento.getEquipamentos()) {
			current.getEquipamentoEnviado().add(Facade.getInstance().localizarEquipamentoEnviado(eq.getNumeroSerie()));			
		}
		
		Facade.getInstance().salvarOsDePassagem(current);
		osPassagem = current;
		return true;
	}

	@Override
	public boolean print(OsDePassagem current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(osPassagem != null){
			hm.put("id",  osPassagem.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordem_servico_passagem.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione uma OS de Passagem para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	
	}

	public void loadInputFields(OsDePassagem t) {
		t.setOrigem(osOrigem);
		t.setDestino(osDestino);
		t.setData(new Date());
		t.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		t.setEquipamentoEnviado(new ArrayList<EquipamentoEnviado>());
	}

	protected void clear() {
		limparDadosOS();
		limparDadosOSDestino();
		modelEquipamento.removeAllElements();
		osPassagem = null;
		
	}

	protected void loadForm(OsDePassagem osp) {
		clear();
		carregarOS(osp.getOrigem());
		carregarOSDestino(osp.getDestino());
		Funcionario responsavel = null;
		modelEquipamento.removeAllElements();
		for(EquipamentoEnviado eq: osp.getEquipamentoEnviado()) {
			modelEquipamento.addElement(eq.getEquipamento());
			responsavel = eq.getFuncionarioEntrega();
		}
		fieldFuncionarioResponsavel.setSelectedItem(responsavel);
		osPassagem = osp;
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(!(fieldFuncionarioResponsavel.getSelectedItem() instanceof Funcionario) ){
			test = false;
			error += "\nSelecione o funcionário responsável";
		}
		if(osOrigem == null) {
			test = false;
			error += "\nSelecione a OS de Origem";
		}
		if(osDestino == null) {
			test = false;
			error += "\nSelecione a OS de Destino";
		}
		if(modelEquipamento.getEquipamentos().size() == 0) {
			test = false;
			error += "\nNenhum equipamento foi selecionado";
		}
		if(!test){
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public OsDePassagem newElement() {
		return new OsDePassagem();
	}
	
	protected boolean validateFormUpdate() {
		return false;
	}
	
	public boolean remove(OsDePassagem current) {
		return false;
	}
	public boolean update(OsDePassagem current) {
		return false;
	}	
	
}
