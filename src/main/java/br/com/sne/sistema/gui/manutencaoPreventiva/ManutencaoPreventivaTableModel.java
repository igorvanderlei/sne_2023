package br.com.sne.sistema.gui.manutencaoPreventiva;

import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.ManutencaoPreventiva;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ManutencaoPreventivaTableModel extends SizedTableModel<ManutencaoPreventiva> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Data", "Código", "Recurso", "Marca", "Modelo", "Técnico", "Status"};
	private String filterColumnName[] = {"Id", "X", "Código", "Recurso", "Marca", "Modelo", "Técnico", "Status"};
	private int [] columnWidth = { 40, 50, 50, 80, 80, 80, 200, 30 };
	private Class<?> columnClass[] = {Long.class, String.class, ManutencaoPreventiva.class, String.class, String.class, String.class, String.class, String.class};
	
	public void addElement(ManutencaoPreventiva manut) {
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		
		Object rowData[] = new Object[8];
		rowData[0] = new Long(manut.getId());
		rowData[1] = formatoData.format(manut.getDataManutencao());
		rowData[2] = manut;
		rowData[3] = manut.getEquipamento().getDescricaoEquipamento().getNome();
		rowData[4] = manut.getEquipamento().getMarca();
		rowData[5] = manut.getEquipamento().getModelo();
		rowData[6] = manut.getTecnicoResponsavel().getNome();
		rowData[7] = (manut.isStatus())?"Concluída":"Pendente";
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
