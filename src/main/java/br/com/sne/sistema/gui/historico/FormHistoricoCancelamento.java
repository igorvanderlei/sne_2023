package br.com.sne.sistema.gui.historico;

import java.util.List;

import br.com.sne.sistema.bean.HistoricoCancelamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.historico.tableModel.HistoricoCancelamentoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultHistoryForm;

public class FormHistoricoCancelamento extends DefaultHistoryForm<HistoricoCancelamento, HistoricoCancelamentoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormHistoricoCancelamento () {
		super(new HistoricoCancelamentoTableModel(), "/images/icon_historico_18.png", "Histórico de Cancelamentos de Receita");
	}

	public List<HistoricoCancelamento> listAll() {
		return Facade.getInstance().listarHistoricoCancelamentos();
	}

}
