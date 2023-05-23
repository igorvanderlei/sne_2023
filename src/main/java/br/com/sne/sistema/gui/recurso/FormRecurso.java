package br.com.sne.sistema.gui.recurso;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.Funcao;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormRecurso extends DefaultForm<Recurso, RecursoTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldTipoRecurso;
	private JComboBox fieldGrupo;
	private JTextField fieldCodigo;
	private JTextField fieldNome;
	private JTextField fieldDescricao;
	private JCheckBox fieldCalcularDiarias;
	
	private JTextField fieldQuantidade;
	private JTextField fieldDisponiveis;
	
	private JMoedaRealTextField fieldPrecoSugerido;
	private JMoedaRealTextField fieldPrecoMinimo;
	private JMoedaRealTextField fieldPrecoCusto;
	private JTextArea fieldObservacoes;
		
	private Recurso recurso;
	
	public FormRecurso() {
		super(new RecursoTableModel(), "/images/icon_subgrupo_18.png", "Cadastro de Subgrupos");
	}
	
	public boolean save(Recurso current) {
		boolean s = false;
		try {
			Recurso r = newElement();
			loadInputFields(r);
			Facade.getInstance().salvarRecurso(r);
			recurso = r;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Subgrupo já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			err.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Recurso current) {
		boolean s = true;
		Recurso r = newElement();
		loadInputFields(r);
		r.setId(current.getId());
		Facade.getInstance().atualizarRecurso(r);
		//Facade.getInstance().atualizarRecurso(current);
		return s;
	}
	
	public boolean remove(Recurso current) {
		boolean test = false;
		try {
		Facade.getInstance().removerRecurso(current);
		test = true;
		} catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover Subgrupo. Verifique se existem Equipamentos cadastrados neste Subgrupo antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldTipoRecurso  = new JTextField();
		fieldGrupo = new JComboBox();
		fieldDescricao = new JTextField();
		fieldPrecoSugerido = new JMoedaRealTextField();
		fieldPrecoCusto = new JMoedaRealTextField();
		fieldPrecoMinimo = new JMoedaRealTextField();
		fieldNome = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldCalcularDiarias = new JCheckBox();
		
		fieldQuantidade = new JTextField();
		fieldQuantidade.setEnabled(false);
		
		fieldDisponiveis = new JTextField();
		fieldDisponiveis.setEnabled(false);
		
		fieldCalcularDiarias.setSelected(true);
		
		MaskFormatter formatoCodigo;
		try {
			formatoCodigo = new MaskFormatter("###");
			formatoCodigo.setPlaceholder("000");
			fieldCodigo = new JFormattedTextField(formatoCodigo);
		} catch (ParseException e) {
			fieldCodigo = new JTextField();
		}
				
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		fieldTipoRecurso.setEditable(false);
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Tipo de Recurso", fieldTipoRecurso), new RestricaoLayout(0, 1, true, false));
		
		this.addInputField(new TitledPanel("Grupo", fieldGrupo), new RestricaoLayout(0, 2, 1, true, false));
		this.addInputField(new TitledPanel("Calcular Diárias", fieldCalcularDiarias), new RestricaoLayout(0, 3, 1, true, false));

		this.addInputField(new TitledPanel("Código", fieldCodigo), new RestricaoLayout(0, 4,1, true, false));
		
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,5, 2, true, false));
		
		this.addInputField(new TitledPanel("Descrição", fieldDescricao), new RestricaoLayout(1,0,2, true, false));
		this.addInputField(new TitledPanel("Preço Custo", fieldPrecoCusto), new RestricaoLayout(1,4,1, true, false));

		this.addInputField(new TitledPanel("Preço Sugerido", fieldPrecoSugerido), new RestricaoLayout(1,2,1, true, false));
		this.addInputField(new TitledPanel("Preço Mínimo", fieldPrecoMinimo), new RestricaoLayout(1,3,1, true, false));
		
		this.addInputField(new TitledPanel("Qtd. Cadastrado", fieldQuantidade), new RestricaoLayout(1,5,1, true, false));
		this.addInputField(new TitledPanel("Qtd. Disponível", fieldDisponiveis), new RestricaoLayout(1,6,1, true, false));
		
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 7, 1, true, true));
	}
		
	public Recurso newElement() {
		Recurso rec = new Recurso();
		String recTipoRec = fieldTipoRecurso.getText();
		if(recTipoRec.equalsIgnoreCase(TipoRecurso.EQUIPAMENTO.toString())== true){
			rec = new DescricaoEquipamento();
		} else if(recTipoRec.equalsIgnoreCase(TipoRecurso.EQUIPE_TECNICA.toString())== true){
			rec = new Funcao();
		}
		return rec;
	}

	public void loadInputFields(Recurso rec) {
		Grupo g = (Grupo) fieldGrupo.getSelectedItem();
		String codigo = fieldCodigo.getText();
		String nome = fieldNome.getText();
		String descricao = fieldDescricao.getText();
		BigDecimal precoSugerido = fieldPrecoSugerido.getValor();
		BigDecimal precoMinimo = fieldPrecoMinimo.getValor();
		BigDecimal precoCusto = fieldPrecoCusto.getValor();
		boolean calcularDiarias = fieldCalcularDiarias.isSelected();

		String observacoes = fieldObservacoes.getText();
		
		rec.setCalcularDiarias(calcularDiarias);
		rec.setGrupo(g);
		rec.setCodigo(codigo);
		rec.setNome(nome);
		rec.setDescricao(descricao);
		rec.setPrecoSugerido(precoSugerido);
		rec.setPrecoCusto(precoCusto);
		rec.setValorMinimo(precoMinimo);
		rec.setObservacoes(observacoes);
		
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldTipoRecurso.setText("");
		fieldGrupo.setSelectedIndex(0);
		fieldCodigo.setText("000");
		fieldNome.setText("");
		fieldDescricao.setText("");
		fieldPrecoSugerido.clear();
		fieldPrecoCusto.clear();
		fieldPrecoMinimo.clear();
		fieldObservacoes.setText("");
		fieldQuantidade.setText("");
		fieldDisponiveis.setText("");
		fieldCalcularDiarias.setSelected(true);
		recurso = null;	
	}

	protected void loadForm(Recurso rec) {
		fieldID.setText("" + rec.getId());
		fieldTipoRecurso.setText(rec.getGrupo().getTipoRecurso().toString());

		fieldGrupo.setSelectedItem(rec.getGrupo());
		fieldCodigo.setText(rec.getCodigo());
		fieldNome.setText(rec.getNome());
		fieldDescricao.setText(rec.getDescricao());
		fieldPrecoSugerido.setValor(rec.getPrecoSugerido());
		fieldPrecoMinimo.setValor(rec.getValorMinimo());
		fieldPrecoCusto.setValor(rec.getPrecoCusto());
		fieldObservacoes.setText(rec.getObservacoes());
		fieldCalcularDiarias.setSelected(rec.isCalcularDiarias());
		
		if(rec instanceof DescricaoEquipamento ) {
			fieldQuantidade.setText("" + Facade.getInstance().contarTodosEquipamentos((DescricaoEquipamento) rec));
			fieldDisponiveis.setText("" + Facade.getInstance().contarEquipamentos((DescricaoEquipamento) rec, StatusEquipamento.DISPONIVEL));
		} else if(rec instanceof Funcao){
			fieldQuantidade.setText("" + Facade.getInstance().contarTodosFuncionario((Funcao) rec));
		}
		
		recurso = rec;
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldGrupo.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o Grupo";
		}
		if(Integer.parseInt(fieldCodigo.getText()) <= 0) {
			test = false;
			error += "\nPreencha o código corretamente";
		}
		if(fieldPrecoSugerido.getValor().compareTo(fieldPrecoMinimo.getValor())<0) {
			test = false;
			error += "\nO preço sugerido deve ser maior ou igual ao preço mínimo";
		}
		if(fieldPrecoSugerido.getValor().compareTo(fieldPrecoCusto.getValor())<0) {
			test = false;
			error += "\nO preço sugerido deve ser maior ou igual ao preço de custo";
		}
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Recurso";
		}
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Recurso> listAll() {

		List<Recurso> listRecurso = Facade.getInstance().listarRecursos();
		List<Recurso> resp = new ArrayList<Recurso>();

		Collections.sort(listRecurso);
		for(Recurso rec : listRecurso) {
			resp.add(rec);
		}
		return resp;
	}

	public void setVisible(boolean aFlag) {
		if (fieldGrupo != null) {
			carregarComboGrupo();
			super.setVisible(aFlag);
		}
		
	}

	private void carregarComboGrupo() {
		fieldGrupo.removeAllItems();
		fieldGrupo.addItem("");
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		for(Grupo grp: lista) {
			if(grp.getTipoRecurso() != TipoRecurso.TERCEIRIZADO) {
				fieldGrupo.addItem(grp);
			}
		}
		
		fieldGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(fieldGrupo.getSelectedItem() != "" && fieldGrupo.getSelectedItem() != null) {
	        		fieldTipoRecurso.setText(((Grupo)fieldGrupo.getSelectedItem()).getTipoRecurso().toString());;
	        	}
	        }
	    });
	}


	public boolean print(Recurso current) {
	HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(recurso != null) {
			hm.put("id", recurso.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/recurso.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um Recurso para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	

	
}
