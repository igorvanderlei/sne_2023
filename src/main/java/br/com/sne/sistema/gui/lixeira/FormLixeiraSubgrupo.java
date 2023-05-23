package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.recurso.RecursoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraSubgrupo extends DefaultGarbageForm<Recurso, RecursoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraSubgrupo () {
		super(new RecursoTableModel(), "/images/icon_trash_18.png", "Subgrupos Excluídos");
	}

	public List<Recurso> listAll() {
		return Facade.getInstance().listarRecursosExcluidos();
	}

	public boolean restore(Recurso current) {
		Facade.getInstance().restaurarRecurso(current);
		return true;
	}
	

}