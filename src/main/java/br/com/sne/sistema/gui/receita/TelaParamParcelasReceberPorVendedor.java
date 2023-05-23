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
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.form.DialogSearchClient;
import br.com.sne.sistema.gui.util.form.DialogSearchFuncionario;

import com.toedter.calendar.JDateChooser;

public class TelaParamParcelasReceberPorVendedor extends JFrame implements ActionListener{

	//private JLabel labelDataInicial = null;
	private JLabel labelDataFinal = null;
	//private JDateChooser fieldDataInicial;
	private JDateChooser fieldDataFinal;
	private JLabel labelTitulo = null;
	private JLabel labelPorVendedor = null;
	private JLabel labelImagem = null;
	private JLabel labelTodosVendedores = null;
	private JTextField fieldVendedor = null;
	private JButton botaoGerar = null;
	private JRadioButton radioPorVendedor = null;
	private JRadioButton radioTodos = null;
	private JButton botaoLocalizarVendedor = null;
	private long codVendedor = 0;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamParcelasReceberPorVendedor(){
		
		//configurando a janela
		setTitle("Parcelas a Receber por Vendedor:");
		setSize(new Dimension(460, 295)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelTitulo = new JLabel("Informe abaixo como deseja que o relatório seja exibido:");
		//labelDataInicial = new JLabel("De:");
		labelDataFinal  = new JLabel("Período Até:");
		//fieldDataInicial = new JDateChooser(new Date());
		fieldDataFinal = new JDateChooser(new Date());
		labelPorVendedor  = new JLabel("Por Vendedor:");
		fieldVendedor = new JTextField();
		fieldVendedor.setEditable(false);
		labelTodosVendedores = new JLabel("Todos os Vendedores:");
		ButtonGroup bgroup = new ButtonGroup();
		radioPorVendedor = new JRadioButton();
		radioTodos = new JRadioButton();
		bgroup.add(radioPorVendedor);
		bgroup.add(radioTodos);
		radioPorVendedor.setSelected(true);
		botaoLocalizarVendedor = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/funcionario_48.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelTitulo.setBounds(new Rectangle(65, 20, 400, 20));
		//labelDataInicial.setBounds(new Rectangle(65, 50, 400, 29));
		labelDataFinal.setBounds(new Rectangle(65, 50, 400, 29));
		//fieldDataInicial.setBounds(new Rectangle(90, 50, 140, 29));
		fieldDataFinal.setBounds(new Rectangle(140, 50, 140, 29));
		radioPorVendedor.setBounds(new Rectangle(65, 120, 20, 25));
		labelPorVendedor.setBounds(new Rectangle(65, 90, 100, 25));
		fieldVendedor.setBounds(new Rectangle(90, 120, 265, 25));
		botaoLocalizarVendedor.setBounds(new Rectangle(360, 120, 45, 25));
		radioTodos.setBounds(new Rectangle(65, 160, 20, 25));
		labelTodosVendedores.setBounds(new Rectangle(90, 160, 200, 25));
		botaoGerar.setBounds(new Rectangle(150, 210, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o relatório!");
		botaoLocalizarVendedor.setToolTipText("Clique para Localizar Vendedores!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,12);
		labelPorVendedor.setFont(f1);
		labelTodosVendedores.setFont(f1);
		
		//Permissão
		if(!Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.RELATORIOS_VENDEDORES)) {
			labelTodosVendedores.setVisible(false);
			labelPorVendedor.setVisible(false);
			fieldVendedor.setVisible(false);
			radioTodos.setVisible(false);
			radioPorVendedor.setVisible(false);
			botaoLocalizarVendedor.setVisible(false);
		}
	
		//adicionando componete na janela
		getContentPane().add(labelDataFinal);
		//getContentPane().add(labelDataInicial);
		//getContentPane().add(fieldDataInicial);
		getContentPane().add(fieldDataFinal);
		getContentPane().add(labelTitulo);
		getContentPane().add(radioPorVendedor);
		getContentPane().add(labelPorVendedor);
		getContentPane().add(fieldVendedor);
		getContentPane().add(botaoLocalizarVendedor);
		getContentPane().add(radioTodos);
		getContentPane().add(labelTodosVendedores);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		radioPorVendedor.addActionListener(this);
		radioTodos.addActionListener(this);
		botaoLocalizarVendedor.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			if(!Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.RELATORIOS_VENDEDORES)) {
				codVendedor = Facade.getInstance().getUsuarioLogado().getFuncionario().getId();
			}
			if(codVendedor != 0 && radioPorVendedor.isSelected() || radioTodos.isSelected()){
					HashMap hm = new HashMap();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					//hm.put("datainicial", fieldDataInicial.getDate());
					hm.put("datafinal", fieldDataFinal.getDate());
					URL arquivo = null;
					
					if(radioTodos.isSelected()){
						arquivo = getClass().getResource("/br/com/sne/sistema/report/parcelasReceberPorVendedor.jasper");  
					}else{
						hm.put("id", codVendedor);
						arquivo = getClass().getResource("/br/com/sne/sistema/report/parcelasReceberPorVendedorIndividual.jasper");  
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
				JOptionPane.showMessageDialog(this, "Selecione um Vendedor para visualizar o relatório", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		if(e.getSource() == botaoLocalizarVendedor){
			List<Funcionario> listaVendedor;
			listaVendedor = Facade.getInstance().listarFuncionarios();
			DialogSearchFuncionario teste = new DialogSearchFuncionario(TelaParamParcelasReceberPorVendedor.this, listaVendedor);
			Funcionario f = teste.showDialog(TelaParamParcelasReceberPorVendedor.this);
			if(f != null) {
				fieldVendedor.setText(f.getId() + " - "+ f.getNome());
				codVendedor = f.getId();
			}
	}
		
		if(e.getSource() == radioPorVendedor){
			fieldVendedor.setEnabled(true);
			botaoLocalizarVendedor.setEnabled(true);
		}
		
		if(e.getSource() == radioTodos){
			fieldVendedor.setEnabled(false);
			botaoLocalizarVendedor.setEnabled(false);
		}
	}
	
	
}
