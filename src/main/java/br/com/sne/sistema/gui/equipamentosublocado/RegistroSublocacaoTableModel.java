package br.com.sne.sistema.gui.equipamentosublocado;
import java.text.SimpleDateFormat;

import br.com.sne.sistema.bean.RegistroSublocacao;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RegistroSublocacaoTableModel extends SizedTableModel<RegistroSublocacao> {
	private static final long serialVersionUID = 1L;
	private String [] columnName = {"Id", "Fornecedor", "Data Início", "Data Fim", "Status"};
	private String[] filterColumnName = {"Id", "Fornecedor", "Data Início", "Data Fim", "Status"};
	private int[] columnWidth = {20, 500, 120, 120, 160};
	private Class<?> columnClass[] = {Long.class, RegistroSublocacao.class, String.class, String.class, String.class};
	
	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public Class<?> getColumnClass(int arg0) {
		return columnClass[arg0];
	}
	
	public int[] getColumnWidth() {
		return columnWidth;
	}

	public String[] getFilterColumnName() {
		return filterColumnName;
	}

	public int getObjectIndex() {
		return 1;
	}

	@Override
	public void addElement(RegistroSublocacao e) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyyy");
		Object rowData[] = new Object[5];
		rowData[0] = new Long(e.getId());
		rowData[1] = e;
		rowData[2] = (e.getDataInicio()==null)?"":formato.format(e.getDataInicio());
		rowData[3] = (e.getDataFim()==null)?"":formato.format(e.getDataFim());
		rowData[4] = (e.isFinalizada())?"Finalizada":"Pendente";
		addRow(rowData);
	}
}
