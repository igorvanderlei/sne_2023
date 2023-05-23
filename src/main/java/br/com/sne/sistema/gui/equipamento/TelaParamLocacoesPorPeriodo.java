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

public class TelaParamLocacoesPorPeriodo extends JFrame implements ActionListener{
	private JLabel labelDataInicial = null;
	private JLabel labelDataFinal = null;
	private JLabel labelTitulo = null;
	private JLabel labelTitulo2 = null;
	private JDateChooser fieldDataInicial;
	private JDateChooser fieldDataFinal;
	private JLabel labelGrupoInicial = null;
	private JLabel labelGrupoFinal = null;
	private JLabel labelImagem = null;
	private JTextField txtCodGrupoInicial = null;
	private JTextField txtCodGrupoFinal = null;
	private JButton botaoGerar = null;
	private JButton botaoLocalizarGrupoInicial = null;
	private JButton botaoLocalizarGrupoFinal = null;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamLocacoesPorPeriodo(){
		
		//configurando a janela
		setTitle("Relatório de Locações por Período");
		setSize(new Dimension(460, 250)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		MaskFormatter formatoCodigoTres;
		try {
			formatoCodigoTres = new MaskFormatter("###");
			formatoCodigoTres.setPlaceholder("000");
			txtCodGrupoInicial = new JFormattedTextField(formatoCodigoTres);
			txtCodGrupoFinal = new JFormattedTextField(formatoCodigoTres);
		} catch (ParseException e) {
			txtCodGrupoInicial = new JTextField();
			txtCodGrupoFinal = new JTextField();
			e.printStackTrace();
		}
	
		labelTitulo = new JLabel("Selecione o intervalo entre Datas:");
		labelTitulo2 = new JLabel("Selecione o intervalo entre Grupos:");
		labelDataInicial = new JLabel("De:");
		labelDataFinal  = new JLabel("Até:");
		fieldDataInicial = new JDateChooser(new Date());
		fieldDataFinal = new JDateChooser(new Date());
		labelGrupoInicial = new JLabel("De:");
		labelGrupoFinal = new JLabel("Até:");
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/equipamento.png"));
		labelImagem = new JLabel(imagem);
		botaoLocalizarGrupoInicial = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoLocalizarGrupoFinal = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		labelTitulo.setBounds(new Rectangle(65, 20, 800, 29));
		labelTitulo2.setBounds(new Rectangle(65, 90, 800, 29));
		labelDataInicial.setBounds(new Rectangle(65, 50, 400, 29));
		labelDataFinal.setBounds(new Rectangle(240, 50, 400, 29));
		fieldDataInicial.setBounds(new Rectangle(90, 50, 140, 29));
		fieldDataFinal.setBounds(new Rectangle(265, 50, 140, 29));
		labelGrupoInicial.setBounds(new Rectangle(65, 120, 100, 29));
		txtCodGrupoInicial.setBounds(new Rectangle(90, 120, 100, 29));
		labelGrupoFinal.setBounds(new Rectangle(240, 120, 100, 29));
		txtCodGrupoFinal.setBounds(new Rectangle(265, 120, 100, 29));
		botaoLocalizarGrupoInicial.setBounds(new Rectangle(190, 120, 40, 29));
		botaoLocalizarGrupoFinal.setBounds(new Rectangle(365, 120, 40, 29));
		botaoGerar.setBounds(new Rectangle(150, 175, 140, 28));
		
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o Relatório!");
		botaoLocalizarGrupoInicial.setToolTipText("Clique para Localizar Grupos!");
		botaoLocalizarGrupoFinal.setToolTipText("Clique para Localizar Grupos!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,14);
		labelTitulo.setFont(f1);
		labelTitulo2.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelTitulo);
		getContentPane().add(labelTitulo2);
		getContentPane().add(labelDataFinal);
		getContentPane().add(labelDataInicial);
		getContentPane().add(fieldDataInicial);
		getContentPane().add(fieldDataFinal);
		getContentPane().add(labelGrupoInicial);
		getContentPane().add(labelGrupoFinal);
		getContentPane().add(txtCodGrupoInicial);
		getContentPane().add(txtCodGrupoFinal);
		getContentPane().add(botaoLocalizarGrupoInicial);
		getContentPane().add(botaoLocalizarGrupoFinal);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		botaoLocalizarGrupoInicial.addActionListener(this);
		botaoLocalizarGrupoFinal.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			//recebe a entrada do usuário
	
				HashMap hm = new HashMap();
				hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
				hm.put("grupo_inicial", Integer.parseInt(txtCodGrupoInicial.getText()));
				hm.put("grupo_final", Integer.parseInt( txtCodGrupoFinal.getText()));
				hm.put("data_inicial", fieldDataInicial.getDate());
				hm.put("data_final", fieldDataFinal.getDate());
				try {
					setVisible(false);
					java.sql.Connection c  = Facade.getInstance().getConnection();
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/equipamentosLocadosPorPeriodo.jasper");  
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
					JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
					JasperViewer.viewReport(impressao,false);
					c.close();
				} catch (Exception e1) {
				e1.printStackTrace();
				}
			}
		
		if(e.getSource() == botaoLocalizarGrupoInicial){
				List<Grupo> listaGrupo;
				listaGrupo = Facade.getInstance().listarGrupos();
				DialogSearchGrupo teste = new DialogSearchGrupo(TelaParamLocacoesPorPeriodo.this, listaGrupo);
				Grupo c = teste.showDialog(TelaParamLocacoesPorPeriodo.this);
				if(c != null) {
					txtCodGrupoInicial.setText("" +c.getCodigo());
				}
		}
		
		if(e.getSource() == botaoLocalizarGrupoFinal){
			List<Grupo> listaGrupo;
			listaGrupo = Facade.getInstance().listarGrupos();
			DialogSearchGrupo teste = new DialogSearchGrupo(TelaParamLocacoesPorPeriodo.this, listaGrupo);
			Grupo c = teste.showDialog(TelaParamLocacoesPorPeriodo.this);
			if(c != null) {
				txtCodGrupoFinal.setText("" +c.getCodigo());
			}
	}
}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
