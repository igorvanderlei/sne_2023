package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.FornecedorTerceirizado;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.fornecedorterceirizado.FornecedorTerceirizadoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraFornecedorTerceirizado extends DefaultGarbageForm<FornecedorTerceirizado, FornecedorTerceirizadoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraFornecedorTerceirizado () {
		super(new FornecedorTerceirizadoTableModel(), "/images/icon_trash_18.png", "Fornecedor Terceirizadoes Excluídos");
	}

	public List<FornecedorTerceirizado> listAll() {
		return Facade.getInstance().listarFornecedorTerceirizadosExcluidos();
	}

	public boolean restore(FornecedorTerceirizado current) {
		Facade.getInstance().restaurarFornecedorTerceirizado(current);
		return true;
	}
	

}
