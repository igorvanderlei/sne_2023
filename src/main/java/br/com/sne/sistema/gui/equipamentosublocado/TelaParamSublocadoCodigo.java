package br.com.sne.sistema.gui.equipamentosublocado;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.RegistroSublocacao;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.main.TelaPrincipal;
import br.com.sne.sistema.gui.util.form.DialogSearchRegistroSublocacao;

public class TelaParamSublocadoCodigo extends JFrame implements ActionListener{

	private JLabel labelCodigo = null;
	private JLabel labelInformacao = null;
	private JLabel labelImagem = null;
	private JTextField txtCodigo = null;
	private JButton botaoGerar = null;
	private JButton botaoLocalizarRegistroSublocacao = null;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamSublocadoCodigo(){
		
		//configurando a janela
		setTitle("Etiquetas de Equipamentos Sublocados:");
		setSize(new Dimension(460, 200)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		txtCodigo = new JTextField();
		labelInformacao = new JLabel("Informe abaixo o código do registro de Sublocação.");
		labelCodigo  = new JLabel("Código:");
		botaoGerar = new JButton("Gerar Etiqueta");
		Icon imagem = new ImageIcon(getClass().getResource("/images/etiqueta_48.png"));
		labelImagem = new JLabel(imagem);
		botaoLocalizarRegistroSublocacao = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelInformacao.setBounds(new Rectangle(65, 20, 400, 20));
		labelCodigo.setBounds(new Rectangle(65, 50, 400, 25));
		txtCodigo.setBounds(new Rectangle(65, 75, 272, 27));
		botaoGerar.setBounds(new Rectangle(150, 125, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		botaoLocalizarRegistroSublocacao.setBounds(new Rectangle(338, 74, 45, 29));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar a Etiqueta!");
		txtCodigo.setToolTipText("Digite o Código do Equipamento!");
		botaoLocalizarRegistroSublocacao.setToolTipText("Clique para Localizar Equipamentos!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,16);
		labelCodigo.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelInformacao);
		getContentPane().add(labelCodigo);
		getContentPane().add(txtCodigo);
		getContentPane().add(botaoLocalizarRegistroSublocacao);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		txtCodigo.addActionListener(this);
		botaoLocalizarRegistroSublocacao.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
				HashMap hm = new HashMap();
				hm.put("id", Long.parseLong(txtCodigo.getText()));
				try {
					setVisible(false);
					
					java.sql.Connection c  = Facade.getInstance().getConnection();
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/etiquetasSublocadoPorSublocacao.jasper");  
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
					JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, c);
					byte[] bytes = JasperExportManager.exportReportToPdf(print);
					

					//Cria um arquivo temporário em pdf
					File f = File.createTempFile("rel", ".pdf" );
					f.deleteOnExit();
				    FileOutputStream fos = new FileOutputStream(f);
				    fos.write(bytes);
				    fos.flush();
				    fos.close();
					
					//Imprime o arquivo na impressora padrão do windows
					java.awt.Desktop desktop = java.awt.Desktop.getDesktop();      
					desktop.print(f);
					c.close();
					
				} catch (Exception e1) {
				e1.printStackTrace();
				}
			}
		
		if(e.getSource() == botaoLocalizarRegistroSublocacao){
				List<RegistroSublocacao> listaRegistroSublocacao;
				listaRegistroSublocacao = Facade.getInstance().listarRegistroSublocacaos();
				DialogSearchRegistroSublocacao teste = new DialogSearchRegistroSublocacao(TelaParamSublocadoCodigo.this, listaRegistroSublocacao);
				RegistroSublocacao c = teste.showDialog(TelaParamSublocadoCodigo.this);
				if(c != null) {
					txtCodigo.setText("" +c.getId());
				}
		}
}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
