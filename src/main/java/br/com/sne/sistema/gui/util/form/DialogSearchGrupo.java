package br.com.sne.sistema.gui.util.form;
import java.awt.Component;
import java.util.List;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.gui.grupo.GrupoTableModel;

public class DialogSearchGrupo extends DefaultSearchDialog<Grupo, GrupoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchGrupo(Component comp, List<Grupo> possibleValues) {
		super(comp, possibleValues, "Localizar Grupo", new GrupoTableModel());
	}
}
