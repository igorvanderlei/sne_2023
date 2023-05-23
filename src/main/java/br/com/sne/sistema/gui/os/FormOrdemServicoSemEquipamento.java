package br.com.sne.sistema.gui.os;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.toedter.calendar.JDateChooser;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.OrdemServicoSemEquipamento;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.equipamentoenviado.EquipamentosRegistradosTableModel;
import br.com.sne.sistema.gui.orcamento.RecursosSolicitadosEmergenciaTableModel;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorBounded;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;
import br.com.sne.sistema.gui.util.form.DialogSearchOS2;
import br.com.sne.sistema.gui.util.form.FormIntervalar;

public class FormOrdemServicoSemEquipamento extends DefaultForm<OrdemServicoSemEquipamento, OrdemServicoSemEquipamentoTableModel> implements FormIntervalar {
	private static final long serialVersionUID = 1L;
	private Cliente cliente = null;
	private JTextField fieldEmergencialNome;
	
	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;
	private JTextField fieldClienteCnpj;
	private JTextField fieldClienteTelefone;
	private JTextField fieldClienteRamal;
	private JTextField fieldClienteContato;
	private JTextField fieldClienteCelular;

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

	private JTabbedPane tabDetalhes;
	private JMoedaRealTextField fieldPrecoTotal;

	private JTable tabelaEquipamentosRegistrados;
	private EquipamentosRegistradosTableModel modelEquipamentoResgistrado;

	private OrdemServico ordemServ = null;
	
	private OrdemServico emergencial = null;

	Date horaInicial = new Date(1000*60*3*60);
	
	public FormOrdemServicoSemEquipamento() {
		super(new OrdemServicoSemEquipamentoTableModel(), "/images/OS3_24.png", "Ordem de Serviço Sem Equipamento");
		clear();
		//desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
	}

