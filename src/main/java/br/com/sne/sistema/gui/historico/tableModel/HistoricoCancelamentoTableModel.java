package br.com.sne.sistema.gui.historico.tableModel;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import br.com.sne.sistema.bean.HistoricoCancelamento;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class HistoricoCancelamentoTableModel extends SizedTableModel<HistoricoCancelamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Evento", "Descrição",  "Vencimento", "Valor", "Valor Pago", "Funcionário Cancelamento", "Data Cancelamento"};
	private String filterColumnName[]= {"Id", "Cliente", "Evento", "Descrição",  "Vencimento", "X", "X", "Funcionário Cancelamento", "Data Cancelamento"};
	private int [] columnWidth = { 10, 100, 120, 100, 40, 40, 200, 40 };
	private Class<?> columnClass[] = {HistoricoCancelamentoTableModel.class, String.class, String.class, String.class, Date.class, String.class, String.class, String.class, Date.class};
	
	public void addElement(HistoricoCancelamento rec) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[9];
		rowData[0] = rec.getReceita();
		rowData[1] = rec.getReceita().getOrdemServico().getCliente().getNome();
		rowData[2] = rec.getReceita().getOrdemServico().getNomeEvento();
		rowData[3] = rec.getReceita().getDescricao();
		rowData[4] = rec.getReceita().getDataVencimento();
		rowData[5] = "R$ " + formato.format(rec.getReceita().getValor());
		rowData[6] = "R$ " + formato.format(rec.getReceita().getValorPago());
		rowData[7] = rec.getFuncionario().getNome();
		rowData[8] = rec.getData();
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 0;
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
