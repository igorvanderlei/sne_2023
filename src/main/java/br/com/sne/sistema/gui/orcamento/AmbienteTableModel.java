package br.com.sne.sistema.gui.orcamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class AmbienteTableModel extends SizedTableModel<AmbienteEvento>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Nome", "Data Inicial", "Data Fim"};
	private String filterColumnName[] = {""};
	private int [] columnWidth = { 200, 40, 40};
	private Class<?> columnClass[] = {AmbienteEvento.class, Date.class, Date.class};

	public void addElement(AmbienteEvento rec) {
		Object rowData[] = new Object[3];
		rowData[0] = rec;
		rowData[1] = rec.getDataInicio();
		rowData[2] = rec.getDataFim();
		addRow(rowData);
	}
	
	public List<AmbienteEvento> getAmbientes() {
		List<AmbienteEvento> recursos = new ArrayList<AmbienteEvento>();
		for(int i = 0; i < getRowCount(); i++) {
			AmbienteEvento rec = (AmbienteEvento) getValueAt(i, 0);
			rec.setDataInicio((Date) getValueAt(i, 1));
			rec.setDataFim((Date) getValueAt(i, 2));
			recursos.add(rec);
		}
		return recursos;
	}
	
	public void alterarDataInicial(Date dataInicio) {
		for(int i = 0; i < getRowCount(); i++) {
			setValueAt(dataInicio, i, 1);
		}
		fireTableDataChanged();
	}
	
	public void alterarDataFinal(Date dataFim) {
		for(int i = 0; i < getRowCount(); i++) {
			setValueAt(dataFim, i, 2);
		}
		fireTableDataChanged();
	}
	
	
	public boolean isCellEditable(int arg0, int arg1) {
		return (arg1 >0);
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
