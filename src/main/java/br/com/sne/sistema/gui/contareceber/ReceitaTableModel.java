package br.com.sne.sistema.gui.contareceber;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class ReceitaTableModel extends SizedTableModel<Receita> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Vendedor", "Evento", "Descrição",  "Faturado", "Vencimento", "Valor", "Valor Pago", "Situação"};
	private String filterColumnName[]= {"Id", "Cliente", "Vendedor", "Evento", "Descrição", "Faturado",  "X", "X", "X", "Situação"};
	private int [] columnWidth = { 10, 100, 100, 120, 100, 20, 40, 40, 40 };
	private Class<?> columnClass[] = {Receita.class, String.class, String.class, String.class, String.class, String.class, Date.class, BigDecimal.class, BigDecimal.class, String.class};
	
	public void addElement(Receita rec) {
		/*NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);*/
		
		Object rowData[] = new Object[10];
		rowData[0] = rec;
		rowData[1] = rec.getOrdemServico().getCliente().getNome();
		rowData[2] = rec.getOrdemServico().getFuncionario().getNome();
		rowData[3] = "" + rec.getOrdemServico().getId() + " - " + rec.getOrdemServico().getNomeEvento();
		rowData[4] = rec.getDescricao();
		rowData[5] = rec.isFaturado()?"Sim":"Não";
		
		rowData[6] = rec.getDataVencimento();
		rowData[7] = rec.getValor();//"R$ " + formato.format(rec.getValor());
		rowData[8] = rec.getValorPago(); //(rec.getValorPago()!=null)?"R$ " + formato.format(rec.getValorPago()):"R$ 0,00";
		rowData[9] = (rec.isSituacao())?"Pago":"Aberto";
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
