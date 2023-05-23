package br.com.sne.sistema.gui.googlecalendar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import br.com.sne.sistema.bean.Funcionario;

public class FuncionarioTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Escolha", "Nome"};
	private Class<?> columnClass[] = {Boolean.class, Funcionario.class};
	
	public void addFuncionario(Funcionario grp,boolean selecionado) {
		Object rowData[] = new Object[2];
		rowData[0] = new Boolean(selecionado);
		rowData[1] = grp;

		addRow(rowData);
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

	public List<Funcionario> listarFuncionariosSelecionados() {
		List<Funcionario> resp = new ArrayList<Funcionario>();
		for(int i =0; i < getRowCount(); i++) {
			Boolean sel = (Boolean) getValueAt(i, 0);
			if(sel.booleanValue()) {
				resp.add((Funcionario) getValueAt(i, 1));
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
