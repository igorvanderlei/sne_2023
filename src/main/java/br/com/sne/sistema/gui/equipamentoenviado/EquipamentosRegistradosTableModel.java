package br.com.sne.sistema.gui.equipamentoenviado;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class EquipamentosRegistradosTableModel extends SizedTableModel<EquipamentoEnviado> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Código", "Recurso", "Estoquista", "Motorista", "Data Envio", "Devolvido"};
	private String filterColumnName[] = {"Código", "Recurso", "Estoquista", "Motorista", "Data Envio", "Devolvido"};
	private int [] columnWidth = { 30, 90, 50, 50,50 , 50};
	private Class<?> columnClass[] = {Long.class, EquipamentoEnviado.class, Long.class, Long.class, Date.class, String.class};
	
	public void addElement(EquipamentoEnviado r) {
		Object rowData[] = new Object[6];
		rowData[0] = r.getEquipamento().getNumeroSerie();
		rowData[1] = r.getEquipamento().getDescricaoEquipamento().getNome();
		rowData[2] = r.getUsuario().getFuncionario().getNome();
		if(r.getFuncionarioEntrega() != null)
			rowData[3] = r.getFuncionarioEntrega().getNome();
		else 
			rowData[3] = "";
		rowData[4] = r.getDataSaida();
		rowData[5] = r.isStatus()?"Sim":"Não";
		addRow(rowData);
	}
	
	public List<EquipamentoEnviado> getEquipamentos() {
		List<EquipamentoEnviado> lista = new ArrayList<EquipamentoEnviado>();
		for(int i = 0; i < getRowCount(); i++) {
			EquipamentoEnviado e = (EquipamentoEnviado) getValueAt(i, 0);
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
