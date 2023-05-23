package br.com.sne.sistema.gui.contrato;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import com.ibm.icu.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Contrato;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoContrato;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.PDFFilter;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchOS;

public class FormContrato extends DefaultForm<Contrato, ContratoTableModel>{

	private static final long serialVersionUID = 1L;
	private JTextField fieldNomeEvento;
	private JTextField fieldIdOS;
	private JTextField fieldIdContrato;
	private JTextField fieldDataInicial;
	private JTextField fieldDataFinal;
	private JTextField fieldTipoContrato;
	private JTextArea fieldObservacoes;

	private JTextField fieldNomeCliente;
	private JTextField fieldCNPJCliente;

	private JButton botaoInserirAssinatura;
	private JButton botaoLocalizarOS;
	private OrdemServico ordemServico;
	private Contrato contrato;
	
	private SwingController pdfContrato;
	private SwingController pdfAssinatura;
	private String contratoStr = "";
	private String assinaturaStr = "";
	
	private JTabbedPane tabDetalhes;


	
	public FormContrato(){
		super(new ContratoTableModel(), "/images/icon_contrato_18.png", "Contratos");
	}
	
	private JButton getBotaoAssinatura() {
		if(botaoInserirAssinatura == null) {
			botaoInserirAssinatura = new JButton("Inserir Contrato Assinado pelo Cliente");

			botaoInserirAssinatura.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JFileChooser fc = new JFileChooser();
							fc.setFileFilter(new PDFFilter());
							fc.setAcceptAllFileFilterUsed(false);
							int returnVal = fc.showOpenDialog(FormContrato.this);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								File file = fc.getSelectedFile();
								FileInputStream fin;
								try {
									fin = new FileInputStream(file);
									byte buffer[];
									byte conteudo[] = new byte[(int)file.length()];
									fin.read(conteudo);
									fin.close();
									buffer = conteudo;
									pdfAssinatura.openDocument(buffer, 0, buffer.length, "Assinatura", null);
									assinaturaStr = Base64.getEncoder().encodeToString(buffer);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					);
		}
		return botaoInserirAssinatura;
	}

