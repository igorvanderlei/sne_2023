package br.com.sne.sistema.gui.comodato;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.Comodato;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusComodato;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.FormCliente;
import br.com.sne.sistema.gui.local.DialogSearchLocal;
import br.com.sne.sistema.gui.orcamento.AmbienteTableModel;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.AutoCompleteCombo;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;
import br.com.sne.sistema.gui.util.form.DialogSearchRecurso;
import br.com.sne.sistema.gui.util.form.FormIntervalar;
import br.com.sne.sistema.teste.SelectedItemListener;

public class FormComodato extends DefaultForm<Comodato, ComodatoTableModel> implements FormIntervalar {
	private static final long serialVersionUID = 1L;

	private Cliente cliente = null;
	private Recurso recurso = null;

	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;
	private JTextField fieldClienteCnpj;
	private JTextField fieldClienteTelefone;
	private JTextField fieldClienteRamal;
	private JTextField fieldClienteContato;
	private JTextField fieldClienteCelular;
	
	
	private JTextField fieldResponsavelCliente;
	private JTextField fieldTelefoneResponsavel;
	private JComboBox fieldStatusOS;	

	private JDateChooser fieldDataInicio;
	private JDateChooser fieldDataFim;
	
	private JTextArea fieldObservacoes;
	private JTextArea fieldObservacoesLocalEvento;

	private AutoCompleteCombo<Recurso> fieldRecursoNome;
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
	
	private JPopupMenu menuTabela;

	private JTabbedPane tabDetalhes;
	private JTabbedPane tabAmbientes;
	
	private JTextField fieldAmbienteNome;
	private JDateChooser fieldAmbienteDataInicio;
	private JDateChooser fieldAmbienteDataFim;
	private JTable tabelaAmbiente;
	private AmbienteTableModel modelAmbiente;
	private JButton botaoAdicionarAmbiente;
	
	private JButton addRecursoButton;
	
	private HashMap<String, PanelSala> paineisRecurso;


	private Comodato ordemServ = null;

	Date horaInicial = new Date(1000*60*3*60);
	
	public FormComodato() {
		super(new ComodatoTableModel(), "/images/icon_comodato_18.png", "Comodato");
		clear();
	}

