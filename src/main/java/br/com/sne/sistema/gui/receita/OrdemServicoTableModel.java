package br.com.sne.sistema.gui.receita;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.DateFormat;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class OrdemServicoTableModel extends SizedTableModel<OrdemServico> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Cliente", "Local", "Evento", "Data Início",  "Valor Total", "Vendedor", "Status", "Faturada"};
	private String filterColumnName[] = {"Id", "Cliente", "Local", "Evento", "Data Início", "Valor", "Vendedor", "Status", "Faturada"};
	private int [] columnWidth = { 10, 200, 180, 180, 50, 50, 150, 50, 50 };
	private Class<?> columnClass[] = {Long.class, String.class, String.class, OrdemServico.class, Date.class, BigDecimal.class, String.class, String.class, String.class};

	public void addElement(OrdemServico os) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		Object rowData[] = new Object[9];
		rowData[0] = new Long(os.getId());
		rowData[1] = os.getCliente().getNome();
		rowData[2] = (os.getLocal() == null)?"":os.getLocal().getNome();
		rowData[3] = os;
		rowData[4] = os.getDataInicio();
		/*if(os.getPreco() !=null)
			if(os.getDesconto() != null)
				rowData[5] = ("R$ " + formato.format(os.getPreco().subtract(os.getDesconto())));
			else 
				rowData[5] = ("R$ " + formato.format(os.getPreco()));
		else
			rowData[5] = "R$ 0,00";*/
		rowData[5] = os.getPreco();
		rowData[6] = os.getFuncionario().getNome();
		rowData[7] = (os.getStatus() == StatusOS.OS_SEM_EQUIPAMENTO)?"<html><b><font color='red'>" + os.getStatus()+"</font></b></html>" :"" + os.getStatus();
		rowData[8] = (os.getObservacoesFaturamento() == null)?"NÃO":(os.getObservacoesFaturamento().trim().length() == 0)?"NÃO":"SIM"; 
		addRow(rowData);
	}

	public int getObjectIndex() {
		return 3;
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
