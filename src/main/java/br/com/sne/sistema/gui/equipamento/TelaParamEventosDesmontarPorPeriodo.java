package br.com.sne.sistema.gui.equipamento;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.equipamentoenviado.FormEnviarEquipamento;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.form.DialogSearchEquipamento;
import br.com.sne.sistema.gui.util.form.DialogSearchGrupo;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class TelaParamEventosDesmontarPorPeriodo extends JFrame implements ActionListener{

	private JLabel labelDataInicial = null;
	private JLabel labelDataFinal = null;
	private JLabel labelTitulo = null;
	private JDateChooser fieldDataInicial;
	private JDateChooser fieldDataFinal;
	private JLabel labelImagem = null;
	private JButton botaoGerar = null;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamEventosDesmontarPorPeriodo(){
		
		//configurando a janela
		setTitle("Relatório de Eventos à Desmontar por Período");
		setSize(new Dimension(460, 180)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		
	
		labelTitulo = new JLabel("Selecione o intervalo entre Datas:");
		labelDataInicial = new JLabel("De:");
		labelDataFinal  = new JLabel("Até:");
		fieldDataInicial = new JDateChooser(new Date());
		fieldDataFinal = new JDateChooser(new Date());
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/equipamento.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		labelTitulo.setBounds(new Rectangle(65, 20, 800, 29));
		
		labelDataInicial.setBounds(new Rectangle(65, 50, 400, 29));
		labelDataFinal.setBounds(new Rectangle(240, 50, 400, 29));
		fieldDataInicial.setBounds(new Rectangle(90, 50, 140, 29));
		fieldDataFinal.setBounds(new Rectangle(265, 50, 140, 29));
		botaoGerar.setBounds(new Rectangle(150, 100, 140, 28));
		
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o Relatório!");

		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,14);
		labelTitulo.setFont(f1);

	
		//adicionando componete na janela
		getContentPane().add(labelTitulo);
		getContentPane().add(labelDataFinal);
		getContentPane().add(labelDataInicial);
		getContentPane().add(fieldDataInicial);
		getContentPane().add(fieldDataFinal);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			//recebe a entrada do usuário
	
				HashMap hm = new HashMap();
				hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
				hm.put("datainicial", fieldDataInicial.getDate());
				hm.put("datafinal", fieldDataFinal.getDate());
				try {
					setVisible(false);
					java.sql.Connection c  = Facade.getInstance().getConnection();
					//URL arquivo = getClass().getResource("/br/com/sne/sistema/report/eventosDesmontarPorPeriodo.jasper");  
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/eventosDesmontarPorPeriodo.jasper");
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
					JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
					JasperViewer.viewReport(impressao,false);
					c.close();
				} catch (Exception e1) {
				e1.printStackTrace();
				}
			}
}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
