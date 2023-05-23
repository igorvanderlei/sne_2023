package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.grupo.GrupoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraGrupo extends DefaultGarbageForm<Grupo, GrupoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraGrupo () {
		super(new GrupoTableModel(), "/images/icon_trash_18.png", "Grupos Excluídos");
	}

	public List<Grupo> listAll() {
		return Facade.getInstance().listarGruposExcluidos();
	}

	public boolean restore(Grupo current) {
		Facade.getInstance().restaurarGrupo(current);
		return true;
	}
	

}
