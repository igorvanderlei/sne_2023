package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.List;

import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.gui.recurso.RecursoTableModel;

public class DialogSearchRecurso extends DefaultSearchDialog<Recurso, RecursoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchRecurso(Component comp, List<Recurso> possibleValues) {
		super(comp, possibleValues, "Localizar Recurso", new RecursoTableModel());
	}
}
