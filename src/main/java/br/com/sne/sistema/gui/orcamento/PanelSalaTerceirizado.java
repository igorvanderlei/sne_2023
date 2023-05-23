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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.RecursoTerceirizadoSolicitado;
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

public class PanelSalaTerceirizado extends JPanel implements FormIntervalar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabelaRecursosSolicitados;
	private RecursosTerceirizadosSolicitadosTableModel modelRecurso;
	private JPopupMenu menuTabela;
	private AmbienteEvento ambiente;
	private JLabel fieldPrecoSubtotal;
	private JLabel fieldDesconto;
	private JLabel fieldPrecoTotal;
	private JMoedaRealTextField fieldDescontoMoeda;
	private Nota nota; 
	private boolean limiteData = true;
	private boolean diarias;
	private boolean empresa = true;
	
/*	public PanelSala(AmbienteEvento amb, Nota nota) {
		ambiente = amb;
		this.nota = nota;
		this.setLayout(new GridBagLayout());
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosSolicitados());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
		this.add(getPanelPrecoTotal(), new RestricaoLayout(1,0,1,true,false));
	}*/
	
	public void setEmpresa(boolean empresa) {
		calcularTotal(empresa);
		this.empresa = empresa;
	}
	
	public boolean getEmpresa() {
		return this.empresa;
	}
	
	public void calcularTotal(boolean empresa){
		if(empresa)
			calcularTotalEmp();
		else
			calcularTotalForn();
	}
	
	public PanelSalaTerceirizado(AmbienteEvento amb, Nota nota, boolean limiteData, boolean diarias,boolean empresa) {
		this.limiteData = limiteData;
		this.diarias = diarias;
		ambiente = amb;
		this.nota = nota;
		this.setLayout(new GridBagLayout());
		this.empresa = empresa;
		
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setViewportView(getTabelaRecursosTerceirizadosSolicitados());
		
		this.add(scrollTabela, new RestricaoLayout(0,0,1,1,true,true));
		this.add(getPanelPrecoTotal(), new RestricaoLayout(1,0,1,true,false));		
		
	}
	
	private JTable getTabelaRecursosTerceirizadosSolicitados() {
		menuTabela = new JPopupMenu();
		menuTabela.add(getMenuRemover());
		modelRecurso = new  RecursosTerceirizadosSolicitadosTableModel(this.diarias);
		ZebraDecorator zebra = new ZebraDecorator();

		
		tabelaRecursosSolicitados = new JTable(modelRecurso){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int vColIndex) {

				Component c = super.prepareRenderer(renderer, row, vColIndex);
				RecursoTerceirizadoSolicitado rec = (RecursoTerceirizadoSolicitado) getValueAt(row, modelRecurso.getObjectIndex());
				if(rec.getPrecoEmpresa().compareTo(rec.getRecurso().getPrecoFornecedor()) < 0) {  
					c.setForeground(Color.RED);
					Font ft = new Font("Arial", Font.BOLD, 11);     
					c.setFont(ft);  
				} else {
					c.setForeground(Color.BLACK);
					Font ft = new Font("Arial",Font.PLAIN, 11);     
					c.setFont(ft);
				}
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
							if(tabelaRecursosSolicitados.getSelectedRow() >= 0 )
								menuTabela.show(tabelaRecursosSolicitados, arg0.getX(), arg0.getY());
							else 
								JOptionPane.showMessageDialog(null, "Selecione um Recurso Terceirizado para remover", "Atenção!",  JOptionPane.WARNING_MESSAGE);
						} 						
					}
				}
		
		);
		tabelaRecursosSolicitados.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent arg0) {
						modelRecurso.atualizarSubtotais();
						if(empresa) 
							calcularTotalEmp();
						else
							calcularTotalForn();
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
		tabelaRecursosSolicitados.getColumnModel().getColumn(9).setCellRenderer(new MoedaCellRenderer());

		tabelaRecursosSolicitados.getColumnModel().getColumn(6).setCellRenderer(new DateCellRenderer());
		tabelaRecursosSolicitados.getColumnModel().getColumn(7).setCellRenderer(new DateCellRenderer());
		return tabelaRecursosSolicitados;
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Recurso Terceirizado");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelRecurso.removeRow(tabelaRecursosSolicitados.getSelectedRow());
						calcularTotal(empresa);
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

	private void calcularTotalForn() {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		BigDecimal subtotal = modelRecurso.calcularTotalRecursosFornecedor();
		BigDecimal subtotalEmp = modelRecurso.calcularTotalRecursos();
		BigDecimal desconto = fieldDescontoMoeda.getValor();
		BigDecimal total = subtotal.subtract(desconto);
		ambiente.setDescontoTerceirizado(desconto);
		fieldPrecoTotal.setText("| Total do Ambiente: R$ " + formato.format(total));
		fieldPrecoSubtotal.setText("Subtotal Terceirizado SNE: R$ " + formato.format(subtotalEmp)+ " | "
				+ "Subtotal Terceirizado Fornecedor: R$ " + formato.format(subtotal)+ " |");
		nota.calcularTotal();
		
		if(total.compareTo(BigDecimal.ZERO) < 0) 
			fieldPrecoTotal.setForeground(Color.RED);
		else 
			fieldPrecoTotal.setForeground(Color.BLUE);
	}
	
	private void calcularTotalEmp() {
	    NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		BigDecimal subtotalForn = modelRecurso.calcularTotalRecursosFornecedor();
		BigDecimal subtotal = modelRecurso.calcularTotalRecursos();
		BigDecimal desconto = fieldDescontoMoeda.getValor();
		BigDecimal total = subtotal.subtract(desconto);
		ambiente.setDescontoTerceirizado(desconto);
		fieldPrecoTotal.setText("| Total do Ambiente: R$ " + formato.format(total));
		fieldPrecoSubtotal.setText("Subtotal Terceirizado SNE: R$ " + formato.format(subtotal)+ " | "
			+ "Subtotal Terceirizado Fornecedor: R$ " + formato.format(subtotalForn)+ " |");
		nota.calcularTotal();
		
		if(total.compareTo(BigDecimal.ZERO) < 0) 
			fieldPrecoTotal.setForeground(Color.RED);
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
		
		if(ambiente.getDescontoTerceirizado() != null)
			fieldDescontoMoeda.setValor(ambiente.getDescontoTerceirizado());
		else
			fieldDescontoMoeda.setValor(BigDecimal.ZERO);
			
		fieldDescontoMoeda.setFont(new Font("Arial", Font.BOLD, 14));
		fieldDescontoMoeda.setForeground(new Color(0,150,0));
		
		fieldDescontoMoeda.setListener(new InputListener() {
			public void valueChanged() {
				calcularTotal(empresa);			
			}
		});
		
		fieldPrecoSubtotal = new JLabel("Subtotal Terceirizado SNE: R$ 0,00 |");
		fieldPrecoSubtotal.setFont(new Font("Arial", Font.BOLD, 14));
		fieldPrecoSubtotal.setForeground(Color.BLUE);
		
		JPanel total = new JPanel();
		total.setLayout(new FlowLayout(FlowLayout.RIGHT));

		total.add(fieldPrecoSubtotal);
		total.add(fieldDesconto);
		total.add(fieldDescontoMoeda);
		total.add(fieldPrecoTotal);
		return total;
	}

	public List<RecursoTerceirizadoSolicitado> getRecursosTerceirizadosSolicitados() {
		return modelRecurso.getRecursos();
	}
	
	public void adicionarRecurso(RecursoTerceirizadoSolicitado rec) {
		rec.setAmbiente(ambiente);
		modelRecurso.addElement(rec);
		calcularTotal(empresa);
	}
	

	public BigDecimal calcularSubTotal(){
		return modelRecurso.calcularTotalRecursos();
	}
	
	public BigDecimal calcularSubTotalFornecedor(){
		return modelRecurso.calcularTotalRecursosFornecedor();
	}
	
	public void removeAllElements() {
		modelRecurso.removeAllElements();
		calcularTotal(empresa);
	}

	public AmbienteEvento getAmbiente() {
		return ambiente;
	}
	public RecursosTerceirizadosSolicitadosTableModel getModel() {
		return modelRecurso; 
	}
}
