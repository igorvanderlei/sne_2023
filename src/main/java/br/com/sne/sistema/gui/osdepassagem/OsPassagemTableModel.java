package br.com.sne.sistema.gui.osdepassagem;

import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.OsDePassagem;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OsPassagemTableModel extends SizedTableModel<OsDePassagem> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Data", "OS de Origem", "OS de Destino"};
	private String filterColumnName[] = {"Id", "Data", "OS de Origem", "OS de Destino"};
	private int [] columnWidth = { 40, 80, 200, 200 };
	private Class<?> columnClass[] = { OsDePassagem.class, String.class, String.class, String.class};
	
	public void addElement(OsDePassagem osp) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Object rowData[] = new Object[4];
		rowData[0] = osp;
		rowData[1] = formato.format(osp.getData());
		rowData[2] = osp.getOrigem().getNomeEvento();
		rowData[3] = osp.getDestino().getNomeEvento();
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
