package br.com.sne.sistema.gui.receita;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ReceitaTableModel extends SizedTableModel<Receita> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Vencimento", "Descrição", "Valor", "Situação"};
	private String filterColumnName[]= {"Id", "X", "Descrição", "Valor", "Situação"};
	private int [] columnWidth = { 10, 70, 80, 40, 40 };
	private Class<?> columnClass[] = {Receita.class, Date.class, String.class, String.class, String.class};
	
	public void addElement(Receita rec) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[6];
		rowData[0] = rec;
		rowData[1] = rec.getDataVencimento();
		rowData[2] = rec.getDescricao();
		rowData[3] = "R$ " + formato.format(rec.getValor());
		rowData[4] = (rec.isSituacao())?"Pago":"Aberto";
		addRow(rowData);
	}
	
	public List<Receita> getListaReceita() {
		List<Receita> lista = new ArrayList<Receita>();
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		for(int i=0; i < getRowCount(); i++) {
			Receita r = (Receita) getValueAt(i, 0);
			try {
				BigDecimal valor = new BigDecimal(formato.parse(((String) getValueAt(i, 3)).replace("R$ ", "")).doubleValue());
				r.setValor(valor.setScale(2,RoundingMode.HALF_EVEN));
				r.setDataVencimento((Date) getValueAt(i, 1));
				r.setDescricao((String) getValueAt(i, 2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			lista.add(r);
		}
		
		return lista;
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
	
	public boolean isCellEditable(int arg0, int arg1) {
		if(!((Receita)getValueAt(arg0, getObjectIndex())).isSituacao())
			return arg1 >=1 && arg1 <= 3;
		else
			return false;
	}
}
