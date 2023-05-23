package br.com.sne.sistema.gui.despesa;

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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Recibo;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.OpcaoPagamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.ImageFilter;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;


public class TelaParamPagarDespesa extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelPagamento;
	private JLabel labelRecibo;
	private JLabel labelImagem;
	private JLabel labelData;
	private JLabel labelOpcao;
	
	private int larguraImagem = 470;
	private int alturaImagem = 229;
	
	private JMoedaRealTextField textPagamento;
	private JDateChooser chooserData;
	private JComboBox<OpcaoPagamento> comboOpcao;
	
	private JButton botaoRecibo;
	private JButton botaoOk;
	private JButton botaoCancelar;
	
	private Despesa despesa;
	
	private Recibo recibo = new Recibo();
	
	private String observacoes;
	
	public TelaParamPagarDespesa(Despesa despesa, String observacoes) {
		setTitle("Pagamento de despesa");
		setSize(new Dimension(600, 500));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		
		labelPagamento = new JLabel("Informe o valor pago:");
		labelData = new JLabel("Informe a data de Pagamento:");
		labelOpcao = new JLabel("Informe a opção de pagamento:");
		chooserData = new JDateChooser();
		chooserData.setMaxSelectableDate(new Date());
		chooserData.setDate(new Date());
		labelRecibo = new JLabel("Selecione o recibo do pagamento:");
		labelImagem = new JLabel();
		botaoRecibo = new JButton("Carregar Imagem...");
		botaoOk = new JButton("OK");
		comboOpcao = new JComboBox<OpcaoPagamento>();
		botaoCancelar = new JButton("Cancelar");
		textPagamento = new JMoedaRealTextField();
		this.despesa = despesa;
		this.observacoes = observacoes;
		
		textPagamento.setValor(despesa.getValor());
		
		carregarComboOpcaoPag();
		
		labelPagamento.setBounds(new Rectangle(65, 20, 150, 29));
		textPagamento.setBounds(new Rectangle(65,50,150,29));
		labelData.setBounds(new Rectangle(300,20,200,29));
		chooserData.setBounds(new Rectangle(300,50,200,29));
		labelRecibo.setBounds(new Rectangle(65,90,250,29));
		botaoRecibo.setBounds(new Rectangle(65,120,225,29));
		labelOpcao.setBounds(new Rectangle(300,90,235,29));
		comboOpcao.setBounds(new Rectangle(300,120,235,29));
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
		getContentPane().add(labelOpcao);
		getContentPane().add(comboOpcao);

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
	
	public void carregarComboOpcaoPag() {
		comboOpcao.removeAllItems();
		for(OpcaoPagamento f: OpcaoPagamento.values()) {
			comboOpcao.addItem(f);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == botaoRecibo) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new ImageFilter());
			fc.setAcceptAllFileFilterUsed(false);
	        int returnVal = fc.showOpenDialog(TelaParamPagarDespesa.this);
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
			despesa = Facade.getInstance().carregarDespesa(despesa.getId());
			
			if(preco != null) {
				if(preco.compareTo(despesa.getValor()) != 0){
					JOptionPane.showMessageDialog(TelaParamPagarDespesa.this, "O valor informado é diferente do valor da despesa.", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(labelImagem.getIcon() != null) {
					recibo.setDespesa(despesa);
			        recibo.setCancelado(false);
			        Facade.getInstance().salvarRecibo(recibo);
				}
					        
				despesa.setValorPago(preco);
				despesa.setDataPagamento(chooserData.getDate());
				despesa.setObservacoes(observacoes);
				despesa.setOpcaoPag((OpcaoPagamento)comboOpcao.getSelectedItem());
				despesa.setSituacao(true);
				Facade.getInstance().atualizarDespesa(despesa);
				JOptionPane.showMessageDialog(null,"Registro de pagamento realizado com sucesso!");
				Facade.getInstance().commit();
				this.dispose();
				return;
			}
		}
		else if(e.getSource() == botaoCancelar) {
			this.dispose();
			return;
		}
		
	}
}
