package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.fornecedor.FornecedorTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraFornecedor extends DefaultGarbageForm<Fornecedor, FornecedorTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraFornecedor () {
		super(new FornecedorTableModel(), "/images/icon_trash_18.png", "Fornecedores Excluídos");
	}

	public List<Fornecedor> listAll() {
		return Facade.getInstance().listarFornecedoresExcluidos();
	}

	public boolean restore(Fornecedor current) {
		Facade.getInstance().restaurarFornecedor(current);
		return true;
	}
	

}
