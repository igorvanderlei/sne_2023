package br.com.sne.sistema.gui.devolucaoequipamento;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class EquipamentoDevolvidoTableModel extends SizedTableModel<Equipamento> {
	private static final long serialVersionUID = 1L;
	private String columnName[] = { "Código", "Recurso", "Marca", "Modelo"};
	private String filterColumnName[] = {"Código", "Recurso", "Marca", "Modelo"};
	private int [] columnWidth = {40, 100, 50, 50};
	//private Class<?> columnClass[] = {Long.class, Equipamento.class, Long.class, Long.class};
	
	private Class<?> columnClass[] = {Equipamento.class, String.class, String.class, String.class};
	
	public void addElement(Equipamento eq) {
		Object rowData[] = new Object[4];
		rowData[0] = eq;
		rowData[1] = eq.getDescricaoEquipamento().getNome();
		rowData[2] = eq.getMarca();
		rowData[3] = eq.getModelo();
		addRow(rowData);
	}
	
	public boolean contains(Equipamento eq){
		boolean contem = false;
		for(int i = 0; i < getRowCount(); i++) {
			Equipamento e = (Equipamento) getValueAt(i, 0);
			if(e.getNumeroSerie().equals(eq.getNumeroSerie())) {
				contem = true;
				break;
			}
		}
		return contem;
	}
	
	/*public boolean getStatusId(long id) {
		for(int i = 0; i < getRowCount(); i++) {
			EquipamentoEnviado e = (EquipamentoEnviado) getValueAt(i, 0);
			if(e.getId() == id) {
				return (Boolean) getValueAt(i, 0);
			}
		}
		return false;		
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
	
	
	public boolean isCellEditable(int arg0, int arg1) {
		return arg1==0;
	}
	
	
}
