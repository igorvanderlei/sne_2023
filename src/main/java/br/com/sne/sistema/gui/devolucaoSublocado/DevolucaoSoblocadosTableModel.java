package br.com.sne.sistema.gui.devolucaoSublocado;


import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.DevolucaoSublocados;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class DevolucaoSoblocadosTableModel extends SizedTableModel<DevolucaoSublocados> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Fornecedor", "Data", "Funcionário"};
	private String filterColumnName[] = {"Id", "Fornecedor", "X", "Funcionário"};
	private int [] columnWidth = { 40, 200, 50, 100};
	private Class<?> columnClass[] = {DevolucaoSublocados.class, String.class, String.class, String.class};
	
	public void addElement(DevolucaoSublocados manut) {
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Object rowData[] = new Object[4];
		rowData[0] = manut;
		rowData[1] = manut.getFornecedor().getNome();
		rowData[2] = formatoData.format(manut.getData());
		rowData[3] = manut.getFuncionario().getNome();
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 0;
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
