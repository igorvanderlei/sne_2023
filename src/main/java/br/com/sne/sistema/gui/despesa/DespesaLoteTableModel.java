package br.com.sne.sistema.gui.despesa;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class DespesaLoteTableModel extends SizedTableModel<Despesa> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Descrição",  "Vencimento", "Centro de Custo", "Fornecedor", "Valor", "Situação", "Pagar"};
	private String filterColumnName[]= {"Id", "Descrição",  "X", "Centro de Custo", "Fornecedor/Freelancer", "Valor", "Situação", "X"};
	private int [] columnWidth = { 10, 100, 20, 70, 70, 40, 40, 5};
	private Class<?> columnClass[] = {Long.class, Despesa.class, Date.class, String.class, String.class, BigDecimal.class, String.class, Boolean.class};
	
	public void addElement(Despesa desp) {
		/*NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);*/
		
		Object rowData[] = new Object[8];
		rowData[0] = new Long(desp.getId());
		rowData[1] = desp;
		rowData[2] = desp.getDataVencimento();//formatoData.format(desp.getDataVencimento());
		rowData[3] = desp.getCentroCusto().getNome();
		if(desp.getTipo().equals("J"))
			rowData[4] = desp.getFornecedor().getNome();
		else
			rowData[4] = desp.getFreelancer().getNome();
		
		rowData[5] = desp.getValor(); // "R$ " + formato.format(desp.getValor());
		rowData[6] = (desp.isSituacao())?"Pago":"Aberto";
		rowData[7] = false;
		addRow(rowData);
	}
	
	public List<Despesa> getDespesasSelecionadas() {
		List<Despesa> selecionadas = new ArrayList<Despesa>();
		for(int i = 0; i < getRowCount(); i++) {
			if(((Boolean)getValueAt(i, 7)).booleanValue()) {
				Despesa rec = (Despesa) getValueAt(i, getObjectIndex());
				selecionadas.add(rec);
			}
		}
		return selecionadas;
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

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return arg1==7;
	}
	
	




}
