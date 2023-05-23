package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.List;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.gui.os.ClienteTableModelEmergencial;

public class DialogSearchClienteEmergencial extends DefaultSearchDialog<Cliente, ClienteTableModelEmergencial>{
	private static final long serialVersionUID = 1L;

	public DialogSearchClienteEmergencial(Component comp, List<Cliente> possibleValues) {
		super(comp, possibleValues, "Localizar Cliente", new ClienteTableModelEmergencial());
	}
}
