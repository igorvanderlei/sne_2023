package br.com.sne.sistema.gui.tipousuario;

import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class TipoUsuarioTableModel extends SizedTableModel<TipoUsuario> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Nome"};
	private String filterColumnName[] = {"Id", "Nome"};
	private int [] columnWidth = { 4, 800 };
	private Class<?> columnClass[] = {Long.class, TipoUsuario.class};
	
	public void addElement(TipoUsuario tp) {
		Object rowData[] = new Object[2];
		rowData[0] = new Long(tp.getId());
		rowData[1] = tp;
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
