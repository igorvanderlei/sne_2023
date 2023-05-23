package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.orcamento.OrcamentoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraOrcamento extends DefaultGarbageForm<Orcamento, OrcamentoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraOrcamento () {
		super(new OrcamentoTableModel(), "/images/icon_trash_18.png", "Orçamentos Excluídos");
	}

	public List<Orcamento> listAll() {
		return Facade.getInstance().listarOrcamentosExcluidos();
	}

	public boolean restore(Orcamento current) {
		Facade.getInstance().restaurarOrcamento(current);
		return true;
	}
	

}
