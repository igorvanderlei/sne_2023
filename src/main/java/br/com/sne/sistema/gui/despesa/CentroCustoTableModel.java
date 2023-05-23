package br.com.sne.sistema.gui.despesa;

import java.util.ArrayList;
import java.util.List;


import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class CentroCustoTableModel extends SizedTableModel<CentroCusto> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"", "Id", "Nome"};
	private String filterColumnName[] = {"Id", "Nome"};
	private int [] columnWidth = { 20, 40, 600 };
	private Class<?> columnClass[] = {Boolean.class, Long.class, CentroCusto.class};
	
	public void addElement(CentroCusto grp) {
		Object rowData[] = new Object[3];
		rowData[0] = new Boolean(false);
		rowData[1] = new Long(grp.getId());
		rowData[2] = grp;
		addRow(rowData);
	}
	
	
	public List<Long> getSelecteds() {
		List<Long> listaId = new ArrayList<Long>();
		
		for(int i =0; i < getRowCount(); i++) {
			if(((Boolean) getValueAt(i, 0)).booleanValue())
				listaId.add((Long) getValueAt(i, 1));
		}
		
		return listaId;
	}
	
	public void selectAll() {
		for(int i =0; i < getRowCount(); i++) {
			setValueAt((Boolean) true, i, 0);
		}
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

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return arg1 == 0;
	}
	
	
}
