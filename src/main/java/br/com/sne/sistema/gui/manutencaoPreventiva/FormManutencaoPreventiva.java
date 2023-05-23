package br.com.sne.sistema.gui.manutencaoPreventiva;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.ManutencaoPreventiva;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormManutencaoPreventiva extends DefaultForm<ManutencaoPreventiva, ManutencaoPreventivaTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JBarCodeInputField fieldCodigo;
	private JTextArea fieldDescricaoProblema;
	private JTextArea fieldParecerTecnico;
	private JTextField fieldRecurso;
	private JTextField fieldMarca;
	private JTextField fieldModelo;
	private JComboBox fieldTecnicoResponsavel;
	
	private ManutencaoPreventiva ManutencaoPreventiva;
	private Equipamento equipamento;
	
	public FormManutencaoPreventiva() {
		super(new ManutencaoPreventivaTableModel(), "/images/icon_mpreventiva_18.png", "Manutenção Preventiva");
		//desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
	}
	
	public void setVisible(boolean aFlag) {
		if (fieldTecnicoResponsavel != null) {
			carregarComboTecnico();
		}
		super.setVisible(aFlag);		
	}
	
	public boolean save(ManutencaoPreventiva current) {
		boolean s = false;
		if(JOptionPane.showConfirmDialog(null, "Tem certeza que deseja registrar a manutenção preventiva do equipamento selecionado ?", "Confirmação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
			if(current.getEquipamento().getStatus() != StatusEquipamento.DISPONIVEL) {
				JOptionPane.showMessageDialog(this, "Só é permitido registar manutenção para equipamentos disponíveis na empresa", "ERRO", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			try {
				current.getEquipamento().setStatus(StatusEquipamento.MANUTENCAO_PREVENTIVA);
				Facade.getInstance().atualizarEquipamentoDevolucao(current.getEquipamento());
				Facade.getInstance().salvarManutencaoPreventiva(current);
				ManutencaoPreventiva = current;
				s = true;
			} catch(RuntimeException err) {
				JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}
		return s;
	}
	
	private void carregarComboTecnico() {
		fieldTecnicoResponsavel.removeAllItems();
		for(Funcionario f: Facade.getInstance().listarFuncionarios()) {
			fieldTecnicoResponsavel.addItem(f);
		}
	}

	public boolean update(ManutencaoPreventiva current) {
		boolean s = true;
		Facade.getInstance().atualizarManutencaoPreventiva(current);
		return s;
	}
	
	public boolean remove(ManutencaoPreventiva current) {
		boolean test = false;
		try {
			Facade.getInstance().removerManutencaoPreventiva(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o registro de  Manutençã Preventiva. Verifique se existem Recursos cadastrados neste registro antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
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
		
		fieldTecnicoResponsavel = new JComboBox();
		fieldDescricaoProblema = new JTextArea();
		fieldParecerTecnico = new JTextArea();

		fieldCodigo = new JBarCodeInputField();
		
		fieldCodigo.setListener(new BarCodeListener() {
			public void barCodeEntered(String code) {
				Equipamento eq = Facade.getInstance().localizarEquipamentoCodigo(code);
				if(eq != null && eq.getStatus()==StatusEquipamento.DISPONIVEL)
					carregarEquipamento(eq);
			}
		});
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldDescricaoProblema);
		
		JScrollPane scrollParecer = new JScrollPane();
		scrollParecer.setViewportView(fieldParecerTecnico);
		
		JPanel pn = new JPanel();
		pn.setLayout(new GridBagLayout());
		pn.add(new TitledPanel("Descrição do Problema", scrollObservacoes), new RestricaoLayout(0, 0, 1 , 1, true, true));
		pn.add(new TitledPanel("Parecer Técnico", scrollParecer), new RestricaoLayout(0, 1, 1 , 1, true, true));
		
		
		fieldID.setEditable(false);
		
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Código", fieldCodigo), new RestricaoLayout(0, 1, 1, true, false));
		this.addInputField(new TitledPanel("Recurso", fieldRecurso), new RestricaoLayout(0, 2, 1, true, false));
		this.addInputField(new TitledPanel("Marca", fieldMarca), new RestricaoLayout(0, 3, 1, true, false));
		this.addInputField(new TitledPanel("Modelo", fieldModelo), new RestricaoLayout(0, 4, 1, true, false));
		this.addInputField(new TitledPanel("Técnico Responsável", fieldTecnicoResponsavel), new RestricaoLayout(1,0, 5, true, false));
		this.addInputField(pn, new RestricaoLayout(2, 0, 5 , 1, true, true));
	}
	
	private void carregarEquipamento(Equipamento eq) {
		equipamento = eq;
		fieldCodigo.setText(eq.getNumeroSerie());
		fieldRecurso.setText(eq.getDescricaoEquipamento().getNome());
		fieldMarca.setText(eq.getMarca());
		fieldModelo.setText(eq.getModelo());
	}
	
	
	public ManutencaoPreventiva newElement() {
		return new ManutencaoPreventiva();
	}

	public void loadInputFields(ManutencaoPreventiva rec) {
		rec.setDescricaoProblema(fieldDescricaoProblema.getText());
		rec.setParecerTecnico(fieldParecerTecnico.getText());
		rec.setTecnicoResponsavel((Funcionario) fieldTecnicoResponsavel.getSelectedItem());
		rec.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		rec.setEquipamento(equipamento);
		rec.setDataManutencao(new Date());
	}
	
	protected void clear() {
		fieldCodigo.setEnabled(true);
		habilitarBotaoAdicionar();
		fieldID.setText("");
		fieldCodigo.setText("");
		fieldDescricaoProblema.setText("");
		fieldParecerTecnico.setText("");
		fieldTecnicoResponsavel.setSelectedItem(null);
		fieldMarca.setText("");
		fieldRecurso.setText("");
		fieldModelo.setText("");
		equipamento = null;
		ManutencaoPreventiva = null;
	}

	protected void loadForm(ManutencaoPreventiva rec) {
		fieldID.setText("" + rec.getId());
		fieldDescricaoProblema.setText(rec.getDescricaoProblema());
		fieldParecerTecnico.setText(rec.getParecerTecnico());
		fieldTecnicoResponsavel.setSelectedItem(rec.getTecnicoResponsavel());
		ManutencaoPreventiva = rec;
		fieldCodigo.setEnabled(false);
		desabilitarBotaoAdicionar();
		carregarEquipamento(rec.getEquipamento());
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(equipamento == null) {
			test = false;
			error += "\nSelecione um equipamento";

		}
		if(! (fieldTecnicoResponsavel.getSelectedItem() instanceof Funcionario )) {
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

	public List<ManutencaoPreventiva> listAll() {
		return Facade.getInstance().listarManutencaoPreventivas();
	}

	public boolean print(ManutencaoPreventiva current) {
		if(ManutencaoPreventiva != null) {
			// TODO
		}
		return false;
	}
}
