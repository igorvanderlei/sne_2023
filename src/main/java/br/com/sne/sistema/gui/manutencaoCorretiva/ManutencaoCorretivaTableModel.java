package br.com.sne.sistema.gui.manutencaoCorretiva;


import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.ManutencaoCorretiva;
import br.com.sne.sistema.bean.ManutencaoPreventiva;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ManutencaoCorretivaTableModel extends SizedTableModel<ManutencaoCorretiva> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Data", "Código", "Recurso", "Marca", "Modelo", "Assistência", "Fone", "Status"};
	private String filterColumnName[] = {"Id", "X", "Código", "Recurso", "Marca", "Modelo", "Assistência", "Fone", "Status"};
	private int [] columnWidth = { 40, 50, 50, 80, 80, 80, 200, 100, 30 };
	private Class<?> columnClass[] = {Long.class, String.class, ManutencaoPreventiva.class, String.class, String.class, String.class, String.class, String.class, String.class};
	
	public void addElement(ManutencaoCorretiva manut) {
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		
		Object rowData[] = new Object[9];
		rowData[0] = new Long(manut.getId());
		rowData[1] = formatoData.format(manut.getDataManutencao());
		rowData[2] = manut;
		rowData[3] = manut.getEquipamento().getDescricaoEquipamento().getNome();
		rowData[4] = manut.getEquipamento().getMarca();
		rowData[5] = manut.getEquipamento().getModelo();
		rowData[6] = manut.getNomeAssistencia();
		rowData[7] = manut.getTelefoneAssistencia();
		rowData[8] = (manut.isStatus())?"Concluída":"Pendente";
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
