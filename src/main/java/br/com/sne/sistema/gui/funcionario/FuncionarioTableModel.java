package br.com.sne.sistema.gui.funcionario;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class FuncionarioTableModel extends SizedTableModel<Funcionario> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Nome", "Celular"};
	private String filterColumnName[] = {"Id", "Nome", "Celular"};
	private int [] columnWidth = { 40, 200, 200 };
	private Class<?> columnClass[] = {Long.class, Funcionario.class, String.class};
	
	public void addElement(Funcionario grp) {
		Object rowData[] = new Object[4];
		rowData[0] = new Long(grp.getId());
		rowData[1] = grp;
		rowData[2] = grp.getCelular();

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
