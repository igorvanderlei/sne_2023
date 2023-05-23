package br.com.sne.sistema.gui.pauta;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;

import br.com.sne.sistema.bean.Pauta;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusPauta;
import br.com.sne.sistema.facade.Facade;


public class TelaParamAcompanharPauta extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelData;
	private JLabel labelOpcao;
	private JLabel labelObservacoes;

	private JTextArea fieldObservacoes;
	private JDateChooser chooserData;
	private JComboBox<StatusPauta> comboOpcao;
	
	private JButton botaoOk;
	private JButton botaoCancelar;
	
	private Pauta pauta;
	private StatusPauta status;
		
	public TelaParamAcompanharPauta(Pauta pauta) {
		setTitle("Acompanhamento de pauta");
		setSize(new Dimension(600, 500));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		
		labelData = new JLabel("Informe a data do próximo contato:");
		labelOpcao = new JLabel("Status da Pauta:");
		labelObservacoes = new JLabel("Observações:");

		chooserData = new JDateChooser();
		chooserData.setMinSelectableDate(new Date());
		fieldObservacoes = new JTextArea();
		botaoOk = new JButton("OK");
		comboOpcao = new JComboBox<StatusPauta>();
		botaoCancelar = new JButton("Cancelar");
		this.pauta = pauta;
				
		JScrollPane scrollObservacoes = new JScrollPane();
		scrollObservacoes.setViewportView(fieldObservacoes);
		
		carregarComboOpcaoPag();
		
		labelData.setBounds(new Rectangle(65, 20, 470, 29));
		chooserData.setBounds(new Rectangle(65,50,470,29));
		labelOpcao.setBounds(new Rectangle(65,90,470,29));
		comboOpcao.setBounds(new Rectangle(65,120,470,29));
		labelObservacoes.setBounds(new Rectangle(65, 150, 470, 29));
		scrollObservacoes.setBounds(new Rectangle(65,180,470,209));
		botaoOk.setBounds(new Rectangle(65,400,100,29));
		botaoCancelar.setBounds(new Rectangle(435,400,100,29));
		
		getContentPane().add(labelData);
		getContentPane().add(chooserData);
		getContentPane().add(labelObservacoes);
        getContentPane().add(scrollObservacoes);
		getContentPane().add(botaoOk);
		getContentPane().add(botaoCancelar);
		getContentPane().add(labelOpcao);
		getContentPane().add(comboOpcao);

		botaoOk.addActionListener(this);
		botaoCancelar.addActionListener(this);
	}
	
	public void carregarComboOpcaoPag() {
		comboOpcao.removeAllItems();
		for(StatusPauta f: StatusPauta.values()) {
			comboOpcao.addItem(f);
		}
		comboOpcao.setSelectedItem(pauta.getStatus());
		status = pauta.getStatus();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == botaoOk) {
			StatusPauta statusNovo = (StatusPauta)comboOpcao.getSelectedItem();
			String observacoes = fieldObservacoes.getText();
			Date proximoContato = chooserData.getDate();
			/*if(status != statusNovo) {
								
				if(!observacoes.trim().equals("")) {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String novasObs = null;
					if(chooserData.getDate() != null)
						novasObs = "---- " + Facade.getInstance().getUsuarioLogado().getFuncionario().getNome() + " ("+  formato.format(new Date()) + "): \n" + observacoes +"\nPróximo Contato: "+formato.format(proximoContato) +"\n\n";
					else
						novasObs = "---- " + Facade.getInstance().getUsuarioLogado().getFuncionario().getNome() + " ("+  formato.format(new Date()) + "): \n" + observacoes +"\n\n";
					
					

					if(pauta.getObservacoes() != null)
						pauta.setObservacoes(novasObs + pauta.getObservacoes());
					else
						pauta.setObservacoes(novasObs);
				}
				else {
					JOptionPane.showMessageDialog(this, "Por favor, informe em Observações o motivo da mudança de Status", "ERRO", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
			else {*/
				if(!observacoes.trim().equals("")) {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String novasObs = null;
					if(chooserData.getDate() != null)
						novasObs = "---- " + Facade.getInstance().getUsuarioLogado().getFuncionario().getNome() + " ("+  formato.format(new Date()) + "): \n" + observacoes +"\nPróximo Contato: "+formato.format(proximoContato) +"\n\n";
					else
						novasObs = "---- " + Facade.getInstance().getUsuarioLogado().getFuncionario().getNome() + " ("+  formato.format(new Date()) + "): \n" + observacoes +"\n\n";
					if(pauta.getObservacoes() != null)
						pauta.setObservacoes(novasObs + pauta.getObservacoes());
					else
						pauta.setObservacoes(novasObs);
				}
			//}
			pauta.setProxContato(proximoContato);
			pauta.setStatus(statusNovo);
			Facade.getInstance().atualizarPauta(pauta);
			JOptionPane.showMessageDialog(null,"Registro atualizado com sucesso!");
			this.dispose();
			return;

		}
		else if(e.getSource() == botaoCancelar) {
			this.dispose();
			return;
		}
		
	}
}
