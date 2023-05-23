package br.com.sne.sistema.gui.orcamento;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrcamentoTableModel extends SizedTableModel<Orcamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Contato", "Evento", "Data Orçamento", "Data Evento", "Valor Total", "Funcionário","Situação"};
	private String filterColumnName[] = {"Id", "Cliente", "Contato", "Evento", "X", "X", "X", "Funcionário","Situação"};
	private int [] columnWidth = { 10, 100, 80, 140, 80, 40, 40, 80, 20 };
	private Class<?> columnClass[] = {Long.class, String.class, String.class, Orcamento.class, String.class, String.class, String.class, String.class,String.class};
	
	public void addElement(Orcamento orc) {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
	    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	    SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		Object rowData[] = new Object[9];
		rowData[0] = orc.getIdPai()!= 0 ? new Long(orc.getIdPai()) : new Long(orc.getId());
		rowData[1] = orc.getCliente().getNome();
		rowData[2] = orc.getCliente().getContato();
		rowData[3] = orc;
		rowData[4] = orc.getDataOrcamento() == null? "" : formatoDataHora.format(orc.getDataOrcamento());
		rowData[5] = formatoData.format(orc.getDataInicio());
		rowData[6] = ("R$ " + formato.format(orc.getPreco().subtract(orc.getDesconto())));
		rowData[7] = orc.getFuncionario().getNome();
		rowData[8] = orc.getSituacao().name();
		
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
