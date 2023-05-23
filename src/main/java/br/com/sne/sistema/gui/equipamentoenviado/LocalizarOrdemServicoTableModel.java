package br.com.sne.sistema.gui.equipamentoenviado;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class LocalizarOrdemServicoTableModel extends SizedTableModel<OrdemServico> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Evento", "Data Montagem", "Data Início", "Vendedor", "Status"};
	private String filterColumnName[] = {"Id", "Evento", "X", "X", "Vendedor", "Status"};
	private int [] columnWidth = { 4, 180, 40, 40, 150, 40};
	private Class<?> columnClass[] = {Long.class, OrdemServico.class, Date.class, Date.class, String.class, String.class};
	
	public void addElement(OrdemServico os) {
		Object rowData[] = new Object[6];
		rowData[0] = new Long(os.getId());
		rowData[1] = os;
		rowData[2] = os.getDataMontagem();
		rowData[3] = os.getDataInicio();
		rowData[4] = os.getFuncionario();
		rowData[5] = os.getStatus();
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
