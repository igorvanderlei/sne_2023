package br.com.sne.sistema.gui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.contrato.TelaParamOSRelatorioContratos;
import br.com.sne.sistema.gui.contrato.TelaParamOSRelatorioContratosAditivos;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasOrdemServico;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasPagarPeriodoFornecedor;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasPagarPeriodoOpcaoPagamento;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasPagasPeriodoCentroCusto;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasPagasPeriodoFornecedor;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasPagasPeriodoOpcaoPagamento;
import br.com.sne.sistema.gui.despesa.TelaParamDespesasPeriodoCentroCusto;
import br.com.sne.sistema.gui.equipamento.TelaParamEquipamentosDescartados;
import br.com.sne.sistema.gui.equipamento.TelaParamEquipamentosDescartadosPeriodoMotivo;
import br.com.sne.sistema.gui.equipamento.TelaParamEquipeTecnicaPorEvento;
import br.com.sne.sistema.gui.equipamento.TelaParamEtiquetasCodigo;
import br.com.sne.sistema.gui.equipamento.TelaParamEtiquetasGrupo;
import br.com.sne.sistema.gui.equipamento.TelaParamEtiquetasRecurso;
import br.com.sne.sistema.gui.equipamento.TelaParamEventosDesmontarPorPeriodo;
import br.com.sne.sistema.gui.equipamento.TelaParamEventosMontarPorPeriodo;
import br.com.sne.sistema.gui.equipamento.TelaParamLocacoesPorPeriodo;
import br.com.sne.sistema.gui.equipamento.TelaParamQtdEquipamentosGrupo;
import br.com.sne.sistema.gui.equipamentosublocado.TelaParamDiariasSublocadosPeriodoEmpresa;
import br.com.sne.sistema.gui.equipamentosublocado.TelaParamEquipamentosASeremSublocadosPorPeriodo;
import br.com.sne.sistema.gui.equipamentosublocado.TelaParamQtdDiariasSublocadosPorPeriodo;
import br.com.sne.sistema.gui.equipamentosublocado.TelaParamSublocadoCodigo;
import br.com.sne.sistema.gui.freelancer.TelaParamFreelancersPorPeriodo;
import br.com.sne.sistema.gui.googlecalendar.TelaParamListarEventos;
import br.com.sne.sistema.gui.orcamento.TelaParamOrcamentos;
import br.com.sne.sistema.gui.orcamento.TelaParamOrcamentos2Assinaturas;
import br.com.sne.sistema.gui.orcamento.TelaParamOrcamentosAbertos;
import br.com.sne.sistema.gui.orcamento.TelaParamOrcamentosPerdidos;
import br.com.sne.sistema.gui.os.TelaParamEventosPeriodoLocal;
import br.com.sne.sistema.gui.os.TelaParamOSAprovadasPorPeriodo;
import br.com.sne.sistema.gui.os.TelaParamOSFaturada;
import br.com.sne.sistema.gui.os.TelaParamOSNaoFaturada;
import br.com.sne.sistema.gui.pauta.TelaParamPautasPorPeriodo;
import br.com.sne.sistema.gui.receita.TelaParamParcelasPagasPorClientePeriodo;
import br.com.sne.sistema.gui.receita.TelaParamParcelasReceberPorClientePeriodo;
import br.com.sne.sistema.gui.receita.TelaParamParcelasReceberPorVendedor;
import br.com.sne.sistema.gui.receita.TelaParamTotalVendasVendedorPeriodo;
import br.com.sne.sistema.gui.util.WindowFactory;
import br.com.sne.sistema.gui.util.components.SecureButton;
import br.com.sne.sistema.gui.util.components.SecureComponent;
import br.com.sne.sistema.gui.util.components.SecureMenuItem;

public class TelaPrincipal extends JFrame implements InterfaceTelaPrincipal {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelParaTesteBotoes = null;
	private JDesktopPane jDesktopPaneCentral = null;	
	private List<SecureComponent> botoesSeguros = new ArrayList<SecureComponent>();

	private SecureButton jButtonCliente = null;
	private SecureButton jButtonOrcamento = null;
	private SecureButton jButtonPagamento = null;
	private SecureButton jButtonFuncionario = null;
	private SecureButton jButtonOS = null;
	private SecureButton jButtonOSPassagem = null;
	private SecureButton jButtonEquiEnviado = null;
	private SecureButton jButtonDevolverEquipamento = null;	
	private SecureButton jButtonEquipamento = null;	
	
	
	private SecureMenuItem jButtonLogin = null;
	private SecureMenuItem jButtonSenha = null;
	private SecureMenuItem jButtonBloquear = null;	

	private JMenuBar barraMenuPrincipal = null;
	
	
	private JMenu menuRecurso = null;
	private SecureMenuItem menuCadastroGrupo = null;
	private SecureMenuItem menuCadastroRecurso = null;
	private SecureMenuItem menuCadastroEquipamento = null;
	private SecureMenuItem menuCadastroTerceirizado = null;
	private SecureMenuItem menuCadastroEquipamentoSublocado = null;
	private SecureMenuItem menuDevolucaoEquipamentoSublocado = null;
	
	


	private JMenu menuAdministracao = null;
	private SecureMenuItem menuTipoUsuario = null;
	private SecureMenuItem menuFornecedor = null;
	private SecureMenuItem menuFornecedorTerceirizado = null;
	private SecureMenuItem menuFuncionario = null;
	private SecureMenuItem menuFreelancer = null;
	private SecureMenuItem menuUsuario = null;
	private SecureMenuItem menuUnidade = null;
	private SecureMenuItem menuHistoricoCancelamento = null;
	private SecureMenuItem menuHistoricoEstorno = null;

	private SecureMenuItem menuLixeiraCliente;
	private SecureMenuItem menuLixeiraCentroCusto;
	private SecureMenuItem menuLixeiraGrupo;
	private SecureMenuItem menuLixeiraSubgrupo;
	private SecureMenuItem menuLixeiraEquipamento;
	private SecureMenuItem menuLixeiraOS;
	private SecureMenuItem menuLixeiraOrcamento;
	private SecureMenuItem menuLixeiraFornecedor;
	private SecureMenuItem menuLixeiraFuncionario;
	private SecureMenuItem menuLixeiraFreelancer;


	private JMenu menuVenda = null;
	private SecureMenuItem menuCliente = null;
	private SecureMenuItem menuOrcamento = null;
	private SecureMenuItem menuLocal = null;
	private SecureMenuItem menuOrdemServico = null;
	private SecureMenuItem menuOrdemServicoExtra = null;
	private SecureMenuItem menuComodato = null;
	private SecureMenuItem menuPauta = null;
	//private SecureMenuItem menuAdicionarEvento = null;
	private SecureMenuItem menuItemContrato = null;
	private SecureMenuItem menuItemContratoAditivo = null;

	private JMenu menuEstoque = null;
	private SecureMenuItem menuEnviarEquipamento = null;
	private SecureMenuItem menuRetornarEquipamento = null;
	private SecureMenuItem menuContagemEstoque = null;
	private SecureMenuItem menuRastreamento = null;
	private SecureMenuItem menuDescarte = null;
	private SecureMenuItem menuManutencaoPreventiva = null;
	private SecureMenuItem menuManutencaoCorretiva = null;
	private SecureMenuItem menuOrdemServicoEmergencial = null;
	private SecureMenuItem menuAgendarRecolhimento = null;
	
	private SecureMenuItem menuOSPassagem = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanelEsquerdo = null;
	private JPanel jPanelDireito = null;
	private JLabel logomarca = null;

	private JMenu menuFinanceiro = null;
	private SecureMenuItem menuCentroCusto = null;
	private SecureMenuItem menuCadastroDespesa = null;
	private SecureMenuItem menuFontePagadora = null;
	private SecureMenuItem menuContaReceber = null;

	//Relatorios
	private JMenu menuRelatorio = null;
	private JMenu menuEtiquetasEquipamento = null;
	private JMenu menuRelatoriosEquipamento = null;
	private JMenu menuRelatoriosFreelancer = null;
	private JMenu menuRelatoriosCliente = null;
	private JMenu menuRelatoriosSublocado = null;
	private JMenu menuRelatoriosOS = null;
	private SecureMenuItem menuItemClienteDesatualizado = null;
	private SecureMenuItem menuItemEquipamentoPorEquipamento = null;
	private SecureMenuItem menuItemEquipamentoPorGrupo = null;
	private SecureMenuItem menuItemEquipamentoPorRecurso = null;
	private SecureMenuItem menuItemSublocado = null;
	private SecureMenuItem menuItemLocacoesPorPeriodo = null;
	private SecureMenuItem menuItemQuantitativoPorGrupo = null;
	private SecureMenuItem menuItemEventosEquipamentosPendentes = null;
	private SecureMenuItem menuItemEventosDesmontagemAtraso = null;
	private SecureMenuItem menuItemEquipeTecnicaPorEvento = null;
	private SecureMenuItem menuItemEventosDesmontarPorPeriodo = null;
	private SecureMenuItem menuItemEventosMontarPorPeriodo = null;
	private SecureMenuItem menuItemControleEquipamentosDescartados = null;
	private SecureMenuItem menuItemFreelancerPorPeriodo = null;
	private SecureMenuItem menuItemQtdDiariasSublocadosPorPeriodo = null;
	private SecureMenuItem menuItemDiariasSublocadosPeriodoEmpresa = null;
	private SecureMenuItem menuItemEquipamentosDescartadosPeriodoMotivo = null;
	private SecureMenuItem menuItemEquipamentosASeremSublocadosPeriodo  = null;
	private SecureMenuItem menuItemParcelasReceberPorClientePeriodo = null;
	private SecureMenuItem menuItemParcelasPagasPorClientePeriodo = null;
	private SecureMenuItem menuItemDespesasPagarPorPeriodoFornecedor = null;
	private SecureMenuItem menuItemDespesasPagarPorPeriodoOpcao = null;
	private SecureMenuItem menuItemDespesasPagasPorPeriodoFornecedor = null;
	private SecureMenuItem menuItemDespesasPagasPorPeriodoOpcao = null;
	private SecureMenuItem menuItemDespesasPagasPorPeriodoCentroCusto = null;
	private SecureMenuItem menuItemDespesasLancadasPorPeriodoCentroCusto = null;
	private SecureMenuItem menuItemOSAprovadasPorPeriodo = null;
	private SecureMenuItem menuItemListarEventos = null;
	private SecureMenuItem menuItemTotalVendasVendedorPeriodo = null;
	private SecureMenuItem menuItemOSComFaturamento = null;
	private SecureMenuItem menuItemRelatorioContratos = null;
	private SecureMenuItem menuItemRelatorioContratosAditivos = null;
	private SecureMenuItem menuItemOrcamentosAbertos = null;
	private SecureMenuItem menuItemOrcamentos2Assin = null;
	private SecureMenuItem menuItemPautasPeriodo = null;

	private SecureMenuItem menuItemOSSemFaturamento = null;
	private SecureMenuItem menuItemParcelasReceberPorVendedor = null;
	private SecureMenuItem menuItemOrcamentosPerdidos = null;
	private SecureMenuItem menuItemOSNaoFaturada = null;
	private SecureMenuItem menuItemDespesaOS = null;
	private SecureMenuItem menuItemOrcamentosDemaisValores = null;
	private SecureMenuItem menuItemEventosPorPeriodoLocalEvento = null;
	
	public TelaPrincipal() {
		super();
	//	excluirTokens();
		initialize();
	//	carregarTokens();
		Facade.getInstance().setTelaPrincipal(this);
		configurarPermissoesSegurancaBotoes();
		desabilitaBotoes();
	}
	/*	
	private void carregarTokens() {
		//aqui
		Usuario user = Facade.getInstance().getUsuarioLogado();
		if(user != null)
			Usuario.salvarToken(user.getTokenGoogle());
	}
	
	private void deletarPasta(File file) throws IOException{
		if(file.isDirectory()){  
       		//directory is empty, then delete it
       		if(file.list().length==0)
       		   file.delete();     			
       		else{
       		   //list all the directory contents
           	   String files[] = file.list();
        
           	   for (String temp : files) {
           	      //construct the file structure
           	      File fileDelete = new File(file, temp);
           	      //recursive delete
           	      deletarPasta(fileDelete);
           	   }
           		
           	   //check the directory again, if empty then delete it
           	   if(file.list().length==0)
              	     file.delete();
           	   
       		}
       		
       	}else{
       		//if file, then delete it
       		file.delete();  
       	}
	}
	
	private void excluirTokens() {
		File file = new File("tokens");
		if(!file.exists()){
           System.out.println("O diretório dos tokens não existe.");
        }
		else{
           try{
        	   deletarPasta(file);
           }catch(IOException e){
               e.printStackTrace();
           }
        }
	}*/

