package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.List;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.gui.equipamentoenviado.LocalizarOrdemServicoTableModel;
import br.com.sne.sistema.gui.os.OrdemServicoTableModel;

public class DialogSearchOS2 extends DefaultSearchDialog<OrdemServico,OrdemServicoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchOS2(Component comp, List<OrdemServico> possibleValues) {
		super(comp, possibleValues, "Localizar OS", new OrdemServicoTableModel());
	}
}
