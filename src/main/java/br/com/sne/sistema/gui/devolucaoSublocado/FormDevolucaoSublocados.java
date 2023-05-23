package br.com.sne.sistema.gui.devolucaoSublocado;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


import br.com.sne.sistema.bean.EquipamentoSublocado;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.DevolucaoSublocados;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.RegistroSublocacao;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.equipamentosublocado.EquipamentoSublocadoTableModel;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormDevolucaoSublocados extends DefaultForm<DevolucaoSublocados, DevolucaoSoblocadosTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JBarCodeInputField fieldCodigo;
	private JComboBox fieldFornecedor;
	private EquipamentoSublocadoTableModel modelo;
	private JTable tabela;
	
	
	private DevolucaoSublocados devolucaoSublocados;
	private Equipamento equipamento;
	
	public FormDevolucaoSublocados() {
		super(new DevolucaoSoblocadosTableModel(), "/images/icon_dsublocado_18.png", "Devolução de Sublocados");
		//desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
		desabilitarBotaoAtualizar();
	}
	
	public void setVisible(boolean aFlag) {
		if (fieldFornecedor != null) {
			carregarComboFornecedor();
		}
		super.setVisible(aFlag);		
	}
	
	public boolean save(DevolucaoSublocados current) {
		boolean s = false;
		if(JOptionPane.showConfirmDialog(null, "Tem certeza que deseja salvar a devolução de sublocados?", "Confirmação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
			try {
				Facade.getInstance().salvarDevolucaoSublocados(current);
				
				for(EquipamentoSublocado eqs: current.getSublocados()) {
					eqs.setStatus(StatusEquipamento.INATIVO);
					eqs.setDevolvido(true);
					Facade.getInstance().atualizarEquipamentoSublocado(eqs);
				}

				List<RegistroSublocacao> sublocacoes = Facade.getInstance().listarRegistroSublocacaosPendentesPorFornecedor(current.getFornecedor());
				for(RegistroSublocacao reg: sublocacoes) {
					boolean finalizado = true;
					for(EquipamentoSublocado eeqs: reg.getEquipamentos()) {
						if(!eeqs.isDevolvido()) {
							finalizado = false;
							break;
						}
					}
					if(finalizado){
						reg.setFinalizada(true);
						Facade.getInstance().atualizarRegistroSublocacao(reg);
					}
				}
				// TODO Falta dar baixa nos registros de sublocação
				
				s = true;
			} catch(RuntimeException err) {
				JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}
		return s;
	}
	
	private void carregarComboFornecedor() {
		fieldFornecedor.removeAllItems();
		for(Fornecedor f: Facade.getInstance().listarFornecedores()) {
			fieldFornecedor.addItem(f);
		}
	}

	public boolean update(DevolucaoSublocados current) {
		boolean s = true;
		Facade.getInstance().atualizarDevolucaoSublocados(current);
		return s;
	}
	
	public boolean remove(DevolucaoSublocados current) {
		boolean test = false;
		try {
			Facade.getInstance().removerDevolucaoSublocados(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o DevolucaoSublocados. Verifique se existem Recursos cadastrados neste de DevolucaoSublocados antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldID.setEnabled(false);
		
		fieldFornecedor = new JComboBox();
		modelo = new EquipamentoSublocadoTableModel();
		tabela = new JTable(modelo);
		JScrollPane scp = new JScrollPane();
		scp.setViewportView(tabela);
		fieldCodigo = new JBarCodeInputField();
		
		fieldCodigo.setListener(new BarCodeListener() {
			public void barCodeEntered(String code) {
				// TODO Arrumar essa bagaçaa
				Equipamento eq = Facade.getInstance().localizarEquipamentoCodigo(code);
				if(eq instanceof EquipamentoSublocado) {
					EquipamentoSublocado eqs = (EquipamentoSublocado) eq;
					if(eq != null && eq.getStatus()==StatusEquipamento.DISPONIVEL) {
						if(fieldFornecedor.getSelectedItem() instanceof Fornecedor) {
							Fornecedor f = (Fornecedor) fieldFornecedor.getSelectedItem();
							if(eqs.getFornecedor().getId() == f.getId()) {
								modelo.addElement(eqs);
							} else {
								JOptionPane.showMessageDialog(FormDevolucaoSublocados.this, "O equipamento informado pertence a um outro fornecedor", "ERRO", JOptionPane.ERROR_MESSAGE);	
							}
						} else {
							JOptionPane.showMessageDialog(FormDevolucaoSublocados.this, "Selecione o Fornecedor", "ERRO", JOptionPane.ERROR_MESSAGE);
						}
					} else{
							JOptionPane.showMessageDialog(FormDevolucaoSublocados.this, "Só é possível devolver os equipamentos disponíveis no estoque", "ERRO", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(FormDevolucaoSublocados.this, "O código informado não corresponde a um equipamento sublocado", "ERRO", JOptionPane.ERROR_MESSAGE);
				}
				//	carregarEquipamento(eq);
			}
		});
		
		fieldID.setEditable(false);
		
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Fornecedor", fieldFornecedor), new RestricaoLayout(0, 1, 1, true, false));
		this.addInputField(new TitledPanel("Código do Equipamento", fieldCodigo), new RestricaoLayout(0, 2, 1, true, false));
		this.addInputField(new TitledPanel("Equipamentos", scp), new RestricaoLayout(2, 0, 3 , 1, true, true));
	}
	
	public DevolucaoSublocados newElement() {
		return new DevolucaoSublocados();
	}

	public void loadInputFields(DevolucaoSublocados rec) {
		rec.setFornecedor((Fornecedor) fieldFornecedor.getSelectedItem());
		rec.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		rec.setData(new Date());
		rec.setSublocados(modelo.getEquipamentos());
	}
	
	protected void clear() {
		fieldCodigo.setEnabled(true);
		fieldID.setText("");
		fieldCodigo.setText("");
		fieldFornecedor.setSelectedItem(null);
		devolucaoSublocados = null;
		modelo.removeAllElements();
	}

	protected void loadForm(DevolucaoSublocados rec) {
		fieldID.setText("" + rec.getId());
		fieldFornecedor.setSelectedItem(rec.getFornecedor());
		devolucaoSublocados = rec;
		fieldCodigo.setEnabled(false);
		modelo.removeAllElements();
		for(EquipamentoSublocado eqs : rec.getSublocados())
			modelo.addElement(eqs);
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(modelo.getEquipamentos().size() == 0) {
			test = false;
			error += "\nInforme os equipamentos a serem devolvidos.";

		}
		if(! (fieldFornecedor.getSelectedItem() instanceof Fornecedor )) {
			test = false;
			error += "\nSelecione o técnico responsável";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<DevolucaoSublocados> listAll() {
		return Facade.getInstance().listarDevolucaoSublocadoss();
	}

	public boolean print(DevolucaoSublocados current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(devolucaoSublocados != null){
		hm.put("id",  devolucaoSublocados.getId());
		hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
		try {
			Connection c  = Facade.getInstance().getConnection() ;
			URL arquivo = getClass().getResource("/br/com/sne/sistema/report/devolucaoSublocados.jasper");  
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
