package br.com.sne.sistema.gui.local;

import java.awt.Component;
import java.util.List;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.gui.util.form.DefaultSearchDialog;

public class DialogSearchLocal extends DefaultSearchDialog<Local, LocalTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchLocal(Component comp, List<Local> possibleValues) {
		super(comp, possibleValues, "Localizar Local", new LocalTableModel());
	}
}
