package br.com.sne.sistema.gui.orcamento;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

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
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.form.DialogSearchOS;
import br.com.sne.sistema.gui.util.form.DialogSearchOrcamento2;


public class TelaParamOrcamentos extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTitulo = null;
	private JLabel labelPorOrcamento = null;
	private JLabel labelImagem = null;
	private JTextField fieldOrcamento = null;
	private JButton botaoGerar = null;
	private JButton botaoLocalizarOrcamento = null;
	private long codOrcamento = 0;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamOrcamentos(){
		
		//configurando a janela
		setTitle("Orçamentos com todos os valores:");
		setSize(new Dimension(460, 295)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelTitulo = new JLabel("Escolha abaixo o Orçamento desejado:");
		labelPorOrcamento  = new JLabel("Orçamento:");
		fieldOrcamento = new JTextField();
		fieldOrcamento.setEditable(false);
		botaoLocalizarOrcamento = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/orcamento_48.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelTitulo.setBounds(new Rectangle(65, 20, 400, 20));
		labelPorOrcamento.setBounds(new Rectangle (65, 90, 150, 25));
		fieldOrcamento.setBounds(new Rectangle(90, 120, 265, 25));
		botaoLocalizarOrcamento.setBounds(new Rectangle(360, 120, 45, 25));
		botaoGerar.setBounds(new Rectangle(150, 210, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o relatório!");
		botaoLocalizarOrcamento.setToolTipText("Clique para Localizar Orcamentos!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,12);
		labelPorOrcamento.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelTitulo);
		getContentPane().add(labelPorOrcamento);
		getContentPane().add(fieldOrcamento);
		getContentPane().add(botaoLocalizarOrcamento);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		botaoLocalizarOrcamento.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			if(codOrcamento != 0 ){
					HashMap hm = new HashMap();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					hm.put("cidade", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
					ImageIcon logos =  new ImageIcon(getClass().getResource("/images/logos.png"));
					hm.put("logos", logos.getImage());
					hm.put("id", codOrcamento);
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/orcamento_precoCusto.jasper");  
					
					try {
						setVisible(false);
						java.sql.Connection c  = Facade.getInstance().getConnection();  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			
			}else{
				JOptionPane.showMessageDialog(this, "Selecione um Orçamento para visualizar o relatório", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		if(e.getSource() == botaoLocalizarOrcamento){
			DialogSearchOrcamento2 teste = new DialogSearchOrcamento2(TelaParamOrcamentos.this);
			Orcamento c = teste.showDialog(TelaParamOrcamentos.this);
			if(c != null) {
				fieldOrcamento.setText(c.getId() + " - "+ c.getNomeEvento());
				codOrcamento = c.getId();
			}
	}
	}
	
	
}
