package br.com.sne.sistema.gui.unidade;

import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class UnidadeTableModel extends SizedTableModel<Unidade> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Código", "Nome", "Razão Social", "Cnpj"};
	private String filterColumnName[] = {"Id", "Código", "Nome", "Razão Social", "Cnpj"};
	private int [] columnWidth = { 40, 40, 200, 200, 150};
	private Class<?> columnClass[] = {Long.class, String.class, Unidade.class, String.class, String.class};
	
	public void addElement(Unidade cli) {
		Object rowData[] = new Object[5];
		rowData[0] = new Long(cli.getId());
		rowData[1] = cli.getCodigo();
		rowData[2] = cli;
		rowData[3] = cli.getRazaoSocial();
		rowData[4] = cli.getCnpj();
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
