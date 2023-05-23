package br.com.sne.sistema.gui.equipamento;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.facade.Facade;

public class TelaParamQtdEquipamentosGrupo extends JFrame implements ActionListener{

	private JLabel labelTitulo = null;
	private JLabel labelPorGrupo = null;
	private JLabel labelImagem = null;
	private JLabel labelTodosGrupos = null;
	private JComboBox comboGrupos = null;
	private JButton botaoGerar = null;
	private JRadioButton radioPorGrupo = null;
	private JRadioButton radioTodos = null;
	
	
	//método construtor para gerar a janela com os dados já configurados
	public TelaParamQtdEquipamentosGrupo(){
		
		//configurando a janela
		setTitle("Qtd de Equipamentos por Grupo:");
		setSize(new Dimension(460, 225)); //ajusta o tamanho da janela, pode ser somente: setSize(12,12);
		//setLocation(340,240); //Ajusta a janela na tela
		setLocationRelativeTo(null); //para centralizar no meio da tela
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Encerra a operação quando fecha a janela
		setResizable(false); //Bloqueia o redimencionamento da janela pelo usuário
		
		//getContentPane().setBackground(Color.ORANGE);  //colocar um cor predefinida de fundo na janela
		//getContentPane().setBackground(new Color(180,200,150)); //colocar cor personalizada
		getContentPane().setLayout(null); //Layout manual, sem gerenciador
	
		//inicializando componentes da janela
		labelTitulo = new JLabel("Informe abaixo como deseja ser exibido o relatório:");
		labelPorGrupo  = new JLabel("Por Grupo:");
		labelTodosGrupos = new JLabel("Todos os Grupos:");
		ButtonGroup bgroup = new ButtonGroup();
		radioPorGrupo = new JRadioButton();
		radioTodos = new JRadioButton();
		bgroup.add(radioPorGrupo);
		bgroup.add(radioTodos);
		radioPorGrupo.setSelected(true);
		comboGrupos = new JComboBox();
		//adicionando itens no combobox
				//comboxGrupo.addItem("");
				for(Grupo grupoItem: Facade.getInstance().listarGrupos()){	
						comboGrupos.addItem(grupoItem); 
				}
				//configurando combobox para exibir apenas 5 linhas
				comboGrupos.setMaximumRowCount(5);
		botaoGerar = new JButton("Gerar Relatório");
		Icon imagem = new ImageIcon(getClass().getResource("/images/equipamento.png"));
		labelImagem = new JLabel(imagem);
		
		//posicionando os componentes na janela
		//objeto.setBounds(posicaoColuna,posicaoLinha,comprimentoDaLinha,alturaLinha);
		labelTitulo.setBounds(new Rectangle(65, 20, 400, 20));
		radioPorGrupo.setBounds(new Rectangle(65, 50, 20, 25));
		labelPorGrupo.setBounds(new Rectangle(90, 50, 100, 25));
		comboGrupos.setBounds(new Rectangle(65, 75, 320, 25));
		radioTodos.setBounds(new Rectangle(65, 110, 20, 25));
		labelTodosGrupos.setBounds(new Rectangle(90, 110, 100, 25));
		botaoGerar.setBounds(new Rectangle(150, 150, 140, 28));
		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		
		//setando tooltip
		botaoGerar.setToolTipText("Clique para Gerar o relatório!");
		
		//Fontes
		Font f1 = new Font("Arial",Font.BOLD,12);
		labelPorGrupo.setFont(f1);
		labelTodosGrupos.setFont(f1);
	
		//adicionando componete na janela
		getContentPane().add(labelTitulo);
		getContentPane().add(radioPorGrupo);
		getContentPane().add(labelPorGrupo);
		getContentPane().add(comboGrupos);
		getContentPane().add(radioTodos);
		getContentPane().add(labelTodosGrupos);
		getContentPane().add(botaoGerar);
		getContentPane().add(labelImagem);
	
		//adicionando componentes aos eventos
		botaoGerar.addActionListener(this);
		radioPorGrupo.addActionListener(this);
		radioTodos.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoGerar) {
			if(radioTodos.isSelected()){
					HashMap hm = new HashMap();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
				
					try {
						setVisible(false);
						java.sql.Connection c  = Facade.getInstance().getConnection();
						URL arquivo = getClass().getResource("/br/com/sne/sistema/report/quantitativoEquipamentosTodosGrupos2.jasper");  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
						c.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}else if(radioPorGrupo.isSelected()){
				Grupo grupoEscolhido = (Grupo) comboGrupos.getSelectedItem();
				
				HashMap hm = new HashMap();
				hm.put("id", grupoEscolhido.getId());
				hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
			
				try {
					setVisible(false);
					java.sql.Connection c  = Facade.getInstance().getConnection();
					URL arquivo = getClass().getResource("/br/com/sne/sistema/report/quantitativoEquipamentosPorGrupo.jasper");  
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
					JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
					JasperViewer.viewReport(impressao,false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if(e.getSource() == radioPorGrupo){
			comboGrupos.setEnabled(true);
		}
		
		if(e.getSource() == radioTodos){
			comboGrupos.setEnabled(false);
		}
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
