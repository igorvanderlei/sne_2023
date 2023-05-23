package br.com.sne.sistema.gui.equipamentoenviado;
import java.util.ArrayList;
import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OSEquipamentoEnviadoTableModel extends SizedTableModel<Equipamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Código", "Recurso", "Marca", "Modelo"}; 
	private String filterColumnName[] = {"Código", "Recurso", "Marca", "Modelo"};
	private int [] columnWidth = { 20, 90, 50, 50 };
	private Class<?> columnClass[] = {Long.class, Equipamento.class, Long.class, Long.class};
	
	public void addElement(Equipamento eq) {
		Object rowData[] = new Object[4];
		rowData[0] = eq;
		rowData[1] = eq.getDescricaoEquipamento().getNome();
		rowData[2] = eq.getMarca();
		rowData[3] = eq.getModelo();
		addRow(rowData);
	}
	
	public List<Equipamento> getEquipamentos() {
		List<Equipamento> lista = new ArrayList<Equipamento>();
		for(int i = 0; i < getRowCount(); i++) {
			Equipamento e = (Equipamento) getValueAt(i, 0);
			lista.add(e);
		}
		return lista;
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
