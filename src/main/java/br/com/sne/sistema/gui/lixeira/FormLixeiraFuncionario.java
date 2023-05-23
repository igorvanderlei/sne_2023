package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.funcionario.FuncionarioTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraFuncionario extends DefaultGarbageForm<Funcionario, FuncionarioTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraFuncionario () {
		super(new FuncionarioTableModel(), "/images/icon_trash_18.png", "Funcionários Excluídos");
	}

	public List<Funcionario> listAll() {
		return Facade.getInstance().listarFuncionariosExcluidos();
	}

	public boolean restore(Funcionario current) {
		Facade.getInstance().restaurarFuncionario(current);
		return true;
	}
	

}
