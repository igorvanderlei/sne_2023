package br.com.sne.sistema.gui.equipamentoenviado;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrdemServicoTableModel extends SizedTableModel<OrdemServico> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Evento", "Data Montagem", "Hora Montagem", "Data Início", "Local", "Bairro", "Logradouro", "Funcionário"};
	private String filterColumnName[] = {"Id", "Cliente", "Evento", "X", "X", "X", "Local", "Bairro", "Logradouro", "Funcionário"};
	private int [] columnWidth = { 4, 100, 180, 40, 40, 40, 150, 100, 120, 80 };
	private Class<?> columnClass[] = {Long.class, String.class, OrdemServico.class, Date.class, String.class, Date.class, String.class, String.class, String.class, String.class};
	
	public void addElement(OrdemServico os) {
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		Object rowData[] = new Object[10];
		rowData[0] = new Long(os.getId());
		rowData[1] = os.getCliente().getNome();
		rowData[2] = os;
		rowData[3] = os.getDataMontagem();
		rowData[4] = formatoHora.format(os.getHoraMontagem());
		System.out.println(os.getHoraMontagem());
		System.out.println(formatoHora.format(os.getHoraMontagem()));
		rowData[5] = os.getDataInicio();
		rowData[6] = os.getLocal().getNome() ;
		rowData[7] = os.getLocal().getEndereco().getBairro() ;
		rowData[8] = os.getLocal().getEndereco().getLogradouro();
		rowData[9] = os.getFuncionario().getNome();
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
