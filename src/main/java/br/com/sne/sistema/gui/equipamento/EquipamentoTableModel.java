package br.com.sne.sistema.gui.equipamento;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class EquipamentoTableModel extends SizedTableModel<Equipamento> {
	private static final long serialVersionUID = 1L;
	private String [] columnName = {"Id", "Grupo", "Subgrupo", "Código", "Marca", "Modelo", "Número Série", "Status"};
	private String[] filterColumnName = {"Id", "Grupo", "Subgrupo", "Código", "Marca", "Modelo", "Número Série", "Status"};
	private int[] columnWidth = {20, 150, 200, 120, 100, 100, 100, 160};
	private Class<?> columnClass[] = {Long.class, String.class, String.class, Equipamento.class, String.class, String.class, String.class, String.class};
	
	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}
	
	public int[] getColumnWidth() {
		return columnWidth;
	}

	public String[] getFilterColumnName() {
		return filterColumnName;
	}

	public void addElement(Equipamento e) {
		Object rowData[] = new Object[8];
		rowData[0] = new Long(e.getId());
		if(e.getGrupo() != null)
			rowData[1] = e.getGrupo().getNome();
		if(e.getDescricaoEquipamento() != null) 
			rowData[2] = e.getDescricaoEquipamento().getNome();
		rowData[3] = e;
		rowData[4] = e.getMarca();
		rowData[5] = e.getModelo();
		rowData[6] = e.getSerialEquipamento();
		rowData[7] = e.getStatus().toString();
		addRow(rowData);
	}

	public int getObjectIndex() {
		return 3;
	}
}
