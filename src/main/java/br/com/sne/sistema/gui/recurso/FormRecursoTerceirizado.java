package br.com.sne.sistema.gui.recurso;

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
import br.com.sne.sistema.bean.FornecedorTerceirizado;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.RecursoTerceirizado;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormRecursoTerceirizado extends DefaultForm<RecursoTerceirizado, RecursoTerceirizadoTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JComboBox fieldGrupo;
	private JComboBox fieldFornecedorTerceirizado;
	private JTextField fieldCodigo;
	private JTextField fieldNome;
	private JTextField fieldDescricao;
	private JCheckBox fieldCalcularDiarias;
		
	private JMoedaRealTextField fieldPrecoEmpresa;
	private JMoedaRealTextField fieldPrecoFornecedor;
	private JTextArea fieldObservacoes;
		
	private RecursoTerceirizado recurso;
	
	public FormRecursoTerceirizado() {
		super(new RecursoTerceirizadoTableModel(), "/images/icon_terceirizado_18.png", "Recursos Terceirizados");
	}
	
	public boolean save(RecursoTerceirizado current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarRecursoTerceirizado(current);
			recurso = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Recurso Terceirizado já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(RecursoTerceirizado current) {
		boolean s = true;
		Facade.getInstance().atualizarRecursoTerceirizado(current);
		return s;
	}
	
	public boolean remove(RecursoTerceirizado current) {
		boolean test = false;
		try {
		Facade.getInstance().removerRecursoTerceirizado(current);
		test = true;
		} catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover Recurso Terceirizado.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldGrupo = new JComboBox();
		fieldFornecedorTerceirizado = new JComboBox();
		fieldDescricao = new JTextField();
		fieldPrecoEmpresa = new JMoedaRealTextField();
		fieldPrecoFornecedor = new JMoedaRealTextField();
		fieldNome = new JTextField();
		fieldObservacoes = new JTextArea();
		fieldCalcularDiarias = new JCheckBox();

				
		MaskFormatter formatoCodigo;
		try {
			formatoCodigo = new MaskFormatter("##");
			formatoCodigo.setPlaceholder("00");
			fieldCodigo = new JFormattedTextField(formatoCodigo);
		} catch (ParseException e) {
			fieldCodigo = new JTextField();
		}
				
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		fieldCalcularDiarias.setSelected(true);
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Fornecedor", fieldFornecedorTerceirizado), new RestricaoLayout(0, 1,1, true, false));
		
		this.addInputField(new TitledPanel("Grupo", fieldGrupo), new RestricaoLayout(0, 2, 1, true, false));
		this.addInputField(new TitledPanel("Calcular Diárias", fieldCalcularDiarias), new RestricaoLayout(0, 3, 1, true, false));
		
		this.addInputField(new TitledPanel("Código", fieldCodigo), new RestricaoLayout(0, 4, true, false));
		
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,5, 2, true, false));
		
		this.addInputField(new TitledPanel("Descrição", fieldDescricao), new RestricaoLayout(1,0,3, true, false));
		
		this.addInputField(new TitledPanel("Preço da Empresa", fieldPrecoEmpresa), new RestricaoLayout(1,3,2, true, false));
		this.addInputField(new TitledPanel("Preço do Fornecedor", fieldPrecoFornecedor), new RestricaoLayout(1,5,2, true, false));
		
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 7, 1, true, true));
		
	}
	
	public RecursoTerceirizado newElement() {
		RecursoTerceirizado rec = new RecursoTerceirizado();
		return rec;
	}

	public void loadInputFields(RecursoTerceirizado rec) {
		Grupo g = (Grupo) fieldGrupo.getSelectedItem();
		FornecedorTerceirizado ft = (FornecedorTerceirizado) fieldFornecedorTerceirizado.getSelectedItem();
		String codigo = fieldCodigo.getText();
		String nome = fieldNome.getText();
		String descricao = fieldDescricao.getText();
		BigDecimal precoEmpresa = fieldPrecoEmpresa.getValor();
		BigDecimal precoFornecedor = fieldPrecoFornecedor.getValor();
		boolean calcularDiarias = fieldCalcularDiarias.isSelected();

		String observacoes = fieldObservacoes.getText();
		
		rec.setGrupo(g);
		rec.setFornecedorTerceirizado(ft);
		rec.setCodigo(codigo);
		rec.setNome(nome);
		rec.setDescricao(descricao);
		rec.setCalcularDiarias(calcularDiarias);
		rec.setPrecoEmpresa(precoEmpresa);
		rec.setPrecoFornecedor(precoFornecedor);
		rec.setObservacoes(observacoes);
		
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldFornecedorTerceirizado.setSelectedIndex(0);
		fieldGrupo.setSelectedIndex(0);
		fieldCodigo.setText("00");
		fieldNome.setText("");
		fieldDescricao.setText("");
		fieldPrecoEmpresa.clear();
		fieldPrecoFornecedor.clear();
		fieldObservacoes.setText("");
		fieldCalcularDiarias.setSelected(true);

		recurso = null;	
	}

	protected void loadForm(RecursoTerceirizado rec) {
		fieldFornecedorTerceirizado.setSelectedItem(rec.getFornecedorTerceirizado());
		fieldGrupo.setSelectedItem(rec.getGrupo());
		fieldCodigo.setText(rec.getCodigo());
		fieldNome.setText(rec.getNome());
		fieldDescricao.setText(rec.getDescricao());
		fieldPrecoEmpresa.setValor(rec.getPrecoEmpresa());
		fieldPrecoFornecedor.setValor(rec.getPrecoFornecedor());
		fieldObservacoes.setText(rec.getObservacoes());
		fieldCalcularDiarias.setSelected(rec.isCalcularDiarias());

		recurso = rec;
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldFornecedorTerceirizado.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o Fornecedor";
		}
		if(fieldGrupo.getSelectedIndex() == 0) {
			test = false;
			error += "\nSelecione o Grupo";
		}
		if(Integer.parseInt(fieldCodigo.getText()) <= 0) {
			test = false;
			error += "\nPreencha o código corretamente";
		}
		if(fieldPrecoEmpresa.getValor().compareTo(fieldPrecoFornecedor.getValor())<0) {
			test = false;
			error += "\nO preço da empresa deve ser maior ou igual ao preço do fornecedor";
		}
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Recurso Terceirizado";
		}
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<RecursoTerceirizado> listAll() {
		
		List<RecursoTerceirizado> listRecursoTerceirizado = Facade.getInstance().listarRecursoTerceirizados();
		List<RecursoTerceirizado> resp = new ArrayList<RecursoTerceirizado>();

		Collections.sort(listRecursoTerceirizado);
		for(RecursoTerceirizado rec : listRecursoTerceirizado) {
			rec.getFornecedorTerceirizado().getNome();
			resp.add(rec);
		}
		return resp;
	}

	public void setVisible(boolean aFlag) {
		if (fieldGrupo != null && fieldFornecedorTerceirizado != null) {
			carregarComboGrupo();
			carregarComboFornecedorTerceirizado();
			super.setVisible(aFlag);
		}
		
	}

	private void carregarComboGrupo() {
		fieldGrupo.removeAllItems();
		fieldGrupo.addItem("");
		List<Grupo> lista = Facade.getInstance().listarGrupos();
		for(Grupo grp: lista) {
			if(grp.getTipoRecurso() == TipoRecurso.TERCEIRIZADO) {
				fieldGrupo.addItem(grp);
			}
			
		}
	}
	
	private void carregarComboFornecedorTerceirizado() {
		fieldFornecedorTerceirizado.removeAllItems();
		fieldFornecedorTerceirizado.addItem("");
		List<FornecedorTerceirizado> lista = Facade.getInstance().listarFornecedorTerceirizadoes();
		for(FornecedorTerceirizado grp: lista) {
			fieldFornecedorTerceirizado.addItem(grp);
		}
	}


	public boolean print(RecursoTerceirizado current) {
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
			JOptionPane.showMessageDialog(this, "Selecione um Recurso Terceirizado para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	

	
}
