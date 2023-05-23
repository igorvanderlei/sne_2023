package br.com.sne.sistema.gui.despesa;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Recibo;
import br.com.sne.sistema.facade.Facade;


public class TelaParamExibirRecibo extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel imagemLabel = new JLabel();
	private JButton botaoImprimir;
	private Recibo recibo;
	
	public TelaParamExibirRecibo(Despesa despesa) {
		this.recibo = Facade.getInstance()
				.localizarReciboPorDespesa(despesa);
		
		setSize(new Dimension(600, 800));
		setTitle("Recibo");
		setLocationRelativeTo(null);
		if(recibo != null) {
		ImageIcon imgIcon = new ImageIcon(recibo.getImagemReciboImage().getImage().getScaledInstance(420, 620, Image.SCALE_DEFAULT));
		imagemLabel.setIcon(imgIcon);
		botaoImprimir = new JButton("Imprimir Recibo");
		
		getContentPane().setLayout(null);
		
		imagemLabel.setBounds(new Rectangle(30, 30, 420, 620));
		botaoImprimir.setBounds(new Rectangle(200, 660, 120, 28));
		
		botaoImprimir.setToolTipText("Clique para Imprimir o Recibo!");
		
		getContentPane().add(imagemLabel);
		getContentPane().add(botaoImprimir);
		
		botaoImprimir.addActionListener(this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoImprimir) {
			//recebe a entrada do usuário
	
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, 
						new FileOutputStream("Recibo.pdf"));
				document.open();
				com.lowagie.text.Image imgRecibo = com.lowagie.text.Image.getInstance(recibo.getImagemReciboImage().getImage(),null);
				float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
			               - document.rightMargin()) / imgRecibo.getWidth()) * 100;

				imgRecibo.scalePercent(scaler);
				document.add(imgRecibo);
				document.close();
				
				File file = new File("Recibo.pdf");
				Desktop.getDesktop().open(file);
				
			} catch (FileNotFoundException | DocumentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
