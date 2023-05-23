package br.com.sne.sistema.gui.equipamento;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.form.DialogSearchEquipamento;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class TelaParamEtiquetasCodigo extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel labelCodigo = null;
	private JLabel labelInformacao = null;
	private JLabel labelImagem = null;
	private JTextField txtCodigo = null;
	private JButton botaoGerar = null;
	private JButton botaoLocalizarEquipamento = null;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamEtiquetasCodigo(){
		
		//configurando a janela
		setTitle("Etiqueta de Equipamento Por Código:");
		setSize(new Dimension(460, 200)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelInformacao = new JLabel("Informe abaixo o código do equipamento.");
		labelCodigo  = new JLabel("Código:");
		txtCodigo = new JTextField(24);
		botaoGerar = new JButton("Gerar Etiqueta");
		Icon imagem = new ImageIcon(getClass().getResource("/images/etiqueta_48.png"));
		labelImagem = new JLabel(imagem);
		botaoLocalizarEquipamento = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelInformacao.setBounds(new Rectangle(65, 20, 400, 20));
		labelCodigo.setBounds(new Rectangle(65, 50, 400, 25));
		txtCodigo.setBounds(new Rectangle(65, 75, 272, 27));
		botaoGerar.setBounds(new Rectangle(150, 125, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		botaoLocalizarEquipamento.setBounds(new Rectangle(338, 74, 45, 29));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar a Etiqueta!");
		txtCodigo.setToolTipText("Digite o Código do Equipamento!");
		botaoLocalizarEquipamento.setToolTipText("Clique para Localizar Equipamentos!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,16);
		labelCodigo.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelInformacao);
		getContentPane().add(labelCodigo);
		getContentPane().add(txtCodigo);
		getContentPane().add(botaoLocalizarEquipamento);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		txtCodigo.addActionListener(this);
		botaoLocalizarEquipamento.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			//recebe a entrada do usuário
			String inputCodigo = txtCodigo.getText();
	
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put("id", Long.parseLong(inputCodigo));
				try {
					setVisible(false);
					java.sql.Connection c  = Facade.getInstance().getConnection();
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/etiquetaEquipamentoPorCodigo.jasper");  
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
					/*JasperViewer.viewReport(print,false);*/
				} catch (Exception e1) {
				e1.printStackTrace();
				}
			}
		
		if(e.getSource() == botaoLocalizarEquipamento){
				List<Equipamento> listaEquipamento;
				listaEquipamento = Facade.getInstance().listarEquipamento();
				DialogSearchEquipamento teste = new DialogSearchEquipamento(TelaParamEtiquetasCodigo.this, listaEquipamento);
				Equipamento c = teste.showDialog(TelaParamEtiquetasCodigo.this);
				if(c != null) {
					txtCodigo.setText("" +c.getId());
				}
		}
}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
