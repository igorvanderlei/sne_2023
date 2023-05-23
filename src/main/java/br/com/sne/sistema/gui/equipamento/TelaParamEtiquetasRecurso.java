package br.com.sne.sistema.gui.equipamento;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.facade.Facade;

public class TelaParamEtiquetasRecurso extends JFrame implements ActionListener, MouseListener{

	private JLabel labelCodigo = null;
	private JLabel labelInformacao = null;
	private JLabel labelImagem = null;
	private JComboBox comboxRecurso = null;
	private JButton botaoGerar = null;
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamEtiquetasRecurso(){ 
		 
		//configurando a janela
		setTitle("Etiquetas de Equipamentos por Recurso");
		setSize(new Dimension(460, 200)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelInformacao = new JLabel("Selecione abaixo o recurso desejado.");
		labelCodigo  = new JLabel("Recurso:");
		comboxRecurso = new JComboBox();
		botaoGerar = new JButton("Gerar Etiquetas");
		Icon imagem = new ImageIcon(getClass().getResource("/images/etiqueta_48.png"));
		labelImagem = new JLabel(imagem);
		
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelInformacao.setBounds(new Rectangle(65, 20, 400, 20));
		labelCodigo.setBounds(new Rectangle(65, 50, 400, 25));
		comboxRecurso.setBounds(new Rectangle(65, 75, 327, 27));
		botaoGerar.setBounds(new Rectangle(150, 125, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 3, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar as Etiquetas!");
		comboxRecurso.setToolTipText("Selecione um recurso!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,16);
		labelCodigo.setFont(f1);
		
		//adicionando itens no combobox
		//comboxRecurso.addItem("");
		for(Recurso recursoItem: Facade.getInstance().listarRecursosAtivos()){	
				comboxRecurso.addItem(recursoItem); 
		}
		//configurando combobox para exibir apenas 5 linhas
		comboxRecurso.setMaximumRowCount(5);
		
		//adicionando componete na janela
		getContentPane().add(labelInformacao);
		getContentPane().add(labelCodigo);
		getContentPane().add(comboxRecurso);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
		
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		comboxRecurso.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			//recebe a entrada do usuário
			Recurso recursoEscolhido = (Recurso) comboxRecurso.getSelectedItem();
		
				HashMap hm = new HashMap();
				hm.put("id", recursoEscolhido.getId());
				try {
					setVisible(false);
					java.sql.Connection c  = Facade.getInstance().getConnection();
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/etiquetasEquipamentosPorRecurso.jasper");  
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
}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
