package br.com.sne.sistema.gui.local;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.sne.sistema.bean.SalaLocal;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class SalaLocalTableModel extends SizedTableModel<SalaLocal>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Nome", "Largura", "Comprimento", "Pé direito", "Área Total", "Pontos de Fixação Aérea"};
	private String filterColumnName[] = {""};
	private int [] columnWidth = { 100, 40, 40, 40, 40,40};
	private Class<?> columnClass[] = {SalaLocal.class, Float.class,Float.class, Float.class, Float.class, Integer.class};
	
	
	public void addElement(SalaLocal amb) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[6];
		rowData[0] = amb;
		rowData[1] = amb.getLargura();
		rowData[2] = amb.getComprimento();
		rowData[3] = amb.getPeDireito();
		rowData[4] = amb.getAreaTotal();
		rowData[5] = amb.getPontosFixacaoAerea();
		addRow(rowData);
	}
	
	public List <SalaLocal> getSalaLocals(){
		List<SalaLocal> SalaLocals = new ArrayList<SalaLocal>();
		for(int i = 0; i < getRowCount(); i++) {
			SalaLocal amb = (SalaLocal) getValueAt(i, 0);
			amb.setLargura((Float) getValueAt(i,1));
			amb.setComprimento((Float) getValueAt(i,2));
			amb.setPeDireito((Float) getValueAt(i, 3));
			amb.setAreaTotal((Float) getValueAt(i, 4));
			amb.setPontosFixacaoAerea((Integer) getValueAt(i, 5));
			amb.setPontoFixacaoAerea(false);
			if(amb.getPontosFixacaoAerea() != 0) {
				amb.setPontoFixacaoAerea(true);
			}
			SalaLocals.add(amb);
		}
		return SalaLocals;
	}

	public int[] getColumnWidth() {
		return columnWidth;
	}

	public String[] getFilterColumnName() {
		return filterColumnName;
	}

	public int getObjectIndex() {
		return 0;
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
