package br.com.sne.sistema.gui.util.form;
import java.awt.Component;
import java.util.List;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.gui.equipamento.EquipamentoTableModel;

public class DialogSearchEquipamento extends DefaultSearchDialog<Equipamento, EquipamentoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchEquipamento(Component comp, List<Equipamento> possibleValues) {
		super(comp, possibleValues, "Localizar Equipamento", new EquipamentoTableModel());
	}
}