	private JButton getBotaoLocalizarOS() {
		if(botaoLocalizarOS == null) {
			botaoLocalizarOS = new JButton("" , new ImageIcon(getClass().getResource("/images/find.png")));
			botaoLocalizarOS.setHorizontalAlignment(SwingConstants.CENTER);
			botaoLocalizarOS.setVerticalAlignment(SwingConstants.CENTER);
			botaoLocalizarOS.setPreferredSize(new Dimension(20,20));
			botaoLocalizarOS.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							DialogSearchOS teste = new DialogSearchOS(FormContrato.this);
							OrdemServico o = teste.showDialog(FormContrato.this);
							if(o != null) {
								carregarOS(o);
							}
						}
					}
					);
		}
		return botaoLocalizarOS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void carregarOS(OrdemServico o){
		ordemServico = o;
		if(o!=null){
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			fieldIdOS.setText(o.getId()+"");
			fieldNomeEvento.setText(o.getNomeEvento());
			fieldDataInicial.setText(formato.format(o.getDataInicio()));
			fieldDataFinal.setText(formato.format(o.getDataFim()));
			fieldNomeCliente.setText(o.getCliente().getNome());
			fieldCNPJCliente.setText(o.getCliente().getCnpj());
			fieldObservacoes.setText(o.getObservacoes());
			
			HashMap hm = new HashMap();
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			hm.put("id", ordemServico.getId());
			hm.put("cidade", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
			hm.put("estado", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getEstado());
			ImageIcon assinatura =  new ImageIcon(getClass().getResource("/images/assinatura.png"));
			hm.put("assinatura", assinatura.getImage());
			
			URL arquivo = null;
				
			if(ordemServico.getOSOriginal() == null)
				arquivo = getClass().getResource("/br/com/sne/sistema/report/contrato.jasper");  
			else
				arquivo = getClass().getResource("/br/com/sne/sistema/report/contratoAditivo.jasper");  

			if(ordemServico.isGerouContrato())
				desabilitarBotaoAdicionar();
			
			try {
				java.sql.Connection c  = Facade.getInstance().getConnection();  
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
				JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
					
				OutputStream output = new FileOutputStream(new File("Contrato.pdf"));
				
				JasperExportManager.exportReportToPdfStream(impressao, output); 
				output.close();
				c.close();
					
				File arquivoContrato = new File("Contrato.pdf");
				FileInputStream fin = new FileInputStream(arquivoContrato);
	        	byte buffer[];
	            byte conteudo[] = new byte[(int)arquivoContrato.length()];
	            fin.read(conteudo);
	            fin.close();
	            buffer = conteudo;
	    		contratoStr = Base64.getEncoder().encodeToString(buffer);
				pdfContrato.openDocument(buffer, 0, buffer.length, "Contrato OS " + ordemServico.getId(), null);
				arquivoContrato.delete();

			} catch (Exception e1) {
					e1.printStackTrace();
			}
		} else {
			limparOS();
		}
	}
	
	public JPanel getPanelCodigoOS() {
		JPanel panelOS = new JPanel();
		panelOS.setLayout(new BoxLayout(panelOS, BoxLayout.LINE_AXIS));
		JButton botaoCliente = getBotaoLocalizarOS();
		botaoCliente.setPreferredSize(new Dimension(30,18));
		panelOS.add(fieldIdOS);
		panelOS.add(botaoCliente);
		return panelOS;
	}

	@Override
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(ordemServico == null) {
			test = false;
			error += "\nSelecione uma ordem de serviço";
		}
		else if(ordemServico.isGerouContrato()) {
			test = false;
			error += "\nO contrato desta ordem de serviço já foi gerado";
		}

		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	@Override
	protected boolean validateFormUpdate() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(ordemServico == null) {
			test = false;
			error += "\nSelecione uma ordem de serviço";
		}

		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	@Override
	public boolean save(Contrato current) {
		boolean s = false;

		contrato = current;
		
		current.setOrdemServico(ordemServico);
		if(ordemServico.getOSOriginal() == null)	
			current.setTipo(TipoContrato.CONTRATO);
		else
			current.setTipo(TipoContrato.TERMO_ADITIVO);
		
		
		Facade.getInstance().salvarContrato(current);
		
		/*
		 * ordemServico.setGerouContrato(true);
		 * ordemServico.setStatus(StatusOS.APROVADA);
		 * Facade.getInstance().atualizarOrdemServico(ordemServico);
		 */
		
		
		int resp = JOptionPane.showConfirmDialog(null,"Deseja imprimir o Contrato ?","Confirmação" ,JOptionPane.OK_CANCEL_OPTION);
		if( resp == JOptionPane.OK_OPTION)
			print(current);

		clear();
		
		s = true;
		return s;
	}

	@Override
	public boolean update(Contrato current) {
		Facade.getInstance().atualizarContrato(current);
		return true;
	}

	@Override
	public boolean remove(Contrato current) {
		Facade.getInstance().removerContrato(current);
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean print(Contrato current) {
		if(contrato != null) {
			HashMap hm = new HashMap();
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			hm.put("id", ordemServico.getId());
			hm.put("cidade", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getCidade());
			hm.put("estado", Facade.getInstance().getUsuarioLogado().getUnidade().getEndereco().getEstado());
			ImageIcon assinatura =  new ImageIcon(getClass().getResource("/images/assinatura.png"));
			hm.put("assinatura", assinatura.getImage());
			
			URL arquivo = null;
			
			if(contrato.getOrdemServico().getOSOriginal() == null)
				arquivo = getClass().getResource("/br/com/sne/sistema/report/contrato.jasper");  
			else
				arquivo = getClass().getResource("/br/com/sne/sistema/report/contratoAditivo.jasper");  

			try {
				java.sql.Connection c  = Facade.getInstance().getConnection();  
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
				JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
				JasperViewer.viewReport(impressao,false);
				c.close();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}				
		return true;
	}

	@Override
	public void loadInputFields(Contrato t) {
		t.setImagemAssinatura(assinaturaStr);
		t.setDocumentoContrato(contratoStr);
	}

	@Override
	protected void loadForm(Contrato rec) {
		clear();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		botaoLocalizarOS.setEnabled(false);
		
		fieldNomeEvento.setText(rec.getOrdemServico().getNomeEvento());;
		fieldIdOS.setText(rec.getOrdemServico().getId()+"");
		fieldIdContrato.setText(rec.getId()+"");
		fieldDataInicial.setText(formato.format(rec.getOrdemServico().getDataInicio()));
		fieldDataFinal.setText(formato.format(rec.getOrdemServico().getDataFim()));
		fieldTipoContrato.setText(rec.getTipo().name().replace("_", " "));
		fieldNomeCliente.setText(rec.getOrdemServico().getCliente().getNome());
		fieldCNPJCliente.setText(rec.getOrdemServico().getCliente().getCnpj());
		fieldObservacoes.setText(rec.getObservacoes());
		
		contratoStr = rec.getDocumentoContrato();
		assinaturaStr = rec.getImagemAssinatura();
		
		if(contratoStr != null && ! contratoStr.trim().equals("")) { 
			byte buffer[] = OrdemServico.converterStringPlanilha(contratoStr);
			pdfContrato.openDocument(buffer, 0, buffer.length, "Contrato OS " + rec.getOrdemServico().getId(), null);
		}
		
		if(assinaturaStr != null && ! assinaturaStr.trim().equals("")) { 
			byte buffer[] = OrdemServico.converterStringPlanilha(assinaturaStr);
			pdfAssinatura.openDocument(buffer, 0, buffer.length, "Assinatura OS " + rec.getOrdemServico().getId(), null);
		}
		
		ordemServico = rec.getOrdemServico();
		contrato = rec;
	}

	@Override
	public List<Contrato> listAll() {
		List<Contrato> lista;
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Contrato>();
		}
		
		Funcionario f = Facade.getInstance().getUsuarioLogado().getFuncionario();
		
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_CONTRATOS))
			lista = Facade.getInstance().listarContrato(campo, texto);
		else
			lista = Facade.getInstance().listarContrato(campo, texto,f);

		
		return lista;
	}

	private JTabbedPane getTabDetalhes() {
		tabDetalhes = new JTabbedPane();
		tabDetalhes.addTab("Dados do Evento", getPanelDadosEvento());
		
		
		pdfContrato = new SwingController();
		SwingViewBuilder factory = new SwingViewBuilder(pdfContrato);
		JPanel viewerComponentPanel = factory.buildViewerPanel();
		tabDetalhes.addTab("Contrato", viewerComponentPanel);
		tabDetalhes.addTab("Assinatura", getTabAssinatura());
		return tabDetalhes;
	}
	
	public JPanel getTabAssinatura() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(getBotaoAssinatura(), BorderLayout.NORTH);
		pdfAssinatura = new SwingController();
		SwingViewBuilder factory2 = new SwingViewBuilder(pdfAssinatura);
		JPanel viewerComponentPanel2 = factory2.buildViewerPanel();
		
		p.add(viewerComponentPanel2, BorderLayout.CENTER);
		return p;
		
	}
	
	private JPanel getPanelDadosEvento() {
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		fieldIdContrato = new JTextField();
		fieldTipoContrato = new JTextField();
		fieldIdContrato.setEditable(false);
		fieldTipoContrato.setEditable(false);
		
		fieldObservacoes = new JTextArea();
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldObservacoes.setLineWrap(true);
		
		
		p.add(new TitledPanel("Id",fieldIdContrato),new RestricaoLayout(0, 0, false, false));
		p.add(new TitledPanel("Tipo",fieldTipoContrato),new RestricaoLayout(0, 1,5, true, false));
		p.add(new TitledPanel("Dados da OS", getDadosOS()), new RestricaoLayout(1, 0,6, true, true));
		p.add(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 6,2,true,true));
		
		return p;

	}

	public void createInputFields() {
		this.addInputField(getTabDetalhes(), new RestricaoLayout(0, 0, 1, 1, true, true));
	}
	
	public JPanel getDadosOS() {
		fieldDataInicial = new JTextField();
		fieldDataFinal = new JTextField();

		fieldNomeCliente = new JTextField();
		fieldCNPJCliente = new JTextField();

		fieldNomeEvento = new JTextField();
		fieldIdOS = new JTextField();
		
		fieldNomeEvento = new JTextField();

		fieldNomeEvento.setEditable(false);
		fieldIdOS.setEditable(false);
		fieldDataInicial.setEditable(false);
		fieldDataFinal.setEditable(false);
		fieldNomeCliente.setEditable(false);
		fieldCNPJCliente.setEditable(false);
		
		JPanel painelDadosEvento = new JPanel();
		painelDadosEvento.setLayout(new GridBagLayout());

		painelDadosEvento.add(new TitledPanel("Código", getPanelCodigoOS()), new RestricaoLayout(0, 0, false, false));
		painelDadosEvento.add(new TitledPanel("Nome do Evento", fieldNomeEvento), new RestricaoLayout(0, 1, 2, true, false));
		painelDadosEvento.add(new TitledPanel("Razão Social", fieldNomeCliente), new RestricaoLayout(0, 3, 2, true, false));
		painelDadosEvento.add(new TitledPanel("Início do Evento", fieldDataInicial), new RestricaoLayout(1, 0, 2, true, false));
		painelDadosEvento.add(new TitledPanel("Fim do Evento", fieldDataFinal), new RestricaoLayout(1, 2, 1, true, false));
		painelDadosEvento.add(new TitledPanel("CNPJ", fieldCNPJCliente), new RestricaoLayout(1, 3, 2, true, false));
		return painelDadosEvento;
	}

	@Override
	public Contrato newElement() {
		return new Contrato();
	}
	
	public void limparOS(){
		ordemServico = null;

		botaoLocalizarOS.setEnabled(true);
		fieldNomeEvento.setText("");;
		fieldIdOS.setText("");
		fieldDataInicial.setText("");
		fieldDataFinal.setText("");

		fieldNomeCliente.setText("");
		fieldCNPJCliente.setText("");
		fieldObservacoes.setText("");
		contratoStr = "";
		assinaturaStr = "";
		
		pdfContrato.closeDocument();
		pdfAssinatura.closeDocument();
	}	

	@Override
	protected void clear() {
		limparOS();
		fieldIdContrato.setText("");
		fieldTipoContrato.setText("");
		habilitarBotaoAdicionar();
		contrato = null;
	}
}
