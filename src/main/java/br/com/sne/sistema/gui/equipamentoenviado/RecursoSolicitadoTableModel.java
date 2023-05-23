package br.com.sne.sistema.gui.equipamentoenviado;
import java.util.Date;

import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursoSolicitadoTableModel extends SizedTableModel<RecursoSolicitado> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Subgrupo", "Inicio", "Fim", "Quant. Solicitada", "Quant. Enviada"};
	private String filterColumnName[] = {"Subgrupo", "Dt. Inicio", "Dt. Fim", "Quant. Solicitada", "Quant. Enviada"};
	private int [] columnWidth = {90, 40, 40, 10, 10 };
	private Class<?> columnClass[] = {RecursoSolicitado.class, Date.class, Date.class, Long.class, Long.class};
	    
	public void addElement(RecursoSolicitado os, int cont) {
		Object rowData[] = new Object[5];
		rowData[0] = os;
		rowData[1] = os.getDataInicio(); 
		rowData[2] = os.getDataFim();
		rowData[3] = os.getQuantidade();
		rowData[4] = cont;
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

	public void addElement(RecursoSolicitado r) {
		// TODO Auto-generated method stub
		
	}

	
}
