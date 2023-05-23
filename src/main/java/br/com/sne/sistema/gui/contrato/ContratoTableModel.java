package br.com.sne.sistema.gui.contrato;

import br.com.sne.sistema.bean.Contrato;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ContratoTableModel extends SizedTableModel<Contrato> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id","Evento","Cliente","Funcionário", "Tipo"};
	private String filterColumnName[] = {"Id","Evento","Cliente","Funcionário", "Tipo"};
	private int [] columnWidth = { 40, 100,100,100,50};
	private Class<?> columnClass[] = {Long.class, Contrato.class,String.class,String.class,String.class};
	
	public void addElement(Contrato cli) {
		Object rowData[] = new Object[5];
		rowData[0] = new Long(cli.getId());
		rowData[1] = cli;
		rowData[2] = cli.getOrdemServico().getCliente().getNome();
		rowData[3] = cli.getOrdemServico().getFuncionario().getNome();
		rowData[4] = cli.getTipo().name().replace("_", " ");
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
