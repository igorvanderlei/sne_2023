package br.com.sne.sistema.gui.grupo;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

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
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormGrupo extends DefaultForm<Grupo, GrupoTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JComboBox fieldTipoRecurso;
	private JTextField fieldCodigo;
	private JTextArea fieldObservacoes;
	
	private Grupo grupo;
	
	public FormGrupo() {
		super(new GrupoTableModel(), "/images/icon_grupo_18.png", "Cadastro de Grupos");
	}
	
	public boolean save(Grupo current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarGrupo(current);
			grupo = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Grupo já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Grupo current) {
		boolean s = true;
		Facade.getInstance().atualizarGrupo(current);
		return s;
	}
	
	public boolean remove(Grupo current) {
		boolean test = false;
		try {
			Facade.getInstance().removerGrupo(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Grupo. Verifique se existem Subgrupos cadastrados neste de Grupo antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldTipoRecurso = new JComboBox();
		
		MaskFormatter formatoCodigo;
		try {
			formatoCodigo = new MaskFormatter("###");
			formatoCodigo.setPlaceholder("000");
			fieldCodigo = new JFormattedTextField(formatoCodigo);
		} catch (ParseException e) {
			fieldCodigo = new JTextField();
		}
		
		carregarTipoRecurso();
		
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Código", fieldCodigo), new RestricaoLayout(0, 1, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,3,3, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(1, 0, 6, 1, true, true));
		this.addInputField(new TitledPanel("Tipo de Recurso", fieldTipoRecurso), new RestricaoLayout(0, 2, 1, true, false));
	}
	
	public void setVisible(boolean aFlag) {
		if (fieldTipoRecurso != null) {
			carregarTipoRecurso();
			super.setVisible(aFlag);
		}
	}
	
	public void carregarTipoRecurso() {
		fieldTipoRecurso.removeAllItems();
		fieldTipoRecurso.addItem("Selecione um tipo de recurso");
		TipoRecurso[] lista = TipoRecurso.values();
		for(TipoRecurso grp: lista) {
			fieldTipoRecurso.addItem(grp);
		}
	}
	
	public Grupo newElement() {
		return new Grupo();
	}

	public void loadInputFields(Grupo grupo) {
		String nome = fieldNome.getText();
		String observacoes = fieldObservacoes.getText();
		String codigo = fieldCodigo.getText();
		TipoRecurso tipoRecurso = (TipoRecurso)fieldTipoRecurso.getSelectedItem();
		
		grupo.setTipoRecurso(tipoRecurso);
		grupo.setObservacoes(observacoes);
		grupo.setNome(nome);
		grupo.setCodigo(codigo);
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		fieldCodigo.setText("000");
		fieldObservacoes.setText("");	
		fieldTipoRecurso.setSelectedIndex(0);
		grupo = null;
	}

	protected void loadForm(Grupo rec) {
		fieldID.setText("" + rec.getId());
		fieldNome.setText(rec.getNome());
		fieldCodigo.setText(rec.getCodigo());
		fieldObservacoes.setText(rec.getObservacoes());	
		fieldTipoRecurso.setSelectedItem(rec.getTipoRecurso());
		grupo = rec;
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(Integer.parseInt(fieldCodigo.getText()) <= 0) {
			test = false;
			error += "\nPreencha o código corretamente";

		}
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Grupo";
		}
		if(fieldTipoRecurso.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o tipo de recurso";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Grupo> listAll() {
		return Facade.getInstance().listarGrupos();
	}

	@Override
	public boolean print(Grupo current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(grupo != null) {
			hm.put("id", grupo.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/grupo.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um Grupo para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
}
