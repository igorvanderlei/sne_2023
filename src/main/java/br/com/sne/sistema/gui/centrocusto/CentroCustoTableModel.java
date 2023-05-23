package br.com.sne.sistema.gui.centrocusto;

import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class CentroCustoTableModel extends SizedTableModel<CentroCusto> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Nome"};
	private String filterColumnName[] = {"Id", "Nome"};
	private int [] columnWidth = { 40, 600 };
	private Class<?> columnClass[] = {Long.class, CentroCusto.class};
	
	public void addElement(CentroCusto grp) {
		Object rowData[] = new Object[2];
		rowData[0] = new Long(grp.getId());
		rowData[1] = grp;
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 1;
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
