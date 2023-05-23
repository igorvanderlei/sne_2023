package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.equipamento.EquipamentoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraEquipamento extends DefaultGarbageForm<Equipamento, EquipamentoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraEquipamento () {
		super(new EquipamentoTableModel(), "/images/icon_trash_18.png", "Equipamentos Excluídos");
	}

	public List<Equipamento> listAll() {
		return Facade.getInstance().listarEquipamentosExcluidos();
	}

	public boolean restore(Equipamento current) {
		Facade.getInstance().restaurarEquipamento(current);
		return true;
	}
	

}
