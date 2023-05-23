package br.com.sne.sistema.gui.centrocusto;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormCentroCusto extends DefaultForm<CentroCusto, CentroCustoTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JTextArea fieldObservacoes;
	private CentroCusto grupo;
	
	public FormCentroCusto() {
		super(new CentroCustoTableModel(), "/images/icon_ccusto_18.png", "Centro de Custos");
		desabilitarBotaoImprimir();
	}
	
	public boolean save(CentroCusto current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarCentroCusto(current);
			grupo = current;
			s = true;
		}
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O CentroCusto já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		} 
		catch(RuntimeException err) {
			err.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(CentroCusto current) {
		boolean s = true;
		Facade.getInstance().atualizarCentroCusto(current);
		return s;
	}
	
	public boolean remove(CentroCusto current) {
		boolean test = false;
		try {
			Facade.getInstance().removerCentroCusto(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o CentroCusto. Verifique se existem Recursos cadastrados neste de CentroCusto antes de tentar remov�-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
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
		
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,1, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(1, 0, 2, 1, true, true));
	}
	
	public CentroCusto newElement() {
		return new CentroCusto();
	}

	public void loadInputFields(CentroCusto grupo) {
		String nome = fieldNome.getText();
		String observacoes = fieldObservacoes.getText();
		grupo.setObservacoes(observacoes);
		grupo.setNome(nome);
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		fieldObservacoes.setText("");	
		grupo = null;
	}

	protected void loadForm(CentroCusto rec) {
		fieldID.setText("" + rec.getId());
		fieldNome.setText(rec.getNome());
		fieldObservacoes.setText(rec.getObservacoes());	
		grupo = rec;
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Centro de Custo";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<CentroCusto> listAll() {
		/*
		 * if(getJComboBoxFiltro().getSelectedIndex() == 0 &&
		 * getJTextFielBusca().getText().length() > 2) {
		 * JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro", "ERRO",
		 * JOptionPane.ERROR_MESSAGE); return new ArrayList<CentroCusto>();
		 * 
		 * }
		 */
		return Facade.getInstance().listarCentroCustos();
	}

	@Override
	public boolean print(CentroCusto current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if(grupo != null) {
			hm.put("id", grupo.getId());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				JasperPrint impressao = JasperFillManager.fillReport("br/com/sne/sistema/report/grupo.jasper", hm, c);   
				JasperViewer.viewReport(impressao ,false);
				c.close();
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione um CentroCusto para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
}
