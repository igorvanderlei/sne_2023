package br.com.sne.sistema.gui.contareceber;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.HistoricoCancelamento;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.bean.Recibo;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.OpcaoPagamento;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.BordedPanel;
import br.com.sne.sistema.gui.util.components.DateCellRenderer;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.MoedaCellRenderer;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.components.ToggleStatus;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormReceita extends DefaultForm<Receita, ReceitaTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldDescricao;

	private JTextField fieldNomeCliente;
	private JTextField fieldOS;
	private JTextField fieldValor;
	private JDateChooser fieldDataVencimento;
	private JTextField fieldDataPagamento;
	private JComboBox fieldOpcaoPag;
	
	private JTextArea fieldObservacoes;
	private Receita receita;
	private JButton botaoPagarReceita;
	private JButton botaoCancelarPagamento;
	private JButton botaoExibirRecibo;
	private JButton botaoVisualizarOS;
	private JButton botaoVisualizarCliente;

	private JCheckBox fieldFaturado;
	
	private JPanel jPanelStatus; // Aberta, Paga
	private JToggleButton botaoStatusAberta;
	private JToggleButton botaoStatusPaga;

	private JMoedaRealTextField fieldTaxaCartao;
	
	public FormReceita() {
		super(new ReceitaTableModel(), "/images/icon_receber_18.png", "Contas a Receber");
		desabilitarBotaoAdicionar();
		desabilitarBotaoNovo();
		desabilitarBotaoRemover();
		getJTable().getColumnModel().getColumn(7).setCellRenderer(new MoedaCellRenderer());
		getJTable().getColumnModel().getColumn(8).setCellRenderer(new MoedaCellRenderer());
		
		getJTable().getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
	}
	
	public boolean save(Receita current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarReceita(current);
			current.setDataCadastro(new Date());
			current.setFuncionarioCadastro(Facade.getInstance().getUsuarioLogado().getFuncionario());
			
			receita = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "A Receita já se encontra cadastrada", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Receita current) {
		boolean s = true;
		
		Facade.getInstance().atualizarReceita(current);
		return s;
	}
	
	public boolean remove(Receita current) {
		boolean test = false;
		try {
			Facade.getInstance().removerReceita(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Receita. ", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void carregarComboOpcaoPag() {
		fieldOpcaoPag.removeAllItems();
		fieldOpcaoPag.addItem("Selecione uma Opção de Pagamento");
		for(OpcaoPagamento f: OpcaoPagamento.values()) {
			fieldOpcaoPag.addItem(f);
		}
	}
	
	public void createInputFields() {
		fieldID = new JTextField();
		fieldDescricao = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldNomeCliente = new JTextField();
		fieldOS = new JTextField();
		fieldDataPagamento = new JTextField();
		fieldValor = new JTextField();
		fieldDataVencimento = new JDateChooser();
		fieldOpcaoPag = new JComboBox();
		fieldTaxaCartao = new JMoedaRealTextField();
		
		fieldFaturado = new JCheckBox("Faturada");
		
		fieldNomeCliente.setEnabled(false);
		fieldOS.setEnabled(false);
		fieldValor.setEnabled(false);
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		fieldDataPagamento.setEditable(false);
		
		carregarComboOpcaoPag();
		
		fieldOpcaoPag.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldOpcaoPag.getSelectedItem() == OpcaoPagamento.CARTAO) {
	        		fieldTaxaCartao.setEditable(true);
	        	}
				else {
					fieldTaxaCartao.setEditable(false);
				}
	        }
	    });
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, true, false));
		this.addInputField(new TitledPanel("Cliente", fieldNomeCliente), new RestricaoLayout(0, 1, 3, true, false));
		this.addInputField(new TitledPanel("Ordem de Serviço", fieldOS), new RestricaoLayout(0, 4, 2, true, false));
		this.addInputField(new TitledPanel("Descrição", fieldDescricao), new RestricaoLayout(1, 0, 5, true, false));
		this.addInputField(new TitledPanel("", fieldFaturado), new RestricaoLayout(1, 5, true, false));
		this.addInputField(new TitledPanel("Valor", fieldValor), new RestricaoLayout(2, 0, 2, true, false));
		this.addInputField(new TitledPanel("Vencimento", fieldDataVencimento), new RestricaoLayout(2, 2, 1, true, false));
		this.addInputField(new TitledPanel("Opção de Pagamento", fieldOpcaoPag), new RestricaoLayout(2, 3, 1, true, false));
		this.addInputField(new TitledPanel("Taxa do cartão", fieldTaxaCartao), new RestricaoLayout(2, 4, 1, true, false));
		this.addInputField(new TitledPanel("Data Pagamento", fieldDataPagamento), new RestricaoLayout(2, 5, 1, true, false));

		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(3, 0, 6, 1, true, true));
		
		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridBagLayout());
		painelBotoes.add(getBotaoPagarReceita(), new RestricaoLayout(0, 0, 1, true, false));
		painelBotoes.add(getBotaoExibirRecibo(), new RestricaoLayout(0, 1, 1, true, false));
		painelBotoes.add(getBotaoVisualizarOS(), new RestricaoLayout(0, 2, 1, true, false));
		painelBotoes.add(getBotaoVisualizarCliente(), new RestricaoLayout(0, 3, 1, true, false));
		painelBotoes.add(getBotaoCancelarPagamento(), new RestricaoLayout(0, 4, 1, true, false));
		this.addInputField(painelBotoes, new RestricaoLayout(4, 0, 6, true, false));
		
		this.addStatusTab("Status", getJPanelStatus());
	}

	public JButton getBotaoPagarReceita() {
		if(botaoPagarReceita == null){
			botaoPagarReceita = new JButton("Registrar Pagamento", new ImageIcon(getClass().getResource("/images/check_24.png")));
			botaoPagarReceita.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(receita == null) {
								JOptionPane.showMessageDialog(FormReceita.this, "Selecione uma Receita para registrar o pagamento", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							JFrame telaParametro = new TelaParamPagarReceita(
									receita,fieldObservacoes.getText(),
									(OpcaoPagamento)fieldOpcaoPag.getSelectedItem(),
									fieldTaxaCartao.getValor());
							JDialog dialog = new JDialog(telaParametro, "Pagamento de receita", true);
							dialog.setContentPane(telaParametro.getContentPane());  
							dialog.setBounds(telaParametro.getBounds());  
							dialog.setVisible(true);
							
							clear(); 
						}
					}
			);
			botaoPagarReceita.setEnabled(false);
			
		}
		return botaoPagarReceita;
	}
	
	public JButton getBotaoCancelarPagamento() {
		if(botaoCancelarPagamento == null){
			botaoCancelarPagamento = new JButton("Cancelar Pagamento", new ImageIcon(getClass().getResource("/images/error_24.png")));
			botaoCancelarPagamento.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(receita == null) {
								JOptionPane.showMessageDialog(FormReceita.this, "Selecione uma Receita para cancelar o pagamento", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							if(JOptionPane.showConfirmDialog(FormReceita.this, "Confirma o cancelamento do pagamento", "Confirmação", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
								if(receita.getValor().compareTo(receita.getValorPago()) > 0) {
									JOptionPane.showMessageDialog(null, "O pagamento desta parcela gerou uma parcela de saldo, \nlembre-se de remover esta parcela.", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
								}
								HistoricoCancelamento hist = new HistoricoCancelamento();
								hist.setData(new Date());
								hist.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
								hist.setReceita(receita);
								Facade.getInstance().salvarHistoricoCancelamento(hist);
								Recibo recibo = Facade.getInstance().localizarReciboPorReceita(receita);
								recibo.setCancelado(true);
								Facade.getInstance().atualizarRecibo(recibo);
								receita.setValorPago(new BigDecimal(0));
								receita.setDataPagamento(null);
								receita.setObservacoes(fieldObservacoes.getText());
								receita.setSituacao(false);
								Facade.getInstance().atualizarReceita(receita);
								JOptionPane.showMessageDialog(null,"Cancelamento do pagamento realizado com sucesso!");	
								clear();
							}
						}
					}
			);
			botaoCancelarPagamento.setEnabled(false);
			
		}
		return botaoCancelarPagamento;
	}
	
	public JButton getBotaoExibirRecibo() {
		if(botaoExibirRecibo == null){
			botaoExibirRecibo = new JButton("Exibir Recibo", new ImageIcon(getClass().getResource("/images/lente_24.png")));
			botaoExibirRecibo.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(receita == null) {
								JOptionPane.showMessageDialog(FormReceita.this, "Selecione uma receita para exibir o recibo", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							JFrame telaParametro = new TelaParamExibirRecibo(receita);
							JDialog dialog = new JDialog(telaParametro, "Exibição de Recibo", true);
							dialog.setContentPane(telaParametro.getContentPane());  
							dialog.setBounds(telaParametro.getBounds());  
							dialog.setVisible(true); 
						}
					}
			);botaoExibirRecibo.setEnabled(false);
			
		}
		return botaoExibirRecibo;
	}
	
	public JButton getBotaoVisualizarOS() {
		if(botaoVisualizarOS == null){
			botaoVisualizarOS = new JButton("Visualizar OS", new ImageIcon(getClass().getResource("/images/icon_os_18.png")));
			botaoVisualizarOS.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(receita == null) {
								JOptionPane.showMessageDialog(FormReceita.this, "Selecione uma receita para visualizar a OS", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							HashMap<String, Object> hm = new HashMap<String, Object>();
							hm.put("id", receita.getOrdemServico().getId());
							hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
							hm.put("cidade", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
							ImageIcon logos =  new ImageIcon(getClass().getResource("/images/logos.png"));

							hm.put("logos", logos.getImage());

							try {
								Connection c  = Facade.getInstance().getConnection() ;
								URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordem_servico_completa.jasper");  
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
					}
			); botaoVisualizarOS.setEnabled(false);
			
		}
		return botaoVisualizarOS;
	}
	
	public JButton getBotaoVisualizarCliente() {
		if(botaoVisualizarCliente == null){
			botaoVisualizarCliente = new JButton("Visualizar Cliente", new ImageIcon(getClass().getResource("/images/find.png")));
			botaoVisualizarCliente.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(receita == null) {
								JOptionPane.showMessageDialog(FormReceita.this, "Selecione uma receita para visualizar o cliente", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							FormCliente telaCliente = (FormCliente) WindowFactory.createTelaCliente(Facade.getInstance().getTelaPrincipal().getDesktop());
							Facade.getInstance().beginTransaction();
							telaCliente.loadForm(Facade.getInstance().carregarCliente(receita.getOrdemServico().getCliente().getId()));
							Facade.getInstance().commit();
							telaCliente.setVisible(true);
						}
					}
			); botaoVisualizarCliente.setEnabled(false);
			
		}
		return botaoVisualizarCliente;
	}
	
	public Receita newElement() {
		return new Receita();
	}

	public void loadInputFields(Receita rec) {
		rec.setObservacoes(fieldObservacoes.getText());
		rec.setDescricao(fieldDescricao.getText());
		rec.setDataVencimento(fieldDataVencimento.getDate());
		rec.setFaturado(fieldFaturado.isSelected());
		rec.setOpcaoPag((OpcaoPagamento)fieldOpcaoPag.getSelectedItem());
		if(rec.isFaturado() && rec.getDataFaturado() == null) {
			rec.setDataFaturado(new Date());
		}
		
		if(!rec.isFaturado() && rec.getDataFaturado() != null) {
			rec.setDataFaturado(null);
		}
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldDescricao.setText("");
		fieldObservacoes.setText("");
		fieldNomeCliente.setText("");
		fieldOS.setText("");
		fieldValor.setText("");
		fieldDataVencimento.setDate(null);
		receita = null;
		botaoPagarReceita.setEnabled(false);
		botaoCancelarPagamento.setEnabled(false);
		botaoVisualizarOS.setEnabled(false);
		botaoVisualizarCliente.setEnabled(false);
		fieldFaturado.setSelected(false);
		botaoExibirRecibo.setSelected(false);
		fieldOpcaoPag.setSelectedIndex(0);
		fieldTaxaCartao.setText("");
	}

	protected void loadForm(Receita desp) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		
		fieldID.setText("" + desp.getId());
		fieldDescricao.setText(desp.getDescricao());
		fieldObservacoes.setText(desp.getObservacoes());
		fieldNomeCliente.setText(desp.getOrdemServico().getCliente().getNome());
		fieldOS.setText(desp.getOrdemServico().getNomeEvento());
		fieldValor.setText("R$ " +  formato.format(desp.getValor()));
		fieldDataVencimento.setDate(desp.getDataVencimento());
		fieldFaturado.setSelected(desp.isFaturado());
		fieldOpcaoPag.setSelectedItem(desp.getOpcaoPag());
		if(desp.getDataPagamento() != null) {
			fieldDataPagamento.setText(formatoData.format(desp.getDataPagamento()));
		}
		receita = desp;
		botaoVisualizarOS.setEnabled(true);
		botaoVisualizarCliente.setEnabled(true);
		if(!receita.isSituacao()) {
			botaoPagarReceita.setEnabled(true);
			botaoCancelarPagamento.setEnabled(false);
			botaoExibirRecibo.setEnabled(false);
		} else {
			botaoPagarReceita.setEnabled(false);
			botaoExibirRecibo.setEnabled(true);
			if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.CANCELAR_PAGAMENTO_RECEITA)) {
				//botaoCancelarPagamento.setEnabled(true);
			}
		}
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";

		if(fieldDescricao.getText().length() <= 0) {
			test = false;
			error += "\nPreencha a descriçã da Receita";
		}
		if(fieldOpcaoPag.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione uma Opção de Pagamento";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Receita> listAll() {

		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Receita>();
		}
		List<Receita> lista = Facade.getInstance().listarReceitas(campo,texto);;

		boolean aberta = botaoStatusAberta.isSelected();
		boolean paga = botaoStatusPaga.isSelected();

		List<Receita> resp = new ArrayList<Receita>();

		for(Receita c : lista) {
			if((c.isSituacao() && paga) ||
					(!c.isSituacao() && aberta)	) {
				resp.add(c);
			}
		}
		return resp;

	}

	@Override
	public boolean print(Receita current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(receita != null) {
			hm.put("id", receita.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/nordesti/locav/report/receita.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione uma Receita para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}

	private JPanel getJPanelStatus() {
		if (jPanelStatus == null) {
			jPanelStatus = new JPanel();
			jPanelStatus.setLayout(new BoxLayout(jPanelStatus, BoxLayout.X_AXIS));
			jPanelStatus.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			jPanelStatus.setName("jPanelStatus");

			botaoStatusAberta = new ToggleStatus("Aberta", "images/receita.png" );
			botaoStatusAberta.setIcon(new ImageIcon(getClass().getResource("/images/receita.png")));
			
			botaoStatusAberta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			botaoStatusPaga = new ToggleStatus("Paga", "" ); 
			botaoStatusPaga.setIcon(new ImageIcon(getClass().getResource("/images/receita_paga.png")));
			botaoStatusPaga.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			jPanelStatus.add(new BordedPanel(botaoStatusAberta), null);
			jPanelStatus.add(new BordedPanel(botaoStatusPaga), null);
		}
		return jPanelStatus;
	}

}
