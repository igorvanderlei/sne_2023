package br.com.sne.sistema.gui.util.form;
import java.awt.Component;
import java.util.List;

import br.com.sne.sistema.bean.RegistroSublocacao;
import br.com.sne.sistema.gui.equipamentosublocado.RegistroSublocacaoTableModel;

public class DialogSearchRegistroSublocacao extends DefaultSearchDialog<RegistroSublocacao, RegistroSublocacaoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchRegistroSublocacao(Component comp, List<RegistroSublocacao> possibleValues) {
		super(comp, possibleValues, "Localizar Registro de Sublocação", new RegistroSublocacaoTableModel());
	}
}