	public boolean save(Comodato current) {
		boolean s = false;
		try {
			if(current.getDataInicio().compareTo(new Date()) < 0){
				int resp = JOptionPane.showConfirmDialog(null,"A data de início do Comodato é anterior a data atual.\nTem certeza que deseja cadastrar um Comodato com a data anterior?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
				if(resp != JOptionPane.OK_OPTION){
					return false;
				}
			}
			
			if(ordemServ != null) {
				current = new Comodato(current);
				loadInputFields(current);
				current.setStatus(StatusComodato.PENDENTE);
				for(AmbienteEvento amb: current.getAmbientes()){
					amb.setId(0);
				}
				for(RecursoSolicitado recs: current.getRecursoSolicitado()) {
					recs.setId(0);
				}
				current.getLocal().setId(0);
			}
			current.setStatus(StatusComodato.PENDENTE);

			current.setDataCadastro(new Date());
			
			Facade.getInstance().salvarComodato(current);
			

			
			ordemServ = current;
			
			int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir o Comodato ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
			if( resp == JOptionPane.OK_OPTION) {
				print(current);
				s = false;
			} else {
				s = true;
			}
			clear();
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Comodato já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Comodato current) {
		boolean s = true;

		if(current.getStatus() == StatusComodato.CONCLUIDO) {
			JOptionPane.showMessageDialog(this, "Não é possível o modificar um Comodato Concluído." , "ERRO", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Facade.getInstance().atualizarComodato(current);
		return s;
	}

	public boolean remove(Comodato current) {
		boolean test = false;
		try {
			if(current.getStatus() == StatusComodato.PENDENTE) {
				Facade.getInstance().removerComodato(current);
				test = true;
			} else {
				JOptionPane.showMessageDialog(this, "Erro ao remover o Comodato. \n Não é possível remover um Comodato que já foi iniciado.", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Comodato. Verifique se existem Recursos cadastrados neste de Comodato antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void setVisible(boolean aFlag) {
		if (fieldRecursoNome != null) {
			super.setVisible(aFlag);
			if(aFlag) 
				fieldRecursoNome.setData(Facade.getInstance().listarRecursos());
			
		}
	}

	public void createInputFields() {
		this.addInputField(getTabDetalhes(), new RestricaoLayout(1, 0, 2, 1, true, true));
	}

	public JPanel getPanelCliente() {
		JPanel panelCliente = new JFormGroup("Dados do Cliente");
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
	
	
	private JTabbedPane getTabDetalhes() {
		tabDetalhes = new JTabbedPane();
		tabDetalhes.addTab("Dados do Comodato", getPanelDadosEvento());
		tabDetalhes.addTab("Detalhes do Local", getPanelEndereco());
		tabDetalhes.addTab("Recursos Solicitados", getPanelRecursos());
		
		tabDetalhes.addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if(tabDetalhes.getSelectedIndex() != 0) {
							if(fieldDataInicio.getDate() == null || fieldDataFim.getDate() == null) {
								tabDetalhes.setSelectedIndex(0);
								JOptionPane.showMessageDialog(null, "Preencha datas de início e fim do comodato");
								return;
							}
							limparRecurso();
							limparAmbiente();
						}
						
						if(tabDetalhes.getSelectedIndex() == 2) {
							if(modelAmbiente.getAmbientes().size() == 0){
								tabDetalhes.setSelectedIndex(1);
								JOptionPane.showMessageDialog(null, "Insira pelo menos um ambiente para o comodato");
								return;
							}
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
		
		endereco.add(new TitledPanel("Ambientes do Comodato(Auditório, Teatro, Sala, etc...)", getPanelAmbientes()), new RestricaoLayout(3,0,6,2, true, true));
		
		endereco.add(new TitledPanel("Observações do Local", scrollObservacoes), new RestricaoLayout(5,0,6,1, true, true));

		return endereco;
	}
	
	public JPanel getPanelAmbientes() {
		JPanel panelAmbiente = new JPanel();
		
		fieldAmbienteNome = new JTextField();
		fieldAmbienteDataInicio = new JDateChooser();
		fieldAmbienteDataFim = new JDateChooser();
		
		modelAmbiente = new AmbienteTableModel();
		tabelaAmbiente = new JTable(modelAmbiente);
		
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		menuTabela.add(getMenuAlterarNomeAmbiente());
		menuTabela.add(getMenuDuplicarAmbiente());
		
		tabelaAmbiente.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaAmbiente.getSelectedRow() >= 0 )
								menuTabela.show(tabelaAmbiente, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Ambiente para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		
		JScrollPane scrollAmbiente = new JScrollPane();
		scrollAmbiente.setViewportView(tabelaAmbiente);
		
		botaoAdicionarAmbiente = new JButton("Adicionar Ambiente");
		
		botaoAdicionarAmbiente.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(fieldAmbienteNome.getText().length() < 3) {
					JOptionPane.showMessageDialog(null, "Informe o nome do ambiente");
					return;
				}
				
				if(paineisRecurso.get(fieldAmbienteNome.getText()) == null) {
					AmbienteEvento amb = new AmbienteEvento(fieldAmbienteNome.getText(), fieldAmbienteDataInicio.getDate(), fieldAmbienteDataFim.getDate());
					adicionarAmbiente(amb);
					limparAmbiente();
				} else {
					JOptionPane.showMessageDialog(null, "O nome do ambiente já está cadastrado");
				}
				

			}
		}
		);
		
		
		panelAmbiente.setLayout(new GridBagLayout());
		panelAmbiente.add(new TitledPanel("Nome", fieldAmbienteNome), new RestricaoLayout(0,0,1,true,false));
		panelAmbiente.add(new TitledPanel("Data Início", fieldAmbienteDataInicio), new RestricaoLayout(0,1,1,true,false));
		panelAmbiente.add(new TitledPanel("Data Fim", fieldAmbienteDataFim), new RestricaoLayout(0,2,1,true,false));
		panelAmbiente.add(new TitledPanel("", botaoAdicionarAmbiente), new RestricaoLayout(0,3,false,false));
		
		panelAmbiente.add(new TitledPanel("", scrollAmbiente), new RestricaoLayout(1,0,4,1,true,true));
		return panelAmbiente;
	}
	
	private JMenuItem getMenuDuplicarAmbiente() {
		JMenuItem menuAlterarNome = new JMenuItem("Duplicar Ambiente");
		menuAlterarNome.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						PanelSala ps = paineisRecurso.get(ambienteAlterar.getNome());
						
						String nome = JOptionPane.showInputDialog(null, "Informe o do novo ambiente:", "Duplicar ambiente", JOptionPane.QUESTION_MESSAGE);
						
						if(nome.length() > 3) {
							AmbienteEvento amb = new AmbienteEvento(nome, ambienteAlterar.getDataInicio(), ambienteAlterar.getDataFim());
							if(paineisRecurso.get(nome) == null) {
								if(ps != null) {
									//paineisRecurso.remove(ambienteAlterar.getNome());
									//tabAmbientes.remove(ps);
									adicionarAmbiente(amb);
									PanelSala ps2 = paineisRecurso.get(amb.getNome());
									for(RecursoSolicitado rs : ps.getRecursosSolicitados()) {
										RecursoSolicitado rs2 = new RecursoSolicitado();
										rs2.setDataFim(rs.getDataFim());
										rs2.setDataInicio(rs.getDataInicio());
										rs2.setRecurso(rs.getRecurso());
										rs2.setQuantidade(rs.getQuantidade());
										rs2.setPrecoUnitario(rs.getPrecoUnitario());
										rs2.setDeletado(rs.isDeletado());
										ps2.adicionarRecurso(rs2);
									}
									//modelAmbiente.removeRow(tabelaAmbiente.getSelectedRow());
								}
							} else {
								JOptionPane.showMessageDialog(null, "O nome do ambiente informado já está cadastrado");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Nome inválido");
						}
					}
				}		
		);
		return menuAlterarNome;
	}

	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Ambiente");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String nomeAbiente = "" + modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						PanelSala ps = paineisRecurso.get(nomeAbiente);
						if(ps != null) {
							paineisRecurso.remove(nomeAbiente);
							tabAmbientes.remove(ps);
							modelAmbiente.removeRow(tabelaAmbiente.getSelectedRow());
						}
					}
				}		
		);
		return menuRemover;
	}
	
	
	private JMenuItem getMenuAlterarNomeAmbiente() {
		JMenuItem menuAlterarNome = new JMenuItem("Alterar nome do Ambiente");
		menuAlterarNome.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						AmbienteEvento ambienteAlterar = (AmbienteEvento)  modelAmbiente.getValueAt(tabelaAmbiente.getSelectedRow(), 0);
						PanelSala ps = paineisRecurso.get(ambienteAlterar.getNome());
						
						String nome = JOptionPane.showInputDialog(null, "Informe o novo nome:", "Alterar o nome do ambiente", JOptionPane.QUESTION_MESSAGE);
						
						if(nome.length() > 3) {
							AmbienteEvento amb = new AmbienteEvento(nome, ambienteAlterar.getDataInicio(), ambienteAlterar.getDataFim());
							if(paineisRecurso.get(nome) == null) {
								if(ps != null) {
									paineisRecurso.remove(ambienteAlterar.getNome());
									tabAmbientes.remove(ps);
									adicionarAmbiente(amb);
									PanelSala ps2 = paineisRecurso.get(amb.getNome());
									for(RecursoSolicitado rs : ps.getRecursosSolicitados()) {
										ps2.adicionarRecurso(rs);
									}
									modelAmbiente.removeRow(tabelaAmbiente.getSelectedRow());
								}
							} else {
								JOptionPane.showMessageDialog(null, "O nome do ambiente informado já está cadastrado");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Nome inválido");
						}
					}
				}		
		);
		return menuAlterarNome;
	}

	private void adicionarAmbiente(AmbienteEvento amb) {
			PanelSala pn = new PanelSala(amb);
			tabAmbientes.addTab(amb.getNome(), pn);
			paineisRecurso.put(amb.getNome(), pn);
			modelAmbiente.addElement(amb);

	}
	
	private void limparAmbiente() {
		fieldAmbienteDataFim.setDate(fieldDataFim.getDate());
		fieldAmbienteDataInicio.setDate(fieldDataInicio.getDate());
		fieldAmbienteNome.setText("");
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
		fieldResponsavelCliente = new JTextField();
		fieldTelefoneResponsavel = new JTextField();
		fieldDataInicio = new JDateChooser();
		fieldDataFim = new JDateChooser();

		fieldStatusOS = new JComboBox();
		fieldStatusOS.setEnabled(false);
		carregarFieldStatus();
		
		
		JPanel painelDataHoraInicio = new JPanel();
		painelDataHoraInicio.setLayout(new GridBagLayout());
		painelDataHoraInicio.add(fieldDataInicio, new RestricaoLayout(0,0,1,true,false));
		
		JPanel painelDataHoraFim = new JPanel();
		painelDataHoraFim.setLayout(new GridBagLayout());
		painelDataHoraFim.add(fieldDataFim, new RestricaoLayout(0,0,1,true,false));
		
		fieldDataInicio.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataInicio.getDate() != null) {
							fieldDataFim.setMinSelectableDate(fieldDataInicio.getDate());
							fieldAmbienteDataInicio.setMinSelectableDate(fieldDataInicio.getDate());
							fieldAmbienteDataFim.setMinSelectableDate(fieldDataInicio.getDate());
						}
							
					}
				}
		);

		fieldDataFim.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						if(fieldDataFim.getDate() != null) {
							fieldAmbienteDataInicio.setMaxSelectableDate(fieldDataFim.getDate());
							fieldAmbienteDataFim.setMaxSelectableDate(fieldDataFim.getDate());
						}
							
					}
				}
		);
		
		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());
		
		painelDadosEvento.add(getPanelCliente(), new RestricaoLayout(0, 0, 7, true, false));

		
		painelDadosEvento.add(new TitledPanel("Data de Início", painelDataHoraInicio), new RestricaoLayout(1, 0, 2, true, false));
		painelDadosEvento.add(new TitledPanel("Data de Fim", painelDataHoraFim), new RestricaoLayout(1,2,2,true, false));
		
		painelDadosEvento.add(new TitledPanel("Responsável (Cliente)", fieldResponsavelCliente), new RestricaoLayout(1, 4, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Telefone do Responsável", fieldTelefoneResponsavel), new RestricaoLayout(1, 5, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Status", fieldStatusOS), new RestricaoLayout(1, 6, 1, true, false));

		painelDadosEvento.add(getPanelObservacoes(), new RestricaoLayout(3, 0, 7, 1, true, true));

		return painelDadosEvento;		
		
	}
	
	public JPanel getPanelObservacoes() {
		JPanel panelObservacoes = new JPanel();
		panelObservacoes.setLayout(new GridBagLayout());
		fieldObservacoes = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldObservacoes.setLineWrap(true);
		
		panelObservacoes.add(new TitledPanel("Observações Internas", scrollObservacoes), new RestricaoLayout(0, 0, 1, 1, true, true));
		return panelObservacoes;
	}


	
	public JPanel getPanelRecursos() {
		fieldRecursoNome = new AutoCompleteCombo<Recurso>();
		fieldRecursoNome.setData(Facade.getInstance().listarRecursos());
		fieldRecursoNome.addListener(new SelectedItemListener<Recurso>() {
			public void itemSelected(Recurso item) {
				carregarRecurso(item);
			}
		});

		fieldRecursoQuantidade = new JIntField();

		tabAmbientes = new JTabbedPane();
		paineisRecurso = new HashMap<String, PanelSala>();

		JPanel panelRecursos = new JPanel();
		panelRecursos.setLayout(new GridBagLayout());

		JPanel adicionar = new JFormGroup("Adicionar Recurso");
		adicionar.setLayout(new GridBagLayout());
		
		adicionar.add(new TitledPanel("Recurso", fieldRecursoNome), new RestricaoLayout(0, 0, 5, true, false));
		adicionar.add(new TitledPanel("Quant.", fieldRecursoQuantidade), new RestricaoLayout(0, 5, false, false));
		adicionar.add(new TitledPanel(" ", getBotaoAdicionarRecurso()) , new RestricaoLayout(0, 6, false, false));
		
		panelRecursos.add(adicionar, new RestricaoLayout(0,0,1,true,false));
		panelRecursos.add(tabAmbientes, new RestricaoLayout(1,0,1,1,true,true));

		limparPanelRecursos();
		return panelRecursos;		
	}

	private JButton getBotaoAdicionarRecurso() {
		addRecursoButton = new JButton("", new ImageIcon(getClass().getResource("/images/add.png")));
		addRecursoButton.setHorizontalAlignment(SwingConstants.CENTER);
		addRecursoButton.setVerticalAlignment(SwingConstants.CENTER);
		addRecursoButton.setPreferredSize(new Dimension(20,20));
		addRecursoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(recurso != null) {
					RecursoSolicitado rec = new RecursoSolicitado();
					rec.setRecurso(recurso);
					rec.setDataFim(fieldDataFim.getDate());
					rec.setDataInicio(fieldDataInicio.getDate());
					rec.setQuantidade(Integer.parseInt(fieldRecursoQuantidade.getText()));
					
					PanelSala salaSelecionada = (PanelSala) tabAmbientes.getComponent(tabAmbientes.getSelectedIndex());
					salaSelecionada.adicionarRecurso(rec);
					limparRecurso();
				} else {
					JOptionPane.showMessageDialog(FormComodato.this, "Selecione um recurso!", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return addRecursoButton;
	}

	private void limparPanelRecursos() {
		fieldRecursoNome.setText("");
		fieldStatusOS.setSelectedItem(StatusComodato.PENDENTE);
	}

	public void carregarCliente(Cliente c) {
		cliente = c;
		if(c!=null){
			fieldClienteID.setText("" + c.getId());
			fieldClienteRazao.setText(c.getNome());
			fieldClienteCnpj.setText(c.getCnpj());
			fieldClienteTelefone.setText(c.getFone());
			fieldClienteRamal.setText(c.getRamal());
			fieldClienteContato.setText(c.getContato());
			fieldClienteCelular.setText(c.getCelular());
		} else {
			limparCliente();
		}
	}

	private void carregarRecurso(Recurso r) {
		recurso = r;
		if(r!=null){
			System.out.println("Diferente de null");
			addRecursoButton.requestFocus();
		} else {
			System.out.println("Igual a null");
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
		fieldClienteCnpj.setText("");
		fieldClienteTelefone.setText("");
		fieldClienteRamal.setText("");
		fieldClienteContato.setText("");
		fieldClienteCelular.setText("");
	}

	public void limparRecurso() {
		recurso = null;
		fieldRecursoNome.setText("");
		fieldRecursoQuantidade.setText("1");
		fieldRecursoNome.requestFocus();
	}

	public JButton getBotaoProcurarCliente() {
		JButton botaoProcurarCliente = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarCliente.setPreferredSize(new Dimension(20,20));

		botaoProcurarCliente.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchClient teste = new DialogSearchClient(FormComodato.this);
						Cliente c = teste.showDialog(FormComodato.this);
						if(c != null)
							carregarCliente(c);
					}
				}	
		);
		return botaoProcurarCliente;
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
							JOptionPane.showMessageDialog(FormComodato.this, "Nenhum cliente selecionado!", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
							
					}
				}	
		);
		return botaoAbrirCliente;
	}

	public JButton getBotaoProcurarRecurso() {
		JButton botaoProcurarRecurso = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarRecurso.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarRecurso.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarRecurso.setPreferredSize(new Dimension(20,20));

		botaoProcurarRecurso.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DialogSearchRecurso teste = new DialogSearchRecurso(FormComodato.this, Facade.getInstance().listarRecursos());
						Recurso r = teste.showDialog(FormComodato.this);
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
						DialogSearchLocal teste = new DialogSearchLocal(FormComodato.this, Facade.getInstance().listarLocais());
						Local l = teste.showDialog(FormComodato.this);
						if(l != null)
							carregarLocal(l);
					}
				}	
		);
		return botaoProcurarLocal;
	}

	public Comodato newElement() {
		return new Comodato();
	}

	public void loadInputFields(Comodato os) {
		os.setStatus((StatusComodato) fieldStatusOS.getSelectedItem());
		
		os.setCliente(cliente);
		
		List<RecursoSolicitado> recursos = new ArrayList<RecursoSolicitado>();
		
		for(PanelSala pns: paineisRecurso.values()) {
			recursos.addAll(pns.getRecursosSolicitados());
		}
		
		
		os.setAmbientes(modelAmbiente.getAmbientes());
		os.setRecursoSolicitado(recursos);
		
		
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
		
		os.setResponsavelCliente(fieldResponsavelCliente.getText());
		os.setTelefoneResponsavel(fieldTelefoneResponsavel.getText());
		
		os.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		
		
		os.setDataInicio(fieldDataInicio.getDate());
		os.setDataFim(fieldDataFim.getDate());
		os.setObservacoes(fieldObservacoes.getText());
	}

	protected void loadForm(Comodato rec) {
		carregarCliente(rec.getCliente());
		modelAmbiente.removeAllElements();
		paineisRecurso.clear();
		
		while(tabAmbientes.getTabCount() > 0) {
			tabAmbientes.remove(0);
		}

		for(AmbienteEvento amb: rec.getAmbientes()) {
			adicionarAmbiente(amb);
		}
		
		for(RecursoSolicitado r: rec.getRecursoSolicitado()) {
			PanelSala ps = paineisRecurso.get(r.getAmbiente().getNome());
			if(ps != null)
				ps.adicionarRecurso(r);
		}
		
		carregarLocal(new Local(rec.getLocal()));
		
		
		fieldResponsavelCliente.setText(rec.getResponsavelCliente());
		fieldTelefoneResponsavel.setText(rec.getTelefoneResponsavel());
		
		fieldDataInicio.setDate(rec.getDataInicio());
		fieldDataFim.setDate(rec.getDataFim());
		
		fieldObservacoes.setText(rec.getObservacoes());

		fieldStatusOS.setSelectedItem(rec.getStatus());
		limparRecurso();
		
		setTitle("COMODATO - NÚMERO: " + rec.getId());
		ordemServ = rec;
	}

	protected void clear() {
		limparCliente();
		limparRecurso();
		
		limparLocal();
		modelAmbiente.removeAllElements();
		paineisRecurso.clear();
		
		while(tabAmbientes.getTabCount() > 0) {
			tabAmbientes.remove(0);
		}
		
		fieldObservacoes.setText("");
		fieldDataInicio.setDate(null);
		fieldDataFim.setDate(null);
		
		tabDetalhes.setSelectedIndex(0);

		fieldStatusOS.setSelectedItem(StatusComodato.PENDENTE);
		
		setTitle("Ordem de Serviço");
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
			error += "\nInforme a Data de Início do Comodato";			

		}
		if(fieldDataFim.getDate() == null) {
			test = false;
			error += "\nInforme a Data de Fim do Comodato";			

		}
		
		boolean possuiRecurso = false;
		for(PanelSala pns: paineisRecurso.values()) {
			if(pns.getRecursosSolicitados().size()>0) {
				possuiRecurso = true;
				break;
			}
		}
		
		if(!possuiRecurso){
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

	public List<Comodato> listAll() {
		List<Comodato> listaComodato;
		listaComodato = Facade.getInstance().listarComodatos();
		return listaComodato;
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

	public boolean print(Comodato current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(ordemServ != null) {
			hm.put("id", ordemServ.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection();
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/ordem_servico.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}

	private void carregarFieldStatus() {
		fieldStatusOS.removeAllItems();
		fieldStatusOS.addItem("");
		for(StatusComodato st : StatusComodato.values()) {
				fieldStatusOS.addItem(st);
			
		}
		fieldStatusOS.setSelectedItem(StatusComodato.PENDENTE);
	}
}
