package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.centrocusto.CentroCustoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraCentroCusto extends DefaultGarbageForm<CentroCusto, CentroCustoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraCentroCusto () {
		super(new CentroCustoTableModel(), "/images/icon_trash_18.png", "Centros de Custo Excluídos");
	}

	public List<CentroCusto> listAll() {
		return Facade.getInstance().listarCentroCustosExcluidos();
	}

	public boolean restore(CentroCusto current) {
		Facade.getInstance().restaurarCentroCusto(current);
		return true;
	}
	

}
