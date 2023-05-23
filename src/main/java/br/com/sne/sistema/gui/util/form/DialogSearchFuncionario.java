package br.com.sne.sistema.gui.util.form;
import java.awt.Component;
import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.gui.funcionario.FuncionarioTableModel;

public class DialogSearchFuncionario extends DefaultSearchDialog<Funcionario, FuncionarioTableModel>{

	private static final long serialVersionUID = 1L;

	public DialogSearchFuncionario(Component comp, List<Funcionario> possibleValues) {
		super(comp, possibleValues, "Localizar Funcionário", new FuncionarioTableModel());
	}
}
