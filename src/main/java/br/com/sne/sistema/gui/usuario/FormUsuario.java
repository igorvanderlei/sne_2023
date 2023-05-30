package br.com.sne.sistema.gui.usuario;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormUsuario extends DefaultForm<Usuario, UsuarioTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JPasswordField fieldPassword;
	private JPasswordField fieldPasswordConfirm;
	private JComboBox fieldFuncionario;
	private JComboBox fieldTipoUsuario;
	private JComboBox fieldUnidade;
	private JTextArea fieldObservacoes;


	public FormUsuario() {
		super(new UsuarioTableModel(), "/images/icon_usuario_18.png", "Usuários");
		desabilitarBotaoImprimir();
	}

	public boolean save(Usuario current) {
		boolean s = false;
		try {
			if(validaPassword()){
				Facade.getInstance().salvarUsuario(current);
				s = true;
			}
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Usuário já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Usuario current) {
		boolean s = false;
		if(validaPassword()){
			Facade.getInstance().atualizarUsuario(current);
			s=true;
		}
		return s;
	}

	public boolean remove(Usuario current) {
		boolean test = false;
		try {
			Facade.getInstance().removerUsuario(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome = new JTextField();
		fieldPassword = new JPasswordField();
		fieldPasswordConfirm = new JPasswordField();
		fieldObservacoes = new JTextArea();

		fieldTipoUsuario = new JComboBox();
		fieldFuncionario = new JComboBox();
		fieldUnidade = new JComboBox();

		carregarFieldFuncionario();
		carregarFieldTipoUsuario();
		carregarFieldUnidade();
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);

		this.addInputField(new TitledPanel("*Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("*Funcionário", fieldFuncionario), new RestricaoLayout(0,1,1, true, false));
		this.addInputField(new TitledPanel("*Unidade", fieldUnidade), new RestricaoLayout(0,2,1, true, false));
		this.addInputField(new TitledPanel("*Tipo de Usuário", fieldTipoUsuario), new RestricaoLayout(0,3,1, true, false));
		
		this.addInputField(new TitledPanel("*Nome do usuário", fieldNome), new RestricaoLayout(1,0,2, true, false));
		this.addInputField(new TitledPanel("*Senha", fieldPassword), new RestricaoLayout(1,2,1, true, false));
		this.addInputField(new TitledPanel("*Confirmação de Senha", fieldPasswordConfirm), new RestricaoLayout(1,3,1, true, false));
		
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 4, 1, true, true));
	}

	private void carregarFieldFuncionario() {
		fieldFuncionario.removeAllItems();
		List<Funcionario> lista = Facade.getInstance().listarFuncionarios();
		for(Funcionario f : lista) {
			fieldFuncionario.addItem(f);
		}
	}
	
	private void carregarFieldUnidade() {
		fieldUnidade.removeAllItems();
		List<Unidade> lista = Facade.getInstance().listarUnidade();
		for(Unidade uni: lista) {
			fieldUnidade.addItem(uni);
		}
	}

	private void carregarFieldTipoUsuario() {
		fieldTipoUsuario.removeAllItems();
		List<TipoUsuario> lista = Facade.getInstance().listarTipoUsuarios();
		for(TipoUsuario tp : lista) {
			fieldTipoUsuario.addItem(tp);
		}
	}

	public Usuario newElement() {
		return new Usuario();
	}

	public void loadInputFields(Usuario user) {

		String observacoes = fieldObservacoes.getText();
		Funcionario funcionario = (Funcionario) fieldFuncionario.getSelectedItem();
		TipoUsuario tipoUsuario = (TipoUsuario) fieldTipoUsuario.getSelectedItem();
		Unidade unidade = (Unidade) fieldUnidade.getSelectedItem();
		String password = new String(fieldPassword.getPassword());
		String username = fieldNome.getText().trim();

		user.setFuncionario(funcionario);
		user.setTipoUsuario(tipoUsuario);
		user.setUnidade(unidade);
		user.setLogin(username);
		user.alterarPassword(password);
		user.setObservacoes(observacoes);

	}

	public boolean validaPassword(){
		String password = new String(fieldPassword.getPassword());
		String password2 = new String(fieldPasswordConfirm.getPassword());

		if(!password.equals(password2)) {
			JOptionPane.showMessageDialog(null,"Foram digitados valores diferentes para a senha e confirmação de senha!", "Erro", JOptionPane.ERROR_MESSAGE);
			fieldPassword.setText("");
			fieldPasswordConfirm.setText("");
			fieldPassword.requestFocus();
			return false;
		}
		return true;
	} 


	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		fieldPassword.setText("");
		fieldPasswordConfirm.setText("");
		fieldObservacoes.setText("");
		carregarFieldFuncionario();
		carregarFieldTipoUsuario();
		carregarFieldUnidade();
	}

	protected void loadForm(Usuario rec) {
		fieldID.setText("" + rec.getId());
		fieldTipoUsuario.setSelectedItem(rec.getTipoUsuario());
		fieldFuncionario.setSelectedItem(rec.getFuncionario());
		fieldUnidade.setSelectedItem(rec.getUnidade());
		fieldNome.setText(rec.getLogin());
		fieldObservacoes.setText(rec.getObservacoes());	
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";

		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o login do Usuário";
		}

		String password =  new String(fieldPassword.getPassword());
		if(password.length() <= 0) {
			test = false;
			error += "\nA Senha não pode ser vazia";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Usuario> listAll() {
		return Facade.getInstance().listarUsuarios();
	}

	@Override
	public boolean print(Usuario current) {
		// TODO Auto-generated method stub
		return false;
	}
}
