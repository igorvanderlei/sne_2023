package br.com.sne.sistema.gui.contareceber;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.bean.Recibo;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.OpcaoPagamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.ImageFilter;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;


public class TelaParamPagarReceita extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelPagamento;
	private JLabel labelRecibo;
	private JLabel labelImagem;
	private JLabel labelData;
	
	private int larguraImagem = 470;
	private int alturaImagem = 229;
	
	private JMoedaRealTextField textPagamento;
	private JDateChooser chooserData;
	
	private JButton botaoRecibo;
	private JButton botaoOk;
	private JButton botaoCancelar;
	
	private Receita receita;
	
	private Recibo recibo = new Recibo();
	
	private String observacoes;
	private OpcaoPagamento oP;
	private BigDecimal taxa;
	
	public TelaParamPagarReceita(Receita receita, String observacoes, OpcaoPagamento oP,BigDecimal taxa) {
		setTitle("Pagamento de receita");
		setSize(new Dimension(600, 500));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		
		labelPagamento = new JLabel("Informe o valor pago:");
		labelData = new JLabel("Informe a data de Pagamento:");
		chooserData = new JDateChooser();
		chooserData.setMaxSelectableDate(new Date());
		chooserData.setDate(new Date());
		labelRecibo = new JLabel("Selecione o recibo do pagamento:");
		labelImagem = new JLabel();
		botaoRecibo = new JButton("Carregar Imagem...");
		botaoOk = new JButton("OK");
		botaoCancelar = new JButton("Cancelar");
		textPagamento = new JMoedaRealTextField();
		this.receita = receita;
		this.observacoes = observacoes;
		this.taxa = taxa;
		this.oP = oP;
		
		textPagamento.setValor(receita.getValor());
		
		labelPagamento.setBounds(new Rectangle(65, 20, 150, 29));
		textPagamento.setBounds(new Rectangle(65,50,150,29));
		labelData.setBounds(new Rectangle(300,20,200,29));
		chooserData.setBounds(new Rectangle(300,50,200,29));
		labelRecibo.setBounds(new Rectangle(65,90,250,29));
		botaoRecibo.setBounds(new Rectangle(65,120,larguraImagem,29));
		labelImagem.setBounds(new Rectangle(65,151,larguraImagem,alturaImagem));
		botaoOk.setBounds(new Rectangle(65,400,100,29));
		botaoCancelar.setBounds(new Rectangle(435,400,100,29));
		
		getContentPane().add(labelPagamento);
		getContentPane().add(textPagamento);
		getContentPane().add(labelData);
		getContentPane().add(chooserData);
		getContentPane().add(labelRecibo);
		getContentPane().add(botaoRecibo);
        getContentPane().add(labelImagem);
		getContentPane().add(botaoOk);
		getContentPane().add(botaoCancelar);
		
		botaoRecibo.addActionListener(this);
		botaoOk.addActionListener(this);
		botaoCancelar.addActionListener(this);
	}
	
	public Double getValor(JMoedaRealTextField valor) {
		try {
			return valor.getValor().doubleValue();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == botaoRecibo) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new ImageFilter());
			fc.setAcceptAllFileFilterUsed(false);
	        int returnVal = fc.showOpenDialog(TelaParamPagarReceita.this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	
		        File file = fc.getSelectedFile();
		        recibo.alterarImagemRecibo(file.getAbsolutePath());
	            Image img = recibo.getImagemReciboImage().getImage().getScaledInstance(larguraImagem,alturaImagem, Image.SCALE_DEFAULT);
	            labelImagem.setIcon(new ImageIcon(img));
	            
	        }		
		}
		else if(e.getSource() == botaoOk) {
			BigDecimal preco = textPagamento.getValor();//textPagamento.getText().replace("R$ ", ""));
			Facade.getInstance().beginTransaction();
			receita = Facade.getInstance().carregarReceita(receita.getId());
			
			
			if(preco != null) {
				if(preco.compareTo(receita.getValor()) != 0){
					JOptionPane.showMessageDialog(TelaParamPagarReceita.this, "O valor informado é diferente do valor da receita.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(labelImagem.getIcon() != null) {
					recibo.setReceita(receita);
			        recibo.setCancelado(false);
			        Facade.getInstance().salvarRecibo(recibo);
				}
					        
				receita.setValorPago(preco);
				receita.setDataPagamento(chooserData.getDate());
				receita.setObservacoes(observacoes);
				receita.setSituacao(true);
				receita.setOpcaoPag(oP);
				if(receita.getOpcaoPag() == OpcaoPagamento.CARTAO) {
					receita.setValor(receita.getValor().subtract(taxa));
					Despesa despesa = new Despesa();
					despesa.setCentroCusto(Facade.getInstance().carregarCentroCusto(1));
					despesa.setDataCadastro(new Date());
					despesa.setDataVencimento(receita.getDataVencimento());
					despesa.setDataPagamento(new Date());
					despesa.setDeletado(false);
					despesa.setDescricao("Taxa de cartão da Receita Nº "+receita.getId());
					despesa.setEmpresa(Facade.getInstance().getUsuarioLogado().getUnidade());
					despesa.setFontePagadora(Facade.getInstance().carregarFontePagadora(1));
					despesa.setFornecedor(Facade.getInstance().carregarFornecedor(1));
					despesa.setFuncionarioCadastro(receita.getFuncionarioCadastro());
					despesa.setObservacoes(receita.getObservacoes());
					despesa.setOpcaoPag(receita.getOpcaoPag());
					despesa.setOrdemServico(receita.getOrdemServico());
					despesa.setSituacao(true);
					despesa.setValor(taxa);
					despesa.setValorPago(taxa);
					Facade.getInstance().salvarDespesa(despesa);
				}
				receita.setValorPago(preco.subtract(taxa));
				Facade.getInstance().atualizarReceita(receita);
				JOptionPane.showMessageDialog(null,"Registro de pagamento realizado com sucesso!");
				this.dispose();
				Facade.getInstance().commit();
				return;
			}
		}
		else if(e.getSource() == botaoCancelar) {
			this.dispose();
			return;
		}
		
	}
}
