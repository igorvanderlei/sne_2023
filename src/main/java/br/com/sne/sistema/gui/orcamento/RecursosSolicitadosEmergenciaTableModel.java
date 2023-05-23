package br.com.sne.sistema.gui.orcamento;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursosSolicitadosEmergenciaTableModel extends SizedTableModel<RecursoSolicitado>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Nome",  "Quantidade", "Dia Inicial", "Dia Final"};
	private String filterColumnName[] = {""};
	private int [] columnWidth = { 200,  40, 60, 60};
	private Class<?> columnClass[] = {String.class, Integer.class, Date.class, Date.class};

	public void addElement(RecursoSolicitado rec) {
		Object rowData[] = new Object[4];
		rowData[0] = rec;
		rowData[1] = rec.getQuantidade();
		rowData[2] = rec.getDataInicio();
		rowData[3] = rec.getDataFim();
		addRow(rowData);
	}
	
	public List<RecursoSolicitado> getRecursos() {
		List<RecursoSolicitado> recursos = new ArrayList<RecursoSolicitado>();
		for(int i = 0; i < getRowCount(); i++) {
			RecursoSolicitado rec = (RecursoSolicitado) getValueAt(i, 0);
			rec.setQuantidade((Integer) getValueAt(i, 1));
			rec.setDataInicio((Date) getValueAt(i, 2));
			rec.setDataFim((Date) getValueAt(i, 3));
			recursos.add(rec);
		}
		return recursos;
	}
	
	public boolean isCellEditable(int arg0, int arg1) {
		return (arg1 >0 && arg1 < 4);
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
