package br.com.sne.sistema.gui.pauta;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.form.ZebraDecorator;

public class PanelPauta extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabelaPautas;
	private PautaTableModel modelRecurso;
	private JPopupMenu menuTabela;
		
/*	public PanelPauta(AmbienteEvento amb, Nota nota) {
		ambiente = amb;
		this.nota = nota;
		this.setLayout(new GridBagLayout());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(gettabelaPautas());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
		this.add(getPanelPrecoTotal(), new RestricaoLayout(1,0,1,true,false));
	}*/
	
	public PanelPauta() {
		
		this.setLayout(new GridBagLayout());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaPautas());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
		
	}
	
	private JTable getTabelaPautas() {
		menuTabela = new JPopupMenu();
		
		modelRecurso = new  PautaTableModel();
		ZebraDecorator zebra = new ZebraDecorator();

		tabelaPautas = new JTable(modelRecurso);
		
		for(int i=0; i < tabelaPautas.getColumnCount(); i++) {
			tabelaPautas.getColumnModel().getColumn(i).setCellRenderer(zebra);
		}
		
		return tabelaPautas;
	}
	
/*	public List<Pauta> getRecursosSolicitados() {
		return modelRecurso.getPautas();
	}*/

	public PautaTableModel getModel() {
		return modelRecurso; 
	}

}
