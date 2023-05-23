package br.com.sne.sistema.gui.util.components;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import br.com.sne.sistema.auth.AccessManager.permission;

public class SecureButton extends JButton implements SecureComponent {
	private static final long serialVersionUID = 1L;

	public SecureButton(String text, Icon icon) {
		super(text, icon);
		setHorizontalAlignment(SwingConstants.LEFT);
		setPreferredSize(new Dimension(175,52));
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.RIGHT);
	}


	private permission permission;

	public permission getPermission() {
		return permission;
	}

	public void setPermission(permission permission) {
		this.permission = permission;
	}

	public Dimension getMaximumSize() {
		return super.getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return super.getPreferredSize();
	}


}
