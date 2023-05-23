package br.com.sne.sistema.gui.recurso;

import java.text.NumberFormat;
import java.util.Locale;
import br.com.sne.sistema.bean.RecursoTerceirizado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursoTerceirizadoTableModel extends SizedTableModel<RecursoTerceirizado> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Grupo","Fornecedor", "Código", "Nome", "Preço Empresa", "Preço Fornecedor"};
	private String filterColumnName[] =  {"Id", "Grupo","Fornecedor", "Código", "Nome", "Preço Empresa", "Preço Fornecedor"};
	private int [] columnWidth = {20, 50, 50, 40, 400, 100, 100};	
	private Class<?> columnClass[] = {Long.class, String.class,String.class, String.class, RecursoTerceirizado.class, String.class, String.class};
	
	
	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}
	
	public void addElement(RecursoTerceirizado rec) {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[9];
		rowData[0] = new Long(rec.getId());
		
		if(rec.getGrupo() != null)
			rowData[1] = rec.getGrupo().getNome();
		else
			rowData[1] = "";
		rowData[2] = rec.getFornecedorTerceirizado();
		rowData[3] = rec.getCodigo();
		rowData[4] = rec;
		rowData[5] = "<html><b><font color='#008800'>R$ " + formato.format(rec.getPrecoEmpresa())  + "</font></b>";
		rowData[6] = "<html><b><font color='#dd0000'>R$ "+ formato.format(rec.getPrecoFornecedor()) + "</font></b>";
		addRow(rowData);
	}

	public int[] getColumnWidth() {
		return columnWidth;
	}

	public String[] getFilterColumnName() {
		return filterColumnName;
	}

	public int getObjectIndex() {
		return 4;
	}

	
	
}
