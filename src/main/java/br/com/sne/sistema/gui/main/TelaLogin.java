package br.com.sne.sistema.gui.main;

import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.facade.Facade;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Point;

public class TelaLogin extends JDialog {
	private static final long serialVersionUID = 1L;
	private InterfaceTelaPrincipal telaPrincipal = null;
	private JPanel jContentPane = null;
	private JPanel jPanelDadosCadastraisEndereco = null;
	private JPanel jPanelLogin = null;
	private JPanel jPanelSenha = null;
	private JTextField fieldLogin = null;
	private JPasswordField fieldPassword = null;
	private JPanel jPanelbuttonCancel = null;
	private JButton jButtonCancelar = null;
	private ImageIcon img = null;
	private JPanel jPanelbuttonOK = null;
	private JButton jButtonOK = null;
	private ImageIcon but23 = null;
	private JLabel jLabel = null;

	public TelaLogin(InterfaceTelaPrincipal telaPrincipal) {
		super((JFrame) telaPrincipal, true);
		setIconImage(new ImageIcon(getClass().getResource("/images/icon_bloquear_18.png")).getImage());
		this.telaPrincipal = telaPrincipal;
		this.setResizable(false);
        this.setUndecorated(true);
		this.setTitle("Entrar no Sistema");
		initialize();
	}

