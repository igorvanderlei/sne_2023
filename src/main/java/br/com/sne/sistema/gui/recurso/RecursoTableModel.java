package br.com.sne.sistema.gui.recurso;

import java.text.NumberFormat;
import java.util.Locale;
import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursoTableModel extends SizedTableModel<Recurso> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Tipo", "Grupo", "Código", "Nome", "Preço", "Valor Mínimo", "Preço de Custo"};
	private String filterColumnName[] =  {"Id", "Tipo", "Grupo", "Código", "Nome", "Preço", "Valor Mínimo", "Preço de Custo"};
	private int [] columnWidth = {20, 50, 50, 40, 400, 100, 100, 100};	
	private Class<?> columnClass[] = {Long.class, String.class, String.class, String.class, Recurso.class, String.class, String.class, String.class};
	
	
	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}
	
	public void addElement(Recurso rec) {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[8];
		rowData[0] = new Long(rec.getId());
		rowData[1] = rec.getGrupo().getTipoRecurso().toString();
		if(rec.getGrupo() != null)
			rowData[2] = rec.getGrupo().getNome();
		else
			rowData[2] = "";
		rowData[3] = rec.getCodigo();
		rowData[4] = rec;
		rowData[5] = "<html><b><font color='#008800'>R$ " + formato.format(rec.getPrecoSugerido())  + "</font></b>";
		rowData[6] = "<html><b><font color='#dd0000'>R$ "+ formato.format(rec.getValorMinimo()) + "</font></b>";
		rowData[7] = "<html><b><font color='#dd0000'>R$ "+ formato.format(rec.getPrecoCusto()) + "</font></b>";
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
