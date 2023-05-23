package br.com.sne.sistema.gui.os;

import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ClienteTableModelEmergencial extends SizedTableModel<Cliente> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Razão Social/Nome"};
	private String filterColumnName[] = {"Id", "Razão Social/Nome"};
	private int [] columnWidth = { 40, 400};
	private Class<?> columnClass[] = {Long.class, Cliente.class};
	
	public void addElement(Cliente cli) {
		Object rowData[] = new Object[2];
		rowData[0] = new Long(cli.getId());
		rowData[1] = cli;
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
