package br.com.sne.sistema.gui.rastreamento;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.BordedPanel;
import br.com.sne.sistema.gui.util.components.JCepField;
import br.com.sne.sistema.gui.util.components.JCnpjField;
import br.com.sne.sistema.gui.util.components.JComboEstado;
import br.com.sne.sistema.gui.util.components.JFoneField;
import br.com.sne.sistema.gui.util.components.JFormGroup;
import br.com.sne.sistema.gui.util.components.JIntField;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.TitledPanel;

public class FormRastreamento extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField fieldCodigo;


	private JTextField fieldClienteID;
	private JTextField fieldClienteRazao;
	private JTextField fieldClienteCnpj;
	private JTextField fieldClienteTelefone;
	private JTextField fieldClienteRamal;
	private JTextField fieldClienteContato;
	private JTextField fieldClienteCelular;

	private JTextField fieldResponsavelEstoque;
	private JTextField fieldResponsavelEvento;
	private JTextField fieldResponsavelDevolucao;
	private JTextField fieldResponsavelRecolhimento;
	private JTextField fieldDataDevolucao;

	private JTextField fieldNomeEvento;
	private JTextField fieldFuncionario;
	private JDateChooser fieldDataInicio;
	private JDateChooser fieldDataFim;
	private JDateChooser fieldDataMontagem;

	private JTextField fieldEnderecoLogradouro;
	private JTextField fieldEnderecoNumero;
	private JTextField fieldEnderecoComplemento;
	private JTextField fieldEnderecoCEP;
	private JTextField fieldEnderecoBairro;
	private JTextField fieldEnderecoCidade;
	private JComboBox fieldEnderecoEstado;
	private JTextField fieldEnderecoReferencia;
	private JTextField fieldLocalNome;

	public FormRastreamento(){
		super("Rastreamento");
		setFrameIcon(new ImageIcon(getClass().getResource("/images/icon_rastrear_18.png")));
		inicialize();
	}

	private void inicialize(){
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new GridBagLayout());
		fieldCodigo = new JTextField();
		conteudo.add(new TitledPanel("Código do Equipamento", fieldCodigo), new RestricaoLayout(0, 0, 5, true, false));
		conteudo.add(new BordedPanel(getBotaoRastrear()), new RestricaoLayout(0, 5, true, false));
		conteudo.add(getPanelResponsaveis(), new RestricaoLayout(1,0,6,true,false));
		conteudo.add(getPanelCliente(), new RestricaoLayout(2, 0, 6, true, false));
		conteudo.add(getPanelDadosEvento(), new RestricaoLayout(3, 0, 6, true, false));
		conteudo.add(new JPanel(), new RestricaoLayout(5,0,6,1,true,true));
		this.setContentPane(conteudo);
	}

	public JPanel getPanelResponsaveis() {
		JPanel responsaveis =  new JFormGroup("Responsáveis");
		responsaveis.setLayout(new GridBagLayout());

		fieldResponsavelDevolucao = new JTextField();
		fieldResponsavelEstoque = new JTextField();
		fieldResponsavelEvento = new JTextField();
		fieldDataDevolucao = new JTextField();
		fieldResponsavelRecolhimento = new JTextField();

		fieldResponsavelDevolucao.setEnabled(false);
		fieldResponsavelEstoque.setEnabled(false);
		fieldResponsavelEvento.setEnabled(false);
		fieldDataDevolucao.setEnabled(false);
		fieldResponsavelRecolhimento.setEnabled(false);

		responsaveis.add(new TitledPanel("Estoquista Entrega", fieldResponsavelEstoque), new RestricaoLayout(0,0,1,true,false));
		responsaveis.add(new TitledPanel("Motorista Entrega", fieldResponsavelEvento), new RestricaoLayout(0,1,1,true,false));
		responsaveis.add(new TitledPanel("Estoquista Devolução", fieldResponsavelDevolucao), new RestricaoLayout(0,2,1,true,false));
		responsaveis.add(new TitledPanel("Motorista Devolução", fieldResponsavelRecolhimento), new RestricaoLayout(0,3,1,true,false));
		responsaveis.add(new TitledPanel("Data da Devolução", fieldDataDevolucao), new RestricaoLayout(0,4,1,true,false));
		return responsaveis;
	}

	public JButton getBotaoRastrear(){
		JButton botaoRastrear = new JButton("Rastrear", new ImageIcon("images/save_24.png"));
		botaoRastrear.setHorizontalAlignment(SwingConstants.CENTER);
		botaoRastrear.setVerticalAlignment(SwingConstants.CENTER);
		botaoRastrear.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						rastrear(fieldCodigo.getText());
					}
				});
		return botaoRastrear;
	}
	
	public void rastrear(String codigo){
		EquipamentoEnviado eqv = Facade.getInstance().localizarEquipamentoEnviadoRastrear(codigo);
		if(eqv == null) {
			limparResponsaveis();
			limparLocal();
			limparCliente();
			JOptionPane.showMessageDialog(null, "O equipamento ainda não foi locado");
			return;
		}
		OrdemServico os = Facade.getInstance().localizaOrdemServicosPorEquipamento(eqv.getId());
		carregarCliente(os.getCliente());
		carregaDadosEvento(os);
		carregarLocal(os.getLocal());
		carregarResponsaveis(eqv);
		
	}

	public JPanel getPanelCliente() {
		JPanel panelCliente = new JFormGroup("Dados do Cliente");
		panelCliente.setLayout(new GridBagLayout());
		fieldClienteID = new JIntField();
		fieldClienteRazao = new JTextField();
		fieldClienteCnpj = new JCnpjField();
		fieldClienteTelefone = new JFoneField();
		fieldClienteRamal = new JTextField();
		fieldClienteContato = new JTextField();
		fieldClienteCelular = new JFoneField();

		fieldClienteID.setEditable(false);
		fieldClienteRazao.setEditable(false);
		fieldClienteCnpj.setEditable(false);
		fieldClienteTelefone.setEditable(false);
		fieldClienteRamal.setEditable(false);
		fieldClienteContato.setEditable(false);
		fieldClienteCelular.setEditable(false);

		panelCliente.add(new TitledPanel("Código", fieldClienteID), new RestricaoLayout(0, 0, false, false));
		panelCliente.add(new TitledPanel("Razão Social", fieldClienteRazao), new RestricaoLayout(0, 1, 5, true, false));
		panelCliente.add(new TitledPanel("CNPJ", fieldClienteCnpj), new RestricaoLayout(0, 6, true, false));

		panelCliente.add(new TitledPanel("Telefone", fieldClienteTelefone), new RestricaoLayout(1, 0, 2, true, false));
		panelCliente.add(new TitledPanel("Ramal", fieldClienteRamal), new RestricaoLayout(1, 2, false, false));
		panelCliente.add(new TitledPanel("Contato", fieldClienteContato), new RestricaoLayout(1, 3, 2, true, false));
		panelCliente.add(new TitledPanel("Celular", fieldClienteCelular), new RestricaoLayout(1, 5, 2, true, false));

		return panelCliente;
	}

	public void carregarCliente(Cliente c) {
		if(c!=null){
			fieldClienteID.setText("" + c.getId());
			fieldClienteRazao.setText(c.getNome());
			fieldClienteCnpj.setText(c.getCnpj());
			fieldClienteTelefone.setText(c.getFone());
			fieldClienteRamal.setText(c.getRamal());
			fieldClienteContato.setText(c.getContato());
			fieldClienteCelular.setText(c.getCelular());
		} else {
			limparCliente();
		}
	}
	public void limparCliente() {
		fieldClienteID.setText("");
		fieldClienteRazao.setText("");
		fieldClienteCnpj.setText("");
		fieldClienteTelefone.setText("");
		fieldClienteRamal.setText("");
		fieldClienteContato.setText("");
		fieldClienteCelular.setText("");
	}

	private JPanel getPanelDadosEvento() {
		fieldNomeEvento = new JTextField();
		fieldFuncionario = new JTextField();
		fieldDataInicio = new JDateChooser();
		fieldDataFim = new JDateChooser();
		fieldDataMontagem = new JDateChooser();
		fieldDataInicio.setMinSelectableDate(new Date());
		fieldDataFim.setMinSelectableDate(new Date());
		fieldDataMontagem.setMinSelectableDate(new Date());


		fieldDataInicio.setEnabled(false);
		fieldDataMontagem.setEnabled(false);
		fieldDataFim.setEnabled(false);

		fieldNomeEvento.setEditable(false);
		fieldFuncionario.setEditable(false);


		JPanel painelDadosEvento =  new JFormGroup("Dados do Evento");
		painelDadosEvento.setLayout(new GridBagLayout());

		painelDadosEvento.add(new TitledPanel("Nome do Evento", fieldNomeEvento), new RestricaoLayout(0, 0, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Início do Evento", fieldDataInicio), new RestricaoLayout(0, 1, false, false));
		painelDadosEvento.add(new TitledPanel("Fim do Evento", fieldDataFim), new RestricaoLayout(0, 2, false, false));
		painelDadosEvento.add(new TitledPanel("Montagem do Evento", fieldDataMontagem), new RestricaoLayout(0, 3, false, false));
		painelDadosEvento.add(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayout(0, 4, 1, true, false));
		painelDadosEvento.add(new TitledPanel("Local do Evento", getPanelEndereco()), new RestricaoLayout(1, 0, 5, true, false));
		return painelDadosEvento;		
	}

	private void carregaDadosEvento(OrdemServico rec){
		fieldNomeEvento.setText(rec.getNomeEvento());
		fieldFuncionario.setText(rec.getFuncionario().getNome());
		fieldDataInicio.setDate(rec.getDataInicio());
		fieldDataFim.setDate(rec.getDataFim());
		fieldDataMontagem.setDate(rec.getDataMontagem());
	}

	private JPanel getPanelEndereco () {
		JPanel endereco = new JPanel();
		endereco.setLayout(new GridBagLayout());

		fieldEnderecoLogradouro = new JTextField();
		fieldEnderecoNumero = new JTextField();
		fieldEnderecoComplemento = new JTextField();
		fieldEnderecoCEP = new JCepField();
		fieldEnderecoBairro = new JTextField();
		fieldEnderecoCidade = new JTextField();
		fieldEnderecoEstado = new JComboEstado();
		fieldEnderecoReferencia = new JTextField();
		fieldLocalNome = new JTextField();

		fieldEnderecoLogradouro.setEditable(false);
		fieldEnderecoNumero.setEditable(false);
		fieldEnderecoComplemento.setEditable(false);
		fieldEnderecoCEP.setEditable(false);
		fieldEnderecoBairro.setEditable(false);
		fieldEnderecoCidade.setEditable(false);
		fieldEnderecoEstado.setEditable(false);
		fieldEnderecoReferencia.setEditable(false);
		fieldLocalNome.setEditable(false);

		endereco.add(new TitledPanel("Nome do Local", fieldLocalNome), new RestricaoLayout(0, 1,5, true, false));
		endereco.add(new TitledPanel("Logradouro", fieldEnderecoLogradouro), new RestricaoLayout(1,0, 3, true, false));
		endereco.add(new TitledPanel("Numero", fieldEnderecoNumero), new RestricaoLayout(1,3, true, false));
		endereco.add(new TitledPanel("Complemento", fieldEnderecoComplemento), new RestricaoLayout(1,4, 1, true, false));
		endereco.add(new TitledPanel("CEP", fieldEnderecoCEP), new RestricaoLayout(1,5, 1, true, false));
		endereco.add(new TitledPanel("Bairro", fieldEnderecoBairro), new RestricaoLayout(2,0, 2, true, false));
		endereco.add(new TitledPanel("Cidade", fieldEnderecoCidade), new RestricaoLayout(2,2, 1, true, false));
		endereco.add(new TitledPanel("Estado", fieldEnderecoEstado), new RestricaoLayout(2,3, 1, true, false));

		endereco.add(new TitledPanel("Ponto de Referência", fieldEnderecoReferencia), new RestricaoLayout(2,4, 2, true, false));

		endereco.add(new JPanel(), new RestricaoLayout(4,0, 6,1, true, true));

		return endereco;
	}

	private void carregarLocal(LocalEvento localEvento) {
		if(localEvento!=null){
			fieldEnderecoLogradouro.setText(localEvento.getEndereco().getLogradouro());
			fieldEnderecoNumero.setText(localEvento.getEndereco().getNumero());
			fieldEnderecoComplemento.setText(localEvento.getEndereco().getComplemento());
			fieldEnderecoCEP.setText(localEvento.getEndereco().getCep());
			fieldEnderecoBairro.setText(localEvento.getEndereco().getBairro());
			fieldEnderecoCidade.setText(localEvento.getEndereco().getCidade());
			fieldEnderecoEstado.setSelectedItem(localEvento.getEndereco().getEstado());
			fieldEnderecoReferencia.setText(localEvento.getEndereco().getPontoReferencia());
			fieldLocalNome.setText(localEvento.getNome());
		} else {
			limparLocal();
		}
	}

	private void carregarResponsaveis(EquipamentoEnviado eqv) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		fieldResponsavelEstoque.setText(eqv.getUsuario().getFuncionario().getNome());
		if(eqv.getFuncionarioDevolucao()!=null)
			fieldResponsavelDevolucao.setText(eqv.getFuncionarioDevolucao().getNome());
		if(eqv.getFuncionarioEntrega()!=null)
			fieldResponsavelEvento.setText(eqv.getFuncionarioEntrega().getNome());
		if(eqv.getDataDevolucao() != null)
			fieldDataDevolucao.setText(formato.format(eqv.getDataDevolucao()));
		if(eqv.getFuncionarioRecolhimento() != null)
			fieldResponsavelRecolhimento.setText(eqv.getFuncionarioRecolhimento().getNome());
	}

	private void limparResponsaveis() {
		fieldResponsavelEstoque.setText("");
		fieldResponsavelDevolucao.setText("");
		fieldResponsavelEvento.setText("");
		fieldResponsavelRecolhimento.setText("");
		fieldDataDevolucao.setText("");
	}

	private void limparLocal() {
		fieldEnderecoLogradouro.setText("");
		fieldEnderecoNumero.setText("");
		fieldEnderecoComplemento.setText("");
		fieldEnderecoCEP.setText("000000000");
		fieldEnderecoBairro.setText("");
		fieldEnderecoCidade.setText("");
		fieldEnderecoEstado.setSelectedItem("PE");
		fieldEnderecoReferencia.setText("");
		fieldLocalNome.setText("");
	}

}
