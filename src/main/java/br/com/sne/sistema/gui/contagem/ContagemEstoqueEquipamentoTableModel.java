package br.com.sne.sistema.gui.contagem;

import java.util.ArrayList;
import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ContagemEstoqueEquipamentoTableModel extends SizedTableModel<Equipamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Código", "Recurso", "Marca", "Status"};
	private String filterColumnName[] = {"Código", "Recurso", "Marca", "Status"};
	private int [] columnWidth = { 40, 100, 50, 50 };
	private Class<?> columnClass[] = {Equipamento.class, String.class, String.class, String.class};
	
	public void addElement(Equipamento eq) {
		Object rowData[] = new Object[4];
		rowData[0] = eq;
		rowData[1] = eq.getDescricaoEquipamento().getNome();
		rowData[2] = eq.getMarca();
		rowData[3] = "" + eq.getStatus();
		addRow(rowData);
	}
	
	public void removeElement(Equipamento eq) {
		for(int i = 0; i < getRowCount(); i ++) {
			Equipamento eq2 = (Equipamento) getValueAt(i, getObjectIndex());
			if(eq2.getId() == eq.getId()) {
				removeRow(i);
				break;
			}
		}
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