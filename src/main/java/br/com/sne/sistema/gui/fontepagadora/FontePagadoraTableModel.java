package br.com.sne.sistema.gui.fontepagadora;

import br.com.sne.sistema.bean.FontePagadora;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class FontePagadoraTableModel extends SizedTableModel<FontePagadora> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Nome",  "Observações"};
	private String filterColumnName[]= {"Id", "Nome",  "Observações"};
	private int [] columnWidth = { 10, 100, 260 };
	private Class<?> columnClass[] = {Long.class, FontePagadora.class, String.class};
	
	public void addElement(FontePagadora desp) {
		/*NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);*/
		
		Object rowData[] = new Object[8];
		rowData[0] = new Long(desp.getId());
		rowData[1] = desp;
		rowData[2] = desp.getObservacoes();//formatoData.format(desp.getDataVencimento());
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
