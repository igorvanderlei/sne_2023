package br.com.sne.sistema.gui.agendarrecolhimento;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Recolhimento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecolhimentoTableModel extends SizedTableModel<Recolhimento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Responsável", "Placa", "Ordens de Serviço"};
	private String filterColumnName[] = {"Id", "Responsável", "Placa", "Ordens de Serviço"};
	private int [] columnWidth = { 40, 300, 120, 120 };
	private Class<?> columnClass[] = {Long.class, Recolhimento.class, String.class, String.class};
	
	public void addElement(Recolhimento r) {
		Object rowData[] = new Object[4];
		rowData[0] = new Long(r.getId());
		rowData[1] = r;
		String oss = "";
		for(OrdemServico os : r.getOrdemServico()) {
			oss = oss + " " + os.getId();
		}
		rowData[2] = r.getVeiculo();;
		rowData[3] = oss;
		addRow(rowData);
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
}
