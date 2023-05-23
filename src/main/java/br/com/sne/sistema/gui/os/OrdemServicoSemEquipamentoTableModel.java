package br.com.sne.sistema.gui.os;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.OrdemServicoSemEquipamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrdemServicoSemEquipamentoTableModel extends SizedTableModel<OrdemServicoSemEquipamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Id (Emergencial)", "Cliente", "Local", "Evento", "Funcionário", "Status"};
	private String filterColumnName[] = {"Id", "Id (Emergencial)", "Cliente", "Local", "Evento", "Funcionário", "Status"};
	private int [] columnWidth = { 30, 50, 120, 100, 100,100 };
	private Class<?> columnClass[] = {Long.class, Long.class, String.class, String.class, OrdemServico.class, String.class, String.class};
	
	public void addElement(OrdemServicoSemEquipamento os) {
		Object rowData[] = new Object[7];
		rowData[0] = new Long(os.getId());
		rowData[1] = new Long(os.getOrdemServicoEmergencial().getId());
		rowData[2] = os.getOrdemServicoEmergencial().getCliente().getNome();
		rowData[3] = os.getOrdemServicoEmergencial().getLocal().getNome();
		rowData[4] = os;
		rowData[5] = os.getFuncionario().getNome();
		rowData[6] = "" + os.getStatus(); 
		addRow(rowData);
	}
	
	
	public int getObjectIndex() {
		return 4;
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
