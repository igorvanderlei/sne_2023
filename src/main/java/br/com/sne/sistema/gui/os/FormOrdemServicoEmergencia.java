package br.com.sne.sistema.gui.os;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.toedter.calendar.JDateChooser;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.AlertaOSEmergencial;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.local.DialogSearchLocal;
import br.com.sne.sistema.gui.orcamento.RecursosSolicitadosEmergenciaTableModel;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorBounded;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;
import br.com.sne.sistema.gui.util.form.DialogSearchRecurso;
import br.com.sne.sistema.gui.util.form.FormIntervalar;

public class FormOrdemServicoEmergencia extends DefaultForm<OrdemServico, OrdemServicoEmergencialTableModel> implements FormIntervalar {
	private static final long serialVersionUID = 1L;

	private Cliente cliente = null;
	private Recurso recurso = null;

	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;

	private JTextField fieldNomeEvento;
	private JTextField fieldFuncionario;
	private JDateChooser fieldDataInicio;
	private JDateChooser fieldDataFim;
	private JDateChooser fieldDataMontagem;
	
	private JSpinner fieldHoraInicio;
	private JSpinner fieldHoraFim;
	private JSpinner fieldHoraMontagem;
	
	private JTextArea fieldObservacoes;
	private JTextArea fieldObservacoesLocalEvento;
	private JComboBox fieldStatusOS;

	private JTextField fieldRecursoID;
	private JTextField fieldRecursoNome;
	private JDateChooser fieldRecursoDataInicio;
	private JDateChooser fieldRecursoDataFim;
	private JTextField fieldRecursoQuantidade;

	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;

	private JTextField fieldLocalID;
	private JTextField fieldLocalNome;

	private JTable tabelaRecursosSolicitados;
	private RecursosSolicitadosEmergenciaTableModel modelRecurso;
	private JPopupMenu menuTabela;

	private JTabbedPane tabDetalhes;
	private JLabel fieldPrecoTotal;


	private OrdemServico ordemServ = null;

	Date horaInicial = new Date(1000*60*3*60);
	
	public FormOrdemServicoEmergencia() {
		super(new OrdemServicoEmergencialTableModel(), "/images/produto20.png", "Ordem de Serviço Emergencial");
		clear();
		desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
	}

	public boolean save(OrdemServico current) {
		boolean s = false;
		try {
			if(ordemServ != null) {
				current = new OrdemServico(current);
				current.setDataCadastro(new Date());
				current.setStatus(StatusOS.OS_EMERGENCIAL);
				Facade.getInstance().salvarOrdemServico(current);
			} else {
				Facade.getInstance().salvarOrdemServico(current);
			}
			
			AlertaOSEmergencial alerta = new AlertaOSEmergencial();
			alerta.setOsEmergencial(current);
			Facade.getInstance().salvarAlertaOSEmergencial(alerta);
			
			s = true;
			ordemServ = current;
			
			int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir a Ordem de Serviço ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
			if( resp == JOptionPane.OK_OPTION) {
				print(current);
				return false;
			}
			
		}
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "A Ordem de Serviço já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(OrdemServico current) {
		return false;
	}

	public boolean remove(OrdemServico current) {
		return false;
	}

	public void createInputFields() {
		this.addInputField(getPanelCliente(), new RestricaoLayout(0, 0, 2, true, false));
		this.addInputField(getTabDetalhes(), new RestricaoLayout(1, 0, 2, 1, true, true));
		this.addInputField(getPanelPrecoTotal(), new RestricaoLayout(2,1,1,true,false));
		carregarFieldFuncionario();
	}

	public JPanel getPanelCliente() {
		JPanel panelCliente = new JFormGroup("Dados do Cliente");
		panelCliente.setLayout(new GridBagLayout());
		fieldClienteID = new JIntField();
		fieldClienteRazao = new JTextField();
		fieldClienteRazao.setEditable(false);

		panelCliente.add(new TitledPanel("Código", getPanelCodigoCliente()), new RestricaoLayout(0, 0, false, false));
		panelCliente.add(new TitledPanel("Razão Social", fieldClienteRazao), new RestricaoLayout(0, 1, 5, true, false));

		return panelCliente;
	}

