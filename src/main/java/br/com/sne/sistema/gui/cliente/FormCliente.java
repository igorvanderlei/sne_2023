package br.com.sne.sistema.gui.cliente;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusCliente;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.BordedPanel;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.components.ToggleStatus;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormCliente extends DefaultForm<Cliente, ClienteTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JTextField fieldCNPJ;
	private JTextField fieldEmail;
	private JComboBox fieldFuncionario;
	private JComboBox fieldStatus;
	private JTextField fieldTelefone;
	private JTextField fieldRamal;
	private JTextField fieldContato;
	private JTextField fieldCelular;
	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;
	private JDateChooser fieldDataUltimaAlteracao;
	private JTextArea fieldObservacoes;
	private JTextArea fieldHistoricoObservacoes;
	
	private JTextField fieldBanco1;
	private JTextField fieldAgencia1;
	private JTextField fieldConta1;

	private JTextField fieldBanco2;
	private JTextField fieldConta2;
	private JTextField fieldAgencia2;
	
	private JPanel jPanelStatus; // Ativo, pendente recusado inativo
	private JToggleButton botaoStatusAtivo;
	private JToggleButton botaoStatusPendente;
	private JToggleButton botaoStatusRecusado;
	private JToggleButton botaoStatusInativo;
	
	
	private JRadioButton fieldTipoFisica;
	private JRadioButton fieldTipoJuridica;

	private Cliente cliente;

	public FormCliente() {
		super(new ClienteTableModel(), "/images/icon_cliente_18.png", "Clientes");
	}

	public boolean save(Cliente current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarCliente(current);
			cliente = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Cliente já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}


	public boolean print(Cliente current){
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(cliente != null){
			hm.put("id",  cliente.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			System.out.println(cliente.getId());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/cliente.jasper");  
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
				JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
				JasperViewer.viewReport(impressao,false);
				c.close();
			} catch (JRException e) {
				e.printStackTrace();
			} catch(JRRuntimeException jrr) {
				System.out.println("JRR2");
				System.out.println("JRR" + jrr.getMessage());
				System.out.println("JRR" + jrr.getMessageKey());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um Cliente para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}

	public boolean update(Cliente current) {
		boolean s = true;
		current.setDataUltimaAlteracao(new Date());
		Facade.getInstance().atualizarCliente(current);
		return s;
	}

	public boolean remove(Cliente current) {
		boolean test = false;
		try {
			Facade.getInstance().removerCliente(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Cliente. Verifique se existem Recursos cadastrados neste de Cliente antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome  = new JTextField();
		fieldEmail = new JTextField();
		fieldCNPJ  = new JCnpjField();
		fieldFuncionario = new JComboBox();
		fieldStatus = new JComboBox();
		fieldTelefone = new JFoneField();
		fieldRamal = new JTextField();
		fieldContato = new JTextField();
		fieldCelular = new JFoneField();
		fieldDataUltimaAlteracao = new JDateChooser();
		fieldObservacoes = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		
		fieldHistoricoObservacoes = new JTextArea();
		fieldHistoricoObservacoes.setEditable(false);
		JScrollPane scrollHistoricoObservacoes = new JScrollPane();
		scrollHistoricoObservacoes.setViewportView(fieldHistoricoObservacoes);
		
		fieldID.setEditable(false);
		
		fieldDataUltimaAlteracao.setDate(new Date());
		fieldDataUltimaAlteracao.setMinSelectableDate(new Date());
		fieldDataUltimaAlteracao.setEnabled(false);
		
		ButtonGroup bgroup = new ButtonGroup();
		fieldTipoFisica = new JRadioButton("Física");
		fieldTipoJuridica = new JRadioButton("Jurídica");
		bgroup.add(fieldTipoFisica);
		bgroup.add(fieldTipoJuridica);
		fieldTipoJuridica.setSelected(true);
		
		JPanel painelTipo = new JPanel();
		painelTipo.setLayout(new GridLayout(1,2));
		painelTipo.add(fieldTipoJuridica);
		painelTipo.add(fieldTipoFisica);
		
		fieldTipoFisica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ajustarFormulario();
			}
		});
		fieldTipoJuridica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ajustarFormulario();
			}
		});

		carregarFieldStatus();		
		carregarFieldFuncionario();

		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Tipo de Cliente", painelTipo), new RestricaoLayout(0, 1, 1, true, false));
		
	
		this.addInputField(new TitledPanel("Razão Social/Nome", fieldNome), new RestricaoLayout(0,2,4, true, false));
		this.addInputField(new TitledPanel("CNPJ/CPF", fieldCNPJ), new RestricaoLayout(0, 6, 1, true, false));
		this.addInputField(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayout(0, 7, 1, true, false));
		this.addInputField(new TitledPanel("Data Ultima Alteração", fieldDataUltimaAlteracao), new RestricaoLayout(0, 8, 1, true, false));		
		this.addInputField(new TitledPanel("Status", fieldStatus), new RestricaoLayout(1, 0, 2, true, false));
		this.addInputField(new TitledPanel("Telefone", fieldTelefone), new RestricaoLayout(1, 2, 2, true, false));
		this.addInputField(new TitledPanel("Ramal", fieldRamal), new RestricaoLayout(1, 4, true, false));
		this.addInputField(new TitledPanel("Celular", fieldCelular), new RestricaoLayout(1, 5, 2, true, false));
		this.addInputField(new TitledPanel("Contato", fieldContato), new RestricaoLayout(1, 7, true, false));
		this.addInputField(new TitledPanel("Email", fieldEmail), new RestricaoLayout(1, 8, true, false));
		this.addInputField(getPanelEndereco(), new RestricaoLayout(2, 0, 9, true, false));
		this.addInputField(getPanelDadosBancarios1(), new RestricaoLayout(3, 0, 9, true, false));
		this.addInputField(getPanelDadosBancarios2(), new RestricaoLayout(4, 0, 9, true, false));

		this.addInputField(new TitledPanel("Observaçoes", scrollObservacoes), new RestricaoLayout(5, 0, 4, 1, true, true));
		this.addInputField(new TitledPanel("Histórico de Observações", scrollHistoricoObservacoes), new RestricaoLayout(5, 4, 5, 1, true, true));
		

		this.addStatusTab("Status", getJPanelTipo());
		verificarPermissao();
	}
	
	private void ajustarFormulario() {
		if(fieldNome!= null) {
			if(fieldTipoJuridica.isSelected()) {
				((JCnpjField)fieldCNPJ).setMaskCnpj();
			} else {
				((JCnpjField)fieldCNPJ).setMaskCpf();
			}
		}
	}

	private void verificarPermissao() {
		TipoUsuario tipoUsuarioLogado = Facade.getInstance().getUsuarioLogado().getTipoUsuario();
		if(tipoUsuarioLogado.getPermissao().contains(permission.IMPRIMIR_CLIENTE)) {
			habilitarBotaoImprimir();
		} else {
			desabilitarBotaoImprimir();
		}
		if(tipoUsuarioLogado.getPermissao().contains(permission.ALTERAR_VENDEDOR)) {
			fieldFuncionario.setEnabled(true);
		} else {
			fieldFuncionario.setEnabled(false);
		}
		if(tipoUsuarioLogado.getPermissao().contains(permission.ALTERAR_STATUS_CLIENTE)) {
			fieldStatus.setEnabled(true);
		} else {
			fieldStatus.setEnabled(false);
		}
	}

	private JPanel getPanelEndereco () {
		JPanel endereco = new JFormGroup("Endereço");
		endereco.setLayout(new GridBagLayout());

		fieldEnderecoLogradouro = new JTextField();
		fieldEnderecoNumero = new JTextField();
		fieldEnderecoComplemento = new JTextField();
		fieldEnderecoCEP = new JCepField();
		fieldEnderecoBairro = new JTextField();
		fieldEnderecoCidade = new JTextField();
		fieldEnderecoEstado = new JComboEstado();
		fieldEnderecoReferencia = new JTextField();

		endereco.add(new TitledPanel("Logradouro", fieldEnderecoLogradouro), new RestricaoLayout(0,0, 2, true, false));
		endereco.add(new TitledPanel("Numero", fieldEnderecoNumero), new RestricaoLayout(0,2, true, false));
		endereco.add(new TitledPanel("Complemento", fieldEnderecoComplemento), new RestricaoLayout(0,3, 1, true, false));
		endereco.add(new TitledPanel("CEP", fieldEnderecoCEP), new RestricaoLayout(0,4, 1, true, false));
		endereco.add(new TitledPanel("Bairro", fieldEnderecoBairro), new RestricaoLayout(1,0, 1, true, false));
		endereco.add(new TitledPanel("Cidade", fieldEnderecoCidade), new RestricaoLayout(1,1, 1, true, false));
		endereco.add(new TitledPanel("Estado", fieldEnderecoEstado), new RestricaoLayout(1,2, 1, true, false));
		endereco.add(new TitledPanel("Ponto de Referência", fieldEnderecoReferencia), new RestricaoLayout(1,3, 2, true, false));

		return endereco;
	}

	private JPanel getJPanelTipo() {
		if (jPanelStatus == null) {
			jPanelStatus = new JPanel();
			jPanelStatus.setLayout(new BoxLayout(getJPanelTipo(), BoxLayout.X_AXIS));
			jPanelStatus.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			jPanelStatus.setName("jPanelContemCampos");

			botaoStatusAtivo = new ToggleStatus("Ativo", "");
			botaoStatusAtivo.setIcon(new ImageIcon(getClass().getResource("/images/ClienteAtivo.png")));
			botaoStatusAtivo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			botaoStatusPendente = new ToggleStatus("Pendente", "");
			botaoStatusPendente.setIcon(new ImageIcon(getClass().getResource("/images/ClientePendente.png")));
			botaoStatusPendente.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			botaoStatusRecusado = new ToggleStatus("Recusado", "" );
			botaoStatusRecusado.setIcon(new ImageIcon(getClass().getResource("/images/ClienteRecusado.png")));
			botaoStatusRecusado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			botaoStatusInativo = new ToggleStatus("Inativo", "" );
			botaoStatusInativo.setIcon(new ImageIcon(getClass().getResource("/images/ClienteInativo.png")));
			botaoStatusInativo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					carregarTabela();
				}
			});

			jPanelStatus.add(new BordedPanel(botaoStatusAtivo), null);
			jPanelStatus.add(new BordedPanel(botaoStatusPendente), null);
			jPanelStatus.add(new BordedPanel(botaoStatusRecusado), null);
			jPanelStatus.add(new BordedPanel(botaoStatusInativo), null);
		}
		return jPanelStatus;
	}

	private void carregarFieldStatus() {
		fieldStatus.removeAllItems();
		fieldStatus.addItem("");
		for(StatusCliente st : StatusCliente.values())
			fieldStatus.addItem(st);
		fieldStatus.setSelectedItem(StatusCliente.PENDENTE);
	}

	private void carregarFieldFuncionario() {
		fieldFuncionario.removeAllItems();
		List<Funcionario> lista = Facade.getInstance().listarFuncionarios();
		fieldFuncionario.addItem(" ");
		for(Funcionario rec: lista)
			fieldFuncionario.addItem(rec);

		fieldFuncionario.setSelectedItem((Funcionario) Facade.getInstance().getUsuarioLogado().getFuncionario());
	}

	public void setVisible(boolean aFlag) {
		if (fieldStatus != null) {
			carregarFieldStatus();
			carregarFieldFuncionario();
			verificarPermissao();
			super.setVisible(aFlag);
		}
	}
	
	private JPanel getPanelDadosBancarios1 () {
		JPanel dadosBancarios = new JFormGroup("Dados Bancários");
		dadosBancarios.setLayout(new GridBagLayout());

		fieldBanco1 = new JTextField();
		fieldAgencia1 = new JTextField();
		fieldConta1 = new JTextField();
		
		dadosBancarios.add(new TitledPanel("Banco", fieldBanco1), new RestricaoLayout(0,0,1, true, false));
		dadosBancarios.add(new TitledPanel("Agência", fieldAgencia1), new RestricaoLayout(0,1,1, true, false));
		dadosBancarios.add(new TitledPanel("Conta", fieldConta1), new RestricaoLayout(0,2,1,	 true, false));
		
		return dadosBancarios;
	}
	
	private JPanel getPanelDadosBancarios2 () {
		JPanel dadosBancarios = new JFormGroup("Dados Bancários (Opcional)");
		dadosBancarios.setLayout(new GridBagLayout());

		fieldBanco2 = new JTextField();
		fieldAgencia2 = new JTextField();
		fieldConta2 = new JTextField();
		
		dadosBancarios.add(new TitledPanel("Banco", fieldBanco2), new RestricaoLayout(0,0,1, true, false));
		dadosBancarios.add(new TitledPanel("Agência", fieldAgencia2), new RestricaoLayout(0,1,1, true, false));
		dadosBancarios.add(new TitledPanel("Conta", fieldConta2), new RestricaoLayout(0,2,1, true, false));
		
		return dadosBancarios;
	}

	public Cliente newElement() {
		return new Cliente();
	}



	public void loadInputFields(Cliente cliente) {

		String nome = fieldNome.getText();
		String cnpj = fieldCNPJ.getText();
		String email = fieldEmail.getText();
		Funcionario funcionario = null;
		if(fieldFuncionario.getSelectedItem() instanceof Funcionario)
			funcionario = (Funcionario) fieldFuncionario.getSelectedItem();
		
		StatusCliente status = (StatusCliente) fieldStatus.getSelectedItem();
		String telefone = fieldTelefone.getText();
		String ramal = fieldRamal.getText();
		String contato = fieldContato.getText();
		String celular = fieldCelular.getText();
		String logradouro = fieldEnderecoLogradouro.getText();
		String numero = fieldEnderecoNumero.getText();
		String complemento = fieldEnderecoComplemento.getText();
		String cep = fieldEnderecoCEP.getText();
		String bairro = fieldEnderecoBairro.getText();
		String cidade = fieldEnderecoCidade.getText();
		String estado = (String) fieldEnderecoEstado.getSelectedItem();
		String referencia = fieldEnderecoReferencia.getText();
		String observacoes = fieldObservacoes.getText();
		String tipo = (fieldTipoFisica.isSelected())?"F":"J";

		Date ultimaAlteracao;
		if(fieldDataUltimaAlteracao.getDate()!=null ){
			ultimaAlteracao = fieldDataUltimaAlteracao.getDate();
		}else{
			ultimaAlteracao = new Date();
		}
		cliente.setNome(nome);
		cliente.setCnpj(cnpj);
		cliente.setEmail(email);
		cliente.setFuncionario(funcionario);
		cliente.setStatus(status);
		cliente.setFone(telefone);
		cliente.setRamal(ramal);
		cliente.setContato(contato);
		cliente.setCelular(celular);
		
		String banco1 = fieldBanco1.getText();
		String agencia1 = fieldAgencia1.getText();
		String conta1 = fieldConta1.getText();
		
		DadosBancarios dadosBancarios1 = new DadosBancarios(banco1,agencia1,conta1);
		
		String banco2 = fieldBanco2.getText();
		String agencia2 = fieldAgencia2.getText();
		String conta2 = fieldConta2.getText();
		DadosBancarios dadosBancarios2 = new DadosBancarios(banco2,agencia2,conta2);
		cliente.setDadosBancarios2(dadosBancarios2);

		
		cliente.setDadosBancarios1(dadosBancarios1);
		
		if(!observacoes.trim().equals("")) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			observacoes = "---- " + Facade.getInstance().getUsuarioLogado().getFuncionario().getNome() + " ("+  formato.format(new Date()) + "): \n" + observacoes + "\n\n";
			if(cliente.getObservacoes() != null)
				cliente.setObservacoes(observacoes + cliente.getObservacoes());
			else
				cliente.setObservacoes(observacoes);
		}
		
		cliente.setDataUltimaAlteracao(ultimaAlteracao);
		Endereco endereco= (cliente.getEndereco()==null)?new Endereco():cliente.getEndereco();
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setPontoReferencia(referencia);
		cliente.setEndereco(endereco);
		cliente.setTipo(tipo);
	}

	protected void clear() {
		fieldBanco1.setText("");
		fieldBanco2.setText("");
		fieldAgencia1.setText("");
		fieldAgencia2.setText("");
		fieldConta1.setText("");
		fieldConta2.setText("");
		fieldID.setText("");
		fieldNome.setText("");
		fieldEmail.setText("");
		fieldCNPJ.setText("00000000000000");
		fieldFuncionario.setSelectedIndex(0);
		fieldStatus.setSelectedIndex(0);
		fieldTelefone.setText("");
		fieldRamal.setText("");
		fieldContato.setText("");
		fieldCelular.setText("");
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("00000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");
		fieldObservacoes.setText("");
		fieldHistoricoObservacoes.setText("");
		fieldDataUltimaAlteracao.setDate(null);
		fieldTipoJuridica.setSelected(true);
		ajustarFormulario();
		cliente = null;
	}

	public void loadForm(Cliente cli) {
		if(cli.getTipo().equals("F")) {
			fieldTipoFisica.setSelected(true);
		} else {
			fieldTipoJuridica.setSelected(true);
		}
		ajustarFormulario();
		
		if(cli.getDadosBancarios1() != null) {
			fieldBanco1.setText(cli.getDadosBancarios1().getBanco());
			fieldAgencia1.setText(cli.getDadosBancarios1().getAgencia());
			fieldConta1.setText(cli.getDadosBancarios1().getConta());

		}
		if(cli.getDadosBancarios2() != null) {
			fieldBanco2.setText(cli.getDadosBancarios2().getBanco());
			fieldAgencia2.setText(cli.getDadosBancarios2().getAgencia());
			fieldConta2.setText(cli.getDadosBancarios2().getConta());

		}
		
		fieldID.setText("" + cli.getId());
		fieldNome.setText(cli.getNome());
		fieldEmail.setText(cli.getEmail());
		fieldCNPJ.setText(cli.getCnpj());
		fieldFuncionario.setSelectedItem(cli.getFuncionario());
		fieldStatus.setSelectedItem(cli.getStatus());
		fieldTelefone.setText(cli.getFone());
		fieldRamal.setText(cli.getRamal());
		fieldContato.setText(cli.getContato());
		fieldCelular.setText(cli.getCelular());
		fieldEnderecoLogradouro.setText(cli.getEndereco().getLogradouro());
		fieldEnderecoNumero.setText(cli.getEndereco().getNumero());
		fieldEnderecoComplemento.setText(cli.getEndereco().getComplemento());
		fieldEnderecoCEP.setText(cli.getEndereco().getCep());
		fieldEnderecoBairro.setText(cli.getEndereco().getBairro());
		fieldEnderecoCidade.setText(cli.getEndereco().getCidade());
		fieldEnderecoEstado.setSelectedItem(cli.getEndereco().getEstado());
		fieldEnderecoReferencia.setText(cli.getEndereco().getPontoReferencia());
		fieldHistoricoObservacoes.setText(cli.getObservacoes());
		fieldDataUltimaAlteracao.setDate(cli.getDataUltimaAlteracao());
		cliente = cli;
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Cliente";
		}
		if(fieldCelular.getText().length() <= 0 && fieldTelefone.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o telefone ou celular do Cliente";
		}
		if(fieldContato.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o contato do Cliente";
		}

		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Cliente> listAll() {
		List<Cliente> lista;
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Cliente>();
		}
		
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_CLIENTES)){
			lista = Facade.getInstance().listarClientes(campo, texto);
		} else {
			lista = Facade.getInstance().listarClientes(campo, texto, Facade.getInstance().getUsuarioLogado().getFuncionario());
		}

		boolean ativo = botaoStatusAtivo.isSelected();
		boolean pendente = botaoStatusPendente.isSelected();
		boolean recusado = botaoStatusRecusado.isSelected();
		boolean inativo = botaoStatusInativo.isSelected();

		List<Cliente> resp = new ArrayList<Cliente>();

		for(Cliente c : lista) {
			if((c.getStatus() == StatusCliente.ATIVO && ativo) ||
					(c.getStatus() == StatusCliente.PENDENTE && pendente) ||
					(c.getStatus() == StatusCliente.RECUSADO && recusado) ||
					(c.getStatus() == StatusCliente.INATIVO && inativo)	) {
				resp.add(c);
			}
		}
		return resp;
	}

}
