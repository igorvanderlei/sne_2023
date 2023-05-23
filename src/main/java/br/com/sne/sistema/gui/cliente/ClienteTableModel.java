package br.com.sne.sistema.gui.cliente;

import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ClienteTableModel extends SizedTableModel<Cliente> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Tipo", "Razão Social/Nome", "CNPJ/Cpf", "Telefone", "Contato", "Celular", "Funcionário", "Status"};
	private String filterColumnName[] = {"Id", "Tipo", "Razão Social/Nome", "CNPJ/Cpf", "X", "Contato", "X", "Funcionário", "X"};
	private int [] columnWidth = { 40, 60, 150, 80, 80, 150, 80, 150, 80 };
	private Class<?> columnClass[] = {Long.class, String.class, Cliente.class, String.class, String.class, String.class, String.class, String.class, String.class};
	
	public void addElement(Cliente cli) {
		Object rowData[] = new Object[9];
		rowData[0] = new Long(cli.getId());
		rowData[1] = (cli.getTipo().equals("F"))?"Física":"Jurídica";
		rowData[2] = cli;
		rowData[3] = cli.getCnpj();
		rowData[4] = cli.getFone();
		rowData[5] = cli.getContato();
		rowData[6] = cli.getCelular();
		rowData[7] = (cli.getFuncionario()!=null)?cli.getFuncionario().getNome():"";
		rowData[8] = cli.getStatus().toString();
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 2;
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
