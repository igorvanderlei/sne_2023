package br.com.sne.sistema.gui.util.components;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ActionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton addButton;
	private JButton deleteButton;
	private JButton updateButton;
	private JButton cancelButton;
	private JButton exitButton;
	private JButton printButton;
	private ActionPanelListener listener;
	

	public ActionPanel() {
		super();
		init();
	}
	
	public ActionPanel(ActionPanelListener listener) {
		this();
		this.listener = listener;
	}

	private void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(973, 60));
		this.setSize(new Dimension(973, 50));
		this.add(new BordedPanel(getAddButton()), null);
		this.add(new BordedPanel(getDeleteButton()), null);
		this.add(new BordedPanel(getUpdateButton()), null);
		this.add(new BordedPanel(getCancelButton()), null);
		this.add(new BordedPanel(getPrintButton()), null);
		this.add(new BordedPanel(getExitButton()), null);

	}

	public JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton("<html><center>Adicionar</center>", new ImageIcon(getClass().getResource("/images/icon_add_24.png")));
			addButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			addButton.setName("addButton");
			addButton.setToolTipText("Alt + A");
			addButton.setMnemonic(KeyEvent.VK_A);
			addButton.setHorizontalAlignment(SwingConstants.CENTER);
			addButton.setHorizontalTextPosition(SwingConstants.CENTER);
			addButton.setVerticalAlignment(SwingConstants.TOP);
			addButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			addButton.setFont(new Font("Arial", Font.BOLD, 12));
			addButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listener.addButtonClicked();
				}
			});
		}
		return addButton;
	}

	public JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton("<html><center>Remover</center>", new ImageIcon(getClass().getResource("/images/icon_remover_24.png")));
			deleteButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			deleteButton.setName("deleteButton");
			deleteButton.setToolTipText("Alt + R");
			deleteButton.setMnemonic(KeyEvent.VK_R);
			deleteButton.setHorizontalAlignment(SwingConstants.CENTER);
			deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
			deleteButton.setVerticalAlignment(SwingConstants.TOP);
			deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
			
			deleteButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listener.deleteButtonClicked();
				}
			});
		}
		return deleteButton;
	}

	public JButton getUpdateButton() {
		if (updateButton == null) {
			updateButton = new JButton("<html><center>Atualizar</center>", new ImageIcon(getClass().getResource("/images/icon_update_24.png")));
			updateButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			updateButton.setName("updateButton");
			updateButton.setToolTipText("Alt + T");
			updateButton.setMnemonic(KeyEvent.VK_T);
			updateButton.setHorizontalAlignment(SwingConstants.CENTER);
			updateButton.setHorizontalTextPosition(SwingConstants.CENTER);
			updateButton.setVerticalAlignment(SwingConstants.TOP);
			updateButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			updateButton.setFont(new Font("Arial", Font.BOLD, 12));
			
			updateButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listener.updateButtonClicked();
				}
			});
		}
		return updateButton;
	}

	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("<html><center>Limpar</center>", new ImageIcon(getClass().getResource("/images/icon_clear_24.png")));
			cancelButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			cancelButton.setName("cancelButton");
			cancelButton.setToolTipText("Alt + N");
			cancelButton.setMnemonic(KeyEvent.VK_N);
			cancelButton.setHorizontalAlignment(SwingConstants.CENTER);
			cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
			cancelButton.setVerticalAlignment(SwingConstants.TOP);
			cancelButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
			
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listener.cancelButtonClicked();
				}
			});
		}		
		return cancelButton;
	}

	public JButton getPrintButton() {
		if (printButton == null) {
			printButton = new JButton("<html><center>Imprimir</center>", new ImageIcon(getClass().getResource("/images/icon_print_24.png")));
			printButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			printButton.setName("printButton");
			printButton.setToolTipText("Alt + P");
			printButton.setMnemonic(KeyEvent.VK_P);
			printButton.setHorizontalAlignment(SwingConstants.CENTER);
			printButton.setHorizontalTextPosition(SwingConstants.CENTER);
			printButton.setVerticalAlignment(SwingConstants.TOP);
			printButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			printButton.setFont(new Font("Arial", Font.BOLD, 12));
			
			printButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listener.printButtonClicked();
				}
			});
		}		
		return printButton;
	}
	
	public JButton getExitButton() {
		if (exitButton == null) {
			exitButton = new JButton("<html><center>Cancelar</center>", new ImageIcon(getClass().getResource("/images/icon_close_24.png")));
			exitButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			exitButton.setName("updateButton");
			exitButton.setToolTipText("Alt + X");
			exitButton.setMnemonic(KeyEvent.VK_X);
			exitButton.setHorizontalAlignment(SwingConstants.CENTER);
			exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
			exitButton.setVerticalAlignment(SwingConstants.TOP);
			exitButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			exitButton.setFont(new Font("Arial", Font.BOLD, 12));
			
			exitButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					listener.exitButtonClicked();
				}
			});
		}
		return exitButton;
	}

}
