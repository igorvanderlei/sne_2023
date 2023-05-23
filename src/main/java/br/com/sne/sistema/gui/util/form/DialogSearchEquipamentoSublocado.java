package br.com.sne.sistema.gui.util.form;
import java.awt.Component;
import java.util.List;
import br.com.sne.sistema.bean.EquipamentoSublocado;
import br.com.sne.sistema.gui.equipamentosublocado.EquipamentoSublocadoTableModel;

public class DialogSearchEquipamentoSublocado extends DefaultSearchDialog<EquipamentoSublocado, EquipamentoSublocadoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchEquipamentoSublocado(Component comp, List<EquipamentoSublocado> possibleValues) {
		super(comp, possibleValues, "Localizar Equipamento Sublocado", new EquipamentoSublocadoTableModel());
	}
}
