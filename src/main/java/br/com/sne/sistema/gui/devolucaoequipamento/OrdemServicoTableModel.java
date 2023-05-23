package br.com.sne.sistema.gui.devolucaoequipamento;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrdemServicoTableModel extends SizedTableModel<OrdemServico> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Evento", "Data Fim", "Hora Fim", "Local"};
	private String filterColumnName[] = {"Id", "Cliente", "Evento", "X", "X", "Local"};
	private int [] columnWidth = { 4, 100, 180, 40, 40, 80 };
	private Class<?> columnClass[] = {Long.class, String.class, OrdemServico.class, Date.class, String.class, String.class};
	
	public void addElement(OrdemServico os) {
		SimpleDateFormat formatoHora = new SimpleDateFormat("mm:ss");
		Object rowData[] = new Object[6];
		rowData[0] = new Long(os.getId());
		rowData[1] = os.getCliente().getNome();
		rowData[2] = os;
		rowData[3] = os.getDataFim();
		rowData[4] = formatoHora.format(os.getHoraFim());
		rowData[5] = os.getLocal().getNome() ;
		addRow(rowData);
	}
	
	public int getObjectIndex() {
		return 2;
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