	public boolean save(OrdemServicoSemEquipamento current) {
		boolean s = false;
		if(ordemServ != null){
			JOptionPane.showMessageDialog(this, "Erro! A Ordem de Serviço Sem Equipamento já foi salva\nClique no botão adicionar apenas uma vez.", "ERRO", JOptionPane.ERROR_MESSAGE);
			//return false;
		}
		try {
			
			if(emergencial != null) {
				current.setDataCadastro(new Date());
				current.setStatus(StatusOS.OS_SEM_EQUIPAMENTO);
				current.setOrdemServicoEmergencial(emergencial);
				
				Facade.getInstance().salvarOrdemServico(current);
				ordemServ = current;
				
				
			} else {
				JOptionPane.showMessageDialog(this, "Erro! Selecione uma Ordem de Serviço Emergencial.", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
			
			s = true;
			
			int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir a Ordem de Serviço ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
			if( resp == JOptionPane.OK_OPTION) {
				print(current);
				return false;
			}
			
		} catch(Exception err) {
			try {
				FileWriter arquivo = new FileWriter("log.txt",true);
				PrintWriter printer = new PrintWriter(arquivo);
				printer.write("\n\n\n######################\n\n" + new Date() + "\n");
				err.printStackTrace(printer);
				printer.flush();
				printer.close();
				arquivo.flush();
				arquivo.close();
			} catch (IOException e) {}
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);

		}
		return s;
	}

	
	public boolean update(OrdemServicoSemEquipamento current) {
		if(current == null) {
			JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço Sem Equipamento", "ERRO", JOptionPane.ERROR_MESSAGE);
		} else {
			if(current.getStatus() != StatusOS.OS_SEM_EQUIPAMENTO) {
				JOptionPane.showMessageDialog(this, "Não é possível alterar esta Ordem de Serviço, pois ela já foi aprovada pelo financeiro.", "ERRO", JOptionPane.ERROR_MESSAGE);	
			} else {
				Facade.getInstance().atualizarOrdemServico(current);
				return true;
			}
		}
		return false;
	}

	public boolean remove(OrdemServicoSemEquipamento current) {
		return false;
	}

	public void createInputFields() {
		this.addInputField(getPanelEmergencial(), new RestricaoLayout(0, 0, 2, true, false));
		this.addInputField(getTabDetalhes(), new RestricaoLayout(1, 0, 2, 1, true, true));
		this.addInputField(getPanelPrecoTotal(), new RestricaoLayout(2,1,1,true,false));
		carregarFieldFuncionario();
	}

	public JPanel getPanelEmergencial() {
		JPanel panelEmergencial = new JFormGroup("Ordem de Serviço Emergencial");
		panelEmergencial.setLayout(new GridBagLayout());
		fieldEmergencialNome = new JTextField();
		fieldEmergencialNome.setEditable(false);
		panelEmergencial.add(new TitledPanel("Código", getPanelCodigoEmergencial()), new RestricaoLayout(0, 0, false, false));
		panelEmergencial.add(new TitledPanel("Cliente / Evento", fieldEmergencialNome), new RestricaoLayout(0, 1, 5, true, false));

		return panelEmergencial;
	}
	
	public JPanel getPanelCliente() {
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(new GridBagLayout());
		fieldClienteID = new JIntField();
		fieldClienteRazao = new JTextField();
		fieldClienteCnpj = new JCnpjField();
		fieldClienteTelefone = new JFoneField();
		fieldClienteRamal = new JTextField();
		fieldClienteContato = new JTextField();
		fieldClienteCelular = new JFoneField();

		fieldClienteCnpj.setEditable(false);
		fieldClienteTelefone.setEditable(false);
		fieldClienteRamal.setEditable(false);
		fieldClienteContato.setEditable(false);
		fieldClienteCelular.setEditable(false);

		panelCliente.add(new TitledPanel("Código", getPanelCodigoCliente()), new RestricaoLayout(0, 0, false, false));
		panelCliente.add(new TitledPanel("Razão Social", getPanelNomeCliente()), new RestricaoLayout(0, 1, 5, true, false));
		panelCliente.add(new TitledPanel("CNPJ", fieldClienteCnpj), new RestricaoLayout(0, 6, true, false));

		panelCliente.add(new TitledPanel("Telefone", fieldClienteTelefone), new RestricaoLayout(1, 0, 2, true, false));
		panelCliente.add(new TitledPanel("Ramal", fieldClienteRamal), new RestricaoLayout(1, 2, false, false));
		panelCliente.add(new TitledPanel("Contato", fieldClienteContato), new RestricaoLayout(1, 3, 2, true, false));
		panelCliente.add(new TitledPanel("Celular", fieldClienteCelular), new RestricaoLayout(1, 5, 2, true, false));
		panelCliente.add(new JPanel(), new RestricaoLayout(2,0,7,1,true,true));

		return panelCliente;
	}
	
	public JPanel getPanelNomeCliente() {
		JPanel panelNomeCliente = new JPanel();
		panelNomeCliente.setLayout(new BoxLayout(panelNomeCliente, BoxLayout.LINE_AXIS));
		JButton botaoCliente = getBotaoAbrirCliente();
		botaoCliente.setPreferredSize(new Dimension(30,18));
		fieldClienteRazao = new JTextField();
		fieldClienteRazao.setEditable(false);
		panelNomeCliente.add(fieldClienteRazao);
		panelNomeCliente.add(botaoCliente);
		return panelNomeCliente;
	}
	
	public JButton getBotaoAbrirCliente() {
		JButton botaoAbrirCliente = new JButton("", new ImageIcon(getClass().getResource("/images/lente_24.png")));
		botaoAbrirCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoAbrirCliente.setPreferredSize(new Dimension(20,20));

		botaoAbrirCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(cliente != null) {
							FormCliente telaCliente = (FormCliente) WindowFactory.createTelaCliente(Facade.getInstance().getTelaPrincipal().getDesktop());
							telaCliente.loadForm(cliente);
							telaCliente.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(FormOrdemServicoSemEquipamento.this, "Nenhum cliente selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
							
					}
				}	
		);
		return botaoAbrirCliente;
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
	
	public JPanel getPanelCodigoEmergencial() {
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(new BorderLayout());
		JButton botaoCliente = getBotaoProcurarEmergencial();
		panelCliente.add(botaoCliente, BorderLayout.CENTER);
		return panelCliente;
	}

	private JTabbedPane getTabDetalhes() {
		tabDetalhes = new JTabbedPane();
		tabDetalhes.addTab("Dados do Cliente", getPanelCliente());
		tabDetalhes.addTab("Dados do Evento", getPanelDadosEvento());
		tabDetalhes.addTab("Local do Evento", getPanelEndereco());		
		tabDetalhes.addTab("Recursos Solicitados", getPanelRecursos());
		tabDetalhes.addTab("Equipamentos Enviados", getTabelaEquipamentosRegistrados() );
		return tabDetalhes;
	}
	
	private JComponent getTabelaEquipamentosRegistrados() {
		JScrollPane panelTabela = new JScrollPane();
		modelEquipamentoResgistrado = new EquipamentosRegistradosTableModel();
		tabelaEquipamentosRegistrados = new JTable(modelEquipamentoResgistrado);
		tabelaEquipamentosRegistrados.setFillsViewportHeight(true);
		tabelaEquipamentosRegistrados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumn column = null;
		int columnWidth[] = modelEquipamentoResgistrado.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			column = tabelaEquipamentosRegistrados.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
		}	

		panelTabela.setViewportView(tabelaEquipamentosRegistrados);
		return panelTabela;
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
		
		
		fieldEnderecoLogradouro.setEnabled(false);
		fieldEnderecoNumero.setEnabled(false);
		fieldEnderecoComplemento.setEnabled(false);
		fieldEnderecoCEP.setEnabled(false);
		fieldEnderecoBairro.setEnabled(false);
		fieldEnderecoCidade.setEnabled(false);
		fieldEnderecoEstado.setEnabled(false);
		fieldEnderecoReferencia.setEnabled(false);
		fieldLocalNome.setEnabled(false);


		fieldObservacoesLocalEvento = new JTextArea();
		fieldObservacoesLocalEvento.setEnabled(false);
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


	public JPanel getPanelCodigoLocal() {
		JPanel panelRecurso = new JPanel();
		panelRecurso.setLayout(new BoxLayout(panelRecurso, BoxLayout.LINE_AXIS));
		fieldLocalID = new JIntField();
		panelRecurso.add(fieldLocalID);
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
		
		fieldNomeEvento.setEnabled(false);
		fieldFuncionario.setEnabled(false);
		fieldFuncionario.setEnabled(false);
		fieldDataInicio.setEnabled(false);
		fieldDataFim.setEnabled(false);
		fieldDataMontagem.setEnabled(false);
		fieldObservacoes.setEnabled(false);
		fieldStatusOS.setEnabled(false);
		
		
		carregarFieldStatus();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);

		fieldHoraInicio = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorInicio = new JSpinner.DateEditor(fieldHoraInicio, "HH:mm");
		fieldHoraInicio.setEditor(timeEditorInicio);
		fieldHoraInicio.setEnabled(false);
		
		fieldHoraFim = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorFim = new JSpinner.DateEditor(fieldHoraFim, "HH:mm");
		fieldHoraFim.setEditor(timeEditorFim);
		fieldHoraFim.setEnabled(false);
		
		fieldHoraMontagem = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditorMontagem = new JSpinner.DateEditor(fieldHoraMontagem, "HH:mm");
		fieldHoraMontagem.setEditor(timeEditorMontagem);
		fieldHoraMontagem.setEnabled(false);
		
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
		modelRecurso = new RecursosSolicitadosEmergenciaTableModel();
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());
		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());

		panelRecursos.add(new TitledPanel("Recursos Solicitados", scrollTabela), new RestricaoLayout(0,0,1,1,true,true));
		return panelRecursos;		
	}

	private JTable getTabelaRecursosSolicitados() {
		tabelaRecursosSolicitados = new JTable(modelRecurso);
		tabelaRecursosSolicitados.setSelectionBackground(Color.LIGHT_GRAY);
		tabelaRecursosSolicitados.getColumnModel().getColumn(2).setCellEditor(new JDateChooserCellEditorBounded(this));
		tabelaRecursosSolicitados.getColumnModel().getColumn(3).setCellEditor(new JDateChooserCellEditorBounded(this));
		return tabelaRecursosSolicitados;
	}

	private JPanel getPanelPrecoTotal() {
		fieldPrecoTotal = new JMoedaRealTextField();
		fieldPrecoTotal.clear();
		fieldPrecoTotal.setFont(new Font("Arial", Font.BOLD, 24));
		fieldPrecoTotal.setForeground(Color.RED);

		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));
		total.add(fieldPrecoTotal);
		return total;
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

	public JButton getBotaoProcurarCliente() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));

		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchClient teste = new DialogSearchClient(FormOrdemServicoSemEquipamento.this);
						Cliente c = teste.showDialog(FormOrdemServicoSemEquipamento.this);
						if(c != null)
							carregarCliente(c);
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	public JButton getBotaoProcurarEmergencial() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));

		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						List<OrdemServico> listaCliente;
						listaCliente = Facade.getInstance().listarOrdemServicosEmergencialAlertaAberto();
						DialogSearchOS2 teste = new DialogSearchOS2(FormOrdemServicoSemEquipamento.this, listaCliente);
						OrdemServico c = teste.showDialog(FormOrdemServicoSemEquipamento.this);
						if(c != null) {
							loadFormEmergencial(c);
							fieldEmergencialNome.setText(c.getId() + " - " + c.getCliente().getNome() + " / " + c.getNomeEvento());
						}
					}
				}	
		);
		return botaoProcurarCliente;
	}
	
	public OrdemServicoSemEquipamento newElement() {
		return new OrdemServicoSemEquipamento();
	}
	
	public void loadInputFields(OrdemServicoSemEquipamento os) {
		os.setCliente(cliente);
		os.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		os.setNomeEvento(fieldNomeEvento.getText());
		os.setDataInicio(fieldDataInicio.getDate());
		os.setDataFim(fieldDataFim.getDate());
		os.setDataMontagem(fieldDataMontagem.getDate());
		
		os.setHoraInicio((Date) fieldHoraInicio.getValue());
		os.setHoraFim((Date) fieldHoraFim.getValue());
		os.setHoraMontagem((Date) fieldHoraMontagem.getValue());
		if(emergencial != null) {
			os.setOrdemServicoEmergencial(emergencial);
			os.setObservacoes("Referente à Ordem de Serviço Emergencial: " + os.getOrdemServicoEmergencial().getId());
			os.setObservacoesFinanceiras("Referente à Ordem de Serviço Emergencial: " + os.getOrdemServicoEmergencial().getId());
		}
				
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
		
		os.setPreco(fieldPrecoTotal.getValor());
		os.setDesconto(BigDecimal.ZERO);
		
		
	}

	protected void loadForm(OrdemServicoSemEquipamento ose) {
		ordemServ = ose;
		loadFormEmergencial(ose.getOrdemServicoEmergencial());
		fieldPrecoTotal.setValor(ose.getPreco());
		fieldEmergencialNome.setText(ose.getOrdemServicoEmergencial().getId() + " - " + ose.getOrdemServicoEmergencial().getCliente().getNome() + " / " +  ose.getOrdemServicoEmergencial().getNomeEvento());
		if(ose.getCliente().getId() != ose.getOrdemServicoEmergencial().getCliente().getId())
			carregarCliente(ose.getCliente());	
		desabilitarBotaoAdicionar();
	}
	
	protected void loadFormEmergencial(OrdemServico rec) {
		carregarCliente(rec.getCliente());
		modelRecurso.removeAllElements();
		for(RecursoSolicitado r: rec.getRecursoSolicitado()) {
			modelRecurso.addElement(r);
		}
		carregarLocal(new Local(rec.getLocal()));
		carregarTabelaEquipamentosRegistrados(rec);
		
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
		emergencial = rec;
	}
	
	private void carregarTabelaEquipamentosRegistrados(OrdemServico selected) {
		modelEquipamentoResgistrado.removeAllElements();
		for(EquipamentoEnviado r: selected.getEquipamentoEnviado()) {
			modelEquipamentoResgistrado.addElement(r);
		}
	}	

	protected void clear() {
		limparCliente();
		limparLocal();
		fieldEmergencialNome.setText("");
		modelRecurso.removeAllElements();
		fieldObservacoes.setText("");
		fieldNomeEvento.setText("");
		fieldDataInicio.setDate(null);
		fieldDataFim.setDate(null);
		fieldDataMontagem.setDate(null);
		
		
		fieldHoraInicio.setValue(horaInicial);
		fieldHoraFim.setValue(horaInicial);
		fieldHoraMontagem.setValue(horaInicial);
		
		fieldPrecoTotal.clear();
		ordemServ = null;
		emergencial = null;
		habilitarBotaoAdicionar();
		modelEquipamentoResgistrado.removeAllElements();
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(emergencial == null) {
			test = false;
			error += "\nSelecione uma OS Emergencial";
		}
		if(cliente == null) {
			test = false;
			error += "\nSelecione o Cliente";
		}
		if(fieldPrecoTotal.getValor().compareTo(new BigDecimal(0)) <= 0) {
			test = false;
			error += "\nInforme o valor da Ordem de Serviço";
		}
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<OrdemServicoSemEquipamento> listAll() {
		List<OrdemServicoSemEquipamento> listaOrdemServico;
		listaOrdemServico = Facade.getInstance().listarOrdemServicoSemEquipamento();
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


	public boolean print(OrdemServicoSemEquipamento current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(current != null) {
			hm.put("id", current.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection();
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordemServicoSemEquipamento.jasper");  
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
		fieldStatusOS.setSelectedItem(StatusOS.OS_SEM_EQUIPAMENTO);
	}

}
