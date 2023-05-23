package br.com.sne.sistema.gui.descarteequipamento;

import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.DescarteEquipamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class DescarteEquipamentoTableModel extends SizedTableModel<DescarteEquipamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Data", "Código", "Recurso", "Marca", "Modelo", "Motivo"};
	private String filterColumnName[] = {"Id", "X", "Código", "Recurso", "Marca", "Modelo", "Motivo"};
	private int [] columnWidth = { 40, 50, 50, 80, 80, 80, 200 };
	private Class<?> columnClass[] = {Long.class, String.class, DescarteEquipamento.class, String.class, String.class, String.class, String.class};
	
	public void addElement(DescarteEquipamento desc) {
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Object rowData[] = new Object[7];
		rowData[0] = new Long(desc.getId());
		rowData[1] = formatoData.format(desc.getData());
		rowData[2] = desc;
		rowData[3] = desc.getEquipamento().getDescricaoEquipamento().getNome();
		rowData[4] = desc.getEquipamento().getMarca();
		rowData[5] = desc.getEquipamento().getModelo();
		rowData[6] = desc.getMotivo();
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 2;
	}
	
	public int[] getColumnWidth() {
		return columnWidth;
	}
	
	public String[] getColumnName() {
		return columnName;
	}

	public String[] getFilterColumnName() {
		return filterColumnName;
	}

	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}
	

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}




}
