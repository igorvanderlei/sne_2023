package br.com.sne.sistema.gui.tipousuario;

import java.util.HashSet;
import java.util.Set;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class PermissaoTableModel extends SizedTableModel<permission>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {".", "Permissão", "Detalhes"};
	private int [] columnWidth = { 10, 400, 600 };
	private Class<?> columnClass[] = {Boolean.class, permission.class, String.class};
	
	public void addElement(permission p, boolean select) {
		Object rowData[] = new Object[3];
		rowData[0] = select;
		rowData[1] = p;
		rowData[2] = p.getDescricao();
		addRow(rowData);
	}
	
	public Set<permission> getPermissoesSelecionadas() {
		Set<permission> resp = new HashSet<permission>();
		for(int i =0; i < getRowCount(); i++) {
			Boolean sel = (Boolean) getValueAt(i, 0);
			if(sel.booleanValue()) {
				resp.add((permission) getValueAt(i, 1));
			}
		}
		return resp;
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

	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}

	public String[] getFilterColumnName() {
		return null;
	}

	public void addElement(permission e) {
		addElement(e, false);
	}
	
	public boolean isCellEditable(int arg0, int arg1) {
		return arg1 == 0;
	}

}
