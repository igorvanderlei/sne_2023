package br.com.sne.sistema.gui.comodato;

import java.util.ArrayList;
import java.util.List;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursosSolicitadosTableModel extends SizedTableModel<RecursoSolicitado>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Nome", "Quantidade"};
	private String filterColumnName[] = {""};
	private int [] columnWidth = { 800,  40};
	private Class<?> columnClass[] = {String.class, Integer.class};

	public void addElement(RecursoSolicitado rec) {
		Object rowData[] = new Object[2];
		rowData[0] = rec;
		rowData[1] = rec.getQuantidade();
		
		addRow(rowData);
	}
	
	public List<RecursoSolicitado> getRecursos() {
		List<RecursoSolicitado> recursos = new ArrayList<RecursoSolicitado>();
		for(int i = 0; i < getRowCount(); i++) {
			RecursoSolicitado rec = (RecursoSolicitado) getValueAt(i, 0);
			rec.setQuantidade((Integer) getValueAt(i, 1));
			recursos.add(rec);
		}
		return recursos;
	}
	
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
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
