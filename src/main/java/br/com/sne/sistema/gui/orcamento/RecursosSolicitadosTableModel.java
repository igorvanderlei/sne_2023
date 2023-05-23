package br.com.sne.sistema.gui.orcamento;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.sne.sistema.bean.Ambiente;
import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RecursoSolicitadoDTO;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.SizedTableModel;

public class RecursosSolicitadosTableModel extends SizedTableModel<RecursoSolicitado>{
	private static final long serialVersionUID = 1L;
	private String columnName[] = {"Id","Descrição", "Valor Mínimo","Preço de Custo", "Preço", "Quantidade", "Dia Inicial", "Dia Final", "Subtotal"};
	private String filterColumnName[] = {""};
	private int [] columnWidth = {10, 100, 40, 40,40, 40, 60, 60, 40 };
	private Class<?> columnClass[] = {String.class,String.class, String.class,BigDecimal.class, BigDecimal.class, Integer.class, Date.class, Date.class, String.class};
	private boolean diarias;

	public RecursosSolicitadosTableModel(boolean diarias) {
		this.diarias = diarias;
	}
	public void addElement(RecursoSolicitado rec) {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		
		Object rowData[] = new Object[9];
		rowData[0] = rec;
		rowData[1] = rec.getDescricao();
		rowData[2] = "R$ " + formato.format(rec.getRecurso().getValorMinimo());
		rowData[3] = rec.getPrecoCusto();
		rowData[4] = rec.getPrecoUnitario();
		rowData[5] = rec.getQuantidade();
		rowData[6] = rec.getDataInicio();
		rowData[7] = rec.getDataFim();
		rowData[8] = "<html><b>R$ " + formato.format(rec.getSubTotal(diarias))+ "</p>";
		
		addRow(rowData);
	}
	
	public List<RecursoSolicitado> getRecursos() {
		List<RecursoSolicitado> recursos = new ArrayList<RecursoSolicitado>();
		for(int i = 0; i < getRowCount(); i++) {
			RecursoSolicitado rec = (RecursoSolicitado) getValueAt(i, 0);
			rec.setDescricao((String) getValueAt(i,1));
			rec.setPrecoUnitario((BigDecimal) getValueAt(i, 4));
			rec.setPrecoCusto((BigDecimal) getValueAt(i, 3));
			rec.setQuantidade((Integer)getValueAt(i, 5));
			rec.setDataInicio((Date) getValueAt(i, 6));
			rec.setDataFim((Date) getValueAt(i, 7)); 
			rec.setSubTotal(rec.getSubTotal(diarias));
			recursos.add(rec);
		}
		return recursos;
	}
	
	public RecursoSolicitado getRecursoAt(int linha) {
		if(linha >=0 && linha < getRowCount()) {
			RecursoSolicitado template = (RecursoSolicitado) getValueAt(linha, 0);
			RecursoSolicitado rec = new RecursoSolicitado(
					(Date) getValueAt(linha, 6),
					(Date) getValueAt(linha, 7),
					(BigDecimal) getValueAt(linha, 4),
					(BigDecimal) getValueAt(linha,3),
					(Integer)getValueAt(linha, 5),
					template.getRecurso()
			);
			rec.setDescricao((String) getValueAt(linha,1));
			return rec;
		}
		return null;
		
	}
	
	public void copiarClipboard(int[] selecao) {
		if(selecao.length > 0) {
			RecursoSolicitadoDTO dto = new RecursoSolicitadoDTO();
			for(int i: selecao) {
				dto.adicionarRecurso(getRecursoAt(i));
			}
			
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(dto, null);
		}
	}

	public void colarClipboard(AmbienteEvento ambiente) {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		

		if (t != null && t.isDataFlavorSupported( RecursoSolicitadoDTO.arrayListFlavor)) {
			try {
				ArrayList<ArrayList<Object>> paste = (ArrayList<ArrayList<Object>>) t.getTransferData(RecursoSolicitadoDTO.arrayListFlavor);
				for(ArrayList<Object> elemento: paste) {
					RecursoSolicitado rec = new RecursoSolicitado ();
					rec.setDataInicio((Date) elemento.get(0));
					rec.setDataFim((Date) elemento.get(1));
					rec.setPrecoUnitario((BigDecimal) elemento.get(2));
					rec.setPrecoCusto((BigDecimal) elemento.get(3));
					rec.setQuantidade((Integer) elemento.get(4));
					rec.setRecurso(Facade.getInstance().carregarRecurso((Long) elemento.get(5)));
					rec.setDescricao((String) elemento.get(6));
					rec.setAmbiente(ambiente);
					addElement(rec);
				}
			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void trocarOrdem(int i, int j) {
		int limite = getRowCount();
		System.out.println(i + ", " + j);
		if(i >= 0 && j >= 0 && i < limite && j < limite && i != j) {
			Object temp;
			for(int indice=0; indice < getColumnCount(); indice++) {
				temp = getValueAt ( i, indice); //temp <- i
				setValueAt(getValueAt(j, indice), i, indice); // i <- j
				setValueAt(temp, j, indice); // j <- temp
			}
			fireTableDataChanged();
		}
	}
	
	public void alterarDatas(Date dataInicio, Date dataFim) {
		for(int i = 0; i < getRowCount(); i++) {
			setValueAt(dataInicio, i, 6);
			setValueAt(dataFim, i, 7);
		}
		atualizarSubtotais();
		fireTableDataChanged();
	}
	
	
	public void atualizarSubtotais() {
		NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);

		for(int i = 0; i < getRowCount(); i++) {
			RecursoSolicitado rec = (RecursoSolicitado) getValueAt(i, 0);
			rec.setDescricao((String) getValueAt(i,1));
			rec.setPrecoUnitario((BigDecimal) getValueAt(i, 4));
			rec.setPrecoCusto((BigDecimal) getValueAt(i, 3));
			rec.setQuantidade((Integer) getValueAt(i, 5));
			rec.setDataInicio((Date) getValueAt(i, 6));
			rec.setDataFim((Date) getValueAt(i, 7));
			setValueAt("<html><b>R$ " + formato.format(rec.getSubTotal(diarias))  + "</b>", i, 8);
		}
	}
	
	
	public BigDecimal calcularTotalRecursos() {
		BigDecimal total = new BigDecimal(0);
		for(RecursoSolicitado r: getRecursos()) {
			total = total.add(r.getSubTotal(diarias));
		}
		return total;
	}
	
	public BigDecimal calcularTotalRecursosPrecoCusto() {
		BigDecimal total = new BigDecimal(0);
		for(RecursoSolicitado r: getRecursos()) {
			total = total.add(r.getSubTotalCusto(diarias));
		}
		return total;
	}
	
	public boolean isCellEditable(int arg0, int arg1) {
		return (arg1 == 1 || (arg1 >3 && arg1 < 8));
	}

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
