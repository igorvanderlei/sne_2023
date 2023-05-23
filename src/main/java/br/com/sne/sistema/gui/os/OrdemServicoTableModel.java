package br.com.sne.sistema.gui.os;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrdemServicoTableModel extends SizedTableModel<OrdemServico> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Local", "Evento", "Data", "Valor Total", "Funcionário", "Status"};
	private String filterColumnName[] = {"Id", "Cliente", "Local", "Evento", "Data", "X", "Funcionário", "Status"};
	private int [] columnWidth = { 10, 120, 80, 160, 40, 40, 100 };
	private Class<?> columnClass[] = {Long.class, String.class, String.class, OrdemServico.class, String.class, String.class, String.class, String.class};
	
	public void addElement(OrdemServico os) {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
	    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		Object rowData[] = new Object[8];
		rowData[0] = new Long(os.getId());
		rowData[1] = os.getCliente().getNome();
		rowData[2] = os.getLocal().getNome();
		rowData[3] = os;
		rowData[4] = formatoData.format(os.getDataInicio());
		rowData[5] = ("R$ " + formato.format(os.getPreco().subtract(os.getDesconto())));
		rowData[6] = (os.getFuncionario() != null)?os.getFuncionario().getNome():"";
		rowData[7] = "" + os.getStatus(); 
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 3;
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
