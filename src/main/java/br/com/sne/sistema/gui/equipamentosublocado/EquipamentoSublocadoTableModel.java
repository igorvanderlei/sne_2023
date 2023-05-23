package br.com.sne.sistema.gui.equipamentosublocado;
import java.util.ArrayList;
import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoSublocado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class EquipamentoSublocadoTableModel extends SizedTableModel<EquipamentoSublocado> {
	private static final long serialVersionUID = 1L;
	private String [] columnName = {"Id", "Grupo", "Recurso", "Código", "Marca", "Modelo", "Número de Série", "Status"};
	private String[] filterColumnName = {"Id", "Grupo", "Recurso", "Código", "Marca", "Modelo", "Número de Série", "Status"};
	private int[] columnWidth = {20, 150, 200, 120, 100, 100, 100, 100};
	private Class<?> columnClass[] = {Long.class, String.class, String.class, Equipamento.class, String.class, String.class, String.class, String.class};
	
	public int getColumnCount() {
		return columnName.length;
	}
	
	public List<EquipamentoSublocado> getEquipamentos() {
		List<EquipamentoSublocado> lista = new ArrayList<EquipamentoSublocado>();
		for(int i=0; i < getRowCount(); i++){
			EquipamentoSublocado eq = (EquipamentoSublocado) getValueAt(i, getObjectIndex());
			String marca = (String)getValueAt(i, 4);
			if(marca == null)
				marca = "";
			eq.setMarca(marca);
			String modelo = (String)getValueAt(i, 5);
			if(modelo == null)
				modelo = "";
			eq.setModelo(modelo);
			String serial = (String)getValueAt(i, 6);
			if(serial == null)
				serial = "";
			eq.setSerialEquipamento(serial);
			lista.add(eq);
		}
		return lista;
	}
	
	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}
	
	public int[] getColumnWidth() {
		return columnWidth;
	}

	public String[] getFilterColumnName() {
		return filterColumnName;
	}

	public int getObjectIndex() {
		return 3;
	}

	public void addElement(EquipamentoSublocado e) {
		Object rowData[] = new Object[8];
		rowData[0] = new Long(e.getId());
		rowData[1] = e.getGrupo().getNome();
		rowData[2] = e.getDescricaoEquipamento().getNome();
		rowData[3] = e;
		rowData[4] = e.getMarca();
		rowData[5] = e.getModelo();
		rowData[6] = e.getSerialEquipamento();
		rowData[7] = e.getStatus().toString();
		addRow(rowData);
	}

	public boolean isCellEditable(int arg0, int arg1) {
		return arg1>=4 && arg1<=6;
	}
	
	
}
