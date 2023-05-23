package br.com.sne.sistema.gui.pauta;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;

import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.Pauta;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class PautaTableModel extends SizedTableModel<Pauta> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id", "Mês", "Evento", "Cliente",  "Local", "Contato", "Próx. Contato", "Funcionário", "Status"};
	private String filterColumnName[] = {"Id", "Mês", "Evento", "Cliente",  "Local", "Contato", "Próx. Contato", "Funcionário", "Status"}; 
	private int [] columnWidth = { 10, 20, 160, 120, 80, 40,40, 20, 20};
	private Class<?> columnClass[] = {Pauta.class, String.class, String.class, String.class, String.class, String.class,String.class, String.class, String.class};
	
	public void addElement(Pauta pau) {
		Object rowData[] = new Object[11];
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		rowData[0] = pau;
		rowData[1] = pau.getData();
		rowData[2] = pau.getNomeEvento();
		rowData[3] = pau.getEmpresa();
		rowData[4] = pau.getLocalEvento();
		rowData[5] = pau.getContato();
		if(pau.getProxContato() != null)
			rowData[6] = formato.format(pau.getProxContato());
		rowData[7] = pau.getFuncionario().getNome();
		
		if(pau.getStatus().ordinal() == 0) {
			rowData[8] = "<html><b><font color=\"yellow\">"+pau.getStatus().toString()+"</font></b>";
		}
		else if(pau.getStatus().ordinal() == 1) {
			rowData[8] = "<html><b><font color=\"red\">"+pau.getStatus().toString()+"</font></b>";
		}
		else if(pau.getStatus().ordinal() == 2) {
			rowData[8] = "<html><b><font color=\"green\">"+pau.getStatus().toString()+"</font></b>";
		} else if(pau.getStatus().ordinal() == 3) {
			rowData[8] = "<html><b><font color=\"blue\">"+pau.getStatus().toString()+"</font></b>";
		}

		
		
 		addRow(rowData);
	}
	
/*	public List<Pauta> getPautas() {
		List<Pauta> recursos = new ArrayList<Pauta>();
		for(int i = 0; i < getRowCount(); i++) {
			Pauta rec = (Pauta) getValueAt(i, 0);
			rec.setContato((String) getValueAt(i,4));
			rec.setProxContato((Date) getValueAt(i,6));
			rec.setLocalEvento((String) getValueAt(i,7));
			rec.setNomeEvento((String) getValueAt(i, 2));
			rec.setData((Date) getValueAt(i, 1));
			rec.setEmpresa((String) getValueAt(i,3));
			recursos.add(rec);
		}
		return recursos;
	}*/
	
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