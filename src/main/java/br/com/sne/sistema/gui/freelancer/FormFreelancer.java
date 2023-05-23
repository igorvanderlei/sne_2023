package br.com.sne.sistema.gui.freelancer;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.bean.Freelancer;
import br.com.sne.sistema.bean.Funcao;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.funcionario.FuncoesTableModel;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormFreelancer extends DefaultForm<Freelancer, FreelancerTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;
	private JTextField fieldCelular;
	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoReferencia;
	private JTextArea fieldObservacoes;
	private JMoedaRealTextField fieldDiaria;
	
	private JTable fieldFuncoes;

	private JScrollPane jScrollPaneFuncoesEscolha;
	private FuncoesTableModel modelFuncoes;
	
	private Freelancer freelancer;

	public FormFreelancer() {
		super(new FreelancerTableModel(), "/images/icon_freelancer_18.png", "Freelancers");
	}

	public boolean save(Freelancer current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarFreelancer(current);
			freelancer = current;
			s = true;
		} 
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Funcionário já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	public boolean update(Freelancer current) {
		boolean s = true;
		Facade.getInstance().atualizarFuncionario(current);
		return s;
	}

	public boolean remove(Freelancer current) {
		boolean test = false;
		try {
			Facade.getInstance().removerFreelancer(current);
			freelancer = null;
			test = true;
		} catch (RuntimeException err) {
			err.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erro ao remover o Funcionário. Verifique se existem cadastros vinculados a este Funcionário antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome = new JTextField();
		fieldCelular =  new JFoneField();
		fieldObservacoes = new JTextArea();

		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		fieldID.setEditable(false);
		
		fieldDiaria = new JMoedaRealTextField();

		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,1,5, true, false));
		this.addInputField(new TitledPanel("Celular", fieldCelular), new RestricaoLayout(0,6,1, true, false));
		this.addInputField(new TitledPanel("Diária", fieldDiaria), new RestricaoLayout(0,7,1, true, false));
		this.addInputField(getPanelEndereco(), new RestricaoLayout(1, 0, 8, true, false));
		this.addInputField(new TitledPanel("Observações", scrollObservacoes), new RestricaoLayout(2, 0, 6, 1, true, true));
		this.addInputField(getPainelFuncoes(), new RestricaoLayout(2, 6, 2,1, true, true));
		carregarTabelaFuncoes();
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

	private JPanel getPainelFuncoes() {
		JPanel funcoes = new JFormGroup("Funções");
		funcoes.setLayout(new GridBagLayout());
		if (jScrollPaneFuncoesEscolha == null) {
			jScrollPaneFuncoesEscolha = new JScrollPane();
			jScrollPaneFuncoesEscolha.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jScrollPaneFuncoesEscolha.setViewportView(getJTableFuncoesEscolha());
			funcoes.add(jScrollPaneFuncoesEscolha, new RestricaoLayout(2, 0, 5, 1, true, true));
		}
		return funcoes;
	}

	private JTable getJTableFuncoesEscolha() {
		if (fieldFuncoes == null) {
			modelFuncoes = new FuncoesTableModel();
			fieldFuncoes = new JTable(modelFuncoes);
			TableColumn column = null;
			int columnWidth[] = {20, 200};
			for (int i = 0; i < columnWidth.length; i++) {
				column = fieldFuncoes.getColumnModel().getColumn(i);
				column.setPreferredWidth(columnWidth[i]);
			}			
			fieldFuncoes.setShowVerticalLines(false);
			fieldFuncoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			fieldFuncoes.setFillsViewportHeight(true);
		}
		return fieldFuncoes;
	}

	private void carregarTabelaFuncoes() {
		FuncoesTableModel modelo = (FuncoesTableModel) fieldFuncoes.getModel();
		modelo.removeAllElements();
		List<Funcao> lista = Facade.getInstance().listarFuncoes();
		for(Funcao func : lista) {
			modelo.addFuncao(func, false);
		}
	}

	
	

	public Freelancer newElement() {
		return new Freelancer();
	}

	public void loadInputFields(Freelancer funcionario) {
		String nome = fieldNome.getText();
		String celular = fieldCelular.getText();
		String enderecoLogradouro = fieldEnderecoLogradouro.getText();
		String enderecoNumero  = fieldEnderecoNumero.getText();
		String enderecoComplemento = fieldEnderecoComplemento.getText();
		String enderecoBairro = fieldEnderecoBairro.getText();
		String enderecoCidade = fieldEnderecoCidade.getText();
		String estado = (String) fieldEnderecoEstado.getSelectedItem();
		String enderecoCEP = fieldEnderecoCEP.getText();
		String enderecoReferencia = fieldEnderecoReferencia.getText();
		//private JTable fieldFuncoes;
		String observacoes = fieldObservacoes.getText();

		funcionario.setNome(nome);
		funcionario.setCelular(celular);
		try {
			if(fieldDiaria.getValor() != null && funcionario instanceof Freelancer)
				((Freelancer) funcionario).setDiaria(fieldDiaria.getValor());
		} catch (Exception e) {
			e.printStackTrace();
		}
		funcionario.setObservacoes(observacoes);
		Endereco endereco= (funcionario.getEndereco()==null)?new Endereco():funcionario.getEndereco();
		endereco.setLogradouro(enderecoLogradouro);
		endereco.setNumero(enderecoNumero);
		endereco.setComplemento(enderecoComplemento);
		endereco.setBairro(enderecoBairro);
		endereco.setCidade(enderecoCidade);
		endereco.setCep(enderecoCEP);
		endereco.setEstado(estado);
		endereco.setPontoReferencia(enderecoReferencia);
		funcionario.setEndereco(endereco);
		funcionario.setFuncao(modelFuncoes.getFuncoesSelecionadas());


	}

	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");
		fieldCelular.setText("");
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("00000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");
		fieldObservacoes.setText("");
		fieldDiaria.setValor(new BigDecimal(0));
		carregarTabelaFuncoes();
		freelancer = null;
	}

	protected void loadForm(Freelancer fun) {
		fieldID.setText("" + fun.getId());
		fieldNome.setText(fun.getNome());
		fieldCelular.setText(fun.getCelular());
		fieldEnderecoLogradouro.setText(fun.getEndereco().getLogradouro());
		fieldEnderecoNumero.setText(fun.getEndereco().getNumero());
		fieldEnderecoComplemento.setText(fun.getEndereco().getComplemento());
		fieldEnderecoCEP.setText(fun.getEndereco().getCep());
		fieldEnderecoBairro.setText(fun.getEndereco().getBairro());
		fieldEnderecoCidade.setText(fun.getEndereco().getCidade());
		fieldEnderecoEstado.setSelectedItem(fun.getEndereco().getEstado());
		fieldEnderecoReferencia.setText(fun.getEndereco().getPontoReferencia());
		fieldObservacoes.setText(fun.getObservacoes());	
		
		if(fun instanceof Freelancer)
			fieldDiaria.setValor(((Freelancer) fun).getDiaria());
		
		Set<Funcao> funcaoAtribuida = fun.getFuncao();
		if(funcaoAtribuida != null) {
			modelFuncoes.removeAllElements();
			List<Funcao> lista = Facade.getInstance().listarFuncoes();
			for(Funcao f : lista) {
				boolean contains = false;
				for(Funcao ff: funcaoAtribuida) {
					if(f.getId() == ff.getId()) {
						contains = true;
						break;
					}
				}
				modelFuncoes.addFuncao(f, contains);
			}
		}
		freelancer = fun;
		
	}

	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";

		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Grupo";
		}

		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Freelancer> listAll() {
		List<Freelancer> lista = new ArrayList<Freelancer>();
		lista.addAll(Facade.getInstance().listarFreelancers());
		return lista;
	}

	public boolean print(Freelancer current) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(freelancer != null){
			hm.put("id",  freelancer.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/freelancer.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um Freelancer para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}
}
