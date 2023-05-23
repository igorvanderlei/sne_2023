package br.com.sne.sistema.gui.comodato;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorBounded;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.form.FormIntervalar;

public class PanelSala extends JPanel implements FormIntervalar{

	private JTable tabelaRecursosSolicitados;
	private RecursosSolicitadosTableModel modelRecurso;
	private JPopupMenu menuTabela;
	private AmbienteEvento ambiente;
	
	public PanelSala(AmbienteEvento amb) {
		ambiente = amb;
		this.setLayout(new GridBagLayout());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
	}
	
	private JTable getTabelaRecursosSolicitados() {
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		modelRecurso = new  RecursosSolicitadosTableModel();
		tabelaRecursosSolicitados = new JTable(modelRecurso);
		tabelaRecursosSolicitados.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							if(tabelaRecursosSolicitados.getSelectedRow() >= 0 )
								menuTabela.show(tabelaRecursosSolicitados, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Recurso para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		
		return tabelaRecursosSolicitados;
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Recurso");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelRecurso.removeRow(tabelaRecursosSolicitados.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}

	public Date getDataInicial() {
		return ambiente.getDataInicio();
	}

	public Date getDataFinal() {
		return ambiente.getDataFim();
	}

	public List<RecursoSolicitado> getRecursosSolicitados() {
		return modelRecurso.getRecursos();
	}
	
	public void adicionarRecurso(RecursoSolicitado rec) {
		rec.setAmbiente(ambiente);
		modelRecurso.addElement(rec);
	}
	
	public void removeAllElements() {
		modelRecurso.removeAllElements();
	}

}
