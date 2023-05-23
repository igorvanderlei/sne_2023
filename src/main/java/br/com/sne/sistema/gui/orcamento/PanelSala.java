package br.com.sne.sistema.gui.orcamento;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.gui.util.components.DateCellRenderer;
import br.com.sne.sistema.gui.util.components.InputListener;
import br.com.sne.sistema.gui.util.components.IntegerCellRenderer;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorBounded;
import br.com.sne.sistema.gui.util.components.JDateChooserCellEditorUnbounded;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.MoedaCellRenderer;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.form.FormIntervalar;
import br.com.sne.sistema.gui.util.form.ZebraDecorator;

public class PanelSala extends JPanel implements FormIntervalar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabelaRecursosSolicitados;
	private RecursosSolicitadosTableModel modelRecurso;
	private JPopupMenu menuTabela;
	private AmbienteEvento ambiente;
	private JLabel fieldPrecoSubtotal;
	private JLabel fieldPrecoTotal;
	private JLabel fieldPrecoCustoTotal;
	private JLabel fieldDesconto;
	private JMoedaRealTextField fieldDescontoMoeda;
	private Nota nota; 
	private boolean limiteData = true;
	public boolean diarias;
	private TipoRecurso tipoRecurso;
	
/*	public PanelSala(AmbienteEvento amb, Nota nota) {
		ambiente = amb;
		this.nota = nota;
		this.setLayout(new GridBagLayout());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
		this.add(getPanelPrecoTotal(), new RestricaoLayout(1,0,1,true,false));
	}*/
	
	public PanelSala(AmbienteEvento amb, Nota nota, boolean limiteData, boolean diarias,TipoRecurso tipoRecurso) {
		this.limiteData = limiteData;
		this.diarias = diarias;
		ambiente = amb;
		this.nota = nota;
		this.tipoRecurso = tipoRecurso;
		this.setLayout(new GridBagLayout());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
		this.add(getPanelPrecoTotal(), new RestricaoLayout(1,0,1,true,false));		
		
	}
	
	private JTable getTabelaRecursosSolicitados() {
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		menuTabela.add(getMenuMoverCima());
		menuTabela.add(getMenuMoverBaixo());
		menuTabela.add(getMenuCopiar());
		menuTabela.add(getMenuColar());
		
		modelRecurso = new  RecursosSolicitadosTableModel(this.diarias);
		ZebraDecorator zebra = new ZebraDecorator();

		
		tabelaRecursosSolicitados = new JTable(modelRecurso){
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int vColIndex) {

				Component c = super.prepareRenderer(renderer, row, vColIndex);
				RecursoSolicitado rec = (RecursoSolicitado) getValueAt(row, modelRecurso.getObjectIndex());
				if(rec.getPrecoUnitario().compareTo(rec.getRecurso().getValorMinimo()) < 0) {  
					c.setForeground(Color.RED);
					Font ft = new Font("Arial", Font.BOLD, 11);     
					c.setFont(ft);  
				} else {
					c.setForeground(Color.BLACK);
					Font ft = new Font("Arial",Font.PLAIN, 11);     
					c.setFont(ft);
				}
				if(vColIndex == 3)
					c.setBackground(new Color(255,0,0,120));
				return c;
			}
		};
		
		for(int i=0; i < tabelaRecursosSolicitados.getColumnCount(); i++) {
			tabelaRecursosSolicitados.getColumnModel().getColumn(i).setCellRenderer(zebra);
		}
		
		
		
		
		tabelaRecursosSolicitados.addMouseListener(
				new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if(arg0.getButton() == MouseEvent.BUTTON3) {
							int [] selecionados = tabelaRecursosSolicitados.getSelectedRows(); 
							menuTabela.show(tabelaRecursosSolicitados, arg0.getX(), arg0.getY());
						} 						
					}

				/*	public void mouseReleased(MouseEvent e) {
						if(e.getButton() == MouseEvent.BUTTON3 && tabelaRecursosSolicitados.getRowCount()==0) {
						int r = tabelaRecursosSolicitados.rowAtPoint(e.getPoint());
						if (r >= 0 && r < tabelaRecursosSolicitados.getRowCount()) {
							tabelaRecursosSolicitados.setRowSelectionInterval(r, r);
						} else {
							tabelaRecursosSolicitados.clearSelection();
						}
						}
					}*/


				}

				);
		tabelaRecursosSolicitados.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						modelRecurso.atualizarSubtotais();
						calcularTotal();
					}
				}
		);
		
		if(limiteData) {
			tabelaRecursosSolicitados.getColumnModel().getColumn(6).setCellEditor(new JDateChooserCellEditorBounded(this));
			tabelaRecursosSolicitados.getColumnModel().getColumn(7).setCellEditor(new JDateChooserCellEditorBounded(this));
		} else {
			tabelaRecursosSolicitados.getColumnModel().getColumn(6).setCellEditor(new JDateChooserCellEditorUnbounded());
			tabelaRecursosSolicitados.getColumnModel().getColumn(7).setCellEditor(new JDateChooserCellEditorUnbounded());
		}
		tabelaRecursosSolicitados.getColumnModel().getColumn(5).setCellRenderer(new IntegerCellRenderer());
		tabelaRecursosSolicitados.getColumnModel().getColumn(2).setCellRenderer(new MoedaCellRenderer());
		tabelaRecursosSolicitados.getColumnModel().getColumn(3).setCellRenderer(new MoedaCellRenderer());
		tabelaRecursosSolicitados.getColumnModel().getColumn(4).setCellRenderer(new MoedaCellRenderer());
		tabelaRecursosSolicitados.getColumnModel().getColumn(8).setCellRenderer(new MoedaCellRenderer());
		
		tabelaRecursosSolicitados.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
		tabelaRecursosSolicitados.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
		return tabelaRecursosSolicitados;
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Recurso");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int [] selecionados = tabelaRecursosSolicitados.getSelectedRows();
						if(selecionados.length >= 0 ) {
							Arrays.sort (selecionados);
							for(int i = selecionados.length-1; i>=0; i--) {
								modelRecurso.removeRow(selecionados[i]);
							}
							calcularTotal();
						}
					}
				}		
		);
		return menuRemover;
	}
	
	private JMenuItem getMenuCopiar() {
		JMenuItem menuRemover = new JMenuItem("Copiar");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int [] selecionados = tabelaRecursosSolicitados.getSelectedRows();
						if(selecionados.length >= 0 ) {
							modelRecurso.copiarClipboard(selecionados);
						}
					}
				}		
				);
		return menuRemover;
	}
	
	private JMenuItem getMenuColar() {
		JMenuItem menuRemover = new JMenuItem("Colar");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelRecurso.colarClipboard(ambiente);
						calcularTotal();
					}
				}		
		);
		return menuRemover;
	}
	
	private JMenuItem getMenuMoverCima() {
		JMenuItem menuRemover = new JMenuItem("Mover para cima");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int [] selecionados = tabelaRecursosSolicitados.getSelectedRows();
						Arrays.sort (selecionados);
						if(selecionados.length >= 0 ) {
							if(selecionados[0] > 0) {
								for(int i = 0; i<selecionados.length; i++) {
									modelRecurso.trocarOrdem(selecionados[i], selecionados[i] -1);
									selecionados[i] = selecionados[i] -1;
								}
							}
							tabelaRecursosSolicitados.clearSelection();
							for(int i = 0; i<selecionados.length; i++) 
								tabelaRecursosSolicitados.addRowSelectionInterval(selecionados[i], selecionados[i]);
						}
					}
				}		
		);
		return menuRemover;
	}
	
	private JMenuItem getMenuMoverBaixo() {
		JMenuItem menuRemover = new JMenuItem("Mover para baixo");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int [] selecionados = tabelaRecursosSolicitados.getSelectedRows();
						Arrays.sort (selecionados);

						if(selecionados.length >= 0 ) {
							if(selecionados[selecionados.length-1] < tabelaRecursosSolicitados.getRowCount()-1) {
								for(int i = selecionados.length-1; i>=0; i--) {
									modelRecurso.trocarOrdem(selecionados[i], selecionados[i] +1);
									selecionados[i] = selecionados[i] +1;
								}
							}
							tabelaRecursosSolicitados.clearSelection();
							for(int i = 0; i<selecionados.length; i++) 
								tabelaRecursosSolicitados.addRowSelectionInterval(selecionados[i], selecionados[i]);
						}
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
	
	public void setDesconto(TipoRecurso tipoRecurso) {
		if(tipoRecurso == TipoRecurso.EQUIPAMENTO)
			ambiente.setDescontoEquipamento(fieldDescontoMoeda.getValor());
		else if(tipoRecurso == TipoRecurso.CENOGRAFIA)
			ambiente.setDescontoCenografia(fieldDescontoMoeda.getValor());
			
	}

	public void calcularTotal() {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		BigDecimal desconto = fieldDescontoMoeda.getValor();
		BigDecimal subtotal = modelRecurso.calcularTotalRecursos();
		BigDecimal stPrecoCusto = modelRecurso.calcularTotalRecursosPrecoCusto();
		BigDecimal total = subtotal.subtract(desconto);
		
		setDesconto(tipoRecurso);
		
		fieldPrecoTotal.setText("| Total do Ambiente: R$" + formato.format(total));
		fieldPrecoSubtotal.setText("Subtotal do Ambiente: R$ " + formato.format(subtotal)+" |");
		fieldPrecoCustoTotal.setText("Subtotal de Custo: R$ "+ formato.format(stPrecoCusto)+" |");
		nota.calcularTotal();
		if(subtotal.compareTo(stPrecoCusto) < 0) 
			fieldPrecoSubtotal.setForeground(Color.BLUE);
		else 
			fieldPrecoSubtotal.setForeground(Color.BLUE);
		
		if(total.compareTo(BigDecimal.ZERO) < 0) 
			fieldPrecoTotal.setForeground(Color.BLUE);
		else 
			fieldPrecoTotal.setForeground(Color.BLUE);

	}

	private JPanel getPanelPrecoTotal() {
		fieldPrecoTotal = new JLabel("| Total do Ambiente: R$ 0,00");
		fieldPrecoTotal.setFont(new Font("Arial", Font.BOLD, 14));
		fieldPrecoTotal.setForeground(Color.BLUE);
		
		fieldDesconto = new JLabel("Desconto:");
		fieldDesconto.setFont(new Font("Arial", Font.BOLD, 14));
		fieldDesconto.setForeground(new Color(0,150,0));
		
		fieldDescontoMoeda = new JMoedaRealTextField();
		
		if(tipoRecurso == TipoRecurso.CENOGRAFIA && ambiente.getDescontoCenografia() != null)
			fieldDescontoMoeda.setValor(ambiente.getDescontoCenografia());
		else if(tipoRecurso == TipoRecurso.EQUIPAMENTO && ambiente.getDescontoEquipamento() != null)
			fieldDescontoMoeda.setValor(ambiente.getDescontoEquipamento());			
		else
			fieldDescontoMoeda.setValor(BigDecimal.ZERO);
			
		System.out.println(ambiente.getDescontoEquipamento());
	
		fieldDescontoMoeda.setFont(new Font("Arial", Font.BOLD, 14));
		fieldDescontoMoeda.setForeground(new Color(0,150,0));
		
		fieldDescontoMoeda.setListener(new InputListener() {
			public void valueChanged() {
				calcularTotal();
			}
		});
		
		fieldPrecoSubtotal = new JLabel("Subtotal do Ambiente: R$ 0,00 |");
		fieldPrecoSubtotal.setFont(new Font("Arial", Font.BOLD, 14));
		fieldPrecoSubtotal.setForeground(Color.BLUE);
		
		fieldPrecoCustoTotal = new JLabel("Subtotal de Custo: R$ 0,00 |");
		fieldPrecoCustoTotal.setFont(new Font("Arial", Font.BOLD, 14));
		fieldPrecoCustoTotal.setForeground(Color.RED);
		
		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		total.add(fieldPrecoCustoTotal);
		total.add(fieldPrecoSubtotal);
		if(!(tipoRecurso == TipoRecurso.LOGISTICA || tipoRecurso == TipoRecurso.EQUIPE_TECNICA)) {
			total.add(fieldDesconto);
			total.add(fieldDescontoMoeda);
			total.add(fieldPrecoTotal);
		}
		return total;
	}

	public List<RecursoSolicitado> getRecursosSolicitados() {
		return modelRecurso.getRecursos();
	}
	
	public void adicionarRecurso(RecursoSolicitado rec) {
		rec.setAmbiente(ambiente);
		modelRecurso.addElement(rec);
		calcularTotal();
	}
	

	public BigDecimal calcularSubTotal(){
		return modelRecurso.calcularTotalRecursos();
	}
	
	public BigDecimal calcularSubTotalPrecoCusto(){
		return modelRecurso.calcularTotalRecursosPrecoCusto();
	}
	
	public void removeAllElements() {
		modelRecurso.removeAllElements();
		calcularTotal();
	}

	public AmbienteEvento getAmbiente() {
		return ambiente;
	}
	
	public RecursosSolicitadosTableModel getModel() {
		return modelRecurso; 
	}

}
