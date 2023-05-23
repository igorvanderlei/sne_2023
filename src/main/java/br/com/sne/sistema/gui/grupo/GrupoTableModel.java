package br.com.sne.sistema.gui.grupo;

import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class GrupoTableModel extends SizedTableModel<Grupo> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Código","Tipo de Recurso", "Nome"};
	private String filterColumnName[] = {"Id", "X","Tipo de Recurso", "Nome"};
	private int [] columnWidth = { 40, 80,200, 400 };
	private Class<?> columnClass[] = {Long.class, String.class,String.class, Grupo.class};
	
	public void addElement(Grupo grp) {
		Object rowData[] = new Object[4];
		rowData[0] = new Long(grp.getId());
		rowData[1] = grp.getCodigo();
		rowData[2] = grp.getTipoRecurso().toString();
		rowData[3] = grp;
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 3;
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
