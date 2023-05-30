package br.com.sne.sistema.gui.tipousuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormTipoUsuario extends DefaultForm<TipoUsuario, TipoUsuarioTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private PermissaoTableModel modelPermissoes;
	
	public FormTipoUsuario() {
		super(new TipoUsuarioTableModel(), "/images/icon_tusuario_18.png", "Tipos de Usuários");
	}
	
	public boolean save(TipoUsuario current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarTipoUsuario(current);
			s = true;
		}
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Tipo de Usuário '" + current.getNome() + "'  já se encontra cadastrado no sistema!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(TipoUsuario current) {
		boolean s = true;
		Facade.getInstance().atualizarTipoUsuario(current);
		return s;
	}
	
	public boolean remove(TipoUsuario current) {
		boolean test = false;
		try {
			Facade.getInstance().removerTipoUsuario(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Tipo de Usuário. Verifique se existem Usuários cadastrados deste tipo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome = new JTextField();

		
		modelPermissoes = new PermissaoTableModel();
		JTable tabelaPermissao = new JTable(modelPermissoes);
		TableColumn column = null;
		int columnWidth[] = modelPermissoes.getColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
		    column = tabelaPermissao.getColumnModel().getColumn(i);
	        column.setPreferredWidth(columnWidth[i]);
		}
		
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(tabelaPermissao);
		fieldID.setEditable(false);
		
		
		this.addInputField(new TitledPanel("*Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,1, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(1, 0, 2, 1, true, true));
		carregarTabelaPermissoes();
	}
	
	public void carregarTabelaPermissoes() {
		modelPermissoes.removeAllElements();
		for(permission p: permission.values()) {
			modelPermissoes.addElement(p);
		}
	}
	
	public TipoUsuario newElement() {
		return new TipoUsuario();
	}

	public void loadInputFields(TipoUsuario TipoUsuario) {
		String nome = fieldNome.getText();
		TipoUsuario.setNome(nome);
		TipoUsuario.setPermissao(modelPermissoes.getPermissoesSelecionadas());
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		carregarTabelaPermissoes();
	}

	protected void loadForm(TipoUsuario rec) {
		fieldID.setText("" + rec.getId());
		fieldNome.setText(rec.getNome());
		
		
		modelPermissoes.removeAllElements();
		for(permission p: permission.values()) {
			if(rec.getPermissao().contains(p)){
				modelPermissoes.addElement(p, true);	
			} else {
				modelPermissoes.addElement(p);
			}
		}
		
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Tipo de Usuário";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<TipoUsuario> listAll() {
		return Facade.getInstance().listarTipoUsuarios();
	}

	@Override
	public boolean print(TipoUsuario current) {
		// TODO Auto-generated method stub
		return false;
	}
}
