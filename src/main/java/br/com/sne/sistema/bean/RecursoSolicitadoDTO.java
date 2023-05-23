package br.com.sne.sistema.bean;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

public class RecursoSolicitadoDTO implements Transferable{
	public static DataFlavor arrayListFlavor = new DataFlavor(ArrayList.class, "java.util.ArrayList");
	
	private ArrayList<ArrayList<Object>> dados;
	
	
	
	public RecursoSolicitadoDTO() {
		dados = new ArrayList<ArrayList<Object>>();
		
		
	}
	
	public void adicionarRecurso(RecursoSolicitado rec) {
		ArrayList<Object> elemento = new ArrayList<Object>();
		elemento.add(rec.getDataInicio());
		elemento.add(rec.getDataFim());
		elemento.add(rec.getPrecoUnitario());
		elemento.add(rec.getPrecoCusto());
		elemento.add(rec.getQuantidade());
		elemento.add(rec.getRecurso().getId());
		elemento.add(rec.getDescricao());
		dados.add(elemento);
	}
	 
	
	

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {RecursoSolicitadoDTO.arrayListFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(RecursoSolicitadoDTO.arrayListFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return dados;
	}

}
