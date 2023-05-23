package br.com.sne.sistema.gui.equipamentoenviado;
import java.util.Date;

import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursoHumanoSolicitadoTableModel extends SizedTableModel<RecursoSolicitado> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Recurso", "Data Inicio", "Data Fim", "Qtd"};
	private String filterColumnName[] = {"Recurso", "Data Inicio", "Data Fim", "Qtd"};
	private int [] columnWidth = {200, 30, 30, 5 };
	private Class<?> columnClass[] = {RecursoSolicitado.class, Date.class, Date.class, Long.class};
	    
	public void addElement(RecursoSolicitado os) {
		Object rowData[] = new Object[5];
		rowData[0] = os;
		rowData[1] = os.getDataInicio(); 
		rowData[2] = os.getDataFim();
		rowData[3] = os.getQuantidade();
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
