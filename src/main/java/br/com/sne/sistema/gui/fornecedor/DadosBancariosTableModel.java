package br.com.sne.sistema.gui.fornecedor;

import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class DadosBancariosTableModel extends SizedTableModel<DadosBancarios> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Banco", "Agência", "Conta", "Fornecedor"};
	private String filterColumnName[] = {"Id", "Banco", "Agência", "Conta", "X"};
	private int [] columnWidth = { 40, 80, 60, 60, 80 };
	private Class<?> columnClass[] = {Long.class, DadosBancarios.class, String.class, String.class, Fornecedor.class};
	
	public void addElement(DadosBancarios cli) {
		Object rowData[] = new Object[5];
		Fornecedor forn = Facade.getInstance().carregarFornecedor(cli);
		if(forn != null) {
			rowData[0] = new Long(cli.getId());
			rowData[1] = cli;
			rowData[2] = cli.getAgencia();
			rowData[3] = cli.getConta();
			rowData[4] = Facade.getInstance().carregarFornecedor(cli);
			addRow(rowData);
		}
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
