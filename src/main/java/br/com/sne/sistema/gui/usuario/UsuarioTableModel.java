package br.com.sne.sistema.gui.usuario;

import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class UsuarioTableModel extends SizedTableModel<Usuario> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Funcionário", "Tipo de Usuário", "Usuário"};
	private String filterColumnName[] = {"Id", "Funcionário", "Tipo de Usuário", "Usuário"};
	private int [] columnWidth = { 40, 200, 100,200 };
	private Class<?> columnClass[] = {Long.class, String.class,String.class, Usuario.class};
	
	public void addElement(Usuario grp) {
		Object rowData[] = new Object[4];
		rowData[0] = new Long(grp.getId());
		rowData[1] = grp.getFuncionario().getNome();
		rowData[2] = grp.getTipoUsuario().getNome();
		rowData[3] = grp;
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 3;
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