	private void initialize() {
		this.setSize(1241, 776);
		this.setExtendedState(6);
		this.setJMenuBar(getBarraMenuPrincipal());
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Grupo SNE");		
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add((new ImageIcon(getClass().getResource("/images/icon_sne_24.png"))).getImage());
		icons.add((new ImageIcon(getClass().getResource("/images/icon_sne_48.png"))).getImage());
		icons.add((new ImageIcon(getClass().getResource("/images/icon_sne_96.png"))).getImage());
		this.setIconImages(icons);
		
		setVisible(true);
		this.addComponentListener(new java.awt.event.ComponentAdapter() { 
			public void componentResized(ComponentEvent e) { 
				if(jPanelDireito != null) {
					Dimension d = jPanelDireito.getSize();
					WindowFactory.redimensionarJanelas(d);
				}
			//	if(logomarca != null)
					//logomarca.setSize((int) getSize().getWidth(), (int) getSize().getHeight());
			}
		});

	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BoxLayout(getJContentPane(), BoxLayout.X_AXIS));
			jContentPane.setSize(new Dimension(1085, 768));
			jContentPane.add(getJPanelDireito(), null);
			//jContentPane.add(getJPanelDireito(), null);

		}
		return jContentPane;
	}



	private JPanel getJPanelDireito() {
		if (jPanelDireito == null) {
			jPanelDireito = new JPanel();
			jPanelDireito.setLayout(new CardLayout());
			jPanelDireito.add(getDesktop(), getDesktop().getName());
		}
		return jPanelDireito;
	}

	public JLabel getLogomarca() {
		return logomarca;
	}

	public JDesktopPane getDesktop() {
		if (jDesktopPaneCentral == null) {
			logomarca = new JLabel();
			logomarca.setBounds(new Rectangle(0, 0, 600, 590));
			logomarca.setSize((int) this.getSize().getWidth(), (int) this.getSize().getHeight());
			logomarca.setBackground(new Color(238, 83, 238));
			logomarca.setHorizontalAlignment(SwingConstants.CENTER);
			logomarca.setHorizontalTextPosition(SwingConstants.CENTER);
			logomarca.setIcon(new ImageIcon(getClass().getResource("/images/logomarca.jpg")));
			jDesktopPaneCentral = new JDesktopPane();
			jDesktopPaneCentral.setLayout(new BorderLayout());
			jDesktopPaneCentral.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
			jDesktopPaneCentral.setBackground(Color.WHITE);
			jDesktopPaneCentral.setName("jDesktopPaneCentral");
			jDesktopPaneCentral.add(logomarca, BorderLayout.CENTER);




		}
		return jDesktopPaneCentral;
	}


	/* ----------------------------------------
	 * Métodos de Inicialização dos Botões ---*
	 * ---------------------------------------*/
	private JButton getJButtonEquipamento() {
		if (jButtonEquipamento == null) {
			ImageIcon imgEqui = new ImageIcon(getClass().getResource("/images/equipamento.png"));
			jButtonEquipamento = new SecureButton("<html><b>Equipamento</b></html>",imgEqui);

			jButtonEquipamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEquip = WindowFactory.createTelaEquipamento(jDesktopPaneCentral);
					telaEquip.setVisible(true);
				}
			});
		}
		return jButtonEquipamento;
	}

	private JButton getJButtonOrcamento() {
		if (jButtonOrcamento == null) {
			ImageIcon image = new ImageIcon(getClass().getResource("/images/orcamento_48.png"));
			jButtonOrcamento = new SecureButton("<html><b>Or�amento</b></html>",image);

			jButtonOrcamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOrcamento = WindowFactory.createTelaOrcamento(jDesktopPaneCentral);
					telaOrcamento.setVisible(true);
				}
			});
		}
		return jButtonOrcamento;
	}

	private JButton getJButtonCliente() {
		if (jButtonCliente == null) {
			ImageIcon imageCli = new ImageIcon(getClass().getResource("/images/cliente_48.png"));
			jButtonCliente = new SecureButton("<html><b>Cliente</b></html>",imageCli);

			jButtonCliente.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaCliente = WindowFactory.createTelaCliente(jDesktopPaneCentral);
					telaCliente.setVisible(true);
				}
			});
		}
		return jButtonCliente;
	}

	private JButton getJButtonFuncionario() {
		if (jButtonFuncionario == null) {
			ImageIcon imgFun = new ImageIcon(getClass().getResource("/images/funcionario_48.png"));
			jButtonFuncionario = new SecureButton("<html><b>Funcionário</b></html>",imgFun);
			jButtonFuncionario.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaFuncionario = WindowFactory.createTelaFuncionario(jDesktopPaneCentral);
					telaFuncionario.setVisible(true);
				}
			});
		}
		return jButtonFuncionario;
	}

	private JButton getJButtonOS() {
		if (jButtonOS == null) {
			ImageIcon imgOS = new ImageIcon(getClass().getResource("/images/equip_48.png"));
			jButtonOS = new SecureButton("<html><b>Ordem de Servi�o</b></html>",imgOS);

			jButtonOS.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOS = WindowFactory.createTelaOS(jDesktopPaneCentral);
					telaOS.setVisible(true);
				}
			});
		}
		return jButtonOS;
	}

	private JButton getJButtonOSPassagem() {
		if (jButtonOSPassagem == null) {
			ImageIcon imgOS = new ImageIcon(getClass().getResource("/images/ospassagem_48.png"));
			jButtonOSPassagem = new SecureButton("<html><b>OS de Passagem</b></html>",imgOS);

			jButtonOSPassagem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOS = WindowFactory.createTelaOSPassagem(jDesktopPaneCentral);
					telaOS.setVisible(true);
				}
			});
		}
		return jButtonOSPassagem;
	}

	private JButton getJButtonEquiEnviado() {
		if (jButtonEquiEnviado == null) {
			ImageIcon imgE = new ImageIcon(getClass().getResource("/images/Mes Videos 48.png"));
			jButtonEquiEnviado = new SecureButton("<html><b>Enviar Equipamento</b></html>",imgE);

			jButtonEquiEnviado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEquipamentoEnviado = WindowFactory.createTelaEquipamentoEnviado(jDesktopPaneCentral);
					telaEquipamentoEnviado.setVisible(true);
				}
			});
		}
		return jButtonEquiEnviado;
	}

	private SecureMenuItem getJButtonLogin() {
		if (jButtonLogin == null) {
			ImageIcon imgSair = new ImageIcon(getClass().getResource("/images/icon_lock_24.png"));
			jButtonLogin = new SecureMenuItem();
			jButtonLogin.setText("<html><b>Login / Logout</b></html>");
			jButtonLogin.setIcon(imgSair);
			jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Facade.getInstance().setUsuarioLogado(null);
					desabilitaBotoes();
					WindowFactory.fecharTodasJanelas();
				}
			});

		}
		return jButtonLogin;
	}

	private SecureMenuItem getJButtonSenha() {
		if (jButtonSenha == null) {
			ImageIcon imgSair = new ImageIcon(getClass().getResource("/images/icon_password_24.png"));
			jButtonSenha = new SecureMenuItem();
			jButtonSenha.setText("<html><b>Alterar Senha</b></html>");
			jButtonSenha.setIcon(imgSair);

			jButtonSenha.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(Facade.getInstance().getUsuarioLogado() != null) {
						TelaTrocarSenha ts = new TelaTrocarSenha(TelaPrincipal.this);
						ts.setVisible(true);
					}
				}
			});
		}
		return jButtonSenha;
	}

	private SecureMenuItem getJButtonBloquear() {
		if (jButtonBloquear == null) {
			ImageIcon imgSair = new ImageIcon(getClass().getResource("/images/icon_bloquear_24.png"));
			jButtonBloquear = new SecureMenuItem();
			jButtonBloquear.setText("<html><b>Bloquear o Sistema</b></html>");
			jButtonBloquear.setIcon(imgSair);

			jButtonBloquear.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(Facade.getInstance().getUsuarioLogado() != null) {
						TelaBloquear lock = new TelaBloquear(TelaPrincipal.this);
						lock.setVisible(true);
					}
				}
			});
		}
		return jButtonBloquear;
	}
	private JButton getJButtonDevolverEquipamento() {
		if (jButtonDevolverEquipamento == null) {
			ImageIcon imgFuncoes = new ImageIcon(getClass().getResource("/images/retorno_48.png"));
			jButtonDevolverEquipamento = new SecureButton("<html><b>Retorno de Equipamento</b></html>",imgFuncoes);

			jButtonDevolverEquipamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaDevolucao = WindowFactory.createTelaDevolucao(jDesktopPaneCentral);
					telaDevolucao.setVisible(true);
				}
			});
		}
		return jButtonDevolverEquipamento;
	}	

	private JButton getJButtonPagamento() {
		if (jButtonPagamento == null) {
			ImageIcon img = new ImageIcon(getClass().getResource("/images/receber_48.png"));
			jButtonPagamento = new SecureButton("<html><b>Libera��o de OS</b></html>",img);

			jButtonPagamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaDescricaoEquipamento = WindowFactory.createTelaCadastroReceita(jDesktopPaneCentral);
					telaDescricaoEquipamento.setVisible(true);
				}
			});
		}
		return jButtonPagamento;
	}	

	private JMenuBar getBarraMenuPrincipal() {
		if (barraMenuPrincipal == null) {
			barraMenuPrincipal = new JMenuBar();
			barraMenuPrincipal.add(getMenuVenda());

			
			JMenu sep = new JMenu("|");
			sep.setEnabled(false);
			barraMenuPrincipal.add(sep);
			
			barraMenuPrincipal.add(getMenuFinanceiro());
			
			sep = new JMenu("|");
			sep.setEnabled(false);
			barraMenuPrincipal.add(sep);
			barraMenuPrincipal.add(getMenuRecurso());
			
			sep = new JMenu("|");
			sep.setEnabled(false);
			barraMenuPrincipal.add(sep);
			barraMenuPrincipal.add(getMenuEstoque());

			
			sep = new JMenu("|");
			sep.setEnabled(false);
			barraMenuPrincipal.add(sep);
			barraMenuPrincipal.add(getMenuAdministracao());			
			
			sep = new JMenu("|");
			sep.setEnabled(false);
			barraMenuPrincipal.add(sep);
			barraMenuPrincipal.add(getMenuRelatorio());
			
			barraMenuPrincipal.add(Box.createHorizontalGlue());
			JMenu menuUsuario =new JMenu("Usuário");
			menuUsuario.setIcon(new ImageIcon(getClass().getResource("/images/icon_account_18.png")));

			menuUsuario.add(getJButtonSenha());			
			menuUsuario.add(getJButtonLogin());
			menuUsuario.add(getJButtonBloquear());		
			barraMenuPrincipal.add(menuUsuario);
			
			
			//todo

		}
		return barraMenuPrincipal;
	}

	private JMenu getMenuRecurso() {
		if (menuRecurso == null) {
			menuRecurso = new JMenu();
			menuRecurso.setText("Recursos");
			menuRecurso.add(getMenuCadastroGrupo());
			menuRecurso.add(getMenuCadastroRecurso());
			menuRecurso.add(getMenuCadastroEquipamento());
			menuRecurso.add(getMenuTerceirizado());
			menuRecurso.add(getMenuCadastroEquipamentoSublocado());
			menuRecurso.add(getMenuDevolucaoEquipamentoSublocado());
			menuRecurso.setIcon(new ImageIcon(getClass().getResource("/images/icon_recurso_18c.png")));
		}
		return menuRecurso;
	}

	private SecureMenuItem getMenuCadastroGrupo() {
		if (menuCadastroGrupo == null) {
			menuCadastroGrupo = new SecureMenuItem();
			menuCadastroGrupo.setText("Grupos");
			menuCadastroGrupo.setIcon(new ImageIcon(getClass().getResource("/images/icon_grupo_24.png")));
			menuCadastroGrupo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaGrupo = WindowFactory.createTelaGrupo(jDesktopPaneCentral);
					telaGrupo.setVisible(true);
				}
			});
		}
		return menuCadastroGrupo;
	}

	private SecureMenuItem getMenuCadastroRecurso() {
		if (menuCadastroRecurso == null) {
			menuCadastroRecurso = new SecureMenuItem();
			menuCadastroRecurso.setText("Subgrupos");
			menuCadastroRecurso.setIcon(new ImageIcon(getClass().getResource("/images/icon_subgrupo_24.png")));
			menuCadastroRecurso.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaRecurso = WindowFactory.createTelaDescricaoEquipamento(jDesktopPaneCentral);
					telaRecurso.setVisible(true);
				}
			});
		}
		return menuCadastroRecurso;
	}

	private SecureMenuItem getMenuCadastroEquipamento() {
		if (menuCadastroEquipamento == null) {
			menuCadastroEquipamento = new SecureMenuItem();
			menuCadastroEquipamento.setText("Equipamentos");
			menuCadastroEquipamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_equipamento_24.png")));
			menuCadastroEquipamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEquipamento = WindowFactory.createTelaEquipamento(jDesktopPaneCentral);
					telaEquipamento.setVisible(true);
				}
			});
		}
		return menuCadastroEquipamento;
	}	

	private SecureMenuItem getMenuTerceirizado() {
		if (menuCadastroTerceirizado == null) {
			menuCadastroTerceirizado = new SecureMenuItem();
			menuCadastroTerceirizado.setText("Recursos Terceirizados");
			menuCadastroTerceirizado.setIcon(new ImageIcon(getClass().getResource("/images/icon_terceirizado_24.png")));
			menuCadastroTerceirizado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEquipamento = WindowFactory.createTelaRecursoTerceirizado(jDesktopPaneCentral);
					telaEquipamento.setVisible(true);
				}
			});
		}
		return menuCadastroTerceirizado;
	}	
	
	
	private SecureMenuItem getMenuCadastroEquipamentoSublocado() {
		if (menuCadastroEquipamentoSublocado == null) {
			menuCadastroEquipamentoSublocado = new SecureMenuItem();
			menuCadastroEquipamentoSublocado.setText("Registro de Sublocados");
			menuCadastroEquipamentoSublocado.setIcon(new ImageIcon(getClass().getResource("/images/icon_sublocado_24.png")));
			menuCadastroEquipamentoSublocado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEquipamentoSublocado = WindowFactory.createTelaEquipamentoSublocado(jDesktopPaneCentral);
					telaEquipamentoSublocado.setVisible(true);
				}
			});
		}
		return menuCadastroEquipamentoSublocado;
	}	

	private SecureMenuItem getMenuDevolucaoEquipamentoSublocado() {
		if (menuDevolucaoEquipamentoSublocado == null) {
			menuDevolucaoEquipamentoSublocado = new SecureMenuItem();
			menuDevolucaoEquipamentoSublocado.setText("Devolução de Sublocados");
			menuDevolucaoEquipamentoSublocado.setIcon(new ImageIcon(getClass().getResource("/images/icon_dsublocado_24.png")));
			menuDevolucaoEquipamentoSublocado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEquipamentoSublocado = WindowFactory.createTelaDevolucaoSublocados(jDesktopPaneCentral);
					telaEquipamentoSublocado.setVisible(true);
				}
			});
		}
		return menuDevolucaoEquipamentoSublocado;
	}



	private JMenu getMenuAdministracao() {
		if (menuAdministracao == null) {
			menuAdministracao = new JMenu();
			menuAdministracao.setText("Administração");

			menuAdministracao.add(getMenuFuncionario());
			menuAdministracao.add(getMenuTipoUsuario());
			menuAdministracao.add(getMenuUsuario());
			
			menuAdministracao.add(new JSeparator());
			
			menuAdministracao.add(getMenuHistoricoCancelamento());
			menuAdministracao.add(getMenuHistoricoEstorno());
			menuAdministracao.add(getMenuLixeira());
			
			menuAdministracao.add(new JSeparator());
			
			
			menuAdministracao.add(getMenuUnidade());
			menuAdministracao.add(getMenuFornecedor());
			menuAdministracao.add(getMenuFornecedorTerceirizado());
			menuAdministracao.add(getMenuFreelancer());
			
			
			
			menuAdministracao.setIcon(new ImageIcon(getClass().getResource("/images/icon_adm_18c.png")));
		}
		return menuAdministracao;
	}

	private JMenu getMenuLixeira() {
		JMenu menuLixeira = new JMenu("Lixeira");
		menuLixeira.setIcon(new ImageIcon(getClass().getResource("/images/icon_trash_24.png")));
		menuLixeira.add(getMenuLixeiraCliente());
		menuLixeira.add(getMenuLixeiraCentroCusto());
		menuLixeira.add(getMenuLixeiraGrupo());
		menuLixeira.add(getMenuLixeiraSubgrupo());
		menuLixeira.add(getMenuLixeiraEquipamento());
		menuLixeira.add(getMenuLixeiraOrcamento());
		menuLixeira.add(getMenuLixeiraOS());
		menuLixeira.add(getMenuLixeiraFornecedor());
		menuLixeira.add(getMenuLixeiraFuncionario());
		menuLixeira.add(getMenuLixeiraFreelancer());
		return menuLixeira;
	}

	private SecureMenuItem getMenuLixeiraCliente() {
		menuLixeiraCliente = new SecureMenuItem();
		menuLixeiraCliente.setText("Clientes");
		menuLixeiraCliente.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraCliente(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraCliente;
	}

	private SecureMenuItem getMenuLixeiraCentroCusto() {
		menuLixeiraCentroCusto = new SecureMenuItem();
		menuLixeiraCentroCusto.setText("Centro de Custo");
		menuLixeiraCentroCusto.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraCentroCusto(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraCentroCusto;
	}

	private SecureMenuItem getMenuLixeiraFuncionario() {
		menuLixeiraFuncionario = new SecureMenuItem();
		menuLixeiraFuncionario.setText("Funcionarios");
		menuLixeiraFuncionario.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraFuncionario(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraFuncionario;
	}

	private SecureMenuItem getMenuLixeiraFornecedor() {
		menuLixeiraFornecedor = new SecureMenuItem();
		menuLixeiraFornecedor.setText("Fornecedors");
		menuLixeiraFornecedor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraFornecedor(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraFornecedor;
	}

	private SecureMenuItem getMenuLixeiraFreelancer() {
		menuLixeiraFreelancer = new SecureMenuItem();
		menuLixeiraFreelancer.setText("Freelancers");
		menuLixeiraFreelancer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraFreelancer(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraFreelancer;
	}

	private SecureMenuItem getMenuLixeiraGrupo() {
		menuLixeiraGrupo = new SecureMenuItem();
		menuLixeiraGrupo.setText("Grupos");
		menuLixeiraGrupo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraGrupo(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraGrupo;
	}

	private SecureMenuItem getMenuLixeiraSubgrupo() {
		menuLixeiraSubgrupo = new SecureMenuItem();
		menuLixeiraSubgrupo.setText("Subgrupos");
		menuLixeiraSubgrupo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraSubgrupo(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraSubgrupo;
	}

	private SecureMenuItem getMenuLixeiraEquipamento() {
		menuLixeiraEquipamento = new SecureMenuItem();
		menuLixeiraEquipamento.setText("Equipamentos");
		menuLixeiraEquipamento.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraEquipamento(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraEquipamento;
	}	

	private SecureMenuItem getMenuLixeiraOS() {
		menuLixeiraOS = new SecureMenuItem();
		menuLixeiraOS.setText("Ordens Serviço");
		menuLixeiraOS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraOS(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraOS;
	}	

	private SecureMenuItem getMenuLixeiraOrcamento() {
		menuLixeiraOrcamento = new SecureMenuItem();
		menuLixeiraOrcamento.setText("Orçamentos");
		menuLixeiraOrcamento.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JInternalFrame telaTipoUsuario = WindowFactory.createTelaLixeiraOrcamento(jDesktopPaneCentral);
				telaTipoUsuario.setVisible(true);
			}
		});
		return menuLixeiraOrcamento;
	}	

	private SecureMenuItem getMenuTipoUsuario() {
		if (menuTipoUsuario == null) {
			menuTipoUsuario = new SecureMenuItem();
			menuTipoUsuario.setText("Tipos de Usuário");
			menuTipoUsuario.setIcon(new ImageIcon(getClass().getResource("/images/icon_tusuario_24.png")));
			menuTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaTipoUsuario = WindowFactory.createTelaTipoUsuario(jDesktopPaneCentral);
					telaTipoUsuario.setVisible(true);
				}
			});
		}
		return menuTipoUsuario;
	}

	private SecureMenuItem getMenuFornecedor() {
		if (menuFornecedor == null) {
			menuFornecedor = new SecureMenuItem();
			menuFornecedor.setText("Fornecedores");
			menuFornecedor.setIcon(new ImageIcon(getClass().getResource("/images/icon_fornecedor_24.png")));
			menuFornecedor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaFornecedor = WindowFactory.createTelaFornecedor(jDesktopPaneCentral);
					telaFornecedor.setVisible(true);
				}
			});
		}
		return menuFornecedor;
	}
	
	
	private SecureMenuItem getMenuFornecedorTerceirizado() {
		if (menuFornecedorTerceirizado == null) {
			menuFornecedorTerceirizado = new SecureMenuItem();
			menuFornecedorTerceirizado.setText("Fornecedores Terceirizados");
			menuFornecedorTerceirizado.setIcon(new ImageIcon(getClass().getResource("/images/icon_fterceirizado_24.png")));
			menuFornecedorTerceirizado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaFornecedor = WindowFactory.createTelaFornecedorTerceirizado(jDesktopPaneCentral);
					telaFornecedor.setVisible(true);
				}
			});
		}
		return menuFornecedorTerceirizado;
	}

	private SecureMenuItem getMenuHistoricoCancelamento() {
		if (menuHistoricoCancelamento == null) {
			menuHistoricoCancelamento = new SecureMenuItem();
			menuHistoricoCancelamento.setText("Histórico de Cancelamentos");
			menuHistoricoCancelamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_historico_24.png")));
			menuHistoricoCancelamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaHistoricoCancelamento = WindowFactory.createTelaHistoricoCancelamento(jDesktopPaneCentral);
					telaHistoricoCancelamento.setVisible(true);
				}
			});
		}
		return menuHistoricoCancelamento;
	}	

	private SecureMenuItem getMenuHistoricoEstorno() {
		if (menuHistoricoEstorno == null) {
			menuHistoricoEstorno = new SecureMenuItem();
			menuHistoricoEstorno.setText("Histórico de Estornos");
			menuHistoricoEstorno.setIcon(new ImageIcon(getClass().getResource("/images/icon_hestorno_24.png")));
			menuHistoricoEstorno.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaHistoricoEstorno = WindowFactory.createTelaHistoricoEstorno(jDesktopPaneCentral);
					telaHistoricoEstorno.setVisible(true);
				}
			});
		}
		return menuHistoricoEstorno;
	}

	private SecureMenuItem getMenuFuncionario() {
		if (menuFuncionario == null) {
			menuFuncionario = new SecureMenuItem();
			menuFuncionario.setText("Funcionários");
			menuFuncionario.setIcon(new ImageIcon(getClass().getResource("/images/icon_funcionario_24.png")));
			menuFuncionario.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaFuncionario = WindowFactory.createTelaFuncionario(jDesktopPaneCentral);
					telaFuncionario.setVisible(true);
				}
			});
		}
		return menuFuncionario;
	}

	private SecureMenuItem getMenuFreelancer() {
		if (menuFreelancer == null) {
			menuFreelancer = new SecureMenuItem();
			menuFreelancer.setText("Cadastro de Freelancers");
			menuFreelancer.setIcon(new ImageIcon(getClass().getResource("/images/icon_freelancer_24.png")));
			menuFreelancer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaFreelancer = WindowFactory.createTelaFreelancer(jDesktopPaneCentral);
					telaFreelancer.setVisible(true);
				}
			});
		}
		return menuFreelancer;
	}

	private SecureMenuItem getMenuUsuario() {
		if (menuUsuario == null) {
			menuUsuario = new SecureMenuItem();
			menuUsuario.setText("Usuários");
			menuUsuario.setIcon(new ImageIcon(getClass().getResource("/images/icon_usuario_24.png")));
			menuUsuario.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telausuario = WindowFactory.createTelaUsuario(jDesktopPaneCentral);
					telausuario.setVisible(true);
				}
			});
		}
		return menuUsuario;
	}

	private SecureMenuItem getMenuUnidade() {
		if (menuUnidade == null) {
			menuUnidade = new SecureMenuItem();
			menuUnidade.setText("Unidades Empresariais");
			menuUnidade.setIcon(new ImageIcon(getClass().getResource("/images/icon_unidade_24.png")));
			menuUnidade.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaUnidade = WindowFactory.createTelaUnidade(jDesktopPaneCentral);
					telaUnidade.setVisible(true);
				}
			});
		}
		return menuUnidade;
	}


	private JMenu getMenuRelatorio() {
		if (menuRelatorio == null) {
			menuRelatorio = new JMenu("Relatórios");
			menuRelatorio.setIcon(new ImageIcon(getClass().getResource("/images/icon_report_18c.png")));

			menuRelatorio.add(getMenuRelatoriosEquipamento());
			menuRelatorio.add(getMenuRelatoriosSublocados());
			menuRelatorio.add(getMenuItemClienteDesatualizado());
			menuRelatorio.add(getMenuItemFreelancerPorPeriodo());
			menuRelatorio.add(getMenuItemOSAprovadasPorPeriodo());	
			menuRelatorio.add(getMenuItemListarEventos());

			JMenu etiquetas = new JMenu("Etiquetas");
			etiquetas.setIcon(new ImageIcon(getClass().getResource("/images/icon_etiqueta_24.png")));
			etiquetas.add(getMenuItemEquipamentoPorEquipamento());
			etiquetas.add(getMenuItemEquipamentoPorGrupo());
			etiquetas.add(getMenuItemEquipamentoPorRecurso());
			etiquetas.add(getMenuItemSublocado());
			


			JMenu financeiros = new JMenu("Financeiros");
			financeiros.setIcon(new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")));
			financeiros.add(getMenuItemParcelasReceberPorClientePeriodo());
			financeiros.add(getMenuItemParcelasPagasPorClientePeriodo());
			financeiros.add(getMenuItemOSNaoFaturada());
			financeiros.add(getMenuItemEventosPorPeriodoLocalEvento());
			financeiros.add(getMenuDespesaOS());
			financeiros.add(getMenuItemDespesasPagarPorPeriodoFornecedor());
			financeiros.add(getMenuItemDespesasPagasPorPeriodoFornecedor());
			financeiros.add(getMenuItemDespesasPagarPorPeriodoOpcao());
			financeiros.add(getMenuItemDespesasPagasPorPeriodoOpcao());
			financeiros.add(getMenuItemDespesasPagasPorPeriodoCentroCusto());
			financeiros.add(getMenuItemDespesasLancadasPorPeriodoCentroCusto());
			
		
			JMenu vendas = new JMenu("Vendas");
			vendas.setIcon(new ImageIcon(getClass().getResource("/images/icon_venda_24.png")));
			vendas.add(getMenuItemParcelasReceberPorVendedor());
			vendas.add(getMenuItemOrcamentosPerdidos());
			vendas.add(getMenuItemOrcamentosAbertos());
			vendas.add(getMenuItemOrcamentos2Assin());
			vendas.add(getMenuOrcamentosDemaisValores());
			vendas.add(getMenuItemTotalVendasVendedorPeriodo());
			vendas.add(getMenuItemOSComFaturamento());
			vendas.add(getMenuItemOSSemFaturamento());
			vendas.add(getMenuItemRelatorioContratos());
			vendas.add(getMenuItemRelatorioContratosAditivos());
			vendas.add(getMenuItemPautasPeriodo());
			
			menuRelatorio.add(etiquetas);
			menuRelatorio.add(financeiros);
			menuRelatorio.add(vendas);
		}
		return menuRelatorio;
	}

	private JMenu getMenuFinanceiro() {
		if(menuFinanceiro == null) {
			menuFinanceiro = new JMenu("Financeiro");
			menuFinanceiro.add(getMenuCentroCusto());
			menuFinanceiro.add(getMenuFontePagadora());
			menuFinanceiro.add(getMenuCadastroDespesa());
			
			menuFinanceiro.add(getMenuContasReceber());
			menuFinanceiro.setIcon(new ImageIcon(getClass().getResource("/images/icon_financeiro_18c.png")));
		}
		return menuFinanceiro;
	}

	private SecureMenuItem getMenuCentroCusto() {
		if (menuCentroCusto == null) {
			menuCentroCusto = new SecureMenuItem();
			menuCentroCusto.setText("Centros de Custo");
			menuCentroCusto.setIcon(new ImageIcon(getClass().getResource("/images/icon_ccusto_24.png")));
			menuCentroCusto.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaCentroCusto = WindowFactory.createTelaCentroCusto(jDesktopPaneCentral);
					telaCentroCusto.setVisible(true);
				}
			});
		}
		return menuCentroCusto;
	}

	private SecureMenuItem getMenuCadastroDespesa() {
		if (menuCadastroDespesa == null) {
			menuCadastroDespesa = new SecureMenuItem();
			menuCadastroDespesa.setText("Despesas");
			menuCadastroDespesa.setIcon(new ImageIcon(getClass().getResource("/images/icon_pagar_24.png")));
			menuCadastroDespesa.setAccelerator(KeyStroke.getKeyStroke('4', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuCadastroDespesa.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaCentroCusto = WindowFactory.createTelaCadastroDespesa(jDesktopPaneCentral);
					telaCentroCusto.setVisible(true);
				}
			});
		}
		return menuCadastroDespesa;		
	}

	private SecureMenuItem getMenuFontePagadora() {
		if (menuFontePagadora == null) {
			menuFontePagadora = new SecureMenuItem();
			menuFontePagadora.setText("Fontes Pagadoras");
			menuFontePagadora.setIcon(new ImageIcon(getClass().getResource("/images/icon_fpagadora_24.png")));
			menuFontePagadora.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaCentroCusto = WindowFactory.createTelaCadastroFontePagadora(jDesktopPaneCentral);
					telaCentroCusto.setVisible(true);
				}
			});
		}
		return menuFontePagadora;		
	}

	private SecureMenuItem getMenuContasReceber() {
		if (menuContaReceber == null) {
			menuContaReceber = new SecureMenuItem();
			menuContaReceber.setText("Contas a Receber");
			menuContaReceber.setAccelerator(KeyStroke.getKeyStroke('5', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuContaReceber.setIcon(new ImageIcon(getClass().getResource("/images/icon_receber_24.png")));
			menuContaReceber.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaCentroCusto = WindowFactory.createTelaContaReceber(jDesktopPaneCentral);
					telaCentroCusto.setVisible(true);
				}
			});
		}
		return menuContaReceber;		
	}

	private JMenu getMenuRelatoriosEquipamento(){
		if (menuRelatoriosEquipamento == null) {
			menuRelatoriosEquipamento = new JMenu("Equipamentos");
			menuRelatoriosEquipamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_equipamento_24.png")));
			menuRelatoriosEquipamento.add(getMenuItemLocacoesPorPeriodo());
			menuRelatoriosEquipamento.add(getMenuItemQuantitativoPorGrupo());
			menuRelatoriosEquipamento.add(getMenuItemEventosEquipamentosPendentes());
			menuRelatoriosEquipamento.add(getMenuItemEventosDesmontagemAtraso());
			menuRelatoriosEquipamento.add(getMenuItemEquipeTecnicaPorEvento());
			menuRelatoriosEquipamento.add(getMenuItemEventosMontarPorPeriodo());
			menuRelatoriosEquipamento.add(getMenuItemEventosDesmontarPorPeriodo());
			menuRelatoriosEquipamento.add(new JSeparator());
			menuRelatoriosEquipamento.add(getMenuItemControleEquipamentosDescartados());
			menuRelatoriosEquipamento.add(getMenuItemEquipamentosDescartadosPeriodoMotivo());

		}

		return menuRelatoriosEquipamento;
	}

	private JMenu getMenuRelatoriosSublocados(){
		if (menuRelatoriosSublocado == null) {
			menuRelatoriosSublocado = new JMenu("Equipamentos Sublocados");
			menuRelatoriosSublocado.setIcon(new ImageIcon(getClass().getResource("/images/icon_sublocado_24.png")));
			menuRelatoriosSublocado.add(getMenuItemQtdDiariasSublocadosPorPeriodo());
			menuRelatoriosSublocado.add(getMenuItemDiariasSublocadosPeriodoEmpresa());  
			menuRelatoriosSublocado.add(getMenuItemEquipamentosASeremSublocadosPeriodo());  
		}

		return menuRelatoriosSublocado;
	}



	private SecureMenuItem getMenuItemClienteDesatualizado(){
		if(menuItemClienteDesatualizado == null){
			menuItemClienteDesatualizado = new SecureMenuItem("Clientes desatualizados");
			menuItemClienteDesatualizado.setIcon(new ImageIcon(getClass().getResource("/images/icon_cliente_24.png")));
			menuItemClienteDesatualizado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					try {
						Connection c  = Facade.getInstance().getConnection() ;
						URL arquivo = getClass().getResource("/br/com/sne/sistema/report/clienteAtualizados.jasper");  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});	
		}
		return menuItemClienteDesatualizado;
	}


	private SecureMenuItem getMenuItemFreelancerPorPeriodo(){
		if(menuItemFreelancerPorPeriodo == null){
			menuItemFreelancerPorPeriodo = new SecureMenuItem("Freelancers por Período");
			menuItemFreelancerPorPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/icon_freelancer_24.png")));
			menuItemFreelancerPorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamFreelancersPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Freelancers por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_freelancer_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemFreelancerPorPeriodo;

	}

	private SecureMenuItem getMenuItemOSAprovadasPorPeriodo(){
		if(menuItemOSAprovadasPorPeriodo == null){
			menuItemOSAprovadasPorPeriodo = new SecureMenuItem("Ordens de Serviço Aprovadas por Período");
			menuItemOSAprovadasPorPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/icon_os_24.png")));
			menuItemOSAprovadasPorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOSAprovadasPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Ordens de Serviço Aprovadas por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_os_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemOSAprovadasPorPeriodo;

	}
	
	private SecureMenuItem getMenuItemListarEventos(){
		if(menuItemListarEventos == null){
			menuItemListarEventos = new SecureMenuItem("Criar Planilha de Eventos");
			menuItemListarEventos.setIcon(new ImageIcon(getClass().getResource("/images/icon_planilha_24.png")));
			menuItemListarEventos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamListarEventos();
					JDialog dialog = new JDialog(telaParametro, "Criação de Planilha de Eventos", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_planilha_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemListarEventos;

	}

	private SecureMenuItem getMenuItemQtdDiariasSublocadosPorPeriodo(){
		if(menuItemQtdDiariasSublocadosPorPeriodo == null){
			menuItemQtdDiariasSublocadosPorPeriodo = new SecureMenuItem("Quantitativo das Diárias de Sublocados por Período");
			//menuItemQtdDiariasSublocadosPorPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/equipamentos_sublocados_18.png")));
			menuItemQtdDiariasSublocadosPorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamQtdDiariasSublocadosPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório Quantitativo das Diárias de Sublocados por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamentos_sublocados_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemQtdDiariasSublocadosPorPeriodo;

	}

	private SecureMenuItem getMenuItemDiariasSublocadosPeriodoEmpresa(){
		if(menuItemDiariasSublocadosPeriodoEmpresa == null){
			menuItemDiariasSublocadosPeriodoEmpresa = new SecureMenuItem("Quantitativo das Diárias de Sublocados por Período e por Empresa");
			//menuItemDiariasSublocadosPeriodoEmpresa.setIcon(new ImageIcon(getClass().getResource("/images/equipamentos_sublocados_18.png")));
			menuItemDiariasSublocadosPeriodoEmpresa.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDiariasSublocadosPeriodoEmpresa();
					JDialog dialog = new JDialog(telaParametro, "Relatório Quantitativo das Diárias de Sublocados por Período e por Empresa", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamentos_sublocados_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true); 
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemDiariasSublocadosPeriodoEmpresa;

	}

	private SecureMenuItem getMenuItemEquipamentosASeremSublocadosPeriodo(){
		if(menuItemEquipamentosASeremSublocadosPeriodo == null){
			menuItemEquipamentosASeremSublocadosPeriodo = new SecureMenuItem("Equipamentos a Serem Sublocados por Período");
			//menuItemEquipamentosASeremSublocadosPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/equipamentos_sublocados_18.png")));
			menuItemEquipamentosASeremSublocadosPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEquipamentosASeremSublocadosPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório Equipamentos a Serem Sublocados por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamentos_sublocados_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true); 
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemEquipamentosASeremSublocadosPeriodo;

	}

	private SecureMenuItem getMenuItemLocacoesPorPeriodo(){
		if(menuItemLocacoesPorPeriodo == null){
			menuItemLocacoesPorPeriodo = new SecureMenuItem("Demonstrativo de Locações de Equipamentos por Período");
			//menuItemLocacoesPorPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemLocacoesPorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamLocacoesPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Locações por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamento_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemLocacoesPorPeriodo;

	}

	private SecureMenuItem getMenuItemQuantitativoPorGrupo(){
		if(menuItemQuantitativoPorGrupo == null){
			menuItemQuantitativoPorGrupo = new SecureMenuItem("Quantitativo de Equipamentos por Grupo");
			//menuItemQuantitativoPorGrupo.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemQuantitativoPorGrupo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamQtdEquipamentosGrupo();
					JDialog dialog = new JDialog(telaParametro, "Relatório Quantitativo de Equipamentos por Grupo", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamento_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemQuantitativoPorGrupo;

	}

	private SecureMenuItem getMenuItemEventosEquipamentosPendentes(){
		if(menuItemEventosEquipamentosPendentes == null){
			menuItemEventosEquipamentosPendentes = new SecureMenuItem("Eventos com Equipamentos Pendentes");
			//menuItemEventosEquipamentosPendentes.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemEventosEquipamentosPendentes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					try {
						Connection c  = Facade.getInstance().getConnection() ;
						URL arquivo = getClass().getResource("/br/com/nordesti/locav/report/eventosEquipamentosPendentes.jasper");  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});	
		}
		return menuItemEventosEquipamentosPendentes;
	}

	private SecureMenuItem getMenuItemOSNaoFaturada(){
		if(menuItemOSNaoFaturada == null){
			menuItemOSNaoFaturada = new SecureMenuItem("Ordens de Serviço Não Faturadas");
			menuItemOSNaoFaturada.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					hm.put("datafinal", new Date());
					try {
						Connection c  = Facade.getInstance().getConnection() ;
						URL arquivo = getClass().getResource("/br/com/nordesti/locav/report/osNaoFaturada.jasper");  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});	
		}
		return menuItemOSNaoFaturada;
	}
	
	private SecureMenuItem getMenuItemEventosPorPeriodoLocalEvento(){
		if(menuItemEventosPorPeriodoLocalEvento == null){
			menuItemEventosPorPeriodoLocalEvento = new SecureMenuItem("Eventos Por Período e Local de Evento");
			//menuItemDespesasPagasPorPeriodoFornecedor.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemEventosPorPeriodoLocalEvento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEventosPeriodoLocal();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Eventos Por Período e Local de Evento", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/hotel_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemEventosPorPeriodoLocalEvento;

	}
	
	private SecureMenuItem getMenuDespesaOS(){
		if(menuItemDespesaOS == null){
			menuItemDespesaOS = new SecureMenuItem("Despesas por Ordem de Serviço");
			menuItemDespesaOS.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasOrdemServico();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Despesas por Ordem de Serviço", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/pagar_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemDespesaOS;
	}

	private SecureMenuItem getMenuOrcamentosDemaisValores(){
		if(menuItemOrcamentosDemaisValores == null){
			menuItemOrcamentosDemaisValores = new SecureMenuItem("Orçamentos com demais valores");
			menuItemOrcamentosDemaisValores.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOrcamentos();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Orçamentos com Demais Valores", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/orcamento_18.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemOrcamentosDemaisValores;
	}


	private SecureMenuItem getMenuItemEventosDesmontagemAtraso(){
		if(menuItemEventosDesmontagemAtraso == null){
			menuItemEventosDesmontagemAtraso = new SecureMenuItem("Eventos com Desmontagem em Atraso");
			//menuItemEventosDesmontagemAtraso.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemEventosDesmontagemAtraso.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());
					try {
						Connection c  = Facade.getInstance().getConnection() ;
						URL arquivo = getClass().getResource("/br/com/nordesti/locav/report/eventosDesmontagemAtraso.jasper");  
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
						JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
						JasperViewer.viewReport(impressao,false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});	
		}
		return menuItemEventosDesmontagemAtraso;
	}

	private SecureMenuItem getMenuItemEquipeTecnicaPorEvento(){
		if(menuItemEquipeTecnicaPorEvento == null){
			menuItemEquipeTecnicaPorEvento = new SecureMenuItem("Equipe Técnica por Evento");
			//menuItemEquipeTecnicaPorEvento.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemEquipeTecnicaPorEvento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEquipeTecnicaPorEvento();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Equipe Técnica por Evento", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamento_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemEquipeTecnicaPorEvento;

	}

	private SecureMenuItem getMenuItemEventosMontarPorPeriodo(){
		if(menuItemEventosMontarPorPeriodo == null){
			menuItemEventosMontarPorPeriodo = new SecureMenuItem("Eventos a Montar por Período");
			//menuItemEventosMontarPorPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemEventosMontarPorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEventosMontarPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Eventos a Montar por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamento_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemEventosMontarPorPeriodo;

	}

	private SecureMenuItem getMenuItemEventosDesmontarPorPeriodo(){
		if(menuItemEventosDesmontarPorPeriodo == null){
			menuItemEventosDesmontarPorPeriodo = new SecureMenuItem("Eventos a Desmontar por Período");
			//menuItemEventosDesmontarPorPeriodo.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemEventosDesmontarPorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEventosDesmontarPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Eventos a Desmontar por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/equipamento_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemEventosDesmontarPorPeriodo;

	}

	private SecureMenuItem getMenuItemControleEquipamentosDescartados(){
		if(menuItemControleEquipamentosDescartados == null){
			menuItemControleEquipamentosDescartados = new SecureMenuItem("Controle de Equipamentos Descartados");
			//menuItemControleEquipamentosDescartados.setIcon(new ImageIcon(getClass().getResource("/images/lixo_18.png")));
			menuItemControleEquipamentosDescartados.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEquipamentosDescartados();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Controle de Equipamentos Descartados", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/lixo_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemControleEquipamentosDescartados;

	}

	private SecureMenuItem getMenuItemEquipamentosDescartadosPeriodoMotivo(){
		if(menuItemEquipamentosDescartadosPeriodoMotivo == null){
			menuItemEquipamentosDescartadosPeriodoMotivo = new SecureMenuItem("Equipamentos Descartados por Período e por Motivo");
			//menuItemEquipamentosDescartadosPeriodoMotivo.setIcon(new ImageIcon(getClass().getResource("/images/lixo_18.png")));
			menuItemEquipamentosDescartadosPeriodoMotivo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEquipamentosDescartadosPeriodoMotivo();
					JDialog dialog = new JDialog(telaParametro, "Relatório Equipamentos Descartados por Período e por Motivo", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/lixo_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemEquipamentosDescartadosPeriodoMotivo;

	}



	private SecureMenuItem getMenuItemEquipamentoPorEquipamento(){
		if(menuItemEquipamentoPorEquipamento == null){
			menuItemEquipamentoPorEquipamento = new SecureMenuItem("Por Equipamento");
			//menuItemEquipamentoPorEquipamento.setIcon(new ImageIcon(getClass().getResource("/images/equipamento_24.png")));
			menuItemEquipamentoPorEquipamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEtiquetasCodigo();
					JDialog dialog = new JDialog(telaParametro, "Etiqueta de Equipamento Por Código", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_etiqueta_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemEquipamentoPorEquipamento;

	}


	private SecureMenuItem getMenuItemEquipamentoPorGrupo(){
		if(menuItemEquipamentoPorGrupo == null){
			menuItemEquipamentoPorGrupo = new SecureMenuItem("Por Grupo");
			//menuItemEquipamentoPorGrupo.setIcon(new ImageIcon(getClass().getResource("/images/grupo_18.png")));
			menuItemEquipamentoPorGrupo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEtiquetasGrupo();
					JDialog dialog = new JDialog(telaParametro, "Etiquetas de Equipamentos Por Grupo", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_etiqueta_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true); 
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemEquipamentoPorGrupo;

	}

	private SecureMenuItem getMenuItemEquipamentoPorRecurso(){
		if(menuItemEquipamentoPorRecurso == null){
			menuItemEquipamentoPorRecurso = new SecureMenuItem("Por Subgrupo");
			//menuItemEquipamentoPorRecurso.setIcon(new ImageIcon(getClass().getResource("/images/subgroup_18.png")));
			menuItemEquipamentoPorRecurso.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamEtiquetasRecurso();
					JDialog dialog = new JDialog(telaParametro, "Etiquetas de Equipamentos por Subgrupos", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_etiqueta_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	

		}
		return menuItemEquipamentoPorRecurso;

	}

	private SecureMenuItem getMenuItemSublocado(){
		if(menuItemSublocado == null){
			menuItemSublocado = new SecureMenuItem("Por Sublocação");
			//menuItemSublocado.setIcon(new ImageIcon(getClass().getResource("/images/sublocado_18.png")));
			menuItemSublocado.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamSublocadoCodigo();
					JDialog dialog = new JDialog(telaParametro, "Etiquetas de Equipamentos por Sublocação", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_etiqueta_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemSublocado;
	}


	private SecureMenuItem getMenuItemParcelasReceberPorClientePeriodo(){
		if(menuItemParcelasReceberPorClientePeriodo == null){
			menuItemParcelasReceberPorClientePeriodo = new SecureMenuItem("Parcelas a Receber por Cliente");
			//menuItemParcelasReceberPorClientePeriodo.setIcon(new ImageIcon(getClass().getResource("/images/cliente_18.png")));
			menuItemParcelasReceberPorClientePeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamParcelasReceberPorClientePeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Parcelas a Receber por Cliente", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemParcelasReceberPorClientePeriodo;

	}

	private SecureMenuItem getMenuItemParcelasPagasPorClientePeriodo(){
		if(menuItemParcelasPagasPorClientePeriodo == null){
			menuItemParcelasPagasPorClientePeriodo = new SecureMenuItem("Parcelas Pagas por Cliente");
			//menuItemParcelasPagasPorClientePeriodo.setIcon(new ImageIcon(getClass().getResource("/images/cliente_18.png")));
			menuItemParcelasPagasPorClientePeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamParcelasPagasPorClientePeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Parcelas Pagas por Cliente", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemParcelasPagasPorClientePeriodo;

	}
	

	private SecureMenuItem getMenuItemDespesasPagarPorPeriodoFornecedor(){
		if(menuItemDespesasPagarPorPeriodoFornecedor == null){
			menuItemDespesasPagarPorPeriodoFornecedor = new SecureMenuItem("Despesas a Pagar por Período e por Fornecedor");
			//menuItemDespesasPagarPorPeriodoFornecedor.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemDespesasPagarPorPeriodoFornecedor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasPagarPeriodoFornecedor();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Despesas a Pagar por Período e por Fornecedor", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemDespesasPagarPorPeriodoFornecedor;

	}

	private SecureMenuItem getMenuItemDespesasPagasPorPeriodoFornecedor(){
		if(menuItemDespesasPagasPorPeriodoFornecedor == null){
			menuItemDespesasPagasPorPeriodoFornecedor = new SecureMenuItem("Despesas Pagas por Período e por Fornecedor");
			//menuItemDespesasPagasPorPeriodoFornecedor.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemDespesasPagasPorPeriodoFornecedor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasPagasPeriodoFornecedor();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Despesas Pagas por Período e por Fornecedor", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemDespesasPagasPorPeriodoFornecedor;

	}
	
	private SecureMenuItem getMenuItemDespesasPagarPorPeriodoOpcao(){
		if(menuItemDespesasPagarPorPeriodoOpcao == null){
			menuItemDespesasPagarPorPeriodoOpcao = new SecureMenuItem("Despesas a Pagar por Período e por Opção de Pagamento");
			//menuItemDespesasPagarPorPeriodoOpcao.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemDespesasPagarPorPeriodoOpcao.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasPagarPeriodoOpcaoPagamento();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Despesas a Pagar por Período e por Opção de Pagamento", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
 
				}
			});	
		}
		return menuItemDespesasPagarPorPeriodoOpcao;

	}
	
	
	
	private SecureMenuItem getMenuItemDespesasPagasPorPeriodoOpcao(){
		if(menuItemDespesasPagasPorPeriodoOpcao == null){
			menuItemDespesasPagasPorPeriodoOpcao = new SecureMenuItem("Despesas Pagas por Período e por Opção de Pagamento");
			//menuItemDespesasPagasPorPeriodoOpcao.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemDespesasPagasPorPeriodoOpcao.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasPagasPeriodoOpcaoPagamento();
					JDialog dialog = new JDialog(telaParametro, "Relatorio de Despesas Pagas por Periodo e por Opção de Pagamento", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);  
				}
			});	
		}
		return menuItemDespesasPagasPorPeriodoOpcao;

	}
	
	
	private SecureMenuItem getMenuItemDespesasLancadasPorPeriodoCentroCusto(){
		if(menuItemDespesasPagasPorPeriodoCentroCusto == null){
			menuItemDespesasPagasPorPeriodoCentroCusto = new SecureMenuItem("Despesas Lançadas por Período e por Centro de Custo");
			//menuItemDespesasPagasPorPeriodoCentroCusto.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemDespesasPagasPorPeriodoCentroCusto.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasPeriodoCentroCusto();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Despesas Lançadas por Período e por Centro de Custo", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemDespesasPagasPorPeriodoCentroCusto;

	}

	private SecureMenuItem getMenuItemDespesasPagasPorPeriodoCentroCusto(){
		if(menuItemDespesasLancadasPorPeriodoCentroCusto == null){
			menuItemDespesasLancadasPorPeriodoCentroCusto = new SecureMenuItem("Despesas Pagas por Período e por Centro de Custo");
			//menuItemDespesasLancadasPorPeriodoCentroCusto.setIcon(new ImageIcon(getClass().getResource("/images/pagar_18.png")));
			menuItemDespesasLancadasPorPeriodoCentroCusto.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamDespesasPagasPeriodoCentroCusto();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Despesas Pagas por Período e por Centro de Custo", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemDespesasLancadasPorPeriodoCentroCusto;

	}	
	
	private SecureMenuItem getMenuItemRelatorioContratos(){
		if(menuItemRelatorioContratos == null){
			menuItemRelatorioContratos = new SecureMenuItem("Contratos por Período");
			menuItemRelatorioContratos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOSRelatorioContratos();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Contratos por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_contrato_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemRelatorioContratos;
	}
	
	private SecureMenuItem getMenuItemRelatorioContratosAditivos(){
		if(menuItemRelatorioContratosAditivos == null){
			menuItemRelatorioContratosAditivos = new SecureMenuItem("Contratos Aditivos por Período");
			menuItemRelatorioContratosAditivos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOSRelatorioContratosAditivos();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Contratos Aditivos por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_contrato_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemRelatorioContratosAditivos;
	}
	
	private SecureMenuItem getMenuItemOSComFaturamento(){
		if(menuItemOSComFaturamento == null){
			menuItemOSComFaturamento = new SecureMenuItem("Ordens de Serviço com Faturamento Confirmado por Período");
			menuItemOSComFaturamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOSFaturada();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Ordens de Serviço com Faturamento Confirmado por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemOSComFaturamento;
	}
	
	private SecureMenuItem getMenuItemOSSemFaturamento(){
		if(menuItemOSSemFaturamento == null){
			menuItemOSSemFaturamento = new SecureMenuItem("Ordens de Serviço SEM Faturamento Confirmado por Período");
			menuItemOSSemFaturamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOSNaoFaturada();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Ordens de Serviço SEM Faturamento Confirmado por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
					
				}
			});	
		}
		return menuItemOSSemFaturamento;
	}
	
	
	
	private SecureMenuItem getMenuItemTotalVendasVendedorPeriodo(){
		if(menuItemTotalVendasVendedorPeriodo == null){
			menuItemTotalVendasVendedorPeriodo = new SecureMenuItem("Total de Vendas por Vendedor e por Período");
			menuItemTotalVendasVendedorPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamTotalVendasVendedorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Total de Vendas por Vendedor e por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemTotalVendasVendedorPeriodo;

	}

	
	

	private SecureMenuItem getMenuItemParcelasReceberPorVendedor(){
		if(menuItemParcelasReceberPorVendedor == null){
			menuItemParcelasReceberPorVendedor = new SecureMenuItem("Parcelas a Receber por Vendedor");
			menuItemParcelasReceberPorVendedor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamParcelasReceberPorVendedor();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Parcelas a Receber por Vendedor", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemParcelasReceberPorVendedor;

	}
	
	private SecureMenuItem getMenuItemOrcamentosPerdidos(){
		if(menuItemOrcamentosPerdidos == null){
			menuItemOrcamentosPerdidos = new SecureMenuItem("Orçamentos Perdidos");
			menuItemOrcamentosPerdidos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOrcamentosPerdidos();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Orçamentos Perdidos por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true); 
				}
			});	
		}
		return menuItemOrcamentosPerdidos;

	}	
	
	private SecureMenuItem getMenuItemOrcamentosAbertos(){
		if(menuItemOrcamentosAbertos == null){
			menuItemOrcamentosAbertos = new SecureMenuItem("Orçamentos Abertos por Período");
			menuItemOrcamentosAbertos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOrcamentosAbertos();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Orçamentos Abertos por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemOrcamentosAbertos;

	}
	
	private SecureMenuItem getMenuItemOrcamentos2Assin(){
		if(menuItemOrcamentos2Assin == null){
			menuItemOrcamentos2Assin = new SecureMenuItem("Orçamentos Fechados com Duas Assinaturas por Período");
			menuItemOrcamentos2Assin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamOrcamentos2Assinaturas();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Orçamentos Fechados com Duas Assinaturas por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_financeiro_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemOrcamentos2Assin;

	}
	
	private SecureMenuItem getMenuItemPautasPeriodo(){
		if(menuItemPautasPeriodo == null){
			menuItemPautasPeriodo = new SecureMenuItem("Pautas por Período");
			menuItemPautasPeriodo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame telaParametro = new TelaParamPautasPorPeriodo();
					JDialog dialog = new JDialog(telaParametro, "Relatório de Pautas por Período", true);
					java.awt.Image icone = new ImageIcon(getClass().getResource("/images/icon_pauta_24.png")).getImage();
					dialog.setIconImage(icone);
					dialog.setModal(true);  
					dialog.setResizable(false);
					dialog.setContentPane(telaParametro.getContentPane());  
					dialog.setBounds(telaParametro.getBounds());  
					dialog.setVisible(true);
				}
			});	
		}
		return menuItemPautasPeriodo;

	}
	

	private JMenu getMenuVenda() {
		if (menuVenda == null) {
			menuVenda = new JMenu();
			menuVenda.setText("Comercial");
			menuVenda.add(getMenuCliente());
			menuVenda.add(getMenuOrcamento());
			menuVenda.add(getMenuOrdemServico());
			menuVenda.add(getMenuItemContrato());
			menuVenda.add(getMenuOrdemServicoExtra());
//			menuVenda.add(getMenuItemContratoAditivo());
			menuVenda.add(getMenuLocal());
			menuVenda.add(getMenuComodato());
			menuVenda.add(getMenuPauta());
			menuVenda.setIcon(new ImageIcon(getClass().getResource("/images/icon_comercial_18.png")));
		}
		return menuVenda;
	}

	private SecureMenuItem getMenuCliente() {
		if (menuCliente == null) {
			menuCliente = new SecureMenuItem();
			menuCliente.setIcon(new ImageIcon(getClass().getResource("/images/icon_cliente_24.png")));
			menuCliente.setText("Clientes");
			menuCliente.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuCliente.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaCliente = WindowFactory.createTelaCliente(jDesktopPaneCentral);
					telaCliente.setVisible(true);
				}
			});
		}
		return menuCliente;
	}

	private SecureMenuItem getMenuOrcamento() {
		if (menuOrcamento == null) {
			menuOrcamento = new SecureMenuItem();
			menuOrcamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_orcamento_24.png")));
			menuOrcamento.setText("Orçamentos");
			menuOrcamento.setAccelerator(KeyStroke.getKeyStroke('2', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuOrcamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOrcamento = WindowFactory.createTelaOrcamento(jDesktopPaneCentral);
					telaOrcamento.setVisible(true);
				}
			});
		}
		return menuOrcamento;
	}

	private SecureMenuItem getMenuOrdemServico() {
		if (menuOrdemServico == null) {
			menuOrdemServico = new SecureMenuItem();
			menuOrdemServico.setIcon(new ImageIcon(getClass().getResource("/images/icon_os_24.png")));
			menuOrdemServico.setText("Ordens de Serviço");
			menuOrdemServico.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuOrdemServico.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOS = WindowFactory.createTelaOS(jDesktopPaneCentral);
					telaOS.setVisible(true);
				}
			});
		}
		return menuOrdemServico;
	}
	
	private SecureMenuItem getMenuOrdemServicoExtra() {
		if (menuOrdemServicoExtra == null) {
			menuOrdemServicoExtra = new SecureMenuItem();
			menuOrdemServicoExtra.setIcon(new ImageIcon(getClass().getResource("/images/icon_ose_24.png")));
			menuOrdemServicoExtra.setText("Ordens de Serviço - Extra");
			//menuOrdemServicoExtra.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuOrdemServicoExtra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOS = WindowFactory.createTelaOSExtra(jDesktopPaneCentral);
					telaOS.setVisible(true);
				}
			});
		}
		return menuOrdemServicoExtra;
	}

	private SecureMenuItem getMenuLocal() {
		if (menuLocal == null) {
			menuLocal = new SecureMenuItem();
			menuLocal.setIcon(new ImageIcon(getClass().getResource("/images/icon_local_24.png")));
			menuLocal.setText("Locais de Evento");
			menuLocal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaLocal = WindowFactory.createTelaLocal(jDesktopPaneCentral);
					telaLocal.setVisible(true);
				}
			});
		}
		return menuLocal;
	}
	
	private SecureMenuItem getMenuComodato() {
		if (menuComodato == null) {
			menuComodato = new SecureMenuItem();
			menuComodato.setIcon(new ImageIcon(getClass().getResource("/images/icon_comodato_24.png")));
			menuComodato.setText("Comodato");
			menuComodato.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaLocal = WindowFactory.createTelaComodato(jDesktopPaneCentral);
					telaLocal.setVisible(true);
				}
			});
		}
		return menuComodato;
	}
	
	private SecureMenuItem getMenuPauta() {
		if (menuPauta == null) {
			menuPauta = new SecureMenuItem();
			menuPauta.setIcon(new ImageIcon(getClass().getResource("/images/icon_pauta_24.png")));
			menuPauta.setText("Pauta");
			menuPauta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaLocal = WindowFactory.createTelaPauta(jDesktopPaneCentral);
					telaLocal.setVisible(true);
				}
			});
		}
		return menuPauta;
	}
	
	private SecureMenuItem getMenuItemContrato() {
		if (menuItemContrato == null) {
			menuItemContrato = new SecureMenuItem();
			menuItemContrato.setIcon(new ImageIcon(getClass().getResource("/images/icon_contrato_24.png")));
			menuItemContrato.setText("Contratos");
			menuItemContrato.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaParametro = WindowFactory.createTelaContrato(jDesktopPaneCentral);
					telaParametro.setVisible(true);
				}
			});
		}
		return menuItemContrato;
	}
	
