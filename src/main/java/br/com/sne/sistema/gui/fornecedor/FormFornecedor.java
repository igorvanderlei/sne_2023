package br.com.sne.sistema.gui.fornecedor;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormFornecedor extends DefaultForm<Fornecedor, FornecedorTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JTextField fieldCNPJ;
	private JTextField fieldEmail;
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
	private JTextArea fieldObservacoes;
	private JTextField fieldCodigo;
	
	private JRadioButton rButtonCNPJ;
	private JRadioButton rButtonCPF;
	
	private Fornecedor fornecedor;

	private JTextField fieldBanco1;

	private JTextField fieldAgencia1;

	private JTextField fieldConta1;

	private JTextField fieldBanco2;

	private JTextField fieldConta2;

	private JTextField fieldAgencia2;
	
	public FormFornecedor() {
		super(new FornecedorTableModel(), "/images/icon_fornecedor_18.png", "Fornecedores");
	}
	
	public boolean save(Fornecedor current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarFornecedor(current);
			fornecedor = current;
			
			s = true;
		}
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Fornecedor já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Fornecedor current) {
		boolean s = true;
		Facade.getInstance().atualizarFornecedor(current);
		return s;
	}
	
	public boolean remove(Fornecedor current) {
		boolean test = false;
		try {
			Facade.getInstance().removerFornecedor(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Fornecedor.\nVerifique se existem Registros de Sublocação pendentes deste Fornecedor antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}
	
	private void ajustarFormulario() {
		if(fieldNome!= null) {
			if(rButtonCNPJ.isSelected()) {
				((JCnpjField)fieldCNPJ).setMaskCnpj();
			} else {
				((JCnpjField)fieldCNPJ).setMaskCpf();
			}
		}
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome  = new JTextField();
		fieldEmail = new JTextField();
		fieldCNPJ  = new JCnpjField();
		fieldTelefone = new JFoneField();
		fieldRamal = new JTextField();
		fieldContato = new JTextField();
		fieldCelular = new JFoneField();
		fieldObservacoes = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		
		ButtonGroup bgroup = new ButtonGroup();
		rButtonCNPJ = new JRadioButton("Jurídico");
		rButtonCPF = new JRadioButton("Físico");
		bgroup.add(rButtonCNPJ);
		bgroup.add(rButtonCPF);
		rButtonCNPJ.setSelected(true);
		
		JPanel painelTipo = new JPanel();
		painelTipo.setLayout(new GridLayout(1,2));
		painelTipo.add(rButtonCNPJ);
		painelTipo.add(rButtonCPF);
		
		rButtonCNPJ.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ajustarFormulario();
			}
		});
		rButtonCPF.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ajustarFormulario();
			}
		});
		
		MaskFormatter formatoCodigo;
		try {
			formatoCodigo = new MaskFormatter("###");
			formatoCodigo.setPlaceholder("000");
			fieldCodigo = new JFormattedTextField(formatoCodigo);
		} catch (ParseException e) {
			fieldCodigo = new JTextField();
		}
		
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Código", fieldCodigo), new RestricaoLayout(0, 1, false, false));
		
		
		this.addInputField(new TitledPanel("Razão Social", fieldNome), new RestricaoLayout(0,2,5, true, false));
		this.addInputField(new TitledPanel("CPF/CNPJ", fieldCNPJ), new RestricaoLayout(0, 8, 1, true, false));
		
		this.addInputField(new TitledPanel("Tipo de Fornecedor", painelTipo), new RestricaoLayout(0, 7, 1, true, false));
		this.addInputField(new TitledPanel("Contato", fieldContato), new RestricaoLayout(1, 0, 3, true, false));
		this.addInputField(new TitledPanel("Telefone", fieldTelefone), new RestricaoLayout(1, 3, 1, true, false));
		this.addInputField(new TitledPanel("Ramal", fieldRamal), new RestricaoLayout(1, 4, true, false));
		this.addInputField(new TitledPanel("Celular", fieldCelular), new RestricaoLayout(1, 5, 2, true, false));
		this.addInputField(new TitledPanel("Email", fieldEmail), new RestricaoLayout(1, 7, 2, true, false));
		this.addInputField(getPanelEndereco(), new RestricaoLayout(2, 0, 9, true, false));
		
		this.addInputField(getPanelDadosBancarios1(), new RestricaoLayout(3, 0, 9, true, false));
		this.addInputField(getPanelDadosBancarios2(), new RestricaoLayout(4, 0, 9, true, false));

		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(5, 0, 9, 1, true, true));
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
	
	public Fornecedor newElement() {
		return new Fornecedor();
	}

	public void loadInputFields(Fornecedor fornecedor) {
		String nome = fieldNome.getText();
		String cnpj = fieldCNPJ.getText();
		String email = fieldEmail.getText();
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
		String tipo = (rButtonCPF.isSelected())?"F":"J";
		
		String banco1 = fieldBanco1.getText();
		String agencia1 = fieldAgencia1.getText();
		String conta1 = fieldConta1.getText();
		
		DadosBancarios dadosBancarios1 = new DadosBancarios(banco1,agencia1,conta1);
		
		String banco2 = fieldBanco2.getText();
		String agencia2 = fieldAgencia2.getText();
		String conta2 = fieldConta2.getText();
		DadosBancarios dadosBancarios2 = new DadosBancarios(banco2,agencia2,conta2);
		fornecedor.setDadosBancarios2(dadosBancarios2);

		
		fornecedor.setDadosBancarios1(dadosBancarios1);
		fornecedor.setCodigo(fieldCodigo.getText());
		fornecedor.setNome(nome);
		fornecedor.setCnpj(cnpj);
		fornecedor.setTipo(tipo);
		fornecedor.setEmail(email);
		fornecedor.setFone(telefone);
		fornecedor.setRamal(ramal);
		fornecedor.setContato(contato);
		fornecedor.setCelular(celular);
		fornecedor.setObservacoes(observacoes);
		Endereco endereco=new Endereco();
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setPontoReferencia(referencia);
		fornecedor.setEndereco(endereco);
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
		fieldTelefone.setText("0000000000");
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
		fornecedor = null;
		fieldCodigo.setText("000");
	}

	protected void loadForm(Fornecedor cli) {
		clear();
		if(cli.getTipo() != null) {
			if(cli.getTipo().equals("F")) {
				rButtonCPF.setSelected(true);
			} else {
				rButtonCNPJ.setSelected(true);
			}
		}
		else {
			rButtonCNPJ.setSelected(true);
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
		fieldObservacoes.setText(cli.getObservacoes());
		fieldCodigo.setText(cli.getCodigo());
		fornecedor = cli;
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Fornecedor";
		}
		if(Integer.parseInt(fieldCodigo.getText().trim()) <=0) {
			test=false;
			error += "\nPreencha o código do fornecedor";
			
		}
/*		if(fieldBanco1.getText().length() <=0) {
			test=false;
			error += "\nPreencha o nome do banco";
			
		}
		if(Integer.parseInt(fieldAgencia1.getText().trim()) <=0) {
			test=false;
			error += "\nPreencha o número da agência";
			
		}
		if(fieldConta1.getText().length() <=0) {
			test=false;
			error += "\nPreencha o número da conta";
			
		}*/
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Fornecedor> listAll() {
		return Facade.getInstance().listarFornecedores();
	}

	@Override
	public boolean print(Fornecedor current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(fornecedor != null) {
			hm.put("id", fornecedor.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				JasperPrint impressao = JasperFillManager.fillReport("br/com/sne/sistema/report/fornecedor.jasper", hm, c);
				JasperViewer.viewReport(impressao,false);
				c.close();
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um Fornecedor para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}
}
