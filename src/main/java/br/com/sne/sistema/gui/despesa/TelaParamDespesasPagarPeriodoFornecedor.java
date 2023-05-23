package br.com.sne.sistema.gui.despesa;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.form.DialogSearchFornecedor;

import com.toedter.calendar.JDateChooser;

public class TelaParamDespesasPagarPeriodoFornecedor extends JFrame implements ActionListener{

	private JLabel labelDataInicial = null;
	private JLabel labelDataFinal = null;
	private JDateChooser fieldDataInicial;
	private JDateChooser fieldDataFinal;
	private JLabel labelTitulo = null;
	private JLabel labelPorFornecedor = null;
	private JLabel labelImagem = null;
	private JLabel labelTodosFornecedores = null;
	private JTextField fieldFornecedor = null;
	private JButton botaoGerar = null;
	private JRadioButton radioPorFornecedor = null;
	private JRadioButton radioTodos = null;
	private JButton botaoLocalizarFornecedor = null;
	private long codFornecedor = 0;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamDespesasPagarPeriodoFornecedor(){
		
		//configurando a janela
		setTitle("Despesas a Pagar por Período e por Fornecedor:");
		setSize(new Dimension(460, 295)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelTitulo = new JLabel("Informe abaixo como deseja ser exibido o relatório:");
		labelDataInicial = new JLabel("De:");
		labelDataFinal  = new JLabel("Até:");
		fieldDataInicial = new JDateChooser(new Date());
		fieldDataFinal = new JDateChooser(new Date());
		labelPorFornecedor  = new JLabel("Por Fornecedor:");
		fieldFornecedor = new JTextField();
		fieldFornecedor.setEditable(false);
		labelTodosFornecedores = new JLabel("Todos os Fornecedores:");
		ButtonGroup bgroup = new ButtonGroup();
		radioPorFornecedor = new JRadioButton();
		radioTodos = new JRadioButton();
		bgroup.add(radioPorFornecedor);
		bgroup.add(radioTodos);
		radioPorFornecedor.setSelected(true);
		botaoLocalizarFornecedor = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/pagar_48.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelTitulo.setBounds(new Rectangle(65, 20, 400, 20));
		labelDataInicial.setBounds(new Rectangle(65, 50, 400, 29));
		labelDataFinal.setBounds(new Rectangle(240, 50, 400, 29));
		fieldDataInicial.setBounds(new Rectangle(90, 50, 140, 29));
		fieldDataFinal.setBounds(new Rectangle(265, 50, 140, 29));
		radioPorFornecedor.setBounds(new Rectangle(65, 120, 20, 25));
		labelPorFornecedor.setBounds(new Rectangle(65, 90, 100, 25));
		fieldFornecedor.setBounds(new Rectangle(90, 120, 265, 25));
		botaoLocalizarFornecedor.setBounds(new Rectangle(360, 120, 45, 25));
		radioTodos.setBounds(new Rectangle(65, 160, 20, 25));
		labelTodosFornecedores.setBounds(new Rectangle(90, 160, 200, 25));
		botaoGerar.setBounds(new Rectangle(150, 210, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o relatório!");
		botaoLocalizarFornecedor.setToolTipText("Clique para Localizar Fornecedores!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,12);
		labelPorFornecedor.setFont(f1);
		labelTodosFornecedores.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelDataFinal);
		getContentPane().add(labelDataInicial);
		getContentPane().add(fieldDataInicial);
		getContentPane().add(fieldDataFinal);
		getContentPane().add(labelTitulo);
		getContentPane().add(radioPorFornecedor);
		getContentPane().add(labelPorFornecedor);
		getContentPane().add(fieldFornecedor);
		getContentPane().add(botaoLocalizarFornecedor);
		getContentPane().add(radioTodos);
		getContentPane().add(labelTodosFornecedores);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		radioPorFornecedor.addActionListener(this);
		radioTodos.addActionListener(this);
		botaoLocalizarFornecedor.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			if(codFornecedor != 0 && radioPorFornecedor.isSelected() || radioTodos.isSelected()){
					HashMap hm = new HashMap();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					hm.put("datainicial", fieldDataInicial.getDate());
					hm.put("datafinal", fieldDataFinal.getDate());
					URL arquivo = null;
					
					if(radioTodos.isSelected()){
						arquivo = getClass().getResource("/br/com/sne/sistema/report/despesasPagarPeriodoFornecedor.jasper");  
					}else{
						hm.put("id", codFornecedor);
						arquivo = getClass().getResource("/br/com/sne/sistema/report/despesasPagarPeriodoFornecedorIndividual.jasper");  
					}
					try {
						setVisible(false);
						java.sql.Connection c  = Facade.getInstance().getConnection();  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
						c.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			
			}else{
				JOptionPane.showMessageDialog(this, "Selecione um Fornecedor para visualizar o relatório", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		if(e.getSource() == botaoLocalizarFornecedor){
			DialogSearchFornecedor teste = new DialogSearchFornecedor(TelaParamDespesasPagarPeriodoFornecedor.this);
			Fornecedor c = teste.showDialog(TelaParamDespesasPagarPeriodoFornecedor.this);
			if(c != null) {
				fieldFornecedor.setText(c.getId() + " - "+ c.getNome());
				codFornecedor = c.getId();
			}
	}
		
		if(e.getSource() == radioPorFornecedor){
			fieldFornecedor.setEnabled(true);
			botaoLocalizarFornecedor.setEnabled(true);
		}
		
		if(e.getSource() == radioTodos){
			fieldFornecedor.setEnabled(false);
			botaoLocalizarFornecedor.setEnabled(false);
		}
	}
	
	
}
