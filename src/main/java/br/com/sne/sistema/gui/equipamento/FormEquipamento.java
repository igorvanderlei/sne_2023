package br.com.sne.sistema.gui.equipamento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormEquipamento extends DefaultForm<Equipamento, EquipamentoTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JComboBox fieldUnidade;
	private JComboBox fieldGrupo;
	private JComboBox fieldRecurso;
	private JComboBox fieldStatus;
	private JTextField fieldNome;
	private JTextField fieldPatrimonio;
	private JTextField fieldDescricao;
	private JTextField fieldSerialEquipamento;
	private JMoedaRealTextField fieldPreco;
	private JTextField fieldCodigoEmpresa;
	private JTextField fieldCodigoGrupo;
	private JTextField fieldCodigoSubgrupo;
	private JTextField fieldCodigoSequencial;
	private JPanel painelFieldCodigo;
	private JTextField fieldMarca;
	private JTextField fieldModelo;
	private JTextField fieldFornecedor;
	private JTextField fieldGarantia;
	private JTextArea fieldObservacoes;
	private JDateChooser fieldData;
	
	private JButton botaoImprimirEtiqueta;
	
	private Equipamento equip;
	String serieAntigo = null;
	
	public FormEquipamento() {
		super(new EquipamentoTableModel(), "/images/icon_equipamento_18.png", "Equipamentos");
		desabilitarBotaoAtualizar();
	}
	

	public boolean print(Equipamento current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();	
		if(equip != null){
		hm.put("id",  equip.getId());
		hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
		try {
			Connection c  = Facade.getInstance().getConnection() ;
			URL arquivo = getClass().getResource("/br/com/sne/sistema/report/equipamento.jasper");  
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
			JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
			JasperViewer.viewReport(impressao,false);
			c.close();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um Equipamento para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	
	public boolean save(Equipamento current) {
		boolean s = false;
		try {
			current.setStatus(StatusEquipamento.DISPONIVEL);
			Facade.getInstance().salvarEquipamento(current);
			equip = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Equipamento já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Equipamento current) {
		boolean s = false;
	try { 
		if(current.getStatus() == StatusEquipamento.DISPONIVEL) {
			Facade.getInstance().atualizarEquipamento(current, serieAntigo); 
			s = true;
		}
		else {
			JOptionPane.showMessageDialog(this, "Só é permitido alterar os dados dos equipamentos em estoque.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}catch (DuplicatedRegisterException err) {  
		JOptionPane.showMessageDialog(this, "O Equipamento já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
	}
		return s;
	}
	
	public boolean remove(Equipamento current) {
		boolean test = false;
		try {
			Facade.getInstance().removerEquipamento(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Equipamento. \nVerifique se existem Ordens de Serviçoo cadastradas com este equipamento. \nCaso realmente deseje excluir este equipamento, utilize a opção Descarte de Equipamento.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldPatrimonio = new JTextField();
		
		fieldStatus = new JComboBox();
		fieldStatus.setEnabled(false);
		carregarFieldStatus();
		fieldNome = new JTextField();
		fieldDescricao = new JTextField();
		fieldPreco = new JMoedaRealTextField();
		fieldMarca = new JTextField();
		fieldModelo = new JTextField();
		fieldFornecedor = new JTextField();
		fieldGarantia = new JTextField();
		fieldSerialEquipamento = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldData = new JDateChooser();
		fieldData.setDate(new Date());
		
		fieldNome.setEditable(false);
		fieldDescricao.setEditable(false);
		fieldPreco.setEditable(false);
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Unidade", getFieldUnidade()), new RestricaoLayout(0, 1, true, false));
		this.addInputField(new TitledPanel("Grupo", getFieldGrupo()), new RestricaoLayout(0, 2,  true, false));
		this.addInputField(new TitledPanel("Subgrupo", getFieldRecurso()), new RestricaoLayout(0, 3, true, false));
		this.addInputField(new TitledPanel("Status", fieldStatus), new RestricaoLayout(0, 4, true, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(1, 0, 3, true, false));
		this.addInputField(new TitledPanel("Descrição", fieldDescricao), new RestricaoLayout(1, 3, 1, true, false));
		this.addInputField(new TitledPanel("Preço", fieldPreco), new RestricaoLayout(1, 4, 1, true, false));
		this.addInputField(new TitledPanel("Código", getPainelFieldCodigo()), new RestricaoLayout(2, 0, 2, true, false));
		this.addInputField(new TitledPanel("Data de Compra", fieldData), new RestricaoLayout(2, 2, 1, true, false));
		this.addInputField(new TitledPanel("Marca", fieldMarca), new RestricaoLayout(2, 3, 1, true, false));
		this.addInputField(new TitledPanel("Modelo", fieldModelo), new RestricaoLayout(2, 4, 1, true, false));
		
		this.addInputField(new TitledPanel("Patrimônio", fieldPatrimonio), new RestricaoLayout(3, 0, false, false));
		this.addInputField(new TitledPanel("Número de Série", fieldSerialEquipamento), new RestricaoLayout(3, 1, 1, true, false));
		
		this.addInputField(new TitledPanel("Fornecedor", fieldFornecedor), new RestricaoLayout(3, 2, 2, true, false));
		this.addInputField(new TitledPanel("Garantia", fieldGarantia), new RestricaoLayout(3, 4,  1, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(4, 0, 5, 1, true, true));
	}
	
	private JPanel getPainelFieldCodigo() {
		if(painelFieldCodigo == null) {
			painelFieldCodigo = new JPanel();
			painelFieldCodigo.setLayout(new BoxLayout(painelFieldCodigo, BoxLayout.LINE_AXIS));
			
			MaskFormatter formatoCodigoDois;
			MaskFormatter formatoCodigoTres;
			try {
				formatoCodigoDois = new MaskFormatter("##");
				formatoCodigoDois.setPlaceholder("00");
				
				formatoCodigoTres = new MaskFormatter("###");
				formatoCodigoTres.setPlaceholder("000");
				
				fieldCodigoEmpresa = new JFormattedTextField(formatoCodigoDois);
				fieldCodigoEmpresa.setToolTipText("Código da Unidade");
				fieldCodigoGrupo = new JFormattedTextField(formatoCodigoTres);
				fieldCodigoGrupo.setToolTipText("Código do Grupo");
				fieldCodigoSubgrupo = new JFormattedTextField(formatoCodigoTres);
				fieldCodigoSubgrupo.setToolTipText("Código do Subgrupo");
			} catch (ParseException e) {
				fieldCodigoEmpresa = new JTextField();
				fieldCodigoGrupo = new JTextField();
				fieldCodigoSubgrupo = new JTextField();
			}

			MaskFormatter formatoCodigoCinco;
			try {
				formatoCodigoCinco = new MaskFormatter("#####");
				formatoCodigoCinco.setPlaceholder("00000");
				fieldCodigoSequencial = new JFormattedTextField(formatoCodigoCinco);
				fieldCodigoSequencial.setToolTipText("Código Sequencial");
			} catch (ParseException e) {
				fieldCodigoSequencial = new JTextField();
			}

			fieldCodigoEmpresa.setBounds(0, 0, 14, 10);
			fieldCodigoEmpresa.setEditable(false);
			fieldCodigoGrupo.setBounds(0, 0, 14, 10);
			fieldCodigoGrupo.setEditable(false);
			fieldCodigoSubgrupo.setBounds(0, 0, 14, 10);
			fieldCodigoSubgrupo.setEditable(false);
			fieldCodigoSequencial.setBounds(0, 0, 14, 15);	
			
			botaoImprimirEtiqueta = new JButton();
			botaoImprimirEtiqueta.setBounds(0,0,22,22);
			botaoImprimirEtiqueta.setIcon(new ImageIcon(getClass().getResource("/images/icon_etiqueta_24.png")));
			
			botaoImprimirEtiqueta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(equip != null) {
						HashMap<String, Object> hm = new HashMap<String, Object>();
						hm.put("id", equip.getId());
						ImageIcon assinatura =  new ImageIcon(getClass().getResource("/images/icon_sne_96_iv.png"));
						hm.put("logo", assinatura.getImage());
						
						
						try {
							java.sql.Connection c  = Facade.getInstance().getConnection();
							URL arquivo = getClass().getResource("/br/com/sne/sistema/report/etiquetaEquipamentoPorCodigo.jasper");  
							JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
							JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, c);
							JasperViewer.viewReport(print,false);
							
							
						
							/*
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
							//desktop.print(f);
							
						    FileInputStream fis = new FileInputStream(f);

						    Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
						    PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
						    for(PrintService p : ps)
						    	System.out.println(p.getName());

						 //   DocPrintJob printJob = ps[0].createPrintJob();

						    //printJob.print(pdfDoc, new HashPrintRequestAttributeSet());

						    fis.close();*/
						} catch (Exception e1) {
						e1.printStackTrace();
						}
						
					} else {
						JOptionPane.showMessageDialog(FormEquipamento.this, "Selecione um Equipamento para poder imprimir sua etiqueta", "ERRO", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			});
			

			painelFieldCodigo.add(fieldCodigoEmpresa);
			painelFieldCodigo.add(fieldCodigoGrupo);
			painelFieldCodigo.add(fieldCodigoSubgrupo);
			painelFieldCodigo.add(fieldCodigoSequencial);
			painelFieldCodigo.add(botaoImprimirEtiqueta);
		}
		return painelFieldCodigo;
	}
	
	private JComboBox getFieldUnidade() {
		if (fieldUnidade == null) {
			fieldUnidade = new JComboBox();
			carregarComboUnidade();
			fieldUnidade.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object obj = fieldUnidade.getSelectedItem();
						if(obj instanceof Unidade) {
							fieldCodigoEmpresa.setText(((Unidade) obj).getCodigo());
						} 
					}
				}	
			);
			
		}
		return fieldUnidade;
	}
	
	private JComboBox getFieldGrupo() {
		if (fieldGrupo == null) {
			fieldGrupo = new JComboBox();
			fieldGrupo.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object obj = fieldGrupo.getSelectedItem();
						if(obj instanceof Grupo) {
							carregarFieldSubgrupo((Grupo) obj);
							fieldCodigoGrupo.setText(((Grupo) obj).getCodigo());
						}
					}
				}	
			);
			
		}
		return fieldGrupo;
	}
	
	private JComboBox getFieldRecurso() {
		if (fieldRecurso == null) {
			fieldRecurso = new JComboBox();
			fieldRecurso.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Object obj = fieldRecurso.getSelectedItem();
							if(obj instanceof Recurso) {
								Recurso subgrupo = (Recurso) obj;
								fieldDescricao.setText(subgrupo.getDescricao());
								fieldNome.setText(subgrupo.getNome());
								fieldPreco.setValor(subgrupo.getPrecoSugerido());
								fieldCodigoSubgrupo.setText(subgrupo.getCodigo());
							}else {
								fieldDescricao.setText("");
								fieldNome.setText("");
								fieldPreco.setText("");
							}
						}
					}
			);
		}
		return fieldRecurso;
	}	
	
	private void carregarFieldSubgrupo(Grupo g) {
		List<Recurso> lista = Facade.getInstance().listarRecurso(g);
		fieldRecurso.removeAllItems();
		fieldRecurso.addItem("");
		for(Recurso rec: lista)
			fieldRecurso.addItem(rec);
	}
	
	private void carregarFieldStatus() {
		fieldStatus.removeAllItems();
		fieldStatus.addItem("");
		for(StatusEquipamento st : StatusEquipamento.values())
			fieldStatus.addItem(st);
		fieldStatus.setSelectedItem(StatusEquipamento.DISPONIVEL);
	}

	public void setVisible(boolean aFlag) {
		if (fieldGrupo != null) {
			carregarComboGrupo();
			verificarPermissao();
			super.setVisible(aFlag);
		}
		
	}
	
	private void verificarPermissao() {
		TipoUsuario tipoUsuarioLogado = Facade.getInstance().getUsuarioLogado().getTipoUsuario();
		if(tipoUsuarioLogado.getPermissao().contains(permission.ALTERAR_CADASTRO_EQUIPAMENTO)) {
			habilitarBotaoAtualizar();
		} else {
			desabilitarBotaoAtualizar();
		}
	}

	private void carregarComboUnidade() {
		fieldUnidade.removeAllItems();
		fieldUnidade.addItem("");
		List<Unidade> lista = Facade.getInstance().listarUnidade();
		for(Unidade uni: lista) {
			fieldUnidade.addItem(uni);
		}
	}
	
	private void carregarComboGrupo() {
		fieldGrupo.removeAllItems();
		fieldGrupo.addItem("");
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		for(Grupo grp: lista) {
			fieldGrupo.addItem(grp);
		}
	}
	
	public Equipamento newElement() {
		return new Equipamento();
	}

	public void loadInputFields(Equipamento equipamento) {
		Unidade unidade = (Unidade) fieldUnidade.getSelectedItem();
		Grupo grupo = (Grupo) fieldGrupo.getSelectedItem();
		DescricaoEquipamento r = (DescricaoEquipamento) fieldRecurso.getSelectedItem();
		StatusEquipamento status = (StatusEquipamento) fieldStatus.getSelectedItem();
		String codigo =	fieldCodigoEmpresa.getText() + "." +
						fieldCodigoGrupo.getText() + "." +
						fieldCodigoSubgrupo.getText() + "." +
						fieldCodigoSequencial.getText();
		String marca = fieldMarca.getText();
		String modelo = fieldModelo.getText();
		String fornecedor = fieldFornecedor.getText();
		String garantia = fieldGarantia.getText();
		String observacoes = fieldObservacoes.getText();
		equipamento.setUnidade(unidade);
		equipamento.setGrupo(grupo);
		equipamento.setDescricaoEquipamento(r);
		equipamento.setStatus(status);
		equipamento.setNumeroSerie(codigo);
		equipamento.setSerialEquipamento(fieldSerialEquipamento.getText());
		equipamento.setMarca(marca);
		equipamento.setModelo(modelo);
		equipamento.setLojaFornecedora(fornecedor);
		equipamento.setGarantia(garantia);
		equipamento.setObservacoes(observacoes);
		equipamento.setData(fieldData.getDate());
		equipamento.setPatrimonio(fieldPatrimonio.getText());
		
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldUnidade.setSelectedIndex(0);
		fieldGrupo.setSelectedIndex(0);
		fieldRecurso.setSelectedIndex(0);
		fieldStatus.setSelectedIndex(1);
		fieldSerialEquipamento.setText("");
		fieldNome.setText("");
		fieldDescricao.setText("");
		fieldPreco.clear();
		fieldCodigoEmpresa.setText("00");
		fieldCodigoGrupo.setText("000");
		fieldCodigoSubgrupo.setText("000");
		fieldCodigoSequencial.setText("00000");
		fieldData.setDate(new Date());
		fieldMarca.setText("");
		fieldModelo.setText("");
		fieldFornecedor.setText("");
		fieldGarantia.setText("");
		fieldObservacoes.setText("");
		fieldPatrimonio.setText("");
		equip = null;
	}

	protected void loadForm(Equipamento rec) {
		fieldID.setText("" + rec.getId());
		fieldUnidade.setSelectedItem(rec.getUnidade());
		fieldGrupo.setSelectedItem(rec.getGrupo());
		fieldRecurso.setSelectedItem(rec.getDescricaoEquipamento());
		fieldStatus.setSelectedItem(rec.getStatus());
		fieldNome.setText(rec.getDescricaoEquipamento().getNome());
		fieldDescricao.setText(rec.getDescricaoEquipamento().getDescricao());
		fieldPreco.setValor(rec.getDescricaoEquipamento().getPrecoSugerido());
		String codigo = rec.getNumeroSerie();
		serieAntigo = codigo; //eliomar
		if(codigo != null && codigo.length() == 16) {
			fieldCodigoEmpresa.setText(codigo.substring(0,2));
			fieldCodigoGrupo.setText(codigo.substring(3,6));
			fieldCodigoSubgrupo.setText(codigo.substring(7,10));
			fieldCodigoSequencial.setText(codigo.substring(11,16));
		}
		
		fieldSerialEquipamento.setText(rec.getSerialEquipamento());
		fieldMarca.setText(rec.getMarca());
		fieldModelo.setText(rec.getModelo());
		fieldFornecedor.setText(rec.getLojaFornecedora());
		fieldGarantia.setText(rec.getGarantia());
		fieldObservacoes.setText(rec.getObservacoes());
		fieldData.setDate(rec.getData());
		
		fieldPatrimonio.setText(rec.getPatrimonio());
		
		equip = rec;
	}

	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		
	/*	if(fieldPatrimonio.getText().length() < 3) {
			test = false;
			error += "\nDigite pelo menos 3 caracteres para o patrimônio";
		}*/
		
		if(fieldUnidade.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione a Unidade do equipamento";
		}
		
		if(fieldGrupo.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o Grupo do equipamento";
		}
		if(fieldRecurso.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o Subgrupo do equipamento";
		}
		
		if(!(fieldRecurso.getSelectedItem() instanceof DescricaoEquipamento)) {
			test = false;
			error += "\nSelecione um Subgrupo do tipo Físico";
		}
		if(fieldStatus.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o Status do equipamento";
		}
		
		if(Integer.parseInt(fieldCodigoEmpresa.getText()) == 0 || 
		   Integer.parseInt(fieldCodigoSequencial.getText()) == 0 ) {
			test = false;
			error += "\nPreencha corretamente o código do equipamento";
			
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Equipamento> listAll() {
		return Facade.getInstance().listarEquipamento();
	}

}
