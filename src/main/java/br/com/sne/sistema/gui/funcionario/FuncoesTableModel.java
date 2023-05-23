package br.com.sne.sistema.gui.funcionario;
import java.util.HashSet;
import java.util.Set;

import javax.swing.table.DefaultTableModel;
import br.com.sne.sistema.bean.Funcao;

public class FuncoesTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Ativo", "Função"};
	private Class<?> columnClass[] = {Boolean.class, Funcao.class};
	
	
	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}
	
	public void addFuncao(Funcao func, boolean selecionada) {
		Object rowData[] = new Object[2];
		rowData[0] = new Boolean(selecionada);
		rowData[1] = func;
		addRow(rowData);
	}
	
	public Set<Funcao> getFuncoesSelecionadas() {
		Set<Funcao> resp = new HashSet<Funcao>();
		for(int i =0; i < getRowCount(); i++) {
			Boolean sel = (Boolean) getValueAt(i, 0);
			if(sel.booleanValue()) {
				resp.add((Funcao) getValueAt(i, 1));
			}
		}
		return resp;
	}
	
	public void removeAllElements() {
		while(getRowCount() > 0) {
			 removeRow(getRowCount() - 1); 
		}
	}
	public boolean isCellEditable(int row, int column) {
		if(column == 0)
			return true;
		return false;
	}
	
	
}
