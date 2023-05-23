package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.ClienteTableModel;

public class DialogSearchClient extends DefaultFilterSearchDialog<Cliente, ClienteTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchClient(Component comp) {
		super(comp,  "Localizar Cliente", new ClienteTableModel());
	}

	protected List<Cliente> listAll() {
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
		
		return lista;
	}

	@Override
	public Cliente init(Cliente value) {
		return value;
	}
}