	private void initialize() {
		this.setSize(600, 300);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setLocationRelativeTo(null);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new CardLayout());
			jContentPane.add(getJPanelDadosCadastraisEndereco(), getJPanelDadosCadastraisEndereco().getName());
		}
		return jContentPane;
	}
	
	private JPanel getJPanelDadosCadastraisEndereco() {
		if (jPanelDadosCadastraisEndereco == null) {
			jLabel = new JLabel();

			jLabel.setIcon(new ImageIcon(getClass().getResource("/images/login2.jpg")));
			jLabel.setLocation(new Point(0, 0));
			jLabel.setSize(new Dimension(600, 300));
			jLabel.setPreferredSize(new Dimension(600, 300));
			jLabel.setText("JLabel");
			jPanelDadosCadastraisEndereco = new JPanel();
			jPanelDadosCadastraisEndereco.setLayout(null);
			jPanelDadosCadastraisEndereco.setName("jPanelDadosCadastrais");
			jPanelDadosCadastraisEndereco.add(getJPanelLogin(), null);
			jPanelDadosCadastraisEndereco.add(getJPanelSenha(), null);
			jPanelDadosCadastraisEndereco.add(getJPanelbuttonCancel(), null);
			jPanelDadosCadastraisEndereco.add(getJPanelbuttonOK(), null);
			jPanelDadosCadastraisEndereco.add(jLabel, null);
		}
		return jPanelDadosCadastraisEndereco;
	}

	public void brincadeira(){
		
		setVisible(true);
		setVisible(false);
		
	}

	private JPanel getJPanelLogin() {
		if (jPanelLogin == null) {
			jPanelLogin = new JPanel();
			jPanelLogin.setLayout(new CardLayout());
			jPanelLogin.setOpaque(false);
			jPanelLogin.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Usuário", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 12), new Color(51, 51, 51)), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
			jPanelLogin.setLocation(new Point(250, 20));
			jPanelLogin.setSize(new Dimension(300, 61));
			jPanelLogin.add(getJTextField(), getJTextField().getName());
		}
		return jPanelLogin;
	}

	private JPanel getJPanelSenha() {
		if (jPanelSenha == null) {
			jPanelSenha = new JPanel();
			jPanelSenha.setLayout(new CardLayout());
			jPanelSenha.setOpaque(false);
			jPanelSenha.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Senha", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 12), new Color(51, 51, 51)), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
			jPanelSenha.setLocation(new Point(250, 85));
			jPanelSenha.setSize(new Dimension(300, 61));
			jPanelSenha.add(getJPasswordField(), getJPasswordField().getName());
		}
		return jPanelSenha;
	}

	private JTextField getJTextField() {
		if (fieldLogin == null) {
			fieldLogin = new JTextField();
			fieldLogin.setName("jTextField");
			fieldLogin.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent evt) {
					if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
						fieldPassword.requestFocus();
					}
				}
			});
		}
		return fieldLogin;
	}

	private JPasswordField getJPasswordField() {
		if (fieldPassword == null) {
			fieldPassword = new JPasswordField();
			fieldPassword.setName("jPasswordField");
			fieldPassword.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent evt) {
					if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
						logar();
					}
				}
			});
		}
		return fieldPassword;
	}

	private JPanel getJPanelbuttonCancel() {
		if (jPanelbuttonCancel == null) {
			jPanelbuttonCancel = new JPanel();
			jPanelbuttonCancel.setLayout(new CardLayout());
			jPanelbuttonCancel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
			jPanelbuttonCancel.setLocation(new Point(400, 162));
			jPanelbuttonCancel.setSize(new Dimension(150, 64));
			jPanelbuttonCancel.add(getJButtonCancelar(), getJButtonCancelar().getName());
		}
		return jPanelbuttonCancel;
	}

	private ImageIcon getImg() {
		if (img == null) {
			img = new ImageIcon(getClass().getResource("/images/error_24.png"));
		}
		return img;
	}

	private JButton getJButtonCancelar() {
		if (jButtonCancelar == null) {
			jButtonCancelar = new JButton("<html><center>Cancelar</center>",
					getImg());
			jButtonCancelar.setName("jButtonCancelar");
			jButtonCancelar.setToolTipText("Alt + C");
			jButtonCancelar.setMnemonic(KeyEvent.VK_C);

			jButtonCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
			jButtonCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
			
			jButtonCancelar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setVisible(false);
						}
					}
			);
			
		}
		return jButtonCancelar;
	}

	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton(getBut23());
			jButtonOK.setName("jButtonAdd");
			jButtonOK.setText("OK");
			
			jButtonOK.setVerticalTextPosition(SwingConstants.BOTTOM);
			jButtonOK.setHorizontalTextPosition(SwingConstants.CENTER);
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButtonOK.requestFocus();
					logar();
					
				}
			});
		}
		return jButtonOK;
	}
	
	
	private JPanel getJPanelbuttonOK() {
		if (jPanelbuttonOK == null) {
			jPanelbuttonOK = new JPanel();
			jPanelbuttonOK.setLayout(new CardLayout());
			jPanelbuttonOK.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
			jPanelbuttonOK.setLocation(new Point(250, 162));
			jPanelbuttonOK.setSize(new Dimension(150, 64));
			jPanelbuttonOK.add(getJButtonOK(), getJButtonOK().getName());
		}
		return jPanelbuttonOK;
	}

	private ImageIcon getBut23() {
		if (but23 == null) {
			but23 = new ImageIcon(getClass().getResource("/images/check_24.png"));
		}
		return but23;
	}



	private void logar() {
		String username = fieldLogin.getText();
		String password = new String(fieldPassword.getPassword());
		Usuario logado = Facade.getInstance().localizarUsuarioPorLogin(username);
		if(logado != null && logado.verificarPassword(password)) {
			Facade.getInstance().setUsuarioLogado(logado);
			telaPrincipal.habilitaBotoes();
			((JFrame) telaPrincipal).setTitle(logado.getUnidade().getNome());
			telaPrincipal.getLogomarca().setIcon(logado.getUnidade().getImagemTelaImage());
			fieldLogin.setText("");
			fieldPassword.setText("");
			dispose();
	} else {
			JOptionPane.showMessageDialog(null, "Usuário ou senha incorreto");
			fieldPassword.setText("");
			fieldPassword.requestFocus();
			
		}
	}

}  //  @jve:decl-index=0:visual-constraint="1,10"
