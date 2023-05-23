package br.com.sne.sistema.gui.unidade;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;

import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;

import br.com.sne.sistema.gui.util.components.ImageFilter;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;

import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormUnidade extends DefaultForm<Unidade, UnidadeTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JTextField fieldCodigo;
	private JTextField fieldRazaoSocial;
	private JTextField fieldCnpj;
	private JTextField fieldInscricaoEsdadual;

	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;

	private JLabel fieldImagemTela;
	private JLabel fieldImagemFormularios;

	private Unidade unidade;

	private Unidade un = new Unidade();

	public FormUnidade() {
		super(new UnidadeTableModel(), "/images/icon_unidade_18.png", "Unidades Empresariais");
		desabilitarBotaoImprimir();
	}

	public boolean save(Unidade current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarUnidade(current);
			unidade = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "A Unidade já se encontra cadastrada", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}


	public boolean print(Unidade current){
		return false;
	}

	public boolean update(Unidade current) {
		boolean s = true;
		Facade.getInstance().atualizarUnidade(current);
		return s;
	}

	public boolean remove(Unidade current) {
		boolean test = false;
		try {
			Facade.getInstance().removerUnidade(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Unidade.\nVerifique se existem usuários ou equipamentos cadastrados nesta unidade.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome  = new JTextField();

		MaskFormatter formatoCodigo;
		try {
			formatoCodigo = new MaskFormatter("##");
			formatoCodigo.setPlaceholder("00");
			fieldCodigo = new JFormattedTextField(formatoCodigo);
		} catch (ParseException e) {
			fieldCodigo = new JTextField();
		}

		fieldRazaoSocial = new JTextField();
		fieldCnpj = new JCnpjField();
		fieldInscricaoEsdadual = new JTextField();

		fieldID.setEditable(false);

		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Código", fieldCodigo), new RestricaoLayout(0, 1, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,2,3, true, false));

		this.addInputField(new TitledPanel("Razão Social", fieldRazaoSocial), new RestricaoLayout(1,0,3, true, false));
		this.addInputField(new TitledPanel("Cnpj", fieldCnpj), new RestricaoLayout(1,3,1, true, false));
		this.addInputField(new TitledPanel("Inscrição Estadual", fieldInscricaoEsdadual), new RestricaoLayout(1,4,1, true, false));

		this.addInputField(getPanelEndereco(), new RestricaoLayout(2, 0, 5, true, false));
		this.addInputField(getPanelImagens(), new RestricaoLayout(3, 0, 5, 1, true, true));
	}

	private JPanel getPanelImagens() {
		JPanel panelImagens = new JPanel();
		panelImagens.setLayout(new GridLayout(1,2));
		panelImagens.add(new TitledPanel("Imagem da Tela", getPanelImagemTela()));
		panelImagens.add(new TitledPanel("Imagem dos Relatórios", getPanelImagemRelatorio()));
		return panelImagens;
	}

	private JPanel getPanelImagemTela() {
		JPanel imagemTela = new JPanel();
		imagemTela.setLayout(new GridBagLayout());

		fieldImagemTela = new JLabel();
		fieldImagemTela.setBackground(Color.pink);

		imagemTela.add(getBotaoCarregarImagemTela(), new RestricaoLayout(0,0,1,true, false));
		imagemTela.add(fieldImagemTela, new RestricaoLayout(1,0,1,1, true, true));

		return imagemTela;
	}


	private JButton getBotaoCarregarImagemTela() {
		JButton botao = new JButton("Carregar Imagem ...");
		botao.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser fc = new JFileChooser();
						fc.setFileFilter(new ImageFilter());
						fc.setAcceptAllFileFilterUsed(false);
						int returnVal = fc.showOpenDialog(FormUnidade.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							un.alterarImagemTela(file.getAbsolutePath());
							Image img = un.getImagemTelaImage().getImage().getScaledInstance(fieldImagemTela.getWidth(), fieldImagemTela.getHeight(), Image.SCALE_DEFAULT);
							fieldImagemTela.setIcon(new ImageIcon(img));
						} 
					}
				}
				);
		return botao;
	}



	private JPanel getPanelImagemRelatorio() {
		JPanel imagemRelatorio = new JPanel();
		imagemRelatorio.setLayout(new GridBagLayout());

		fieldImagemFormularios = new JLabel();
		fieldImagemFormularios.setBackground(Color.blue);

		imagemRelatorio.add(getBotaoCarregarImagemRelatorio(), new RestricaoLayout(0,0,1,true, false));
		imagemRelatorio.add(fieldImagemFormularios, new RestricaoLayout(1,0,1,1, true, true));

		return imagemRelatorio;		
	}

	private JButton getBotaoCarregarImagemRelatorio() {
		JButton botao = new JButton("Carregar Imagem ...");
		botao.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser fc = new JFileChooser();
						fc.setFileFilter(new ImageFilter());
						fc.setAcceptAllFileFilterUsed(false);
						int returnVal = fc.showOpenDialog(FormUnidade.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							un.alterarImagemFormulario(file.getAbsolutePath());
							Image img = un.getImagemFormularioImage().getImage().getScaledInstance(fieldImagemFormularios.getWidth(), fieldImagemFormularios.getHeight(), Image.SCALE_DEFAULT);
							fieldImagemFormularios.setIcon(new ImageIcon(img));
						} 
					}
				}
				);
		return botao;
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


	public Unidade newElement() {
		return new Unidade();
	}



	public void loadInputFields(Unidade unidade) {

		String nome = fieldNome.getText();
		String razaoSocial = fieldRazaoSocial.getText();
		String codigo = fieldCodigo.getText();
		String cnpj = fieldCnpj.getText();
		String inscricaoEstadual = fieldInscricaoEsdadual.getText();
		String logradouro = fieldEnderecoLogradouro.getText();
		String numero = fieldEnderecoNumero.getText();
		String complemento = fieldEnderecoComplemento.getText();
		String cep = fieldEnderecoCEP.getText();
		String bairro = fieldEnderecoBairro.getText();
		String cidade = fieldEnderecoCidade.getText();
		String estado = (String) fieldEnderecoEstado.getSelectedItem();
		String referencia = fieldEnderecoReferencia.getText();

		unidade.setNome(nome);
		unidade.setRazaoSocial(razaoSocial);
		unidade.setCnpj(cnpj);
		unidade.setCodigo(codigo);
		unidade.setInscricaoEstadual(inscricaoEstadual);

		unidade.setImagemFormulario(un.getImagemFormulario());
		unidade.setImagemTela(un.getImagemTela());

		Endereco endereco= (unidade.getEndereco()==null)?new Endereco():unidade.getEndereco();
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setPontoReferencia(referencia);
		unidade.setEndereco(endereco);

	}

	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("00000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");

		fieldCodigo.setText("00");
		fieldRazaoSocial.setText("");
		fieldCnpj.setText("00000000000000");
		fieldInscricaoEsdadual.setText("");
		fieldImagemFormularios.setIcon(null);
		fieldImagemTela.setIcon(null);

		unidade = null;
	}

	protected void loadForm(Unidade unid) {
		fieldID.setText("" + unid.getId());
		fieldNome.setText(unid.getNome());
		fieldEnderecoLogradouro.setText(unid.getEndereco().getLogradouro());
		fieldEnderecoNumero.setText(unid.getEndereco().getNumero());
		fieldEnderecoComplemento.setText(unid.getEndereco().getComplemento());
		fieldEnderecoCEP.setText(unid.getEndereco().getCep());
		fieldEnderecoBairro.setText(unid.getEndereco().getBairro());
		fieldEnderecoCidade.setText(unid.getEndereco().getCidade());
		fieldEnderecoEstado.setSelectedItem(unid.getEndereco().getEstado());
		fieldEnderecoReferencia.setText(unid.getEndereco().getPontoReferencia());

		fieldCnpj.setText(unid.getCnpj());
		fieldCodigo.setText(unid.getCodigo());
		fieldRazaoSocial.setText(unid.getRazaoSocial());
		fieldInscricaoEsdadual.setText(unid.getInscricaoEstadual());

		un.setImagemFormulario(unid.getImagemFormulario());
		un.setImagemTela(unid.getImagemTela());

		if(un.getImagemFormularioImage() != null) {
			Image img1 = un.getImagemFormularioImage().getImage().getScaledInstance(fieldImagemFormularios.getWidth(), fieldImagemFormularios.getHeight(), Image.SCALE_DEFAULT);
			fieldImagemFormularios.setIcon(new ImageIcon(img1));
		}

		if(un.getImagemTelaImage() != null) {
			Image img = un.getImagemTelaImage().getImage().getScaledInstance(fieldImagemTela.getWidth(), fieldImagemTela.getHeight(), Image.SCALE_DEFAULT);
			fieldImagemTela.setIcon(new ImageIcon(img)); 
		}

		unidade = unid;
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome da Unidade";
		}

		if(fieldCodigo.getText().equals("00")) {
			test = false;
			error += "\nPreencha o código da Unidade";
		}


		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Unidade> listAll() {
		return Facade.getInstance().listarUnidade();
	}

}
