package br.com.sne.sistema.gui.local;

import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class LocalTableModel extends SizedTableModel<Local> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Nome", "Logradouro", "Num.", "Bairro", "Cidade", "Estado"};
	private String filterColumnName[] = {"Id", "Nome", "Logradouro", "X", "Bairro", "Cidade", "Estado"};
	private int [] columnWidth = { 40, 200, 200, 40, 80, 80, 40};
	private Class<?> columnClass[] = {Long.class, Local.class, String.class, String.class, String.class, String.class, String.class};
	
	public void addElement(Local local) {
		Object rowData[] = new Object[7];
		rowData[0] = new Long(local.getId());
		rowData[1] = local;
		rowData[2] = local.getEndereco().getLogradouro();
		rowData[3] = local.getEndereco().getNumero();
		rowData[4] = local.getEndereco().getBairro();
		rowData[5] = local.getEndereco().getCidade();
		rowData[6] = local.getEndereco().getEstado();
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