//	private SecureMenuItem getMenuItemContratoAditivo() {
//		if (menuItemContratoAditivo == null) {
//			menuItemContratoAditivo = new SecureMenuItem();
//			menuItemContratoAditivo.setIcon(new ImageIcon(getClass().getResource("/images/agendamento_48.png")));
//			menuItemContratoAditivo.setText("Gerar Contrato Aditivo");
//			menuItemContratoAditivo.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					JFrame telaParametro = new TelaParamContratoAditivo();
//					telaParametro.setVisible(true);
//				}
//			});
//		}
//		return menuItemContratoAditivo;
//	}

	private JMenu getMenuEstoque() {
		if (menuEstoque == null) {
			menuEstoque = new JMenu();
			menuEstoque.setText("Estoque");
			menuEstoque.add(getMenuEnviarEquipamento());
			menuEstoque.add(getMenuRetornarEquipamento());
			menuEstoque.add(getMenuOSPassagem());
			menuEstoque.add(getMenuAgendarRecolhimento());
			menuEstoque.add(new JSeparator());
			
			menuEstoque.add(getMenuContagemEstoque());
			menuEstoque.add(getMenuRastreamento());
			menuEstoque.add(new JSeparator());
			
			
			menuEstoque.add(getMenuManutencaoPreventiva());
			menuEstoque.add(getMenuManutencaoCorretiva());
			menuEstoque.add(getMenuDescarte());
			
			//menuEstoque.add(getMenuOrdemServicoEmergencial());
			
			menuEstoque.setIcon(new ImageIcon(getClass().getResource("/images/icon_estoque_18c.png")));
		}
		return menuEstoque;
	}

	private SecureMenuItem getMenuEnviarEquipamento() {
		if (menuEnviarEquipamento == null) {
			menuEnviarEquipamento = new SecureMenuItem();
			menuEnviarEquipamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_enviar_24.png")));
			
			menuEnviarEquipamento.setAccelerator(KeyStroke.getKeyStroke('6', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuEnviarEquipamento.setText("Enviar Equipamento");
			menuEnviarEquipamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaEnviarEquipamento = WindowFactory.createTelaEquipamentoEnviado(jDesktopPaneCentral);
					telaEnviarEquipamento.setVisible(true);
				}
			});
		}
		return menuEnviarEquipamento;
	}

	private SecureMenuItem getMenuRetornarEquipamento() {
		if (menuRetornarEquipamento == null) {
			menuRetornarEquipamento = new SecureMenuItem();
			menuRetornarEquipamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_devolucao_24.png")));
			menuRetornarEquipamento.setAccelerator(KeyStroke.getKeyStroke('7', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
			menuRetornarEquipamento.setText("Retorno de Equipamento");
			menuRetornarEquipamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaRetornarEquipamento = WindowFactory.createTelaDevolucao(jDesktopPaneCentral);
					telaRetornarEquipamento.setVisible(true);
				}
			});
		}
		return menuRetornarEquipamento;
	}
	
	
	private SecureMenuItem getMenuOSPassagem() {
		if (menuOSPassagem == null) {
			menuOSPassagem = new SecureMenuItem();
			menuOSPassagem.setIcon(new ImageIcon(getClass().getResource("/images/icon_passagem_24.png")));
			menuOSPassagem.setText("OS de Passagem");
			menuOSPassagem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOSPassagem = WindowFactory.createTelaOSPassagem(jDesktopPaneCentral);
					telaOSPassagem.setVisible(true);
				}
			});
		}
		return menuOSPassagem;
	}


	
	private SecureMenuItem getMenuContagemEstoque(){
		if (menuContagemEstoque==null){
			menuContagemEstoque = new SecureMenuItem();
			menuContagemEstoque.setIcon(new ImageIcon(getClass().getResource("/images/icon_estoque_24.png")));
			menuContagemEstoque.setText("Contagem de Estoque");
			menuContagemEstoque.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaContagem = WindowFactory.createTelaContagemEstoque(jDesktopPaneCentral);
					telaContagem.setVisible(true);
				}
			});
		}
		return menuContagemEstoque;
	}

	private SecureMenuItem getMenuRastreamento(){
		if (menuRastreamento==null){
			menuRastreamento = new SecureMenuItem();
			menuRastreamento.setIcon(new ImageIcon(getClass().getResource("/images/icon_rastrear_24.png")));
			menuRastreamento.setText("Rastreamento");
			menuRastreamento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaRastreamento = WindowFactory.createTelaRastreamento(jDesktopPaneCentral);
					telaRastreamento.setVisible(true);
				}
			});
		}
		return menuRastreamento;
	}

	private SecureMenuItem getMenuDescarte(){
		if (menuDescarte==null){
			menuDescarte = new SecureMenuItem();
			menuDescarte.setIcon(new ImageIcon(getClass().getResource("/images/icon_descarte_24.png")));
			menuDescarte.setText("Descarte de Equipamentos");
			menuDescarte.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaDescarte = WindowFactory.createTelaDescarte(jDesktopPaneCentral);
					telaDescarte.setVisible(true);
				}
			});
		}
		return menuDescarte;
	}

	private SecureMenuItem getMenuManutencaoPreventiva(){
		if (menuManutencaoPreventiva==null){
			menuManutencaoPreventiva = new SecureMenuItem();
			menuManutencaoPreventiva.setIcon(new ImageIcon(getClass().getResource("/images/icon_mpreventiva_24.png")));
			menuManutencaoPreventiva.setText("Manutenção Preventiva");
			menuManutencaoPreventiva.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaManutencaoPreventiva = WindowFactory.createTelaManutencaoPreventiva(jDesktopPaneCentral);
					telaManutencaoPreventiva.setVisible(true);
				}
			});
		}
		return menuManutencaoPreventiva;
	} 

	private SecureMenuItem getMenuManutencaoCorretiva(){
		if (menuManutencaoCorretiva==null){
			menuManutencaoCorretiva = new SecureMenuItem();
			menuManutencaoCorretiva.setIcon(new ImageIcon(getClass().getResource("/images/icon_mcorretiva_24.png")));
			menuManutencaoCorretiva.setText("Manutenção Corretiva");
			menuManutencaoCorretiva.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaManutencaoCorretiva = WindowFactory.createTelaManutencaoCorretiva(jDesktopPaneCentral);
					telaManutencaoCorretiva.setVisible(true);
				}
			});
		}
		return menuManutencaoCorretiva;
	} 

	private SecureMenuItem getMenuOrdemServicoEmergencial(){
		if (menuOrdemServicoEmergencial==null){
			menuOrdemServicoEmergencial = new SecureMenuItem();
			menuOrdemServicoEmergencial.setIcon(new ImageIcon(getClass().getResource("/images/OS2_48.png")));
			menuOrdemServicoEmergencial.setText("Ordem de Serviço Emergencial");
			menuOrdemServicoEmergencial.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaOrdemServicoEmergencial = WindowFactory.createTelaOrdemServicoEmergencial(jDesktopPaneCentral);
					telaOrdemServicoEmergencial.setVisible(true);
				}
			});
		}
		return menuOrdemServicoEmergencial;
	} 

	private SecureMenuItem getMenuAgendarRecolhimento(){
		if (menuAgendarRecolhimento==null){
			menuAgendarRecolhimento = new SecureMenuItem();
			menuAgendarRecolhimento.setIcon(new ImageIcon(getClass().getResource("/images/icon_agendar_24.png")));
			menuAgendarRecolhimento.setText("Agendar Recolhimento de OS");
			menuAgendarRecolhimento.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame telaAgendarRecolhimento = WindowFactory.createTelaAgendarRecolhimento(jDesktopPaneCentral);
					telaAgendarRecolhimento.setVisible(true);
				}
			});
		}
		return menuAgendarRecolhimento;
	} 



	/* ----------------------------------------------
	 * Configurações de Segurança para os botões ---*
	 * ---------------------------------------------*/
	public void habilitaBotoes(){
		TipoUsuario tipoUsuarioLogado = Facade.getInstance().getUsuarioLogado().getTipoUsuario();
		for(SecureComponent botao: botoesSeguros) {
			if(tipoUsuarioLogado.getPermissao().contains(botao.getPermission())) {
				botao.setEnabled(true);
			}
		}
	}

	public void desabilitaBotoes(){
		System.out.println("Desabilitar Botoes");
		for(SecureComponent botao: botoesSeguros) {
//			System.out.println(botao.getPermission());
			//if(botao != null)
			botao.setEnabled(false);
		} 
		TelaLogin telaLogin = WindowFactory.createTelaLogin(this);
		telaLogin.setVisible(true);		
	}	


	public void configurarPermissoesSegurancaBotoes() {
	/*	jButtonEquipamento.setPermission(permission.CADASTRAR_EQUIPAMENTO);
		jButtonCliente.setPermission(permission.CADASTRAR_CLIENTE);
		jButtonOrcamento.setPermission(permission.CADASTRAR_ORCAMENTO);
		jButtonPagamento.setPermission(permission.CADASTRAR_RECEITA);
		jButtonDevolverEquipamento.setPermission(permission.ALTERAR_PERMISSOES_USUARIO);
		jButtonEquiEnviado.setPermission(permission.ALOCAR_RECURSOS);
		jButtonOS.setPermission(permission.CADASTRAR_OS);
		jButtonFuncionario.setPermission(permission.CADASTRAR_FUNCIONARIO);	
		jButtonDevolverEquipamento.setPermission(permission.DEVOLVER_RECURSOS);
		jButtonOSPassagem.setPermission(permission.CADASTRAR_OS_PASSAGEM);

		botoesSeguros.add(jButtonCliente);
		botoesSeguros.add(jButtonFuncionario);
		botoesSeguros.add(jButtonOS);
		botoesSeguros.add(jButtonPagamento);
		botoesSeguros.add(jButtonEquiEnviado);
		botoesSeguros.add(jButtonEquipamento);
		botoesSeguros.add(jButtonOrcamento);
		botoesSeguros.add(jButtonDevolverEquipamento);
		botoesSeguros.add(jButtonOSPassagem);*/

		//Recursos
		menuCadastroGrupo.setPermission(permission.CADASTRAR_GRUPO);
		menuCadastroRecurso.setPermission(permission.CADASTRAR_SUBGRUPO);
		menuCadastroEquipamento.setPermission(permission.CADASTRAR_EQUIPAMENTO);
		menuCadastroEquipamentoSublocado.setPermission(permission.REGISTRO_SUBLOCADOS);
		menuDevolucaoEquipamentoSublocado.setPermission(permission.DEVOLVER_SUBLOCADOS);
		menuCadastroTerceirizado.setPermission(permission.CADASTRAR_RECURSO_TERCEIRIZADO);
//		menuOrdemServicoEmergencial.setPermission(permission.OS_EMERGENCIAL);


		botoesSeguros.add(menuCadastroGrupo);
		botoesSeguros.add(menuCadastroRecurso);
		botoesSeguros.add(menuCadastroEquipamento);
		botoesSeguros.add(menuCadastroEquipamentoSublocado);
		botoesSeguros.add(menuDevolucaoEquipamentoSublocado);
		botoesSeguros.add(menuCadastroTerceirizado);
//		botoesSeguros.add(menuOrdemServicoEmergencial);


		//Administração
		menuFornecedor.setPermission(permission.CADASTRAR_FORNECEDORES);
		menuFornecedorTerceirizado.setPermission(permission.CADASTRAR_FORNECEDORES_TERCEIRIZADOS);
		menuTipoUsuario.setPermission(permission.CADASTRAR_TIPO_USUARIO);
		menuFuncionario.setPermission(permission.CADASTRAR_FUNCIONARIO);
		menuFreelancer.setPermission(permission.CADASTRAR_FREELANCER);
		menuUsuario.setPermission(permission.CADASTRAR_USUARIO);
		menuUnidade.setPermission(permission.CADASTRAR_UNIDADE);
		menuHistoricoCancelamento.setPermission(permission.HISTORICO_CANCELAMENTO);
		menuHistoricoEstorno.setPermission(permission.HISTORICO_ESTORNO);


		botoesSeguros.add(menuFornecedor);
		botoesSeguros.add(menuFornecedorTerceirizado);
		botoesSeguros.add(menuTipoUsuario);
		botoesSeguros.add(menuFuncionario);
		botoesSeguros.add(menuUsuario);
		botoesSeguros.add(menuUnidade);
		botoesSeguros.add(menuFreelancer);
		botoesSeguros.add(menuHistoricoCancelamento);
		botoesSeguros.add(menuHistoricoEstorno);


		//Lixeira
		menuLixeiraCliente.setPermission(permission.LIXEIRA);
		menuLixeiraGrupo.setPermission(permission.LIXEIRA);
		menuLixeiraSubgrupo.setPermission(permission.LIXEIRA);
		menuLixeiraEquipamento.setPermission(permission.LIXEIRA);
		menuLixeiraOS.setPermission(permission.LIXEIRA);
		menuLixeiraOrcamento.setPermission(permission.LIXEIRA);
		menuLixeiraFuncionario.setPermission(permission.LIXEIRA);
		menuLixeiraFornecedor.setPermission(permission.LIXEIRA);
		menuLixeiraFreelancer.setPermission(permission.LIXEIRA);
		menuLixeiraCentroCusto.setPermission(permission.LIXEIRA);

		botoesSeguros.add(menuLixeiraCliente);
		botoesSeguros.add(menuLixeiraGrupo);
		botoesSeguros.add(menuLixeiraSubgrupo);
		botoesSeguros.add(menuLixeiraEquipamento);
		botoesSeguros.add(menuLixeiraOS);
		botoesSeguros.add(menuLixeiraOrcamento);
		botoesSeguros.add(menuLixeiraFuncionario);
		botoesSeguros.add(menuLixeiraFornecedor);
		botoesSeguros.add(menuLixeiraFreelancer);
		botoesSeguros.add(menuLixeiraCentroCusto);	


		//Vendas
		menuCliente.setPermission(permission.CADASTRAR_CLIENTE);
		menuOrcamento.setPermission(permission.CADASTRAR_ORCAMENTO);
		menuLocal.setPermission(permission.CADASTRAR_LOCAL);
		menuOrdemServico.setPermission(permission.CADASTRAR_OS);
		menuOrdemServicoExtra.setPermission(permission.CADASTRAR_OS);
		menuComodato.setPermission(permission.CADASTRAR_COMODATO);
		menuPauta.setPermission(permission.CADASTRAR_PAUTA);
		
		menuItemParcelasReceberPorVendedor.setPermission(permission.RELATORIOS_VENDAS);
		menuItemOrcamentosPerdidos.setPermission(permission.RELATORIOS_VENDAS);
		menuItemTotalVendasVendedorPeriodo.setPermission(permission.RELATORIOS_VENDAS);
		menuItemOSComFaturamento.setPermission(permission.RELATORIOS_VENDAS);
		menuItemOSSemFaturamento.setPermission(permission.RELATORIOS_VENDAS);
		//menuAdicionarEvento.setPermission(permission.ADICIONAR_EVENTOS);
		menuItemContrato.setPermission(permission.GERAR_CONTRATOS);
		//menuItemContratoAditivo.setPermission(permission.LISTAR_TODAS_OS);
		menuItemRelatorioContratos.setPermission(permission.LISTAR_TODAS_OS);
		menuItemRelatorioContratosAditivos.setPermission(permission.LISTAR_TODAS_OS);

		
		botoesSeguros.add(menuCliente);
		botoesSeguros.add(menuItemContrato);
		botoesSeguros.add(menuItemRelatorioContratos);
		botoesSeguros.add(menuItemRelatorioContratosAditivos);
		//botoesSeguros.add(menuItemContratoAditivo);
		botoesSeguros.add(menuOrcamento);
		botoesSeguros.add(menuLocal);
		botoesSeguros.add(menuOrdemServico);
		botoesSeguros.add(menuOrdemServicoExtra);
		botoesSeguros.add(menuComodato);
		botoesSeguros.add(menuPauta);
		//botoesSeguros.add(menuAdicionarEvento);
		botoesSeguros.add(menuItemParcelasReceberPorVendedor);
		botoesSeguros.add(menuItemOrcamentosPerdidos);
		botoesSeguros.add(menuItemOSComFaturamento);
		botoesSeguros.add(menuItemOSSemFaturamento);

		//Estoque
		menuEnviarEquipamento.setPermission(permission.ENVIAR_EQUIPAMENTO);
		menuRetornarEquipamento.setPermission(permission.DEVOLVER_RECURSOS);
		menuContagemEstoque.setPermission(permission.CONTAGEM_ESTOQUE);
		menuRastreamento.setPermission(permission.RASTREAR_EQUIPAMENTOS);
		menuDescarte.setPermission(permission.DESCARTAR_EQUIPAMENTO);
		menuManutencaoCorretiva.setPermission(permission.MANUTENCAO_CORRETIVA);
		menuManutencaoPreventiva.setPermission(permission.MANUTENCAO_PREVENTIVA);
		menuAgendarRecolhimento.setPermission(permission.AGENDAR_RECOLHIMENTO);
		menuOSPassagem.setPermission(permission.CADASTRAR_OS_PASSAGEM);
		
		botoesSeguros.add(menuEnviarEquipamento);
		botoesSeguros.add(menuRetornarEquipamento);
		botoesSeguros.add(menuOSPassagem);
		botoesSeguros.add(menuContagemEstoque);
		botoesSeguros.add(menuRastreamento);
		botoesSeguros.add(menuDescarte);
		botoesSeguros.add(menuManutencaoCorretiva);
		botoesSeguros.add(menuManutencaoPreventiva);
		botoesSeguros.add(menuAgendarRecolhimento);

		//Financeiro
		menuCentroCusto.setPermission(permission.CADASTRAR_CENTRO_CUSTO);
		menuCadastroDespesa.setPermission(permission.CADASTRAR_DESPESA);
		menuFontePagadora.setPermission(permission.CADASTRAR_FONTE_PAGADORA);
		menuContaReceber.setPermission(permission.CONTAS_A_RECEBER);
		botoesSeguros.add(menuCentroCusto);
		botoesSeguros.add(menuCadastroDespesa);
		botoesSeguros.add(menuFontePagadora);
		botoesSeguros.add(menuContaReceber);

		menuItemParcelasReceberPorClientePeriodo.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemParcelasPagasPorClientePeriodo.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesasPagarPorPeriodoFornecedor.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesasPagarPorPeriodoOpcao.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesasPagasPorPeriodoFornecedor.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesasPagasPorPeriodoOpcao.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesasPagasPorPeriodoCentroCusto.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesasLancadasPorPeriodoCentroCusto.setPermission(permission.RELATORIOS_FINANCEIROS);
		
		menuItemOSNaoFaturada.setPermission(permission.RELATORIOS_FINANCEIROS);
		
		
		
		menuItemOSAprovadasPorPeriodo.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemOSNaoFaturada.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemDespesaOS.setPermission(permission.RELATORIOS_FINANCEIROS);
		menuItemOrcamentosDemaisValores.setPermission(permission.CADASTRAR_ORCAMENTO);

		
		botoesSeguros.add(menuItemParcelasReceberPorClientePeriodo);
		botoesSeguros.add(menuItemParcelasPagasPorClientePeriodo);
		botoesSeguros.add(menuItemDespesasPagarPorPeriodoFornecedor);
		botoesSeguros.add(menuItemDespesasPagasPorPeriodoFornecedor);
		botoesSeguros.add(menuItemDespesasPagasPorPeriodoCentroCusto);
		botoesSeguros.add(menuItemDespesasLancadasPorPeriodoCentroCusto);
		botoesSeguros.add(menuItemTotalVendasVendedorPeriodo);
		botoesSeguros.add(menuItemOSAprovadasPorPeriodo);
		botoesSeguros.add(menuItemDespesaOS);
		botoesSeguros.add(menuItemDespesasPagasPorPeriodoOpcao);
		botoesSeguros.add(menuItemDespesasPagarPorPeriodoOpcao);
		botoesSeguros.add(menuItemOSNaoFaturada);
		botoesSeguros.add(menuItemOrcamentosDemaisValores);



		//Relatórios
		menuItemClienteDesatualizado.setPermission(permission.RELATORIOS_GERENCIAIS);
		
		//Planilha
		menuItemListarEventos.setPermission(permission.LISTAR_TODAS_OS);

		//Etiquetas
		menuItemEquipamentoPorEquipamento.setPermission(permission.RELATORIOS_ETIQUETAS);
		menuItemEquipamentoPorGrupo.setPermission(permission.RELATORIOS_ETIQUETAS);
		menuItemEquipamentoPorRecurso.setPermission(permission.RELATORIOS_ETIQUETAS);
		menuItemSublocado.setPermission(permission.RELATORIOS_ETIQUETAS_SUBLOCADOS);

		//menuItemRelatorioFornecedores.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemLocacoesPorPeriodo.setPermission(permission.RELATORIOS_GERENCIAIS);


		menuItemQuantitativoPorGrupo.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemEventosEquipamentosPendentes.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemEventosDesmontagemAtraso.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemEquipeTecnicaPorEvento.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemEventosDesmontarPorPeriodo.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemEventosMontarPorPeriodo.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemControleEquipamentosDescartados.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemFreelancerPorPeriodo.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemQtdDiariasSublocadosPorPeriodo.setPermission(permission.RELATORIOS_GERENCIAIS);
		
		menuItemDiariasSublocadosPeriodoEmpresa.setPermission(permission.RELATORIOS_GERENCIAIS);
		menuItemEquipamentosASeremSublocadosPeriodo.setPermission(permission.RELATORIOS_GERENCIAIS);
		
		menuItemEquipamentosDescartadosPeriodoMotivo.setPermission(permission.RELATORIOS_GERENCIAIS);
		
		menuItemEventosPorPeriodoLocalEvento.setPermission(permission.RELATORIOS_GERENCIAIS);
		

		botoesSeguros.add(menuItemClienteDesatualizado);
		botoesSeguros.add(menuItemListarEventos);
		botoesSeguros.add(menuItemEquipamentoPorEquipamento);
		botoesSeguros.add(menuItemEquipamentoPorGrupo);
		botoesSeguros.add(menuItemEquipamentoPorRecurso);
		botoesSeguros.add(menuItemSublocado);
		//botoesSeguros.add(menuItemRelatorioFornecedores);
		botoesSeguros.add(menuItemLocacoesPorPeriodo);
		botoesSeguros.add(menuItemQuantitativoPorGrupo);
		botoesSeguros.add(menuItemEventosEquipamentosPendentes);
		botoesSeguros.add(menuItemEventosDesmontagemAtraso);
		botoesSeguros.add(menuItemEquipeTecnicaPorEvento);
		botoesSeguros.add(menuItemEventosDesmontarPorPeriodo);
		botoesSeguros.add(menuItemEventosMontarPorPeriodo);
		botoesSeguros.add(menuItemControleEquipamentosDescartados);
		botoesSeguros.add(menuItemFreelancerPorPeriodo);
		botoesSeguros.add(menuItemQtdDiariasSublocadosPorPeriodo);
		botoesSeguros.add(menuItemEquipamentosASeremSublocadosPeriodo);
		botoesSeguros.add(menuItemEquipamentosDescartadosPeriodoMotivo);
		botoesSeguros.add(menuItemDiariasSublocadosPeriodoEmpresa);
		botoesSeguros.add(menuItemEventosPorPeriodoLocalEvento);
		
	}


	public static void main(String[] args){

		UIManager.put("swing.boldMetal", false);
		try {

			UIManager.setLookAndFeel("com.sun.javax.swing.plaf.metal");
		} catch (Exception e) {} 
		
		/*		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		} 
		 
		try {
			//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			Properties props = new Properties();

			props.put("logoString", "SNE"); 
			props.put("licenseKey", "INSERT YOUR LICENSE KEY HERE");


			HiFiLookAndFeel.setCurrentTheme(props);
			// select the Look and Feel
			//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");



			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch(Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		 */

		final TelaPrincipal tela = new TelaPrincipal();
		tela.setVisible(true);

	}


} 
