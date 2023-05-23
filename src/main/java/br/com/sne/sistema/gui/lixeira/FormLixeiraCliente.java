package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.ClienteTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraCliente extends DefaultGarbageForm<Cliente, ClienteTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraCliente () {
		super(new ClienteTableModel(), "/images/icon_trash_18.png", "Clientes Excluídos");
	}

	public List<Cliente> listAll() {
		return Facade.getInstance().listarClientesExcluidos();
	}

	public boolean restore(Cliente current) {
		Facade.getInstance().restaurarCliente(current);
		return true;
	}
	

}
