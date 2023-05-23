package br.com.sne.sistema.gui.util.components;

import javax.swing.table.DefaultTableModel;

public abstract class SizedTableModel <T> extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	public abstract int [] getColumnWidth();
	public abstract String [] getFilterColumnName();
	public abstract void addElement(T r); 
	public abstract int getObjectIndex();
	
	public void removeAllElements() {
		setRowCount(0);
		/*while(getRowCount() > 0) {
			 removeRow(getRowCount() - 1); 
		}*/
	}
	
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

}
