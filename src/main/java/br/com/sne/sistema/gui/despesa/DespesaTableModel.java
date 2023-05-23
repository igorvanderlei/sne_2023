package br.com.sne.sistema.gui.despesa;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class DespesaTableModel extends SizedTableModel<Despesa> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Descrição",  "Vencimento", "Pagamento", "Centro de Custo", "Fornecedor/Freelancer", "Valor", "Situação"};
	private String filterColumnName[]= {"Id", "Descrição",  "X", "X", "Centro de Custo", "Fornecedor/Freelancer", "Valor", "Situação"};
	private int [] columnWidth = { 10, 100, 20, 20, 70, 70, 40, 40 };
	private Class<?> columnClass[] = {Long.class, Despesa.class, Date.class, Date.class, CentroCusto.class, String.class, BigDecimal.class, String.class};
	
	public void addElement(Despesa desp) {
		/*NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);*/
		
		Object rowData[] = new Object[8];
		rowData[0] = new Long(desp.getId());
		rowData[1] = desp;
		rowData[2] = desp.getDataVencimento();//formatoData.format(desp.getDataVencimento());
		rowData[3] = desp.getDataPagamento();//formatoData.format(desp.getDataVencimento());
		rowData[4] = desp.getCentroCusto();
		if(desp.getTipo().equals("J"))
			rowData[5] = desp.getFornecedor().getNome();
		else
			rowData[5] = desp.getFreelancer().getNome();
		rowData[6] = desp.getValor(); // "R$ " + formato.format(desp.getValor());
		rowData[7] = (desp.isSituacao())?"Pago":"Aberto";
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
