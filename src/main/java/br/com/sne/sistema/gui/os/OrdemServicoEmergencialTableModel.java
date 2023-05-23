package br.com.sne.sistema.gui.os;

import java.util.ArrayList;
import java.util.List;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrdemServicoEmergencialTableModel extends SizedTableModel<OrdemServico> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Local", "Evento", "Funcionário", "Status"};
	private String filterColumnName[] = {"Id", "Cliente", "Local", "Evento", "Funcionário", "Status"};
	private int [] columnWidth = { 30, 120, 100, 100,100 };
	private Class<?> columnClass[] = {Long.class, String.class, String.class, OrdemServico.class, String.class, String.class};
	
	public void addElement(OrdemServico os) {
		Object rowData[] = new Object[6];
		rowData[0] = new Long(os.getId());
		rowData[1] = os.getCliente().getNome();
		rowData[2] = os.getLocal().getNome();
		rowData[3] = os;
		rowData[4] = os.getFuncionario().getNome();
		rowData[5] = "" + os.getStatus(); 
		addRow(rowData);
	}
	
	public List<OrdemServico> getListaOs() {
		List<OrdemServico> ordens = new ArrayList<OrdemServico>();
		for(int i = 0; i < getRowCount(); i++) {
			OrdemServico rec = (OrdemServico) getValueAt(i, 3);
			ordens.add(rec);
		}
		return ordens;
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
