package br.com.sne.sistema.gui.orcamento;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RecursoTerceirizadoSolicitado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursosTerceirizadosSolicitadosTableModel extends SizedTableModel<RecursoTerceirizadoSolicitado>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id","Descrição","Valor Unitário", "Valor SNE", "Valor Fornecedor","Quantidade", "Dia Inicial", "Dia Final", "Subtotal SNE", "Subtotal Fornecedor"};
	private String filterColumnName[] = {""};
	private int [] columnWidth = {10, 100,40,40, 40,60, 60, 60, 40,40 };
	private Class<?> columnClass[] = {String.class,String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class,Integer.class, Date.class, Date.class, String.class, String.class};
	private boolean diarias;

	public RecursosTerceirizadosSolicitadosTableModel(boolean diarias) {
		this.diarias = diarias;
	}
	public void addElement(RecursoTerceirizadoSolicitado rec) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[10];
		rowData[0] = rec;
		rowData[1] = rec.getDescricao();
		rowData[2] = rec.getPrecoUnitario();
		rowData[3] = rec.getPrecoEmpresa();
		rowData[4] = rec.getPrecoFornecedor();
		rowData[5] = rec.getQuantidade();
		rowData[6] = rec.getDataInicio();
		rowData[7] = rec.getDataFim();
		rowData[8] = "<html><b>R$ " + formato.format(rec.getSubTotal(diarias))+ "</p>";
		rowData[9] = "<html><b>R$ " + formato.format(rec.getSubTotalFornecedor(diarias))+ "</p>";

		addRow(rowData);
	}
	
	public List<RecursoTerceirizadoSolicitado> getRecursos() {
		List<RecursoTerceirizadoSolicitado> recursos = new ArrayList<RecursoTerceirizadoSolicitado>();
		for(int i = 0; i < getRowCount(); i++) {
			RecursoTerceirizadoSolicitado rec = (RecursoTerceirizadoSolicitado) getValueAt(i, 0);
			rec.setDescricao((String) getValueAt(i,1));
			rec.setPrecoUnitario((BigDecimal) getValueAt(i, 2));
			rec.setPrecoEmpresa((BigDecimal) getValueAt(i, 3));
			rec.setPrecoFornecedor((BigDecimal)getValueAt(i, 4));
			rec.setQuantidade((Integer)getValueAt(i, 5));
			rec.setDataInicio((Date) getValueAt(i, 6));
			rec.setDataFim((Date) getValueAt(i, 7));
			rec.setSubTotal(rec.getSubTotal(diarias));
			recursos.add(rec);
		}
		return recursos;
	}
	
	public void alterarDatas(Date dataInicio, Date dataFim) {
		for(int i = 0; i < getRowCount(); i++) {
			setValueAt(dataInicio, i, 6);
			setValueAt(dataFim, i, 7);
		}
		atualizarSubtotais();
		fireTableDataChanged();
	}
	

	
	public void atualizarSubtotais() {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);

		for(int i = 0; i < getRowCount(); i++) {
			RecursoTerceirizadoSolicitado rec = (RecursoTerceirizadoSolicitado) getValueAt(i, 0);
			rec.setDescricao((String) getValueAt(i,1));
			rec.setPrecoUnitario((BigDecimal) getValueAt(i, 2));
			rec.setPrecoEmpresa((BigDecimal) getValueAt(i, 3));
			rec.setPrecoFornecedor((BigDecimal)getValueAt(i, 4));
			rec.setQuantidade((Integer)getValueAt(i, 5));
			rec.setDataInicio((Date) getValueAt(i, 6));
			rec.setDataFim((Date) getValueAt(i, 7));
			rec.setSubTotal(rec.getSubTotal(diarias));
			setValueAt("<html><b>R$ " + formato.format(rec.getSubTotal(diarias))  + "</b>", i, 8);
			setValueAt("<html><b>R$ " + formato.format(rec.getSubTotalFornecedor(diarias))  + "</b>", i, 9);
		}
	}
	
	
	public BigDecimal calcularTotalRecursos() {
		BigDecimal total = new BigDecimal(0);
		for(RecursoTerceirizadoSolicitado r: getRecursos()) {
			total = total.add(r.getSubTotal(diarias));
		}
		return total;
	}
	
	public BigDecimal calcularTotalRecursosFornecedor() {
		BigDecimal total = new BigDecimal(0);
		for(RecursoTerceirizadoSolicitado r: getRecursos()) {
			total = total.add(r.getSubTotalFornecedor(diarias));
		}
		return total;
	}

	
	public boolean isCellEditable(int arg0, int arg1) {
		return ((arg1 >4 && arg1 < 8) || arg1 == 2 || arg1 == 1);
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
