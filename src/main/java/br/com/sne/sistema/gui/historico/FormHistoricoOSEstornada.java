package br.com.sne.sistema.gui.historico;

import java.util.List;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.os.OrdemServicoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultHistoryForm;

public class FormHistoricoOSEstornada extends DefaultHistoryForm<OrdemServico, OrdemServicoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormHistoricoOSEstornada () {
		super(new OrdemServicoTableModel(), "/images/icon_hestorno_18.png", "Histórico de Estornos");
	}

	public List<OrdemServico> listAll() {
		return Facade.getInstance().listarOrdemServicosEstornada();
	}

}
