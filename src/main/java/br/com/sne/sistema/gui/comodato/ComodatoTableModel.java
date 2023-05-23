package br.com.sne.sistema.gui.comodato;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


import br.com.sne.sistema.bean.Comodato;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ComodatoTableModel extends SizedTableModel<Comodato> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Local", "Data", "Funcionário", "Status"};
	private String filterColumnName[] = {"Id", "Cliente", "Local", "Data", "Funcionário", "Status"};
	private int [] columnWidth = { 10, 120, 80, 160, 100, 60 };
	private Class<?> columnClass[] = {Comodato.class, String.class, String.class, String.class, String.class, String.class};
	
	public void addElement(Comodato os) {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
	    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		Object rowData[] = new Object[6];
		rowData[0] = os;
		rowData[1] = os.getCliente().getNome();
		rowData[2] = os.getLocal().getNome();
		rowData[3] = formatoData.format(os.getDataInicio());
		rowData[4] = (os.getFuncionario() != null)?os.getFuncionario().getNome():"";
		rowData[5] = "" + os.getStatus(); 
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
