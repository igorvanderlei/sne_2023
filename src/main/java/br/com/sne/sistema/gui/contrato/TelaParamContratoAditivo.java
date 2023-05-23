package br.com.sne.sistema.gui.contrato;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.ImageFilter;
import br.com.sne.sistema.gui.util.form.DialogSearchOSExtrasAprovadas;


public class TelaParamContratoAditivo extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTitulo = null;
	private JLabel labelImagem = null;
	private JLabel labelOS = null;
	private JTextField fieldOS = null;
	private JButton botaoGerar = null;
	private JButton botaoAssinatura = null;
	private JButton botaoLocalizarOS = null;
	private long codOS = 0;

	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamContratoAditivo(){
		
		//configurando a janela
		setTitle("Gerar contrato aditivo de OS extra aprovada:");
		setSize(new Dimension(460, 295)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelTitulo = new JLabel("Informe abaixo a OS para o contrato:");
		labelOS  = new JLabel("Ordem de Serviço:");
		fieldOS = new JTextField();
		botaoLocalizarOS = new JButton("Localizar", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoGerar = new JButton("Gerar Contrato");
		botaoAssinatura = new JButton("Enviar Assinatura");
		Icon imagem = new ImageIcon(getClass().getResource("/images/agendamento_48.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelTitulo.setBounds(new Rectangle(65, 20, 400, 20));
		labelOS.setBounds(new Rectangle(65, 90, 200, 25));
		fieldOS.setBounds(new Rectangle(90, 120, 265, 25));
		botaoLocalizarOS.setBounds(new Rectangle(360, 120, 45, 25));
		botaoGerar.setBounds(new Rectangle(85, 210, 140, 28));
		botaoAssinatura.setBounds(new Rectangle(235, 210, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o relatório!");
		botaoAssinatura.setToolTipText("Clique para salvar a assinatura digital!");
		botaoLocalizarOS.setToolTipText("Clique para Localizar Ordens de Serviço!");
	
		//adicionando componete na janela
		getContentPane().add(labelOS);
		getContentPane().add(fieldOS);
		getContentPane().add(botaoLocalizarOS);
		getContentPane().add(labelTitulo);
		getContentPane().add(botaoGerar);
		getContentPane().add(botaoAssinatura);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		botaoAssinatura.addActionListener(this);
		botaoLocalizarOS.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			if(codOS != 0 ){
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					hm.put("id", codOS);
					hm.put("cidade", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
					hm.put("estado", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getEstado());
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/contratoAditivo.jasper");  

					try {
						setVisible(false);
						java.sql.Connection c  = Facade.getInstance().getConnection();  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
						
//						if(ordemServico.getContrato() != null) {
//							Contrato contrato = ordemServico.getContrato();
//							OutputStream output = new FileOutputStream(new File("Contrato.pdf")); 
//							JasperExportManager.exportReportToPdfStream(impressao, output); 
//							output.close();
//							
//							File arquivoContrato = new File("Contrato.pdf");
//							contrato.alterarContrato(arquivoContrato);
//							
//							Facade.getInstance().atualizarContrato(contrato);
//							arquivoContrato.delete();
//							output.close();
//						}
//						else {
//							Contrato contrato = new Contrato();
//							OutputStream output = new FileOutputStream(new File("Contrato.pdf")); 
//							JasperExportManager.exportReportToPdfStream(impressao, output); 
//							output.close();
//							
//							File arquivoContrato = new File("Contrato.pdf");
//							contrato.alterarContrato(arquivoContrato);
//							
//							ordemServico.setContrato(contrato);
//							ordemServico.setGerouContrato(true);
//							
//							Facade.getInstance().salvarContrato(contrato);
//							Facade.getInstance().atualizarOrdemServico(ordemServico);
//						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			
			}else{
				JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para visualizar o relatório", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		if(e.getSource() == botaoLocalizarOS){
			DialogSearchOSExtrasAprovadas teste = new DialogSearchOSExtrasAprovadas(TelaParamContratoAditivo.this);
			OrdemServico c = teste.showDialog(TelaParamContratoAditivo.this);
			if(c != null) {
				fieldOS.setText(c.getId() + " - "+ c.getNomeEvento());
				codOS = c.getId();
			}
		}
		
		if(e.getSource() == botaoAssinatura){
			if(codOS != 0 ){

				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new ImageFilter());
				fc.setAcceptAllFileFilterUsed(false);
		        int returnVal = fc.showOpenDialog(TelaParamContratoAditivo.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
//		        	File file = fc.getSelectedFile();
		        	
//					if(ordemServico.getContrato() != null) {
//						Contrato contrato = ordemServico.getContrato();
//				        contrato.alterarImagemAssinatura(file.getAbsolutePath());
//				        Facade.getInstance().atualizarContrato(contrato);
//
//					}
//					else {
//						Contrato contrato = new Contrato();
//				        contrato.alterarImagemAssinatura(file.getAbsolutePath());
//			            ordemServico.setContrato(contrato);
//				        Facade.getInstance().salvarContrato(contrato);
//						Facade.getInstance().atualizarOrdemServico(ordemServico);
//
//					}
					JOptionPane.showMessageDialog(null,"Assinatura alterada com sucesso!");

		        }
			}
			else{
				JOptionPane.showMessageDialog(this, "Selecione uma Ordem de Serviço para salvar a assinatura", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
}
