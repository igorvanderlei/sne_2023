package br.com.sne.sistema.gui.local;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.SalaLocal;
import br.com.sne.sistema.bean.Endereco;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.orcamento.FormOrcamento;
import br.com.sne.sistema.gui.orcamento.PanelSala;
import br.com.sne.sistema.gui.orcamento.PanelSalaTerceirizado;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;

public class FormLocal extends DefaultForm<Local, LocalTableModel> {
	private static final long serialVersionUID = 1L;

	private JTextField fieldID;
	private JTextField fieldNome;

	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;
	
	private Local local;

	private JTextField fieldSalaNome;
	private JTextField fieldSalaLargura;
	private JTextField fieldSalaComprimento;
	private JTextField fieldSalaPeDireito;

	private SalaLocalTableModel modelSalas;

	private JTable tabelaSala;

	private JPopupMenu menuTabela;

	private JButton botaoAdicionarSala;

	private JTextField fieldSalaPontos;
	

	public FormLocal() {
		super(new LocalTableModel(), "/images/icon_local_18.png", "Locais");
	}
	
	public boolean save(Local current) {
		boolean s = false;
		try {
			Facade.getInstance().salvarLocal(current);
			local = current;
			s = true;
		}
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "O Local já se encontra cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}
	
	
	public boolean print(Local current){
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(local != null) {
			hm.put("id", local.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/local.jasper");  
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
			JOptionPane.showMessageDialog(this, "Selecione um Local para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}

	public boolean update(Local current) {
		boolean s = true;
		Facade.getInstance().atualizarLocal(current);
		return s;
	}
	
	public boolean remove(Local current) {
		boolean test = false;
		try {
			Facade.getInstance().removerLocal(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover o Local. Verifique se existem Recursos cadastrados neste de Local antes de tentar removê-lo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public void createInputFields() {
		fieldID = new JTextField();
		fieldNome  = new JTextField();
		fieldID.setEditable(false);
		
		this.addInputField(new TitledPanel("Id", fieldID), new RestricaoLayout(0, 0, false, false));
		this.addInputField(new TitledPanel("Nome", fieldNome), new RestricaoLayout(0,1,1, true, false));
		this.addInputField(getPanelEndereco(), new RestricaoLayout(1, 0, 2, true, false));
		this.addInputField(new JPanel(), new RestricaoLayout(3, 0, 3,1, true, true));
		
		  this.addInputField(new
		  TitledPanel("Salas do Local de Evento",getPanelSalas()), new
		  RestricaoLayout(2, 0,8,1, true, true));
	}
	
	private JMenuItem getMenuRemover() {
		JMenuItem menuRemover = new JMenuItem("Remover Sala");
		menuRemover.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelSalas.removeRow(tabelaSala.getSelectedRow());
					}
				}		
		);
		return menuRemover;
	}
	
	
	  public JPanel getPanelSalas() { 
		  JPanel panelLocal = new JPanel();
	  
		  fieldSalaNome = new JTextField(); 
		  fieldSalaLargura = new JTextField();
		  fieldSalaComprimento = new JTextField(); 
		  fieldSalaPeDireito = new JTextField();
		  fieldSalaPontos = new JTextField();
	
		  modelSalas = new SalaLocalTableModel(); 
		  tabelaSala = new JTable(modelSalas);
	  
		  menuTabela = new JPopupMenu();
	  
		  
		  menuTabela.add(getMenuRemover());
		  
		  tabelaSala.addMouseListener( new MouseAdapter() { 
			  public void mouseClicked(MouseEvent arg0) { 
				  if(arg0.getButton() == MouseEvent.BUTTON3) {
					  if(tabelaSala.getSelectedRow() >= 0 ) 
						  menuTabela.show(tabelaSala,arg0.getX(), arg0.getY()); 
					  else 
						  JOptionPane.showMessageDialog(null,"Selecione uma Sala para remover", "Atenção!", JOptionPane.WARNING_MESSAGE);
					  } 
				  } 
			  }
		  );
	  
		  JScrollPane scrollLocal = new JScrollPane();
		  scrollLocal.setViewportView(tabelaSala);
		  
		  botaoAdicionarSala = new JButton("Adicionar Sala");
		  botaoAdicionarSala.addActionListener(new ActionListener(){ 
			  public void actionPerformed(ActionEvent e) { 
				  if(fieldSalaNome.getText().length() < 3) {
					  JOptionPane.showMessageDialog(null, "Informe o nome da Sala"); 
					  return; 
				  } 
				  boolean nomeExiste = false; 
				  for(SalaLocal ae:modelSalas.getSalaLocals()) {
					  if(ae.getNome().equals(fieldSalaNome.getText())) { 
						  nomeExiste = true; 
						  break;
					  } 
				  } 
				  if(!nomeExiste) { 
					  try {
						  SalaLocal amb = new SalaLocal(
								  fieldSalaNome.getText(),
								  new Float(fieldSalaLargura.getText()),
								  new Float(fieldSalaComprimento.getText()),
								  new Float(fieldSalaPeDireito.getText()));
						  int pontos = new Integer(fieldSalaPontos.getText());
						  amb.setPontosFixacaoAerea(pontos);
						  amb.setPontoFixacaoAerea(false);
						  if(pontos != 0) {
							  amb.setPontoFixacaoAerea(true);
						  }
						  modelSalas.addElement(amb); 
						  limparSala(); 
					  }
					  catch(NumberFormatException nfe){
						  JOptionPane.showMessageDialog(null, "Há algum texto inválido!"); 
					  }
					  catch(ClassCastException cce){
						  JOptionPane.showMessageDialog(null, "Há algum texto inválido!"); 
					  }
				  } 
				  else {
					  JOptionPane.showMessageDialog(null, "O nome da Sala já está cadastrado"); 
				  } 
				  }
			  } 
		  );
		  panelLocal.setLayout(new GridBagLayout()); 
		  panelLocal.add(new TitledPanel("Nome", fieldSalaNome), new RestricaoLayout(0,0,1,true,false));
		  panelLocal.add(new TitledPanel("Largura", fieldSalaLargura), new RestricaoLayout(0,1,1,true,false)); 
		  panelLocal.add(new TitledPanel("Comprimento", fieldSalaComprimento), new RestricaoLayout(0,2,1,true,false)); 
		  panelLocal.add(new TitledPanel("Quantidade de Pontos de Fixação Aérea", fieldSalaPontos), new RestricaoLayout(1,0,1,true,false)); 
		  panelLocal.add(new TitledPanel("Pé Direito", fieldSalaPeDireito), new RestricaoLayout(1,1,1,true,false)); 
		  panelLocal.add(new TitledPanel("",botaoAdicionarSala), new RestricaoLayout(1,2,false,false));
		  panelLocal.add(new TitledPanel("", scrollLocal), new RestricaoLayout(2,0,4,1,true,true)); 
		  return panelLocal; 
	}
	 
	
	private void limparSala() {
		fieldSalaNome.setText("");
		fieldSalaLargura.setText("");
		fieldSalaComprimento.setText("");
		fieldSalaPeDireito.setText("");
		fieldSalaPontos.setText("");
	}
	
	private void limparPanelSala() {
		modelSalas.removeAllElements();
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


	public Local newElement() {
		return new Local();
	}
	


	public void loadInputFields(Local Local) {
		String nome = fieldNome.getText();
		String logradouro = fieldEnderecoLogradouro.getText();
		String numero = fieldEnderecoNumero.getText();
		String complemento = fieldEnderecoComplemento.getText();
		String cep = fieldEnderecoCEP.getText();
		String bairro = fieldEnderecoBairro.getText();
		String cidade = fieldEnderecoCidade.getText();
		String estado = (String) fieldEnderecoEstado.getSelectedItem();
		String referencia = fieldEnderecoReferencia.getText();
		Local.setSalaLocals(modelSalas.getSalaLocals());

		Local.setNome(nome);
		Endereco endereco=new Endereco();
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setComplemento(complemento);
		endereco.setCep(cep);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setPontoReferencia(referencia);
		Local.setEndereco(endereco);
	}
	
	protected void clear() {
		fieldID.setText("");
		fieldNome.setText("");

		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("00000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("SP");
		fieldEnderecoReferencia.setText("");
		limparSala();
		limparPanelSala();
		local = null;
		
	}
	

	protected void loadForm(Local loc) {
		clear();
		fieldID.setText("" + loc.getId());
		fieldNome.setText(loc.getNome());
		fieldEnderecoLogradouro.setText(loc.getEndereco().getLogradouro());
		fieldEnderecoNumero.setText(loc.getEndereco().getNumero());
		fieldEnderecoComplemento.setText(loc.getEndereco().getComplemento());
		fieldEnderecoCEP.setText(loc.getEndereco().getCep());
		fieldEnderecoBairro.setText(loc.getEndereco().getBairro());
		fieldEnderecoCidade.setText(loc.getEndereco().getCidade());
		fieldEnderecoEstado.setSelectedItem(loc.getEndereco().getEstado());
		fieldEnderecoReferencia.setText(loc.getEndereco().getPontoReferencia());
		List<SalaLocal> salaLocals = loc.getSalaLocals();
		
		System.out.println(salaLocals.size());
		for(SalaLocal amb: salaLocals) {
			modelSalas.addElement(amb);
		}
		local = loc;
	}
	
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldNome.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Local";
		}
		
		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);
	
		return test;
	}
	
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	public List<Local> listAll() {
		return Facade.getInstance().listarLocais();
	}

}