	public JPanel getPanelCodigoCliente() {
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(new BoxLayout(panelCliente, BoxLayout.LINE_AXIS));
		JButton botaoCliente = getBotaoProcurarCliente();

		fieldClienteID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idCliente = Integer.parseInt(fieldClienteID.getText());
						Cliente c = Facade.getInstance().carregarCliente(idCliente);
						if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_CLIENTES) ||
								Facade.getInstance().getUsuarioLogado().getFuncionario().getId() == c.getFuncionario().getId())
						carregarCliente(c);
					} catch (Exception e){
						limparCliente();
					}
				}
			}
		});

		botaoCliente.setPreferredSize(new Dimension(30,18));

		panelCliente.add(fieldClienteID);
		panelCliente.add(botaoCliente);
		return panelCliente;
	}

	private JTabbedPane getTabDetalhes() {
		tabDetalhes = new JTabbedPane();
		tabDetalhes.addTab("Dados do Evento", getPanelDadosEvento());
		tabDetalhes.addTab("Local do Evento", getPanelEndereco());		
		tabDetalhes.addTab("Recursos Solicitados", getPanelRecursos());
		tabDetalhes.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if(tabDetalhes.getSelectedIndex() == 2) {
							limparRecurso();
						}
					}
				}
		);
		return tabDetalhes;
	}

	private JPanel getPanelEndereco () {
		JPanel endereco = new JPanel();
		endereco.setLayout(new GridBagLayout());

		fieldEnderecoLogradouro = new JTextField();
		fieldEnderecoNumero = new JTextField();
		fieldEnderecoComplemento = new JTextField();
		fieldEnderecoCEP = new JCepField();
		fieldEnderecoBairro = new JTextField();
		fieldEnderecoCidade = new JTextField();
		fieldEnderecoEstado = new JComboEstado();
		fieldEnderecoReferencia = new JTextField();
		fieldLocalNome = new JTextField();


		fieldObservacoesLocalEvento = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoesLocalEvento);
		
		endereco.add(new TitledPanel("Código", getPanelCodigoLocal()), new RestricaoLayout(0, 0, false, false));
		
		endereco.add(new TitledPanel("Nome do Local", fieldLocalNome), new RestricaoLayout(0, 1, 5, true, false));

		endereco.add(new TitledPanel("Logradouro", fieldEnderecoLogradouro), new RestricaoLayout(1,0, 3, true, false));
		endereco.add(new TitledPanel("Numero", fieldEnderecoNumero), new RestricaoLayout(1,3, true, false));
		endereco.add(new TitledPanel("Complemento", fieldEnderecoComplemento), new RestricaoLayout(1,4, 1, true, false));
		endereco.add(new TitledPanel("CEP", fieldEnderecoCEP), new RestricaoLayout(1,5, 1, true, false));
		
		endereco.add(new TitledPanel("Bairro", fieldEnderecoBairro), new RestricaoLayout(2,0, 2, true, false));
		endereco.add(new TitledPanel("Cidade", fieldEnderecoCidade), new RestricaoLayout(2,2, 1, true, false));
		endereco.add(new TitledPanel("Estado", fieldEnderecoEstado), new RestricaoLayout(2,3, 1, true, false));
		endereco.add(new TitledPanel("Ponto de Referência", fieldEnderecoReferencia), new RestricaoLayout(2,4, 2, true, false));
		
		endereco.add(new TitledPanel("Observações do Local do Evento", scrollObservacoes), new RestricaoLayout(3,0,6,1, true, true));

		return endereco;
	}


	private JButton getBotaoImportarEnderecoCliente() {
		JButton botaoImportarEnderecoCliente = new JButton("Utilizar Endereço do Cliente" );

		botaoImportarEnderecoCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cliente != null) {
							fieldEnderecoBairro.setText(cliente.getEndereco().getBairro());
							fieldEnderecoLogradouro.setText(cliente.getEndereco().getLogradouro());
							fieldEnderecoNumero.setText(cliente.getEndereco().getNumero());
							fieldEnderecoComplemento.setText(cliente.getEndereco().getComplemento());
							fieldEnderecoCidade.setText(cliente.getEndereco().getCidade());
							fieldEnderecoEstado.setSelectedItem(cliente.getEndereco().getEstado());
							fieldEnderecoReferencia.setText(cliente.getEndereco().getPontoReferencia());
							fieldEnderecoCEP.setText(cliente.getEndereco().getCep());
						}

					}
				}
		);
		return botaoImportarEnderecoCliente;
	}


	public JPanel getPanelCodigoLocal() {
		JPanel panelRecurso = new JPanel();
		panelRecurso.setLayout(new BoxLayout(panelRecurso, BoxLayout.LINE_AXIS));
		JButton botaoLocal = getBotaoProcurarLocal();
		fieldLocalID = new JIntField();
		fieldLocalID.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int idLocal = Integer.parseInt(fieldLocalID.getText());
						Local l = Facade.getInstance().carregarLocal(idLocal);
						carregarLocal(l);
					} catch (Exception e){
						e.printStackTrace();
						limparLocal();
					}
				}
			}
		});

		botaoLocal.setPreferredSize(new Dimension(30,18));
		panelRecurso.add(fieldLocalID);
		panelRecurso.add(botaoLocal);
		panelRecurso.add(getBotaoImportarEnderecoCliente());
		return panelRecurso;
	}

	private JPanel getPanelDadosEvento() {
		fieldNomeEvento = new JTextField();
		fieldFuncionario = new JTextField();
		fieldFuncionario.setEnabled(false);
		fieldDataInicio = new JDateChooser();
		fieldDataFim = new JDateChooser();
		fieldDataMontagem = new JDateChooser();
		fieldObservacoes = new JTextArea();
		fieldStatusOS = new JComboBox();
		fieldStatusOS.setEnabled(false);
		carregarFieldStatus();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);

		fieldHoraInicio = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorInicio = new JSpinner.DateEditor(fieldHoraInicio, "HH:mm");
		fieldHoraInicio.setEditor(timeEditorInicio);
		
		fieldHoraFim = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorFim = new JSpinner.DateEditor(fieldHoraFim, "HH:mm");
		fieldHoraFim.setEditor(timeEditorFim);
		
		fieldHoraMontagem = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorMontagem = new JSpinner.DateEditor(fieldHoraMontagem, "HH:mm");
		fieldHoraMontagem.setEditor(timeEditorMontagem);
		
		JPanel painelDataHoraInicio = new JPanel();
		painelDataHoraInicio.setLayout(new GridBagLayout());
		painelDataHoraInicio.add(fieldDataInicio, new RestricaoLayout(0,0,1,true,false));
		painelDataHoraInicio.add(fieldHoraInicio, new RestricaoLayout(0,1,1,true,false));
		
		JPanel painelDataHoraFim = new JPanel();
		painelDataHoraFim.setLayout(new GridBagLayout());
		painelDataHoraFim.add(fieldDataFim, new RestricaoLayout(0,0,1,true,false));
		painelDataHoraFim.add(fieldHoraFim, new RestricaoLayout(0,1,1,true,false));
		
		JPanel painelDataHoraMontagem = new JPanel();
		painelDataHoraMontagem.setLayout(new GridBagLayout());
		painelDataHoraMontagem.add(fieldDataMontagem, new RestricaoLayout(0,0,1,true,false));
		painelDataHoraMontagem.add(fieldHoraMontagem, new RestricaoLayout(0,1,1,true,false));
		
		fieldDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataInicio.getDate() != null)
							fieldDataFim.setMinSelectableDate(fieldDataInicio.getDate());
					}
				}
		);

		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());

		painelDadosEvento.add(new TitledPanel("Nome do Evento", fieldNomeEvento), new RestricaoLayout(0, 0, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayout(0, 1, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Status", fieldStatusOS), new RestricaoLayout(0, 2, 1, true, false));
		
		painelDadosEvento.add(new TitledPanel("Início do Evento", painelDataHoraInicio), new RestricaoLayout(1, 0, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Fim do Evento", painelDataHoraFim), new RestricaoLayout(1,1,1,true, false));
		painelDadosEvento.add(new TitledPanel("Montagem do Evento", painelDataHoraMontagem), new RestricaoLayout(1,2,1,true, false));

		painelDadosEvento.add(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 3, 1, true, true));

		return painelDadosEvento;		
	}

	public JPanel getPanelRecursos() {
		fieldRecursoNome = new JTextField();
		fieldRecursoNome.setEditable(false);
		fieldRecursoDataInicio = new JDateChooser();
		fieldRecursoDataFim = new JDateChooser();
		fieldRecursoQuantidade = new JIntField();
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());

		modelRecurso = new RecursosSolicitadosEmergenciaTableModel();
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());

		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());

		fieldRecursoDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						fieldRecursoDataFim.setMinSelectableDate(fieldRecursoDataInicio.getDate());
					}
				}
		);
		JPanel adicionar = new JFormGroup("Adicionar Recurso");
		adicionar.setLayout(new GridBagLayout());

		adicionar.add(new TitledPanel("Codigo", getPanelCodigoRecurso()), new RestricaoLayout(0, 0, false, false));
		adicionar.add(new TitledPanel("Recurso", fieldRecursoNome), new RestricaoLayout(0, 1, 1, true, false));
		adicionar.add(new TitledPanel("Data Inicial", fieldRecursoDataInicio), new RestricaoLayout(0, 2, false, false));
		adicionar.add(new TitledPanel("Data Final", fieldRecursoDataFim), new RestricaoLayout(0, 3, false, false));
		adicionar.add(new TitledPanel("Quant.", fieldRecursoQuantidade), new RestricaoLayout(0, 4, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecurso()) , new RestricaoLayout(0, 5, false, false));

		panelRecursos.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelRecursos.add(new TitledPanel("Recursos Solicitados", scrollTabela), new RestricaoLayout(1,0,1,1,true,true));

		limparPanelRecursos();
		return panelRecursos;		
	}

	private JTable getTabelaRecursosSolicitados() {
		tabelaRecursosSolicitados = new JTable(modelRecurso);
		tabelaRecursosSolicitados.setSelectionBackground(Color.LIGHT_GRAY);

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
		
		tabelaRecursosSolicitados.getColumnModel().getColumn(2).setCellEditor(new JDateChooserCellEditorBounded(this));
		tabelaRecursosSolicitados.getColumnModel().getColumn(3).setCellEditor(new JDateChooserCellEditorBounded(this));
		return tabelaRecursosSolicitados;
	}

	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Recurso");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(ordemServ!=null && ordemServ.getStatus()!=StatusOS.PENDENTE) {
							if(!Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.ALTERAR_OS_ANDAMENTO)) {
								JOptionPane.showMessageDialog(FormOrdemServicoEmergencia.this, "O usuário logado não possui permissão para alterar uma OS em andamento!", "ERRO", JOptionPane.ERROR_MESSAGE);	
								return;
							}
						}
						modelRecurso.removeRow(tabelaRecursosSolicitados.getSelectedRow());
					}
				}		
		);
		return menuRemover;
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

	private JPanel getPanelPrecoTotal() {
		fieldPrecoTotal = new JLabel("R$ 0,00");
		fieldPrecoTotal.setFont(new Font("Arial", Font.BOLD, 24));
		fieldPrecoTotal.setForeground(Color.RED);

		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));
		total.add(fieldPrecoTotal);
		return total;
	}

	private JButton getBotaoAdicionarRecurso() {
		JButton addRecursoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addRecursoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addRecursoButton.setVerticalAlignment(SwingConstants.CENTER);
		addRecursoButton.setPreferredSize(new Dimension(20,20));
		addRecursoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(ordemServ!=null && ordemServ.getStatus()!=StatusOS.PENDENTE) {
					if(!Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.ALTERAR_OS_ANDAMENTO)) {
						JOptionPane.showMessageDialog(FormOrdemServicoEmergencia.this, "O usuário logado não possui permissão para alterar uma OS em andamento!", "ERRO", JOptionPane.ERROR_MESSAGE);	
						return;
					}
				}
				
				if(recurso == null) {
					JOptionPane.showMessageDialog(FormOrdemServicoEmergencia.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(fieldRecursoDataInicio.getDate() == null) {
					JOptionPane.showMessageDialog(FormOrdemServicoEmergencia.this, "Selecione a data inicial do recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(fieldRecursoDataFim.getDate() == null) {
					JOptionPane.showMessageDialog(FormOrdemServicoEmergencia.this, "Selecione a data final do recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}

				RecursoSolicitado rec = new RecursoSolicitado();
				rec.setRecurso(recurso);
				rec.setDataFim(fieldRecursoDataFim.getDate());
				rec.setDataInicio(fieldRecursoDataInicio.getDate());
				rec.setPrecoUnitario(BigDecimal.ZERO);
				rec.setQuantidade(Integer.parseInt(fieldRecursoQuantidade.getText()));
				modelRecurso.addElement(rec);
				limparRecurso();

			}
		});
		return addRecursoButton;
	}

	private void limparPanelRecursos() {
		fieldRecursoID.setText("");		
		fieldRecursoNome.setText("");
		fieldRecursoDataInicio.setDate(fieldDataInicio.getDate()) ;
		fieldRecursoDataFim.setDate(fieldDataFim.getDate()) ;
		fieldStatusOS.setSelectedItem(StatusOS.OS_EMERGENCIAL);
	}

	public void carregarCliente(Cliente c) {
		cliente = c;
		if(c!=null){
			fieldClienteID.setText("" + c.getId());
			fieldClienteRazao.setText(c.getNome());
		} else {
			limparCliente();
		}
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

	private void carregarLocal(Local l) {
		if(l!=null){
			fieldEnderecoLogradouro.setText(l.getEndereco().getLogradouro());
			fieldEnderecoNumero.setText(l.getEndereco().getNumero());
			fieldEnderecoComplemento.setText(l.getEndereco().getComplemento());
			fieldEnderecoCEP.setText(l.getEndereco().getCep());
			fieldEnderecoBairro.setText(l.getEndereco().getBairro());
			fieldEnderecoCidade.setText(l.getEndereco().getCidade());
			fieldEnderecoEstado.setSelectedItem(l.getEndereco().getEstado());
			fieldEnderecoReferencia.setText(l.getEndereco().getPontoReferencia());
			fieldLocalID.setText("" + l.getId());
			fieldLocalNome.setText(l.getNome());
			fieldObservacoesLocalEvento.setText(l.getObservacoes());
		} else {
			limparLocal();
		}
	}

	private void limparLocal() {
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("000000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");
		fieldLocalID.setText("");
		fieldLocalNome.setText("");
		fieldObservacoesLocalEvento.setText("");
	}

	public void limparCliente() {
		cliente = null;
		fieldClienteID.setText("");
		fieldClienteRazao.setText("");
	}

	public void limparRecurso() {
		recurso = null;
		fieldRecursoID.setText("");
		fieldRecursoNome.setText("");
		fieldRecursoDataInicio.setDate(fieldDataInicio.getDate());
		fieldRecursoDataFim.setDate(fieldDataFim.getDate());
		fieldRecursoQuantidade.setText("1");

		fieldRecursoDataInicio.setMinSelectableDate(fieldDataInicio.getDate());
		fieldRecursoDataInicio.setMaxSelectableDate(fieldDataFim.getDate());

		fieldRecursoDataFim.setMinSelectableDate(fieldDataInicio.getDate());
		fieldRecursoDataFim.setMaxSelectableDate(fieldDataFim.getDate());
	}

	public JButton getBotaoProcurarCliente() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));

		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchClient teste = new DialogSearchClient(FormOrdemServicoEmergencia.this);
						Cliente c = teste.showDialog(FormOrdemServicoEmergencia.this);
						if(c != null)
							carregarCliente(c);
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	
	

	public JButton getBotaoProcurarRecurso() {
		JButton botaoProcurarRecurso = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarRecurso.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarRecurso.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarRecurso.setPreferredSize(new Dimension(20,20));

		botaoProcurarRecurso.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchRecurso teste = new DialogSearchRecurso(FormOrdemServicoEmergencia.this, Facade.getInstance().listarRecursos());
						Recurso r = teste.showDialog(FormOrdemServicoEmergencia.this);
						if(r != null)
							carregarRecurso(r);
					}
				}	
		);
		return botaoProcurarRecurso;
	}

	public JButton getBotaoProcurarLocal() {
		JButton botaoProcurarLocal = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarLocal.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarLocal.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarLocal.setPreferredSize(new Dimension(20,20));

		botaoProcurarLocal.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchLocal teste = new DialogSearchLocal(FormOrdemServicoEmergencia.this, Facade.getInstance().listarLocais());
						Local l = teste.showDialog(FormOrdemServicoEmergencia.this);
						if(l != null)
							carregarLocal(l);
					}
				}	
		);
		return botaoProcurarLocal;
	}

	public OrdemServico newElement() {
		return new OrdemServico();
	}

	public void loadInputFields(OrdemServico os) {
		os.setCliente(cliente);
		os.setRecursoSolicitado(modelRecurso.getRecursos());
		os.setStatus((StatusOS) fieldStatusOS.getSelectedItem());
		LocalEvento e = new LocalEvento();
		Endereco endereco=new Endereco();
		endereco.setLogradouro(fieldEnderecoLogradouro.getText());
		endereco.setNumero(fieldEnderecoNumero.getText());
		endereco.setComplemento(fieldEnderecoComplemento.getText());
		endereco.setCep(fieldEnderecoCEP.getText());
		endereco.setBairro(fieldEnderecoBairro.getText());
		endereco.setCidade(fieldEnderecoCidade.getText());
		endereco.setEstado((String) fieldEnderecoEstado.getSelectedItem());
		endereco.setPontoReferencia(fieldEnderecoReferencia.getText());
		e.setEndereco(endereco);
		e.setNome(fieldLocalNome.getText());
		e.setObservacoes(fieldObservacoesLocalEvento.getText());
		os.setLocal(e);
		os.setNomeEvento(fieldNomeEvento.getText());
		os.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		os.setDataInicio(fieldDataInicio.getDate());
		os.setDataFim(fieldDataFim.getDate());
		os.setDataMontagem(fieldDataMontagem.getDate());
		
		os.setHoraInicio((Date) fieldHoraInicio.getValue());
		os.setHoraFim((Date) fieldHoraFim.getValue());
		os.setHoraMontagem((Date) fieldHoraMontagem.getValue());
		
		os.setObservacoes(fieldObservacoes.getText());
	}

	protected void loadForm(OrdemServico rec) {
		carregarCliente(rec.getCliente());
		modelRecurso.removeAllElements();
		for(RecursoSolicitado r: rec.getRecursoSolicitado()) {
			modelRecurso.addElement(r);
		}
		carregarLocal(new Local(rec.getLocal()));
		fieldNomeEvento.setText(rec.getNomeEvento());
		fieldFuncionario.setText(rec.getFuncionario().getNome());
		fieldDataInicio.setDate(rec.getDataInicio());
		fieldDataFim.setDate(rec.getDataFim());
		fieldDataMontagem.setDate(rec.getDataMontagem());
		
		fieldHoraInicio.setValue(rec.getHoraInicio());
		fieldHoraFim.setValue(rec.getHoraFim());
		fieldHoraMontagem.setValue(rec.getHoraMontagem());
		
		fieldObservacoes.setText(rec.getObservacoes());
		fieldStatusOS.setSelectedItem(rec.getStatus());
		limparRecurso();
		ordemServ = rec;
	}

	protected void clear() {
		limparCliente();
		limparRecurso();
		limparLocal();
		modelRecurso.removeAllElements();
		fieldObservacoes.setText("");
		fieldNomeEvento.setText("");
		fieldDataInicio.setDate(null);
		fieldDataFim.setDate(null);
		fieldDataMontagem.setDate(null);
		
		
		fieldHoraInicio.setValue(horaInicial);
		fieldHoraFim.setValue(horaInicial);
		fieldHoraMontagem.setValue(horaInicial);
		
		fieldPrecoTotal.setText( "R$ 0,00");
		ordemServ = null;
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(cliente == null) {
			test = false;
			error += "\nSelecione o Cliente";

		}
		if(fieldDataInicio.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Início do Evento";			

		}
		if(fieldDataFim.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Fim do Evento";			

		}
		if(fieldDataMontagem.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Montagem do Evento";			

		}
		if(modelRecurso.getRecursos().size() <= 0){
			test = false;
			error += "\nAdicione pelo menos um recurso";
		}
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<OrdemServico> listAll() {
		List<OrdemServico> listaOrdemServico;
		listaOrdemServico = Facade.getInstance().listarOrdemServicosEmergencial();
		return listaOrdemServico;
	}

	public Date getDataInicial() {
		if(fieldDataInicio != null)
			return fieldDataInicio.getDate();
		return null;
	}

	public Date getDataFinal() {
		if(fieldDataFim != null)
			return fieldDataFim.getDate();		
		return null;
	}

	private void carregarFieldFuncionario() {
		fieldFuncionario.setText(Facade.getInstance().getUsuarioLogado().getFuncionario().getNome());
	}


	public boolean print(OrdemServico current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(ordemServ != null) {
			hm.put("id", ordemServ.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection();
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordemServicoEmergencial.jasper");  
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
				JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);   
				JasperViewer.viewReport(impressao,false);  
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}

	private void carregarFieldStatus() {
		fieldStatusOS.removeAllItems();
		fieldStatusOS.addItem("");
		for(StatusOS st : StatusOS.values())
			fieldStatusOS.addItem(st);
		fieldStatusOS.setSelectedItem(StatusOS.OS_EMERGENCIAL);
	}

}
