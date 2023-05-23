package br.com.sne.sistema.gui.contagem;
import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.ContagemEstoque;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ContagemEstoqueTableModel extends SizedTableModel<ContagemEstoque> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Data", "Funcionário", "Grupo", "Recurso", "Quantidade Ausências"};
	private String filterColumnName[] = {"Data", "Funcionário", "Grupo", "Recurso", "Quantidade Ausências"};
	private int [] columnWidth = { 40, 150, 100, 100, 50};
	private Class<?> columnClass[] = {String.class, ContagemEstoque.class, String.class, String.class, Long.class};
	
	public void addElement(ContagemEstoque ct) {
		Object rowData[] = new Object[5];
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		rowData[0] = formato.format(ct.getDataContagem());
		rowData[1] = ct;
		rowData[2] = ct.getGrupo().getNome();
		rowData[3] = (ct.getRecurso() != null)?ct.getRecurso().getNome():"";
		rowData[4] = ct.getEquipamentosFaltantes().size();
		addRow(rowData);
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