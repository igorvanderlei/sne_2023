package br.com.sne.sistema.gui.equipamentoenviado;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.sne.sistema.bean.PessoalAlocado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class PessoalAlocadoTableModel extends SizedTableModel<PessoalAlocado> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Nome", "Dt. Início", "Dt. Fim"};
	private String filterColumnName[] = {"Nome", "Dt. Início", "Dt. Fim"};
	private int [] columnWidth = {150, 40, 40 };
	private Class<?> columnClass[] = {PessoalAlocado.class, Date.class, Date.class};
	
	public void addElement(PessoalAlocado r) {
		Object rowData[] = new Object[3];
		rowData[0] = r;
		rowData[1] = r.getDataInicio();
		rowData[2] = r.getDataFim(); 
		addRow(rowData);
	}
	
	public List<PessoalAlocado> getPessoalAlocado() {
		List<PessoalAlocado> lista = new ArrayList<PessoalAlocado>();
		for(int i = 0; i < getRowCount(); i++) {
			PessoalAlocado e = (PessoalAlocado) getValueAt(i, 0);
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
