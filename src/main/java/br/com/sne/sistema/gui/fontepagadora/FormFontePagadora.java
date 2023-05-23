package br.com.sne.sistema.gui.fontepagadora;

import java.net.URL;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.FontePagadora;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormFontePagadora extends DefaultForm<FontePagadora, FontePagadoraTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;

	private JTextArea fieldObservacoes;
	private FontePagadora fontePagadora;
					
	public FormFontePagadora() {
		super(new FontePagadoraTableModel(), "/images/icon_fpagadora_18.png", "Fontes Pagadoras");
		clear();
	}
		
	public boolean save(FontePagadora current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarFontePagadora(current);
			fontePagadora = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "A Fonte Pagadora já se encontra cadastrada.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(FontePagadora current) {
		boolean s = true;
		Facade.getInstance().atualizarFontePagadora(current);
		return s;
	}
	
	public boolean remove(FontePagadora current) {
		boolean test = false;
		try {
			Facade.getInstance().removerFontePagadora(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Fonte Pagadora.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome = new JTextField();
		fieldObservacoes = new JTextArea();
				
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		this.desabilitarBotaoImprimir();
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0, 1, 1, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(1, 0, 2, 1, true, true));
	}
	
	public FontePagadora newElement() {
		return new FontePagadora();
	}

	public void loadInputFields(FontePagadora grupo) {
		grupo.setNome(fieldNome.getText());
		grupo.setObservacoes(fieldObservacoes.getText());
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		fieldObservacoes.setText("");
		
		fontePagadora = null;
		
	}

	protected void loadForm(FontePagadora desp) {
						
		fieldID.setText("" + desp.getId());
		fieldNome.setText(desp.getNome());
		fieldObservacoes.setText(desp.getObservacoes());
		
		fontePagadora = desp;
		
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";

		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha a descrição da FontePagadora";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<FontePagadora> listAll() {
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<FontePagadora>();
		}
		
		List<FontePagadora> lista = Facade.getInstance().listarFontePagadoras(campo,texto);
		List<FontePagadora> resp = new ArrayList<FontePagadora>();

		for(FontePagadora c : lista) {
			resp.add(c);
		}
		return resp;
	}

	@Override
	public boolean print(FontePagadora current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(fontePagadora != null) {
			hm.put("id", fontePagadora.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/fontePagadora.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um FontePagadora para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
		
}
