package br.com.sne.sistema.gui.receita;

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
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;

import com.toedter.calendar.JDateChooser;

public class TelaParamParcelasReceberPorClientePeriodo extends JFrame implements ActionListener{

	private JLabel labelDataInicial = null;
	private JLabel labelDataFinal = null;
	private JDateChooser fieldDataInicial;
	private JDateChooser fieldDataFinal;
	private JLabel labelTitulo = null;
	private JLabel labelPorCliente = null;
	private JLabel labelImagem = null;
	private JLabel labelTodosClientes = null;
	private JTextField fieldCliente = null;
	private JButton botaoGerar = null;
	private JRadioButton radioPorCliente = null;
	private JRadioButton radioTodos = null;
	private JButton botaoLocalizarCliente = null;
	private long codCliente = 0;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamParcelasReceberPorClientePeriodo(){
		
		//configurando a janela
		setTitle("Parcelas a Receber por Periodo e por Cliente:");
		setSize(new Dimension(460, 295)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelTitulo = new JLabel("Informe abaixo como deseja que seja exibido o relatório:");
		labelDataInicial = new JLabel("De:");
		labelDataFinal  = new JLabel("Até:");
		fieldDataInicial = new JDateChooser(new Date());
		fieldDataFinal = new JDateChooser(new Date());
		labelPorCliente  = new JLabel("Por Cliente:");
		fieldCliente = new JTextField();
		fieldCliente.setEditable(false);
		labelTodosClientes = new JLabel("Todos os Clientes:");
		ButtonGroup bgroup = new ButtonGroup();
		radioPorCliente = new JRadioButton();
		radioTodos = new JRadioButton();
		bgroup.add(radioPorCliente);
		bgroup.add(radioTodos);
		radioPorCliente.setSelected(true);
		botaoLocalizarCliente = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/cliente_48.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelTitulo.setBounds(new Rectangle(65, 20, 400, 20));
		labelDataInicial.setBounds(new Rectangle(65, 50, 400, 29));
		labelDataFinal.setBounds(new Rectangle(240, 50, 400, 29));
		fieldDataInicial.setBounds(new Rectangle(90, 50, 140, 29));
		fieldDataFinal.setBounds(new Rectangle(265, 50, 140, 29));
		radioPorCliente.setBounds(new Rectangle(65, 120, 20, 25));
		labelPorCliente.setBounds(new Rectangle(65, 90, 100, 25));
		fieldCliente.setBounds(new Rectangle(90, 120, 265, 25));
		botaoLocalizarCliente.setBounds(new Rectangle(360, 120, 45, 25));
		radioTodos.setBounds(new Rectangle(65, 160, 20, 25));
		labelTodosClientes.setBounds(new Rectangle(90, 160, 200, 25));
		botaoGerar.setBounds(new Rectangle(150, 210, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o relatório!");
		botaoLocalizarCliente.setToolTipText("Clique para Localizar Clientes!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,12);
		labelPorCliente.setFont(f1);
		labelTodosClientes.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelDataFinal);
		getContentPane().add(labelDataInicial);
		getContentPane().add(fieldDataInicial);
		getContentPane().add(fieldDataFinal);
		getContentPane().add(labelTitulo);
		getContentPane().add(radioPorCliente);
		getContentPane().add(labelPorCliente);
		getContentPane().add(fieldCliente);
		getContentPane().add(botaoLocalizarCliente);
		getContentPane().add(radioTodos);
		getContentPane().add(labelTodosClientes);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		radioPorCliente.addActionListener(this);
		radioTodos.addActionListener(this);
		botaoLocalizarCliente.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			if(codCliente != 0 && radioPorCliente.isSelected() || radioTodos.isSelected()){
					HashMap hm = new HashMap();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					hm.put("datainicial", fieldDataInicial.getDate());
					hm.put("datafinal", fieldDataFinal.getDate());
					URL arquivo = null;
					
					if(radioTodos.isSelected()){
						arquivo = getClass().getResource("/br/com/sne/sistema/report/parcelasReceberPorClientePeriodo.jasper");  
					}else{
						hm.put("id", codCliente);
						arquivo = getClass().getResource("/br/com/sne/sistema/report/parcelasReceberPorClientePeriodoIndividual.jasper");  
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
				JOptionPane.showMessageDialog(this, "Selecione um Cliente para visualizar o relatório", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		if(e.getSource() == botaoLocalizarCliente){
			DialogSearchClient teste = new DialogSearchClient(TelaParamParcelasReceberPorClientePeriodo.this);
			Cliente c = teste.showDialog(TelaParamParcelasReceberPorClientePeriodo.this);
			if(c != null) {
				fieldCliente.setText(c.getId() + " - "+ c.getNome());
				codCliente = c.getId();
			}
	}
		
		if(e.getSource() == radioPorCliente){
			fieldCliente.setEnabled(true);
			botaoLocalizarCliente.setEnabled(true);
		}
		
		if(e.getSource() == radioTodos){
			fieldCliente.setEnabled(false);
			botaoLocalizarCliente.setEnabled(false);
		}
	}
	
	
}
