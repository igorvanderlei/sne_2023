package br.com.sne.sistema.gui.descarteequipamento;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


import br.com.sne.sistema.bean.DescarteEquipamento;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import br.com.sne.sistema.gui.util.form.DialogSearchEquipamento;

public class FormDescarteEquipamento extends DefaultForm<DescarteEquipamento, DescarteEquipamentoTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JComboBox fieldMotivo;
	private JTextField fieldCodigo;
	private JTextArea fieldObservacoes;
	
	private JTextField fieldRecurso;
	private JTextField fieldMarca;
	private JTextField fieldModelo;
	
	private DescarteEquipamento descarteEquipamento;
	private Equipamento equipamento;
	
	public FormDescarteEquipamento() {
		super(new DescarteEquipamentoTableModel(), "/images/icon_descarte_18.png", "Descarte de Equipamentos");
		desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
	}
	
	public boolean save(DescarteEquipamento current) {
		boolean s = false;
		if(JOptionPane.showConfirmDialog(null, "Tem certeza que deseja regitrar o descarte do equipamento selecionado ?", "Confirmação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
			try {
				if(current.getEquipamento().getStatus() == StatusEquipamento.LOCADO) {
					EquipamentoEnviado eq = Facade.getInstance().localizarEquipamentoEnviado(current.getEquipamento().getNumeroSerie());

					if(eq != null) {

						eq.setDataDevolucao(new Date());
						eq.setStatus(true);

						Facade.getInstance().atualizarEquipamentoEnviado(eq);
						OrdemServico os = Facade.getInstance().localizaOrdemServicosPorEquipamento(eq.getId());

						boolean finalizada = true;
						for(EquipamentoEnviado e: os.getEquipamentoEnviado()) {
							if(!e.isStatus()) {
								finalizada = false;
								break;
							}
						}
						if(finalizada){
							os.setStatus(StatusOS.CONCLUIDA);
							Facade.getInstance().atualizarOrdemServico(os);
						}
					}
				}

				current.getEquipamento().setStatus(StatusEquipamento.INATIVO);
				Facade.getInstance().atualizarEquipamentoDevolucao(current.getEquipamento());
				Facade.getInstance().salvarDescarteEquipamento(current);
				descarteEquipamento = current;
				s = true;

			} catch(RuntimeException err) {
				JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}
		return s;
	}

	public boolean update(DescarteEquipamento current) {
		boolean s = true;
		Facade.getInstance().atualizarDescarteEquipamento(current);
		return s;
	}
	
	public boolean remove(DescarteEquipamento current) {
		boolean test = false;
		try {
			Facade.getInstance().removerDescarteEquipamento(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o DescarteEquipamento. Verifique se existem Recursos cadastrados neste de DescarteEquipamento antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldID.setEnabled(false);
		
		fieldRecurso = new JTextField();
		fieldMarca = new JTextField();
		fieldModelo = new JTextField();
		
		fieldRecurso.setEnabled(false);
		fieldMarca.setEnabled(false);
		fieldModelo.setEnabled(false);
		
		fieldMotivo = new JComboBox(new String[]{"Selecione o motivo", "Roubo", "Desgaste Natural", "Quebra em Evento"});
		fieldObservacoes = new JTextArea();

		fieldCodigo = new JTextField();
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Código", getPanelCodigoEquipamento()), new RestricaoLayout(0, 1, false, false));
		this.addInputField(new TitledPanel("Recurso", fieldRecurso), new RestricaoLayout(0, 2, 1, true, false));
		this.addInputField(new TitledPanel("Marca", fieldMarca), new RestricaoLayout(0, 3, 1, true, false));
		this.addInputField(new TitledPanel("Modelo", fieldModelo), new RestricaoLayout(0, 4, 1, true, false));
		this.addInputField(new TitledPanel("Motivo do Descarte", fieldMotivo), new RestricaoLayout(1,0, 5, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 5 , 1, true, true));
	}
	
	public JPanel getPanelCodigoEquipamento() {
		JPanel panelCodigoEquipamento = new JPanel();
		//panelCodigoEquipamento.setLayout(new GridBagLayout());
		panelCodigoEquipamento.setLayout(new BoxLayout(panelCodigoEquipamento, BoxLayout.LINE_AXIS));
		JButton botaoCliente = getBotaoProcurarEquipamento();
		fieldCodigo = new JIntField();
		fieldCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						String codigoEquipamento = fieldCodigo.getText();
						Equipamento eq = Facade.getInstance().localizarEquipamentoCodigo(codigoEquipamento);
						if(eq != null)
							carregarEquipamento(eq);
					} catch (Exception e){
						java.awt.Toolkit.getDefaultToolkit().beep();  
					}
				}
			}
		});

		//botaoCliente.setPreferredSize(new Dimension(18,18));

		panelCodigoEquipamento.add(fieldCodigo);//, new RestricaoLayout(0,0,1,true,false));
		panelCodigoEquipamento.add(botaoCliente);//, new RestricaoLayout(0,1,false,false));
		return panelCodigoEquipamento;
	}
	
	public JButton getBotaoProcurarEquipamento() {
		JButton botaoProcurarEquipamento = new JButton("", new ImageIcon(getClass().getResource("/images/find.png")));
		botaoProcurarEquipamento.setHorizontalAlignment(SwingConstants.CENTER);
		botaoProcurarEquipamento.setVerticalAlignment(SwingConstants.CENTER);
		botaoProcurarEquipamento.setPreferredSize(new Dimension(30,18));

		botaoProcurarEquipamento.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
							List<Equipamento> listaEquipamento = new ArrayList<Equipamento>();
							for(Equipamento eq: Facade.getInstance().listarEquipamento()) {
								if(eq.getStatus() != StatusEquipamento.INATIVO){
									listaEquipamento.add(eq);
								}
							}
							DialogSearchEquipamento teste = new DialogSearchEquipamento(FormDescarteEquipamento.this, listaEquipamento);
							Equipamento c = teste.showDialog(FormDescarteEquipamento.this);
							if(c != null)
								carregarEquipamento(c);
					}
				}	
		);
		return botaoProcurarEquipamento;
	}
	
	
	private void carregarEquipamento(Equipamento eq) {
		equipamento = eq;
		fieldCodigo.setText(eq.getNumeroSerie());
		fieldRecurso.setText(eq.getDescricaoEquipamento().getNome());
		fieldMarca.setText(eq.getMarca());
		fieldModelo.setText(eq.getModelo());
	}
	
	
	public DescarteEquipamento newElement() {
		return new DescarteEquipamento();
	}

	public void loadInputFields(DescarteEquipamento descarteEquipamento) {
		String motivo = (String)fieldMotivo.getSelectedItem();
		String observacoes = fieldObservacoes.getText();
		descarteEquipamento.setObservacoes(observacoes);
		descarteEquipamento.setMotivo(motivo);
		descarteEquipamento.setData(new Date());
		descarteEquipamento.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		descarteEquipamento.setEquipamento(equipamento);
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldMotivo.setSelectedIndex(0);
		fieldCodigo.setText("");
		fieldObservacoes.setText("");
		fieldMarca.setText("");
		fieldRecurso.setText("");
		fieldModelo.setText("");
		equipamento = null;
		descarteEquipamento = null;
	}

	protected void loadForm(DescarteEquipamento rec) {
		fieldID.setText("" + rec.getId());
		fieldMotivo.setSelectedItem(rec.getMotivo());
		fieldObservacoes.setText(rec.getObservacoes());	
		descarteEquipamento = rec;
		carregarEquipamento(rec.getEquipamento());
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(equipamento == null) {
			test = false;
			error += "\nSelecione um equipamento";

		}
		if(fieldMotivo.getSelectedIndex() <= 0) {
			test = false;
			error += "\nSelecione o motivo do descarte";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<DescarteEquipamento> listAll() {
		return Facade.getInstance().listarDescarteEquipamentos();
	}

	public boolean print(DescarteEquipamento current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(descarteEquipamento != null){
		hm.put("id",  descarteEquipamento.getId());
		hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
		try {
			Connection c  = Facade.getInstance().getConnection() ;
			URL arquivo = getClass().getResource("/br/com/sne/sistema/report/descarteEquipamento.jasper");  
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
		return true;
	}
}
