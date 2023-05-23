package br.com.sne.sistema.gui.fornecedorterceirizado;

import br.com.sne.sistema.bean.FornecedorTerceirizado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class FornecedorTerceirizadoTableModel extends SizedTableModel<FornecedorTerceirizado> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Código", "Razão Social", "CNPJ/CPF", "Telefone", "Contato", "Celular"};
	private String filterColumnName[] = {"Id",  "Código", "Razão Social", "CNPJ/CPF", "X", "Contato", "X"};
	private int [] columnWidth = { 40, 40, 150, 80, 80, 150, 80};
	private Class<?> columnClass[] = {Long.class, String.class, FornecedorTerceirizado.class, String.class, String.class, String.class, String.class};
	
	public void addElement(FornecedorTerceirizado forn) {
		Object rowData[] = new Object[7];
		rowData[0] = new Long(forn.getId());
		rowData[1] = forn.getCodigo();
		rowData[2] = forn;
		rowData[3] = forn.getCnpj();
		rowData[4] = forn.getFone();
		rowData[5] = forn.getContato();
		rowData[6] = forn.getCelular();
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
