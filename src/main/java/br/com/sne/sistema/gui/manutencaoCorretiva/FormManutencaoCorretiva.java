package br.com.sne.sistema.gui.manutencaoCorretiva;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;


import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.ManutencaoCorretiva;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.BarCodeListener;
import br.com.sne.sistema.gui.util.components.JBarCodeInputField;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormManutencaoCorretiva extends DefaultForm<ManutencaoCorretiva, ManutencaoCorretivaTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JBarCodeInputField fieldCodigo;
	private JTextArea fieldDescricaoProblema;
	private JTextArea fieldParecerTecnico;
	private JTextField fieldRecurso;
	private JTextField fieldMarca;
	private JTextField fieldModelo;
	private JTextField fieldNomeAssistencia;
	private JTextField fieldFoneAssistencia;
	private JDateChooser fieldPrevisaoDevolucao;
	
	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;
	
	private ManutencaoCorretiva ManutencaoCorretiva;
	private Equipamento equipamento;
	
	public FormManutencaoCorretiva() {
		super(new ManutencaoCorretivaTableModel(), "/images/icon_mcorretiva_18.png", "Manutenção Corretiva");
		//desabilitarBotaoAtualizar();
		desabilitarBotaoRemover();
	}
	
	public boolean save(ManutencaoCorretiva current) {
		boolean s = false;
		if(JOptionPane.showConfirmDialog(null, "Tem certeza que deseja registrar a manuteção do equipamento selecionado ?", "Confirmação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
			if(current.getEquipamento().getStatus() != StatusEquipamento.DISPONIVEL) {
				JOptionPane.showMessageDialog(this, "Só é permitido registar manutenção para equipamentos disponíveis na empresa", "ERRO", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			try {
				current.getEquipamento().setStatus(StatusEquipamento.MANUTENCAO_CORRETIVA);
				Facade.getInstance().atualizarEquipamentoDevolucao(current.getEquipamento());
				Facade.getInstance().salvarManutencaoCorretiva(current);
				ManutencaoCorretiva = current;
				s = true;
			} catch(RuntimeException err) {
				JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}
		return s;
	}
	
	public boolean update(ManutencaoCorretiva current) {
		boolean s = true;
		Facade.getInstance().atualizarManutencaoCorretiva(current);
		return s;
	}
	
	public boolean remove(ManutencaoCorretiva current) {
		boolean test = false;
		try {
			Facade.getInstance().removerManutencaoCorretiva(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Manutenção Corretiva. Verifique se existem Recursos cadastrados neste regitro antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
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
		
		fieldNomeAssistencia = new JTextField();
		fieldFoneAssistencia = new JTextField();
		fieldDescricaoProblema = new JTextArea();
		fieldParecerTecnico = new JTextArea();
		fieldPrevisaoDevolucao = new JDateChooser();

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
		this.addInputField(new TitledPanel("Assistência Técnica", fieldNomeAssistencia), new RestricaoLayout(1,0, 3, true, false));
		this.addInputField(new TitledPanel("Telefone", fieldFoneAssistencia), new RestricaoLayout(1,3, 1, true, false));
		this.addInputField(new TitledPanel("Previsão de Devolução", fieldPrevisaoDevolucao), new RestricaoLayout(1,4, 1, true, false));
		this.addInputField(getPanelEndereco(), new RestricaoLayout(2, 0, 5, true, false));
		this.addInputField(pn, new RestricaoLayout(3, 0, 5 , 1, true, true));
	}
	
	private void carregarEquipamento(Equipamento eq) {
		equipamento = eq;
		fieldCodigo.setText(eq.getNumeroSerie());
		fieldRecurso.setText(eq.getDescricaoEquipamento().getNome());
		fieldMarca.setText(eq.getMarca());
		fieldModelo.setText(eq.getModelo());
	}
	
	
	private JPanel getPanelEndereco () {
		JPanel endereco = new JFormGroup("Endereço");
		endereco.setLayout(new GridBagLayout());

		fieldEnderecoLogradouro = new JTextField();
		fieldEnderecoNumero = new JTextField();
		fieldEnderecoComplemento = new JTextField();
		fieldEnderecoCEP = new JCepField();
		fieldEnderecoBairro = new JTextField();
		fieldEnderecoCidade = new JTextField();
		fieldEnderecoEstado = new JComboEstado();
		fieldEnderecoReferencia = new JTextField();

		endereco.add(new TitledPanel("Logradouro", fieldEnderecoLogradouro), new RestricaoLayout(0,0, 2, true, false));
		endereco.add(new TitledPanel("Numero", fieldEnderecoNumero), new RestricaoLayout(0,2, true, false));
		endereco.add(new TitledPanel("Complemento", fieldEnderecoComplemento), new RestricaoLayout(0,3, 1, true, false));
		endereco.add(new TitledPanel("CEP", fieldEnderecoCEP), new RestricaoLayout(0,4, 1, true, false));
		endereco.add(new TitledPanel("Bairro", fieldEnderecoBairro), new RestricaoLayout(1,0, 1, true, false));
		endereco.add(new TitledPanel("Cidade", fieldEnderecoCidade), new RestricaoLayout(1,1, 1, true, false));
		endereco.add(new TitledPanel("Estado", fieldEnderecoEstado), new RestricaoLayout(1,2, 1, true, false));
		endereco.add(new TitledPanel("Ponto de Referência", fieldEnderecoReferencia), new RestricaoLayout(1,3, 2, true, false));

		return endereco;
	}
	
	public ManutencaoCorretiva newElement() {
		return new ManutencaoCorretiva();
	}

	public void loadInputFields(ManutencaoCorretiva rec) {
		rec.setDescricaoProblema(fieldDescricaoProblema.getText());
		rec.setParecerTecnico(fieldParecerTecnico.getText());
		rec.setNomeAssistencia(fieldNomeAssistencia.getText());
		rec.setTelefoneAssistencia(fieldFoneAssistencia.getText());
		rec.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
		rec.setEquipamento(equipamento);
		rec.setDataManutencao(new Date());
		rec.setPrevisaoDevolucao(fieldPrevisaoDevolucao.getDate());
		
		String logradouro = fieldEnderecoLogradouro.getText();
		String numero = fieldEnderecoNumero.getText();
		String complemento = fieldEnderecoComplemento.getText();
		String cep = fieldEnderecoCEP.getText();
		String bairro = fieldEnderecoBairro.getText();
		String cidade = fieldEnderecoCidade.getText();
		String estado = (String) fieldEnderecoEstado.getSelectedItem();
		String referencia = fieldEnderecoReferencia.getText();
		
		Endereco endereco= (rec.getEnderecoAssistencia()==null)?new Endereco():rec.getEnderecoAssistencia();
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setPontoReferencia(referencia);
		rec.setEnderecoAssistencia(endereco);
	}
	
	protected void clear() {
		fieldCodigo.setEnabled(true);
		habilitarBotaoAdicionar();
		fieldID.setText("");
		fieldCodigo.setText("");
		fieldDescricaoProblema.setText("");
		fieldParecerTecnico.setText("");
		fieldNomeAssistencia.setText("");
		fieldFoneAssistencia.setText("");
		fieldMarca.setText("");
		fieldRecurso.setText("");
		fieldModelo.setText("");
		fieldPrevisaoDevolucao.setDate(null);
		equipamento = null;
		ManutencaoCorretiva = null;
		
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("00000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");
	}

	protected void loadForm(ManutencaoCorretiva rec) {
		fieldID.setText("" + rec.getId());
		fieldDescricaoProblema.setText(rec.getDescricaoProblema());
		fieldParecerTecnico.setText(rec.getParecerTecnico());
		fieldNomeAssistencia.setText(rec.getNomeAssistencia());
		fieldFoneAssistencia.setText(rec.getTelefoneAssistencia());
		fieldEnderecoLogradouro.setText(rec.getEnderecoAssistencia().getLogradouro());
		fieldEnderecoNumero.setText(rec.getEnderecoAssistencia().getNumero());
		fieldEnderecoComplemento.setText(rec.getEnderecoAssistencia().getComplemento());
		fieldEnderecoCEP.setText(rec.getEnderecoAssistencia().getCep());
		fieldEnderecoBairro.setText(rec.getEnderecoAssistencia().getBairro());
		fieldEnderecoCidade.setText(rec.getEnderecoAssistencia().getCidade());
		fieldEnderecoEstado.setSelectedItem(rec.getEnderecoAssistencia().getEstado());
		fieldEnderecoReferencia.setText(rec.getEnderecoAssistencia().getPontoReferencia());
		fieldPrevisaoDevolucao.setDate(rec.getPrevisaoDevolucao());
		ManutencaoCorretiva = rec;
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
		if(fieldNomeAssistencia.getText().length() < 2) {
			test = false;
			error += "\nInforme o nome da assistência técnica";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<ManutencaoCorretiva> listAll() {
		return Facade.getInstance().listarManutencaoCorretiva();
	}

	public boolean print(ManutencaoCorretiva current) {
		if(ManutencaoCorretiva != null) {
			// TODO
		}
		return false;
	}
}
